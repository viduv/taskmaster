package fr.fpage.taskmaster;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.fpage.taskmaster.application.services.ProcessService;
import fr.fpage.taskmaster.model.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TaskmasterApplicationRunner implements ApplicationRunner {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ProcessService processService;

    public TaskmasterApplicationRunner(ProcessService processService) {
        this.processService = processService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.getSourceArgs().length == 1) {
            File f = new File(args.getSourceArgs()[0]);
            Configuration conf = objectMapper.readValue(f, Configuration.class);
            processService.loadProcess(conf);
        } else
            System.out.println("Un yaml gros !");
    }
}
