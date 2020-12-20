import java.util.*;
import java.io.*;

class Main {
  public static final File usersFile = new File("users.txt");
  public static List<User> usersList;

  public static void main(String args[]) throws FileNotFoundException {
    usersList = getAllUsers();

    User currentUser = logInMenu();
    //log in menu
    //- log in(username)(password)
    //- sign in(username)(password)
    //- exit
    
    while (true) { 
      mainMenu(currentUser);
    }
    //main menu
    //- manage accounts
    //- create new account
    //- log out
    
    //(option inside of mainMenu function)
    //mange accounts menu
    //- transfer money (amount)(account 1)(account 2)
    //- deposit money (amount)(account 1)
    //- extract money (amount)(account 1)
    //- back

    //(option inside of mainMenu function)
    //new account menu
    //- intrest
    //- starting balance
  }

  public static User logInMenu() throws FileNotFoundException{
    Scanner scan = new Scanner(System.in);

    while (true) { 
      System.out.println("Would you like too...");

      System.out.println("1) Log In");
      System.out.println("2) Sign Up");
      System.out.println("3) Exit");

      User user;
      switch (scan.nextLine()) {
        case "1":
          user = logIn();
          return user;
        case "2":
          user = signUp();
          usersList = getAllUsers();
          return user;
        case "3":
          System.exit(0);
          return null;
        default:
          System.out.println("Please enter 1 of the 3 options.");
      }
    }
  }

  public static User logIn() { 
    Scanner scan = new Scanner(System.in);
    boolean noUserYet = true;
    User outputUser = new User();
      while (noUserYet == true) { 
        System.out.println("Loggin In...");
        System.out.print("Username : ");
        String username = scan.nextLine();
        System.out.print("Password : ");
        String password = scan.nextLine();

        outputUser = findUser(username, password);
        if (outputUser != null) { 
          noUserYet = false;
        }
        else { 
          System.out.println("User not found.");
        }
    }
    return outputUser;
  }

  public static User signUp() {
    Scanner scan = new Scanner(System.in);
      while (true) { 
        System.out.println("Creating User...");
        System.out.print("Username : ");
        String username = scan.nextLine();
        System.out.print("Password : ");
        String password = scan.nextLine();

        
        if (findUserName(username) == null) { 
          return new User(username, password, true);
        }
        else { 
          System.out.println("That Username Is Already Taken.");
        }
    }
  }

  public static User mainMenu(User currentUser) throws FileNotFoundException{
    while (true) { 
      Scanner scan = new Scanner(System.in);
      System.out.println("Would you like too...");

      System.out.println("1) Manage Your Accounts");
      System.out.println("2) Create a New Account");
      System.out.println("3) Log Out");

      switch (scan.nextLine()) {
        case "1":
          accountsMenu(currentUser);
          return currentUser;
        case "2":
          createAccountMenu(currentUser);
          return currentUser;
        case "3":
          return logInMenu();
        default:
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
          transferMoney(currentUser);
          stillGoing = false;
          break;
        case "2":
          depositeMoney(currentUser);
          stillGoing = false;
          break;
        case "3":
          extractMoney(currentUser);
          stillGoing = false;
          break;
        case "4":
          stillGoing = false;
          break;
        default:
          System.out.println("Please Pick 1 of the 4 Options.");
      }
    }
  }

  public static void transferMoney(User currentUser){
    Scanner scan = new Scanner(System.in);
    List<Account> accounts = currentUser.getAccounts();

    System.out.println("Transfering From...");
    for (int i = 1; i <= accounts.size(); i++) { 
      System.out.println(i + ") " + accounts.get(i-1).getAccountNumber());
    }

    int startingAccountID = scan.nextInt();


    if (startingAccountID <= accounts.size()) { 
      System.out.println("Transfering Too...");
      for (int i = 1; i <= accounts.size(); i++) { 
        System.out.println(i + ") " + accounts.get(i-1).getAccountNumber());
      }
      int endAccountID = scan.nextInt();
      if (endAccountID <= accounts.size()) {
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

    System.out.println("Depositing To...");
    for (int i = 1; i <= accounts.size(); i++) { 
      System.out.println(i + ") " + accounts.get(i-1).getAccountNumber());
    }

    int accountID = scan.nextInt();

    if (accountID <= accounts.size()) { 
      System.out.print("Amount : ");
      double amount = scan.nextDouble();
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

    System.out.println("Extracting From...");
    for (int i = 1; i <= accounts.size(); i++) { 
      System.out.println(i + ") " + accounts.get(i-1).getAccountNumber());
    }

    int accountID = scan.nextInt();

    if (accountID <= accounts.size()) { 
      System.out.print("Amount : ");
      double amount = scan.nextDouble();
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
      System.out.println("Creating Account...");
      System.out.print("Intrest : ");
      double intrest = scan.nextDouble();
      System.out.print("Starting Balance : ");
      double balance = scan.nextDouble();

      
      currentUser.createAccount(intrest, balance);
  }

  public static List<User> getAllUsers() throws FileNotFoundException {
    Scanner input = new Scanner(usersFile); 
    List<User> outputList = new ArrayList();
    while (input.hasNextLine())  
    {  
      String line = input.nextLine();
      String lineCopy = line;
      String[] stringArray = lineCopy.split(", ", 2);

      User tempUser = new User(stringArray[0], stringArray[1], false);
      outputList.add(tempUser);
    } 
    for (int i = 0; i < outputList.size(); i++) { 
      Scanner accountsInput = new Scanner(new File(outputList.get(i).getName() + ".txt"));
      while (accountsInput.hasNextLine()) { 
        String nextLine = accountsInput.nextLine();
        String[] nextLineSplit = nextLine.split(", ");
        Account newAccount = new Account(outputList.get(i), Double.parseDouble(nextLineSplit[1]), Double.parseDouble(nextLineSplit[2]), Long.parseLong(nextLineSplit[0]));
        outputList.get(i).addAccount(newAccount);
      }
    }
    return outputList;
  }

  public static User findUser(String username, String password){
    for (int i = 0; i < usersList.size(); i++) {
      if (usersList.get(i).getName().equals(username) && usersList.get(i).getPassword().equals(password)) {
        return usersList.get(i);
      }
    }
    return null;
  }

  public static User findUserName(String username){
    for (int i = 0; i < usersList.size(); i++) {
      if (usersList.get(i).getName().equals(username)) {
        return usersList.get(i);
      }
    }
    return null;
  }
}