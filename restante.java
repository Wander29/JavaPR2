public interface DataBoard<E extends Data> {

	// Rimuove il dato dalla bacheca
	// se vengono rispettati i controlli di identità
	public E remove(String passw, <E extends Data> dato);

	//Crea la lista dei dati in bacheca di una determinata categoria
	// se vengono rispettati i controlli di identità
	public List<E extends Data>
	getDataCategory(String passw, String category);

	
	// restituisce un iteratore (senza remove) che genera tutti i dati in
	// bacheca ordinati rispetto al numero di like
	// se vengono rispettati i controlli di identità
	public Iterator<E> getIterator(String passw);

	// restituisce un iteratore (senza remove) che genera tutti i dati in
	// bacheca condivisi
	public Iterator<E> getFriendIterator(String friend);
	
	// ... altre operazione da definire a scelta
}
