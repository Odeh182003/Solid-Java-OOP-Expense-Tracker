
public enum ExpenseCategory implements Category{
	Food, Rent, Transport, Utilities, Entertainment, Health, Other;

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return "Expense";
	}

}
