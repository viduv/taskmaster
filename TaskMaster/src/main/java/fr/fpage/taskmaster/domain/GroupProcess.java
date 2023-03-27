package fr.fpage.taskmaster.domain;

import fr.fpage.backend.openapi.model.ProcessEtat;
import fr.fpage.taskmaster.application.services.JNAService;
import fr.fpage.taskmaster.model.ProcessConfiguration;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupProcess {

    @Getter private final List<Process> processes = new ArrayList<>();

    @Getter
    private final ProcessConfiguration configuration;

    private File stdoutFile;
    private File stderrFile;

    private final JNAService jnaService;
    public GroupProcess(ProcessConfiguration configuration, JNAService jnaService) throws IOException {
        this.configuration = configuration;
        this.jnaService = jnaService;
        System.out.println(configuration.getStdoutFile());
        if (configuration.getStdoutFile() != null) {
            File f = new File(configuration.getStdoutFile());
            f.createNewFile();
            this.stdoutFile = f;
        }
        if (configuration.getStderrFile() != null) {
            File f = new File(configuration.getStderrFile());
            f.createNewFile();
            this.stderrFile = f;
        }
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
