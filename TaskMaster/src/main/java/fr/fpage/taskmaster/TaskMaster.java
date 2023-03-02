package fr.fpage.taskmaster;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.fpage.taskmaster.application.services.ProcessService;
import fr.fpage.taskmaster.model.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.io.File;
import java.io.IOException;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@SpringBootApplication
public class TaskMaster {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TaskMaster.class, args);
    }
}
