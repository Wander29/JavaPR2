import java.util.*;

//	@author	 Venturi Ludovico

public class Main {

	public static void main(String[] args) {
		
		//per utilizzare l'altra Implementazione 
			// fare un find & replace di " Board2" con " Board22" (spazi compresi)
		
		/********************************************
		*	 SCEGLIERE L' IMPLEMENTAZIONE di Board2			
		/********************************************/
		
		// creo una rete == socialNetwrok
		MyNetwork<MyData, Board2<MyData>> rete = new MyNetwork<>();
		
		// TEST 
		// creo una lista di utenti (che userò anche come amici)
		ArrayList<String> users = new ArrayList<>();
		users.add("Ludovico"); users.add("Pietro"); users.add("Marco"); users.add("Emanuele");
		
		ArrayList<String> categ = new ArrayList<>();
		categ.add("Gattini"); categ.add("Moto"); categ.add("Videogiochi");
		
		// inserisco anche un'altra bacheca, che testerò alla fine
		System.out.println("1) password minore di 8 caratteri: ");
		try { 
			
			rete.addUser(users.get(0), new Board2<MyData>("passwordSuperStrong"));
			rete.addUser(users.get(1), new Board2<MyData>("test"));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		// creo un'altra bacheca
		rete.addUser(users.get(1), new Board2<MyData>("password01"));
		
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
		
		// provo inizialmente una sola bacheca, testando tutti i metodi compiendo sia azioni corrette
		// che sbagliate: queste ultime sono sempre le ultime in ogni blocco try-catch
		
		System.out.println("\n2) get di una bacheca non presente: ");
		try { 
			rete.getBoard(users.get(2));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n3) password errata: ");
		try { 
			rete.getBoard(users.get(0)).createCategory(categ.get(0), "passwordSuperStrong");
			rete.getBoard(users.get(0)).createCategory(categ.get(2), "passwordSuperStrong");
			rete.getBoard(users.get(0)).createCategory(categ.get(1), "passwordNotSoStrong");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (WrongPasswordException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n4) stringa nulla passata (vale per tutti i metodi): ");
		try { 
			rete.getBoard(users.get(0)).createCategory(null, "passwordSuperStrong");
		} catch (NullPointerException e) {
			System.out.println("Uno dei dati inseriti è nullo, operazione fallita");
		}
		
		System.out.println("\n5) aggiunta di una categoria già presente: ");
		try {
			rete.getBoard(users.get(0)).createCategory(categ.get(0), "passwordSuperStrong");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\n6) rimozione di una categoria non presente: ");
		try { 
			rete.getBoard(users.get(0)).removeCategory(categ.get(1), "passwordSuperStrong");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n7) condivisione di una stessa categoria con uno stesso amico: ");
		try { 
			rete.getBoard(users.get(0)).addFriend(categ.get(0), "passwordSuperStrong", users.get(1));
			rete.getBoard(users.get(0)).addFriend(categ.get(2), "passwordSuperStrong", users.get(1));
			rete.getBoard(users.get(0)).addFriend(categ.get(0), "passwordSuperStrong", users.get(2));
			rete.getBoard(users.get(0)).addFriend(categ.get(2), "passwordSuperStrong", users.get(2));
			rete.getBoard(users.get(0)).addFriend(categ.get(0), "passwordSuperStrong", users.get(1));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\n8) condivisione di una categoria non presente nella bacheca: ");
		try { 
			rete.getBoard(users.get(0)).addFriend(categ.get(1), "passwordSuperStrong", users.get(1));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n9) rimozione di un amico non presente nella lista amici: ");
		try { 
			rete.getBoard(users.get(0)).removeFriend(categ.get(0), "passwordSuperStrong", users.get(1));
			rete.getBoard(users.get(0)).removeFriend(categ.get(0), "passwordSuperStrong", users.get(3));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n10) rimozione di un amico da una categoria cui non ha accesso, anche se presente nella lista amici: ");
		try { 	
			rete.getBoard(users.get(0)).removeFriend(categ.get(0), "passwordSuperStrong", users.get(1));
		} catch (HiddenCategoryException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n11) inserimento di un dato già presente: ");
		try { 
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(0), data.get(0).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(1), data.get(1).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(5), data.get(5).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(6), data.get(6).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(0), data.get(0).getCategory());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n12) inserimento di un dato la cui categoria non è presente in bacheca: ");
		try { 
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(8), data.get(8).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(9), data.get(9).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(10), data.get(10).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(12), data.get(12).getCategory());
			rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(3), data.get(3).getCategory());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\n13) get di un dato la cui categoria non è presente: ");
		try {
			rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(3));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n14) get di un dato non presente: ");
		try { 
			rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(0));
			rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(2));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n15) get di un dato precedentemente inserito in modo corretto ma la cui categoria è stato poi rimossa: ");
		try { 	
		rete.getBoard(users.get(0)).createCategory(categ.get(1), "passwordSuperStrong");
		rete.getBoard(users.get(0)).put("passwordSuperStrong", data.get(4), data.get(4).getCategory());
		rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(4));
		rete.getBoard(users.get(0)).removeCategory(categ.get(1), "passwordSuperStrong");
		rete.getBoard(users.get(0)).get("passwordSuperStrong", data.get(4));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n16) rimozione di un dato non presente: ");
		try { 
			rete.getBoard(users.get(0)).remove("passwordSuperStrong", data.get(0));
			rete.getBoard(users.get(0)).remove("passwordSuperStrong", data.get(2));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n17) getDataCategory e modifica della lista ritornata: ");
		try { 
			rete.getBoard(users.get(0)).getDataCategory("passwordSuperStrong", categ.get(0)).add(data.get(0));
		} catch (UnsupportedOperationException e) {
			System.out.println("Operazione NON supportata");
		}
		
		System.out.println("\n18) amico vuole inserire like ad un dato di una categoria non condivisa con lui: ");
		try { 
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
		
		System.out.println("\n19) amico vuole inserire like ad un dato cui lo ha già messo: ");
		try { 
			rete.getBoard(users.get(0)).insertLike(users.get(3), data.get(6));
		} catch (DuplicateLikeException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("\n20) amico vuole inserire like ad un dato non presente: ");
		try { 
			rete.getBoard(users.get(0)).insertLike(users.get(3), data.get(7));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		//ITERATORI
		System.out.println("\n(ITERATORI)\n");	
		System.out.println("\nDATI nella bacheca di " + users.get(0) + ": \n");
		
		Iterator<MyData> it = rete.getBoard(users.get(0)).getIterator("passwordSuperStrong");
		MyData tmp;
		while(it.hasNext()) {
			tmp = it.next();
			System.out.println("LIKES: " + rete.getBoard(users.get(0)).getNumLikes("passwordSuperStrong", tmp));
			tmp.display();
		}
		
		System.out.println("\n---->Dati CONDIVISI CON " + users.get(2));
		it = rete.getBoard(users.get(0)).getFriendIterator(users.get(2));
		while(it.hasNext()) {
			it.next().display();		
		}
		
		System.out.println("\n->elimino una categoria e itero sui dati di tale categoria tramite un amico con cui essa era condivisa: ");
		try { 
			rete.getBoard(users.get(0)).removeCategory(categ.get(0), "passwordSuperStrong");
			
			System.out.println("\nDati CONDIVISI CON " + users.get(2));
			it = rete.getBoard(users.get(0)).getFriendIterator(users.get(2));
			while(it.hasNext()) {
				it.next().display();		
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
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
			
			System.out.println("\n### BACHECA 2)");
			System.out.println("\nDATI nella bacheca di " + users.get(1) + ": \n");
			
			it = rete.getBoard(users.get(1)).getIterator("password01");
			while(it.hasNext()) {
				tmp = it.next();
				System.out.println("LIKES: " + rete.getBoard(users.get(1)).getNumLikes("password01", tmp));
				tmp.display();
			}
			
			System.out.println("\n---->Dati CONDIVISI CON " + users.get(2));
			it = rete.getBoard(users.get(1)).getFriendIterator(users.get(2));
			while(it.hasNext()) {
				it.next().display();		
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
