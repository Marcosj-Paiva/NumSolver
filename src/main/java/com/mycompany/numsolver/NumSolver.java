/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.numsolver;

import model.MetodoGauss;
import presenter.SolverPresenter;
import view.ViewSolver;

/**
 *
 * @author marqu
 */
public class NumSolver {

    public static void main(String[] args) {
        ViewSolver view = new ViewSolver();
        MetodoGauss metodoGauss = new MetodoGauss();
        SolverPresenter presenter = new SolverPresenter(view,metodoGauss,3);
        
        view.setPresenter(presenter);
        
        view.setVisible(true);
    }
}
