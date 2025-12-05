package presenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.JFileChooser;
import model.MetodoGauss;
import view.ViewSolverTeste;

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

    
    public void carregarArquivo() {
        JFileChooser fc = new JFileChooser();
        
        int result = fc.showOpenDialog(view); 

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            StringBuilder matrizTexto = new StringBuilder();
            StringBuilder vetorTexto = new StringBuilder();
            
            try (
                Scanner scanner = new Scanner(file).useLocale(Locale.US) 
            ) {
                
                // 1. LER A DIMENSÃO (N)
                if (!scanner.hasNextInt()) {
                    view.setTextResultado("ERRO: O arquivo não inicia com a dimensão N.");
                    return;
                }
                int N = scanner.nextInt();
                
                // 2. LER A MATRIZ DOS COEFICIENTES (N x N)
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (scanner.hasNextDouble()) {
                            matrizTexto.append(scanner.nextDouble());
                            if (j < N - 1) {
                                matrizTexto.append(" "); 
                            }
                        } else {
                            view.setTextResultado("Erro: Dados insuficientes para a Matriz A. Esperava " + N + " x " + N + " elementos.");
                            return;
                        }
                    }
                    matrizTexto.append("\n"); 
                }

                // 3. LER O VETOR SOLUÇÃO (b)
                for (int i = 0; i < N; i++) {
                    if (scanner.hasNextDouble()) {
                        vetorTexto.append(scanner.nextDouble());
                        vetorTexto.append("\n");
                    } else {
                        view.setTextResultado("Erro: Dados insuficientes para o Vetor Solução B.");
                        return;
                    }
                }
                
                // 4. PREENCHE AS JTEXTAREAS NA VIEW
                view.setTextMatriz(matrizTexto.toString().trim());
                view.setTextVetor(vetorTexto.toString().trim());

            } catch (FileNotFoundException ex) {
                view.setTextResultado("ERRO: O arquivo selecionado não foi encontrado.");
            } catch (Exception ex) {
                 view.setTextResultado("ERRO inesperado durante a leitura: " + ex.getMessage());
            }
        } else if (result == JFileChooser.CANCEL_OPTION) {
            view.setTextResultado("Seleção de arquivo cancelada.");
        }
    }
}