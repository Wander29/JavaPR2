import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class MyPasswordCrypt {
	// OVERVIEW 	Classe di supporto che gestisce il mascheramento delle password e il loro confronto
	public static byte[] cryptPsw(String s) throws NullPointerException {
		/* @REQUIRES 	s != null
		 * @EFFECTS 	esegue un hash SHA-512 sulla psw passata come input e la salva in bytes restituendola
		 * @THROWS 		if s == null
		 * 					throw NullPointerException
		 * 
		 * SITE: 	https://www.baeldung.com/java-password-hashing
		 */
		if (s == null) throw new NullPointerException();
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		byte[] c = null;
		MessageDigest md;
		
		random.nextBytes(salt);
		try {
			md = MessageDigest.getInstance("SHA-512");
			//md.update(salt);
			c = md.digest(s.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) { }
		
		return c;
	}
	
	public static boolean cmpPasswords(byte[] s, byte[]t) {
		/* @REQUIRES 	s != null && t != null
		 * @EFFECTS 	confronta byte per byte gli hash delle 2 password e restituisce true sse sono uguali, false altrimenti
		 * @THROWS 		if s == null || t == null
		 * 					throw NullPointerException
		 */
		if (s == null || t == null) throw new NullPointerException();
		
		for(int i=0; i<s.length; i++) {
			if (s[i] != t[i])
				return false;
		}
		return true;
	}

}
