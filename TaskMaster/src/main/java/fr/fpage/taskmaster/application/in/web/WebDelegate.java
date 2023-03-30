package fr.fpage.taskmaster.application.in.web;

import fr.fpage.backend.openapi.api.TaskmasterApi;
import fr.fpage.backend.openapi.model.GroupProcess;
import fr.fpage.backend.openapi.model.GroupProcessDetails;
import fr.fpage.taskmaster.application.services.ProcessService;
import fr.fpage.taskmaster.mapper.ProcessMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebDelegate implements TaskmasterApi {

    private final ProcessService processService;

    WebDelegate(ProcessService processService) {
        this.processService = processService;
    }

    /**
     * GET /listProcess : Renvois la liste des process
     *
     * @return Tableau des process (status code 200)
     * @see TaskmasterApi#listGroupProcess
     */
    @Override
    public ResponseEntity<List<GroupProcess>> listGroupProcess() {
        return ResponseEntity.ok(this.processService.listProcess().stream().map(ProcessMapper.INSTANCE::domainToApi).toList());
    }

    /**
     * GET /groupProcess : Renvois un groupe de process
     *
     * @param name (required)
     * @return Groupe des process (status code 200)
     */
    @Override
    public ResponseEntity<GroupProcessDetails> groupProcess(String name) {
        try {
            return ResponseEntity.ok(ProcessMapper.INSTANCE.domainToApiDetail(this.processService.getProcessGroup(name)));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /processLog : get les log d&#39;un process
     *
     * @param name (required)
     * @return OK (status code 200)
     * or le process n&#39;existe pas (status code 404)
     */
    @Override
    public ResponseEntity<String> processLog(String name) {
        try {
            return ResponseEntity.ok(this.processService.getStdoutLogs(name));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /processLogError : get les log d&#39;Error d&#39;un process
     *
     * @param name (required)
     * @return OK (status code 200)
     * or le log du process n&#39;existe pas (status code 404)
     */
    @Override
    public ResponseEntity<String> processLogError(String name) {
        try {
            return ResponseEntity.ok(this.processService.getStderrLogs(name));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /restart : Redemarre un process
     *
     * @param name (required)
     * @return OK (status code 200)
     * or Le process n&#39;est pas demarré (status code 400)
     */
    @Override
    public ResponseEntity<Void> restart(String name) {
        try {
            this.processService.stopProcess(name);
            this.processService.startProcess(name);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * POST /start : Demarre un process
     *
     * @param name (required)
     * @return OK (status code 200)
     * or Le process est deja démarré (status code 400)
     */
    @Override
    public ResponseEntity<Void> start(String name) {
        try {
            this.processService.startProcess(name);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * POST /stop : Arette un process
     *
     * @param name (required)
     * @return OK (status code 200)
     * or Le process n&#39;est pas demarré (status code 400)
     */
    @Override
    public ResponseEntity<Void> stop(String name) {
        try {
            this.processService.stopProcess(name);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * GET /deleteProcess : delete un process
     *
     * @param name (required)
     * @return OK (status code 200)
     * or le process n&#39;existe pas (status code 404)
     */
    @Override
    public ResponseEntity<Void> deleteProcess(String name) {
        try {
            this.processService.deleteProcess(name);
            return ResponseEntity.ok().build();
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PUT /createProcess : Ajoute un process
     *
     * @param groupProcess Nouveau process à ajouter (required)
     * @return OK (status code 200)
     */
    @Override
    public ResponseEntity<Void> createProcess(GroupProcess groupProcess) {
        this.processService.createProcess(ProcessMapper.INSTANCE.GroupProcessApiToGroupProcessConfiguration(groupProcess));
        return ResponseEntity.ok().build();
    }

    /**
     * PATCH /editProcess : Modifie un process
     *
     * @param name         (required)
     * @param groupProcess Nouvel etat du process (required)
     * @return OK (status code 200)
     * or le process n&#39;existe pas (status code 404)
     */
    @Override
    public ResponseEntity<Void> editProcess(String name, GroupProcess groupProcess) {
        return TaskmasterApi.super.editProcess(name, groupProcess);
    }
}
