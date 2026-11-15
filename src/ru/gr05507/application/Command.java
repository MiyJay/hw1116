package ru.gr05507.application;

public interface Command {
    void execute();
    boolean canExecute();
    void undo();
}
