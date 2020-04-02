import java.io.*;
import java.util.*;

/**
 * this class contains helper function
 */
public class CommandConversion {
    /**
     * @param user and writes to currentUserFile
     */
    static String currentUserFile = "currentUserFile.inp";
    static String availableTicketFile = "availableTicketFile.inp";

    public static void wirteToCurrentUserFile(User user) {

        Map sessionTypeMap = new HashMap<String, String>();

        sessionTypeMap.put("admin", "AA");
        sessionTypeMap.put("full-standard", "FS");
        sessionTypeMap.put("buy-standard", "BS");
        sessionTypeMap.put("sell-standard", "SS");

        String UserString;
        String username = user.getUsername();
        if (username.length() <= 15) {
            // add extra _
            int emptySpace = 15 - username.length();
            for (int i = 0; i < emptySpace; i++) {
                username = username + "_";
            }
        } else {
            // write out unsername not valid more than 15 chars
        }
        UserString = username + "_";

        String userType = user.getType();
        String sessionHash = sessionTypeMap.get(userType.toString()).toString();

        UserString = UserString + sessionHash + "_";

        String credit = String.valueOf(user.getCredit());
        if (credit.length() <= 9) {
            // add ectra 00 to front
            int emptySpace = 9 - credit.length();
            for (int i = 0; i < emptySpace; i++) {
                credit = "0" + credit;
            }
        } else {
            // write out credit not valid more than 9 chars
        }
        UserString = UserString + credit;

        FileParser.writeFile(currentUserFile, UserString,true);
        // System.out.println( "Welcome");

    }

    public static ArrayList<User> readFromCurrentUserFile(String file) {
        // array list of users
        ArrayList<User> userList = new ArrayList<User>();

        // read saved users- put in a a queue
        Queue userStack = FileParser.readFile(file);

        // while file not empty
        while (!userStack.isEmpty()) {

            // read each line
            String userLine = userStack.remove().toString();

            // call function to create a user obj from line
            User user = createUser(userLine);

            // add user obj to user arraylist
            userList.add(user);
        }
        // return arraylist with all user
        return userList;

    }

    public static User createUser(String userString) {

        // this is the session type map
        Map sessionTypeMap = new HashMap<String, String>();
        sessionTypeMap.put("AA", "admin");
        sessionTypeMap.put("FS", "full-standard");
        sessionTypeMap.put("BS", "buy-standard");
        sessionTypeMap.put("SS", "sell-standard");

        String username = "";
        double credit = 0;
        String type = "";

        // get a char queue out of string
        Queue<Character> charsQueue = new LinkedList<Character>();
        for (char c : userString.toCharArray()) {
            charsQueue.offer(c);
        }

        // loop to get username till _ appears
        while (!((charsQueue.peek().toString()).equals("_"))) {
            username = username + charsQueue.remove().toString();
        }

        // while they are underscoticketString we pop
        while ((charsQueue.peek().toString()).equals("_")) {
            charsQueue.remove();
        }

        // we take the type hash
        type = type + (charsQueue.remove()) + (charsQueue.remove());

        // set to actual type
        type = (String) sessionTypeMap.get(type);

        // keep popping all _ and 0
        while (((charsQueue.peek().toString()).equals("_")) || ((charsQueue.peek().toString()).equals("0"))) {
            charsQueue.remove();
        }

        // till empty wo keep adding to credit
        while (!charsQueue.isEmpty()) {
            credit = credit + charsQueue.remove();
        }

        // make the user finally
        User user = new User(username, credit, type);
        return user;

    }

