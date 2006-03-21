package org.metaz.harvester;

import org.metaz.util.MetaZ;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;

/**
 * The class MetazJob represents a scheduled job
 * 
 * @author Peter van Dorp, Open University Netherlands, OTO Meta/Z project
 * @version 0.1
 */
public class MetazJob implements org.quartz.Job {

	private static Logger logger = MetaZ.getLogger(MetazScheduler.class);

	private Harvester harvester = new Harvester();

	/**
	 * Constructor does nothing
	 * 
	 */
	public void MetazJob() {

	}

	/**
	 * Carry out the job
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		// get the name of the file to harvest
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String filename = dataMap.getString("filename");
		if (filename != "") {
			// provide the harvester with the filename
			// so that it starts harvesting
			harvester.setXMLFile(filename);
		} else {
			// one of the following error handlers must be removed
			logger.error("No file found to harvest");
			throw new JobExecutionException("No file found to harvest");
		}
	}
}
