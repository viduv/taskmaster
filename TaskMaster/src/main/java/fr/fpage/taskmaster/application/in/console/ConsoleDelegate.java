package fr.fpage.taskmaster.application.in.console;

import fr.fpage.backend.openapi.model.GroupProcess;
import fr.fpage.backend.openapi.model.GroupProcessDetails;
import fr.fpage.taskmaster.application.services.ProcessService;
import fr.fpage.taskmaster.mapper.ProcessMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConsoleDelegate {

    private final ProcessService processService;

    ConsoleDelegate(ProcessService processService) {
        this.processService = processService;
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
}
