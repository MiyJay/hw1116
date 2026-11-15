package ru.gr05507.application;

import ru.gr05507.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class CalculationService {
    private final CalculationRepository repository;
    private final FormulaParser parser;
    private final List<CalculationObserver> observers = new ArrayList<>();

    public CalculationService(CalculationRepository repository, FormulaParser parser) {
        this.repository = repository;
        this.parser = parser;
    }

    public void subscribe(CalculationObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(CalculationObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (CalculationObserver observer : observers) {
            observer.onCalculationsChanged();
        }
    }

    // Разбирает формулу, вычисляет интеграл и сохраняет расчёт
    public Calculation calculate(String formula, double lower, double upper,
                                 int intervals, MethodRealisation method) {
        DoubleUnaryOperator f = parser.parse(formula);
        double result = Integrator.integrate(f, lower, upper, intervals, method);
        Calculation calculation = new Calculation(formula, lower, upper, intervals, method, result);
        repository.save(calculation);
        notifyObservers();
        return calculation;
    }

    public void delete(Calculation calculation) {
        repository.delete(calculation);
        notifyObservers();
    }

    public List<Calculation> getAll() {
        return repository.findAll();
    }
}
