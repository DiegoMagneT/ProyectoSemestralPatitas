package cl.dsy1103.ms_mascotas.DTO;

import cl.dsy1103.ms_mascotas.model.Especie;
import cl.dsy1103.ms_mascotas.model.EstadoMascota;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaRequestDTO {

    @NotBlank(message = "El nombre del animal es obligatorio")
    private String nombre;

    @NotNull(message = "La especie es obligatoria (PERRO, GATO, OTRO)")
    private Especie especie;

    private String raza;

    @NotNull(message = "La edad aproximada es obligatoria")
    @Min(value = 0, message = "La edad en meses no puede ser negativa")
    private Integer edadMeses;

    private String descripcion;

    @NotNull(message = "El estado inicial de la mascota es obligatorio")
    private EstadoMascota estado;

    @NotNull(message = "El ID del refugio asociado es obligatorio")
    private Long refugioId;
}