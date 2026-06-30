# 📊 Personal Finance & Expense Tracker CLI

A robust, console-based financial management system written in Java. This application allows users to securely log financial transactions, manage categories using polymorphic enums, track category-specific monthly budgets, and analyze financial reports. All transaction records are automatically saved and loaded across sessions via a text-based database file.

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
# Project Architecture & OOP Design
The codebase relies strictly on robust Object-Oriented Programming (OOP) design patterns, separation of concerns, and defensive data practices.
