package wojtowicz.tomi.booklibrary.services.security;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wojtowicz.tomi.booklibrary.services.security.EncryptionService;

/**
 * Created by tommy on 7/9/2017.
 */
@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Autowired
    private StrongPasswordEncryptor strongPasswordEncryptor;

    @Override
    public String encryptString(String input) {
        return strongPasswordEncryptor.encryptPassword(input);
    }

    @Override
    public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return strongPasswordEncryptor.checkPassword(plainPassword, encryptedPassword);
    }
}
