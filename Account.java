import java.util.*;
import java.io.*;

class Account {
  long accountNumber = newAccountNumber();
  User user; 
  double interestRates;
  double balance;

  //main constructor
  public Account(User newUser, double newInterestRates, double startingBalance){
    user = newUser;
    interestRates = newInterestRates;
    balance = startingBalance;
  }

  //constructor but you manually set account number 
  public Account(User newUser, double newInterestRates, double startingBalance, long newAccountNumber){
    user = newUser;
    interestRates = newInterestRates;
    balance = startingBalance;
    accountNumber = newAccountNumber;
  }

  //empty constructor
  public Account() { 
    user = new User();
    interestRates = 0;
    balance = 0;
  }

  public void transferMoney(Account endDestination, double amount) {
    //check to make sure new amount is greater than 0
    if (this.balance - amount >= 0) { 
      //check to make sure your not transfering negative money
      if (amount > 0) { 
        //change balance
        this.extractDepositMoney(-1*amount);
        endDestination.extractDepositMoney(amount);
      }
      else { 
        System.out.println("Amount must be greater than");
      }
    }
    else { 
      System.out.println("The new balance can not be less that 0.");
    }
  }

  public void extractDepositMoney(double amount) {
    //make sure new balance is greater than 0
    if (this.balance + amount > 0) { 
      //change balance
      this.editBalance(this.balance + amount);
    }
    else {
      System.out.println("The new balance can not be less that 0.");
    }
  }

  public void editBalance(double newBalance) { 
    try { 
      File file = new File(this.user.getName() + ".txt");
      File tempFile = new File("tempText.txt");
      FileWriter fw = new FileWriter(tempFile.getAbsoluteFile(), false);
      BufferedWriter bw = new BufferedWriter(fw);
      Scanner scan = new Scanner(file);

      //set the line we want to add (modify)
      String newLine = this.accountNumber + ", " + this.interestRates + ", " + this.balance;

      while (scan.hasNextLine()) { 
        String nextLine = scan.nextLine();
        //check if this line is newLine
        if (nextLine.equals(newLine)) { 
          //if so write that line in tempFile
          bw.write(this.accountNumber + ", " + this.interestRates + ", " + newBalance + "\n");
        }
        else { 
          //else - write the original line in the file onto the temp file
          bw.write(nextLine + "\n");
        }
      }
      bw.close();
      //make temp file into the actaul 
      tempFile.renameTo(file);
      this.balance = newBalance;
    }
    catch (IOException e) { 
      System.out.println(e);
    }

  }

  public static long newAccountNumber(){
    //generate random account number
    boolean numNotFound = true;
    Random rand = new Random();
    int newAccountNumber = rand.nextInt(10000000);
    return newAccountNumber;
  }

  public long getAccountNumber() {
    return this.accountNumber;
  }

  public String toString() {
    return this.user.name + " has " + this.balance + " in " + this.accountNumber;
  }

  public double getBalance() { 
    return this.balance;
  }

  public double getIntrestRates() { 
    return this.interestRates;
  }
}