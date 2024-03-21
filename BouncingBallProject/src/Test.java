import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\s+");

        double n, m, p, q;
        n = scanner.nextDouble();
        m = scanner.nextDouble();
        p = scanner.nextDouble();
        q = scanner.nextDouble();
        double slp = q / (n - p);
        double b = -slp * p;
        for (int i = (int) (2 * n); ; i += n) {
            double y = slp * i + b;
            double val = Math.floor(y / m);
            if (y - (val * m) == q)
                break;
            double dd = y - (val) * m;
            if (dd < 5.0) {
                int ans = (i / (int) n) + (int) val;
                if (dd == 0.0)
                    ans -= 2;
                else
                    ans -= 1;
                System.out.println(ans);
                return;
            }
        }
        System.out.println(0);
    }
}
