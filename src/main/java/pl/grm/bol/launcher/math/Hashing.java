package pl.grm.bol.launcher.math;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
	public static String getHash(String password, String salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest((password + salt).getBytes("UTF-8"));
		return new String(hash);

		// digest.reset();
		// digest.update(salt);
		// return digest.digest(password.getBytes("UTF-8"));
	}

	public static String hash(String pass, String method, String... salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		switch (method) {
			case "MD5" :
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] array = md.digest(pass.getBytes());
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < array.length; ++i) {
					sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
							.substring(1, 3));
				}
				return sb.toString();
			case "SHA-256" :
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				digest.update(salt[0].getBytes());
				byte[] hash = digest.digest((pass).getBytes("UTF-8"));
				StringBuffer hexString = new StringBuffer();

				for (int i = 0; i < hash.length; i++) {
					String hex = Integer.toHexString(0xff & hash[i]);
					if (hex.length() == 1)
						hexString.append('0');
					hexString.append(hex);
				}
				return hexString.toString();
			default :
				return null;
		}
	}
}
