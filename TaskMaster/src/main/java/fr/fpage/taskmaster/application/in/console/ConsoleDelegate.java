package fr.fpage.taskmaster.application.in.console;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.fpage.backend.openapi.model.GroupProcess;
import fr.fpage.backend.openapi.model.GroupProcessDetails;
import fr.fpage.taskmaster.application.services.ProcessService;
import fr.fpage.taskmaster.mapper.ProcessMapper;
import fr.fpage.taskmaster.model.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ConsoleDelegate {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ProcessService processService;

    ConsoleDelegate(ProcessService processService) {
        this.processService = processService;
    }

    public void loadConfig(String configurationFile) throws IOException {
        File f = new File(configurationFile);
        Configuration conf = objectMapper.readValue(f, Configuration.class);
        processService.loadProcess(conf);
    }
    public List<GroupProcess> listGroupProcess() {
        return this.processService.listProcess().stream().map(ProcessMapper.INSTANCE::domainToApi).toList();
    }

    public boolean stop(String name) {
        try {
            this.processService.stopProcess(name);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public boolean start(String name) {
        try {
            this.processService.startProcess(name);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public boolean restart(String name) {
        try {
            this.processService.stopProcess(name);
            this.processService.startProcess(name);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public GroupProcessDetails groupProcess(String name) {
        try {
            return ProcessMapper.INSTANCE.domainToApiDetail(this.processService.getProcessGroup(name));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void stopAll() {
        this.processService.stopAll();
    }
}
