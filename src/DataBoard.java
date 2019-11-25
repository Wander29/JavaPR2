import java.util.*;

public interface DataBoard<E extends Data> {
/* 	OVERVIEW 	Rappresenta un contenitore di oggetti generici che estendono il tipo di dato Data. 
 * 				Permette di memorizzare dati e visualizzarli
 * 				Ogni dato ha associata la propria categoria
 * 				Ogni bacheca ha associata una password, e una lista di amici ognuno dei quali ha associato 1 o + categorie 
 * 				Per ogni amico associa anche una lista di categorie di dati da condividere con esso
 * 				É una struttura mutabile, di dimensione finita ma non fissa
 * 				Non sono ammessi dati/amici/categorie duplicati o null
 *  
 *  TYPICAL ELEMENT { 	psw, 
 *  					INSIEMI: 		FRIENDS, CATEGORIES, DATA		
 *  																tc
 *  						for all j in [0, |DATA|). d_j != null && (for all k in (j, |DATA|). d_j != d_k)
 *  						for all j in [0, |FRIENDS|). f_j != null && (for all k in (j, |FRIENDS|). f_j != f_k)
 *  						for all j in [0, |CATEGORIES|). c_j != null && (for all k in (j, |CATEGORIES|). c_j != c_k)
 *  
 *  					FUNZIONI:
 *  						f(FRIEND) 	-> 	{ c0, ..., c_f }  con c0 != ... != c_f != null in CATEGORIES 
 *  						g(DATA) 	-> 	{ c in CATEGORIES, #like, { friend0, f1, ... , f_q } } 
 *  											con  c != null, #like >= 0 && friend0 != f1 != ... != f_q != null
 *  						h(CATEGORY) -> 	{ {d_i, ..., d_l}, { friend0, f1, ... , f_z } }  
 *  											con d_i != ... != d_l != null && f0 != ... != f_z != null
 *  						i(this) 	-> 	{ 	psw, 
 *  											{ 	<category0, data0> , <c0, d1> ... ,	<c0, d_x> ...
 *  												<category_m, data_n>, .... 		  , <c_m, d_y> } ,
 *  										{	friend0, f1, .... , f_w} }
 *  			}
*/	
	
	// Crea una categoria di dati se vengono rispettati i controlli di identità
	public void createCategory(String category, String passw) throws NullPointerException, WrongPasswordException, IllegalArgumentException;
	/* 	@REQUIRES		category != null 
	 * 				&& 	passw != null 
	 * 				&& 	passw == this.psw 
	 * 				&& 	NOT (EXISTS i in CATEGORIES. c_i == category)
	 *  @MODIFIES	this
	 *  @EFFECTS	CATEGORIES = pre(CATEGORIES) U category;
	 *  @THROWS 	if category == null || passw == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if passw != this.psw
	 *  				throws WrongPasswordException (non disp. in Java, unchecked)
	 *  			if EXISTS i in CATEGORIES. c_i == category
	 *  				throws IllegalArgumentException (disp. in Java, unchecked)
	 */
	
	// Rimuove una categoria di dati
	// se vengono rispettati i controlli di identità
	public void removeCategory(String category, String passw) throws NullPointerException, WrongPasswordException, IllegalArgumentException;
	/* 	@REQUIRES		category != null 
	 * 				&& 	passw != null 
	 * 				&& 	passw == this.psw 
	 * 				&& 	EXISTS i in CATEGORIES. c_i == category
	 *  @MODIFIES	this
	 *  @EFFECTS	CATEGORIES = pre(CATEGORIES) \ category &&
	 *  			for all f in FRIENDS tc category IN f(f)
	 *  				post( f(f) ) = f(f) \ category
	 *  			DATA = DATA \ { d_i  tc  d_i IN h(category) }
	 *  @THROWS 	if category == null || passw == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if passw != this.psw
	 *  				throws WrongPasswordException (non disp. in Java, unchecked)
	 *  			if NOT (EXISTS i in CATEGORIES. c_i == category )
	 *  				throws IllegalArgumentException (disp. in Java, unchecked)
	 */
	
	// Aggiunge un amico ad una categoria di dati
	// se vengono rispettati i controlli di identità
	public void addFriend(String category, String passw, String friend) throws NullPointerException, WrongPasswordException, IllegalArgumentException ;
	/*	@REQUIRES		category != null 
	 * 				&& 	passw  != null 
	 * 				&& 	friend != null
	 * 				&& 	passw == this.psw 
	 * 				&& 	EXISTS i in CATEGORIES. c_i == category
	 * 				&& 	category NOT IN f(friend)
	 *  @MODIFIES	this
	 *  @EFFECTS	FRIENDS = pre(FRIENDS) U friend 	
	 *  			&&	se pre( f(friend) ) = { c0, ..., c_w } ==> f(friend) = { c0, ..., c_w } U category	
	 *  @THROWS 	if category == null || passw == null || friend == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if passw != this.psw
	 *  				throws WrongPasswordException (non disp. in Java, unchecked)
	 *  			if category IN f(friend) || NOT (EXISTS i in CATEGORIES. c_i == category )
	 *  				throws IllegalArgumentException (disp. in Java, unchecked) 
	 */
	
