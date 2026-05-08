# Library Management System: Code Deep Dive

This document provides a detailed explanation of every component in the Library Management System codebase, organized by layer and functionality.

---

## 1. High-Level Architecture: Clean Architecture
The project follows **Clean Architecture** principles. The main goal is **Separation of Concerns**:
*   **Domain**: Business rules (Entities, Use Cases).
*   **Data**: Infrastructure (Databases, Repository Implementations).
*   **Presentation**: UI (Console).
*   **Main**: Initialization and Dependency Injection.

---

## 2. Package Breakdown

### A. Domain Layer (`com.library.domain`)
The "Brain" of the application. It knows *what* the system does but not *how* data is stored.

#### Entities (`entity`)
- **`Book.java`**: Represents a book with ID, Title, Author, ISBN, and availability status.
- **`User.java`**: Represents a library member.
- **`Transaction.java`**: Represents the lending of a book to a user. It calculates due dates and stores fine amounts.

#### Repositories (`repository`)
- **`IBookRepository.java`, `IUserRepository.java`, `ITransactionRepository.java`**: These are **Interfaces**. They define a contract. Any class that implements `IBookRepository` *must* provide a way to `save`, `delete`, and `findById`. This allows us to swap databases easily.

#### Use Cases / Interactors (`usecase`)
- **`BookInteractor.java`**: Handles logic like adding a book or removing one.
- **`UserInteractor.java`**: Handles user management.
- **`TransactionInteractor.java`**: The most complex part. It handles:
    - **Issuing Books**: Checks if the book and user exist, and if the book is available.
    - **Returning Books**: Marks the book as available and **calculates fines** if the book is returned after the due date.

---

### B. Data Layer (`com.library.data`)
The "Hands" of the application. It handles the actual storage.

#### Database (`database`)
- **`DatabaseConnection.java`**: A utility class that uses JDBC (`DriverManager`) to connect to a MySQL database running on `localhost:3306`. It uses the credentials `root/root`.

#### Repository Implementations (`repository`)
- **`InMemory...Repository.java`**: Uses `List` or `Map` to store data in memory. Fast for testing, but data is lost when the program stops.
- **`MySQL...Repository.java`**: Uses SQL queries (`INSERT`, `SELECT`, `UPDATE`, `DELETE`) to talk to the database.
    - **`ON DUPLICATE KEY UPDATE`**: Used in `save()` to either insert a new record or update an existing one if the ID already exists.

---

### C. Presentation Layer (`com.library.presentation`)
- **`LibraryConsolePresenter.java`**: The User Interface.
    - Uses a `Scanner` to read keyboard input.
    - Contains a large `while(true)` loop with a `switch` statement to handle menu options.
    - It calls the **Interactors** to perform work.

---

### D. Main Layer (`com.library.main`)
- **`LibraryMain.java`**: The entry point.
    - This is where **Dependency Injection** happens.
    - We create the `MySQL...Repository` objects and "inject" them into the `Interactors`.
    - Finally, we start the `Presenter`.

---

## 3. Java Feature: `Optional<T>`
`Optional` is used heavily in the repository and interactor layers to handle missing data safely.

### What is it?
It's a container that may or may not contain a non-null value.

### Usage in this code:
1.  **Returning from Repo**: `findById(id)` returns `Optional<Book>`. If the book isn't in the database, it returns `Optional.empty()` instead of `null`.
2.  **Checking Presence**: `bookOpt.isPresent()` or `bookOpt.isEmpty()` is used to check if a record was found.
3.  **Getting the Value**: `bookOpt.get()` retrieves the book (only do this after checking `isPresent()`).
4.  **Functional Approach**: 
    ```java
    bookRepository.findById(bookId).ifPresent(b -> {
        b.setAvailable(true);
        bookRepository.save(b);
    });
    ```
    This code only runs if the book exists, making it very safe and readable.

---

## 4. Key Logic: Fine Calculation
In `TransactionInteractor.java`, the system calculates a fine of **₹10.0 per day** if a book is returned late:
```java
long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
if (daysLate > 0) {
    double fine = daysLate * FINE_PER_DAY;
}
```
It uses the `java.time` API (`LocalDate`), which is the modern way to handle dates in Java.
