/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import model.MetodoGauss;

import view.ViewSolverTeste;

/**
 *
 * @author marqu
 */
public class SolverPresenter {
    private final ViewSolverTeste view;
    private final MetodoGauss metodoGauss;

    public SolverPresenter(ViewSolverTeste view, MetodoGauss metodoGauss) {
        this.view = view;
        this.metodoGauss = metodoGauss;

    }

    public void gerarResultado() {
        try {
            // Lê a matriz
            String[] linhas = view.getTextMatriz().trim().split("\\n");
            int n = linhas.length;
            float[][] mat = new float[n][n];

            for (int i = 0; i < n; i++) {
                String[] valores = linhas[i].trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    mat[i][j] = Float.parseFloat(valores[j]);
                }
            }

            // Lê o vetor
            String[] vetLinhas = view.getTextVetor().trim().split("\\n");

            float[] vet = new float[n];
            for (int i = 0; i < n; i++) {
                vet[i] = Float.parseFloat(vetLinhas[i].trim());
            }

            // Calcula
            float[] resultado = metodoGauss.calcular(mat, vet, n);

            // Mostra na View
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < resultado.length; i++) {
                sb.append("x").append(i + 1).append(" = ").append(String.format("%.4f", resultado[i])).append("\n");
            }

            view.setTextResultado(sb.toString());

        } catch (Exception ex) {
        }
    }
}