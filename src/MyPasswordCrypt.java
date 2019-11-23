import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class MyPasswordCrypt {
	
	
	public static byte[] cryptPsw(String s) 
	{
		/*
		 * 
		 * SITE: 	https://www.baeldung.com/java-password-hashing
		 */
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		byte[] c = null;
		MessageDigest md;
		
		random.nextBytes(salt);
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			c = md.digest(s.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) { }
		
		return c;
	}
	
	public static boolean cmpPasswords(byte[] s, byte[]t) {
		for(int i=0; i<s.length; i++) {
			if (s[i] != t[i])
				return false;
		}
		return true;
	}

}
