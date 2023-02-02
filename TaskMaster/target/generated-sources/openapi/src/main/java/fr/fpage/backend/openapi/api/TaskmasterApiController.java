package fr.fpage.backend.openapi.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-02-02T17:17:43.346542200+01:00[Europe/Paris]")
@Controller
@RequestMapping("${openapi.taskMaster.base-path:/api}")
public class TaskmasterApiController implements TaskmasterApi {

    private final TaskmasterApiDelegate delegate;

    public TaskmasterApiController(@Autowired(required = false) TaskmasterApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new TaskmasterApiDelegate() {});
    }

    @Override
    public TaskmasterApiDelegate getDelegate() {
        return delegate;
    }

}
