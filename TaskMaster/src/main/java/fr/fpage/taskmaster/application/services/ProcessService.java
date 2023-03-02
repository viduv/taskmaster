package fr.fpage.taskmaster.application.services;

import fr.fpage.taskmaster.domain.GroupProcess;
import fr.fpage.taskmaster.domain.Process;
import fr.fpage.taskmaster.model.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    @Autowired
    private JNAService jnaService;
    private final HashMap<String, GroupProcess> processMap = new HashMap<>();

    public List<GroupProcess> listProcess() {
        return this.processMap.values().stream().toList();
    }

    public void loadProcess(Configuration configuration) {
        configuration.getProcessConfiguration().stream().map(conf -> new GroupProcess(conf, this.jnaService)).toList().forEach(process -> {
            this.processMap.put(process.getConfiguration().getName(), process);
            process.start();
        });
    }

    public void stopProcess(String programName) {
        this.processMap.get(programName).stop();
    }
}
