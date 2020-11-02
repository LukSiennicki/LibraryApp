package library.io.file;

import library.exception.NoSuchFileTypeException;
import library.io.ConsolePrinter;
import library.io.DataReader;

import java.io.File;

public class FileManagerBuilder {
    private ConsolePrinter printer;
    private DataReader dataReader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader dataReader) {
        this.printer = printer;
        this.dataReader = dataReader;
    }

    public FileManager build() {
        printer.printLine("Wybierz format danych: ");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL:
                return new SerializableFileManager();
            case CSV:
                return new CsvFileManager();
            default:
                throw new NoSuchFileTypeException("Nieobslugiwany typ danych");
        }

    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String string = dataReader.getString().toUpperCase();
            try {
                result = FileType.valueOf(string);
                typeOk = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Nieobslugiwany tym danych, wybierz ponownie.");
            }
        } while (!typeOk);
        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }
}
