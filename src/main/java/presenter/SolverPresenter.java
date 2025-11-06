/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import model.MetodoGauss;
import view.ViewSolver;

/**
 *
 * @author marqu
 */
public class SolverPresenter {
    private final ViewSolver view;
    private final MetodoGauss metodoGauss;
    private final int dim;
    
    public SolverPresenter(ViewSolver view, MetodoGauss metodoGauss, int dim){
        this.view = view;
        this.metodoGauss = metodoGauss;
        this.dim = dim;
    }
    
    public void gerarResultado(){
        System.out.println("Botão Calcular foi clicado!");
        float mat[][] = new float[dim][dim]; 
        mat[0][0] = Float.parseFloat(view.getX11().getText());
        mat[1][0] = Float.parseFloat(view.getX12().getText());
        mat[2][0] = Float.parseFloat(view.getX13().getText());
        mat[0][1] = Float.parseFloat(view.getY11().getText());
        mat[1][1] = Float.parseFloat(view.getY12().getText());
        mat[2][1] = Float.parseFloat(view.getY13().getText());
        mat[0][2] = Float.parseFloat(view.getZ11().getText());
        mat[1][2] = Float.parseFloat(view.getZ12().getText());
        mat[2][2] = Float.parseFloat(view.getZ13().getText());
        
        float vetRes[] = new float[dim];
        vetRes[0] = Float.parseFloat(view.getR11().getText());
        vetRes[1] = Float.parseFloat(view.getR12().getText());
        vetRes[2] = Float.parseFloat(view.getR13().getText());
        System.out.println("resultado");
        float resultado[] = metodoGauss.calcular(mat, vetRes, dim);
        System.out.println("resultadooo");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            sb.append("x").append(i + 1).append(" = ").append(resultado[i]).append("\n");
        }
        System.out.println("resultado2");
        view.setResultado(sb.toString());
        System.out.println("resultado 3");
    }
}
