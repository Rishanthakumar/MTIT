
package cgpa.guider.service;

public interface CGPAGuiderService {

    public Boolean firstClass;
    public Boolean secondClassUpper;
    public Boolean secondClassLower;

    public String[] calculateCGPAStatus(float[] gpa);
}