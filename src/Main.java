import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Wallet wallet = new Wallet();
    private static final Scanner scan = new Scanner(System.in);
    private static final String FILE_PATH = "C:\\Users\\SS\\OneDrive\\Desktop\\Leen java Code\\ExpenseTracker\\src\\Transaction.txt";

    public static void main(String[] args) {
        // FR-7.2: Load existing data on startup automatically
        wallet.loadFromFile(FILE_PATH);

        boolean running = true;
        while (running) {
            System.out.println("\n===== PERSONAL FINANCE TRACKER =====");
            System.out.println("1. Add a Transaction (Income/Expense)");
            System.out.println("2. View All Transactions (Newest-First)");
            System.out.println("3. Filter Transactions by Category");
            System.out.println("4. Filter Transactions by Month");
            System.out.println("5. Delete a Transaction by ID");
            System.out.println("6. View Financial Report (Totals & Balances)");
            System.out.println("7. Manage Monthly Budgets");
            System.out.println("8. Save and Exit");
            System.out.print("Choose an option: ");

            String option = scan.nextLine().trim();
            switch (option) {
                case "1": {addTransactionFlow();break;}
                case "2": {showAllTransactions();break;}
                case "3": {filterByCategoryFlow();break;}
                case "4": {filterByMonthFlow();break;}
                case "5": {deleteTransactionFlow();break;}
                case "6": {viewReportFlow();break;}
                case "7": {budgetManagementFlow();break;}
                case "8": {
                    // FR-7.5: Data persisted on exit
                    wallet.saveToFile(FILE_PATH);
                    System.out.println("Data successfully saved. Goodbye!");
                    running = false;
                }
                default: System.out.println("Error: Invalid option. Choose between 1 and 8.");
            }
        }
        scan.close();
    }

    // === 1. TRANSACTION CREATION FLOW (Your original code extracted cleanly) ===
    private static void addTransactionFlow() {
        System.out.println("\n--- Add New Transaction ---");
        
        double amount = 0;
        while (amount <= 0) {
            System.out.print("Enter Amount: ");
            try {
                amount = Double.parseDouble(scan.nextLine());
                if (amount <= 0) System.out.println("Error: Amount must be greater than 0");
            } catch (NumberFormatException ex) {
                System.out.println("Error: Non-numeric input rejected.");
            }
        }

        LocalDate date = null;
        while (date == null) {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            try {
                LocalDate parsedDate = LocalDate.parse(scan.nextLine());
                if (parsedDate.isAfter(LocalDate.now())) {
                    System.out.println("Error: Future dates are rejected.");
                } else {
                    date = parsedDate;
                }
            } catch (DateTimeParseException ex) {
                System.out.println("Error: Invalid date format.");
            }
        }

        String selection = "";
        while (!selection.equalsIgnoreCase("Income") && !selection.equalsIgnoreCase("Expense")) {
            System.out.print("Is this Income or Expense?: ");
            selection = scan.nextLine().trim();
        }

        String note = "";
        while (true) {
            System.out.print("Enter Note (Optional - press enter to skip): ");
            note = scan.nextLine();
            if (note.length() > 100) System.out.println("Error: Note capped at 100 chars.");
            else break;
        }

        String id;
        if (selection.equalsIgnoreCase("Income")) {
            IncomeCategory cat = (IncomeCategory) promptEnum(true);
            id = note.trim().isEmpty() ? wallet.addIncomeTransaction(amount, cat, date) 
                                       : wallet.addIncomeTransaction(amount, cat, date, note);
        } else {
            ExpenseCategory cat = (ExpenseCategory) promptEnum(false);
            id = note.trim().isEmpty() ? wallet.addExpenseTransaction(amount, cat, date) 
                                       : wallet.addExpenseTransaction(amount, cat, date, note);
            
            // Budget warning trigger evaluation right after recording an expense
            if (wallet.isOverBudget(cat, date.getYear(), date.getMonthValue())) {
                System.out.println("\n WARNING: You have met or exceeded your monthly budget limit for " + cat + "!");
            }
        }
        System.out.println("Transaction Recorded! ID: " + id);
    }

    // VIEWING & FILTERING FLOWS 
    private static void showAllTransactions() {
        // FR-3.1: Newest first display strategy
        printTransactionList(wallet.allTransactions());
    }

    private static void filterByCategoryFlow() {
        System.out.println("Filter Type: (1) Income Categories (2) Expense Categories");
        String type = scan.nextLine();
        Category cat = type.equals("1") ? (Category) promptEnum(false) : (Category) promptEnum(true);
        
        printTransactionList(wallet.view(cat));
    }

    private static void filterByMonthFlow() {
        try {
            System.out.print("Enter Year (YYYY): ");
            int year = Integer.parseInt(scan.nextLine());
            System.out.print("Enter Month (1-12): ");
            int month = Integer.parseInt(scan.nextLine());

            printTransactionList(wallet.view(year, month));
        } catch (NumberFormatException e) {
            System.out.println("Error: Numeric values required.");
        }
    }

    // === 5. DELETING FLOW ===
    private static void deleteTransactionFlow() {
        System.out.print("Enter the ID of the transaction to delete: ");
        String id = scan.nextLine().trim();
        
        int sizeBefore = wallet.allTransactions().size();
        wallet.removeTransaction(id);
        
        // FR-4.2: Detect if something actually changed to verify ID validity
        if (wallet.allTransactions().size() < sizeBefore) {
            System.out.println("Success: Transaction removed.");
        } else {
            System.out.println("Error: ID not found. No changes made.");
        }
    }

    // === 6. REPORTING FLOW ===
    private static void viewReportFlow() {
        System.out.print("Scope report to: (1) All-Time (2) Specific Month: ");
        String scopeChoice = scan.nextLine();
        LocalDate scope = null;

        if (scopeChoice.equals("2")) {
            System.out.print("Enter sample date within that target month (YYYY-MM-DD): ");
            try {
                scope = LocalDate.parse(scan.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date context. Defaulting to all-time.");
            }
        }

        // FR-5.1 Calculations
        double inc = wallet.getTotalIncome(scope);
        double exp = wallet.getTotalExpense(scope);
        double bal = wallet.getNetBalance(scope);

        System.out.println("\n--- Financial Report Summary ---");
        System.out.printf("Total Income:  $%.2f\n", inc);
        System.out.printf("Total Expense: $%.2f\n", exp);
        System.out.printf("Net Balance:   $%.2f\n", bal);

        // FR-5.2 Breakdown Metrics
        System.out.println("\n--- Category Breakdown Expense Share ---");
        Map<ExpenseCategory, Double> breakdown = wallet.getExpenseBreakdown(scope);
        if (breakdown == null || breakdown.isEmpty() || exp == 0) {
            System.out.println("No expense data found in the chosen scope (0% across categories).");
        } else {
            for (Map.Entry<ExpenseCategory, Double> entry : breakdown.entrySet()) {
                double pct = (entry.getValue() / exp) * 100;
                System.out.printf("- %s: $%.2f (%.1f%% of total)\n", entry.getKey(), entry.getValue(), pct);
            }
        }
    }

    // === 7. BUDGET MANAGEMENT FLOW ===
    private static void budgetManagementFlow() {
        System.out.println("\nBudget Panel: (1) Set Limit (2) Check Status Report");
        String choice = scan.nextLine();
        
        if (choice.equals("1")) {
            ExpenseCategory cat = (ExpenseCategory) promptEnum(choice.equals("Income"));
            System.out.print("Enter Year (YYYY): ");
            int y = Integer.parseInt(scan.nextLine());
            System.out.print("Enter Month (1-12): ");
            int m = Integer.parseInt(scan.nextLine());
            System.out.print("Enter Limit Amount: ");
            double amt = Double.parseDouble(scan.nextLine());

            wallet.setMonthlyBudget(cat, y, m, amt);
        } else {
            // FR-6.4 Report Layout
            System.out.print("Enter Year (YYYY): ");
            int y = Integer.parseInt(scan.nextLine());
            System.out.print("Enter Month (1-12): ");
            int m = Integer.parseInt(scan.nextLine());

            System.out.println("\n--- Monthly Budget Ledger Report ---");
            for (ExpenseCategory ec : ExpenseCategory.values()) {
                double rem = wallet.getBudgetRemaining(ec, y, m);
                boolean over = wallet.isOverBudget(ec, y, m);
                
              //2  String limitStr = (rem == Double.MAX_VALUE) ? "Unlimited" : "Restricted";
                System.out.printf("Category: %-15s Status: %-12s Balance Remaining: %s\n", 
                                  ec, (over ? "OVER" : "OK"), (rem == Double.MAX_VALUE ? "N/A" : "$"+rem));
            }
        }
    }

    // === INTERNAL GENERIC HELPER FOR ENUMS ===
    private static Object promptEnum(boolean isIncome) {
    	while (true) {
            System.out.println("Available options:");
            
            if (isIncome) {
                for (IncomeCategory choice : IncomeCategory.values()) {
                    System.out.println(" - " + choice.name());
                }
            } else {
                for (ExpenseCategory choice : ExpenseCategory.values()) {
                    System.out.println(" - " + choice.name());
                }
            }

            System.out.print("Type choice exactly: ");
            String input = scan.nextLine().trim();

            try {
                if (isIncome) {
                    return IncomeCategory.valueOf(input); // Safely returns IncomeCategory Enum
                } else {
                    return ExpenseCategory.valueOf(input); // Safely returns ExpenseCategory Enum
                }
            } catch (IllegalArgumentException e) {
                System.out.println("\n[Error] '" + input + "' is not a valid category name. Match the case exactly.\n");
            }
    	}
    }
        
    // === PRINT CONTAINER
    private static void printTransactionList(List<Transaction> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("\n[!] No transactions found matching criteria.");
            return;
        }
        System.out.println("\n--- Search Results (" + list.size() + ") ---");
        for (Transaction t : list) {
            System.out.println(t.toString());
        }
    }
}