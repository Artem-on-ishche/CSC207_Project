package entity;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncryptionService {
    private final Cipher cipher;
    private final Key key;

    public PasswordEncryptionService() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("AES");
        this.key = generateKey();
    }

    private Key generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(256, secureRandom);
        return keyGenerator.generateKey();
    }

    public String encryptMessage(String message) throws IllegalBlockSizeException, BadPaddingException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return new String(cipher.doFinal(message.getBytes()), StandardCharsets.UTF_8);
    }

    public String decryption(String message) throws IllegalBlockSizeException, BadPaddingException {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return new String(cipher.doFinal(message.getBytes()), StandardCharsets.UTF_8);
    }
}
