package fr.fpage.taskmaster.model;

import lombok.Getter;

public enum ExitSignalType {

    SIGINT(2),
    SIGTERM(15),
    SIGKILL(9),
    SIGQUIT(3);

    @Getter
    private final int sig;

    ExitSignalType(int sig) {
        this.sig = sig;
    }
}
