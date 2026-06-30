import java.time.LocalDate;

public class Income extends Transaction{
	public Income(double amount, IncomeCategory category, LocalDate date, String note) {
		super(TransactionType.Income, amount, category, date, note);
}
public Income(double amount, IncomeCategory category, LocalDate date) {
	super(TransactionType.Income,amount,category,date);
}
public String toString() {
	return getId()+", "+TransactionType.Income+", "+getAmount()+", "+getDate()+", "+(getNote()==null?" ":getNote());
}
}
