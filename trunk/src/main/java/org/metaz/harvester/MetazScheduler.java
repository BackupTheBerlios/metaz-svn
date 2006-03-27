package org.metaz.harvester;

import org.apache.log4j.Logger;
import org.metaz.util.MetaZ;

import java.util.*;

import org.quartz.*;


/**
 * @author Peter van Dorp, Open University Netherlands, OTO Meta/Z project
 * @version 0.1 The Scheduler class is responsible for starting a Harvester at a
 *          regular interval. The start date and time and the interval can be
 *          set by an administrator. At a later stage, the directory to harvest 
 *          from can also be set by an administrator, but currently, 
 *          this is hard-coded.
 * 
 * FROM DOCS: JobDetail instances can then be registered with the Scheduler via
 * the scheduleJob(JobDetail, Trigger) or addJob(JobDetail, boolean) method
 */
public class MetazScheduler {
	
	private static final int START_TIME_HOUR = 2; // default start hour on 24 hr clock
	
	private static final int START_TIME_MINUTE = 0; // default start value for minutes
	
	private static final int HARVEST_INTERVAL = 24; // default interval value in hours

	private final static String APPLICATIONZ_TRANSFER_PATH = "xml/transfer"; // default directory for XML files
	// Note: if this is changed, it has to be changed in the Harvester as well!

	private static Logger logger = MetaZ.getLogger(MetazScheduler.class);

	private Scheduler scheduler = MetaZ.getScheduler();

	private JobDetail jobDetail = new JobDetail("RdMC_harvest_job", null,
			MetazJob.class); // details for the default job
	
	private String transferpath; // directory where the XML file to be harvested is to be found

	private Trigger trigger; // trigger to start the scheduled default task

	private Date startTime; // start time for the scheduled task

	private long interval; // interval for the scheduled task

	  

	/**
	 * Constructor: creates and activates the scheduler, gets the transfer
	 * directory as given in the metaz.props file), and creates the default
	 * setting for the harvest start time and interval. Schedules the default
	 * harvest job with these settings.
	 * 
	 */
	public MetazScheduler() {
		transferpath = getTransferPath(); // establish the XML directory
		resetParams(); // set the default harvest start time and interval
		
		try {
			scheduler.start(); // get the scheduler running
			scheduleJob(); // schedule the default job with the default settings
		} catch (SchedulerException exc) {
			logger.fatal("Unable to start the Meta-Z scheduler: "
					+ exc.getMessage());
		}
		
	}

	/**
	 * Sets the start time of the scheduled job. It it can either be set to
	 * start on the NEXT day, or on the same day, at the given time. Starting on
	 * the same day should only be used for test purposes, because it may cause
	 * problems if the start time is earlier than the current time. For that
	 * reason, starting on the next day is the default. If the start time is
	 * changed, the change takes effect immediately. NOTE: ALSO ALLOW FOR AN
	 * IMMEDIATE START OF THE HARVESTING BY A DIFFERENT TRIGGER
	 * 
	 * @param hour
	 *            the hour to start harvesting (on a 24h scale).
	 * @param minute
	 *            the minute to start the harvesting.
	 * @param today
	 *            if true, the harvesting starts on the same day, else on the
	 *            day after. Defaults to false.
	 */
	public void setStartTime(int hour, int minute, boolean today) {
		java.util.Calendar cal = new java.util.GregorianCalendar(); // get current moment
		if (!today) cal.add(java.util.GregorianCalendar.DAY_OF_MONTH, 1); // add a day (i.e. set tomorrow)
		cal.set(java.util.GregorianCalendar.HOUR, hour); // set the hour
		cal.set(java.util.GregorianCalendar.MINUTE, minute); //set the minute
		cal.set(java.util.GregorianCalendar.SECOND, 0);
		cal.set(java.util.GregorianCalendar.MILLISECOND, 0);
		startTime = cal.getTime();
	}

	/**
	 * Sets the interval of the scheduled job. If the interval is changed,
	 * this change will take effect immediately SO WE NEED TO CALL A SETPARAMS() SETTER
	 * 
	 * @param intv
	 *            the new interval in hours
	 */
	public void setInterval(long intv) {
		interval = intv * 1000L * 60L * 60L;
	}

	/**
	 * Resets the harvest start date and the interval to the default settings.
	 * 
	 */
	public void resetParams() {
		setStartTime(START_TIME_HOUR, START_TIME_MINUTE, false);
		setInterval(HARVEST_INTERVAL);
	}

	/**
	 * Gets the (relative) transfer path. This is the directory where the XML files to
	 * harvest are to be found.
	 * @return the relative transfer path
	 */
	private String getTransferPath() {
		MetaZ app = MetaZ.getInstance();
		String path = app.getProperties().getProperty(
				"applicationz_transfer_path");
		if (path == null) {
			path = APPLICATIONZ_TRANSFER_PATH;
		}
		return path;
	}
	
	/**
	 * Sets the name of the directory where the files to be harvested are put.
	 * This method is not used. If the transferpath is changed,
	 * the scheduled job will start using the new transferpath immediately
	 * 
	 * @param pathname
	 *            the name of the directory
	 * 
	 * public void setTransferPath(String pathname) {}
	 */

	/**
	 * Schedules the default job by defining (1) the directory to harvest from,
	 * (2) the time to start the harvesting, (3) the harvest interval.
	 * This assumes a relative directory the XML files are harvested from has been
	 * established, and the harvest start time and interval have been set.
	 * If the administrator has not picked any values for these parameters,
	 * the default values will be used.
	 * The default job is currently scheduled in the constructor of this class,
	 * with the default parameters.
	 */
	public void scheduleJob() {
		// define the job detail, which only contains the transfer path
		jobDetail.getJobDataMap().put("transferpath", transferpath);

		// make the trigger, which specifies the moment to start harvesting and the interval
		trigger = new SimpleTrigger("RdMC_harvest_trigger", null, startTime,
				null, SimpleTrigger.REPEAT_INDEFINITELY, interval);

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException exc) {
			logger.fatal("Unable to schedule the RdMC harvest job: "
					+ exc.getMessage());
		}

	}

}
