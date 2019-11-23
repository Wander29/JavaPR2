import java.util.*;
import java.util.Iterator;

public class Board<E extends Data> implements DataBoard<E> {
/* OVERVIEW 	Collezione di oggetti generici che estendono il tipo di dato Data
 * 				Basata sull'interfaccia DataBoard<E extends Data>
 * 				È una struttura mutable, unbounded
 * 
 * 	AF() : 	{ psw, CATEGORIES, FRIENDS tc
 * 						categories.get(cat_i)	-> 	{d_i in DATA, #likes{d_i}, { friend0, f1, ..., f_z }
 * 						friends(f_i)	-> 	{ cat_p, ... , cat_z }
 * 				}
 * 
 * IR()  : 		psw != null
 * 				&& 	categories != null
 * 				&&	friends != null
 * 				&&	for all c IN cateogreis.keySet() ==> c != null && (
 * 						for each remaining j in categories.keySet() ==> c != j )
 * 				&& 	for all v IN categories.values() ==> v != null && v.likes >= 0  && (
 * 						for each remaining j in categories.values() ==> v.data != j.data )
 * 						&& for all f IN v.friendsWhoLiked ==> f!= null && (
 * 							for each remaining z in v.friendsWhoLiked ==> f != z )
 * 				&& 	for all f IN friends. f != null && (
 * 						for each remaining j in friends ==> f != j )
 * 				&&	for all c IN friends.values() ==> c != null && (
 * 						for each remaining j in friends.values() ==> c != j )
 * 
 */
	
	private class InternalData{
	// STRUCT di appoggio, collega ad un dato la lista di amici che mettono un like e il numero dei likes
		E data;
		ArrayList<String> friendsWhoLiked;
		int likes;
		
		InternalData (E data) {
			this.data = data;
			friendsWhoLiked = new ArrayList<>();
			likes = 0;
		}
		
		// aggiunge un like al contatore ed inserisce f nella lista degli amici che hanno messo like
		// tutti i controlli sono da considerare già effettuati dal chiamante in questo caso
		void addLike(String f) { 
		// @REQUIRES 	f != null && NOT (f IN friendsWhoLiked )
			friendsWhoLiked.add(f);
			likes++;
		}
	}
	
	private byte[] psw;
	
	private HashMap<String, ArrayList<InternalData<E>>> categories;
	private HashMap<String, ArrayList<String>> friends;
	
	public Board(String nome, String psw_plain) {
		psw = MyPasswordCrypt.cryptPsw(psw_plain);
		
		categories = new HashMap<>();
		friends = new HashMap<>();
	}
	
	// Crea una categoria di dati se vengono rispettati i controlli di identità
	public void createCategory(String category, String psw_plain) throws NullPointerException, WrongPasswordException, IllegalArgumentException {
		
		if(category == null || psw_plain == null) throw new NullPointerException();
		
		if(!login(psw_plain)) throw new WrongPasswordException();
		
		if(categories.containsKey(category)) throw new IllegalArgumentException();
		
		categories.put(category, new ArrayList<>());
	}
	
	// Rimuove una categoria di dati
	// se vengono rispettati i controlli di identità
	public void removeCategory(String category, String 	psw_plain) {
		if(!login(psw_plain)) throw new WrongPasswordException();
		
		if(category == null) throw new NullPointerException();
		
		if(categories.containsKey(category)) 
			categories.remove(category);
	}
	
	public void addFriend(String category, String psw_plain, String friend) throws WrongPasswordException, NullPointerException {
		if (!login(psw_plain)) throw new WrongPasswordException();
		
		if(category == null) throw new NullPointerException();
		
		if(friend == null) throw new NullPointerException();
		
		if (! friends.containsKey(friend))
			friends.put(friend, new ArrayList<>());
		
		friends.get(friend).add(category);	
	}
	

	public void removeFriend(String category, String psw_plain, String friend) {
		if (!login(psw_plain)) throw new WrongPasswordException();
		
		if(category == null) throw new NullPointerException();
		
		if(friend == null) throw new NullPointerException();
		
		if(friends.containsKey(friend))
			friends.get(friend).remove(category);
	}
	
