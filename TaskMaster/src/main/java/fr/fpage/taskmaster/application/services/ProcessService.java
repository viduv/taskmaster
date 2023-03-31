package fr.fpage.taskmaster.application.services;

import fr.fpage.taskmaster.domain.GroupProcess;
import fr.fpage.taskmaster.model.Configuration;
import fr.fpage.taskmaster.model.ProcessConfiguration;
import fr.fpage.taskmaster.util.MapUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class ProcessService {

    private final JNAService jnaService;
    private final HashMap<String, GroupProcess> processMap = new HashMap<>();

    public ProcessService(JNAService jnaService) {
        this.jnaService = jnaService;
    }

    public List<GroupProcess> listProcess() {
        return this.processMap.values().stream().toList();
    }

    public void loadProcess(Configuration configuration) {
        configuration.getProcessConfiguration().forEach(configuration1 -> {
            if (this.processMap.containsKey(configuration1.getName()))
                this.editProcess(configuration1.getName(), configuration1);
            else
                this.createProcess(configuration1);
        });
/*        configuration.getProcessConfiguration().stream().map(conf -> {
            try {
                return new GroupProcess(conf, this.jnaService);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList().forEach(process -> {
            this.processMap.put(process.getConfiguration().getName(), process);
            if (process.getConfiguration().isStartAtLaunch()) {
                process.start();
            }
        });*/
    }

    public void stopProcess(String programName) throws NullPointerException {
        this.processMap.get(programName).stop();
    }

    public void startProcess(String programName) throws NullPointerException {
        this.processMap.get(programName).start();
    }

    public GroupProcess getProcessGroup(String name) throws NullPointerException {
        return this.processMap.get(name);
    }

    public String getStdoutLogs(String name) throws NullPointerException {
        return this.processMap.get(name).getStdoutLogs();
    }

    public String getStderrLogs(String name) throws NullPointerException {
        return this.processMap.get(name).getStderrLogs();
    }

    public void deleteProcess(String programName) {
        this.processMap.remove(programName).stop();
    }

    public void createProcess(ProcessConfiguration groupProcess) {
        try {
            this.processMap.put(groupProcess.getName(), new GroupProcess(groupProcess, this.jnaService));
            if (groupProcess.isStartAtLaunch())
                this.processMap.get(groupProcess.getName()).start();
        } catch (IOException e) {
        }
    }

    public void editProcess(String name, ProcessConfiguration groupProcess) throws NullPointerException {
        GroupProcess oldGp = this.getProcessGroup(name);
        ProcessConfiguration oldConfig = oldGp.getConfiguration();
        oldGp.setConfiguration(groupProcess);
        System.out.println(groupProcess);
        if (!oldConfig.getCmd().equals(groupProcess.getCmd())
                || (oldConfig.getFolder() != null && !oldConfig.getFolder().equals(groupProcess.getFolder()))
                || !MapUtils.deepCompareHashMap(oldConfig.getEnv(), groupProcess.getEnv())) {
            System.out.println("recreate process");
            this.deleteProcess(name);
            this.createProcess(groupProcess);
        }
        if (!oldConfig.getName().equals(groupProcess.getName())) {
            oldConfig.setName(groupProcess.getName());
            this.processMap.remove(name);
            this.processMap.put(groupProcess.getName(), oldGp);
        }
        if (oldConfig.getUmask() != null && !oldConfig.getUmask().equals(groupProcess.getUmask()))
            oldGp.changeUmask(groupProcess.getUmask());
        if (oldConfig.getStdoutFile() != null && !oldConfig.getStdoutFile().equals(groupProcess.getStdoutFile()))
            oldGp.restartOutputThread();
        if (oldConfig.getStderrFile() != null && !oldConfig.getStderrFile().equals(groupProcess.getStderrFile()))
            oldGp.restartErrThread();
        if (oldConfig.getNbInstance() != groupProcess.getNbInstance()) {
            oldGp.changeNbInstance(groupProcess.getNbInstance(), this.jnaService);
        }
    }

    public void stopAll() {
        this.processMap.values().forEach(groupProcess -> this.stopProcess(groupProcess.getConfiguration().getName()));
    }
}
