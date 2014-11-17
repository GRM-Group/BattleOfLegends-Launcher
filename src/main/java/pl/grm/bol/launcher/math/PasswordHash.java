package pl.grm.bol.launcher.math;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHash {
	public static final String	PBKDF2_ALGORITHM	= "PBKDF2WithHmacSHA1";
	public static final int		HASH_BYTE_SIZE		= 32;
	public static final int		ITERATION_INDEX		= 1000;
	
	public static String hash(String pass, String saltT) {
		char[] chars = pass.toCharArray();
		byte[] salt = saltT.getBytes();
		
		PBEKeySpec spec = new PBEKeySpec(chars, salt, ITERATION_INDEX, HASH_BYTE_SIZE * 8);
		SecretKeyFactory skf;
		byte[] hash = null;
		try {
			skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
			hash = skf.generateSecret(spec).getEncoded();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return toHex(hash);
	}
	
	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) { return String.format("%0" + paddingLength + "d", 0) + hex; }
		return hex;
	}
}