package cl.dsy1103.ms_adopciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "adopciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitante_id", nullable = false)
    private Long solicitanteId;

    @Column(name = "mascota_id", nullable = false)
    private Long mascotaId;

    @Column(name = "refugio_id", nullable = false)
    private Long refugioId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAdopcion estado;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "motivo_rechazo", columnDefinition = "TEXT")
    private String motivoRechazo;

    @PrePersist
    protected void onCreate() {
        fechaSolicitud = LocalDateTime.now();
    }
}