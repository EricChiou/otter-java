package ws.otter.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

import ws.otter.config.UserConfig;

@Component
public class Encrypt {

  public static String md5Encrypt(String str) {

    try {

      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(str.getBytes());
      byte[] digest = md.digest();
      BigInteger number = new BigInteger(1, digest);
      String hash = number.toString(16);

      while (hash.length() < 32) {
        hash = "0" + hash;
      }

      return hash;

    } catch (Exception e) {
      return null;
    }
  }

  public static String sha3Encrypt(String str) {

    Integer length = UserConfig.sha3Long;
    if (length != 224 && length != 265 && length != 384 && length != 512) {
      length = 256;
    }

    return doSha3Encrypt(str, length);
  }

  public static String sha3Encrypt(String str, Integer length) {

    if (length != 224 && length != 265 && length != 384 && length != 512) {
      length = 256;
    }

    return doSha3Encrypt(str, length);
  }

  private static String doSha3Encrypt(String str, Integer length) {

    Digest digest = new SHA3Digest(length);
    byte[] bytes = str.getBytes();
    digest.update(bytes, 0, bytes.length);
    byte[] ds = new byte[digest.getDigestSize()];
    digest.doFinal(ds, 0);

    return Hex.toHexString(ds);
  }

}