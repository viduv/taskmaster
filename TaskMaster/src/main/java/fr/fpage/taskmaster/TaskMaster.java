package fr.fpage.taskmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TaskMaster {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TaskMaster.class, args);
    }
}
