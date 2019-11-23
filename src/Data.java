public abstract class Data {
/* 	OVERVIEW 		Data rappresenta un dato sottoforma di un insieme di 3 attributi e alcune operazioni
 * 					È una struttura astratta immutable, di dimensione finita e fissa
 * 
 * TYPICAL ELEMENT 	{ dataName, content, category }
 * 				tc
 * 					dataName 	!= null
 * 				&&	content 	!= null
 * 				&&	cateogry 	!= null
 */
	private String dataName;
	private String content;
	private String category;
	
	public Data(String name, String cont, String cat) throws NullPointerException {
		/* @REQUIRES 		name != null
		 * 				&&	cont != null
		 * 				&&	cat  != null
		 * @EFFECTS 	istanzia this
		 * @THROWS 		if name == null || cont == null || cat == null
		 * 					throws NullPointerException
		 */
		if(name == null || cont == null) throw new NullPointerException();
		
		dataName = name;
		content = cont;
		category = cat;
	}
	
	//verrà implementato dalle sottoclassi
	public abstract void display();
	
	public String getDataName() {
		return this.dataName;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public String getContent() {
		return this.content;
	}
	
	// restituisce true sse lo stato locale dei 2 oggetti è lo stesso, deep equality
	public boolean equals(Data b) throws NullPointerException {
		/* @REQUIRES 	b != null
		 * @EFFECTS 	restituisce true se this e b denotano lo stesso oggetto relativamente ai dati contenuti
		 * @THROWS 		if b == null
		 * 					throws NullPointerException
		 */
		if (b == null) throw new NullPointerException();
		
		if ( 	this.dataName.equals(b.getDataName()) 
			&&  this.category.equals(b.getCategory())
			&& 	this.content.equals(b.getContent()) 	)
			return true;
		else
			return false;
	}
}
