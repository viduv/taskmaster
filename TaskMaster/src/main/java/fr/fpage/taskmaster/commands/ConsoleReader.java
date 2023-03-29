package fr.fpage.taskmaster.commands;

import fr.fpage.taskmaster.application.in.console.ConsoleDelegate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReader implements CommandLineRunner {

    private final ConsoleDelegate consoleDelegate;

    public ConsoleReader(ConsoleDelegate consoleDelegate) {
        this.consoleDelegate = consoleDelegate;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] command = input.split(" ");
            switch (command[0]) {
                case "stop" -> this.stop(command[1]);
                case "start" -> this.start(command[1]);
                case "info" -> this.info(command);
            }
            System.out.println("Entrée utilisateur : " + input);
        }
    }

    private void info(String[] cmdArgs) {
        if (cmdArgs.length == 1) {
            this.consoleDelegate.listGroupProcess().forEach(groupProcess -> {
                System.out.printf("Process name: %s%n Status %s%n", groupProcess.getName(), groupProcess.getEtat());
                System.out.println(groupProcess);
            });
        }
        else {
            System.out.println(this.consoleDelegate.groupProcess(cmdArgs[1]));
        }
    }

    private void stop(String programName) {
        this.consoleDelegate.stop(programName);
    }
    private void start(String programName) {
        this.consoleDelegate.start(programName);
    }

}
