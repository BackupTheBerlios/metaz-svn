package org.metaz.harvester;

import org.apache.log4j.Logger;
import org.metaz.util.MetaZ;

import java.util.*;

import org.quartz.*;

/**
 * @author Peter van Dorp, Open University Netherlands, OTO Meta/Z project
 * @version 0.1 The Scheduler class is responsible for starting a Harvester at a
 *          regular interval. The start date and time and the interval can be
 *          set by an administrator. Possibly, the directory to harvest from can 
 *          also be set by an administrator.
 * 
 * FROM DOCS: JobDetail instances can then be registered with the Scheduler via
 * the scheduleJob(JobDetail, Trigger) or addJob(JobDetail, boolean) method
 */
public class MetazScheduler {

	private static Logger logger = MetaZ.getLogger(MetazScheduler.class);

	private Scheduler scheduler = MetaZ.getScheduler();

	private JobDetail jobDetail = new JobDetail("RdMC_harvest_job", null,
			MetazJob.class);

	private Trigger trigger; // trigger to start the scheduled task

	private Date startTime; // start time for the scheduled task

	private long interval = 1000L * 60L * 60L * 24L; // interval for the
														// scheduled task:
														// default 24 hrs

	private String filename; // the file to harvest

	/**
	 * Constructor: creates the scheduler and starts it with the 
	 * default setting of starting at 2.00 a.m. on the next day 
	 * and triggering a harvest every 24 hrs
	 * 
	 * @throws SchedulerException
	 */
	public MetazScheduler() {
		setStartTime(2, 0);
		try {
			scheduler.start();
		} catch (SchedulerException exc) {
			logger.fatal("Unable to start the Meta-Z scheduler: "
					+ exc.getMessage());
		}

	}

	/**
	 * Sets the start time of the scheduler. It always starts on the NEXT day,
	 * at the given time 
	 * NOTE: FOR TEST PURPOSES, OVERLOAD THE METHOD TO ALLOW FOR STARTING ON THE SAME DAY
	 * 
	 * @param hour
	 *            the hour to start harvesting (on a 24h scale)
	 * @param minute
	 *            the minute to start the harvesting
	 */
	public void setStartTime(int hour, int minute) {
		java.util.Calendar cal = new java.util.GregorianCalendar(); // current moment
		cal.add(java.util.GregorianCalendar.DAY_OF_MONTH, 1); // add a day
		cal.set(java.util.GregorianCalendar.HOUR, hour); // set the hour
		cal.set(java.util.GregorianCalendar.MINUTE, minute); //set the minute
		cal.set(java.util.GregorianCalendar.SECOND, 0);
		cal.set(java.util.GregorianCalendar.MILLISECOND, 0);
		startTime = cal.getTime();
	}

	/**
	 * Sets the interval of the scheduler
	 * 
	 * @param intv
	 *            the new interval in hours
	 */
	public void setInterval(long intv) {
		interval = intv * 1000L * 60L * 60L;
	}

	/**
	 * Resets the start date and the interval to the default settings of
	 * 2 a.m. and 24 hrs
	 * 
	 */
	public void resetParams() {
		setStartTime(2, 0);
		setInterval(24);
	}

	/**
	 * Sets the name of the file to harvest
	 * NOTE: THIS HAS TO BE EXTRACTED FROM A GIVEN DIRECTORY
	 * EITHER THE ONLY FILE OR THE FILE WITH THE BIGGEST NAME
	 * (I.E. THE LAST ONE PRODUCED) SHOULD BE TAKEN
	 * CREATE AN AUXILIARY METHOD
	 * 
	 * @param fname
	 *            the name of the file to harvest
	 */
	public void setFilename(String fname) {
		filename = fname;
	}
	
	/**
	 * Sets the name of the directory where the files to be harvested
	 * are put.
	 * @param dirname the name of the directory
	 */
	public void setHarvestDirectory(String dirname) {
		
	}

	/**
	 * Starts the default job by starting the default harvester This assumes a
	 * filename for the file to be harvested has been established
	 * THROW AN EXCEPTION IN CASE NO FILENAME IS SPECIFIED
	 * MAKE SURE THE JOB IS ACTUALLY STARTED!
	 */
	public void startJob() {
		// define the job detail
		jobDetail.getJobDataMap().put("filename", filename);

		// make the trigger
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
