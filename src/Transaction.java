//import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

abstract class Transaction {
private String id ;
private TransactionType type;
private double amount;
private Category category;
LocalDate date;
ExpenseCategory cat;
//private Date date;
private String note;
public Transaction() {
	this.id=UUID.randomUUID().toString();
}
public Transaction(TransactionType type, double amount,
		Category category, LocalDate date, String note) {
	this.id = UUID.randomUUID().toString();
	this.type = type;
	this.amount = amount;
	this.category = category;
	this.date = date;
	this.note = note;
}
public Transaction(TransactionType type, double amount,
		Category category, LocalDate date) {
	this.id = UUID.randomUUID().toString();
	this.type = type;
	this.amount = amount;
	this.category = category;
	this.date = date;
}
public TransactionType getType() {
	return type;
}
public void setType(TransactionType type) {
	this.type = type;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount2) {
	this.amount = amount2;
}

public Category getCategory() {
	return category;
}

public void setCategory(Category category) {
	this.category = category;
}
public LocalDate getDate() {
	return date;
}
public void setDate(LocalDate date) {
	this.date = date;
}
public String getNote() {
	return note;
}
public void setNote(String note) {
	this.note = note;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}


}
