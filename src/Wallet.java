import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet implements TransactionActions {
private ArrayList<Transaction> ar = new ArrayList<Transaction>();
Map<ExpenseCategory,Double> map = new HashMap<>();
private Map<String, Double> budgetMap = new HashMap<>();

	@Override
	public String addIncomeTransaction(double amount, IncomeCategory cat, LocalDate date, String note) {
		Income income = new Income(amount, cat, date, note);
//		income.setAmount(amount);
//		income.setCategory(cat);
//		income.setDate(date);
//		income.setNote(note);
		ar.add(income);
		return income.getId();
	}

	@Override
	public String addIncomeTransaction(double amount, IncomeCategory cat, LocalDate date) {
		Income income = new Income(amount, cat, date);
		ar.add(income);

		return income.getId();
	}

	@Override
	public String addExpenseTransaction(double amount, ExpenseCategory cat, LocalDate date, String note) {
		// TODO Auto-generated method stub
		Expense expense = new Expense(amount, cat, date, note);
		ar.add(expense);
		return expense.getId();
		
	}

	@Override
	public String addExpenseTransaction(double amount, ExpenseCategory cat, LocalDate date) {
		// TODO Auto-generated method stub
		Expense expense = new Expense(amount, cat, date);
		ar.add(expense);
		return expense.getId();
	}

	@Override
	public void removeTransaction(String id) {
		// TODO Auto-generated method stub
		ar.removeIf(transaction -> transaction.getId().equals(id));		
	}

	@Override
	public List<Transaction> allTransactions() {
		// TODO Auto-generated method stub
		
		return ar;
	}

	@Override
	public List<Transaction> view(Category category) {
		// TODO Auto-generated method stub
		ArrayList<Transaction> filtered = new ArrayList<>();
		for(Transaction tr: ar) {
			if(tr.getCategory()!=null && tr.getCategory().equals(category)) {
				filtered.add(tr);
			}
		}
		
		return filtered;
	}

	@Override
	public List<Transaction> view(int year, int month) {
		// TODO Auto-generated method stub
		ArrayList<Transaction> filtered = new ArrayList<>();
		for(Transaction tr: ar) {
			LocalDate date = tr.getDate();
			if(date !=null) {
				if(date.getYear()==year && date.getMonthValue()==month) {
					filtered.add(tr);
				}
			}
		}
		
		return filtered;
	}

	@Override
	public double getTotalIncome(LocalDate monthScope) {
		// TODO Auto-generated method stub
		double total=0;
		for(Transaction tran: ar) {
			if(tran instanceof Income) {
				// If monthScope is null, treat it as "All-Time"

				if(monthScope == null) {
					total+=tran.getAmount();
				}
				else if(tran.getDate().getYear()==monthScope.getYear()&& tran.getDate().getMonth()==monthScope.getMonth()) {
					total+=tran.getAmount();
				}
			}
		}
		
		return total;
	}

	@Override
	public double getTotalExpense(LocalDate monthScope) {
		// TODO Auto-generated method stub
		double total=0;
		for(Transaction tran:ar) {
			if(tran instanceof Expense) {
				// If monthScope is null, treat it as "All-Time"
				if(monthScope == null) {
					total+=tran.getAmount();
				}
			else if(tran.getDate().getYear() == monthScope.getYear() && tran.getDate().getMonth() == monthScope.getMonth()) {
					total+=tran.getAmount();
				}
			}
		}
		return total;
	}

	@Override
	public double getNetBalance(LocalDate monthScope) {//balance = getTotalIncome-getTotalExpe
		// TODO Auto-generated method stub
		return getTotalIncome(monthScope)- getTotalExpense(monthScope);
	}

	@Override
	public Map<ExpenseCategory, Double> getExpenseBreakdown(LocalDate monthScope) {
		// TODO Auto-generated method stub
		//ExpenseCategory[] cat=ExpenseCategory.values();// values of the ENUM
		//I want to get  the amount spent on each ENUM
		for(Transaction tr:ar) {
			if(tr instanceof Expense e && (monthScope ==null || (tr.getDate().getYear() == monthScope.getYear() &&tr.getDate().getMonth() == monthScope.getMonth() ))) {
				ExpenseCategory category =(ExpenseCategory) e.getCategory();
				map.merge( category, tr.getAmount(), Double::sum);

			}
			
		}
//			if(getDate().getMonth() == monthScope.getMonth()) {
				
//			}
		
		
		return map;
	}

	@Override
	public void setMonthlyBudget(ExpenseCategory cat, int year, int month, double limitAmount) {
		String key = cat.name() + "-" + year + "-" + String.format("%02d", month);
		budgetMap.put(key, limitAmount);
		System.out.println("Budget set for " + cat + " (" + year + "-" + month + "): $" + limitAmount);
	}
	private double spentIn(ExpenseCategory cat, int year, int month) {
	    double totalSpent = 0;

	    for (Transaction tran : ar) {
	        // 1. Must be an Expense
	        if (tran instanceof Expense) {
	            Expense exp = (Expense) tran;
	            
	            // 2. Must match the exact category and the target Year/Month
	            if (exp.getCategory() == cat && 
	                exp.getDate().getYear() == year && 
	                exp.getDate().getMonthValue() == month) {
	                
	                totalSpent += exp.getAmount();
	            }
	        }
	    }
	    return totalSpent;
	}
	@Override
	public double getBudgetRemaining(ExpenseCategory cat, int year, int month) {
		// TODO Auto-generated method stub
		String key = cat.name() + "-" + year + "-" + String.format("%02d", month);
	    
	    // FR-6.5: If no limit is set, treat it as unlimited. 
	    // We will return Double.MAX_VALUE as a sentinel value to signify "Unlimited"
	    if (!budgetMap.containsKey(key)) {
	        return Double.MAX_VALUE; 
	    }
	    
	    double limit = budgetMap.get(key);
	    double spent = spentIn(cat, year, month);
	    
	    return limit - spent;
		
	}

	@Override
	public boolean isOverBudget(ExpenseCategory cat, int year, int month) {
		// TODO Auto-generated method stub
		String key = cat.name()+"-"+year+"-"+String.format("%02d", month);
		if (!budgetMap.containsKey(key)) {
	        return false;
	    }
		double limit = budgetMap.get(key);
	    double spent = spentIn(cat, year, month);
	    
	    // Returns true if spent meets or exceeds the monthly limit
	    return spent >= limit;
	}

	@Override
	public void saveToFile(String filename) {
		// TODO Auto-generated method stub
		File f = new File(filename);
		try {
			FileWriter write = new FileWriter(f);
			for(Transaction tran:ar) {
				write.append(tran.toString());

			}
			System.out.println("Data successfully saved to " + filename);
			write.close();
	    } catch (IOException e) {
	        System.out.println("Error saving to file: " + e.getMessage());
	    }
		
		
	}

	@Override
	public String loadFromFile(String filename) {
		File f = new File(filename);
	    
	    // If the file doesn't exist yet, gracefully skip loading (FR-7.2)
	    if (!f.exists()) {
	        System.out.println("No existing data file found. Starting with an empty dataset.");
	        return "No file found";
	    }

	    try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            try {
	                // Here, you would parse the line back into an Income or Expense object
	                // For now, we print it out to verify it works
	                System.out.println("Loaded record: " + line);
	                
	            } catch (Exception parseException) {
	                // FR-7.4: Skip malformed records with a warning instead of crashing
	                System.out.println("Warning: Skipping malformed line in file: " + line);
	            }
	        }
	        return "Successful read";
	    } catch (IOException e) {
	        System.out.println("Error reading file: " + e.getMessage());
	        return "Read failed";
	    }
		
	}

}
