package cl.dsy1103.ms_pedidos.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class MarketplaceOrquestadorClient {

    private final WebClient webClientUsuarios;
    private final WebClient webClientPublicaciones;

    public MarketplaceOrquestadorClient(
            @Value("${usuario-service.url}") String urlUsuarios,
            @Value("${publicacion-service.url}") String urlPublicaciones) {

        webClientUsuarios = WebClient.builder().baseUrl(urlUsuarios).build();
        webClientPublicaciones = WebClient.builder().baseUrl(urlPublicaciones).build();
    }

    public Boolean verificarCompradorActivo(Long compradorId) {
        return webClientUsuarios.get()
                .uri("/{id}/activo", compradorId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("El comprador ingresado no existe en los registros principales")))
                .bodyToMono(Boolean.class)
                .block();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> obtenerDetallePublicacion(Long publicacionId) {
        return webClientPublicaciones.get()
                .uri("/{id}", publicacionId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("El artículo seleccionado no se encuentra registrado")))
                .bodyToMono(Map.class)
                .block();
    }

    public void marcarComoVendidoRemoto(Long publicacionId) {
        webClientPublicaciones.delete()
                .uri("/{id}", publicacionId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Error al dar de baja el artículo en el Marketplace")))
                .toBodilessEntity()
                .block();
    }
}
