package com.library.presentation.console;

import com.library.domain.usecase.IBookUseCase;
import com.library.domain.usecase.IUserUseCase;
import com.library.domain.usecase.ITransactionUseCase;
import java.util.Scanner;

public class LibraryConsolePresenter {
    private final IBookUseCase bookUseCase;
    private final IUserUseCase userUseCase;
    private final ITransactionUseCase transactionUseCase;
    private final Scanner scanner;

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";

    public LibraryConsolePresenter(IBookUseCase bookUseCase, IUserUseCase userUseCase, ITransactionUseCase transactionUseCase) {
        this.bookUseCase = bookUseCase;
        this.userUseCase = userUseCase;
        this.transactionUseCase = transactionUseCase;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println(CYAN + "=== MySQL Library Management System ===" + RESET);
        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            if (choice.equals("0")) break;
            handleChoice(choice);
        }
    }

    private void printMenu() {
        System.out.println("\n" + YELLOW + "1. Add Book | 2. Remove Book | 3. Register User");
        System.out.println("4. Issue Book | 5. Return Book | 6. Search Books");
        System.out.println("7. List Books | 8. List Transactions | 0. Exit" + RESET);
        System.out.print(BLUE + "Choice: " + RESET);
    }

    private void handleChoice(String choice) {
        try {
            switch (choice) {
                case "1": addBook(); break;
                case "2": removeBook(); break;
                case "3": registerUser(); break;
                case "4": issueBook(); break;
                case "5": returnBook(); break;
                case "6": searchBooks(); break;
                case "7": listBooks(); break;
                case "8": listTransactions(); break;
                default: System.out.println(RED + "Invalid choice." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Error: " + e.getMessage() + RESET);
        }
    }

    private void addBook() {
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Title: "); String title = scanner.nextLine();
        System.out.print("Author: "); String author = scanner.nextLine();
        System.out.print("ISBN: "); String isbn = scanner.nextLine();
        bookUseCase.addBook(id, title, author, isbn);
        System.out.println(GREEN + "Success!" + RESET);
    }

    private void removeBook() {
        System.out.print("ID: "); String id = scanner.nextLine();
        if (bookUseCase.removeBook(id)) System.out.println(GREEN + "Success!" + RESET);
        else System.out.println(RED + "Not found." + RESET);
    }

    private void registerUser() {
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        userUseCase.registerUser(id, name, email);
        System.out.println(GREEN + "Success!" + RESET);
    }

    private void issueBook() {
        System.out.print("Book ID: "); String bId = scanner.nextLine();
        System.out.print("User ID: "); String uId = scanner.nextLine();
        System.out.print("Days: "); int days = Integer.parseInt(scanner.nextLine());
        System.out.println(GREEN + transactionUseCase.issueBook(bId, uId, days) + RESET);
    }

    private void returnBook() {
        System.out.print("Book ID: "); String bId = scanner.nextLine();
        System.out.println(GREEN + transactionUseCase.returnBook(bId) + RESET);
    }

    private void searchBooks() {
        System.out.print("Query: "); String q = scanner.nextLine();
        bookUseCase.searchBooks(q).forEach(b -> System.out.println(PURPLE + b + RESET));
    }

    private void listBooks() {
        bookUseCase.getAllBooks().forEach(b -> System.out.println(CYAN + b + RESET));
    }

    private void listTransactions() {
        transactionUseCase.getAllTransactions().forEach(t -> System.out.println(YELLOW + t + RESET));
    }
}
