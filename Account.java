class Account {
  long accountNumber = newAccountNumber();
  User user; 
  double interestRates;
  double balance;

  public Account(User newUser, double newInterestRates, double startingBalance){
    user = newUser;
    interestRates = newInterestRates;
    balance = startingBalance;
  }

  public void transferMoney(Account endDestination, double amount) {

  }

  public void depositMoney(double amount) {

  }

  public void extractMoney(double amount) {

  }

  public static long newAccountNumber(){
    return 1234567890;
  }

  public void sleep() {

  }

  public long getAccountNumber() {
    return this.accountNumber;
  }
}