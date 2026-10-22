package RootMethods;

public class Bisection {

    public static double f(String eq, double x) {

        try {

            javax.script.ScriptEngine engine =
                    new javax.script.ScriptEngineManager()
                            .getEngineByName("JavaScript");

            eq = eq.replaceAll("\\bx\\b", "(" + x + ")");

            return Double.parseDouble(engine.eval(eq).toString());

        } catch(Exception e) {

            return 0;
        }
    }

    public static String solve(String eq, double xl, double xu, double tol) {

        StringBuilder sb = new StringBuilder();

        double xr = 0, prev = 0, error = 100;

        int i = 1;

        sb.append(String.format("%-5s %-12s %-12s %-12s %-12s\n",
                "Iter", "xL", "xU", "xR", "Error"));

        while(error > tol) {

            xr = (xl + xu) / 2;

            if(i != 1) {

                error = Math.abs((xr - prev) / xr) * 100;
            }


            sb.append(String.format("%-5d %-12.6f %-12.6f %-12.6f %-12.6f\n",
                    i, xl, xu, xr, error));

            if(f(eq, xl) * f(eq, xr) < 0) {
                xu = xr;
            } else {
                xl = xr;
            }

            prev = xr;
            i++;
        }


        sb.append("\n=================================\n");
        sb.append("Total Iterations = " + (i - 1)+ "\n");
        sb.append(String.format("Root = %.6f\n", xr));
        sb.append("=================================\n");

        return sb.toString();
    }
}
