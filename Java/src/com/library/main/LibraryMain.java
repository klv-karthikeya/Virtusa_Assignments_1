package com.library.main;

import com.library.data.repository.*;
import com.library.domain.repository.*;
import com.library.domain.usecase.*;
import com.library.presentation.console.LibraryConsolePresenter;

public class LibraryMain {
    public static void main(String[] args) {
        IBookRepository bookRepo = new MySQLBookRepository();
        IUserRepository userRepo = new MySQLUserRepository();
        ITransactionRepository transactionRepo = new MySQLTransactionRepository();

        IBookUseCase bookUseCase = new BookInteractor(bookRepo);
        IUserUseCase userUseCase = new UserInteractor(userRepo);
        ITransactionUseCase transactionUseCase = new TransactionInteractor(bookRepo, userRepo, transactionRepo);

        LibraryConsolePresenter presenter = new LibraryConsolePresenter(bookUseCase, userUseCase, transactionUseCase);
        presenter.run();
    }
}
