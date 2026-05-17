package cl.dsy1103.ms_refugios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refugios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Refugio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(name = "capacidad_max", nullable = false)
    private Integer capacidadMax;

    @Column(name = "capacidad_actual", nullable = false)
    private Integer capacidadActual = 0; // Inicia en 0

    @Column(name = "operador_id", nullable = false)
    private Long operadorId;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean activo = true;
}