
public class WrongPasswordException extends RuntimeException {
	public WrongPasswordException(String s) { super(s); }
	
	public WrongPasswordException() { super("Password Errata"); }
}
