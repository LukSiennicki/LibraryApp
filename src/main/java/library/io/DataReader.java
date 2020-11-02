package library.io;

import library.model.Book;
import library.model.LibraryUser;
import library.model.Magazine;

import java.util.Scanner;

public class DataReader {

    private Scanner scanner = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public void close() {
        scanner.close();
    }

    public int getInt() {
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }

    public String getString() {
        return scanner.nextLine();
    }

    public Book readAndCreateBook() {
        printer.printLine("Tytul");
        String title = scanner.nextLine();
        printer.printLine("Autor:");
        String author = scanner.nextLine();
        printer.printLine("Wydawnictwo:");
        String publisher = scanner.nextLine();
        printer.printLine("ISBN:");
        String isbn = scanner.nextLine();
        printer.printLine("Rok wydania: ");
        int releaseData = getInt();
        printer.printLine("Liczba stron:");
        int pages = getInt();

        return new Book(title, author, releaseData, pages, publisher, isbn);
    }

    public Magazine readAndCreateMagazine() {
        printer.printLine("Tytul");
        String title = scanner.nextLine();
        printer.printLine("Wydawnictwo:");
        String publisher = scanner.nextLine();
        printer.printLine("Jezyk:");
        String language = scanner.nextLine();
        printer.printLine("Rok wydania:");
        int year = getInt();
        printer.printLine("Miesiac: ");
        int month = getInt();
        printer.printLine("Dzien:");
        int day = getInt();

        return new Magazine(title, publisher, month, day, language, year);
    }

    public LibraryUser createLibraryUser() {
        printer.printLine("Imie:");
        String firstName = scanner.nextLine();
        printer.printLine("Nazwisko");
        String lastName = scanner.nextLine();
        printer.printLine("Pesel:");
        String pesel = scanner.nextLine();
        return new LibraryUser(firstName, lastName, pesel);
    }

}
