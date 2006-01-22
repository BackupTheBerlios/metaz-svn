/**
 * 
 */
package org.metaz.repository;

import java.net.URI;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.metaz.domain.Record;
import org.metaz.util.MetaZ;

/**
 * Concrete implementation for the Facade interface.
 * @author Jurgen Goelen
 */
public class FacadeImpl implements Facade {

	//logger instance
	private static Logger logger = MetaZ.getLogger(FacadeImpl.class);

	/* The SearchService implementation to be used
	 */
	private SearchService searchService;

	/* The DataService implementation to be used
	 */
	private DataService dataService;

	/* Read-write lock  to avoid incosistent search results 
	 * during the update of the Repository
	 */
	private ReadWriteLock rwLock;

	/*
	 * Default constructor
	 */
	public FacadeImpl() {
		rwLock = new ReentrantReadWriteLock();
	}

	/* (non-Javadoc)
	 * @see org.metaz.repository.Facade#doSearch(java.lang.String)
	 */
	public List<Result<Record>> doSearch(String query) throws Exception {

		List<Result<Record>> records = new Vector<Result<Record>>();
		
		logger.debug("query: " + query);
		
		//lock for reading
		rwLock.readLock().lock();
		logger.debug("acquired read lock");
		
		try {
			//FIXME insert query validation mechanism

			List<Result<URI>> hits = searchService.doSearch(query);

			//transform list
			for (Result<URI> hit : hits) {
				try {
					//get record by its URI
					Record record = dataService.getRecord(hit.getObject());
					//add a new result instance
					records.add(new Result<Record>(record, hit.getScore()));
				} catch (Exception e) {
					//FIXME: the action to be taken depends on the behaviour of the DataService
					logger.log(Priority.ERROR, e);
				}
			}
		} finally {
			logger.debug("released read lock");
			rwLock.readLock().unlock();
		}
		
		return records;
	}

	/* (non-Javadoc)
	 * @see org.metaz.repository.Facade#doUpdate(java.util.List)
	 */
	public void doUpdate(List<Record> records) throws Exception {

		rwLock.writeLock().lock();
		logger.debug("acquired write lock");

		try {
			//clear the indexed
			searchService.doPurge();
			//clear the database
			dataService.doPurge();
			//update index
			searchService.doUpdate(records);
			//update database
			dataService.doUpdate(records);
		} catch (Exception e) {
			//log and re-throw
			String msg = "Error updating repository";
			logger.log(Priority.ERROR, e);
			throw new Exception("Error updating repository: " + e.getMessage());
		} finally {
			logger.debug("released write lock");
			rwLock.writeLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see org.metaz.repository.Facade#setSearchService(org.metaz.repository.SearchService)
	 */
	public void setSearchService(SearchService service) {
		searchService = service;
	}

	/* (non-Javadoc)
	 * @see org.metaz.repository.Facade#setDataService(org.metaz.repository.DataService)
	 */
	public void setDataService(DataService service) {
		dataService = service;
	}

}
