//  SRP: Bank class only bank related info 
//  one class = one responsibility
class Bank {
    private String bankName;

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }
}


// OCP + LSP: Account Hierarchy 
// OCP: if we want to add new account so create new class dont modify existing one
// LSP: SavingsAccount and CurrentAccount can be used through Account reference 

abstract class Account {
    int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    // Common functionality: deposit
    public void deposit(int amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + " and  New balance: " + balance);
    }

    // Withdrawal according to account
    public abstract void withdraw(int amount);

    // View balance
    public void viewBalance() {
        System.out.println("Balance: " + balance);
    }
}

// In Savings account withdraw is possible when amount is available 
class SavingsAccount extends Account {
    public SavingsAccount(int balance) {
        super(balance);
    }

    @Override
    public void withdraw(int amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + "  New balance: " + balance);
        } else {
            System.out.println(" Insufficient funds in Savings Account!");
        }
    }
}

// Current account allows overdraft (balance can be negative )
class CurrentAccount extends Account {
    public CurrentAccount(int balance) {
        super(balance);
    }

    @Override
    public void withdraw(int amount) {
        balance -= amount; // overdraft allowed
        System.out.println("Withdrawn: " + amount + "  Balance after overdraft: " + balance);
    }
}


// ISP: Loan Interfaces 
// ISP: class should implement only required interfaces

interface Loan {
    void showLoanDetails();
}

class HomeLoan implements Loan {
    int amount;
    int interestRate;

    public HomeLoan(int amount, int interestRate) {
        this.amount = amount;
        this.interestRate = interestRate;
    }

    @Override
    public void showLoanDetails() {
        System.out.println("Home Loan: " + amount + " at " + interestRate + "% interest.");
    }
}

class StudentLoan implements Loan {
    int amount;
    int interestRate;

    public StudentLoan(int amount, int interestRate) {
        this.amount = amount;
        this.interestRate = interestRate;
    }

    @Override
    public void showLoanDetails() {
        System.out.println("Student Loan: " + amount + " at " + interestRate + "% interest.");
    }
}


// SRP: Transaction
// Transaction

class TransactionService {
    public void deposit(Account acc, int amount) {
        acc.deposit(amount);
    }

    public void withdraw(Account acc, int amount) {
        acc.withdraw(amount);
    }

    public void view(Account acc) {
        acc.viewBalance();
    }
}


public class BankApp 
{
    public static void main(String[] args) 
	{
        Bank b = new Bank("SBI Bank"); 
        System.out.println("Welcome to " + b.getBankName());

        // Accounts
        Account savings = new SavingsAccount(10000);
        Account current = new CurrentAccount(5000);

        // Loans
        Loan h = new HomeLoan(500000, 7);
        Loan s = new StudentLoan(200000, 5);

        // Transaction Service (only deposit, withdraw, view)
        TransactionService t = new TransactionService();

        // Transactions
        t.deposit(savings, 2000);
        t.withdraw(current, 1000);
        t.view(savings);

        // Loan Details
        h.showLoanDetails();
        s.showLoanDetails();
    }
}
