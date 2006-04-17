package org.metaz.test;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.metaz.domain.HyperlinkMetaData;
import org.metaz.domain.Record;
import org.metaz.repository.DataService;
import org.metaz.repository.DataServiceImpl;
import org.metaz.util.MetaZ;

public class TestDataServicePerformance {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        DataService ds = new DataServiceImpl();

        Record x = null;

        try {
            //System.out.println(System.currentTimeMillis());
            //x = execQuery("http://www.google.com/search?q=1957796098");
            //System.out.println(System.currentTimeMillis());
            //x = execQuery("http://www.google.com/search?q=6893076688");
            //System.out.println(System.currentTimeMillis());
            execQuery();
            execQuery();
            execQuery();
            execQuery();
        
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (x != null) {
            System.out.println(x.toString());
        }

    }

    private static Record execQuery(String uri) throws Exception {
        
        Session sess = null;
        Record result = null;

        try {
            
            System.out.println("open session begin "
                    + System.currentTimeMillis());
            
            sess = MetaZ.getHibernateSessionFactory().openSession();
            
            System.out.println("start open session end "
                    + System.currentTimeMillis());

            System.out.println("query 1 record begin " + System.currentTimeMillis());
                                    
            Criteria crit = sess.createCriteria(HyperlinkMetaData.class);
            crit.add( Expression.eq( "value", "http://www.google.com/search?q=1957796098" ) );                        
            HyperlinkMetaData hmd = (HyperlinkMetaData) crit.uniqueResult();
            
            crit = sess.createCriteria(Record.class);            
            crit.add( Expression.eq( "uri", hmd ) ); 
            result = (Record)crit.uniqueResult();

            
            
            
            
            System.out.println("query 1 record end " + System.currentTimeMillis());
            
            
            crit = sess.createCriteria(HyperlinkMetaData.class);                        
            crit.setMaxResults(200);
            List hmds =  crit.list();

            System.out.println("query 200 records begin " + System.currentTimeMillis());
            
            crit = crit = sess.createCriteria(Record.class);            
            //crit.add( Expression.in( "uri", hmds ) ); 
            List records = crit.list();
            System.out.println(records.size());
            System.out.println("query 200 records end " + System.currentTimeMillis());
            
            System.out.println("query end " + System.currentTimeMillis());
        } catch (HibernateException e) {
            
            throw e; // rethrow error
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
        return result;
    }

    private static void execQuery() throws Exception {
        
        Session sess = null;

        List metadata = null;

        
        try {
            
            sess = MetaZ.getHibernateSessionFactory().openSession();
            
            long now = System.currentTimeMillis();
            
//          .setProjection(Projections.property("schoolType"))
            //.setFetchMode("value", FetchMode.SELECT)         
            //.setFetchMode("targetEndUser",FetchMode.SELECT)
            //.setFetchMode("schoolType",FetchMode.SELECT)
            //.setFetchMode("schoolDiscipline",FetchMode.SELECT)
            //.setFetchMode("professionalSituation",FetchMode.SELECT)
            
            metadata = sess.createCriteria(Record.class)            
            .setProjection(Projections.property("schoolType"))
            .list();
                                               
            //metadata = sess.createFilter(metadata,"");
            
            
            
            
            //metadata = sess.createCriteria(HierarchicalStructuredTextMetaData.class)
            //.add( Expression.eqProperty("kt.name", "mt.name") )
            //.
            //.setFetchMode("value", FetchMode.JOIN)
            //.list();
            
            System.out.println("unique finished " + ((System.currentTimeMillis() - now)/1000f) + "s");
            
            
        } catch (HibernateException e) {
            
            throw e; // rethrow error
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

        System.out.println("# " + metadata.size());
        
    }
    /*
     
     */
    
}
