public interface DataBoard<E extends Data> {
	// Crea una categoria di dati
	// se vengono rispettati i controlli di identità
	public void createCategory(String category, String passw);

	// Rimuove una categoria di dati
	// se vengono rispettati i controlli di identità
	public void removeCategory(String category, String passw);

	// Aggiunge un amico ad una categoria di dati
	// se vengono rispettati i controlli di identità
	public void addFriend(String category, String passw, String friend);

	// rimuove un amico da una categoria di dati
	// se vengono rispettati i controlli di identità
	public void removeFriend(String category, String passw, String friend);

	//Inserisce un dato in bacheca
	// se vengono rispettati i controlli di identità
	public boolean put(String passw, <E extends Data> dato, String categoria);

	//Restituisce una copia del dato in bacheca
	// se vengono rispettati i controlli di identità
	public E get(String passw, <E extends Data> dato);

	// Rimuove il dato dalla bacheca
	// se vengono rispettati i controlli di identità
	public E remove(String passw, <E extends Data> dato);

	//Crea la lista dei dati in bacheca di una determinata categoria
	// se vengono rispettati i controlli di identità
	public List<E extends Data>
	getDataCategory(String passw, String category);

	// Aggiunge un like a un dato
	void insertLike(String friend, <E extends Data> data);

	
	// restituisce un iteratore (senza remove) che genera tutti i dati in
	// bacheca ordinati rispetto al numero di like
	// se vengono rispettati i controlli di identità
	public Iterator<E> getIterator(String passw);

	// restituisce un iteratore (senza remove) che genera tutti i dati in
	// bacheca condivisi
	public Iterator<E> getFriendIterator(String friend);
	
	// ... altre operazione da definire a scelta
}
