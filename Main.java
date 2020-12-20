import java.util.*;
import java.io.*;

class Main {
  public static final File usersFile = new File("users.txt");
  public static List<User> usersList;

  public static void main(String args[]) throws FileNotFoundException {
    //get all users
    usersList = getAllUsers();

    //get user to login/sign in
    User currentUser = logInMenu();
    
    //always go back to main menu from which other function can be based off of
    while (true) { 
      mainMenu(currentUser);
    }
  }

  public static User logInMenu() throws FileNotFoundException{
    Scanner scan = new Scanner(System.in);

    while (true) { 
      System.out.println("Would you like to...");

      System.out.println("1) Log In");
      System.out.println("2) Sign Up");
      System.out.println("3) Exit");

      User user;
      switch (scan.nextLine()) {
        case "1":
          //user logs in
          user = logIn();
          return user;
        case "2":
          //users signs in
          user = signUp();
          usersList = getAllUsers();
          return user;
        case "3":
          //users quits
          System.exit(0);
          return null;
        default:
          //they didn't enter anything
          System.out.println("Please enter 1 of the 3 options.");
      }
    }
  }

  public static User logIn() { 
    Scanner scan = new Scanner(System.in);
    boolean noUserYet = true;
    User outputUser = new User();
      while (noUserYet == true) { 
        //get username and password
        System.out.println("Loggin In...");
        System.out.print("Username : ");
        String username = scan.nextLine();
        System.out.print("Password : ");
        String password = scan.nextLine();

        //find the account matching the username in password
        outputUser = findUser(username, password);
        if (outputUser != null) { 
          noUserYet = false;
        }
        else { 
          //give error if it doesn't exist
          System.out.println("User not found.");
        }
    }
    return outputUser;
  }

  public static User signUp() {
    Scanner scan = new Scanner(System.in);
      while (true) { 
        //get username and passowrd
        System.out.println("Creating User...");
        System.out.print("Username : ");
        String username = scan.nextLine();
        System.out.print("Password : ");
        String password = scan.nextLine();

        //see if username already exists
        if (findUserName(username) == null) { 
          return new User(username, password, true);
        }
        else { 
          //if it does then give error
          System.out.println("That Username Is Already Taken.");
        }
    }
  }

  public static User mainMenu(User currentUser) throws FileNotFoundException{
    while (true) { 
      Scanner scan = new Scanner(System.in);
      System.out.println("Would you like to...");

      System.out.println("1) Manage Your Accounts");
      System.out.println("2) Create a New Account");
      System.out.println("3) Log Out");

      switch (scan.nextLine()) {
        case "1":
          //making modifications to accounts
          accountsMenu(currentUser);
          return currentUser;
        case "2":
          //making new account
          createAccountMenu(currentUser);
          return currentUser;
        case "3":
          //log out
          return logInMenu();
        default:
          //nothing given
          System.out.println("Please enter 1 of the 3 options.");
      }
    }
  }

  public static void accountsMenu(User currentUser) {
    boolean stillGoing = true;
    while (stillGoing) { 
      Scanner scan = new Scanner(System.in);
      System.out.println("Manage your accounts by...");

      System.out.println("1) Transfering Money");
      System.out.println("2) Depositing Money");
      System.out.println("3) Extracting Money");
      System.out.println("4) Back");

      switch (scan.nextLine()) {
        case "1":
          //for transfer money
          transferMoney(currentUser);
          stillGoing = false;
          break;
        case "2":
          //for depositing money
          depositeMoney(currentUser);
          stillGoing = false;
          break;
        case "3":
          //for extracting money
          extractMoney(currentUser);
          stillGoing = false;
          break;
        case "4":
          //for exiting
          stillGoing = false;
          break;
        default:
          //enter in nothing
          System.out.println("Please Pick 1 of the 4 Options.");
      }
    }
  }

  public static void transferMoney(User currentUser){
    Scanner scan = new Scanner(System.in);
    List<Account> accounts = currentUser.getAccounts();

    //get 1st account
    System.out.println("Transfering From...");
    for (int i = 1; i <= accounts.size(); i++) { 
      System.out.println(i + ") " + accounts.get(i-1).getAccountNumber() + " –– " + accounts.get(i-1).getBalance() + "$");
    }

    int startingAccountID = scan.nextInt();

    //make sure index not out of bounds
    if (startingAccountID <= accounts.size()) { 
      //get 2nd account
      System.out.println("Transfering to...");
      for (int i = 1; i <= accounts.size(); i++) { 
        System.out.println(i + ") " + accounts.get(i-1).getAccountNumber() + " –– " + accounts.get(i-1).getBalance() + "$");
      }
      int endAccountID = scan.nextInt();
      //make sure 2nd accounts index not out of bounds
      if (endAccountID <= accounts.size()) {
        //get amount
        System.out.print("Amount : ");
        double amount = scan.nextDouble();
        accounts.get(startingAccountID-1).transferMoney(accounts.get(endAccountID-1), amount);
      }
      else { 
        System.out.println("Please pick 1 of the " + accounts.size() + " options.");
        transferMoney(currentUser);
      }
    }
    else { 
      System.out.println("Please pick 1 of the " + accounts.size() + " options.");
      transferMoney(currentUser);
    }
  }

