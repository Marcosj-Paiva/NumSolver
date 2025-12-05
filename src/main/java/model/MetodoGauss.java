package model;

public class MetodoGauss implements IMetodoCalcular{
    @Override
     public float[] calcular(float[][] mat, float[] vet, int dim) {
        for (int i = 0; i < dim - 1; i++) {
            for (int j = i + 1; j < dim; j++) {
                float m = mat[j][i] / mat[i][i];
                for (int c = i; c < dim; c++) {
                    mat[j][c] -= m * mat[i][c];
                }
                vet[j] -= m * vet[i];
            }
        }

        float[] vetRes = new float[dim];
        for (int i = dim - 1; i >= 0; i--) {
            vetRes[i] = vet[i];
            for (int j = i + 1; j < dim; j++) {
                vetRes[i] -= mat[i][j] * vetRes[j];
            }
            vetRes[i] /= mat[i][i];
        }

        return vetRes;
    }
}
