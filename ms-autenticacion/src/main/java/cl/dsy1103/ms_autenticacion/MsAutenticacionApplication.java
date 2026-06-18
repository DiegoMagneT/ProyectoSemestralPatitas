package cl.dsy1103.ms_autenticacion;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API MóduloEspecífico Patitas",
				version = "1.0",
				description = "Definición técnica de los endpoints de este microservicio"
		)
)
public class MsAutenticacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAutenticacionApplication.class, args);
	}

}
