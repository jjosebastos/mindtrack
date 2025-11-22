package br.com.fiap.mindtrack.form.mood;

import br.com.fiap.mindtrack.user.User;
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
    @Column(name = "id_humor")
    public Long idHumor;
    @Enumerated(EnumType.STRING)
    @Column(name = "st_humor")
    private HumorType humorType;
    @Column(name = "ds_comentario")
    private String comentario;
    @Column(name = "ts_registro")
    private LocalDateTime dataRegistro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

}
