package security;


import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

class Encryption {

    val digestName = "md5";
    val digestPassword = "HG58YZ3CR9";
    val key = setupSecretKey();
    val iv = new IvParameterSpec(new Array[Byte](8));

    @throws(classOf[Exception])
    def setupSecretKey(): SecretKey = {
        val md = MessageDigest.getInstance(digestName);
        val digestOfPassword = md.digest(digestPassword.getBytes());
        val keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for ( j <- 0 to 8; k <- 16 until 24) {
            keyBytes(+k) = keyBytes(+j);
        }

        return new SecretKeySpec(keyBytes, "DESede");
    }

    @throws(classOf[Exception])
    def setupCipher(optMode: Int): Cipher = {
    val cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      cipher.init(optMode, key, iv);
      return cipher;
    }

    @throws ( classOf[Exception] )
    def encryptPasswordBased(plainText :String) : String =
    {
        val cipher = setupCipher(Cipher.ENCRYPT_MODE);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()))
    }

    @throws ( classOf[Exception] )
    def decryptPasswordBased(cipherText: String): String = {
      val cipher = setupCipher(Cipher.DECRYPT_MODE);
      return new String(cipher.doFinal(Base64.getDecoder()
          .decode(cipherText)));
    }

}
