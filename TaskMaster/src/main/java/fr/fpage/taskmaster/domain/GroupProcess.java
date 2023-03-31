package fr.fpage.taskmaster.domain;

import fr.fpage.backend.openapi.model.ProcessEtat;
import fr.fpage.taskmaster.application.services.JNAService;
import fr.fpage.taskmaster.model.ProcessConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GroupProcess {

    @Getter
    private final List<Process> processes = new ArrayList<>();

    @Getter
    @Setter
    private ProcessConfiguration configuration;

    private File stdoutFile;
    private File stderrFile;

    private final JNAService jnaService;

    public GroupProcess(ProcessConfiguration configuration, JNAService jnaService) throws IOException {
        this.configuration = configuration;
        this.jnaService = jnaService;
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
        if (this.processes.stream().noneMatch(process -> process.getEtat().equals(ProcessEtat.RUN))) {
            this.processes.clear();
            for (int i = 0; i < this.configuration.getNbInstance(); i++) {
                this.processes.add(new Process(this.configuration, this.jnaService));
            }
            this.processes.forEach(process -> {
                if (process.getEtat().equals(ProcessEtat.STOP))
                    process.start();
            });
        }
    }

    public void stop() {
        this.processes.forEach(process -> {
            if (process.getEtat().equals(ProcessEtat.RUN))
                process.stop();
        });
    }

    public String getStdoutLogs() {
        Scanner scanner;
        try {
            scanner = new Scanner(this.stdoutFile);
            StringBuilder stringBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stringBuilder.append(line).append("\n");
            }
            scanner.close();
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            return "";
        }
    }

    public String getStderrLogs() {
        Scanner scanner;
        try {
            scanner = new Scanner(this.stderrFile);
            StringBuilder stringBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stringBuilder.append(line).append("\n");
            }
            scanner.close();
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            return "";
        }
    }

    public void changeNbInstance(int nbInstance, JNAService jnaService) {
        while (this.processes.size() > nbInstance) {
            this.processes.remove(this.processes.size() - 1).stop();
        }
        while (this.processes.size() < nbInstance) {
            Process p = new Process(this.configuration, jnaService);
            this.processes.add(p);
            if (this.configuration.isStartAtLaunch())
                p.start();
        }

    }

    public void changeUmask(String umask) {
        this.processes.forEach(process -> process.setUmask(umask));
    }

    public void restartOutputThread() {
        this.processes.forEach(process -> process.getMainRunnable().restartOutputThread());
    }

    public void restartErrThread() {
        this.processes.forEach(process -> process.getMainRunnable().restartErrThread());
    }
}
