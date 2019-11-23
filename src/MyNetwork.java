import java.util.HashMap;

public class MyNetwork<E extends Data> {
	// OVERVIEW 	Classe di supporto che contiene tutte le varie bacheche degli utenti, 
	// 				può essere visto come un social netwrok dall'esterno
	// 				Mutable, unbounded
	
	HashMap<String, Board<E>> users;
	static final int MIN_LENGTH_PSW = 8;
	
	public MyNetwork() {
		users = new HashMap<String, Board<E>>();
	}
	
	public boolean addUser(String nome, String psw_plain) throws NullPointerException, IllegalArgumentException {
		/* @REQUIRES 		nome != null && psw_plain != null
		 * 				&&	psw.length() >= MIN_LENGTH_PSW
		 * @MODIFIES 	this
		 * @EFFECTS 	crea una nuova bacheca associata ad una coppia <utente, psw>
		 * @THROWS 		if nome == null || psw_plain == null
		 * 					throws NullPointerException
		 * 				if psw.length() < MIN_LENGTH_PSW
		 * 					throws IllegalArgumentException
		 */
		if (nome == null || psw_plain == null) throw new NullPointerException();
		if (psw_plain.length() < MIN_LENGTH_PSW) throw new IllegalArgumentException("La password NON rispetta i requisiti "
				+ "minimi di lunghezza: almeno " +  MIN_LENGTH_PSW + " caratteri");
		
		users.put(nome, new Board<E>(psw_plain));
		return true;
	}
	
	// restituisce la bacheca associata all'utente s
	public Board<E> getBoard(String s) throws NullPointerException, IllegalArgumentException {
		
		if(s == null) throw new NullPointerException();
		
		if(! users.containsKey(s)) throw new IllegalArgumentException(s + " NON presente");
		
		return users.get(s);
	}
}
