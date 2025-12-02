/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.numsolver;

import model.MetodoGauss;
import presenter.SolverPresenter;
import view.ViewSolver;
import view.ViewSolverTeste;

/**
 *
 * @author marqu
 */
public class NumSolver {

    public static void main(String[] args) {
        ViewSolverTeste view = new ViewSolverTeste();
        MetodoGauss metodoGauss = new MetodoGauss();
        SolverPresenter presenter = new SolverPresenter(view,metodoGauss);
        
        view.setPresenter(presenter);
        
        view.setVisible(true);
    }
}
