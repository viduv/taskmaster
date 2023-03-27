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
     * POST /restart : Redémarre un process
     *
     * @param id (required)
     * @return OK (status code 200)
     * or Le process n&#39;est pas démarré (status code 400)
     * @see TaskmasterApi#restart
     */
    @Override
    public ResponseEntity<Void> restart(Integer id) {
        return TaskmasterApi.super.restart(id);
    }

    /**
     * POST /start : Démarre un process
     *
     * @param id (required)
     * @return OK (status code 200)
     * or Le process est deja démarré (status code 400)
     * @see TaskmasterApi#start
     */
    @Override
    public ResponseEntity<Void> start(Integer id) {
        return TaskmasterApi.super.start(id);
    }

    /**
     * POST /stop : Stop un process
     *
     * @param id (required)
     * @return OK (status code 200)
     * or Le process n&#39;est pas démarré (status code 400)
     * @see TaskmasterApi#stop
     */
    @Override
    public ResponseEntity<Void> stop(Integer id) {
        return TaskmasterApi.super.stop(id);
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
}
