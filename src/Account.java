import java.sql.Connection;
import java.sql.DriverManager;


public class Account {

	private double balance;
	private double databaseBalance;
	
	// constructor
	
	public Account(double  balance){
		
		this.balance = balance;
		
	}
	
	// method check balance
	
	public void checkBalance(){
		try {
			// connection to database
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false","root","");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
