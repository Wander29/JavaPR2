import java.util.*;

//	@author	 Venturi Ludovico

public class Main {

	public static void main(String[] args) {
		
		/********************************************
		*	 PRIMA IMPLEMENTAZIONE di Board		*
		/********************************************/
		// creo una rete == socialNetwrok
		MyNetwork<MyData, Board<MyData>> rete = new MyNetwork<>();
		// TEST 
		// creo una lista di utenti (che faranno anche da amici)
		ArrayList<String> users = new ArrayList<>();
		users.add("Ludovico"); users.add("Pietro"); users.add("Marco"); users.add("Emanuele");
		
		ArrayList<String> categ = new ArrayList<>();
		categ.add("Gattini"); categ.add("Moto"); categ.add("Videogiochi");
		
		// lista di dati [OSS: ogni dato è sottotipo di MyData]
		ArrayList<MyData> data = new ArrayList<>();
		data.add(new Testo("Mainecoon", "il mio Mainecoon è inarrivabile", categ.get(0)));
		data.add(new Testo("Lemure", "Piovono gatti!", categ.get(0)));
		data.add(new Testo("Dormire", "Pensa, i gatti si ribaltano mentre dormono", categ.get(0)));
		data.add(new Testo("Lorenzo si ritira", "Jorge Lorenzo si ritira dalla MotoGP, oh no!", categ.get(1)));
		data.add(new Testo("nuova Ducati", "adrenalina a mille nel provare la nuova Ducati", categ.get(1)));
		data.add(new Testo("Kojima", "Death Stranding raggiunge apici di coinvolgimento mai visti", categ.get(2)));
		data.add(new Testo("PS5", "Rumor sulla possibilità di cartucce per PS5", categ.get(2)));
		data.add(new Testo("Google Stadia", "Che flop epocale", categ.get(2)));
		
		data.add(new Audio("Fusa gattini", categ.get(0))); //8
		data.add(new Audio("Miagolii", categ.get(0))); //9
		data.add(new Audio("Gatto che chiede scusa", categ.get(0))); //10
		data.add(new Audio("Accensione Desmosedici", categ.get(1))); //11
		data.add(new Audio("The Last Of Us OST", categ.get(2))); //12
		
		// testo inizialmente su una sola bacheca tutti i metodi compiendo sia azioni corrette
		// che sbagliate: queste ultime sono sempre le ultime in ogni blocco try-catch
		
		// successivamente inserisco anche un'altra bahceca
		try { // password minore di 8 caratteri
			
			rete.addUser(users.get(0), new Board<MyData>("passwordSuperStrong"));
			rete.addUser(users.get(1), new Board<MyData>("test"));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try { // get di una bacheca non presente
			rete.getBoard(users.get(1));
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
		
		try { // stringa nulla passata (vale per tutti i metodi)
			rete.getBoard(users.get(0)).createCategory(null, "passwordSuperStrong");
		} catch (NullPointerException e) {
			System.out.println("Uno dei dati inseriti è nullo, operazione fallita");
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
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(8), data.get(8).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(9), data.get(9).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(10), data.get(10).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(12), data.get(12).getCategory());
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
		
		System.out.println("\nDati CONDIVISI CON " + users.get(2));
		it = rete.getBoard(users.get(0)).getFriendIterator(users.get(2));
		while(it.hasNext()) {
			it.next().display();		
		}
		
		try { // elimino una categoria e itero sui dati di tale categoria tramite una amico con cui essa era condivisa
			rete.getBoard(users.get(0)).removeCategory(categ.get(0), "passwordSuperStrong");
			System.out.println("\nDati CONDIVISI CON " + users.get(2));
			it = rete.getBoard(users.get(0)).getFriendIterator(users.get(2));
			while(it.hasNext()) {
				it.next().display();		
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		// creo un'altra bacheca
		rete.addUser(users.get(1), new Board<MyData>("password01"));
		
		try {
			rete.getBoard(users.get(1)).createCategory(categ.get(0), "password01");
			rete.getBoard(users.get(1)).createCategory(categ.get(1), "password01");
			rete.getBoard(users.get(1)).createCategory(categ.get(2), "password01");
			
			rete.getBoard(users.get(1)).addFriend(categ.get(0), "password01", users.get(0));
			rete.getBoard(users.get(1)).addFriend(categ.get(1), "password01", users.get(0));
			rete.getBoard(users.get(1)).addFriend(categ.get(2), "password01", users.get(0));
			
			rete.getBoard(users.get(1)).addFriend(categ.get(1), "password01", users.get(2));
			rete.getBoard(users.get(1)).addFriend(categ.get(2), "password01", users.get(3));
			
			rete.getBoard(users.get(1)).put("password01", data.get(0), data.get(0).getCategory());
			rete.getBoard(users.get(1)).put("password01", data.get(3), data.get(3).getCategory());
			rete.getBoard(users.get(1)).put("password01", data.get(6), data.get(6).getCategory());
			rete.getBoard(users.get(1)).put("password01", data.get(8), data.get(8).getCategory());
			rete.getBoard(users.get(1)).put("password01", data.get(9), data.get(9).getCategory());
			rete.getBoard(users.get(1)).put("password01", data.get(11), data.get(11).getCategory());
			
			rete.getBoard(users.get(1)).insertLike(users.get(0), data.get(0));
			rete.getBoard(users.get(1)).insertLike(users.get(0), data.get(11));
			rete.getBoard(users.get(1)).insertLike(users.get(0), data.get(9));
			
			rete.getBoard(users.get(1)).insertLike(users.get(3), data.get(6));
			rete.getBoard(users.get(1)).insertLike(users.get(2), data.get(11));
			
			
			System.out.println(MyPasswordCrypt.cmpPasswords(MyPasswordCrypt.cryptPsw("password01"), rete.getBoard(users.get(1)).getPsw()));
			System.out.println("\n\nBACHECA DI " + users.get(1));
			
			System.out.println("\nDATI nella bacheca: \n");
			
			it = rete.getBoard(users.get(1)).getIterator("password01");
			while(it.hasNext()) {
				tmp = it.next();
				System.out.println("LIKES: " + rete.getBoard(users.get(1)).getNumLikes("password01", tmp));
				tmp.display();
			}
			
			System.out.println("\nDati CONDIVISI CON " + users.get(2));
			it = rete.getBoard(users.get(1)).getFriendIterator(users.get(2));
			while(it.hasNext()) {
				it.next().display();		
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		/********************************************
		*	 SECONDA IMPLEMENTAZIONE di Board		*
		/********************************************/
		
		System.out.println("\n### BOARD2 ###\n\n");
		MyNetwork<MyData, Board2<MyData>> rete2 = new MyNetwork<>();
		
		try {
			rete2.addUser(users.get(0), new Board2<MyData>("WonderfulCats"));
			rete2.getBoard(users.get(0)).createCategory(categ.get(0), "WonderfulCats");
			rete2.getBoard(users.get(0)).createCategory(categ.get(1), "WonderfulCats");
			List<MyData> a;
			
			rete2.getBoard(users.get(0)).put("WonderfulCats", data.get(0), data.get(0).getCategory());
			rete2.getBoard(users.get(0)).put("WonderfulCats", data.get(1), data.get(1).getCategory());
			rete2.getBoard(users.get(0)).put("WonderfulCats", data.get(8), data.get(8).getCategory());
			rete2.getBoard(users.get(0)).put("WonderfulCats", data.get(9), data.get(9).getCategory());
			rete2.getBoard(users.get(0)).put("WonderfulCats", data.get(11), data.get(11).getCategory());
			
			rete2.getBoard(users.get(0)).addFriend(categ.get(0), "WonderfulCats", users.get(1));
			rete2.getBoard(users.get(0)).addFriend(categ.get(1), "WonderfulCats", users.get(1));
			rete2.getBoard(users.get(0)).addFriend(categ.get(0), "WonderfulCats", users.get(2));
			
			rete2.getBoard(users.get(0)).insertLike(users.get(1), data.get(0));
			rete2.getBoard(users.get(0)).insertLike(users.get(1), data.get(1));
			rete2.getBoard(users.get(0)).insertLike(users.get(1), data.get(8));
			rete2.getBoard(users.get(0)).insertLike(users.get(2), data.get(8));
			
		
			System.out.println("\n\nBACHECA DI " + users.get(0));
			
			System.out.println("\nDati CONDIVISI CON " + users.get(1));
			it = rete2.getBoard(users.get(0)).getFriendIterator(users.get(1));
			while(it.hasNext()) {
				it.next().display();		
			}
			
			System.out.println("\nDATI nella bacheca: \n");
			
			it = rete2.getBoard(users.get(0)).getIterator("WonderfulCats");
			while(it.hasNext()) {
				tmp = it.next();
				System.out.println("LIKES: " + rete2.getBoard(users.get(0)).getNumLikes("WonderfulCats", tmp));
				tmp.display();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// qualche operazione sulla seconda implementazione
	}
}
