package br.com.fiap.mindtrack.telefone;

import br.com.fiap.mindtrack.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "t_mt_telefone")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefone")
    private Long idTelefone;
    @Column(name = "nr_telefone")
    private String numero;
    @Column(name = "tp_telefone")
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;
}
