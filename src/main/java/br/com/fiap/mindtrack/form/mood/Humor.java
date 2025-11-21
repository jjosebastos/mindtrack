package br.com.fiap.mindtrack.form.mood;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_mt_humor")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Humor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idHumor;
    @Enumerated(EnumType.STRING)
    private HumorType humorType;
    private String comentario;
    private LocalDateTime dataHoraRegistro;
}
