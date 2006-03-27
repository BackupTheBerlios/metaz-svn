package org.metaz.harvester;

import org.metaz.util.MetaZ;
import org.metaz.util.XMLFilter;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;

import java.io.*;

/**
 * The class MetazJob represents a scheduled Meta/Z job.
 * The execution of this job involves feeding the latest xml
 * file in the transfer directory to the (Meta/Z) Harvester.
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
	 * Carry out the job. This means: identify the XML file to be harvested and
	 * feed it to the harvester.
	 * 
	 * @throws JobExecutionException
	 *             if there is no XML file to harvest
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		// get the directory of the file to harvest
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String transferpath = dataMap.getString("transferpath");
		// identify the file to harvest
		File xmlFile = getXMLFile(transferpath);

		if (xmlFile != null)
			// provide the harvester with the file (if there is one)
			// so that it starts harvesting
			harvester.processXMLFile(xmlFile);
		else
			throw new JobExecutionException("No file found to harvest");
	}
	
	/**
	 * Auxiliary method to return the xml file with the largest name (this should
	 * be the latest addition to the transfer directory) from a given relative directory
	 * @param reldir the relative directory from which the XML file must be picked
	 * @return a file object representing the XML file with the largest filename in the 
	 * given directory, or null, if there is no such file.
	 */
	private File getXMLFile(String reldir) {
		MetaZ app = MetaZ.getInstance();
		File absoluteDir = app.getRelativeFile(reldir);
		String[] list = absoluteDir.list(new XMLFilter()); // names of all XML files in the given directory
		if (list.length == 0) return null;
		java.util.Arrays.sort(list);
		String filename = list[list.length - 1]; // the filename of the latest file according to the naming convention
		return new File(filename);
	}
}
