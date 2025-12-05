package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to read matrix data from .dat files
 * File format:
 * - First line: dimension n
 * - Next n lines: matrix rows (space-separated values)
 * - Last line: result vector (space-separated values)
 */
public class MatrixFileReader {

    public static class MatrixData {
        public float[][] matrix;
        public float[] resultVector;
        public int dimension;

        public MatrixData(float[][] matrix, float[] resultVector, int dimension) {
            this.matrix = matrix;
            this.resultVector = resultVector;
            this.dimension = dimension;
        }
    }

    public static MatrixData readFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }

        if (lines.isEmpty()) {
            throw new IOException("File is empty");
        }

        int n = Integer.parseInt(lines.get(0));

        if (lines.size() < n + 2) {
            throw new IOException("File format is invalid. Expected " + (n + 2) + " lines, got " + lines.size());
        }

        float[][] matrix = new float[n][n];
        for (int i = 0; i < n; i++) {
            String[] values = lines.get(i + 1).trim().split("\\s+");
            if (values.length != n) {
                throw new IOException("Invalid matrix row " + (i + 1) + ". Expected " + n + " values, got " + values.length);
            }
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Float.parseFloat(values[j]);
            }
        }

        // Read result vector
        String[] vectorValues = lines.get(n + 1).trim().split("\\s+");
        if (vectorValues.length != n) {
            throw new IOException("Invalid result vector. Expected " + n + " values, got " + vectorValues.length);
        }

        float[] resultVector = new float[n];
        for (int i = 0; i < n; i++) {
            resultVector[i] = Float.parseFloat(vectorValues[i]);
        }

        return new MatrixData(matrix, resultVector, n);
    }
}
