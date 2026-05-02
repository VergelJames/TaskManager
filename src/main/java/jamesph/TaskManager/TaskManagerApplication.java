package jamesph.TaskManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing
public class TaskManagerApplication {
	public static void main(String[] args) {
		log.info("Running TaskManagerApplication now..");
		SpringApplication.run(TaskManagerApplication.class, args);
		log.info("TaskManagerApplication is now running..");
	}

}
