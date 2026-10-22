package RootMethods;

public class FalsePosition {

    // FUNCTION f(x)
    public static double f(String eq, double x) {

        try {

            eq = eq.replaceAll("\\bx\\b", "(" + x + ")");

            return evalSimple(eq);

        } catch(Exception e) {

            return Double.NaN;
        }
    }

    public static String solve(String eq, double xl, double xu, double tol) {

        StringBuilder sb = new StringBuilder();

        double xr = 0;
        double prev = 0;
        double error = 100;

        int i = 1;

        double fxl = f(eq, xl);
        double fxu = f(eq, xu);

        if (Double.isNaN(fxl) || Double.isNaN(fxu)) {
            return "Invalid Equation!";
        }

        if (fxl * fxu > 0) {
            return "Invalid Initial Guesses!\n" + "f(xL) and f(xU) must have opposite signs.";
        }

        sb.append("=============== FALSE POSITION METHOD ===============\n\n");

        sb.append(String.format("%-5s %-12s %-12s %-12s %-12s\n",
                "Iter", "xL", "xU", "xR", "Error%"));

        sb.append("------------------------------------------------------------\n");

        while (i <= 100) {

            fxl = f(eq, xl);
            fxu = f(eq, xu);

            // Prevent division by zero
            if (Math.abs(fxu - fxl) < 1e-12) {
                sb.append("\nDivision by zero avoided.\n");
                break;
            }

            // False Position Formula
            xr = (xl * fxu - xu * fxl) / (fxu - fxl);

            double fxr = f(eq, xr);

            // Error calculation
            if (i > 1) {

                if (xr != 0)
                    error = Math.abs((xr - prev) / xr) * 100;
                else
                    error = 0;

            } else {
                error = 100;
            }

            sb.append(String.format("%-5d %-12.6f %-12.6f %-12.6f %-12.6f\n", i, xl, xu, xr, error));

            if (error < tol || Math.abs(fxr) < tol)
                break;

            // Interval update
            if (fxl * fxr < 0)
                xu = xr;
            else
                xl = xr;

            prev = xr;
            i++;
        }

        sb.append("\n=================================\n");
        sb.append("Total Iterations = " + i + "\n");
        sb.append(String.format("Root = %.6f\n", xr));
        sb.append("=================================\n");

        return sb.toString();
    }

    public static double evalSimple(String eq) {

        return new Object() {

            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < eq.length()) ? eq.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {

                while (ch == ' ')
                    nextChar();

                if (ch == charToEat) {
                    nextChar();
                    return true;
                }

                return false;
            }

            double parse() {

                nextChar();

                double x = parseExpression();

                if (pos < eq.length()) {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                return x;
            }

            double parseExpression() {

                double x = parseTerm();

                for (;;) {

                    if (eat('+'))
                        x += parseTerm();

                    else if (eat('-'))
                        x -= parseTerm();

                    else
                        return x;
                }
            }

            double parseTerm() {

                double x = parseFactor();

                for (;;) {

                    if (eat('*'))
                        x *= parseFactor();

                    else if (eat('/'))
                        x /= parseFactor();

                    else
                        return x;
                }
            }

            double parseFactor() {

                if (eat('+'))
                    return parseFactor();

                if (eat('-'))
                    return -parseFactor();

                double x;

                int startPos = this.pos;

                // Parentheses
                if (eat('(')) {

                    x = parseExpression();
                    eat(')');

                }

                // Numbers
                else if ((ch >= '0' && ch <= '9') || ch == '.') {

                    while ((ch >= '0' && ch <= '9') || ch == '.')
                        nextChar();

                    x = Double.parseDouble(
                            eq.substring(startPos, this.pos));
                }

                // Functions
                else if (Character.isLetter(ch)) {

                    while (Character.isLetter(ch))
                        nextChar();

                    String func = eq.substring(startPos, this.pos);

                    if (eat('(')) {

                        x = parseExpression();

                        eat(')');
                    }
                    else {

                        x = parseFactor();
                    }

                    switch (func) {

                        case "sqrt":
                            x = Math.sqrt(x);
                            break;

                        case "sin":
                            x = Math.sin(x);
                            break;

                        case "cos":
                            x = Math.cos(x);
                            break;

                        case "tan":
                            x = Math.tan(x);
                            break;

                        case "log":
                            x = Math.log10(x);
                            break;

                        case "ln":
                            x = Math.log(x);
                            break;

                        default:
                            throw new RuntimeException(
                                    "Unknown function: " + func);
                    }
                }

                else {

                    throw new RuntimeException(
                            "Unexpected: " + (char) ch);
                }

                // Power operator (^)
                if (eat('^')) {

                    x = Math.pow(x, parseFactor());
                }

                return x;
            }

        }.parse();
    }}