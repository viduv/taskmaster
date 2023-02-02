/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.2.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package fr.fpage.backend.openapi.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-02-02T17:17:43.346542200+01:00[Europe/Paris]")
@Validated
@Tag(name = "Taskmaster", description = "the Taskmaster API")
public interface TaskmasterApi {

    default TaskmasterApiDelegate getDelegate() {
        return new TaskmasterApiDelegate() {};
    }

    /**
     * GET /listProcess : Renvois la liste des process
     *
     * @return Tableau des process (status code 200)
     */
    @Operation(
        operationId = "listProcess",
        summary = "Renvois la liste des process",
        tags = { "taskmaster" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Tableau des process", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/listProcess",
        produces = { "application/json" }
    )
    default ResponseEntity<List<Object>> listProcess(
        
    ) {
        return getDelegate().listProcess();
    }


    /**
     * POST /restart : Redemarre un process
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or Le process n&#39;est pas demarré (status code 400)
     */
    @Operation(
        operationId = "restart",
        summary = "Redemarre un process",
        tags = { "taskmaster" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Le process n'est pas demarré")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/restart"
    )
    default ResponseEntity<Void> restart(
        @NotNull @Parameter(name = "id", description = "", required = true) @Valid @RequestParam(value = "id", required = true) Integer id
    ) {
        return getDelegate().restart(id);
    }


    /**
     * POST /start : Demarre un process
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or Le process est deja démarré (status code 400)
     */
    @Operation(
        operationId = "start",
        summary = "Demarre un process",
        tags = { "taskmaster" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Le process est deja démarré")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/start"
    )
    default ResponseEntity<Void> start(
        @NotNull @Parameter(name = "id", description = "", required = true) @Valid @RequestParam(value = "id", required = true) Integer id
    ) {
        return getDelegate().start(id);
    }


    /**
     * POST /stop : Arette un process
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or Le process n&#39;est pas demarré (status code 400)
     */
    @Operation(
        operationId = "stop",
        summary = "Arette un process",
        tags = { "taskmaster" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Le process n'est pas demarré")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/stop"
    )
    default ResponseEntity<Void> stop(
        @NotNull @Parameter(name = "id", description = "", required = true) @Valid @RequestParam(value = "id", required = true) Integer id
    ) {
        return getDelegate().stop(id);
    }


    /**
     * POST /updateConfig : Met a jour le fichier de configuration
     *
     * @param body Json file (required)
     * @return ok (status code 200)
     */
    @Operation(
        operationId = "updateConfig",
        summary = "Met a jour le fichier de configuration",
        tags = { "taskmaster" },
        responses = {
            @ApiResponse(responseCode = "200", description = "ok")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/updateConfig",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> updateConfig(
        @Parameter(name = "body", description = "Json file", required = true) @Valid @RequestBody String body
    ) {
        return getDelegate().updateConfig(body);
    }

}
