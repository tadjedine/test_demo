package version1_exo1;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import javax.lang.model.type.NullType;


enum Category {

		Fiction(1),
		Comics(2),
		BioMemo(3),
		Religion(4),
		ArtPhotography(5),
		Health(6),
		History(7),
		Novel(8);

	  protected final int Catnumber;

      Category(int Catnumber){
		this.Catnumber = Catnumber;
	  }
     // return the number associated to the category
	 int getnumber(){
		return this.Catnumber;
	 }
}

class Resource {
	protected String id;
}

abstract class Book extends Resource {
	protected int copyCount = 0;
	protected String title; 
	protected Category category; 
	protected ArrayList<string> Authors;
	protected ArrayList<Copy> copies;
	
	public Book() {
		this.title = "None";
		this.id = "-1";
		
	}
	
	public Book(String title,Category category/*other fields of books*/) {
		Library.Bcount++;
		this.id= this.BookIDgenerator();
		this.title = title;
		this.category = category;
		this.Authors = new ArrayList<string>();
		this.copies = new ArrayList<Copy>();
	}

   // To add the author(s) of the book
    public void AddAuthors(int n){
		string name;
        Scanner scanner = new Scanner(System.in);
		 for(i=1; i<= n ; i++){
			System.out.println("Please enter the name of the author" + i + " : ");
			name = scanner.nextLine();
			this.Authors.add(name);
		 }
	}
	//generate an ID for a Book
	public string BookIDgenerator(){
		id= " B";
		id= id.concat(String.valueOf(Library.Bcount));
		id= id.concat("C");
		id= id.concat(this.category.getnumber());
	  return id;
	}  

}

class Copy extends Book {
	
	protected boolean available;
	public Copy(Book bk){
		bk.copyCount++;
		this.id = Library.SystemNaming(bk.id,bk.copyCount,"Copy",bk.category); 
		this.title = bk.title;
		this.category = bk.category;
		this.available = true;
		bk.copies.add(this);
	}
}


class Operation {
   
}

class Loan extends Operation {
	static int loan_count=0;
	protected int id; 
	protected String copy_loaned;
	protected int user_borrower;
	protected LocalDate start_loan;
	
	public Loan(Copy cp,User user){
		this.id= loan_count;
		loan_count++;
		this.copy_loaned = cp.id;
		this.user_borrower = user.id;
		cp.available = false;
		user.AddBook();
		start_loan = LocalDate.now();
	}
	
	@Override
    public String toString() {
        return "Loan ID: " + id + "\nCopy Loaned: " + copy_loaned + "\nUser Borrower : " + getUserById(user_borrower) +
                "\nStart Loan Date: " + start_loan;
    }
}

class BorrowDemand extends Operation {
	protected String bookname;
    protected int borrowerID;
	protected LocalDate submit_demand;
	protected boolean treated;

     BorrowDemand(String title,int borrowerID){
	  bookname= title;
      this.borrowerID= borrowerID;  
	  submit_demand= LocalDate.now();
	  treated = false;
	}
	
	@Override
    public String toString() {
        return "Borrow Demand - Book: " + bookname + "\nBorrower ID: " + borrowerID +
                "\nSubmission Date: " + submit_demand + "\nTreated: " + treated;
    }
}

public class Library {
	static int Bcount = 0;
	static int OR=0; // Other Resources counter 
	static public ArrayList<Book> bks = new ArrayList<>();
	static public ArrayList<User> users = new ArrayList<>();
	static public ArrayList<Operation> Lib_operations = new ArrayList<>(); 
	
	static String SystemNaming(String ParentID,int numcopies,String resource,Category categ) {
	  String id;

		switch(resource) {
			case "Book" :
				id= " B";
				id= id.concat(String.valueOf(Bcount));
				id= id.concat("C");
				id= id.concat(categ.getnumber);
			    break;
			case "copy" :
			    id= " E";
				id= id.concat(String.valueOf(numcopies));
				id= id.concat(ParentID);
                break;
			default:
			    id=" OR";
				id= id.concat(String.valueOf(OR));	
				OR++;
		}
		return id;
	}

	 //function used to search books by its name
	static Book FindBooks(String booktitle) {
	  
		for (Book book : bks) {
            if (book.title.toLowerCase().contains(booktitle.toLowerCase())) {
                System.out.println(" This book does exist in our Linrary");
				return book;
            }
        }
		System.out.println("No match");
		return null;
	}
	
	public static void registerBorrowDemand(User user,String booktitle) {

		BorrowDemand bd = new BorrowDemand(booktitle, user);
		Lib_operations.add(bd);
	}


}
