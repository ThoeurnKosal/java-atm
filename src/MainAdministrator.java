import java.sql.*;
import java.util.Scanner;


public class MainAdministrator {
public static void main(String[] args) {
	try {
		
	    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false","root","");
		Scanner sc = new Scanner(System.in);
	    int index = 0;
	    do {
			System.out.println("***System Administrator***\n");
			System.out.println("What will you like to do? ");
			System.out.println("++++++++++++++++++++++++++++++");
			System.out.println("+++ 1.Create user account  +++");
			System.out.println("+++ 2.Exit                 +++");
			System.out.println("++++++++++++++++++++++++++++++");
			System.out.printf("Please choose an option : ");
			index = sc.nextInt();
			while(!sc.hasNext()){
				sc.next();
				System.out.printf("Please choose an option : ");
			}
			switch (index) {
			case 1:
				System.out.printf("Please input Card Numeber (8 digits) : ");
				while(!sc.hasNext()){
					sc.next();
					System.out.printf("Please input number!\n Please input Card Numeber (8 digits) : ");
				}
				int cardNumber = sc.nextInt();
				System.out.printf("Please input pin (4 digits) :");
				while(!sc.hasNext()){
					sc.next();
					System.out.printf("Please input number!\n Please input pin (4 digits) : ");
				}
				int pinCode = sc.nextInt();
				System.out.printf("Please input first name :");
				String firstName = sc.next();
				System.out.println("please input last name :");
				String lastName = sc.next();
				Statement stm = myConn.createStatement();				
				String query = "INSERT INTO useraccount ( card_number, pin, first_name, last_name, balance) VALUES ("+cardNumber+", "+pinCode+", '"+firstName+"', '"+lastName+"', 5)";
				stm.executeUpdate(query);
				System.out.println("Done!");
				System.out.println("You have  Create 1 account !");			
				break;
			case 2:
				System.exit(0);
			}
	    } while (index<=2);
		
	} catch (Exception exc) {
		// TODO: handle exception
		exc.printStackTrace();
	}
}
}
