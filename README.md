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
* 1. Initialize a Budget
     Select option 7 (Manage Monthly Budgets), then choose 1 (Set Limit). Provide these sample parameters to restrict your monthly dining options:
     Category: Food
     Year / Month: 2026 / 6
     Limit: 50

# Project Architecture & OOP Design
The codebase relies strictly on robust Object-Oriented Programming (OOP) design patterns, separation of concerns, and defensive data practices.
* ** There are two types of transactions either Income or Expense
* ** Abstract Transaction
* ** Expense & Income Classes children of Transaction abstract class
* ** IcomeCategory & ExpenseCategory Interfaces implementing Category Interface
* **The TransactionActions interface defines the contract for what operations a user can perform on transactions — essentially the can-do capabilities such as adding, viewing, reporting, or deleting records.
* ** Wallet class extends Transaction and implement TransactionActions Interface to implement the user's operations
