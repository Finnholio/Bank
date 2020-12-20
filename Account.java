import java.util.*;
import java.io.*;

class Account {
  public static List<Integer> allAccountNumbers = new ArrayList();
  long accountNumber = newAccountNumber();
  User user; 
  double interestRates;
  double balance;

  public Account(User newUser, double newInterestRates, double startingBalance){
    user = newUser;
    interestRates = newInterestRates;
    balance = startingBalance;
  }

  public Account(User newUser, double newInterestRates, double startingBalance, long newAccountNumber){
    user = newUser;
    interestRates = newInterestRates;
    balance = startingBalance;
    accountNumber = newAccountNumber;
  }

  public void transferMoney(Account endDestination, double amount) {
    if (this.balance - amount >= 0) { 
      if (amount > 0) { 
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
    if (this.balance + amount > 0) { 
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

      String newLine = this.accountNumber + ", " + this.interestRates + ", " + this.balance;
      String totalString = "";
      while (scan.hasNextLine()) { 
        String nextLine = scan.nextLine();
        if (nextLine.equals(newLine)) { 
          bw.write(this.accountNumber + ", " + this.interestRates + ", " + newBalance + "\n");
        }
        else { 
          bw.write(nextLine + "\n");
        }
      }
      bw.close();
      tempFile.renameTo(file);
      this.balance = newBalance;
    }
    catch (IOException e) { 
      System.out.println(e);
    }

  }

  public static long newAccountNumber(){
    boolean numNotFound = true;
    Random rand = new Random();
    int newAccountNumber = rand.nextInt(10000000);
    allAccountNumbers.add(newAccountNumber);
    return newAccountNumber;
  }

  public void sleep() {

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
}