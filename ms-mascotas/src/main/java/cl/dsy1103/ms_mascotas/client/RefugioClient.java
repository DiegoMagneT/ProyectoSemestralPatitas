package cl.dsy1103.ms_mascotas.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RefugioClient {

    private final WebClient webClient;

    public RefugioClient(@Value("${refugio-service.url}") String refugioServidor) {
        webClient = WebClient.builder().baseUrl(refugioServidor).build();
    }

    public Integer obtenerCapacidadDisponible(Long refugioId) {
        return webClient.get()
                .uri("/{id}/capacidad", refugioId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("El refugio seleccionado no existe o está inactivo")))
                .bodyToMono(Integer.class)
                .block();
    }
}