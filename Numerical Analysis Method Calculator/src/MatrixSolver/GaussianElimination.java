package MatrixSolver;

public class GaussianElimination {

    public static String solve(double[][] a, int n) {

        StringBuilder sb = new StringBuilder();

        sb.append("Input Augmented Matrix\n\n");

        for(int i = 0; i < n; i++) {

            for(int j = 0; j <= n; j++) {

                if(j == n)
                    sb.append(" | ");

                sb.append(String.format("%10.4f", a[i][j]));
            }

            sb.append("\n");
        }

        sb.append("\n=================================\n\n");

        // Forward Elimination
        for(int k = 0; k < n - 1; k++) {

            for(int i = k + 1; i < n; i++) {

                double factor = a[i][k] / a[k][k];

                for(int j = k; j <= n; j++) {

                    a[i][j] = a[i][j] - factor * a[k][j];
                }
            }
        }

        // Back Substitution
        double[] x = new double[n];

        x[n - 1] = a[n - 1][n] / a[n - 1][n - 1];

        for(int i = n - 2; i >= 0; i--) {

            double sum = 0;

            for(int j = i + 1; j < n; j++) {

                sum += a[i][j] * x[j];
            }

            x[i] = (a[i][n] - sum) / a[i][i];
        }

        sb.append("Solution:\n\n");

        for(int i = 0; i < n; i++) {

            sb.append(String.format("x%d = %.6f\n", i + 1, x[i]));
        }

        return sb.toString();
    }
}
