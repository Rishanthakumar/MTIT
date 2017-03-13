
package cgpa.guider.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import cgpa.guider.service.CGPAGuiderService;

/**
 * This class implements a bundle that uses a dictionary
 * service to check for the proper spelling of a word by
 * checking for its existence in the dictionary. This bundle
 * is more complex than the bundle in Example 3 because it
 * monitors the dynamic availability of the dictionary
 * services. In other words, if the service it is using
 * departs, then it stops using it gracefully, or if it needs
 * a service and one arrives, then it starts using it
 * automatically. As before, the bundle uses the first service
 * that it finds and uses the calling thread of the
 * start() method to read words from standard input.
 * You can stop checking words by entering an empty line, but
 * to start checking words again you must stop and then restart
 * the bundle.
**/
public class Activator implements BundleActivator {
    // Bundle's context.
    private BundleContext m_context = null;
    // The service tacker object.
    private ServiceTracker m_tracker = null;

    /**
     * Implements BundleActivator.start(). Crates a service
     * tracker to monitor dictionary services and starts its "word
     * checking loop". It will not be able to check any words until
     * the service tracker find a dictionary service; any discovered
     * dictionary service will be automatically used by the client.
     * It reads words from standard input and checks for their
     * existence in the discovered dictionary.
     * (NOTE: It is very bad practice to use the calling thread
     * to perform a lengthy process like this; this is only done
     * for the purpose of the tutorial.)
     * @param context the framework context for the bundle.
    **/
    public void start(BundleContext context) throws Exception {
        m_context = context;

        // Create a service tracker to monitor CGPAGuiderService services.
        m_tracker = new ServiceTracker(m_context,
                m_context.createFilter("(&(objectClass=" + CGPAGuiderService.class.getName() + ")" + "(Language=*))"),
                null);
        m_tracker.open();

        try {
            System.out.println("Enter a blank line to exit.");
            String word = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            CGPAGuiderService guider = (CGPAGuiderService) m_tracker.getService();
            List<Double> gpaList = new ArrayList<Double>();
			
            // Loop 4 times
            for (int i = 0; i < 4; i++) {
                // Ask the user to enter a word.
                System.out.print("Enter gpa for Year " + Integer.toString(i + 1) + " :");
                word = in.readLine();

                // Get the selected CGPAGuiderService, if available.

                // If the user entered a blank line, then
                // exit the loop.
                if (word.length() == 0) {
                    break;
                } else {
                    gpaList.add( Double.parseDouble(word));
                }
            }

            
            if(guider == null){
              System.out.println("No CGPAGuiderService available.");
            }

             Double[] gpaPrimitive = gpaList.toArray(new Double[0]);
             System.out.println(guider.calculateCGPAStatus(gpaPrimitive));

        } catch (Exception ex) {
            System.out.println("Some error occured");
        }
    }

    /**
     * Implements BundleActivator.stop(). Does nothing since
     * the framework will automatically unget any used services.
     * @param context the framework context for the bundle.
    **/
    public void stop(BundleContext context) {
    }
}