package fr.fpage.taskmaster.domain;

import fr.fpage.backend.openapi.model.ProcessEtat;
import fr.fpage.taskmaster.application.services.JNAService;
import fr.fpage.taskmaster.model.ProcessConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class GroupProcess {

    private static final int LOG_LINES = 500;

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

        this.stdoutFile = createLogFile(configuration.getStdoutFile());
        this.stderrFile = createLogFile(configuration.getStderrFile());
        for (int i = 0; i < this.configuration.getNbInstance(); i++) {
            this.processes.add(new Process(this.configuration, this.jnaService));
        }
    }

    private File createLogFile(String filePath) throws IOException {
        if (filePath != null) {
            File f = new File(filePath);
            f.createNewFile();
            return f;
        }
        return null;
    }

    public void start() {
        this.processes.stream().filter(process -> process.getEtat().equals(ProcessEtat.STOP)).toList().forEach(process -> {
            this.processes.remove(process);
            Process p = new Process(this.configuration, this.jnaService);
            this.processes.add(p);
            try {
                p.start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void stop() {
        this.processes.stream().filter(process -> process.getEtat().equals(ProcessEtat.RUN)).forEach(Process::stop);
    }

    public String getStdoutLogs() {
        try (RandomAccessFile reader = new RandomAccessFile(this.stdoutFile, "r")) {
            long length = this.stdoutFile.length();
            long pos = length - 1;
            int count = 0;
            StringBuilder sbLine = new StringBuilder();
            StringBuilder sbLogs = new StringBuilder();

            while (pos >= 0 && count < LOG_LINES) {
                reader.seek(pos);
                char c = (char) reader.readByte();

                if (c == '\n') {
                    sbLogs.append(sbLine).append('\n');
                    sbLine.setLength(0);
                    count++;
                } else {
                    sbLine.append(c);
                }
                pos--;
            }

            if (sbLine.length() > 0) {
                sbLogs.append(sbLine);
            }
            return sbLogs.reverse().toString();
        } catch (IOException ignored) {
        }
        return null;
    }

    public String getStderrLogs() {
        try (RandomAccessFile reader = new RandomAccessFile(this.stderrFile, "r")) {
            long length = this.stderrFile.length();
            long pos = length - 1;
            int count = 0;
            StringBuilder sbLine = new StringBuilder();
            StringBuilder sbLogs = new StringBuilder();

            while (pos >= 0 && count < LOG_LINES) {
                reader.seek(pos);
                char c = (char) reader.readByte();

                if (c == '\n') {
                    sbLogs.append(sbLine).append('\n');
                    sbLine.setLength(0);
                    count++;
                } else {
                    sbLine.append(c);
                }
                pos--;
            }

            if (sbLine.length() > 0) {
                sbLogs.append(sbLine);
            }
            return sbLogs.reverse().toString();
        } catch (IOException ignored) {
        }
        return null;
    }

    public void changeNbInstance(int nbInstance, JNAService jnaService) {
        while (this.processes.size() > nbInstance) {
            this.processes.remove(this.processes.size() - 1).stop();
        }
        while (this.processes.size() < nbInstance) {
            Process p = new Process(this.configuration, jnaService);
            this.processes.add(p);
            if (this.configuration.isStartAtLaunch()) {
                try {
                    p.start();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public void changeUmask(String umask) {
        this.processes.forEach(process -> process.setUmask(umask));
    }

    public void restartOutputThread() {
        try {
            this.stdoutFile = this.createLogFile(this.configuration.getStdoutFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.processes.forEach(process -> process.getMainRunnable().restartOutputThread());
    }

    public void restartErrThread() {
        try {
            this.stderrFile = this.createLogFile(this.configuration.getStderrFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }        this.processes.forEach(process -> process.getMainRunnable().restartErrThread());
    }
}
