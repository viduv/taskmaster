package fr.fpage.taskmaster.domain;

import fr.fpage.backend.openapi.model.ProcessEtat;
import fr.fpage.taskmaster.application.services.JNAService;
import fr.fpage.taskmaster.model.ProcessConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class Process {
    @Getter
    private final JNAService jnaService;
    private final ProcessConfiguration configuration;

    @Setter
    private ProcessEtat etat = ProcessEtat.STOP;

    private ProcessRunnable mainRunnable;
    private Thread processThread;

    private boolean stop = false;

    public Process(ProcessConfiguration configuration, JNAService jnaService) {
        this.configuration = configuration;
        this.jnaService = jnaService;
    }

    public void start() throws InterruptedException {
        Logger.getGlobal().log(Level.INFO, "Start process thread " + this.configuration.getName());
        this.mainRunnable = new ProcessRunnable(this);
        this.processThread = new Thread(this.mainRunnable);
        this.processThread.start();

        while (this.mainRunnable.getJavaProcess() == null) {
            Thread.sleep(100);
        }
    }

    public void stop() {
        this.processThread.interrupt();
        this.mainRunnable.stopProcess();
    }

    public void setUmask(String umask) {
        this.mainRunnable.setUmask(umask);
    }
}
