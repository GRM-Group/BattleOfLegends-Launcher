package pl.grm.boll.math;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
	public Hashing() {

	}

	public static String hash(String pass, String method) {
		switch (method) {
			case "MD5" :
				try {
					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] array = md.digest(pass.getBytes());
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < array.length; ++i) {
						sb.append(Integer
								.toHexString((array[i] & 0xFF) | 0x100)
								.substring(1, 3));
					}
					return sb.toString();
				} catch (NoSuchAlgorithmException e) {

				}
				break;
			default :
				return null;
		}
		return null;
	}
}
