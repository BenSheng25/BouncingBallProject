import java.util.Scanner;

public class Revised {

    /**
     * @author Jackson Ding, Benjamin Sheng
     *         Version 4: Fixed all issues including the rouding error of the corner checks, the two way reflection checks, and corner pocket checks
     *         Main Algorithms - take inputs and simulates the bouncing of the ball as line equations but not with respect to time. Find the intersection between the current ball like with all 4 bounding box lines to see which one is in bound to be the next point, then simulate from there. If the ball reaches one of the initial points then it continues forever.
     *         Solves the CCC '02 S5 - Follow the Bouncing Ball (https://dmoj.ca/problem/ccc02s5)
     *
     *         source 1: https://github.com/sjay05/CCC-Solutions/blob/master/2002/S5.cpp
     *         source 2: David Shan
     *         source 3: https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-box-intersection.html
     *
     *         problem 1: two possible nextPoints
     *         problem 2: didn't check for if the ball hits the edge of the pocket
     *         problem 3: corner checks weren't good or complete depending on the corners
     *         problem 4: corner checks double rounding
     * @param args
     */
    public static void main(String[] args) {
        //takes all inputs
        Scanner input = new Scanner(System.in);
        double x = input.nextInt();
        double y = input.nextInt();
        double p = input.nextInt();
        double q = input.nextInt();

        int ballBounceCount = 0; //make variable for the amount of time the ball bounces
        double[] initPoint = { p, 0 }; //sets the initial point for the ball's bounce to check for infinite loop later

        double slope = q / (x - p); //find the current slope with the ball's trajectory
        double currIntercept = getIntercept(initPoint, slope); //uses the function to find the current intercept for the equation of the line

        double[] secondPoint = { x, q }; //create and declares the second point, all points represented by an array
        ballBounceCount++; //add one bounce for the initial bounce on the right side

        double[] lastPoint = secondPoint; //declares and initializes the last point to the second point, this is used to track the last point the ball bounced on

        //for loop for finding the amount of bounces and simulate, while loop doesn't work because run time expires
        for (int i = 0; i < 1000000; i++) {
            slope = -slope; //switches the slope for the new line of the ball's trajectory
            currIntercept = getIntercept(lastPoint, slope); //gets the intercept for the current line with the new slope and the last point to be used to solve for intersection points for the 4 bounding box lines
            double[] nextPoint; //declares the point for that represent the next point

            //finds the intersection point bewteen the current ball trajectory line with all 4 bounding box lines
            double[] p1 = horizontalSolve(0, slope, currIntercept);
            double[] p2 = horizontalSolve(y, slope, currIntercept);
            double[] p3 = verticalSolve(0, slope, currIntercept);
            double[] p4 = verticalSolve(x, slope, currIntercept);

            //finds which point is actually on the box boundary excluding the current point
            if (p1[0] <= x && p1[0] >= 0 && lastPoint[0] != p1[0] && lastPoint[1] != p1[1]) {
                nextPoint = p1;
            } else if (p2[0] <= x && p2[0] >= 0 && lastPoint[0] != p2[0] && lastPoint[1] != p2[1]) {
                nextPoint = p2;
            } else if (p3[1] <= y && p3[1] >= 0 && lastPoint[0] != p3[0] && lastPoint[1] != p3[1]) {
                nextPoint = p3;
            } else if (p4[1] <= y && p4[1] >= 0 && lastPoint[0] != p4[0] && lastPoint[1] != p4[1]) {
                nextPoint = p4;
            } else {
                //error management
                System.out.println(0);
                System.exit(0);
                nextPoint = new double[] { 0, 0 };
            }
            lastPoint = nextPoint; //sets the last point to the current point
            if (isInCorner(nextPoint, x, y)) {//check if the intersection is in one of the corners if it does then terminate after printing the bounce count
                //System.out.println(nextPoint[0] + " " + nextPoint[1]); //used for debugging
                System.out.println(ballBounceCount);
                return;
            } else if(lastPoint[0]==initPoint[0]&&lastPoint[1]==initPoint[1]){ //check if the lastPoint is equal to the initial point for infinite loop
                System.out.println(0);
                return;
            }else{ //otherwise increase the bouncing
                ballBounceCount++;
            }
        }
        System.out.println(0); //print 0 if everything else fails error managing
    }

//    public static boolean isInCorner(double[] point, double x, double y) {
//        double xPoint = roundToTwoDecimals(point[0]);
//        double yPoint = roundToTwoDecimals(point[1]);
//        if (xPoint < 5 && yPoint < 5) {
//            return true;
//        } else if (xPoint < 5 && yPoint > y - 5) {
//            return true;
//        } else if (xPoint > x - 5 && yPoint < 5) {
//            return true;
//        } else if (xPoint > x - 5 && yPoint > y - 5) {
//            return true;
//        }
//        return false;
//    }

//    public static boolean isInCorner(double[] point, double x, double y){
//        if(point[0]==0||point[0]==x){
//            if((point[1]<y&&point[1]>y-5)||(point[1]<5&&point[1]>0)){
//                return true;
//            }
//        }else if(point[1]==0||point[1]==y){
//            if((point[0]<x&&point[0]>x-5)||(point[0]<5&&point[0]>0)){
//                return true;
//            }
//        }else{
//            return false;
//        }
//        return false;
//

    /**
     *
     * @param point the coordinate of intersection
     * @param x the width of the box
     * @param y the height of the box
     * @return returns of the coordinate is in the corner or not (if the ball is in the corner or not)
     *
     * @else if the ball and the current coordinate is in one of the corners or not and returns a boolean value
     */
    public static boolean isInCorner(double[] point, double x, double y) {
        //convert the coordinate to two decimals to get rid of double data type errors
        double xPoint = roundToTwoDecimals(point[0]);
        double yPoint = roundToTwoDecimals(point[1]);

        //check of the point is in each corner the actual corners are included but the 5 units away are excluded because the problem specify that the balls bounce at the corners
        if (xPoint == 0 || xPoint == x) {
            if ((yPoint <= y && yPoint > y - 5) || (yPoint < 5 && yPoint >= 0)) {
                return true;
            }
        } else if (yPoint == 0 || yPoint == y) {
            if ((xPoint <= x && xPoint > x - 5) || (xPoint < 5 && xPoint >= 0)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param v x coordinate of the vertical lines
     * @param m slope of the ball trajectory line
     * @param b y-intercept of the ball trajectory lines
     * @return the coordinate of the intersection
     */
    public static double[] verticalSolve(double v, double m, double b) {
        double y = m * v + b;
        return new double[] { v, y };
    }

    /**
     * @param h y coordinate of the horizontal line
     * @param m slope of the ball trajectory line
     * @param b y-intercept of the ball trajectory lines
     * @return the coordinate of the intersection
     */
    public static double[] horizontalSolve(double h, double m, double b) {
        double x = (h - b) / m;
        return new double[] { x, h };
    }

    /**
     * @param point the a point on the line
     * @param slope the slope of the line
     * @return the y incercept of the line
     */
    public static double getIntercept(double[] point, double slope) {
        return point[1] - slope * point[0];
    }

    /**
     * @param value given value to be rounded
     * @return the rounded value
     */
    public static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}