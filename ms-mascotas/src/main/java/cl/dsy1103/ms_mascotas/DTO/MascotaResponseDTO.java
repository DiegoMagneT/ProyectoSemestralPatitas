package cl.dsy1103.ms_mascotas.DTO;

import cl.dsy1103.ms_mascotas.model.Especie;
import cl.dsy1103.ms_mascotas.model.EstadoMascota;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaResponseDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Integer edadMeses;
    private String descripcion;
    private String estado;
    private Long refugioId;
}