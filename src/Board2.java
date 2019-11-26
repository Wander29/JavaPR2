import java.util.*;

public class Board2<E extends Data> implements DataBoard<E> {
/* OVERVIEW 	Collezione di oggetti generici che estendono il tipo di dato Data
 * 				Basata sull'interfaccia DataBoard<E extends Data>
 * 				È una struttura mutable, unbounded
 * 
 * 	AF() : 		{ psw, CATEGORIES, FRIENDS tc
 * 						categories.get(cat_i)	-> 	{ d_i in DATA, #likes{d_i}, { friend0, f1, ..., f_z } }
 * 						friends(f_i)	-> 	{ cat_p, ... , cat_z }
 * 				}
 * 
 * IR()  : 		psw != null
 * 				&& 	categories != null
 * 				&&	friends != null
 * 				&&	for all c IN categories.keySet() ==> c != null 
 * 					{ &&  (for each remaining j in categories.keySet() ==> c != j ) } -> garantito da HashMap
 * 				&& 	{ for all a IN categories.values() ==> a != null } -> garantito da TreeSet
 * 					&& for all v IN a. 
 * 							v.likes >= 0  
 * 						{ && ( for each remaining j in a ==> v.data != j.data ) } -> garantito da TreeSet
 * 						{ && for all f IN v.friendsWhoLiked ==> f!= null } -> garantito da TreeSet
 * 							{ && (for each remaining z in v.friendsWhoLiked ==> f != z ) } -> garantito da TreeSet
 * 				&& 	for all f IN friends.keySet() ==> f != null 
 *  				{ && (for each remaining j in friends ==> f != j ) } -> garantito da HashMap
 * 				&&	{ for all c IN friends.values() ==> c != null && (
 * 						for each remaining j in friends.values() ==> c != j ) } -> garantite entrambe da TreeSet
 * 
 */
	
	private class InternalData<T extends Data>{
	// STRUCT di appoggio, collega ad un dato la lista di amici che mettono un like e il numero dei likes
		T data;
		TreeSet<String> friendsWhoLiked;
		int likes;
		
		InternalData (T data) {
			this.data = data;
			friendsWhoLiked = new TreeSet<String>();
			likes = 0;
		}
		
		// aggiunge un like al contatore ed inserisce f nella lista degli amici che hanno messo like
		// TreeSet garantisce che elementi duplicati o null non vengano inseriti
		boolean addLike(String f) { 
		// @EFFECTS 	post(friendsWhoLiked) = pre(friendsWhoLiked) U f 	if f != null
			if (friendsWhoLiked.add(f)) {
				likes++;
				return true;
			}
			return false;
			
		}
		
		public boolean equals(InternalData<T> b) {
			return (this.data.equals(b.data) && this.likes == b.likes);
		}
	}
	
	static final int MIN_LENGTH_PSW = 8;
	private byte[] psw;
	
	private HashMap<String, TreeSet<InternalData<E>>> categories;
	private HashMap<String, TreeSet<String>> friends;
	
	// istanzia una bacheca
	public Board2(String psw_plain) {
		/* @REQUIRES 		psw_plain != null
		 * 				&&	psw.length() >= MIN_LENGTH_PSW
		 * @MODIFIES 	this
		 * @EFFECTS 	crea una nuova bacheca
		 * @THROWS 		if psw_plain == null
		 * 					throws NullPointerException
		 * 				if psw.length() < MIN_LENGTH_PSW
		 * 					throws IllegalArgumentException
		 */
		
		if (psw_plain == null) throw new NullPointerException();
		if (psw_plain.length() < MIN_LENGTH_PSW) throw new IllegalArgumentException("La password NON rispetta i requisiti "
				+ "minimi di lunghezza: almeno " +  MIN_LENGTH_PSW + " caratteri");
		
		psw = MyPasswordCrypt.cryptPsw(psw_plain);
		
		categories = new HashMap<>(); // inferenza di tipi generici
		friends = new HashMap<>();
	}
	
