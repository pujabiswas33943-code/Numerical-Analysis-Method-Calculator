package RootMethods;

public class Secant {

    public static double f(String eq, double x) {

        try {
            eq = eq.replaceAll("\\bx\\b", "(" + x + ")");

            return FalsePosition.evalSimple(eq);

        } catch(Exception e) {

            return Double.NaN;
        }
    }

    public static String solve(String eq, double x0, double x1, double tol) {

        StringBuilder sb = new StringBuilder();

        double x2;
        double error = 100;

        int i = 1;

        sb.append(String.format("%-5s %-15s %-15s %-15s %-15s\n", "Iter", "Xi-1", "Xi", "Xi+1", "Error"));

        while(i <= 100) {

            double fx0 = f(eq, x0);
            double fx1 = f(eq, x1);

            if(Double.isNaN(fx0) || Double.isNaN(fx1)) {

                return "Invalid Equation!";
            }

            if(Math.abs(fx1 - fx0) < 1e-12) {

                return "Division by zero risk.";
            }

            x2 = x1 - (fx1 * (x1 - x0)) / (fx1 - fx0);

            if(i > 1) {

                error = Math.abs((x2 - x1) / x2) * 100;
            }

            sb.append(String.format("%-5d %-15.6f %-15.6f %-15.6f %-15.6f\n", i, x0, x1, x2, error));

            if(i > 1 && error < tol) {

                x1 = x2;
                break;
            }

            x0 = x1;
            x1 = x2;

            i++;
        }
        sb.append("\n=================================\n");
        sb.append("Total Iterations = " + i + "\n");
        sb.append("\n=================================\n");
        sb.append(String.format("Root = %.6f\n", x1));
        sb.append("=================================\n");

        return sb.toString();
    }
}