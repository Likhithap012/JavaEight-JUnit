# JavaEight-JUnit 

This repository contains five Java projects that demonstrate the practical use of **Java 8 features**, **custom exception handling**, and **JUnit testing**. Each project is designed to highlight core Java concepts like **Streams**, **Lambdas**, **Optionals**, and **Functional Interfaces**, along with robust unit testing practices.

---

##  Projects 

### 1.  Inventory Management System
**Scenario:** Manage products in a retail inventory system.

**Highlights:**
- Filter low-stock items using **Java Streams**
- Sort products by **category** and **price**
- Handle edge cases with **custom exceptions**:
    - `ProductNotFoundException`
    - `InvalidProductException`
- Services for adding, removing, and searching products
- **JUnit test coverage** for valid and invalid operations

---

### 2.  Student Grading System
**Scenario:** Calculate averages and assign grades to students.

**Highlights:**
- Compute average marks with **Streams**
- Assign grades using **Lambdas** and **Functional Interfaces**
- Handle errors with **custom exceptions**:
    - `EmptyMarkListException`
    - `InvalidMarkException`
- **JUnit tests** for grading logic and input validation

---

### 3.  Online Order Processing
**Scenario:** Validate orders in an e-commerce system.

**Highlights:**
- Use **Optional** for nullable promo codes
- Apply **Predicate** for payment and address validation
- Handle failures with **custom exceptions**:
    - `InvalidPaymentException`
    - `InvalidAddressException`
- **JUnit tests** for the order lifecycle and edge cases

---

### 4.  Employee Leave Tracker
**Scenario:** Track employee leaves and balance checks.

**Highlights:**
- Track and calculate leave balance using **Java 8 features**
- Use **Lambdas** for defining leave rules
- Identify employees with low leave balance
- Custom exceptions:
    - `LeaveLimitExceededException`
    - `InvalidLeaveDateException`
- **JUnit tests** for leave applications and validations

---

### 5.  Library Book Lending System
**Scenario:** Manage book borrowing and returning in a library.

**Highlights:**
- Use **Streams** to filter books by author or genre
- Use **Optional** to handle unavailable books
- Custom exceptions:
    - `BookUnavailableException`
    - `BookLimitExceededException`
- **JUnit tests** for borrow/return logic and failure cases

---

##  Technologies Used

- **Java 8**
    - Streams
    - Lambdas
    - Optionals
    - Functional Interfaces
    - Predicates
- **Custom Exception Handling**
- **JUnit 4/5** for Unit Testing

---

## How to Run

1. Clone the repository  
   `git clone https://github.com/your-username/JavaEight-JUnit.git`

2. Open the project in your preferred IDE (e.g., IntelliJ, Eclipse)

3. Run the `main` classes or JUnit test cases under each module to see the output.

---

##  Test Coverage

Each project includes comprehensive **JUnit test cases** to validate:
- Positive scenarios (successful operations)
- Negative scenarios (exceptions and edge cases)
- Boundary conditions (e.g., grade limits, stock limits)



