package org.metaz.harvester;

import org.apache.log4j.Logger;

import org.metaz.util.MetaZ;

import org.quartz.*;

import org.quartz.impl.StdSchedulerFactory;

import java.util.*;

/**
 * The MetazScheduler class is responsible for starting a Harvester, either at a regular interval or for immediate
 * execution. The harvest start date and time and the harvest interval can be set by an administrator; they default to
 * 2.00 a.m. and 24 hrs. The administrator can also start an immediate harvest. At a later stage, the directory to
 * harvest from can also be set by an administrator, but currently, this is read from the runtime properties file
 * (metaz.props). This class makes use of the Quartz Enterprise Job Scheduler.
 *
 * @author Peter van Dorp, Open University Netherlands, OTO Meta/Z project
 * @version 0.3
 */
public class MetazScheduler {

  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final int START_TIME_HOUR = 2; // default start hour on 24

  // hr clock
  private static final int START_TIME_MINUTE = 0; // default start value for

  // minutes
  private static final int HARVEST_INTERVAL = 24; // default interval value in

  // hours
  private final static String APPLICATIONZ_TRANSFER_PATH = "xml/transfer"; // default

  // directory for XML files

  // Note: if this setting is changed, it has to be changed in the Harvester
  // as well!
  private static Logger logger = MetaZ.getLogger(MetazScheduler.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Scheduler     scheduler; // Quartz scheduler
  private JobDetail     RdMCJobDetail; // details for the default job
  private SimpleTrigger trigger; // trigger to start the scheduled default

  // task
  private Date startTime; // start time for the scheduled task
  private long interval; // interval for the scheduled task

  //~ Constructors -----------------------------------------------------------------------------------------------------

/**
   * Constructor. Creates and activates the scheduler. Creates a new default
   * trigger and assigns it the default setting for the harvest start time and
   * interval. Creates a new default job detail. Retrieves from the MetaZ
   * properties file the relative directory the XML files are harvested from
   * and stores this setting in the default job detail. Schedules the default
   * harvest job with this default trigger and job detail.
   */
  public MetazScheduler() {

    // create the Quartz scheduler
    SchedulerFactory schedFact = new StdSchedulerFactory();

    try {

      scheduler = schedFact.getScheduler();

    } catch (SchedulerException exc) {

      logger.fatal("Unable to create the Meta-Z scheduler: " + exc.getMessage());

    }

    // create the default trigger with default settings
    trigger = new SimpleTrigger("RdMC_harvest_trigger", null, startTime, null, SimpleTrigger.REPEAT_INDEFINITELY,
                                interval);
    resetTrigger(); // set the default harvest start time and interval

    // create the default job detail, which only contains the transfer path
    RdMCJobDetail = new JobDetail("RdMC_harvest_job", null, MetazJob.class);
    RdMCJobDetail.getJobDataMap().put("transferpath", getTransferPath());

    try {

      scheduler.start(); // get the scheduler running
      scheduleJob(); // schedule the default job with the default
                     // settings

    } catch (SchedulerException exc) {

      logger.fatal("Unable to start the Meta-Z scheduler: " + exc.getMessage());

    }

  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Sets the start time of the scheduled job. It either be set to start on the NEXT day, or on the same day, at
   * the given time. Starting on the same day should only be used for test purposes, because it may cause problems if
   * the start time is earlier than the current time. For that reason, starting on the next day is the default. If the
   * start time is changed, the change takes effect immediately, because the trigger of the default job is refreshed.
   *
   * @param hour the hour to start harvesting (on a 24h scale).
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
    cal.set(java.util.GregorianCalendar.HOUR, hour);
    // set the minute
    cal.set(java.util.GregorianCalendar.MINUTE, minute);
    cal.set(java.util.GregorianCalendar.SECOND, 0);
    cal.set(java.util.GregorianCalendar.MILLISECOND, 0);
    startTime = cal.getTime();
    // refresh the trigger of the default job
    trigger.setStartTime(startTime);

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

    }

  }

  /**
   * Resets the harvest start date and the interval to the default settings. These are assigned to the default
   * trigger.
   */
  public void resetTrigger() {

    setStartTime(START_TIME_HOUR, START_TIME_MINUTE, false);
    setInterval(HARVEST_INTERVAL);

  }

  /**
   * Gets the (relative) transfer path. This is the directory where the XML files to harvest are to be found.
   *
   * @return the relative transfer path
   */
  private String getTransferPath() {

    MetaZ  app = MetaZ.getInstance();
    String path = app.getProperties().getProperty("applicationz.transfer.path");

    if (path == null) {

      path = APPLICATIONZ_TRANSFER_PATH;

    }

    return path;

  }

  /**
   * Sets the name of the directory where the files to be harvested are put. This method is not used. If the
   * transferpath is changed, the scheduled job will start using the new transferpath immediately (it must be assigned
   * to the JobDataMap and NOTE that this will only work if the job is made into a StatefulJob!).
   */
  /**
   * Schedules the default job with the default trigger (which defines start date and interval) and the default
   * job detail (which defines the harvest directory). If the administrator has not changed the trigger and job detail
   * settings, the default values will be used.
   */
  public void scheduleJob() {

    try {

      scheduler.scheduleJob(RdMCJobDetail, trigger);

    } catch (SchedulerException exc) {

      logger.fatal("Unable to schedule the RdMC harvest job: " + exc.getMessage());

    }

  }

  /**
   * Executes the default job with a 10 second delay from the time this method is called.
   */
  public void executeJob() {

    // create a once-only trigger that fires in 10 seconds
    long          immediateStartTime = System.currentTimeMillis() + 10000L;
    SimpleTrigger immediateTrigger = new SimpleTrigger("immediateTrigger", null, new Date(immediateStartTime), null, 0,
                                                       0L);

    // schedule the default job with this trigger
    try {

      scheduler.scheduleJob(RdMCJobDetail, immediateTrigger);

    } catch (SchedulerException exc) {

      logger.fatal("Unable to schedule the RdMC harvest job for immediate execution: " + exc.getMessage());

    }

  }

}
