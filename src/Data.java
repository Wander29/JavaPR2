public abstract class Data {
	
	private String dataName;
	private String content;
	private String category;
	
	public Data(String name, String cont, String cat) {
		dataName = name;
		content = cont;
		category = cat;
	}
	
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
	
	public boolean equals(Data b) {
		if ( 	this.dataName.equals(b.getDataName()) 
			&&  this.category.equals(b.getCategory())
			&& 	this.content.equals(b.getContent())
			)
			return true;
		else
			return false;
	}
}
