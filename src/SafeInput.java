import java.util.Scanner;
public class SafeInput {
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = ""; // Set this to zero length. Loop runs until it isn't
        do
        {
            System.out.print("\n" + prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        } while(retString.length() == 0);

        return retString;
    }

    public static int getInt(Scanner pipe, String prompt) {
        int retValue = 0;
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt()) {
                retValue = pipe.nextInt();
                pipe.nextLine();  // Clear the buffer
                done = true;
            } else {
                String trash = pipe.nextLine(); // Read and discard bad input
                System.out.println("You must enter a valid integer. You said: " + trash);
            }
        } while (!done);

        return retValue;
    }

    public static double getDouble(Scanner pipe, String prompt) {
        double retValue = 0;
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextDouble()) {
                retValue = pipe.nextDouble();
                pipe.nextLine(); // clear buffer
                done = true;
            } else {
                String trash = pipe.nextLine(); // read and throw away bad input
                System.out.println("You must enter a valid decimal number. You said: " + trash);
            }
        } while (!done);

        return retValue;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int retValue = 0;
        boolean done = false;

        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextInt()) {
                retValue = pipe.nextInt();
                pipe.nextLine(); // clear buffer

                if (retValue >= low && retValue <= high) {
                    done = true;
                } else {
                    System.out.println("Input is out of range. Try again.");
                }
            } else {
                String trash = pipe.nextLine(); // discard invalid input
                System.out.println("You must enter a valid integer. You said: " + trash);
            }
        } while (!done);

        return retValue;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retValue = 0;
        boolean done = false;

        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextDouble()) {
                retValue = pipe.nextDouble();
                pipe.nextLine(); // clear buffer

                if (retValue >= low && retValue <= high) {
                    done = true;
                } else {
                    System.out.println("Input is out of range. Try again.");
                }
            } else {
                String trash = pipe.nextLine(); // discard invalid input
                System.out.println("You must enter a valid decimal number. You said: " + trash);
            }
        } while (!done);

        return retValue;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String response = "";
        boolean isValid = false;

        do {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.nextLine().trim();

            if (response.equalsIgnoreCase("Y")) {
                return true;
            } else if (response.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("You must enter Y or N. You said: " + response);
            }
        } while (!isValid);

        return false;
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String response = "";
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + ": ");
            response = pipe.nextLine();

            if (response.matches(regEx)) {
                valid = true;
            } else {
                System.out.println("Input does not match the required pattern. Try again.");
            }
        } while (!valid);

        return response;
    }

    public static void prettyHeader(String msg) {
        final int LINE_WIDTH = 60;
        final int SIDE_STARS = 3;

        // Line 1: Top border
        for (int i = 0; i < LINE_WIDTH; i++) {
            System.out.print("*");
        }
        System.out.println();

        // Line 2: Centered message with 3 stars on each side
        int spaceForMsg = LINE_WIDTH - 2 * SIDE_STARS;
        int paddingTotal = spaceForMsg - msg.length();
        int paddingLeft = paddingTotal / 2;
        int paddingRight = paddingTotal - paddingLeft;

        // Left stars
        for (int i = 0; i < SIDE_STARS; i++) {
            System.out.print("*");
        }

        // Left padding
        for (int i = 0; i < paddingLeft; i++) {
            System.out.print(" ");
        }

        // Message
        System.out.print(msg);

        // Right padding
        for (int i = 0; i < paddingRight; i++) {
            System.out.print(" ");
        }

        // Right stars
        for (int i = 0; i < SIDE_STARS; i++) {
            System.out.print("*");
        }

        System.out.println();

        // Line 3: Bottom border
        for (int i = 0; i < LINE_WIDTH; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}