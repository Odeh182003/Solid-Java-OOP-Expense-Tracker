import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionActions {
	// ADD INCOME TRANSACTION WITH NOTE
	public String addIncomeTransaction(double amount, IncomeCategory cat, LocalDate date, String note);

// ADD INCOME TRANSACTION WITHOUT NOTE
	String addIncomeTransaction(double amount, IncomeCategory cat, LocalDate date);

// ADD EXPENSE TRANSACTION WITH NOTE
	public String addExpenseTransaction(double amount, ExpenseCategory cat, LocalDate date, String note);

// ADD EXPENSE TRANSACTION WITHOUT NOTE
	String addExpenseTransaction(double amount, ExpenseCategory cat, LocalDate date);

//DELETE TRANSACTION BY ITS ID
	public void removeTransaction(String id);

//PRINTS A LIST OF ALL TRANSACTIONS
	public List<Transaction> allTransactions();

//PRINTS A LIST OF TRANSACTION FILTERED BY CATEGORY
	public List<Transaction> view(Category category);

//PRINTS A LIST OF TRANSACTION FILTERED BY YEAR AND MONTH
	public List<Transaction> view(int year, int month);
//REPORTING

	double getTotalIncome(LocalDate monthScope);

	double getTotalExpense(LocalDate monthScope);

// Returns net balance (Income - Expense) for all-time or a given month
	double getNetBalance(LocalDate monthScope);

// FR-5.2: Returns each category mapped to its total spent and percentage share
// e.g., Map Key: ExpenseCategory.Food -> Value: Total Amount Spent
	Map<ExpenseCategory, Double> getExpenseBreakdown(LocalDate monthScope);

// === BUDGETING

// FR-6.1: Sets a limit for a category on a specific monthly basis
	void setMonthlyBudget(ExpenseCategory cat, int year, int month, double limitAmount);

// FR-6.2 & FR-6.4: Retrieves the amount remaining or if it's over budget
	double getBudgetRemaining(ExpenseCategory cat, int year, int month);

	boolean isOverBudget(ExpenseCategory cat, int year, int month);

// === PERSISTENCE 
	void saveToFile(String filename);

	String loadFromFile(String filename);

}
