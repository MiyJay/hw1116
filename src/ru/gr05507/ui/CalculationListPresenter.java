// ui/CalculationListPresenter.java
package ru.gr05507.ui;

import ru.gr05507.application.CalculationObserver;
import ru.gr05507.application.CalculationService;
import ru.gr05507.domain.Calculation;

// Наблюдатель (ConcreteObserver): сам обновляет список, когда расчёты изменились
public class CalculationListPresenter implements CalculationObserver {
    private final CalculatorView view;
    private final CalculationService service;

    public CalculationListPresenter(CalculatorView view, CalculationService service) {
        this.view = view;
        this.service = service;
    }

    public void start() {
        service.subscribe(this);   // подписка на изменения списка расчётов
        refresh();
    }

    @Override
    public void onCalculationsChanged() {
        refresh();
    }

    public void refresh() {
        view.showCalculations(service.getAll());
    }

    public void deleteSelected() {
        Calculation calculation = view.getSelected();
        if (calculation == null) {
            view.showError("Выберите расчёт в списке");
            return;
        }
        service.delete(calculation);   // список обновится через наблюдателя
    }
}
