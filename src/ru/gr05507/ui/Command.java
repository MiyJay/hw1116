package ru.gr05507.ui;

public interface Command {
    void execute();
    boolean canExecute();
    void undo();
}
