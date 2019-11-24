import java.util.HashMap;

public class MyNetwork<E extends Data, T extends DataBoard<E>> {
	// OVERVIEW 	Classe di supporto che contiene tutte le varie bacheche degli utenti, 
	// 				può essere visto come un social netwrok dall'esterno
	// 				Mutable, unbounded
	
	HashMap<String, T> users;
	
	public MyNetwork() {
		users = new HashMap<String, T>();
	}
	
	public boolean addUser(String nome, T board) throws NullPointerException, IllegalArgumentException {
		/* @REQUIRES 		nome != null && board != null && ! users.containsKey(nome)
		 * @MODIFIES 	this
		 * @EFFECTS 	associa una bacheca ad un nome
		 * @THROWS 		if nome == null || board == null
		 * 					throws NullPointerException
		 * 				if users.containsKey(nome)
		 * 					throws IllegalArgumentException
		 */
		
		if (nome == null || board == null) throw new NullPointerException();
		
		if (users.containsKey(nome))
				throw new IllegalArgumentException("Utente già presente");
		
		users.put(nome, board);
		return true;
	}
	
	// restituisce la bacheca associata all'utente s
	public T getBoard(String s) throws NullPointerException, IllegalArgumentException {
		
		if(s == null) throw new NullPointerException();
		
		if(! users.containsKey(s)) throw new IllegalArgumentException(s + " NON presente");
		
		return users.get(s);
	}
}
