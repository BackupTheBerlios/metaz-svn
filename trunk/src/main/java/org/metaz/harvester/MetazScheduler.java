package org.metaz.harvester;

import org.apache.log4j.Logger;
import org.metaz.util.MetaZ;

import java.util.*;

import org.quartz.*;

/**
 * The Scheduler class is responsible for starting a Harvester at a regular
 * interval. The harvest start date and time and the harvest interval can be set
 * by an administrator. At a later stage, the directory to harvest from can also
 * be set by an administrator, but currently, this is read from the runtime
 * properties file (metaz.props).
 * 
 * @author Peter van Dorp, Open University Netherlands, OTO Meta/Z project
 * @version 0.3
 */
public class MetazScheduler {

	private static final int START_TIME_HOUR = 2; // default start hour on 24

	// hr clock

	private static final int START_TIME_MINUTE = 0; // default start value for

	// minutes

	private static final int HARVEST_INTERVAL = 24; // default interval value in

	// hours

	private final static String APPLICATIONZ_TRANSFER_PATH = "xml/transfer"; // default

	// directory
	// for
	// XML
	// files

	// Note: if this setting is changed, it has to be changed in the Harvester
	// as well!

	private static Logger logger = MetaZ.getLogger(MetazScheduler.class);

	private Scheduler scheduler = MetaZ.getScheduler();

	private JobDetail jobDetail = new JobDetail("RdMC_harvest_job", null,
			MetazJob.class); // details for the default job

	private Trigger trigger; // trigger to start the scheduled default task

	private Date startTime; // start time for the scheduled task

	private long interval; // interval for the scheduled task

	/**
	 * Constructor: creates and activates the scheduler. Creates the default
	 * setting for the harvest start time and interval and assigns this to the
	 * trigger. Schedules the default harvest job with this trigger.
	 * 
	 */
	public MetazScheduler() {
		resetTrigger(); // set the default harvest start time and interval

		try {
			scheduler.start(); // get the scheduler running
			scheduleJob(); // schedule the default job with the default
			// settings
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
	 * changed, the change takes effect immediately, because the trigger of the
	 * default job is refreshed. NOTE: ALSO ALLOW FOR AN IMMEDIATE START OF THE
	 * HARVESTING BY A DIFFERENT TRIGGER
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
		// get current moment
		java.util.Calendar cal = new java.util.GregorianCalendar();
		// add a day (i.e. set tomorrow) if the today setting is false
		if (!today)
			cal.add(java.util.GregorianCalendar.DAY_OF_MONTH, 1);
		// set the hour
		cal.set(java.util.GregorianCalendar.HOUR, hour);
		// set the minute
		cal.set(java.util.GregorianCalendar.MINUTE, minute);
		cal.set(java.util.GregorianCalendar.SECOND, 0);
		cal.set(java.util.GregorianCalendar.MILLISECOND, 0);
		startTime = cal.getTime();
		// refresh the trigger of the default job
		trigger = new SimpleTrigger("RdMC_harvest_trigger", null, startTime,
				null, SimpleTrigger.REPEAT_INDEFINITELY, interval);
	}

	/**
	 * Sets the interval of the scheduled job. If the interval is changed, this
	 * change will take effect immediately because the trigger of the default
	 * job is updated.
	 * 
	 * @param intv
	 *            the new interval in hours
	 */
	public void setInterval(long intv) {
		if (interval != intv * 1000L * 60L * 60L) {
			// if the interval changed, refresh the trigger
			interval = intv * 1000L * 60L * 60L;
			trigger = new SimpleTrigger("RdMC_harvest_trigger", null,
					startTime, null, SimpleTrigger.REPEAT_INDEFINITELY,
					interval);
		}
	}

	/**
	 * Resets the harvest start date and the interval to the default settings.
	 * These are assigned to the default trigger.
	 */
	public void resetTrigger() {
		setStartTime(START_TIME_HOUR, START_TIME_MINUTE, false);
		setInterval(HARVEST_INTERVAL);
	}

	/**
	 * Gets the (relative) transfer path. This is the directory where the XML
	 * files to harvest are to be found.
	 * 
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
	 * This method is not used. If the transferpath is changed, the scheduled
	 * job will start using the new transferpath immediately (it must be
	 * assigned to the JobDataMap).
	 * 
	 * @param pathname
	 *            the name of the directory
	 * 
	 * public void setTransferPath(String pathname) {}
	 */

	/**
	 * Schedules the default job by defining (1) the directory to harvest from,
	 * (2) the trigger, which contains the time to start the harvesting and the
	 * harvest interval. The relative directory the XML files are harvested from
	 * is retrieved from the MetaZ properties file. The current trigger settings
	 * are assigned. If the administrator has not set these parameters, the
	 * default values will be used.
	 */
	public void scheduleJob() {
		// define the job detail, which only contains the transfer path
		jobDetail.getJobDataMap().put("transferpath", getTransferPath());

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException exc) {
			logger.fatal("Unable to schedule the RdMC harvest job: "
					+ exc.getMessage());
		}

	}

}
