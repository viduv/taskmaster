package fr.fpage.taskmaster.domain;

import fr.fpage.backend.openapi.model.ProcessEtat;
import fr.fpage.taskmaster.application.services.JNAService;
import fr.fpage.taskmaster.model.ProcessConfiguration;
import fr.fpage.taskmaster.model.RestartType;
import lombok.Getter;

import java.io.*;
import java.util.concurrent.TimeUnit;
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

    private boolean stop = false;

    public Process(ProcessConfiguration configuration, JNAService jnaService) {
        this.configuration = configuration;
        this.jnaService = jnaService;
    }

    public void start() {
        Logger.getGlobal().log(Level.INFO, "Start process thread " + this.configuration.getName());
        this.stop = false;
        this.processThread = new Thread(() -> {
            boolean processError;
            do {
                System.out.println("set to RUN " + (this.getProcess() != null ? this.getProcess().pid() : "NO_PID"));
                this.etat = ProcessEtat.RUN;
                System.out.println("Start process " + (this.getProcess() != null ? this.getProcess().pid() : "NO_PID"));
                processError = this.runProcess();
                System.out.println("Set to STOP " + (this.getProcess() != null ? this.getProcess().pid() : "NO_PID"));
                this.etat = ProcessEtat.STOP;
            }
            while ((this.configuration.getRestartType().equals(RestartType.ALWAYS) || this.configuration.getRestartType().equals(RestartType.ON_FAILURE) && processError) && !this.stop);
        });
        this.processThread.start();

    }

    private boolean runProcess() {
        Logger.getGlobal().log(Level.INFO, "Start process " + this.configuration.getName());
        ProcessBuilder processBuilder = new ProcessBuilder(this.configuration.getCmd().split(" "));
        processBuilder.environment().putAll(this.configuration.getEnv());
        if (this.configuration.getFolder() != null)
            processBuilder.directory(new File(this.configuration.getFolder()));
        if (this.configuration.getUmask() != null)
            this.jnaService.setUmask(this.configuration.getUmask());
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
            } catch (IOException | InterruptedException ignored) {
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
                        stderrStream.write((line + "\n").getBytes());
                    else
                        Logger.getLogger(configuration.getName()).warning(line);
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException ignored) {
            }
        });
        this.outputThread.start();
        this.errorThread.start();
        try {
            this.process.waitFor();
        } catch (InterruptedException ignored) {
        }
        try {
            Logger.getGlobal().log(Level.INFO, "Exit process value: " + this.process.exitValue());
            return this.process.exitValue() != this.configuration.getExpectedExitCode();
        } catch (Exception ignored) {
        }
        return true;
    }

    public void stopProcess() {
        this.stop = true;
        this.outputThread.interrupt();
        this.errorThread.interrupt();
        this.processThread.interrupt();
        this.jnaService.kill(this.process, this.configuration.getExitSignal());
        try {
            this.process.waitFor(this.configuration.getGracefulStopTime(), TimeUnit.SECONDS);
            if (this.process.isAlive()) {
                Logger.getLogger(this.configuration.getName()).info(String.format("Le programme {%s} ne s'est pas arrette dans le delais accordé. Forcage de l'arret.", this.configuration.getName()));
                this.process.destroy();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.etat = ProcessEtat.STOP;
    }
}
