package cl.dsy1103.ms_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // Anotación fundamental para activar el ciclo de vida de Eureka Server
public class MsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEurekaApplication.class, args);
	}
}