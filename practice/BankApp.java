package practice;


import java.util.Scanner;

abstract class Accounts{
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public Accounts(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposite(double amount){
        balance += amount;
        System.out.println(amount + " deposited successfully. current balance is : " + balance);
    }

    public void withdraw(double amount){
        balance -= amount;
        System.out.println(amount + " withdraw from the account, current balance is : " + balance);
    }

    public void displayDetails(){
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder Name : " + accountHolderName);
        System.out.println("Current Balance is : " + balance);
    }
}

interface Interest{
    void calculateInterest();
}

class SavingAccounts extends Accounts implements Interest{

    private double interestRate = 4.0; // 4% annually

    public SavingAccounts(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }

    @Override
    public void calculateInterest() {
        double interest = (getBalance() * interestRate) / 100;
        System.out.println("Annual Interest on your savings: ₹" + interest);
    }
}

class CurrentAccount extends Accounts{

    public CurrentAccount(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }
}

class AccountOpenings{
    public static Accounts createAccount(int type, String accNo, String name, double balance) {
        switch (type){
            case 1:
                return new SavingAccounts(accNo, name, balance);
            case 2:
                return new CurrentAccount(accNo, name, balance);
            default:
                throw new IllegalArgumentException("Illegal Account type!");
        }
    }
}

interface LoanCalculator {
    double calculateInterest(double loanAmount, double interestRate);
}

class SimpleLoanCalculator implements LoanCalculator {
    @Override
    public double calculateInterest(double loanAmount, double interestRate) {
        return (loanAmount * interestRate) / 100;
    }
}

class BankService {
    public void deposit(Accounts account, double amount) {
        account.deposite(amount);
    }

    public void withdraw(Accounts account, double amount) {
        account.withdraw(amount);
    }

    public void displayDetails(Accounts account) {
        account.displayDetails();
    }

    public void showSavingsInterest(Accounts account) {
        if (account instanceof Interest) {
            ((Interest) account).calculateInterest();
        } else {
            System.out.println("This account type does not earn interest.");
        }
    }
}

public class BankApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---- Welcome to Bank Application ----");
        System.out.println("Choose the Bank Account type 1. Saving Account 2. Current Account ");

        int choice = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter Account Number : ");
        String accountNumber = sc.nextLine();

        System.out.println("Enter the Account Holder Name : ");
        String accountHolderName = sc.nextLine();

        System.out.println("Enter the Initial Balance : ");
        double balance = sc.nextDouble();

        Accounts account = AccountOpenings.createAccount(choice, accountNumber, accountHolderName, balance);
        BankService bankService = new BankService();
        LoanCalculator loanCalculator = new SimpleLoanCalculator();

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Display Account Details");
            System.out.println("4. Calculate Savings Interest");
            System.out.println("5. Calculate Loan Interest");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double dep = sc.nextDouble();
                    bankService.deposit(account, dep);
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double wit = sc.nextDouble();
                    bankService.withdraw(account, wit);
                    break;

                case 3:
                    bankService.displayDetails(account);
                    break;

                case 4:
                    bankService.showSavingsInterest(account);
                    break;

                case 5:
                    System.out.print("Enter Loan Amount: ");
                    double loanAmt = sc.nextDouble();
                    System.out.print("Enter Interest Rate (%): ");
                    double rate = sc.nextDouble();
                    double interest = loanCalculator.calculateInterest(loanAmt, rate);
                    System.out.println("Interest on your loan: ₹" + interest);
                    break;

                case 6:
                    System.out.println("Thank you for using the Bank Application!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
