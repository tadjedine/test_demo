package version1_exo1;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale.Category;

public class Main {
    
    public static void main(String[] args) {
        
        // Création d'une instance de l'agent
        Agent agent = new Agent();

        // Création de quelques livres
        Book book1 = new Book("The Catcher in the Rye", Category.Fiction);
		System.out.println("Book title : " + book1.title + "\n ID : " + book1.id);

        Book book2 = new Book("To Kill a Mockingbird", Category.Novel);
		System.out.println("Book title : " + book2.title + "\n ID : " + book2.id);
        
        // Création de copies pour les livres
        Copy copy1_1 = new Copy(book1);
        Copy copy1_2 = new Copy(book1);
        Copy copy2_1 = new Copy(book2);

        //Ajout des livres avec ses copies
		Library.bks.add(book1);
        Library.bks.add(book2);

        // Création d'un utilisateur régulier (ahmed) et d'un utilisateur VIP (sara)
        RegularUser ahmed = new RegularUser("Ahmed", "User", Date.valueOf(LocalDate.of(2000, 1, 1)));
        VIPUser sara = new VIPUser("Sara", "VIP", Date.valueOf(LocalDate.of(1995, 5, 10)));
        Library.users.add(ahmed);
        Library.users.add(sara);

        // Ahmed veut emprunter un livre
        ahmed.Borrow("The Catcher in the Rye");

        // Sara veut emprunter un livre
        sara.Borrow("To Kill a Mockingbird");

        // Le processus de l'agent peut être lancé ici
        agent.launchLoan();
        agent.ProcessEndofDay();

    }
    
}

