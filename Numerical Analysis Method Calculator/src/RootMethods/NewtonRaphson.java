package RootMethods;

public class NewtonRaphson {


    public static double f(String eq, double x) {

        try {
            eq = eq.replaceAll("\\bx\\b", "(" + x + ")");
            return FalsePosition.evalSimple(eq);

        } catch(Exception e) {

            return Double.NaN;
        }
    }


    //DERIVATIVE

    public static double derivative(String eq, double x) {

        double h = 0.000001;

        return (f(eq, x + h) - f(eq, x - h)) / (2 * h);
    }



    public static String solve(String eq, double x0, double tol) {

        StringBuilder sb = new StringBuilder();

        double x1;
        double error = 100;

        int i = 1;

        sb.append(String.format("%-5s %-15s %-15s %-15s\n", "Iter", "Xi", "Xi+1", "Error"));

        while(i <= 100) {

            double fx = f(eq, x0);
            double dfx = derivative(eq, x0);

            if(Double.isNaN(fx) || Double.isNaN(dfx)) {

                return "Invalid Equation!";
            }

            if(Math.abs(dfx) < 1e-12) {

                return "Derivative became zero.";
            }

            x1 = x0 - (fx / dfx);

            if(i > 1) {

                error = Math.abs((x1 - x0) / x1)

                                * 100;
            }

            sb.append(String.format("%-5d %-15.6f %-15.6f %-15.6f\n", i, x0, x1, error));

            if(i > 1 && error < tol) {

                x0 = x1;
                break;
            }

            x0 = x1;
            i++;
        }

        sb.append("\n=================================\n");
        sb.append("Total Iterations = " + i  + "\n");
        sb.append(String.format("Root = %.6f\n", x0));
        sb.append("=================================\n");

        return sb.toString();
    }
}
