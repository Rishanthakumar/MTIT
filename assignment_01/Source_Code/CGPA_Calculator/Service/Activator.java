/*
 * CGPA_Calculator.
**/

package cgpa.calculator.service;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceEvent;

/**
 * This class implements a simple bundle that uses the bundle
 * context to register an CGPA Calculator service
 * with the OSGi framework. The dictionary service interface is
 * defined in a separate class file and is implemented by an
 * inner class.
**/
public class Activator implements BundleActivator
{
    /**
     * Implements BundleActivator.start(). Registers an
     * instance of a dictionary service using the bundle context;
     * attaches properties to the service that can be queried
     * when performing a service look-up.
     * @param context the framework context for the bundle.
    **/
    public void start(BundleContext context)
    {
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("Language", "English");
        context.registerService(
            CGPACalculatorService.class.getName(), new CGPA_CalculatorImpl(), props);
		System.out.println("CGPA Calculator Service registered and started successfully");		
    }

    /**
     * Implements BundleActivator.stop(). Does nothing since
     * the framework will automatically unregister any registered services.
     * @param context the framework context for the bundle.
    **/
    public void stop(BundleContext context)
    {
        // NOTE: The service is automatically unregistered.
    }

    /**
     * A private inner class that implements a dictionary service;
     * see DictionaryService for details of the service.
    **/
    private static class CGPA_CalculatorImpl implements CGPACalculatorService
    {


        /**
         * Calculates the cumulative CGPA according to the given method.
         * @param gpa - GPAS for all semesters.
         * @param calMethod - normal => for Normal Method.
         *                  - weighted => for Weighted Method.
         * @return CGPA for all 8 semesters.
         *     
        **/
        public float calculateCGPA(float[] gpa, String calMethod, int semCount);
        {
            if (calMethod == "normal") {
                return calculateNormalCGPA(float[] gpa, int semCount);
            } else if (calMethod == "weighted") {

            }
        }

        private float calculateNormalCGPA(float[] gpa, int semCount) {
            float totalGPA = 0.0f;

            for (int i = 0; i < semCount; i++)
            {
                totalGPA += gpa[i];
            }

            return totalGPA/semCount;
        }

        private float calculateWeightedCGPA(float[] gpa, int semCount) {
            float cgpa = 0.0f;

            switch (semCount) {
                case 1:  
                case 2:  cgpa = 0.0f;
                         break;
                case 3:  cgpa = gpa[2]*0.20;
                         break;
                case 4:  cgpa = gpa[2]*0.20 + gpa[3]*0.20;
                         break;
                case 5:  cgpa = gpa[2]*0.20 + gpa[3]*0.20 + gpa[4]*0.30;
                         break;
                case 6:  cgpa = gpa[2]*0.20 + gpa[3]*0.20 + gpa[4]*0.30 + gpa[5]*0.30;
                         break;
                case 7:  cgpa = gpa[2]*0.20 + gpa[3]*0.20 + gpa[4]*0.30 + gpa[5]*0.30+gpa[6]*0.50;
                         break;
                case 8:  cgpa = gpa[2]*0.20 + gpa[3]*0.20 + gpa[4]*0.30 + gpa[5]*0.30+gpa[6]*0.50 + gpa[7]*0.50;
                         break;
                default: cgpa = 0;
                         break;
            }
        }
}