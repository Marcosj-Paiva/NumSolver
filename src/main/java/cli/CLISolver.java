package cli;

import java.io.IOException;

import model.MetodoGauss;
import util.MatrixFileReader;
import util.MatrixFileReader.MatrixData;

/**
 * CLI interface for NumSolver
 * Supports reading matrix data from files
 */
public class CLISolver {

    private final MetodoGauss metodoGauss;

    public CLISolver() {
        this.metodoGauss = new MetodoGauss();
    }

    public void solveFromFile(String filePath) {
        try {
            System.out.println("Reading matrix from file: " + filePath);
            MatrixData data = MatrixFileReader.readFromFile(filePath);

            System.out.println("\nMatrix (" + data.dimension + "x" + data.dimension + "):");
            printMatrix(data.matrix, data.dimension);

            System.out.println("\nResult vector:");
            printVector(data.resultVector, data.dimension);

            System.out.println("\nSolving using Gaussian elimination...");
            float[] solution = metodoGauss.calcular(data.matrix, data.resultVector, data.dimension);

            System.out.println("\nSolution:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("x%d = %.4f\n", i + 1, solution[i]);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error solving system: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Solve a linear system from command line arguments
     * Format: n matrix_values... result_values...
     * Example: 2 1 2 3 4 5 6
     *   represents: 1x + 2y = 5
     *               3x + 4y = 6
     */
    public void solveFromArgs(String[] args) {
        try {
            if (args.length < 1) {
                throw new IllegalArgumentException("Missing dimension argument");
            }

            int n = Integer.parseInt(args[0]);
            int expectedArgs = 1 + n * n + n;

            if (args.length < expectedArgs) {
                throw new IllegalArgumentException(
                    "Expected " + expectedArgs + " arguments for " + n + "x" + n + " system, got " + args.length
                );
            }

            // Parse matrix
            float[][] matrix = new float[n][n];
            int argIndex = 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Float.parseFloat(args[argIndex++]);
                }
            }

            // Parse result vector
            float[] resultVector = new float[n];
            for (int i = 0; i < n; i++) {
                resultVector[i] = Float.parseFloat(args[argIndex++]);
            }

            System.out.println("Matrix (" + n + "x" + n + "):");
            printMatrix(matrix, n);

            System.out.println("\nResult vector:");
            printVector(resultVector, n);

            System.out.println("\nSolving using Gaussian elimination...");
            float[] solution = metodoGauss.calcular(matrix, resultVector, n);

            System.out.println("\nSolution:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("x%d = %.4f\n", i + 1, solution[i]);
            }

        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            printUsage();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error solving system: " + e.getMessage());
            System.exit(1);
        }
    }

    private void printMatrix(float[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%8.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    private void printVector(float[] vector, int n) {
        for (int i = 0; i < n; i++) {
            System.out.printf("%8.2f\n", vector[i]);
        }
    }

    public static void printUsage() {
        System.out.println("\nUsage:");
        System.out.println("  GUI mode:");
        System.out.println("    java -jar NumSolver.jar");
        System.out.println();
        System.out.println("  CLI mode - from file:");
        System.out.println("    java -jar NumSolver.jar -f <file.dat>");
        System.out.println("    java -jar NumSolver.jar --file <file.dat>");
        System.out.println();
        System.out.println("  CLI mode - from arguments:");
        System.out.println("    java -jar NumSolver.jar <n> <matrix_values...> <result_values...>");
        System.out.println();
        System.out.println("  Examples:");
        System.out.println("    java -jar NumSolver.jar -f sistema3x3.dat");
        System.out.println("    java -jar NumSolver.jar 2 1 2 3 4 5 6");
        System.out.println("      Solves: 1x + 2y = 5");
        System.out.println("              3x + 4y = 6");
    }
}
