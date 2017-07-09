package wojtowicz.tomi.booklibrary.services.security;

/**
 * Created by tommy on 7/9/2017.
 */
public interface EncryptionService {
    String encryptString(String input);

    boolean checkPassword(String plainPassword, String encryptedPassword);
}
