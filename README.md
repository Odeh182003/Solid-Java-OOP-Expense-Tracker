# 📊 Personal Finance & Expense Tracker CLI

A robust, console-based financial management system written in Java. This application allows users to securely log financial transactions, manage categories using polymorphic enums, track category-specific monthly budgets, and analyze financial reports. All transaction records are automatically saved and loaded across sessions via a text-based database file. This project was built to reinforce my understanding of Abstract classes and Interfaces after feedback from Jaffa .NET’s technical team.

---

## 🚀 Key Features

* **Polymorphic Transaction Engine:** Dynamically handle both `Income` and `Expense` records inheriting from an abstract `Transaction` model.
* **Automated Metadata Generation:** Every single transaction automatically receives a unique system-generated `UUID` signature.
* **Defensive Console Loops:** Validates input in real-time. Features automated protection against future dates, formatting mistakes, and out-of-bounds numbers.
* **Smart Budget Threshold Warnings:** Establish exact monthly spending limits per category. The CLI automatically fires immediate visual warnings if an entry causes a category to meet or exceed its cap.
* **Comprehensive Analytics Ledger:** Instantly extract total income, total expense, and net balances dynamically filtered by target months or calculated as all-time records.
* **Graceful File Persistence:** Automates state serialization to a flat file. Includes error-handling routines that skip corrupted data lines rather than letting the application crash on startup.

---

## 🛠️ Installation & Setup

### Prerequisites
* **Java Development Kit (JDK) 17** or higher.
* Terminal/CLI or an Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse.

### Quick Run Instructions
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/your-username/expense-tracker.git](https://github.com/your-username/expense-tracker.git)
   cd expense-tracker
   ```bash
   javac Main.java Wallet.java Transaction.java Income.java Expense.java
   ```bash
   java Main
# Fast review (Test Scenario)
* 1.  Initialize a Budget <br>
     Select option 7 (Manage Monthly Budgets), then choose 1 (Set Limit). Provide these sample parameters to restrict your monthly dining options:
    <ul>
       <li>Category: Food</li>
       <li>Year / Month: 2026 / 6</li>
       <li> Limit: 50</li>
    </ul> 
* 2. Record an Expense exceeding the budget<br>
Select option 1 (Add a Transaction) and choose Expense. Intentionally input a value that breaches your freshly made cap to check the immediate interceptor rules:
<ul>
   <li>Amount: 65.50</li>
  <li>Date: 2026-06-30</li>
  <li>Category: Food</li>
</ul><br>
Expected Outcome: The console will successfully output a unique generated UUID, instantly followed by a high-visibility terminal alert message:<br>
   WARNING: You have met or exceeded your monthly budget limit for Food!
   Transaction Recorded! ID: 3eb8bd3a-fa1b-4a85-a630-d76dcfc3e4f9
* 3. Audit & File Output<br>
<ul>
<li>Select option 2 (View All Transactions) to confirm the object has been cleanly loaded inside memory using the system's overwritten toString() layout. </li><li>Select option 8 (Save and Exit). Open the local directory file src/Transaction.txt to verify that your record has been cleanly written to persistent storage.</li>
</ul>
Note: Reviewer Team dinner
# Project Architecture & OOP Design
The codebase relies strictly on robust Object-Oriented Programming (OOP) design patterns, separation of concerns, and defensive data practices.
* There are two types of transactions either Income or Expense
* Abstract Transaction
* Expense & Income Classes children of Transaction abstract class
* IcomeCategory & ExpenseCategory Interfaces implementing Category Interface
* The TransactionActions interface defines the contract for what operations a user can perform on transactions — essentially the can-do capabilities such as adding, viewing, reporting, or deleting records.
* Wallet class extends Transaction and implement TransactionActions Interface to implement the user's operations
