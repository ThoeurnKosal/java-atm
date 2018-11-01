import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Login1 {

	public void Log() {
		do {
			try {
				int databaseCardNumber = 0;
				int databasepinCode = 0;
				// input number
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter Card Number : ");
				int CardNumber = sc.nextInt();
				System.out.println("Enter pin code");
				int pinCode = sc.nextInt();

				// get connection to database
				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/atm?useSSL=false", "root",
						"");

				// Create Statement

				Statement myStm = myConn.createStatement();
				String SQl = "SELECT `card_number`,`pin` FROM  `useraccount` WHERE `card_number` = "
						+ CardNumber + " AND `pin` = " + pinCode;

				// Execute SQL Query

				ResultSet rs = myStm.executeQuery(SQl);
				//int a = 1;
				
			
				

				while (rs.next()) {
					databaseCardNumber = rs.getInt("card_number");
					databasepinCode = rs.getInt("pin");
				}

				if (CardNumber == databaseCardNumber
						&& pinCode == databasepinCode) {
					System.out.println("Successful Login!\n----");
					// System.out.println("input string : ");
					// String a = sc.next();
				} else {
					System.out.println("Incorrect Password\n----");
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		} while (true);
	}
}