	public boolean put(String psw_plain, E data, String category) {
		if(!login(psw_plain)) throw new WrongPasswordException();
		
		if(category == null) throw new NullPointerException();
		
		if(data == null) throw new NullPointerException();
		
		if(categories.get(category) == null)
			categories.put(category, new ArrayList<>());
		
		// IF data esiste già -> FALSE
			
		categories.get(category).add(new InternalData<E>(data));
		
		return true;
	}
	
	public E get(String psw_plain, E data) {
		if(!login(psw_plain)) throw new WrongPasswordException();
		
		if(data == null) throw new NullPointerException();
		
		ArrayList<InternalData<E>> tmp = categories.get(data.getCategory());
		for (int i=0; i<categories.size(); i++) {
			if (tmp.get(i).data.equals(data)) {
				return tmp.get(i).data;
			}
		}	
		return null;
	}
	
	public E remove(String psw_plain, E data) {
		if(!login(psw_plain)) throw new WrongPasswordException();
				
		if(data == null) throw new NullPointerException();
		
		ArrayList<InternalData<E>> tmp = categories.get(data.getCategory());
		for (int i=0; i<categories.size(); i++) {
			if (tmp.get(i).data.equals(data))
				return tmp.remove(i).data;
		}	
		return null;
		
	}
	
	
	public List<E> getDataCategory(String psw_plain, String category) {
		if(!login(psw_plain)) throw new WrongPasswordException();
		
		if(category == null) throw new NullPointerException();
		
		ArrayList<E> a = new ArrayList<>();
		for(InternalData<E> tmp : categories.get(category)) {
			a.add(tmp.data);
		}
		
		return Collections.unmodifiableList(a);
		
	}
	
	public void insertLike(String friend, E data) {
		if(friend == null) throw new NullPointerException();
		
		if(data == null) throw new NullPointerException();
		
		if(! friends.get(friend).contains(data.getCategory())) 
			throw new HiddenCategoryException(friend + "non ha accesso alla categoria di" + data.getDataName());
		
		ArrayList<InternalData<E>> tmp = categories.get(data.getCategory());
		for (int i=0; i<categories.size(); i++) {
			if (tmp.get(i).data.equals(data)) {
				if(! tmp.get(i).friendsWhoLiked.contains(friend))
					tmp.get(i).addLike(friend);
			}
		}
	}
	
	public Iterator<E> getIterator(String passw) {
		return new LikeSortedDataIterator();
	}
	
	private ArrayList<InternalData<E>> genAllData() {
		
		ArrayList<InternalData<E>> v = new ArrayList<InternalData<E>>();
		
		for(ArrayList<InternalData<E>> tmp : categories.values()) {
			v.addAll(tmp);
		}
		return v;
		
	}
	
	private class MyComparator implements Comparator<InternalData<E>> {

		@Override
		public int compare(InternalData<E> arg0, InternalData<E> arg1) {
			return arg0.likes - arg1.likes;
		}
		
	}
	
	private class LikeSortedDataIterator implements Iterator<E> {

		/*	@REQUIRES 		La struttura non deve essere modificata durante l'iterazione
		 * 
		 * 
		 */
		int ind;
		ArrayList<InternalData<E>> a;
		
		public LikeSortedDataIterator() {
			ind = 0;
			a = genAllData();
			Collections.sort(a, new MyComparator());
		}
		
		@Override	
		public boolean hasNext() {
			if (ind >= a.size() || a.get(ind+1) == null)
				return false;
			
			return true;
		}

		@Override
		public E next() {
			if (!hasNext())
				return null;
			return a.get(ind++).data; 
		}
		
		public void remove() { 
			throw new UnsupportedOperationException();
		}
		
	}
	
	public Iterator<E> getFriendIterator(String friend) {
		return new FriendIterator(friend);
	}
	
	private class FriendIterator implements Iterator<E> {
		
		int ind;
		ArrayList<InternalData<E>> a;
		
		public FriendIterator(String f) {
			ArrayList<String> cat_of_f = friends.get(f);
			a = new ArrayList<>();
			
			for (String s : cat_of_f) {
				a.addAll(categories.get(s));
			}
		}

		@Override
		public boolean hasNext() {
			if (ind >= a.size() || a.get(ind+1) == null)
				return false;
			return true;
		}

		@Override
		public E next() {
			if (!hasNext())
				return null;
			return a.get(ind++).data;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
		
	private boolean login(String psw_plain) {
		return MyPasswordCrypt.cmpPasswords(psw, MyPasswordCrypt.cryptPsw(psw_plain));
	}
	
}
