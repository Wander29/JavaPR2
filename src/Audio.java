public class Audio extends MyData {
	
	private String content;
	
	public Audio(String name, String cat) throws NullPointerException {
		/* @REQUIRES 	name != null && cont != null && cat != null
		 * @THROWS 		if name == null || cont == null || cat == null
		 * 					throws NullPointerException
		 */
		super(name, cat);
		content = "♪♫♬♪♫♬♪♫♬♪♫♬♪♫♬♪♫♬♪♫♬♪♫♬";
	}
	
	public String getContent() {
		return this.content;
	}

	public boolean equals(Testo b) throws NullPointerException {
		/* @REQUIRES 	b != null
		 * @EFFECTS 	restituisce true se this e b denotano lo stesso oggetto relativamente ai dati contenuti
		 * @THROWS 		if b == null
		 * 					throws NullPointerException
		 */
		if (b == null) throw new NullPointerException();
		
		if ( 	this.getDataTitle().equals(b.getDataTitle()) 
			&&  this.getCategory().equals(b.getCategory()) )
			return true;
		else
			return false;
	}

	@Override
	public void display() {
		System.out.println("TITOLO: " + this.getDataTitle());
		System.out.println("Categoria: " + this.getCategory());
		System.out.println("Contenuto: " + getContent() + "\n");
		this.printFrame();
	}

}

