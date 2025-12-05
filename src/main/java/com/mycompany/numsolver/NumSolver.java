package com.mycompany.numsolver;

import cli.CLISolver;
import model.MetodoGauss;
import presenter.SolverPresenter;
import view.ViewSolverTeste;

public class NumSolver {

    public static void main(String[] args) {
        if (args.length == 0) {
            launchGUI();
            return;
        }

        CLISolver cliSolver = new CLISolver();

        if (args.length >= 2 && (args[0].equals("-f") || args[0].equals("--file"))) {
            cliSolver.solveFromFile(args[1]);
        }

        else if (args[0].equals("-h") || args[0].equals("--help")) {
            CLISolver.printUsage();
        } else {
            cliSolver.solveFromArgs(args);
        }
    }

    private static void launchGUI() {
        ViewSolverTeste view = new ViewSolverTeste();
        MetodoGauss metodoGauss = new MetodoGauss();
        SolverPresenter presenter = new SolverPresenter(view, metodoGauss);

        view.setPresenter(presenter);
        view.setVisible(true);
    }
}
