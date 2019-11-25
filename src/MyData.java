public abstract class MyData implements Data {
	/* 	OVERVIEW 		Data rappresenta un dato sottoforma di un insieme di 3 attributi e alcune operazioni
	 * 					È una struttura astratta immutable, di dimensione finita e fissa
	 * 
	 * TYPICAL ELEMENT 	{ dataName, content, category }
	 * 				tc
	 * 					dataName 	!= null
	 * 				&&	content 	!= null
	 * 				&&	cateogry 	!= null
	 */
	
	private String dataTitle;
	private String category;
	
	public MyData (String name, String cat) throws NullPointerException {
		/* @REQUIRES 		name != null
		 * 				&&	cat  != null
		 * @EFFECTS 	istanzia this
		 * @THROWS 		if name == null || cat == null
		 * 					throws NullPointerException
		 */
		if(name == null || cat == null) throw new NullPointerException();
		
		dataTitle = name;
		category = cat;
	}
	
	public String getDataTitle() {
		return this.dataTitle;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void printFrame() {
		System.out.println("# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ #");
	}

	//verrà implementato dalle sottoclassi
	public abstract void display();
	
}
