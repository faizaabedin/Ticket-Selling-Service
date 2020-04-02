import java.util.*;

public class main {

	public static void main(String[] args) {

		// this is for seeting all the paths to files
		String currentUserFile = args[0];
		String availableTicketFile = args[1];
		String loginFile = args[2];
		String outputFile = args[3];

		// this is to read each file line and make objects and put them in lists
		ArrayList<User> userList = CommandConversion.readFromCurrentUserFile("currentUserFile.inp");
		ArrayList<Tickets> ticketsAvailableList = CommandConversion.readFromAvailableTicketsrFile(availableTicketFile);
		Queue commandStack = FileParser.readFile(loginFile);

		// this is new list 
		ArrayList<Tickets> newTicketList = new ArrayList<Tickets>();
		ArrayList<User> newUserList = new ArrayList<User>();

		// before going through print welcome
		System.out.println("Welcome");

		User user = new User("", 0, "");
		boolean isLoggedIn = false;
		int code = 00;
		Tickets tickets = new Tickets("", "", 0, 0);
		String buyerName = "";
		String sellerName = "";
		double credit = 0;

		// while not empty
		while (!commandStack.isEmpty()) {

			// read next command
			String nextCommand = commandStack.remove().toString().trim();

			// this is the switch cases for the commands
			switch (nextCommand) {

				case "login":

					// need to ask for a username
					System.out.println("Please enter username");

					// then read in the next line from commandStack - useanme
					String username = commandStack.remove().toString();

					// try to find this username in the userStack
					if (userList.stream().anyMatch(o -> o.getUsername().equals(username))) {

						// set the user to the user we found in list
						Optional<User> userLoggedIn = userList.stream().filter(c -> c.getUsername().equals(username))
								.findAny();
						user = userLoggedIn.get();

						// if yes then print login successful
						System.out.println("login successful");

						// set boolen loggin in
						isLoggedIn = true;

					} else {

						// means username not a user
						System.out.println("login failed not a username");
					}

					break;
				case "logout":

					// can logout only if loggin in
					if (isLoggedIn) {

						// set islogin boolean
						isLoggedIn = false;

						// write new users to file
						for (User userObj : newUserList) {
							CommandConversion.wirteToCurrentUserFile(userObj);
						}

						// write down all new tickets file
						for (Tickets ticketsObj : newTicketList) {
							CommandConversion.writeToAvailableTicketFile(ticketsObj);
						}

						// let user know
						System.out.println("logout succesful");

						// write to the dtf file
						CommandConversion.writeToDailyTransactiontFile(00, user, tickets, buyerName, sellerName,
								credit, outputFile);

					} else {

						// not loggin in so
						System.out.println("not logged in");
					}

					break;
				case "create":

					// check if loggin user is admin
					if (user.getType().equals("admin")) {

						// ask new user name
						System.out.println("Enter name for new user");
						String name = (String) commandStack.remove();

						// this is to check if already the username exists
						if (!(userList.stream().anyMatch(o -> o.getUsername().equals(name)))) {

							// ask new user type
							System.out.println("Enter user type");
							String type = (String) commandStack.remove();

							// ask new user name
							System.out.println("Enter user credit");
							credit = (double) commandStack.remove();

							// create the user obj
							User newUser = new User(name, credit, type);

							// add it to the userList
							newUserList.add(newUser);

							// let user know
							System.out.println("created user");

							// write out to the DFT file
							CommandConversion.writeToDailyTransactiontFile(01, newUser, tickets, buyerName, sellerName,
									credit, outputFile);
						} else {
							// this is to say usename is taken
							System.out.println("Username taken");
						}

					} else {
						// this is to say that the user name is taken
						System.out.println("Command only allowed as admin");
					}

					break;
				case "delete":

					// check if loggin user is admin
					if (user.getType().equals("admin")) {

						// ask user name
						System.out.println("Enter name of user to be deleted");
						String name = (String) commandStack.remove();

						// this is to check if already the username exists
						if (userList.stream().anyMatch(o -> o.getUsername().equals(name))) {

							// go through all the files
							for (User deleteUser : userList) {

								// find the user wih the username
								if ((deleteUser.getUsername()).equals(name)) {

									// delete the user with username
									userList.remove(deleteUser);
								}

								// write to the dtf file
								CommandConversion.writeToDailyTransactiontFile(02, deleteUser, tickets, buyerName,
										sellerName, credit, outputFile);

								break;
							}
						} else {

							// else this name does not exist
							System.out.println("Username does not exist");
						}
					} else {

						// means user not admin and connot process the command
						System.out.println("Command only allowed as admin");
					}

					break;
				case "sell":

					// only accepted when logged in any type of account except standard-buy.
					if (!(user.getType().equals("standard-buy"))) {

						// ask event name
						System.out.println("Enter name of event");
						String eventName = (String) commandStack.remove();

						// ask sales price
						System.out.println("Enter sale price for the ticket in dollars");
						double pricePerTicket = (double) commandStack.remove();

						// ask number of tickets
						System.out.println("Enter the number of tickets for sale");
						int numOfTickets = (int) commandStack.remove();

						double maxSalePrice = 999.99;
						int maxNumberOfTickets = 100;
						int maxEnventNameLen = 25;

						// check if its below the max price for tickes
						if (pricePerTicket <= maxSalePrice) {

							// check if num if tickets is below max
							if (numOfTickets <= 100) {

								// check if event name has below max character
								if (eventName.length() <= 25) {

									// sellername is the username that is logged in
									sellerName = user.getUsername();

									// create the event
									tickets = new Tickets(eventName, sellerName, numOfTickets, pricePerTicket);

									// addd it to the ticket new operarions can be done on new tickets
									newTicketList.add(tickets);

									// write to the dtf file
									CommandConversion.writeToDailyTransactiontFile(03, user, tickets, buyerName,
											sellerName, credit, outputFile);

								} else {

									// name is too long
									System.out.println("name has more character than maximun: 25");

								}
							} else {

								// else number of tickets is above max
								System.out.println("Number of tickets is above maximun: 100");

							}

						} else {

							// else price is higher so cannot accept the price
							System.out.println("Price above maximun price for tickets: 999.99");

						}

					} else {
						// user is only standard-buy so not allowed
						System.out.println("Operation not allowed");
					}

					break;
				case "buy":

					// privileged
					if (!(user.getType().equals("standard-buy"))) {

						// ask event name
						System.out.println("Enter name of event");
						String eventName = (String) commandStack.remove();

						// ask number of tickets
						System.out.println("Enter the number of tickets");
						int numOfTickets = (int) commandStack.remove();

						// ask seller name
						System.out.println("Enter name of seller");
						String seller = (String) commandStack.remove();

						// see if event tittle is exists
						if (ticketsAvailableList.stream().anyMatch(
								o -> o.getEventName().equals(eventName) && o.getSellerName().equals(seller))) {

							// set the user to the user we found in list
							Optional<Tickets> ticketWanted = ticketsAvailableList.stream()
									.filter(o -> o.getEventName().equals(eventName) && o.getSellerName().equals(seller))
									.findAny();
							tickets = ticketWanted.get();

							// see if we have enough tickets
							if (tickets.getNumOfTickets() <= numOfTickets) {

								// at most 4 tickets
								if (numOfTickets > 4) {

									// show price per tickets
									System.out.println("price per ticket:" + tickets.getPricePerTicket());

									// show show total cost
									System.out
											.println("price per ticket:" + tickets.getPricePerTicket() * numOfTickets);

									// ask for confirmation yes no
									System.out.println("please confirm: yes or no");

									// if yes confirm
									if (commandStack.remove().toString().equalsIgnoreCase("yes")) {

										// substact number of tickets
										for (Tickets butTickets : ticketsAvailableList) {
											if (butTickets.getEventName().equals(eventName)
													&& butTickets.getSellerName().equals(sellerName)) {
												butTickets.setNumOfTickets(butTickets.getNumOfTickets() - numOfTickets);

												// save in the dialy transaction file
												CommandConversion.writeToDailyTransactiontFile(04, user, butTickets,
														buyerName, sellerName, credit, outputFile);

												break;
											}
										}

									} else {
										System.out.println("please confirm: yes");
									}

								} else {
									System.out.println("at most 4 tickets can be bought");
								}
							} else {
								System.out.println("not enough tickets");
							}
						} else {
							System.out.println("event name not found");
						}

					} else {
						// does not have permission
						System.out.println("Operation not allowed");

					}

					break;
				case "refund":

					if ((user.getType().equals("full-standard")) || (user.getType().equals("admin"))) {

						System.out.println("Enter name of buyer");
						buyerName = (String) commandStack.remove();

						System.out.println("Enter name of seller");
						sellerName = (String) commandStack.remove();

						// ask seller name
						System.out.println("Enter amount of credit to transfer");
						credit = (double) commandStack.remove();

						// add money to the buyer
						for (User buyer : userList) {
							if (buyer.getUsername().equals(buyerName)) {
								buyer.setCredit(buyer.getCredit() + credit);
								break;
							}
						}

						// get the user by seller Username minus the credit
						for (User seller : userList) {
							if (seller.getUsername().equals(sellerName)) {
								seller.setCredit(seller.getCredit() + credit);
								break;
							}
						}
						// write to dtf file
						CommandConversion.writeToDailyTransactiontFile(05, user, tickets, buyerName, sellerName, credit,
								outputFile);

					} else {
						// does not have permission
						System.out.println("Operation not allowed");

					}

					break;
				case "addcredit":

					if (user.getType().equals("admin")) {

						System.out.println("Enter name of user");
						String userName = (String) commandStack.remove();

						System.out.println("Enter amount of credit to add");
						credit = (double) commandStack.remove();

						if (credit > 1000) {

							// add money to the buyer
							for (User userToAddCredit : userList) {
								if (userToAddCredit.getUsername().equals(userName)) {
									userToAddCredit.setCredit(userToAddCredit.getCredit() + credit);

									// write to the dtf file
									CommandConversion.writeToDailyTransactiontFile(06, userToAddCredit, tickets,
											buyerName, sellerName, credit, outputFile);

									break;
								}
							}

						} else {
							System.out.println("highest credit that can be added is: 1000.00");
						}
					} else {
						// does not have permission
						System.out.println("Operation not allowed");

					}
					break;

				default:
					System.out.println("command not found");
			}

		}

	}
}
