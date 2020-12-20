import java.util.*;
import java.io.*;

class User {
  String name;
  String password;
  List<Account> accounts = new ArrayList();

  public User(String username, String password, boolean newUser) {
    this.name = username;
    this.password = password;
    if (newUser) {
      try {
        File newUsersFile = new File(username + ".txt");
        if (newUsersFile.createNewFile()) {
          File usersFile = new File("users.txt");
          FileWriter usersfw = new FileWriter(usersFile.getAbsoluteFile(), true);
          BufferedWriter bw = new BufferedWriter(usersfw);

          bw.write(this.name + ", " + this.password + "\n");
          bw.close();

        }
        else {
          System.out.println("Username already exists.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
    
  }

  public User() {
    this.name = "";
    this.password = "";
  }

  public void createAccount(double intrestRates, double startingBalance) throws FileNotFoundException {
    Account newAccount = new Account(this, intrestRates, startingBalance);
    this.accounts.add(newAccount);

    try { 
      File file = new File(this.name + ".txt");
      FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(newAccount.getAccountNumber() + ", " + intrestRates + ", " + startingBalance + "\n");
      bw.close();
    }
    catch (IOException e) { 
      System.out.println(e);
    }
  }

  public void addAccount(Account newAccount) {
    this.accounts.add(newAccount);
  }

  public void removeAccount(long accountNumber) {
    for (int i = 0; i < this.accounts.size(); i++){
      if (this.accounts.get(i).getAccountNumber() == accountNumber) {
        this.accounts.remove(this.accounts.get(i));
        break;
      }
      else if (i == this.accounts.size() - 1) {
        System.out.println("ERROR : ACCOUNT NUMBER NOT FOUND");
      }
    }
  }

  public List<Account> getAccounts(){
    return accounts;
  } 

  public String getName(){
    return this.name;
  }

  public String getPassword(){
    return this.password;
  }
}