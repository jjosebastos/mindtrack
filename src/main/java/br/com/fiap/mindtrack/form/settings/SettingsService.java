package br.com.fiap.mindtrack.form.settings;

import br.com.fiap.mindtrack.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettingsService {

    private final UserRepository userRepository;

    public SettingsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveSettings(SettingsDto settingsDto){

    }

}
