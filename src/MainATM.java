import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;
public class MainATM {
	public static void main(String[] args) {
		do {
			try {
				
				int databaseCardNumber = 0;
				int databasepinCode = 0;
				// input number
				
				System.out.println("*** Welcom to ATM System ***\n");
				Scanner sc = new Scanner(System.in);
				System.out.printf("Enter Card Number : ");
				while(!sc.hasNextInt()){
					sc.next();
					System.out.printf("Please input only number! \nEnter Card Number : ");
				}
				int CardNumber = sc.nextInt();				
				System.out.printf("Enter pin code : ");
				while(!sc.hasNextInt()){
					sc.next();
					System.out.printf("Please input only number \nEnter pin code : ");					
				}
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

				while (rs.next()) {
					databaseCardNumber = rs.getInt("card_number");
					databasepinCode = rs.getInt("pin");
				}
				
				// Test Card number and pin code
				
				if (CardNumber == databaseCardNumber
						&& pinCode == databasepinCode) {
//					System.out.println("Successful Login!\n----");
						ResultSet showName = myStm.executeQuery("select * from useraccount where card_number = "+CardNumber);
//						System.out.println("++++++++++++++++++++++++++++++++");
						while(showName.next()){
						System.out.println("\nWelcome Back "+showName.getString("first_name")+" "+showName.getString("last_name"));
//						System.out.println("++++++++++++++++++++++++++++++++");
						}
//							*********************************
//							  Loop for choose the program 
//							*********************************
							int option=0;
							do{
								// option choose the program
								System.out.println("\nWhat will you like to do?");
								System.out.println("+++++++++++++++++++++++++");
								System.out.println("+++ 1. Check balance  +++");
								System.out.println("+++ 2. Withdraw       +++");
								System.out.println("+++ 3. Deposit        +++");
								System.out.println("+++ 4. Tranfer        +++");
								System.out.println("+++ 5. Exit           +++");
								System.out.println("+++++++++++++++++++++++++");
								System.out.printf("\nPlese choose an option : ");
								while(!sc.hasNextInt()){
									sc.next();
									System.out.printf("Please input only number! \nPlese choose an option : ");
								}
								option = sc.nextInt();
								switch (option) {
								
//								*****************
//								// Check balance
//								*****************
								
								case 1:
										ResultSet showBalance = myStm.executeQuery("select * from useraccount where card_number = "+CardNumber);
										while(showBalance.next()){
										System.out.println("\nYour Name : "+showBalance.getString("first_name")+" "+showBalance.getString("last_name"));
										System.out.println("Your Current Balance is : "+"$"+showBalance.getDouble("balance"));
										}
									break;
									
									
//								   ***************
//								// WithDraw Money
//								   ***************
									
									
								case 2:
										ResultSet withDrawBalance = myStm.executeQuery("select * from useraccount where card_number = "+CardNumber);
										while(withDrawBalance.next()){
										System.out.println("\nYour Current Balance is : "+"$"+withDrawBalance.getDouble("balance"));
										System.out.printf("Enter the amount to withdraw : ");
										while(!sc.hasNextDouble()){
											sc.next();
											System.out.printf("Please input only number! \nEnter the amount to withdraw : ");
										}
										double withdrawMoney = sc.nextDouble();
										
										// Test balance maximum
										
				 						if(withdrawMoney<=withDrawBalance.getDouble("balance") && withdrawMoney > 0){
				 							
				 							// Update balance in database 
				 							
				 							double currentBalance = withDrawBalance.getDouble("balance")-withdrawMoney;
				 							String queryUdateBlance = "update useraccount set balance = "+currentBalance + "where card_number="+CardNumber;
											PreparedStatement psb = myConn.prepareStatement(queryUdateBlance);
											psb.executeUpdate(queryUdateBlance);
				 							
				 							
											int index = 0;
											// Ask Customer to Print invoice or not
											do{
//											System.out.println(" Done!");
											System.out.println("Do you want to print your invoice?");
											System.out.println(" 1. YES ");
											System.out.println(" 2. NO ");
											System.out.printf("Please choose an option : ");
											while(!sc.hasNextInt()){
												sc.next();
												System.out.printf("Please input only number! \nPlease choose an option : ");
											}
											index = sc.nextInt();
											
												switch (index) {
												// user input YES
												case 1:
													Date date = new Date();
													System.out.println(date.toString());
													System.out
															.println("Your Name : " +withDrawBalance.getString("first_name")+" "+withDrawBalance.getString("last_name"));
													System.out
															.println("You have Withdraw money : $"+withdrawMoney);
													System.out
															.println("Your current balance is : $"+currentBalance);
													break;
												case 2:
													System.out
															.println("---Thank You!---");
													break;
												default:
													System.out
															.println("---Please input Again!---");
//															*************************
//																Loop Again
//															*************************
													
														
//														System.out.println(" Done!");
														System.out.println("Do you want to print your invoice?");
														System.out.println(" 1. YES ");
														System.out.println(" 2. NO ");
														System.out.printf("Please choose an option : ");
														while(!sc.hasNextInt()){
															sc.next();
															System.out.printf("Please input only number \nPlease choose an option : ");
														}
														index = sc.nextInt();
														
															switch (index) {
															// user input YES
															case 1:
																date = new Date();
																System.out.println(date.toString());
																System.out
																		.println("Your Name : " +withDrawBalance.getString("first_name")+" "+withDrawBalance.getString("last_name"));
																System.out
																		.println("You have Withdraw money : $"+withdrawMoney);
																System.out
																		.println("Your current balance is : $"+currentBalance);
																break;
															case 2:
																System.out
																		.println("---Thank You!---");
																break;
															default:
																System.out
																		.println("---please input again!---");
																break;
//																		
															}
															break;
														
													//break; // break default case
													
												}		
						
											} while(index>1&&index>2);
										} // close if (test balance)
										else{
											System.out.println("---Error please try again!---");
										}
				 						
										}
										break;
										
//								****************
//								Deposit Balance
//								****************
								case 3:
									ResultSet depositBalance = myStm.executeQuery("select * from useraccount where card_number = "+CardNumber);
									while(depositBalance.next()){
										System.out.println("Your Current Balance is : "+"$"+depositBalance.getDouble("balance"));
										System.out.printf("Please input amount the money to Deposit : ");
										while(!sc.hasNextDouble()){
											sc.next();
											System.out.printf("Please input only number! \nPlease input amount the money to Deposit  : ");
										}
										double moneyDeposit = sc.nextDouble();
										if(moneyDeposit > 0){
										double balanlceAfterDeposit = moneyDeposit + depositBalance.getDouble("balance");
//										String queryUdateBlance = "update useraccount set balance = "+currentBalance + "where card_number="+CardNumber;
//										PreparedStatement psb = myConn.prepareStatement(queryUdateBlance);
//										psb.executeUpdate(queryUdateBlance);
										String queryDeposit = "update useraccount set balance = "+balanlceAfterDeposit+ "where card_number="+CardNumber;
										PreparedStatement preSt = myConn.prepareStatement(queryDeposit);
										preSt.executeUpdate(queryDeposit);
										
										int index = 0;
										// Ask Customer to Print invoice or not
										do{
//										System.out.println(" Done!");
										System.out.println("Do you want to print your invoice?");
										System.out.println(" 1. YES ");
										System.out.println(" 2. NO ");
										System.out.printf("Please choose an option : ");
										while(!sc.hasNextInt()){
											sc.next();
											System.out.printf("Please input only number!\nPlease choose an option : ");
										}
										index = sc.nextInt();
										
											switch (index) {
											// user input YES
											case 1:
												Date date = new Date();
												System.out.println(date.toString());
												System.out
														.println("\nYour Name : " +depositBalance.getString("first_name")+" "+depositBalance.getString("last_name"));
												System.out
														.println("You have deposit money : $"+moneyDeposit);
												System.out
														.println("Your current balance is : $"+balanlceAfterDeposit);
												break;
											case 2:
												System.out
														.println("\n---Thank You!---");
												break;
											default:
												System.out
														.println("---Please input Again!---");
//														*************************
//															Loop Again
//														*************************
												
//													System.out.println(" Done!");
													System.out.println("Do you want to print your invoice?");
													System.out.println(" 1. YES ");
													System.out.println(" 2. NO ");
													System.out.printf("Please choose an option : ");
													while(!sc.hasNextInt()){
														sc.next();
														System.out.printf("Please input only number! \nPlease choose an option : ");
													}
													index = sc.nextInt();
													
														switch (index) {
														// user input YES
														case 1:
															date = new Date();
															System.out.println(date.toString());
															System.out
																	.println("\nYour Name : " +depositBalance.getString("first_name")+" "+depositBalance.getString("last_name"));
															System.out
																	.println("You have deposit money : $"+moneyDeposit);
															System.out
																	.println("Your current balance is : $"+balanlceAfterDeposit);
															break;
														case 2:
															System.out
																	.println("\n---Thank You!---");
															break;
														default:
															System.out
																	.println("---Please input again!---");
															break;
//																	
														}
														break;
												
												//break; // break default case
											
											}	
										
					
										} while(index>1&&index>2);
										
										} // close if (moneydeposit test)
										else {
											System.out.println("Error please try again!");
										}
										
									}
									break;
									
									
//									****************
//									Transfer balance
//									****************
									
									
								case 4:				
									
									ResultSet tranferBalance = myStm.executeQuery("select * from useraccount where card_number = "+CardNumber);
									while(tranferBalance.next()){
									System.out.println("\nYour Current Balance is : "+"$"+tranferBalance.getDouble("balance"));
									System.out.printf("input the money to transfer ($) : ");
									while(!sc.hasNextDouble()){
										sc.next();
										System.out.printf("Please input only number! \nEnter the amount to transfer : ");
									}
									double amountTransfer = sc.nextDouble();
									if(amountTransfer<=tranferBalance.getDouble("balance") && amountTransfer > 0){
										System.out.printf("Please enter card number account that you want to transfer : ");
										while(!sc.hasNextInt()){
											sc.next();
											System.out.printf("Please input only number! \nEnter the amount to transfer : ");
										}
										int cardnumberAccount = sc.nextInt();
										String query = "select * from useraccount where card_number = "+cardnumberAccount;
										tranferBalance = myStm.executeQuery(query);
										int  accCardNumber = 0;
										while(tranferBalance.next()){
											accCardNumber = tranferBalance.getInt("card_number");
										
										if(accCardNumber==cardnumberAccount){
											
											
											//tranferBalance = myStm.executeQuery("select * from useraccount where card_number = "+cardnumberAccount);
											
											double moneyGetAfterTranfer = amountTransfer + tranferBalance.getDouble("balance");
											String queryAfterUdateBlance = "update useraccount set balance = "+moneyGetAfterTranfer + "where card_number="+cardnumberAccount;
											PreparedStatement psb = myConn.prepareStatement(queryAfterUdateBlance);
											psb.executeUpdate(queryAfterUdateBlance);
											
											
											String queryTranferMoneyAccount = "select * from useraccount where card_number = "+CardNumber;
											tranferBalance = myStm.executeQuery(queryTranferMoneyAccount);
											while(tranferBalance.next()){
												double moneyRemain = tranferBalance.getDouble("balance") - amountTransfer;
												String queryRemainBalane ="update useraccount set balance = "+moneyRemain + "where card_number = "+CardNumber;
												PreparedStatement preSt = myConn.prepareStatement(queryRemainBalane);
												preSt.executeUpdate(queryRemainBalane);
											}
											

											int index = 0;
											// Ask Customer to Print invoice or not
											do{
//											System.out.println(" Done!");
											System.out.println("Do you want to print your invoice?");
											System.out.println(" 1. YES ");
											System.out.println(" 2. NO ");
											System.out.printf("Please choose an option : ");
											while(!sc.hasNextInt()){
												sc.next();
												System.out.printf("Please input only number! \nPlease choose an option : ");
											}
											index = sc.nextInt();
											
												switch (index) {
												case 1:
													Date date = new Date();
													System.out
															.println(date.toString());
													String queryAccGetMoney = "select * from useraccount where card_number ="+cardnumberAccount;
													tranferBalance = myStm.executeQuery(queryAccGetMoney);
													while(tranferBalance.next()){
														System.out
																.println("You have transfer $"+amountTransfer +" to" + " "+tranferBalance.getString("first_name")+" "+tranferBalance.getString("last_name")+" Card Number : "+tranferBalance.getInt("card_number"));
													}
													String queryAccSendMoney = "select * from useraccount where card_number ="+CardNumber;
													tranferBalance = myStm.executeQuery(queryAccSendMoney);
													while(tranferBalance.next()){
														System.out
																.println("Your current balance is $"+tranferBalance.getDouble("balance"));
													}
													break;
												case 2:
														System.out
																.println("---Thank You!---");
													break;
												default:
													System.out
															.println("---Please input Again!---");
													System.out.println("Do you want to print your invoice?");
													System.out.println(" 1. YES ");
													System.out.println(" 2. NO ");
													System.out.printf("Please choose an option : ");
													while(!sc.hasNextInt()){
														sc.next();
														System.out.printf("Please input only number! \nPlease choose an option : ");
													}
													index = sc.nextInt();
														switch (index) {
														case 1:
															 date = new Date();
															System.out
																	.println(date.toString());
															 queryAccGetMoney = "select * from useraccount where card_number ="+cardnumberAccount;
															tranferBalance = myStm.executeQuery(queryAccGetMoney);
															while(tranferBalance.next()){
																System.out
																		.println("You have transfer $"+amountTransfer +" to" + " "+tranferBalance.getString("first_name")+" "+tranferBalance.getString("last_name")+" Card Number : "+tranferBalance.getInt("card_number"));
															}
															 queryAccSendMoney = "select * from useraccount where card_number ="+CardNumber;
															tranferBalance = myStm.executeQuery(queryAccSendMoney);
															while(tranferBalance.next()){
																System.out
																		.println("Your current balance is $"+tranferBalance.getDouble("balance"));
															}
															break;
														case 2:
																System.out
																		.println("---Thank You!---");
															break;
														default:
															System.out
																	.println("---Please input again!---");
															break;
														
														}
														break;										
												}
												break;
												
											} while(index>1&&index>2); 
											
										   //System.out.println("yes");
											//double moneyGetAfterTranfer = amountTransfer + tranferBalance.getDouble("balance");
//											String queryUdateBlance = "update useraccount set balance = "+currentBalance + "where card_number="+CardNumber;
//											PreparedStatement psb = myConn.prepareStatement(queryUdateBlance);
//											psb.executeUpdate(queryUdateBlance);
//											String queryUpdateAfterTransfer = "update useraccount set balance = "+moneyGetAfterTranfer+"where card_number="+cardnumberAccount;
//											PreparedStatement ppst = myConn.prepareStatement(queryUpdateAfterTransfer);
//											ppst.executeUpdate(queryUpdateAfterTransfer);
											
											
//											ResultSet amountAfterTranfer = myStm.executeQuery("select * from useraccount where card_number = "+CardNumber);
//											double balanceInAccount = 0;
//											double senderCurrentBlance = 0 ;
//											while(amountAfterTranfer.next()){
//												balanceInAccount = amountAfterTranfer.getDouble("balance");
//											}
//											senderCurrentBlance = balanceInAccount - amountTransfer;
											
											
										}// close if test card number equal or not 
										else{
											System.out.println("---You input wrong card number please try again!---");
										}// close else test card number equal or not
											
										}
										
									//break;
									} // close if test balance 
									else{
										System.out.println("---Error please try again!---");
									} // close else test balance 
										
										
										
									}// close while(tranferBalance.next())
									break;
								case 5:
//									System.out.printf("Enter new pin code : ");
//									while(!sc.hasNextInt()){
//										sc.next();
//										System.out.printf("Please input number!\nEnter new pin code : ");
//									}
//									int newPin = sc.nextInt();
//									System.out.printf("Confirm pin code : ");
//									while(!sc.hasNextInt()){
//										sc.next();
//										System.out.printf("Please input number!\nConfirm pin code : ");
//									}
//									int confirmPin = sc.nextInt();
//									ResultSet ChagePinCode = myStm.executeQuery("select * from useraccount where card_number = "+CardNumber);
//									if(newPin == confirmPin){	
//											while(ChagePinCode.next()){	
//											String queryChangePin ="update useraccount set pin = "+confirmPin+ "where card_number = "+CardNumber;
//											PreparedStatement preSt = myConn.prepareStatement(queryChangePin);
//											preSt.executeUpdate(queryChangePin);
//											System.out.println("Your pin code has chaged!");
//											}
//											
//								//	String querySelectPin
//									}
//									else{
//										System.out.println("Your pin is not confirm!");
//									}
									System.exit(0);
									break;
								} // Close Big switch
							}
							while(option<=5);				
				} else {
					System.out.println("Incorrect Card Number or Pin Code\n----");
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		} while (true);
	}
}
