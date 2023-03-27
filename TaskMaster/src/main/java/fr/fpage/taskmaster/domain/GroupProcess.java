package fr.fpage.taskmaster.domain;

import fr.fpage.backend.openapi.model.ProcessEtat;
import fr.fpage.taskmaster.application.services.JNAService;
import fr.fpage.taskmaster.model.ProcessConfiguration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GroupProcess {

    @Getter private final List<Process> processes = new ArrayList<>();

    @Getter
    private final ProcessConfiguration configuration;

    private final JNAService jnaService;
    public GroupProcess(ProcessConfiguration configuration, JNAService jnaService) {
        this.configuration = configuration;
        this.jnaService = jnaService;
    }

    public void start() {
        for (int i = 0; i < this.configuration.getNbInstance(); i++) {
            this.processes.add(new Process(this.configuration, this.jnaService));
        }
        this.processes.forEach(process -> {
            if (process.getEtat().equals(ProcessEtat.STOP))
                process.start();
        });
    }

    public void stop() {
        this.processes.forEach(process -> {
            if (process.getEtat().equals(ProcessEtat.RUN))
                process.stopProcess();
        });
    }
}