	// rimuove un amico da una categoria di dati
	// se vengono rispettati i controlli di identità
	public void removeFriend(String category, String passw, String friend) throws HiddenCategoryException, NullPointerException, WrongPasswordException, IllegalArgumentException ;
	/*	@REQUIRES		category != null 
	 * 				&& 	passw  != null 
	 * 				&& 	friend != null
	 * 				&& 	passw == this.psw 
	 * 				&& 	EXISTS i in CATEGORIES. c_i == category
	 * 				&&  EXISTS j in FRIENDS . f_j == friend
	 * 				&& 	category IN f(friend)
	 *  @MODIFIES	this
	 *  @EFFECTS		FRIENDS = pre(FRIENDS) \ friend 	
	 *  			&&	se pre( f(friend) ) = { c0, ..., cateogry, ..., c_w } ==> f(friend) = { c0, ..., c_w }	
	 *  @THROWS 	if category == null || passw == null || friend == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if passw != this.psw
	 *  				throws WrongPasswordException (non disp. in Java, unchecked)
	 *  			if NOT (EXISTS i in CATEGORIES. c_i == category ) || NOT (EXISTS j in FRIENDS . f_j == friend)
	 *  				throws IllegalArgumentException (disp. in Java, unchecked) 
	 *  			if NOT (category IN f(friend))
	 *  				throws HiddenCategoryException (non disp. in Java, unchecked)
	 */
	
	//Inserisce un dato in bacheca
	// se vengono rispettati i controlli di identità
	public boolean put(String passw, E dato, String category) throws NullPointerException, WrongPasswordException, IllegalArgumentException ;
	/* 	@REQUIRES	category != null 
	 * 				&& 	passw  != null 
	 * 				&& 	dato != null
	 * 				&& 	passw == this.psw 
	 * 				&& 	EXISTS i in CATEGORIES. c_i == category
	 * 				&&	cat_dato == category
	 * 				&&  NOT EXISTS j in [0, |DATA|). d_j == dato
	 *  @MODIFIES	this
	 *  @EFFECTS	post(DATA) = pre(DATA) U dato 	tc	g(dato) == category
	 *  @THROWS 	if category == null || passw == null || dato == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if passw != this.psw
	 *  				throws WrongPasswordException (non disp. in Java, unchecked)
	 *  			if NOT (EXISTS i in CATEGORIES. c_i == category ) 	|| EXISTS j in [0, |DATA|). d_j == dato
	 *  				throws IllegalArgumentException (disp. in Java, unchecked) 
	 */
		
	
	//Restituisce una copia del dato in bacheca
	// se vengono rispettati i controlli di identità
	public E get(String passw, E dato) throws NullPointerException, WrongPasswordException, IllegalArgumentException ;
	/* 	@REQUIRES	passw  != null 
	 * 				&& 	dato != null
	 * 				&& 	passw == this.psw 
	 * 				&&  EXISTS j in [0, |DATA|). d_j == dato
	 *  @MODIFIES	//
	 *  @EFFECTS	restituisce d IN DATA tc d == dato
	 *  @THROWS 	if passw == null || dato == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if passw != this.psw
	 *  				throws WrongPasswordException (non disp. in Java, unchecked)
	 *  			if NOT (EXISTS j in [0, |DATA|). d_j == dato )
	 *  				throws IllegalArgumentException (disp. in Java, unchecked) 
	 */
	
	// Rimuove il dato dalla bacheca
	// se vengono rispettati i controlli di identità
	public E remove(String passw, E dato) throws NullPointerException, WrongPasswordException, IllegalArgumentException;
	/* 	@REQUIRES	passw  != null 
	 * 				&& 	dato != null
	 * 				&& 	passw == this.psw 
	 * 				&&  EXISTS j in [0, |DATA|). d_j == dato
	 *  @MODIFIES	this
	 *  @EFFECTS	post(DATA) = pre(DATA) \ dato
	 *  @THROWS 	if passw == null || dato == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if passw != this.psw
	 *  				throws WrongPasswordException (non disp. in Java, unchecked)
	 *  			if NOT (EXISTS j in [0, |DATA|). d_j == dato )
	 *  				throws IllegalArgumentException (disp. in Java, unchecked) 
	 */
	
