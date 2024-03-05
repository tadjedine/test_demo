package version1_exo1;

import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Agent {
	
	public ArrayList<Operation> _operations; // there is two operations : borrow demand book , Loan book

	public void ProcessStartofDay() {
		// clean all queues...
	}
	
	public void ProcessEndofDay() {
		//this process will be launched at the end of every day
		displayOperationReport();
		CalculateDebtOfLoanedUser();
	}
	
	public void AddBook(Book bk){
		for (Book book : Library.bks) {
            if (book.title.toLowerCase().contains(bk.title.toLowerCase())) {
                System.out.println(" This book already exists in our Library");
				return;
            }
        }
		Library.bks.add(bk);

	}
	
	public void AddNewCopy(Book bk) {
		// add new copy to an existed book
		for (Book book : Library.bks) {
            if (book.title.toLowerCase().contains(bk.title.toLowerCase())) {
                Copy copy = new Copy(book);
				return;
            }
        }   
	}
	
	public void AddNewUser(String firstname,String lastname,Date birthdate ,PlanSubsription plansubs) {
		if(plansubs == PlanSubsription.basic_plan) {
			RegularUser user = new RegularUser( firstname, lastname, birthdate);
			Library.users.add(user);
		}	
		else{
			VIPUser user = new VIPUser(firstname, lastname, birthdate);
			Library.users.add(user);
		}
	}
	
	public void displayOperationReport() {
		// at the end of every day, agent need to display all operation (borrow demand, loan book) that happened in that day // toString
		LocalDate currentDate = LocalDate.now();
        
		for (Operation operation : _operations) {
			if (operation instanceof Loan && ((Loan) operation).start_loan.equals(currentDate)) {
				System.out.println(operation.toString());
				System.out.println("------------------------------");
			} else if (operation instanceof BorrowDemand && ((BorrowDemand) operation).submit_demand.equals(currentDate)) {
				System.out.println(operation.toString());
				System.out.println("------------------------------");
			}
		}
	}
	
	public void CalculateDebtOfLoanedUser () {
		// at the end of day, the agent need to launch the process to check if there is late users..
		// calculate the late fine of every user..
		for (Operation operation : _operations) {
			if (operation instanceof Loan) {
				Loan loan = (Loan) operation;
				LocalDate currentDate = LocalDate.now();
				LocalDate dueDate;
				int fine;
	
				// Find the corresponding User using the borrower's ID
				User borrower = getUserById(loan.user_borrower);
	
				if (borrower == null){
					System.out.println("Error: User with ID " + loan.user_borrower + " not found.");
					continue; // Skip this loan if the user is not found
				}
	
				if (borrower.plan == PlanSubsription.basic_plan) {
					// For regular users
					dueDate = loan.start_loan.plusWeeks(2);
					fine = RegularUser.Fine_delay_regular_user;
				} else {
					// For VIP users
					dueDate = loan.start_loan.plusMonths(1);
					fine = VIPUser.Fine_delay_vip_user;
				}
	
				if (currentDate.isAfter(dueDate)) {
					long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, currentDate);
					int totalFine = fine * (int) daysLate;
	
					// Update user's debt
					borrower.debt += totalFine;
	
					System.out.println("User ID : " + borrower.id + " is late in returning the book. Fine applied: " + totalFine);
				}
			}
		}
    }

	
	
	public void launchLoan() {
		// find if there is not treated borrow demands
		// check if it possible to launch this borrow demands : user.state (VIP or regular) and user.debt == 0 
		// then launch loan the book if it is all normal
		for (Operation operation : Library.Lib_operations){
			if (operation instanceof BorrowDemand ) {
				BorrowDemand bDemand= (BorrowDemand) operation;

				// checking if the demand is already treated
				if (!bDemand.treated){

					operation.treated=true; //the demand is considered as treated in case we launch a loan or not  
					// getting the user
					User user = getUserById(bDemand.borrowerID);

					// checking user's status
					if (user.debt == 0 && ((user instanceof VIPUser && user.borrowed_books < 5) || (user instanceof RegularUser && user.borrowed_books < 2))) {
						Book wanted_book = Library.FindBooks(bDemand.bookname);

						// checking copies' availability and loaning a precised copy
						for( Copy copy : wanted_book.copies ){
							if (copy.available) {
								// launching the Loan
								Loan loan = new Loan(copy,user);
								loan.toString(); 
								this._operations.add(loan);
								return;
						
							}
						}	
						
					} 
					this._operations.add(bDemand); // the loan didn't happen but the the agent treated this borrow demand
				}
					
			}
		}

	}
	
}
