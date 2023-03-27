package fr.fpage.taskmaster.application.services;

import fr.fpage.backend.openapi.model.GroupProcessDetails;
import fr.fpage.taskmaster.domain.GroupProcess;
import fr.fpage.taskmaster.model.Configuration;
import org.springframework.http.ResponseEntity;
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
        configuration.getProcessConfiguration().stream().map(conf -> {
            try {
                return new GroupProcess(conf, this.jnaService);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList().forEach(process -> {
            this.processMap.put(process.getConfiguration().getName(), process);
            process.start();
        });
    }

    public void stopProcess(String programName) {
        this.processMap.get(programName).stop();
    }

    public GroupProcess getProcessGroup(String name) {
        return this.processMap.get(name);
    }
}