    public static void writeToAvailableTicketFile(Tickets tickets) {

        String event = tickets.getEventName();
        String username = tickets.getSellerName();
        String num = String.valueOf(tickets.getNumOfTickets());
        String price = String.valueOf(tickets.pricePerTicket);

        String ticketString = "";

        if (event.length() <= 19) {
            // add extra _
            int emptySpace = 19 - event.length();
            for (int i = 0; i < emptySpace; i++) {
                username = username + "_";
            }
        } else {
            // write out event name not valid if more than 19 chars
        }

        ticketString += event + "_";

        if (username.length() <= 13) {
            // add extra _
            int emptySpace = 13 - username.length();
            for (int i = 0; i < emptySpace; i++) {
                username = username + "_";
            }
        } else {
            // write out username not valid if more than 13 chars
        }
        // add the username
        ticketString += username + "_";

        if (num.length() <= 3) {
            // add extra 00 to front
            int emptySpace = 3 - num.length();
            for (int i = 0; i < emptySpace; i++) {
                num = "0" + num;
            }
        } else {
            // write out credit not valid more than 3 chars
        }
        ticketString += num + '_';

        if (price.length() <= 6) {
            // add extra 00 to front
            int emptySpace = 6 - price.length();
            for (int i = 0; i < emptySpace; i++) {
                price = "0" + price;
            }
        } else {
            // write out credit not valid more than 6 chars
        }
        ticketString += price;

        FileParser.writeFile(availableTicketFile, ticketString, true);

    }

    public static ArrayList<Tickets> readFromAvailableTicketsrFile(String file) {
        // array list of tickets
        ArrayList<Tickets> ticketList = new ArrayList<Tickets>();

        // read saved tickets- put in a a queue
        Queue ticketStack = FileParser.readFile(file);

        // while file not empty
        while (!ticketStack.isEmpty()) {

            // read each line
            String ticketLine = ticketStack.remove().toString();

            // call function to create a ticket obj from line
            Tickets ticket = createTicket(ticketLine);

            // add user obj to user arraylist
            ticketList.add(ticket);
        }

        // return arraylist with all user
        return ticketList;
    }

    private static Tickets createTicket(String ticketLine) {

        String eventName = "";
        String sellerName = "", numOfTicketsString = "", pricePerTicketString = "";
        int numOfTickets = 0;
        int pricePerTicket = 0;

        // get a char queue out of string
        Queue<Character> charsQueue = new LinkedList<Character>();
        for (char c : ticketLine.toCharArray()) {
            charsQueue.offer(c);
        }

        // read till 19 for event name
        for (int i = 1; i <= 19; i++) {
            if (!((charsQueue.peek().toString()).equals("_"))) {
                eventName = eventName + charsQueue.remove().toString();
            }
        }

        // while they are underscoticketString we pop
        while ((charsQueue.peek().toString()).equals("_")) {
            charsQueue.remove();
        }

        // this is to read the userName till we read _
        while (!((charsQueue.peek().toString()).equals("_"))) {
            sellerName = sellerName + charsQueue.remove().toString();
        }
        // when we get "_" we keep on popping or 0s
        while ((charsQueue.peek().toString()).equals("_") || (charsQueue.peek().toString()).equals("0")) {
            charsQueue.remove();
        }

        // this is to see the number of tickets
        while (!((charsQueue.peek().toString()).equals("_"))) {
            numOfTicketsString = numOfTicketsString + charsQueue.remove().toString();
        }
        // convert them to int
        numOfTickets = Integer.parseInt(numOfTicketsString);

        // this remove all _ and 0s in between
        while ((charsQueue.peek().toString()).equals("_") || (charsQueue.peek().toString()).equals("0")) {
            charsQueue.remove();
        }

        // get the price per ticket till queue not empty
        while (!(charsQueue.isEmpty())) {
            pricePerTicketString = pricePerTicketString + charsQueue.remove().toString();
        }
        // convert price to int
        pricePerTicket = Integer.parseInt(pricePerTicketString);

        // make the ticket finally
        Tickets ticket = new Tickets(eventName, sellerName, numOfTickets, pricePerTicket);
        return ticket;

    }

