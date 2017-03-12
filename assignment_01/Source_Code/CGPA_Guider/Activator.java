package cgpa.guider;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceEvent;

import cgpa.guider.service.CGPAGuiderService;

/**
 * This class implements a simple bundle that uses the bundle
 * context to register an English language CGPAGuiderService
 * with the OSGi framework. The CGPAGuiderService interface is
 * defined in a separate class file and is implemented by an
 * inner class.
**/
public class Activator implements BundleActivator {
    /**
     * Implements BundleActivator.start(). Registers an
     * instance of a CGPAGuiderService using the bundle context;
     * attaches properties to the service that can be queried
     * when performing a service look-up.
     * @param context the framework context for the bundle.
    **/
    public void start(BundleContext context) {
        // Hashtable<String, String> props = new Hashtable<String, String>();
        // props.put("Language", "English");
        context.registerService(CGPAGuiderService.class.getName(), new CGPAGuiderImpl());
        System.out.println("CGPAGuiderService service registered and started successfully");
    }

    /**
     * Implements BundleActivator.stop(). Does nothing since
     * the framework will automatically unregister any registered services.
     * @param context the framework context for the bundle.
    **/
    public void stop(BundleContext context) {
        // NOTE: The service is automatically unregistered.
    }

    private static class CGPAGuiderImpl implements CGPAGuiderService {

        double[] gpaRates = { 0.0, 0.2, 0.3, 0.5 };
        String[] classes = { "First Class", "Second Upper Class", "Second Lower Class" };

        public Boolean firstClass = false;
        public Boolean secondClassUpper = false;
        public Boolean secondClassLower = false;

        public String calculateCGPAStatus(double[] gpa) {
            double totalGPA;
            String message;

            if (gpa.length > 4 || gpa.length == 0) {
                message = "Sorry! You have entered invalid information";

            } else if (gpa.length == 4) {
                totalGPA = this.calCurrentGPA(gpa);
                message = "Your class is : " + this.getClass(totalGPA);

            } else {
                totalGPA = this.calCurrentGPA(gpa);
                message = "Highest Class you can get now is : " + this.getProbability((gpa.length - 1), totalGPA);
            }

            return message;
        }

        private double calCurrentGPA(double[] gpa) {
            double sum = 0;
            for (int i = 0; i < gpa.length; i++) {

                if (this.gpaRates[i] == 0.0) {
                    break;
                } else {
                    sum += gpa[i] / this.gpaRates[i];
                }
            }
            return sum;
        }

        private String getClass(double totalGPA) {
            if (totalGPA > 3.70) {
                this.firstClass = true;
                return this.classes[0];
            } else if (totalGPA > 3.50) {
                this.secondClassUpper = true;
                return this.classes[1];
            } else if (totalGPA > 3.20) {
                this.secondClassLower = true;
                return this.classes[2];
            } else {
                return "Pass";
            }
        }

        private String getProbability(int completedYears, double total) {

            double filledTotal = 0;
            filledTotal = this.fillGPA(completedYears, total);

            return this.getClass(total);

        }

        private double fillGPA(int completedYears, double total) {

            for (int i = completedYears; i < 4; i++) {
                total += (4.0 / this.gpaRates[i]);
            }
            return total;
        }
    }
}