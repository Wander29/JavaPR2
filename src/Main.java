
public class Main {

	public static void main(String[] args) {
		MyNetwork<MyData> rete = new MyNetwork<>();
		
		rete.addUser("Ludovico", "gattinoRampante");
		Board<MyData> tmp = rete.getBoard("Ludovico");

	}

}
