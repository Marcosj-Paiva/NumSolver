package cli;

import java.io.IOException;

import model.MetodoGauss;
import util.MatrixFileReader;
import util.MatrixFileReader.MatrixData;

public class CLISolver {

    private final MetodoGauss metodoGauss;

    public CLISolver() {
        this.metodoGauss = new MetodoGauss();
    }

    public void solveFromFile(String filePath) {
        try {
            System.out.println("Lendo matriz do arquivo: " + filePath);
            MatrixData data = MatrixFileReader.readFromFile(filePath);

            System.out.println("\nMatriz (" + data.dimension + "x" + data.dimension + "):");
            printMatrix(data.matrix, data.dimension);

            System.out.println("\nVetor resultado:");
            printVector(data.resultVector, data.dimension);

            System.out.println("\nResolvendo usando eliminação de Gauss...");
            float[] solution = metodoGauss.calcular(data.matrix, data.resultVector, data.dimension);

            System.out.println("\nSolução:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("x%d = %.4f\n", i + 1, solution[i]);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Erro ao resolver sistema: " + e.getMessage());
            System.exit(1);
        }
    }

    public void solveFromArgs(String[] args) {
        try {
            if (args.length < 1) {
                throw new IllegalArgumentException("Argumento de dimensão ausente");
            }

            int n = Integer.parseInt(args[0]);
            int expectedArgs = 1 + n * n + n;

            if (args.length < expectedArgs) {
                throw new IllegalArgumentException(
                    "Esperado " + expectedArgs + " argumentos para sistema " + n + "x" + n + ", recebido " + args.length
                );
            }

            float[][] matrix = new float[n][n];
            int argIndex = 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Float.parseFloat(args[argIndex++]);
                }
            }

            float[] resultVector = new float[n];
            for (int i = 0; i < n; i++) {
                resultVector[i] = Float.parseFloat(args[argIndex++]);
            }

            System.out.println("Matriz (" + n + "x" + n + "):");
            printMatrix(matrix, n);

            System.out.println("\nVetor resultado:");
            printVector(resultVector, n);

            System.out.println("\nResolvendo usando eliminação de Gauss...");
            float[] solution = metodoGauss.calcular(matrix, resultVector, n);

            System.out.println("\nSolução:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("x%d = %.4f\n", i + 1, solution[i]);
            }

        } catch (NumberFormatException e) {
            System.err.println("Erro: Formato de número inválido");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
            printUsage();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Erro ao resolver sistema: " + e.getMessage());
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
        System.out.println("\nUso:");
        System.out.println("  Modo GUI:");
        System.out.println("    java -jar NumSolver.jar");
        System.out.println();
        System.out.println("  Modo CLI - a partir de arquivo:");
        System.out.println("    java -jar NumSolver.jar -f <arquivo.dat>");
        System.out.println("    java -jar NumSolver.jar --file <arquivo.dat>");
        System.out.println();
        System.out.println("  Modo CLI - a partir de argumentos:");
        System.out.println("    java -jar NumSolver.jar <n> <valores_matriz...> <valores_resultado...>");
        System.out.println();
        System.out.println("  Exemplos:");
        System.out.println("    java -jar NumSolver.jar -f sistema3x3.dat");
        System.out.println("    java -jar NumSolver.jar 2 1 2 3 4 5 6");
        System.out.println("      Resolve: 1x + 2y = 5");
        System.out.println("               3x + 4y = 6");
    }
}
