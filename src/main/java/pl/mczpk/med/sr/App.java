package pl.mczpk.med.sr;

import org.apache.log4j.Logger;

public class App 
{
	private final static Logger logger = Logger.getLogger(App.class);

    public static void main( String[] args )
    {
    	if(args.length == 0) {
    		System.out.println("You need to give config file name as parameter.");
    		return;
    	}
        String configFileName = args[0];
        try {
        	AppRunner runner = new AppRunner();
        	runner.run(configFileName);
        } catch(Throwable e) {
        	System.out.println("Error while running algorithm. Check log for more information");
        	logger.error("Error while running algorithm.", e);
        }
    }
    
}
