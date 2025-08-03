import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        boolean done = false;
        boolean needsToBeSaved = false;
        String currentFileName = null;

        do{
            displayList(list);
            String choice = SafeInput.getRegExString(in,
                    "Choose [A]Add, [D]Delete, [I]Insert, [M]Move, [V]View, [C]Clear, [O]Open, [S]Save, [Q]Quit",
                    "[AaDdIiMmVvCcOoSsQq]").toUpperCase();

            switch (choice) {
                case "A" -> {
                    addItem(list, in);
                    needsToBeSaved = true;
                }
                case "D" -> {
                    deleteItem(list, in);
                    needsToBeSaved = true;
                }
                case "I" -> {
                    insertItem(list, in);
                    needsToBeSaved = true;
                }
                case "M" -> {
                    moveItem(list, in);
                    needsToBeSaved = true;
                }
                case "C" -> {
                    list.clear();
                    needsToBeSaved = true;
                    System.out.println("List cleared.");
                }
                case "V" -> displayList(list);
                case "O" -> {
                    if (needsToBeSaved) promptToSaveCurrentList(list, currentFileName, in);
                    currentFileName = openFile(list, in);
                    needsToBeSaved = false;
                }
                case "S" -> {
                    if (currentFileName == null)
                        currentFileName = getNewFileName(in);
                    saveFile(list, currentFileName);
                    needsToBeSaved = false;
                }
                case "Q" -> {
                    if (needsToBeSaved) promptToSaveCurrentList(list, currentFileName, in);
                    if (SafeInput.getYNConfirm(in, "Are you sure you want to quit?")) done = true;
                }
            }

        }while(!done);
        System.out.println("You Quit");
    }
    private static void addItem(ArrayList<String> list, Scanner in) {
        String item = SafeInput.getNonZeroLenString(in, "Enter item to add");
        list.add(item);
    }

    private static void deleteItem(ArrayList<String> list, Scanner in) {
        if (list.isEmpty()) {
            System.out.println("List is empty, nothing to delete.");
            return;
        }
        displayNumberedList(list);
        int index = SafeInput.getRangedInt(in, "Enter index to delete", 1, list.size()) - 1;
        list.remove(index);
    }

    private static void insertItem(ArrayList<String> list, Scanner in) {
        String item = SafeInput.getNonZeroLenString(in, "Enter item to insert");
        int position = SafeInput.getRangedInt(in, "Enter position to insert at", 1, list.size() + 1) - 1;
        list.add(position, item);
    }

    private static void moveItem(ArrayList<String> list, Scanner in) {
        if (list.size() < 2) {
            System.out.println("Need at least two items to move.");
            return;
        }
        displayNumberedList(list);
        int fromIndex = SafeInput.getRangedInt(in, "Move which item?", 1, list.size()) - 1;
        int toIndex = SafeInput.getRangedInt(in, "To what position?", 1, list.size()) - 1;
        String item = list.remove(fromIndex);
        list.add(toIndex, item);
    }

    private static void displayList(ArrayList<String> list) {
        System.out.println("\nCurrent List:");
        if (list.isEmpty()) {
            System.out.println("[The list is currently empty]");
        } else {
            for (String item : list) {
                System.out.println("- " + item);
            }
        }
        System.out.println();
    }

    private static void displayNumberedList(ArrayList<String> list) {
        System.out.println("\nNumbered List:");
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d: %s\n", i + 1, list.get(i));
        }
        System.out.println();
    }

    private static void saveFile(ArrayList<String> list, String filename) {
        try {
            Files.write(Paths.get(filename), list, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("List saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static String openFile(ArrayList<String> list, Scanner in) {
        list.clear();
        String fileName = SafeInput.getNonZeroLenString(in, "Enter file name to open (.txt will be added)") + ".txt";
        try {
            List<String> fileContents = Files.readAllLines(Paths.get(fileName));
            list.addAll(fileContents);
            System.out.println("Loaded list from " + fileName);
            return fileName;
        } catch (IOException e) {
            System.out.println("Failed to load file: " + e.getMessage());
            return null;
        }
    }

    private static void promptToSaveCurrentList(ArrayList<String> list, String currentFileName, Scanner in) {
        if (SafeInput.getYNConfirm(in, "You have unsaved changes. Save now?")) {
            if (currentFileName == null)
                currentFileName = getNewFileName(in);
            saveFile(list, currentFileName);
        }
    }

    private static String getNewFileName(Scanner in) {
        return SafeInput.getNonZeroLenString(in, "Enter new file name (.txt will be added)") + ".txt";
    }
}
