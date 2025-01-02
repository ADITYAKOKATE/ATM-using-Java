import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

class Account {
    private int accountNumber;
    private int pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Account(int accountNumber, int pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public boolean verifyPin(int enteredPin) {
        return pin == enteredPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            recordTransaction("Deposit", amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recordTransaction("Withdrawal", -amount);
            return true;
        }
        return false;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    private void recordTransaction(String description, double amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String transaction = sdf.format(date) + " - " + description + ": $" + amount;
        transactionHistory.add(transaction);
    }
}

public class ATM {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("ATM Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    logIn();
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public static void createAccount() {
        System.out.print("Enter a 4-digit account number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter a 4-digit PIN: ");
        int pin = scanner.nextInt();

        if (accountNumber >= 1000 && accountNumber <= 9999 && pin >= 1000 && pin <= 9999) {
            Account account = new Account(accountNumber, pin);
            accounts.add(account);
            System.out.println("Account created successfully.");
        } else {
            System.out.println("Invalid account number or PIN. Both should be 4-digit numbers.");
        }
    }

    public static void logIn() {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter your PIN: ");
        int pin = scanner.nextInt();

        Account account = findAccount(accountNumber);

        if (account != null && account.verifyPin(pin)) {
            boolean loggedIn = true;
            while (loggedIn) {
                System.out.println("Welcome to your account.");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. View Transaction History");
                System.out.println("5. Log Out");
                System.out.print("Enter your choice: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        System.out.println("Your account balance is: $" + account.getBalance());
                        break;
                    case 2:
                        System.out.print("Enter the amount to deposit: $");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        System.out.println("Deposited $" + depositAmount + " successfully. Your new balance is: $" + account.getBalance());
                        break;
                    case 3:
                        System.out.print("Enter the amount to withdraw: $");
                        double withdrawAmount = scanner.nextDouble();
                        boolean success = account.withdraw(withdrawAmount);
                        if (success) {
                            System.out.println("Withdrawn $" + withdrawAmount + " successfully. Your new balance is: $" + account.getBalance());
                        } else {
                            System.out.println("Invalid amount or insufficient balance.");
                        }
                        break;
                    case 4:
                        ArrayList<String> transactions = account.getTransactionHistory();
                        if (transactions.size() > 0) {
                            System.out.println("Transaction History:");
                            for (String transaction : transactions) {
                                System.out.println(transaction);
                            }
                        } else {
                            System.out.println("No transactions yet.");
                        }
                        break;
                    case 5:
                        loggedIn = false;
                        System.out.println("Logged out successfully.");
                        break;
                    default:
                        System.out.println("Invalid option. Please select a valid one.");
                }
            }
        } else {
            System.out.println("Invalid account number or PIN. Please try again.");
        }
    }

    public static Account findAccount(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }
}
