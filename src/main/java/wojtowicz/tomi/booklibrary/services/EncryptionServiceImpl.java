package wojtowicz.tomi.booklibrary.services;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tommy on 7/9/2017.
 */
public class EncryptionServiceImpl implements EncryptionService{

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
