/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author marqu
 */
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
