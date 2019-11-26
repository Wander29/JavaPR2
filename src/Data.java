public interface Data {
/* 	OVERVIEW 		Data rappresenta un dato astratto sottoforma di un insieme di 2 attributi e alcune operazioni
 * 					È una struttura astratta immutable, di dimensione finita e fissa
 * 
 * TYPICAL ELEMENT 	< dataTitle, category >
 * 				tc
 * 					dataTitle	!= null
 * 				&&	cateogry 	!= null
 */
	
	//verrà implementato dalle sottoclassi
	public void display();
	
	public String getDataTitle();
	
	public String getCategory();
}