	public void createCategory(String category, String psw_plain) throws NullPointerException, WrongPasswordException, IllegalArgumentException {
		
		if(category == null || psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		if(categories.containsKey(category)) throw new IllegalArgumentException("Categoria già presente");
		
		categories.put(category, new TreeSet<>(new MyComparator()));
	}
	
	public void removeCategory(String category, String 	psw_plain) throws NullPointerException, WrongPasswordException, IllegalArgumentException {
		
		if(category == null || psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		if(! categories.containsKey(category))  throw new IllegalArgumentException("Categoria NON presente");	
		
		categories.remove(category);
		
		for(String s : friends.keySet()) {
			if(friends.get(s).contains(category))
				friends.get(s).remove(category);
		}
	}
	
	public void addFriend(String category, String psw_plain, String friend) throws WrongPasswordException, NullPointerException, IllegalArgumentException {
		
		if(category == null || friend == null || psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		if(! categories.containsKey(category))  throw new IllegalArgumentException("Categoria NON presente");	
		
		if(! friends.containsKey(friend))
			friends.put(friend, new TreeSet<>());
			
		if (! friends.get(friend).add(category))
			throw new IllegalArgumentException(friend + " ha già accesso a " + category);
	}
	
	public void removeFriend(String category, String psw_plain, String friend) throws HiddenCategoryException, NullPointerException, WrongPasswordException, IllegalArgumentException {
		
		if(category == null || friend == null || psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		if(! categories.containsKey(category))  throw new IllegalArgumentException("Categoria NON presente");	
		
		if(! friends.containsKey(friend)) throw new IllegalArgumentException(friend + " NON è un amico valido");	
		
		if(! friends.get(friend).remove(category)) throw new HiddenCategoryException("Categoria NON condivisa con " + friend);
		
	}
	
	public boolean put(String psw_plain, E data, String category) throws NullPointerException, WrongPasswordException, IllegalArgumentException {
		
		if(category == null || data == null || psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		// la categoria del dato ha priorità superiore ==>
		if (category != data.getCategory())
			category = data.getCategory();
		
		if(! categories.containsKey(category))  throw new IllegalArgumentException("Categoria NON presente");	
		
		if(categories.get(category) == null) {
			categories.put(category, new TreeSet<>(new MyComparator()));
			categories.get(category).add(new InternalData<E>(data));
		} else { // Anche se TreeSet non ammette elementi duplicati devo vedere se esiste il dato negli InternalData
			// controlle se dati uguali, confronto fra InternalData<E> ed E
			for(InternalData<E> el : categories.get(data.getCategory()))
				if( el.data.equals(data) ) throw new IllegalArgumentException("DATO già presente: " + data.getDataTitle());
				
			categories.get(category).add(new InternalData<E>(data));	
		}	
		
		return true;
	}
	
	public E get(String psw_plain, E data) throws NullPointerException, WrongPasswordException, IllegalArgumentException {
		
		if(data == null || psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		if(! categories.containsKey(data.getCategory())) throw new IllegalArgumentException("Categoria del dato NON valida");	
		
		for(InternalData<E> el : categories.get(data.getCategory())) {
			if (el.data.equals(data)) 
				return el.data;
		}	
		throw new IllegalArgumentException("Dato NON presente: " + data.getDataTitle());
	}
	
	public E remove(String psw_plain, E data) throws NullPointerException, WrongPasswordException, IllegalArgumentException {

		if(data == null || psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		if(! categories.containsKey(data.getCategory())) throw new IllegalArgumentException("Categoria del dato NON valida");	
		
		Iterator<InternalData<E>> it = categories.get(data.getCategory()).iterator();
		InternalData<E> tmp;
		while(it.hasNext()) {
			tmp= it.next();
			if (tmp.data.equals(data)) {
				it.remove();
				return tmp.data;
			}
				
		}
		throw new IllegalArgumentException("Dato NON presente: " + data.getDataTitle());
	}
	
	public List<E> getDataCategory(String psw_plain, String category) throws NullPointerException, WrongPasswordException, IllegalArgumentException {
		
		if(category == null || psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		if(! categories.containsKey(category))  throw new IllegalArgumentException("Categoria NON presente");	
		
		ArrayList<E> a = new ArrayList<>();
		for(InternalData<E> tmp : categories.get(category)) {
			a.add(tmp.data);
		}
		
		return Collections.unmodifiableList(a); // ridondante poiché i dati sono immutabili
	}
	
	public void insertLike(String friend, E data) throws DuplicateLikeException, HiddenCategoryException, NullPointerException, IllegalArgumentException {
		
		if(friend == null || data == null) throw new NullPointerException();
		
		if(! categories.containsKey(data.getCategory()))  throw new IllegalArgumentException("Categoria del dato NON valida");	
		
		if(! friends.containsKey(friend)) throw new IllegalArgumentException(friend + " NON è un amico valido");
		
		if(! friends.get(friend).contains(data.getCategory()) ) 
			throw new HiddenCategoryException(friend + " non ha accesso alla categoria " + data.getCategory());
		
		for(InternalData<E> el : categories.get(data.getCategory())) {
			if (el.data.equals(data)) {
				if (! el.addLike(friend))
					throw new DuplicateLikeException(friend + " ha già messo like al dato " + data.getDataTitle());
				return;
			}
		}
		throw new IllegalArgumentException("Dato NON presente: " + data.getDataTitle());
	}
	
	// ITERATORE SU TUTTI I DATI
	public Iterator<E> getIterator(String psw_plain) throws ConcurrentModificationException, NullPointerException, WrongPasswordException {
		
		if(psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		return new LikeSortedDataIterator();
	}
	
	// produce una lista di TUTTI i dati di tipo InternalData<E> presenti in bacheca
	private TreeSet<InternalData<E>> genAllData() {
		
		TreeSet<InternalData<E>> v = new TreeSet<InternalData<E>>(new MyComparator());
		
		for(TreeSet<InternalData<E>> tmp : categories.values()) {
			v.addAll(tmp);
		}
		return v;
		
	}
	
	// classe di comparazione per ordinamento relativo ai like, ORDINE decrescente
	private class MyComparator implements Comparator<InternalData<E>> {
		@Override
		public int compare(InternalData<E> arg0, InternalData<E> arg1) {
			if (arg1.likes - arg0.likes == 0) {
				return arg0.data.getDataTitle().compareTo(arg1.data.getDataTitle());
			} else
				return arg1.likes - arg0.likes;	// decrescente
		}
	}
	
	// classe privata che genera iteratori di istanza per l'iteratore su tutti i dati
	private class LikeSortedDataIterator implements Iterator<E> {

		E tmp;
		TreeSet<InternalData<E>> a;
		
		public LikeSortedDataIterator() {
			a = genAllData();
		}
		
		@Override	
		public boolean hasNext() {
			if (a.size() <1  || a.first() == null)
				return false;
			return true;
		}

		@Override
		public E next() {
			if (! hasNext())
				return null;
			tmp = a.first().data;
			a.remove(a.first());
			return tmp; 
		}
		
		public void remove() { 
			throw new UnsupportedOperationException();
		}
		
	}
	
	// ITERATORE SUI DATI CONDIVISI
	public Iterator<E> getFriendIterator(String friend) throws ConcurrentModificationException, NullPointerException, IllegalArgumentException {
		
		if (friend == null) throw new NullPointerException();
		
		if (! friends.containsKey(friend)) throw new IllegalArgumentException(friend + " NON è un amico valido");	
		
		return new FriendIterator(friend);
	}
	
	// classe privata che genera iteratori di istanza per l'iteratore sui dati condivisi
	private class FriendIterator implements Iterator<E> {
		
		int ind;
		ArrayList<InternalData<E>> a;
		
		public FriendIterator(String f) {
			ind = 0;
			a = new ArrayList<>();
			
			for (String s : friends.get(f)) {
				a.addAll(categories.get(s));
			}
		}

		@Override
		public boolean hasNext() {
			if (ind >= a.size() || a.get(ind) == null)
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
		
	// confronta le password
	private boolean login(String psw_plain) {
		return MyPasswordCrypt.cmpPasswords(psw, MyPasswordCrypt.cryptPsw(psw_plain));
	}
	
	// restituisce il numero di like associato ad un dato se si rispettano le richieste
	public int getNumLikes(String psw_plain, E data) throws NullPointerException, WrongPasswordException, IllegalArgumentException {
		/* 	@REQUIRES	psw_plain  != null 
		 * 				&& 	data != null
		 * 				&& 	psw_plain == this.psw 
		 * 				&&  EXISTS j in [0, |DATA|). d_j == data
		 *  @THROWS 	if passw == null || dat == null
		 *  				throws NullPointerException (disp. in Java, unchecked)
		 *  			if passw != this.psw
		 *  				throws WrongPasswordException (non disp. in Java, unchecked)
		 *  			if NOT (EXISTS j in [0, |DATA|). d_j == dato )
		 *  				throws IllegalArgumentException (disp. in Java, unchecked) 
		 */
		if(data == null || psw_plain == null) throw new NullPointerException();
		
		if(! login(psw_plain)) throw new WrongPasswordException();
		
		if(! categories.containsKey(data.getCategory())) throw new IllegalArgumentException("Categoria del dato NON valida");	
		
		for(InternalData<E> el : categories.get(data.getCategory())) {
			if (el.data.equals(data)) 
				return el.likes;
		}
		
		throw new IllegalArgumentException("Dato NON presente: " + data.getDataTitle());
	}
}
