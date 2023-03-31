package fr.fpage.taskmaster.domain;

import fr.fpage.backend.openapi.model.ProcessEtat;
import fr.fpage.taskmaster.model.RestartType;
import lombok.Getter;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessRunnable implements Runnable {

    private Process process;
    @Getter
    private java.lang.Process javaProcess;

    private Thread outputThread;
    private Thread errorThread;
    private boolean stop = false;

    public ProcessRunnable(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        stop = false;
        boolean processError;
        do {
            this.process.setEtat(ProcessEtat.RUN);
            processError = this.runProcess();
            this.process.setEtat(ProcessEtat.STOP);
        }
        while ((this.process.getConfiguration().getRestartType().equals(RestartType.ALWAYS) || this.process.getConfiguration().getRestartType().equals(RestartType.ON_FAILURE) && processError) && !this.stop);
    }

    private boolean runProcess() {
        Logger.getGlobal().log(Level.INFO, "Start process " + this.process.getConfiguration().getName());
        ProcessBuilder processBuilder = new ProcessBuilder(this.process.getConfiguration().getCmd().split(" "));
        processBuilder.environment().putAll(this.process.getConfiguration().getEnv());
        if (this.process.getConfiguration().getFolder() != null)
            processBuilder.directory(new File(this.process.getConfiguration().getFolder()));
        if (this.process.getConfiguration().getUmask() != null)
            this.setUmask(this.process.getConfiguration().getUmask());
        try {
            this.javaProcess = processBuilder.start();
        } catch (IOException ignored) {
        }
        this.startOutputThread();
        this.startErrThread();
        this.outputThread.start();
        this.errorThread.start();
        try {
            this.javaProcess.waitFor();
        } catch (InterruptedException ignored) {
        }
        try {
            Logger.getGlobal().log(Level.INFO, "Exit process value: " + this.javaProcess.exitValue());
            return this.javaProcess.exitValue() != this.process.getConfiguration().getExpectedExitCode();
        } catch (Exception ignored) {
        }
        return true;
    }

    public void stopProcess() {
        this.stop = true;
        this.outputThread.interrupt();
        this.errorThread.interrupt();
        this.process.getJnaService().kill(this.javaProcess, this.process.getConfiguration().getExitSignal());
        try {
            this.javaProcess.waitFor(this.process.getConfiguration().getGracefulStopTime(), TimeUnit.SECONDS);
            if (this.javaProcess.isAlive()) {
                Logger.getLogger(this.process.getConfiguration().getName()).info(String.format("Le programme {%s} ne s'est pas arrette dans le delais accordé. Forcage de l'arret.", this.process.getConfiguration().getName()));
                this.javaProcess.destroy();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.process.setEtat(ProcessEtat.STOP);
    }

    public void setUmask(String umask) {
        this.process.getJnaService().setUmask(umask);
    }

    public void restartOutputThread() {
        this.outputThread.interrupt();
        this.startOutputThread();
    }

    private void startOutputThread() {
        this.outputThread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.javaProcess.getInputStream()));
            try {
                OutputStream stdoutStream = null;
                if (this.process.getConfiguration().getStdoutFile() != null)
                    stdoutStream = new FileOutputStream(this.process.getConfiguration().getStdoutFile(), true);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (this.process.getConfiguration().getStdoutFile() != null)
                        stdoutStream.write((line + "\n").getBytes());
                    else
                        Logger.getLogger(this.process.getConfiguration().getName()).info(line);
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException ignored) {
            }
        });
    }

    public void restartErrThread() {
        this.errorThread.interrupt();
        this.startErrThread();
    }

    private void startErrThread() {
        this.errorThread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(javaProcess.getErrorStream()));
            try {
                OutputStream stderrStream = null;
                if (this.process.getConfiguration().getStderrFile() != null)
                    stderrStream = new FileOutputStream(this.process.getConfiguration().getStderrFile(), true);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (stderrStream != null)
                        stderrStream.write((line + "\n").getBytes());
                    else
                        Logger.getLogger(this.process.getConfiguration().getName()).warning(line);
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException ignored) {
            }
        });
    }
}
