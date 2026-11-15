package ru.gr05507.ui;

import java.util.ArrayDeque;
import java.util.Deque;

// Invoker: выполняет команды и хранит историю для отмены
public class CommandHistory {
    private final Deque<Command> history = new ArrayDeque<>();

    // Выполняет команду, если она допустима, и запоминает её для отмены
    public boolean execute(Command command) {
        if (!command.canExecute()) {
            return false;
        }
        command.execute();
        history.push(command);
        return true;
    }

    // Отменяет последнюю команду; false — если отменять нечего
    public boolean undo() {
        if (history.isEmpty()) {
            return false;
        }
        history.pop().undo();
        return true;
    }
}
