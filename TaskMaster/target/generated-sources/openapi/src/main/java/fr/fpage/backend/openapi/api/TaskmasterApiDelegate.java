package fr.fpage.backend.openapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link TaskmasterApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-02-02T17:17:43.346542200+01:00[Europe/Paris]")
public interface TaskmasterApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /listProcess : Renvois la liste des process
     *
     * @return Tableau des process (status code 200)
     * @see TaskmasterApi#listProcess
     */
    default ResponseEntity<List<Object>> listProcess() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "\"\"";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /restart : Redemarre un process
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or Le process n&#39;est pas demarré (status code 400)
     * @see TaskmasterApi#restart
     */
    default ResponseEntity<Void> restart(Integer id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /start : Demarre un process
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or Le process est deja démarré (status code 400)
     * @see TaskmasterApi#start
     */
    default ResponseEntity<Void> start(Integer id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /stop : Arette un process
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or Le process n&#39;est pas demarré (status code 400)
     * @see TaskmasterApi#stop
     */
    default ResponseEntity<Void> stop(Integer id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /updateConfig : Met a jour le fichier de configuration
     *
     * @param body Json file (required)
     * @return ok (status code 200)
     * @see TaskmasterApi#updateConfig
     */
    default ResponseEntity<Void> updateConfig(String body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
