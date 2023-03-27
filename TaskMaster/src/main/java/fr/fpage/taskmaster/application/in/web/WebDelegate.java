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
     * POST /updateConfig : Met a jour le fichier de configuration
     *
     * @param body Json file (required)
     * @return ok (status code 200)
     * @see TaskmasterApi#updateConfig
     */
    @Override
    public ResponseEntity<Void> updateConfig(String body) {
        return TaskmasterApi.super.updateConfig(body);
    }

    /**
     * GET /groupProcess : Renvois un groupe de process
     *
     * @param name (required)
     * @return Groupe des process (status code 200)
     */
    @Override
    public ResponseEntity<GroupProcessDetails> groupProcess(String name) {
        return ResponseEntity.ok(ProcessMapper.INSTANCE.domainToApiDetail(this.processService.getProcessGroup(name)));
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
        return TaskmasterApi.super.processLog(name);
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
        return TaskmasterApi.super.restart(name);
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
        return TaskmasterApi.super.start(name);
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
        return TaskmasterApi.super.stop(name);
    }
}
