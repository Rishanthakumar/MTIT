/*
 * CGPA_Calculator.
**/

package cgpa.calculator.service;

/**
 * A simple service interface that defines a CGPA calculator service.
 * A CGPA calculator service simply calculates the CGPA according to percentage weighted method and normal method.
**/
public interface CGPACalculatorService
{
    /**
     * Calculates the cumulative CGPA according to the given method.
     * @param gpa - GPAS for all 8 semesters.
     * @param calMethod - normal => for Normal Method.
     *					- weighted => for Weighted Method.
     * @return CGPA for all 8 semesters.
     *     
    **/
    public float calculateCGPA(float[] gpa, String calMethod, int semCount);
}