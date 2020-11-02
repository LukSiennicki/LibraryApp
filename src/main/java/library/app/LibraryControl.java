package library.app;

import library.exception.*;
import library.io.ConsolePrinter;
import library.io.DataReader;
import library.io.file.FileManager;
import library.io.file.FileManagerBuilder;
import library.model.*;


import java.util.Comparator;
import java.util.InputMismatchException;

public class LibraryControl {
    private Library library;
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private FileManager fileManager;
    private DataReader dataReader = new DataReader(consolePrinter);

    LibraryControl() {
        fileManager = new FileManagerBuilder(consolePrinter, dataReader).build();
        try {
            library = fileManager.importData();
            consolePrinter.printLine("Zaimportowano dane z pliku");
        } catch (DataImportException | InvalidDataException e) {
            consolePrinter.printLine(e.getMessage());
            consolePrinter.printLine("Zainicjonowano nowa baze");

            library = new Library();
        }

    }

    void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case FIND_BOOK:
                    findBook();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    consolePrinter.printLine("Nie ma takiej opcji, wprowadz ponownie");
            }
        } while (option != Option.EXIT);
    }

    private void findBook() {
        consolePrinter.printLine("Podaj tytul publikacji");
        String title = dataReader.getString();
        String notFoundMessage = "Brak publikacji o takim tytule";
        library.findPublicationbyTitle(title)
                .map(Publication::toString)
                .ifPresentOrElse(
                        System.out::println,
                        () -> consolePrinter.printLine(notFoundMessage)
                );
    }

    private void printUsers() {
        consolePrinter.printUsers(library.getSortedUsers(
                Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        } catch (UserAlreadyExsistsException e) {
            consolePrinter.printLine(e.getMessage());
        }
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                consolePrinter.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                consolePrinter.printLine("Wprowadzono wartosc, ktora nie jest liczba, podaj ponownie:");
            }
        }
        return option;
    }

    private void printOptions() {
        consolePrinter.printLine("Wybierz opcje:");
        Option[] values = Option.values();
        for (Option option : values) {
            System.out.println(option.toString());
        }
    }

    private void printMagazines() {
        consolePrinter.printMagazine(library.getSortedPublication(
                Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublications(magazine);
        } catch (InputMismatchException e) {
            consolePrinter.printLine("Nie udalo sie utworzyc magazynu, niepoprawne dane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            consolePrinter.printLine(e.getMessage());
        }
    }

    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine)) {
                consolePrinter.printLine("Usunieto magazyn");
            } else {
                System.out.println("Brak wskazanego magazynu");
            }
        } catch (InputMismatchException e) {
            consolePrinter.printLine("Nie udalo sie usunac magazynu, niepoprawne dane");
        }

    }

    private void exit() {
        try {
            fileManager.exportData(library);
            consolePrinter.printLine("Export danych do pliku zakonczony powodzeniem");
        } catch (DataExportException e) {
            consolePrinter.printLine(e.getMessage());
        }
        consolePrinter.printLine("Koniec programu, papa!");
        dataReader.close();
    }

    private void printBooks() {
        consolePrinter.printBooks(library.getSortedPublication(
                Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublications(book);
        } catch (InputMismatchException e) {
            consolePrinter.printLine("Nie udalo sie utworzyc ksiazki, niepoprawne dane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            consolePrinter.printLine(e.getMessage());
        }
    }

    private void deleteBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book)) {
                consolePrinter.printLine("Usunieto ksiazke");
            } else {
                System.out.println("Brak wskazanej ksiazki");
            }
        } catch (InputMismatchException e) {
            consolePrinter.printLine("Nie udalo sie usunac ksiazki, niepoprawne dane");
        }
    }

    public enum Option {
        EXIT(0, "wyjscie z programu"),
        ADD_BOOK(1, "dodanie nowej ksiazki"),
        ADD_MAGAZINE(2, "dodanie nowego magazynu"),
        PRINT_BOOKS(3, "wyswietl dostepne ksiazki"),
        PRINT_MAGAZINES(4, "wyswietl dostepne magazyny"),
        DELETE_BOOK(5, "usun ksiazke"),
        DELETE_MAGAZINE(6, "usun magazyn"),
        ADD_USER(7, "Dodaj czytelnika"),
        PRINT_USERS(8, "Wyswietl czytelnikow"),
        FIND_BOOK(9,"Wyszukaj ksiazke");

        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id  " + option);
            }

        }
    }
}
