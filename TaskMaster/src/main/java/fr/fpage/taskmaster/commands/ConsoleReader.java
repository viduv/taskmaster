package fr.fpage.taskmaster.commands;

import fr.fpage.taskmaster.application.in.console.ConsoleDelegate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

@Component
public class ConsoleReader implements CommandLineRunner {

    private final ConsoleDelegate consoleDelegate;

    private Logger logger;

    public ConsoleReader(ConsoleDelegate consoleDelegate) {
        this.consoleDelegate = consoleDelegate;
        this.logger = Logger.getLogger("Commands");
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] command = input.split(" ");
            switch (command[0]) {
                case "stop" -> this.stop(command);
                case "start" -> this.start(command);
                case "restart" -> this.restart(command);
                case "info" -> this.info(command);
                case "reload" -> this.reload(command);
                case "help" -> this.help();
                case "exit" -> this.exit();
            }
        }
    }

    private void exit() {
        this.consoleDelegate.stopAll();
        System.exit(0);
    }

    private void help() {
        this.logger.info("help");
        this.logger.info("info");
        this.logger.info("info [program]");
        this.logger.info("start [program]");
        this.logger.info("stop [program]");
        this.logger.info("restart [program]");
        this.logger.info("reload [File]");
        this.logger.info("exit");
    }

    private void reload(String[] command) {
        if (command.length == 2) {
            try {
                this.consoleDelegate.loadConfig(command[1]);
            } catch (IOException e) {
                this.logger.info("Le fichier n'existe pas");
            }
        } else
            this.logger.info("reload [file]");
    }

    private void info(String[] cmdArgs) {
        if (cmdArgs.length == 1) {
            this.consoleDelegate.listGroupProcess().forEach(groupProcess -> {
                this.logger.info(String.format("\nProcess name: %s%n Status %s%n", groupProcess.getName(), groupProcess.getEtat()));
                this.logger.info("\n" + groupProcess.toString());
            });
        } else {
            this.logger.info(this.consoleDelegate.groupProcess(cmdArgs[1]).toString());
        }
    }

    private void stop(String[] args) {
        if (args.length != 2)
            this.logger.info("stop [program]");
        else
            try {
                this.consoleDelegate.stop(args[1]);
            } catch (NullPointerException ignored) {
                this.logger.info("Le programe n'existe pas");
            }
    }

    private void start(String[] args) {
        if (args.length != 2)
            this.logger.info("start [program]");
        else
            try {
                this.consoleDelegate.start(args[1]);
            } catch (NullPointerException ignored) {
                this.logger.info("Le programe n'existe pas");
            }
    }

    private void restart(String[] args) {
        if (args.length != 2)
            this.logger.info("restart [program]");
        else
            try {
                this.consoleDelegate.restart(args[1]);
            } catch (NullPointerException ignored) {
                this.logger.info("Le programe n'existe pas");
            }
    }

}
