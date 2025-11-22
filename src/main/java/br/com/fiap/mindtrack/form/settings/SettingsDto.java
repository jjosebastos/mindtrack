package br.com.fiap.mindtrack.form.settings;


import lombok.Data;

@Data
public class SettingsDto {
    private String nome;
    private String sobrenome;
    private String lang;

    private String newEmail;

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private String authProvider;

}
