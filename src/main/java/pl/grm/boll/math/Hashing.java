package pl.grm.boll.math;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
	public Hashing() {

	}

	public static String hash(String pass, String method)
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
				byte[] hash = digest.digest(pass.getBytes("UTF-8"));
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
