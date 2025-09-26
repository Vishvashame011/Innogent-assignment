package bankingProject;

import java.util.Scanner;

// ===== 1. Abstraction: Account Base Class =====
abstract class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public Account(String accountNumber, String accountHolderName, double balance) {
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

    public void deposit(double amount) {
        balance += amount;
        System.out.println("₹" + amount + " deposited successfully. Current Balance: ₹" + balance);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            System.out.println("₹" + amount + " withdrawn successfully. Current Balance: ₹" + balance);
        }
    }

    public void displayDetails() {
        System.out.println("\nAccount Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: ₹" + balance);
    }
}

// ===== 2. Interface Segregation: Interest-bearing accounts =====
interface InterestBearing {
    void calculateInterest();
}

// ===== 3. Savings Account implements InterestBearing =====
class SavingsAccount extends Account implements InterestBearing {
    private double interestRate = 4.0; // 4% annually

    public SavingsAccount(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }

    @Override
    public void calculateInterest() {
        double interest = (getBalance() * interestRate) / 100;
        System.out.println("Annual Interest on your savings: ₹" + interest);
    }
}

// ===== 4. Current Account =====
class CurrentAccount extends Account {
    public CurrentAccount(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }
}

// ===== 5. Open/Closed: AccountFactory =====
class AccountOpening {
    public static Account createAccount(int type, String accNo, String name, double balance) {
        switch (type) {
            case 1:
                return new SavingsAccount(accNo, name, balance);
            case 2:
                return new CurrentAccount(accNo, name, balance);
            default:
                throw new IllegalArgumentException("Invalid account type!");
        }
    }
}

// ===== 6. Dependency Inversion: LoanCalculator Interface =====
interface LoanCalculator {
    double calculateInterest(double loanAmount, double interestRate);
}

// Implementation of LoanCalculator
class SimpleLoanCalculator implements LoanCalculator {
    @Override
    public double calculateInterest(double loanAmount, double interestRate) {
        return (loanAmount * interestRate) / 100;
    }
}

// ===== 7. Single Responsibility: BankService =====
class BankService {
    public void deposit(Account account, double amount) {
        account.deposit(amount);
    }

    public void withdraw(Account account, double amount) {
        account.withdraw(amount);
    }

    public void displayDetails(Account account) {
        account.displayDetails();
    }

    public void showSavingsInterest(Account account) {
        if (account instanceof InterestBearing) {
            ((InterestBearing) account).calculateInterest();
        } else {
            System.out.println("This account type does not earn interest.");
        }
    }
}

// ===== 8. Main Application (UI Layer) =====
public class BankApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Bank Application!");
        System.out.println("Choose account type: 1. Savings  2. Current");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        Account account = AccountOpening.createAccount(choice, accNo, name, balance);
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
