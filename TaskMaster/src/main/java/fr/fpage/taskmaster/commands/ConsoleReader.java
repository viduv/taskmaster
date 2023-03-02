package fr.fpage.taskmaster.commands;

import fr.fpage.taskmaster.application.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReader implements CommandLineRunner {

    @Autowired
    private ProcessService processService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] command = input.split(" ");
            switch (command[0]) {
                case "stop" -> this.stop(command[1]);
            }
            System.out.println("Entrée utilisateur : " + input);
        }
    }

    private void stop(String programName) {
        this.processService.stopProcess(programName);
    }

}
