
public enum IncomeCategory implements Category {
	
	Salary, Freelance, Gift, Other;
;

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return "Income";
	}
	
}
