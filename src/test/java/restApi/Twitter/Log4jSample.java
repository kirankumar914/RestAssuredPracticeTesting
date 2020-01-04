package restApi.Twitter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;


public class Log4jSample {

   private static Logger log= LogManager.getLogger(Log4jSample.class.getName());

    @Test
    public void firstLog4JSampleTest()
    {
        log.debug("first test case with log 4J");
    }

    @Test
    public void secondLog4JSampleTest()
    {
        log.info("2nd test case with log 4J");
    }

    @Test
    public void ThreedLog4JSampleTest()
    {
        log.fatal("3rd test case with log 4J");
    }

    @Test
    public void fourthdLog4JSampleTest()
    {
       try{
           int i=10;
           int j=i/0;
       }
       catch (Exception e)
       {
      log.error(e);
       }
    }

}
