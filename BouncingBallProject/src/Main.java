import java.util.Scanner;


public class Main {

    /**
     * @author Jackson Ding, Benjamin Sheng
     * source 1: https://github.com/sjay05/CCC-Solutions/blob/master/2002/S5.cpp
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double x = input.nextInt();
        double y = input.nextInt();
        double p = input.nextInt();
        double q = input.nextInt();

        int ballBounceCount = 0;
        double[] initPoint = {p,0};
        //ArrayList<double[]> pointList = new ArrayList<double[]>();
        //pointList.add(initPoint);

        double slope = q/(x-p);
        double currIntercept = getIntercept(initPoint, slope);
        boolean isForever = false;

        double[] secondPoint = {x,q};
        //pointList.add(secondPoint);
        ballBounceCount++;

        double[] lastPoint = secondPoint;

        while(true){
            slope = -slope;
            currIntercept = getIntercept(lastPoint,slope);
            double[] nextPoint;
            double[] p1 = horizontalSolve(0,slope,currIntercept);
            double[] p2 = horizontalSolve(y,slope,currIntercept);
            double[] p3 = verticalSolve(0,slope,currIntercept);
            double[] p4 = verticalSolve(x,slope,currIntercept);
            if(p1[0]<=x&&p1[0]>=0&&!p1.equals(lastPoint)){
                nextPoint = p1;
            }else if(p2[0]<=x&&p2[0]>=0){
                nextPoint = p2;
            }else if(p3[1]<=y&&p3[1]>=0){
                nextPoint = p3;
            }else{
                nextPoint = p4;
            }
            lastPoint = nextPoint;
            if(nextPoint.equals(initPoint)){
                isForever = true;
                break;
            }else if(isInCorner(nextPoint,x,y)){
                break;
            }else{
                ballBounceCount++;
            }

        }

        if (isForever){
            System.out.println(0);
        }else{
            System.out.println(ballBounceCount);
        }
    }

    public static boolean isInCorner(double[] point, double x, double y){
        if(point[0]==0||point[0]==x){
            if(point[1]<=y&&point[1]>=y-5||point[1]<=5&&point[1]>=0){
                return true;
            }
        }else if(point[1]==0||point[1]==y){
            if(point[0]<=x&&point[0]>=x-5||point[0]<=5&&point[0]>=0){
                return true;
            }
        }else{
         return false;
        }
        return false;
    }

    public static double[] verticalSolve(double v, double m, double b){
        double y = m * v + b;
        return new double[]{v, y};
    }

    public static double[] horizontalSolve(double h, double m, double b){
        double x = (h - b) / m;
        return new double[]{x,h};
    }

    public static double getIntercept(double[] point, double slope){
        return point[1]-slope* point[0];
    }
}