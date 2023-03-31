package fr.fpage.taskmaster;

import fr.fpage.taskmaster.application.in.console.ConsoleDelegate;
import fr.fpage.taskmaster.application.services.ProcessService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class TaskmasterApplicationRunner implements ApplicationRunner {
    private final ConsoleDelegate consoleDelegate;
    private final Logger logger;

    public TaskmasterApplicationRunner(ConsoleDelegate consoleDelegate) {
        this.consoleDelegate = consoleDelegate;
        this.logger = Logger.getLogger("Runner");
    }

    @Override
    public void run(ApplicationArguments args) {
        if (args.getSourceArgs().length == 1) {
            try {
                this.consoleDelegate.loadConfig(args.getSourceArgs()[0]);
            } catch (IOException e) {
                this.logger.info("Le fichier n'existe pas");
            }
        } else {
            this.logger.info("Ajoutez une configuration json");
        }
    }
}
