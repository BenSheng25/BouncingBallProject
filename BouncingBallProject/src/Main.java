import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();
        int y = input.nextInt();
        int p = input.nextInt();
        int q = input.nextInt();

        double ballx = p;
        double bally = 0;

        double xVelocity = 0.02;
        double yVelocity = xVelocity*((double)q/p);

        int ballBounceCount = 0;

        while(!((ballx>x-5&&bally<5)||(ballx>x-5&&bally>y-5)||(ballx<5&&bally>y-5)||(ballx<5&&bally<5))){
            if((x-ballx)<0.02||ballx<0.02){
                xVelocity = -xVelocity;
                ballBounceCount++;
            }
            if((y-bally)<0.02||bally<0.02){
                xVelocity = -xVelocity;
                ballBounceCount++;
            }
            ballx+=xVelocity;
            bally+=yVelocity;
        }
        System.out.println(ballBounceCount);
    }
}