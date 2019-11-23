import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		MyNetwork<MyData> rete = new MyNetwork<>();
		// TEST 
		ArrayList<String> users = new ArrayList<>();
		users.add("Ludovico"); users.add("Pietro"); users.add("Marco"); users.add("Emanuele");
		
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
		data.add(new MyData("GoogleStadia", "Che flop epocale", categ.get(2)));
		
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
		
		try { // condivisione di una stessa categoria con uno stesso amico
			rete.getBoard(users.get(0)).addFriend(categ.get(0), "passwordSuperStrong", users.get(1));
			rete.getBoard(users.get(0)).addFriend(categ.get(2), "passwordSuperStrong", users.get(1));
			rete.getBoard(users.get(0)).addFriend(categ.get(0), "passwordSuperStrong", users.get(2));
			rete.getBoard(users.get(0)).addFriend(categ.get(2), "passwordSuperStrong", users.get(2));
			rete.getBoard(users.get(0)).addFriend(categ.get(0), "passwordSuperStrong", users.get(1));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try { // condivisione di una categoria non presente nella bacheca
			rete.getBoard(users.get(0)).addFriend(categ.get(1), "passwordSuperStrong", users.get(1));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { // rimozione di un amico non presente nella lista amici
			rete.getBoard(users.get(0)).removeFriend(categ.get(0), "passwordSuperStrong", users.get(1));
			rete.getBoard(users.get(0)).removeFriend(categ.get(0), "passwordSuperStrong", users.get(3));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { 	// rimozione di un amico da una categoria cui non ha accesso, 
				// anche se presente nella lista amici
			rete.getBoard(users.get(0)).removeFriend(categ.get(0), "passwordSuperStrong", users.get(1));
		} catch (HiddenCategoryException e) {
			System.out.println(e.getMessage());
		}
		
		try { // inserimento di un dato già presente
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(0), data.get(0).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(1), data.get(1).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(5), data.get(5).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(6), data.get(6).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(0), data.get(0).getCategory());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { // inserimento di un dato la cui categoria non è presente in bacheca
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(3), data.get(3).getCategory());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try { // get di un dato la cui categoria non è presente
			rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(3));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { // get di un dato non presente
			rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(0));
			rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(2));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { 	// get di un dato precedentemente inserito in modo corretto
				// ma la cui categoria è stato poi rimossa
		rete.getBoard(users.get(0)).createCategory(categ.get(1), "passwordSuperStrong");
		rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(4), data.get(4).getCategory());
		rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(4));
		rete.getBoard(users.get(0)).removeCategory(categ.get(1), "passwordSuperStrong");
		rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(4));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { // rimozione di un dato non presente
			rete.getBoard(users.get(0)).remove("passwordSuperStrong", data.get(0));
			rete.getBoard(users.get(0)).remove("passwordSuperStrong", data.get(2));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { // getDataCategory e modifica della lista ritornata
			rete.getBoard(users.get(0)).getDataCategory("passwordSuperStrong", categ.get(0)).add(data.get(0));
		} catch (UnsupportedOperationException e) {
			System.out.println("Operazione NON supportata");
		}
		
		try { // amico vuole inserire like ad un dato di una categoria non condivisa con lui
			rete.getBoard(users.get(0)).insertLike(users.get(1), data.get(5));
			rete.getBoard(users.get(0)).insertLike(users.get(1), data.get(6));
			
			rete.getBoard(users.get(0)).insertLike(users.get(2), data.get(1));
			rete.getBoard(users.get(0)).insertLike(users.get(2), data.get(5));
			rete.getBoard(users.get(0)).insertLike(users.get(2), data.get(6));
			
			rete.getBoard(users.get(0)).addFriend(categ.get(2), "passwordSuperStrong", users.get(3));
			rete.getBoard(users.get(0)).insertLike(users.get(3), data.get(6));
			
			rete.getBoard(users.get(0)).insertLike(users.get(1), data.get(1));
		} catch (HiddenCategoryException e) {
			System.out.println(e.getMessage());
		}
		
		try { // amico vuole inserire like ad un dato cui lo ha già messo
			rete.getBoard(users.get(0)).insertLike(users.get(3), data.get(6));
		} catch (DuplicateLikeException e) {
			System.out.println(e.getMessage());
		}
		
		try { // amico vuole inserire like ad un dato non presente
			rete.getBoard(users.get(0)).insertLike(users.get(3), data.get(7));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		//ITERATORI
		System.out.println("\n\nITERATORI_TEST\n\n");
		
		Iterator<MyData> it = rete.getBoard(users.get(0)).getIterator("passwordSuperStrong");
		MyData tmp;
		while(it.hasNext()) {
			tmp = it.next();
			System.out.println("LIKES: " + rete.getBoard(users.get(0)).getNumLikes("passwordSuperStrong", tmp));
			tmp.display();
		}
		
		System.out.println("\n Dati CONDIVISI CON " + users.get(1));
		it = rete.getBoard(users.get(0)).getFriendIterator(users.get(1));
		while(it.hasNext()) {
			it.next().display();		
		}
		
		System.out.println("\n Dati CONDIVISI CON " + users.get(2));
		it = rete.getBoard(users.get(0)).getFriendIterator(users.get(2));
		while(it.hasNext()) {
			it.next().display();		
		}
		
		System.out.println("\n Dati CONDIVISI CON " + users.get(3));
		it = rete.getBoard(users.get(0)).getFriendIterator(users.get(3));
		while(it.hasNext()) {
			it.next().display();		
		}
		
		try { // elimino una categoria e itero sui dati di tale categoria tramite una amico con cui essa era condivisa
			rete.getBoard(users.get(0)).removeCategory(categ.get(0), "passwordSuperStrong");
			System.out.println("\n Dati CONDIVISI CON " + users.get(2));
			it = rete.getBoard(users.get(0)).getFriendIterator(users.get(2));
			while(it.hasNext()) {
				it.next().display();		
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// creo un'altra bacheca
		
		// qualche operazione sulla seconda implementazione
	}
}
