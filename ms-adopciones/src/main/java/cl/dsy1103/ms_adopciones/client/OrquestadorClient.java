package cl.dsy1103.ms_adopciones.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class OrquestadorClient {

    private final WebClient webClientUsuarios;
    private final WebClient webClientRefugios;
    private final WebClient webClientMascotas;

    public OrquestadorClient(
            @Value("${usuario-service.url}") String urlUsuarios,
            @Value("${refugio-service.url}") String urlRefugios,
            @Value("${mascota-service.url}") String urlMascotas) {

        webClientUsuarios = WebClient.builder().baseUrl(urlUsuarios).build();
        webClientRefugios = WebClient.builder().baseUrl(urlRefugios).build();
        webClientMascotas = WebClient.builder().baseUrl(urlMascotas).build();
    }

    public Boolean verificarUsuarioActivo(Long usuarioId) {
        return webClientUsuarios.get()
                .uri("/{id}/activo", usuarioId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("El adoptante ingresado no existe en el sistema principal")))
                .bodyToMono(Boolean.class)
                .block();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> obtenerFichaMascota(Long mascotaId) {
        return webClientMascotas.get()
                .uri("/{id}", mascotaId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("La mascota solicitada no se encuentra registrada")))
                .bodyToMono(Map.class)
                .block();
    }

    public Integer obtenerCapacidadDisponibleRefugio(Long refugioId) {
        return webClientRefugios.get()
                .uri("/{id}/capacidad", refugioId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("El refugio especificado no existe en el ecosistema operativo")))
                .bodyToMono(Integer.class)
                .block();
    }

    public void actualizarEstadoMascotaRemoto(Long mascotaId, String estadoNuevo) {
        webClientMascotas.put()
                .uri("/{id}/estado?estado={estado}", mascotaId, estadoNuevo)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Error crítico al sincronizar el nuevo estado de la mascota")))
                .toBodilessEntity()
                .block();
    }
}