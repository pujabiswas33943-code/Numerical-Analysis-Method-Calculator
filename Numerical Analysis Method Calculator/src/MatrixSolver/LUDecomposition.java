package MatrixSolver;

public class LUDecomposition {

    public static String solve(double[][] aug, int n) {

        StringBuilder sb = new StringBuilder();

        double[][] A = new double[n][n];
        double[] B = new double[n];

        // Separate A and B
        for(int i = 0; i < n; i++) {

            for(int j = 0; j < n; j++) {
                A[i][j] = aug[i][j];
            }

            B[i] = aug[i][n];
        }

        double[][] L = new double[n][n];
        double[][] U = new double[n][n];

        sb.append("Input Augmented Matrix\n\n");

        for(int i = 0; i < n; i++) {

            for(int j = 0; j <= n; j++) {

                if(j == n)
                    sb.append(" | ");

                sb.append(String.format("%10.4f", aug[i][j]));
            }

            sb.append("\n");
        }

        sb.append("\n=================================\n\n");

        // LU Decomposition

        for(int i = 0; i < n; i++) {

            // U Matrix

            for(int k = i; k < n; k++) {

                double sum = 0;

                for(int j = 0; j < i; j++) {
                    sum += L[i][j] * U[j][k];
                }

                U[i][k] = A[i][k] - sum;
            }

            // L Matrix

            for(int k = i; k < n; k++) {

                if(i == k) {
                    L[i][i] = 1;
                }
                else {

                    double sum = 0;

                    for(int j = 0; j < i; j++) {
                        sum += L[k][j] * U[j][i];
                    }

                    L[k][i] = (A[k][i] - sum) / U[i][i];
                }
            }
        }

        // Display L


        sb.append("L Matrix\n\n");

        for(int i = 0; i < n; i++) {

            for(int j = 0; j < n; j++) {

                sb.append(String.format("%10.4f", L[i][j]));
            }

            sb.append("\n");
        }

        // Display U


        sb.append("\nU Matrix\n\n");

        for(int i = 0; i < n; i++) {

            for(int j = 0; j < n; j++) {

                sb.append(String.format("%10.4f", U[i][j]));
            }

            sb.append("\n");
        }
        sb.append("\nOriginal Matrix A\n\n");

        for(int i = 0; i < n; i++) {

            for(int j = 0; j < n; j++) {

                sb.append(String.format("%10.4f", A[i][j]));
            }

            sb.append("\n");
        }

        sb.append("\n=================================\n");
        sb.append("Verification (L x U)\n");
        sb.append("=================================\n\n");

        double[][] verify = new double[n][n];

        for(int i = 0; i < n; i++) {

            for(int j = 0; j < n; j++) {

                verify[i][j] = 0;

                for(int k = 0; k < n; k++) {

                    verify[i][j] += L[i][k] * U[k][j];
                }
            }
        }

        for(int i = 0; i < n; i++) {

            for(int j = 0; j < n; j++) {

                sb.append(String.format("%10.4f", verify[i][j]));
            }

            sb.append("\n");
        }



        // Forward Substitution
        // LY = B

        double[] Y = new double[n];

        for(int i = 0; i < n; i++) {

            double sum = 0;

            for(int j = 0; j < i; j++) {
                sum += L[i][j] * Y[j];
            }

            Y[i] = (B[i] - sum) / L[i][i];
        }


        // Backward Substitution
        // UX = Y


        double[] X = new double[n];

        X[n - 1] = Y[n - 1] / U[n - 1][n - 1];

        for(int i = n - 2; i >= 0; i--) {

            double sum = 0;

            for(int j = i + 1; j < n; j++) {
                sum += U[i][j] * X[j];
            }

            X[i] = (Y[i] - sum) / U[i][i];
        }


        // Display Y


        sb.append("\nY Vector\n\n");

        for(int i = 0; i < n; i++) {

            sb.append(String.format("y%d = %.6f\n", i + 1, Y[i]));
        }


        // Final Solution

        sb.append("\n=================================\n");
        sb.append("Final Solution\n");
        sb.append("=================================\n");

        for(int i = 0; i < n; i++) {

            sb.append(String.format("x%d = %.6f\n", i + 1, X[i]));
        }

        return sb.toString();
    }
}
