import java.util.HashMap;

public class MyNetwork<E extends Data> {
	HashMap<String, Board<E>> users;
	
	public MyNetwork() {
		users = new HashMap<String, Board<E>>();
	}
	
	public boolean addUser(String nome, String psw_plain) throws NullPointerException{
		if (nome == null) throw new NullPointerException();
		
		users.put(nome, new Board<E>(nome, psw_plain));
		return true;
	}
	
	public Board<E> getBoard(String s) {
		return users.get(s);
	}
}