	// Crea la lista dei dati in bacheca di una determinata categoria
	// se vengono rispettati i controlli di identità
	public List<E> getDataCategory(String passw, String category) throws NullPointerException, WrongPasswordException, IllegalArgumentException;
	/* 	@REQUIRES	category != null 
	 * 				&& 	passw  != null 
	 * 				&& 	passw == this.psw 
	 * 				&& 	EXISTS i in CATEGORIES. c_i == category
	 *  @MODIFIES	//
	 *  @EFFECTS	restituisce tutti i dati nella categoria, ovvero 
	 *  			h(category) = { d_i, ..., d_l ) tramite una lista, quindi
	 *  				d_i :: ... :: d_l
	 *  @THROWS 	if category == null || passw == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if passw != this.psw
	 *  				throws WrongPasswordException (non disp. in Java, unchecked)
	 *  			if NOT (EXISTS i in CATEGORIES. c_i == category )
	 *  				throws IllegalArgumentException (disp. in Java, unchecked) 
	 */
	
	// Aggiunge un like a un dato
	void insertLike(String friend, E data) throws DuplicateLikeException, HiddenCategoryException, NullPointerException, IllegalArgumentException;
	/* 	@REQUIRES		data != null
	 * 				&& 	friend != null
	 * 				&& 	EXISTS j in FRIENDS . f_j == friend
	 * 				&&	data IN DATA
	 * 				&&  cat_data IN f(friend)
	 * 				&& 	NOT ( friend IN g(data) )
	 *  @MODIFIES	this
	 *  @EFFECTS	se 	pre ( g(data) ) = { c in CATEGORIES, #like, { friend0, f1, ... , f_z } }
	 *  				post( g(data) ) = { c in CATEGORIES, #like + 1, { friend0, f1, ... , f_z } U  friend }
	 *  @THROWS 	if friend == null || data == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if NOT (EXISTS j in FRIENDS . f_j == friend) || NOT (data IN DATA)
	 *  				throws IllegalArgumentException (disp. in Java, unchecked) 
	 *  			if NOT (cat_data IN f(friend) )
	 *  				throws HiddenCategoryException (non disp. in Java, unchecked)
	 *  			if friend in g(data)
	 *  				throw DuplicateLikeException (non disp. in Java, unchecked)
	 */
	
	// restituisce un iteratore (senza remove) che genera tutti i dati in
	// bacheca ordinati rispetto al numero di like
	// se vengono rispettati i controlli di identità
	public Iterator<E> getIterator(String passw) throws ConcurrentModificationException, NullPointerException, WrongPasswordException;
	/* 	@REQUIRES		this non modificato durante tutta l'iterazione
	 * 				&& 	passw  != null 
	 * 				&& 	passw == this.psw 
	 *  @MODIFIES	//
	 *  @EFFECTS	resitutisce un iteratore con tutti i dati ordinati in modo tc
	 *  				d_0, d_1, ... , d_q		con		#likes(d_0) > #likes(d_1) > ... > #likes(d_q)
	 *  @THROWS 	if this viene modificato durante l'iterazione
	 *  				throws ConcurrentModificationException
	 *  			if passw == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if passw != this.psw
	 *  				throws WrongPasswordException (non disp. in Java, unchecked)
	 */
	
	// restituisce un iteratore (senza remove) che genera tutti i dati in
	// bacheca condivisi
	public Iterator<E> getFriendIterator(String friend) throws ConcurrentModificationException, NullPointerException, IllegalArgumentException;
	/* 	@REQUIRES		this non modificato durante tutta l'iterazione
	 * 				&& 	friend  != null 
	 * 				&& 	friend IN FRIENDS
	 *  @MODIFIES	//
	 *  @EFFECTS	resitutisce un iteratore con tutti i dati della bacheca condivisi con friend, 
	 *  			ovvero restituisce tutti i dati presenti nelle categorie condivise con friend : 
	 *  			f(FRIEND)	= { c0, ..., c_f } in CATEGORIES && h(CATEGORY) -> 	{ d_i, ..., d_l )
	 *  			==> restituisce ( U h(c) for all c IN f(friend) )
	 *  @THROWS 	if this viene modificato durante l'iterazione
	 *  				throws ConcurrentModificationException
	 *  			if friend == null
	 *  				throws NullPointerException (disp. in Java, unchecked)
	 *  			if NOT (EXISTS j in FRIENDS . f_j == friend)
	 *  				throws IllegalArgumentException (disp. in Java, unchecked) 
	 *  				
	 */
	
}