  public static void depositeMoney(User currentUser) {
    Scanner scan = new Scanner(System.in);
    List<Account> accounts = currentUser.getAccounts();

    //get account to deposite to
    System.out.println("Depositing to...");
    for (int i = 1; i <= accounts.size(); i++) { 
      System.out.println(i + ") " + accounts.get(i-1).getAccountNumber() + " –– " + accounts.get(i-1).getBalance() + "$");
    }

    int accountID = scan.nextInt();

    //see if accountID is out of bounds
    if (accountID <= accounts.size()) { 
      //get amount
      System.out.print("Amount : ");
      double amount = scan.nextDouble();
      //see if amount is greater 0
      if (amount > 0) { 
        accounts.get(accountID-1).extractDepositMoney(amount); 
      }
      else { 
        System.out.println("Amount must be greater than 0.");
      }
    }
    else { 
      System.out.println("Please pick 1 of the " + accounts.size() + " options.");
      depositeMoney(currentUser);
    }
  }

  public static void extractMoney(User currentUser) { 
    Scanner scan = new Scanner(System.in);
    List<Account> accounts = currentUser.getAccounts();

    //find account
    System.out.println("Extracting From...");
    for (int i = 1; i <= accounts.size(); i++) { 
      System.out.println(i + ") " + accounts.get(i-1).getAccountNumber() + " –– " + accounts.get(i-1).getBalance() + "$");
    }

    int accountID = scan.nextInt();

    //see if account is out of bounds
    if (accountID <= accounts.size()) { 
      //get amount
      System.out.print("Amount : ");
      double amount = scan.nextDouble();
      //see if amount is greater than 0
      if (amount > 0) { 
        accounts.get(accountID-1).extractDepositMoney(-1 * amount); 
      }
      else { 
        System.out.println("Amount must be greater than 0.");
      }
    }
    else { 
      System.out.println("Please pick 1 of the " + accounts.size() + " options.");
      depositeMoney(currentUser);
    }
  }

  public static void createAccountMenu(User currentUser) throws FileNotFoundException{
    Scanner scan = new Scanner(System.in);
      //get intrest and starting balance
      System.out.println("Creating Account...");
      System.out.print("Intrest : ");
      double intrest = scan.nextDouble();
      System.out.print("Starting Balance : ");
      double balance = scan.nextDouble();

      //create account
      currentUser.createAccount(intrest, balance);
  }

  public static List<User> getAllUsers() throws FileNotFoundException {
    Scanner input = new Scanner(usersFile); 
    List<User> outputList = new ArrayList();
    //for each user in file create a user in outputList
    while (input.hasNextLine())  
    {  
      String line = input.nextLine();
      String lineCopy = line;
      String[] stringArray = lineCopy.split(", ", 2);

      User tempUser = new User(stringArray[0], stringArray[1], false);
      outputList.add(tempUser);
    } 
    //for each line in the subfiles of the users - create and account 
    for (int i = 0; i < outputList.size(); i++) { 
      Scanner accountsInput = new Scanner(new File(outputList.get(i).getName() + ".txt"));
      while (accountsInput.hasNextLine()) { 
        String nextLine = accountsInput.nextLine();
        String[] nextLineSplit = nextLine.split(", ");
        Account newAccount = new Account(outputList.get(i), Double.parseDouble(nextLineSplit[1]), Double.parseDouble(nextLineSplit[2]), Long.parseLong(nextLineSplit[0]));
        outputList.get(i).createAccountWithoutFile(newAccount);
      }
    }
    return outputList;
  }

  public static User findUser(String username, String password){
    //search through the list of users to find the inputed one
    for (int i = 0; i < usersList.size(); i++) {
      if (usersList.get(i).getName().equals(username) && usersList.get(i).getPassword().equals(password)) {
        return usersList.get(i);
      }
    }
    //if it doesn't exist return null
    return null;
  }

  public static User findUserName(String username){
    // search throught the list of users to find one with a matching username
    for (int i = 0; i < usersList.size(); i++) {
      if (usersList.get(i).getName().equals(username)) {
        return usersList.get(i);
      }
    }
    //if it doesn't exist return null
    return null;
  }
}