    public static void writeToDailyTransactiontFile(int code, User user, Tickets tickets, String buyerName,
            String sellerName, double credit, String outputFile) {
        if (code == 01 || code == 02 || code == 06 || code == 00) {

            Map sessionTypeMap = new HashMap<String, String>();

            sessionTypeMap.put("admin", "AA");
            sessionTypeMap.put("full-standard", "FS");
            sessionTypeMap.put("buy-standard", "BS");
            sessionTypeMap.put("sell-standard", "SS");

            String UserString = String.valueOf(code) + "_";
            String username = user.getUsername();
            if (username.length() <= 15) {
                // add extra _
                int emptySpace = 15 - username.length();
                for (int i = 0; i < emptySpace; i++) {
                    username = username + "_";
                }
            } else {
                // write out unsername not valid more than 15 chars
            }
            UserString = username + "_";

            String userType = user.getType();
            String sessionHash = sessionTypeMap.get(userType.toString()).toString();

            UserString = UserString + sessionHash + "_";

            String userCredit = String.valueOf(user.getCredit());
            if (userCredit.length() <= 9) {
                // add ectra 00 to front
                int emptySpace = 9 - userCredit.length();
                for (int i = 0; i < emptySpace; i++) {
                    userCredit = "0" + userCredit;
                }
            } else {
                // write out credit not valid more than 9 chars
            }
            UserString = UserString + userCredit;

            FileParser.writeFile(outputFile, UserString,true);

        } else if (code == 03 || code == 04) {

            String ticketString = String.valueOf(code) + "_";

            String event = tickets.getEventName();
            String username = tickets.getSellerName();
            String num = String.valueOf(tickets.getNumOfTickets());
            String price = String.valueOf(tickets.pricePerTicket);

            if (event.length() <= 19) {
                // add extra _
                int emptySpace = 19 - event.length();
                for (int i = 0; i < emptySpace; i++) {
                    username = username + "_";
                }
            } else {
                // write out event name not valid if more than 19 chars
            }

            ticketString += event + "_";

            if (username.length() <= 13) {
                // add extra _
                int emptySpace = 13 - username.length();
                for (int i = 0; i < emptySpace; i++) {
                    username = username + "_";
                }
            } else {
                // write out username not valid if more than 13 chars
            }
            // add the username
            ticketString += username + "_";

            if (num.length() <= 3) {
                // add extra 00 to front
                int emptySpace = 3 - num.length();
                for (int i = 0; i < emptySpace; i++) {
                    num = "0" + num;
                }
            } else {
                // write out credit not valid more than 3 chars
            }
            ticketString += num + '_';

            if (price.length() <= 6) {
                // add extra 00 to front
                int emptySpace = 6 - price.length();
                for (int i = 0; i < emptySpace; i++) {
                    price = "0" + price;
                }
            } else {
                // write out credit not valid more than 6 chars
            }
            ticketString += price;

            FileParser.writeFile(outputFile, ticketString,true);

        } else if (code == 05) {

            String UserString = String.valueOf(code) + "_";
            String username = buyerName;
            if (username.length() <= 15) {
                // add extra _
                int emptySpace = 15 - username.length();
                for (int i = 0; i < emptySpace; i++) {
                    username = username + "_";
                }
            } else {
                // write out unsername not valid more than 15 chars
            }
            UserString = username + "_";

            String usernameSeller = sellerName;
            if (usernameSeller.length() <= 15) {
                // add extra _
                int emptySpace = 15 - usernameSeller.length();
                for (int i = 0; i < emptySpace; i++) {
                    usernameSeller = usernameSeller + "_";
                }
            } else {
                // write out unsername not valid more than 15 chars
            }
            UserString = usernameSeller + "_";

            String creditString = String.valueOf(credit);
            if (creditString.length() <= 9) {
                // add ectra 00 to front
                int emptySpace = 9 - creditString.length();
                for (int i = 0; i < emptySpace; i++) {
                    creditString = "0" + creditString;
                }
            } else {
                // write out credit not valid more than 9 chars
            }
            UserString = UserString + creditString;

            FileParser.writeFile(currentUserFile, UserString, true);
        }

    }

}
