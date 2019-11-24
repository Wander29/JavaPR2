public interface Data2 {
/* 	OVERVIEW 		Data rappresenta un dato sottoforma di un insieme di 3 attributi e alcune operazioni
 * 					È una struttura astratta immutable, di dimensione finita e fissa
 * 
 * TYPICAL ELEMENT 	{ dataName, content, category }
 * 				tc
 * 					dataName 	!= null
 * 				&&	content 	!= null
 * 				&&	cateogry 	!= null
 */
	
	//verrà implementato dalle sottoclassi
	public void display();
	
	public String getDataTitle();
	
	public String getCategory();
}
