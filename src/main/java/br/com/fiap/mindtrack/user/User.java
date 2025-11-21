package br.com.fiap.mindtrack.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_mt_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    public Long idUser;
    @Column(name = "id_username")
    public String username;
    @Column(name = "nm_first")
    public String firstName;
    @Column(name = "nm_last")
    public String lastName;
    @Column(name = "ad_email")
    public String email;
    @Column(name = "pw_security")
    public String password;
    @Column(name = "ts_creation")
    public LocalDateTime dateCreation;
    @Column(name = "ts_update")
    public LocalDateTime dateUpdate;
    @Column(name = "us_role")
    @Enumerated(EnumType.STRING)
    public UserRole userRole;
    @Column(name = "au_provider")
    public String authProvider;
}
