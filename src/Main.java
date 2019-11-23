import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		MyNetwork<MyData> rete = new MyNetwork<>();
		// TEST 
		ArrayList<String> users = new ArrayList<>();
		users.add("Ludovico"); users.add("Pietro"); users.add("Marco");
		
		ArrayList<String> categ = new ArrayList<>();
		categ.add("Gattini"); categ.add("Moto"); categ.add("Videogiochi");
		
		ArrayList<MyData> data = new ArrayList<>();
		data.add(new MyData("Mainecoon", "il mio Mainecoon è inarrivabile", categ.get(0)));
		data.add(new MyData("Lemure", "Quasi un gatto", categ.get(0)));
		data.add(new MyData("Dormire", "Gatto che si ribalta mentre dorme", categ.get(0)));
		data.add(new MyData("Lorenzo si ritira", "Jorge Lorenzo si ritira dalla MotoGP, oh no!", categ.get(1)));
		data.add(new MyData("nuova Ducati", "La nuova Ducati da strada è ora disponibile sul mercato", categ.get(1)));
		data.add(new MyData("Kojima", "Death Stranding raggiunge apici mai visti", categ.get(2)));
		data.add(new MyData("PS5", "Rumor sulla possibilità di cartucce per PS5", categ.get(2)));
		
		try { // password minore di 8 caratteri
			rete.addUser(users.get(0), "passwordSuperStrong");
			rete.addUser(users.get(1), "test");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { // get di una bacheca non presente
			Board<MyData> tmp = rete.getBoard(users.get(1));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { // password errata
			rete.getBoard(users.get(0)).createCategory(categ.get(0), "passwordSuperStrong");
			rete.getBoard(users.get(0)).createCategory(categ.get(2), "passwordSuperStrong");
			rete.getBoard(users.get(0)).createCategory(categ.get(1), "passwordNotSoStrong");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (WrongPasswordException e) {
			System.out.println(e.getMessage());
		}
		
		try { // categoria già presente
			rete.getBoard(users.get(0)).createCategory(categ.get(0), "passwordSuperStrong");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try { // rimozione di una categoria non presente
			rete.getBoard(users.get(0)).removeCategory(categ.get(1), "passwordSuperStrong");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { // 
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try { // 
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}
