package fr.fpage.taskmaster.domain;

import fr.fpage.backend.openapi.model.ProcessEtat;
import fr.fpage.taskmaster.application.services.JNAService;
import fr.fpage.taskmaster.model.ProcessConfiguration;
import fr.fpage.taskmaster.model.RestartType;
import lombok.Getter;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class Process {
    private final JNAService jnaService;
    private final ProcessConfiguration configuration;

    private ProcessEtat etat = ProcessEtat.STOP;
    private Thread processThread;
    private Thread outputThread;
    private Thread errorThread;
    private java.lang.Process process;

    public Process(ProcessConfiguration configuration, JNAService jnaService) {
        this.configuration = configuration;
        this.jnaService = jnaService;
    }

    public void start() {
        Logger.getGlobal().log(Level.INFO, "Start process thread " + this.configuration.getName());

        this.processThread = new Thread(() -> {
            this.etat = ProcessEtat.RUN;
            boolean processError;
            do {
                processError = this.runProcess();
            }
            while (this.configuration.getRestartType().equals(RestartType.ALWAYS) || this.configuration.getRestartType().equals(RestartType.ON_FAILURE) && processError);
            this.etat = ProcessEtat.STOP;
        });
        this.processThread.start();

    }

    private boolean runProcess() {
        Logger.getGlobal().log(Level.INFO, "Start process " + this.configuration.getName());
        ProcessBuilder processBuilder = new ProcessBuilder(this.configuration.getCmd());
        try {
            this.process = processBuilder.start();
        } catch (IOException ignored) {
        }
        this.outputThread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                OutputStream stdoutStream = null;
                if (this.configuration.getStdoutFile() != null)
                    stdoutStream = new FileOutputStream(this.configuration.getStdoutFile(), true);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (configuration.getStdoutFile() != null)
                        stdoutStream.write((line + "\n").getBytes());
                    else
                        Logger.getLogger(configuration.getName()).info(line);
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        this.errorThread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            try {
                OutputStream stderrStream = null;
                if (configuration.getStderrFile() != null)
                    stderrStream = new FileOutputStream(this.configuration.getStderrFile(), true);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (stderrStream != null)
                        stderrStream.write(line.getBytes());
                    else
                        Logger.getLogger(configuration.getName()).warning(line);
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        this.outputThread.start();
        this.errorThread.start();
        try {
            this.process.waitFor();
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
        Logger.getGlobal().log(Level.INFO, "Exit process value: " + this.process.exitValue());
        return this.process.exitValue() != this.configuration.getExpectedExitCode();
    }

    public void stopProcess() {
        this.jnaService.kill(this.process, this.configuration.getExitSignal());
        try {
            this.process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.outputThread.interrupt();
        this.errorThread.interrupt();
        this.processThread.interrupt();
        this.etat = ProcessEtat.STOP;
    }
}
