import org.apache.log4j.*; // log4j stuff
 
public class LoggingExample { 

  private static Logger logger = MetaZ.getLogger(Test.class);

  public static void main(String []args) {
  
    MetaZ app = MetaZ.getInstance();
  
    logger.info("Hello, logging world");

    System.out.println("Root dir = " + app.getPath(app.getRoot()));
    System.out.println("Config file dir = " + app.getPath(app.getConfigFilePath()));
    System.out.println("ClassPath = " + app.getClassPathString(app.getClassPath()));
    
  }

}