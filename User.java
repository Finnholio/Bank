import java.util.*;

class User {
  String name;
  String password;
  List<Account> accounts = new ArrayList();

  public User(String username, String password) {
    this.name = username;
    this.password = password;
  }

  public void createAccount(double intrestRates, double startingBalance) {
    Account newAccount = new Account(this, intrestRates, startingBalance);
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
}