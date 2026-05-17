package cl.dsy1103.ms_reportes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuario-service.url}") String usuarioServidor) {
        webClient = WebClient.builder().baseUrl(usuarioServidor).build();
    }

    public Boolean verificarUsuarioActivo(Long usuarioId) {
        return webClient.get()
                .uri("/{id}/activo", usuarioId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("El usuario que intenta reportar no existe en el sistema principal")))
                .bodyToMono(Boolean.class)
                .block();
    }
}