import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
public class Project1 {
    // -----------------------------------------------------------------------
    // Do not modify the names of the variables below! This is where you will
    // store the results generated in the Results() function.
    // -----------------------------------------------------------------------
    public int circleCounter; // Number of non-singular circles in the file.
    public int envelopsFirstLast;  //The result of the first circle enveloping the last circle in the file.
    public double maxArea;       // Area of the largest circle (by area).
    public double minArea;       // Area of the smallest circle (by area).
    public double averageArea;   // Average area of the circles.
    public double stdArea;       // Standard deviation of area of the circles.
    public double medArea;       // Median of the area.
    public int stamp = 472148;
    // -----------------------------------------------------------------------
    // You may implement - but *not* change the names or parameters of - the
    // functions below.
    // -----------------------------------------------------------------------

    /**
     * Default constructor for Project1. You should leave it empty.
     */
    public Project1() {
        // This method is complete.
    }

    public int count(String fileName) {
        double xc,yc,rad;
        int rcount = 0;
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));

            while(scanner.hasNext()) {
                xc = scanner.nextDouble();
                yc = scanner.nextDouble();
                rad = scanner.nextDouble();

                if (rad > Point.GEOMTOL) {
                    rcount++;
                }
            }
        }
        catch(Exception e) {
            System.err.println("An error has occured. See below for details");
            e.printStackTrace();
        }
        return rcount;
    }

    /**
     * Results function. It should open the file called FileName (using
     * Scanner), and from it generate the statistics outlined in the project
     * formulation. These are then placed in the instance variables above.
     *
     * @param fileName The name of the file containing the circle data.
     */
    public void results(String fileName) {
        // You need to fill in this method.
        int rcount = 0;
        double maxX = Double.MIN_VALUE;
        double minX = Double.MAX_VALUE;
        double xc, yc, rad;
        //double xa, ya, rada;
        Circle[] circlet = new Circle[count(fileName)];
        int counter = 0;
        double sum = 0;
        double tum = 0;
        double[] scircles = new double[count(fileName)];
        double[] acircles = new double[count(fileName)];
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
            while (scanner.hasNext()) {
                xc = scanner.nextDouble();
                yc = scanner.nextDouble();
                rad = scanner.nextDouble();
                if (rad > Point.GEOMTOL) {
                    Circle a = new Circle(xc, yc, rad);
                    circlet[counter] = a;
                    acircles[counter] = a.area();
                    counter++;
                }

            }
        }
        catch (Exception e) {
            System.err.println("An error has occured. See below for details");
            e.printStackTrace();
        }
        for (int j = 0; j < acircles.length; j++) {
            if (acircles[j] > maxX) {
                maxX = acircles[j];
            }
            //finding min area
            if (acircles[j] < minX) {
                minX = acircles[j];
            }
            sum += acircles[j]; //sum of areas
            scircles[j] = acircles[j] * acircles[j]; //array with areas squared
        }

        for (double scircle : scircles) { //enhanced for loop
            tum += scircle;
        }
        Project1 a = new Project1();
        circleCounter = a.count(fileName);
        averageArea = sum / circlet.length; //may need to take out of while loop
        maxArea = maxX;
        minArea = minX;
        stdArea = Math.sqrt((tum / scircles.length) - ((sum / scircles.length) * (sum / scircles.length)));
        Arrays.sort(acircles);
        if (acircles.length % 2 == 0) {
            medArea = (acircles[acircles.length / 2] + acircles[(acircles.length / 2) - 1]) / 2;
        } else {
            medArea = acircles[(acircles.length - 1) / 2];
        }
        envelopsFirstLast = circlet[0].envelops(circlet[(circlet.length)-1]); //try rcount
    }


        /**
         * A function to calculate the avarage area of circles in the array provided.
         *
         * @param circles  An array if Circles
         */
        public double averageArea (Circle[] circles){
            // You need to fill in this method
            double sum = 0;
            double avg;
            for (Circle circle : circles) { //enhanced for loop
                sum += circle.getRadius() * circle.getRadius() * Math.PI; //do i have to discount singular
            }
            avg = sum/circles.length;
            return avg;
        }

        /**
         * A function to calculate the standart deviation of areas in the circles in the array provided.
         *
         * @param circles  An array of Circles
         */
        public double areaStandardDeviation (Circle[] circles){
            //You need to complete this method.
            double[] acircles = new double[circles.length];
            double[] scircles = new double[circles.length];
            double tsum = 0;
            for(int j =0; j<circles.length; j++) {
                acircles[j] = circles[j].getRadius() * circles[j].getRadius() * Math.PI;
            }
            for(int k = 0; k<circles.length; k++) {
                scircles[k] = acircles[k] * acircles[k];
            }
            for (int l =0; l<circles.length; l++) {
                tsum += scircles[l];
            }
            return Math.sqrt((tsum/circles.length) - (averageArea(circles)*averageArea(circles)));
        }

        // =======================================================
        // Tester - tests methods defined in this class
        // =======================================================

        /**
         * Your tester function should go here (see week 14 lecture notes if
         * you're confused). It is not tested by BOSS, but you will receive extra
         * credit if it is implemented in a sensible fashion.
         */
        public static void main(String[] args){
            // You need to fill in this method.
            Project1 test = new Project1();
            //System.out.println(test.count("Project1.data"));
            test.results("Project1.data");
            int count=test.circleCounter;
            System.out.println(count);
            int overlap=test.envelopsFirstLast;
            System.out.println(overlap);
            double max=test.maxArea;
            System.out.println(+max);
            double min=test.minArea;
            System.out.println(min);
            double area=test.averageArea;
            System.out.println(area);
            double stddev=test.stdArea;
            System.out.println(stddev);
            double med=test.medArea;
            System.out.println(med);
            Circle a = new Circle(0, 1, 3);
            Circle b = new Circle (0, 0, 1);
            Circle c = new Circle (3, 4, 5);
            Circle[] set = {a,b,c};
            double p = test.averageArea(set);
            double q = test.areaStandardDeviation(set);
            System.out.println(p);
            System.out.println(q);
        }
}