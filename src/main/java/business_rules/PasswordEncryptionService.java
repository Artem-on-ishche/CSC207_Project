package business_rules;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncryptionService implements PasswordEncryption{
    private final Cipher cipher;
    private final Key key;

    public PasswordEncryptionService() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("AES");
        this.key = generateKey();
    }

    private Key generateKey() throws NoSuchAlgorithmException {
        //MODIFICATIONS!!!!
        byte[] keyBytes = "ThisIsASecretKey".getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public String encryptMessage(String message) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new String(cipher.doFinal(message.getBytes()), StandardCharsets.UTF_8);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

}
