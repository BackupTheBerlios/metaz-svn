/**
 * 
 */
package org.metaz.repository;

import java.net.URI;
import java.util.List;
import java.util.Vector;

import org.metaz.domain.Record;

/**
 * @author jurgoe
 *
 */
public class DataServiceImpl implements DataService {

	/* (non-Javadoc)
	 * @see org.metaz.repository.DataService#getRecords(java.util.List)
	 */
	public List<Record> getRecords(List<URI> ids) throws Exception {
		// TODO Auto-generated method stub
		return new Vector<Record>();
	}

	/* (non-Javadoc)
	 * @see org.metaz.repository.RepositoryService#doUpdate(java.util.List)
	 */
	public void doUpdate(List<Record> records) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.metaz.repository.RepositoryService#doPurge()
	 */
	public void doPurge() throws Exception {
		// TODO Auto-generated method stub

	}

	public Record getRecord(URI id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
