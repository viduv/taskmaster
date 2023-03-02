package fr.fpage.taskmaster.application.in.web;

import fr.fpage.backend.openapi.api.TaskmasterApi;
import fr.fpage.backend.openapi.api.TaskmasterApiDelegate;
import fr.fpage.backend.openapi.model.Process;
import fr.fpage.taskmaster.application.services.ProcessService;
import fr.fpage.taskmaster.mapper.ProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebDelegate implements TaskmasterApiDelegate {

    @Autowired
    private ProcessService processService;

    /**
     * GET /listProcess : Renvois la liste des process
     *
     * @return Tableau des process (status code 200)
     * @see TaskmasterApi#listProcess
     */
    @Override
    public ResponseEntity<List<Process>> listProcess() {
        return ResponseEntity.ok(this.processService.listProcess().stream().map(ProcessMapper.INSTANCE::domainToApi).toList());
    }

    /**
     * POST /restart : Redemarre un process
     *
     * @param id (required)
     * @return OK (status code 200)
     * or Le process n&#39;est pas demarré (status code 400)
     * @see TaskmasterApi#restart
     */
    @Override
    public ResponseEntity<Void> restart(Integer id) {
        return TaskmasterApiDelegate.super.restart(id);
    }

    /**
     * POST /start : Demarre un process
     *
     * @param id (required)
     * @return OK (status code 200)
     * or Le process est deja démarré (status code 400)
     * @see TaskmasterApi#start
     */
    @Override
    public ResponseEntity<Void> start(Integer id) {
        return TaskmasterApiDelegate.super.start(id);
    }

    /**
     * POST /stop : Arette un process
     *
     * @param id (required)
     * @return OK (status code 200)
     * or Le process n&#39;est pas demarré (status code 400)
     * @see TaskmasterApi#stop
     */
    @Override
    public ResponseEntity<Void> stop(Integer id) {
        return TaskmasterApiDelegate.super.stop(id);
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
        return TaskmasterApiDelegate.super.updateConfig(body);
    }
}
