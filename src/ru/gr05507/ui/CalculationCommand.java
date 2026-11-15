package ru.gr05507.ui;

import ru.gr05507.application.CalculationService;
import ru.gr05507.domain.Calculation;
import ru.gr05507.domain.MethodRealisation;

public class CalculationCommand implements Command {
    private final CalculationService service;
    private final String formula;
    private final double lower;
    private final double upper;
    private final int intervals;
    private final MethodRealisation method;
    private Calculation calculation;

    public CalculationCommand(CalculationService service, String formula, double lower,
                              double upper, int intervals, MethodRealisation method) {
        this.service = service;
        this.formula = formula;
        this.lower = lower;
        this.upper = upper;
        this.intervals = intervals;
        this.method = method;
    }

    @Override
    public void execute() {
        calculation = service.calculate(formula, lower, upper, intervals, method);
    }

    @Override
    public boolean canExecute() {
        return formula != null && !formula.trim().isEmpty();
    }

    @Override
    public void undo() {
        if (calculation != null) {
            service.delete(calculation);   // список обновит Observer
        }
    }
}
