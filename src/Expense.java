import java.time.LocalDate;

public class Expense extends Transaction{
	public Expense(double amount, ExpenseCategory cat, LocalDate date, String note) {	
		super(TransactionType.Expense, amount, cat, date, note);
}
public Expense(double amount, ExpenseCategory cat, LocalDate date) {	
	super(TransactionType.Expense, amount, cat, date);
}
public String toString() {
	String categoryName = (getCategory() != null) ? getCategory().toString() : "Unknown";
	String noteText = (getNote() == null || getNote().trim().isEmpty()) ? " " : getNote();
	return getId()+", "+TransactionType.Expense+", "+getAmount()+", "+categoryName+", "+getDate()+", "+noteText;
}
public ExpenseCategory getExpenseCategory() {
	return cat;
}
}