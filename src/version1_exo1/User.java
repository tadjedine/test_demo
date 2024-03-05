package version1_exo1;

import java.sql.Date;

enum PlanSubsription {
	basic_plan,
	vip_plan,
}


class RegularUser extends User {
	  final static int Fine_delay_regular_user = 2000; // I chose a fine randomly

     RegularUser(String firstname,String lastname,Date birthDate){

		super( firstname, lastname, birthDate);
		this.plan= basic_plan;	
	 }

	
	
}

class VIPUser extends User {

    static int Fine_delay_vip_user = 1500;
	VIPUser(String firstname,String lastname,Date birthDate){

	  super( firstname, lastname, birthDate);
	  this.plan = PlanSubsription.vip_plan;
	}
}

public class User {
	static int count = 0;
	protected int id; 
	protected String firstname,lastname;
	protected Date birthdate;
	protected float debt;
	protected PlanSubsription plan;
	protected int borrowed_books;
	
	public User(String firstname,String lastname,Date birthDate){
		this.id = count;
		count++;
		this.firstname=firstname;
		this.lastname= lastname;
		this.birthdate=birthDate;
		this.borrowed_books=0;
	}
	
	public void Borrow(String bookName) {
		// find the books first and then launch borrow demand, we will suppose the name is correct..

	    Book book = Library.FindBooks(bookName); // searching for the book
		if ( book!= null && book.copyCount > 0){ 
		  for(Copy copy : book.copies){         // checking copies availability
			if (copy.available) {
				Library.registerBorrowDemand(this,bookName);
				return;	
			}
		  }
		   System.out.println("All copies are unavailable");
		   return;
		}
		System.out.println("No match for this Title");
	}
	
	public void findBookPerTitle(String booktitle) {
		
		for (Book book : Library.bks) {
            if (book.title.toLowerCase().contains(booktitle.toLowerCase())) {
              System.out.println(" (ID: " + book.id + ").....Number of copies : " + book.copyCount );
			  return;
            }
        }
		System.out.println("No match");
	    
	}
	
	public void findBooksPerCategory(Category category) {
		// show all books that have the category given
	  boolean found= false; 
        for (Book book : Library.bks) {
            if (book.category == category) {
				found=true;
              System.out.println(" Title : " + book.title + ".....(Number of copies : " + book.copyCount + ")" );
            }
        }
		if(!found){
			System.out.println("No match");
		} 	
	}

	public User getUserById(int userId) {
		for (User user : Library.users) {
			if (user.id == userId) {
				return user;
			}
		}
		return null;
	}
	// To adjust the number of borrowed books
	   public void AddBook(){
		  borrowed_books++;
	   }
	   public void DecBook(){
		borrowed_books--;
	   }
	
}
