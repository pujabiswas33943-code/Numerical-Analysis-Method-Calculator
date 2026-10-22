package RootMethods;

public class FixedPoint {

    // g(x)

    public static double g(String eq, double x) {

        try {

            eq = eq.replaceAll("\\bx\\b", "(" + x + ")");

            return FalsePosition.evalSimple(eq);

        }
        catch(Exception e) {

            return Double.NaN;
        }
    }


    public static String solve(String eq, double x0, double tol) {

        StringBuilder sb = new StringBuilder();

        double x1;
        double error = 100;

        int i = 1;

        sb.append(String.format(
                "%-5s %-15s %-15s %-15s\n", "Iter", "Xi", "Xi+1", "Error"));

        while(i <= 100) {

            x1 = g(eq, x0);

            if(Double.isNaN(x1)) {

                return "Invalid Equation!";
            }

            if(i > 1) {

                error = Math.abs((x1 - x0) / x1) * 100;
            }

            sb.append(String.format(
                    "%-5d %-15.6f %-15.6f %-15.6f\n", i, x0, x1, error));

            if(i > 1 && error < tol) {

                x0 = x1;
                break;
            }

            x0 = x1;
            i++;
        }
        sb.append("\n=================================\n");
        sb.append("Total Iterations = " + i + "\n");
        sb.append("\n=================================\n");

        sb.append(String.format("Root = %.6f\n", x0));

        sb.append("=================================\n");

        return sb.toString();
    }
}