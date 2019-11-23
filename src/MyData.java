public class MyData extends Data{

	public MyData(String name, String cont, String cat) {
		super(name, cont, cat);
	}

	public void display() {
		System.out.println("DATO: " + super.getDataName());
		/*
		System.out.println("LIKES: ");
		for (String s : likes) 
			System.out.print(s + "  ");
		*/
		System.out.println("----------");
	}
	
	
}
