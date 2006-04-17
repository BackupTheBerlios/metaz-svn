package org.metaz.harvester;

import org.apache.log4j.Logger;

import org.metaz.util.MetaZ;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerException;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;

import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * The MetazScheduler class is responsible for starting a Harvester, either at a regular interval or for immediate
 * execution. The harvest start date and time and the harvest interval can be set by an administrator; they default to
 * 2.00 a.m. and 24 hrs. The administrator can also start an immediate harvest. At a later stage, the directory to
 * harvest from can also be set by an administrator, but currently, this is read from the runtime properties file
 * (metaz.props). This class makes use of the Quartz Enterprise Job Scheduler.
 *
 * @author Peter van Dorp, Open University Netherlands, OTO Meta/Z project
 * @version 0.4
 */
public class MetazScheduler {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final int START_TIME_HOUR = 2; // default start hour on 24 hr clock (2 a.m.)

  private static final int START_TIME_MINUTE = 0; // default start value for minutes

  private static final int HARVEST_INTERVAL = 24; // default interval value in hours

  private static final String APPLICATIONZ_TRANSFER_PATH = "xml/transfer"; // default directory for XML files

  // Note: if the transfer path setting is changed, it has to be changed in the Harvester as well!

  private static Logger logger = MetaZ.getLogger(MetazScheduler.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Scheduler     scheduler; // Quartz scheduler
  private JobDetail     rdmcJobDetail; // details for the default job
  private SimpleTrigger trigger; // trigger to start the scheduled default job
  private Date          startTime = new Date(); // start time for the default scheduled job
  private long          interval; // interval for the scheduled default job

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Constructor. Creates the scheduler. Creates a new default trigger and assigns it the default setting for the
   * harvest start time and interval. Creates a new default job detail. Retrieves from the MetaZ properties file
   * the relative directory the XML files are harvested from and stores this setting in the default job detail.
   * Schedules the default harvest job with this default trigger and job detail. Does NOT start the scheduler yet.
   */
  public MetazScheduler() {

    // create the Quartz scheduler
    SchedulerFactory schedFact = new StdSchedulerFactory();

    try {

      scheduler = schedFact.getScheduler();

      // create the default trigger with default settings
      trigger = new SimpleTrigger("RdMC_harvest_trigger", null, startTime, null, SimpleTrigger.REPEAT_INDEFINITELY,
                                  interval);
      // set the default harvest start time and interval relative to the current moment
      resetTrigger();

      // create the default job detail, which only contains the transfer path
      rdmcJobDetail = new JobDetail("RdMC_harvest_job", null, MetazJob.class);
      rdmcJobDetail.getJobDataMap().put("transferpath", getTransferPath());

      // schedule the default job with the default settings
      scheduleJob();

      logger.info("Created the Meta-Z scheduler and scheduled the default job.");

    } catch (SchedulerException exc) {

      logger.fatal("Unable to create the Meta-Z scheduler: " + exc.getMessage());

    }

   }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Only used as a temporary start-up for testing purposes. Starts the scheduler and schedules the harvest job
   * according to the settings given in the parameters.
   *
   * @param args arguments, all are optional. First argument to contain the hour setting for the harvest start time.
   * Second argument to contain the minute setting for the harvest start time. Third argument to contain the interval
   * setting for the harvest interval. The first three must be positive integers. Fourth argument may be "today" or
   * "tomorrow", indicating the harvest start day, or "now" indicating that an immediate harvest is required.
   *
   */
  public static void main(String[] args) {
	  int hr = START_TIME_HOUR;
	  int min = START_TIME_MINUTE;
	  long intv = HARVEST_INTERVAL;
	  boolean today = false;
	  boolean now = false;
	  String fourthArgument = "none";

	  switch(args.length) {

	  case 4:
		  fourthArgument = args[3];
	  case 3:
		  intv = Long.parseLong(args[2]);
	  case 2:
		  min = Integer.parseInt(args[1]);
	  case 1:
		  hr = Integer.parseInt(args[0]);
	  default:

	  }

    if (fourthArgument.equals("today"))
			  today = true;
	else if (fourthArgument.equals("now"))
			  now = true;
    String info = hr + ", min: " + min + ", intv: "+ intv + ", today: " + today + ", now: " + now;
	logger.debug("Scheduler CLI used with arguments hr: " + info);

    // instantiate the MetazScheduler class
    MetazScheduler sch = new MetazScheduler();
    // start the scheduler and schedule the job
    sch.startScheduler();
    sch.setStartTime(hr,min,today);
    sch.setInterval(intv);
    logger.debug("Trigger info after setting startTime: " + sch.getTrigger().toString());
    if(now)
    		sch.executeJob();

  }

  /**
   * Starts the scheduler, enabling execution of any triggered job.
   *
   * @return true if the MetazScheduler was started successfully, else false
   */
  public boolean startScheduler() {
	  try {

		  scheduler.start(); // gets the scheduler running
	      logger.info("Meta-Z scheduler started.");
		  return true;

	  } catch (SchedulerException exc) {

		  logger.fatal("Unable to start the Meta-Z scheduler: " + exc.getMessage());
		  return false;

	  }

  }

  /**
   * Pauses the scheduler, interrupting all job execution.
   *
   * @return true if the MetazScheduler was stopped successfully, else false
   */
  public boolean stopScheduler() {
	  try {

		  scheduler.standby(); // pauses the scheduler
	      logger.info("Meta-Z scheduler paused.");
		  return true;

	  } catch (SchedulerException exc) {

		  logger.fatal("Unable to pause the Meta-Z scheduler: " + exc.getMessage());
		  return false;

	  }

  }

  /**
   * Sets the start time of the scheduled job. It either be set to start on the NEXT day, or on the same day, at
   * the given time. Starting on the same day should only be used for test purposes, because it may cause problems if
   * the start time is earlier than the current time. For that reason, starting on the next day is the default. If the
   * start time is changed, the change takes effect immediately, because the trigger of the default job is refreshed.
   * Note: the start time is also referred to as the next fire time. In fact, both time settings are applied in one
   * go.
   *
   * @param hour the hour to start harvesting (on a 24-hour clock).
   * @param minute the minute to start the harvesting.
   * @param today if true, the harvesting starts on the same day, else on the day after. Defaults to false.
   */
  public void setStartTime(int hour, int minute, boolean today) {

    // get current moment
    java.util.Calendar cal = new java.util.GregorianCalendar();

    // add a day (i.e. set tomorrow) if the today setting is false
    if (! today)
      cal.add(java.util.GregorianCalendar.DAY_OF_MONTH, 1);

    // set the hour
    cal.set(java.util.GregorianCalendar.HOUR_OF_DAY, hour);
    // set the minute
    cal.set(java.util.GregorianCalendar.MINUTE, minute);
    cal.set(java.util.GregorianCalendar.SECOND, 0);
    cal.set(java.util.GregorianCalendar.MILLISECOND, 0);
    startTime = cal.getTime();
    // refresh the trigger of the default job
    trigger.setStartTime(startTime);
    trigger.setNextFireTime(startTime);
    logger.debug("Set harvest start time at " + startTime.toString());

  }

  /**
   * Sets the interval of the scheduled job. If the interval is changed, this change will take effect
   * immediately, because the trigger of the default job is updated.
   *
   * @param intv the new interval in hours
   */
  public void setInterval(long intv) {

    if (interval != (intv * 1000L * 60L * 60L)) {

      // if the interval changed, refresh the trigger
      interval = intv * 1000L * 60L * 60L;
      trigger.setRepeatInterval(interval);
      logger.debug("Set harvest interval at " + interval + " millis");

    }

  }

  /**
   * Resets the harvest start date and the interval to the default settings. These are assigned to the default
   * trigger.
   */
  public void resetTrigger() {

    setStartTime(START_TIME_HOUR, START_TIME_MINUTE, false);
    setInterval(HARVEST_INTERVAL);
    logger.debug("Reset harvest trigger.");

  }

  /**
   * Gets the (relative) transfer path. This is the directory where the XML files to harvest are to be found.
   *
   * @return the relative transfer path
   */
  private String getTransferPath() {

    MetaZ  app = MetaZ.getInstance();
    String path = app.getProperties().getProperty("applicationz.transfer.path.prop");

    if (path == null) {

      path = APPLICATIONZ_TRANSFER_PATH;

    }

    logger.debug("Transfer path is " + path);
    return path;

  }

  /**
   * New method to be created: setTransferPath(String transferpath). Sets the name of the directory where the
   * files to be harvested are put. This method is not used. If the transferpath is changed, the scheduled job
   * will start using the new transferpath immediately (it must be assigned to the JobDataMap and NOTE that
   * this will only work if the job is made into a StatefulJob!).
   */

  /**
   * Schedules the default job with the default trigger (which defines start date and interval) and the default
   * job detail (which defines the harvest directory). If the administrator has not changed the trigger and job detail
   * settings, the default values will be used.
   */
  private void scheduleJob() {

    try {

      scheduler.scheduleJob(rdmcJobDetail, trigger);

    } catch (SchedulerException exc) {

      logger.fatal("Unable to schedule the RdMC harvest job: " + exc.getMessage());

    }

  }

  /**
   * Triggers the default job.
   */
  public void executeJob() {

    try {

      scheduler.triggerJobWithVolatileTrigger("RdMC_harvest_job", null);
      logger.debug("Triggered immediate harvest.");

    } catch (SchedulerException exc) {

      logger.error("Unable to trigger the RdMC harvest job: " + exc.getMessage());

    }

  }


  /**
   * Returns the defult trigger object (test purposes only).
   * @return trigger the default trigger object
   */
  private SimpleTrigger getTrigger() {

	  return trigger;

  }

}
