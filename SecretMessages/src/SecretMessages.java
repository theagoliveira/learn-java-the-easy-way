import java.util.Scanner;

public class SecretMessages {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a message to encode or decode (or press Enter to quit):");
        String message = scan.nextLine();
        while (!message.isEmpty()) {
            StringBuilder bld = new StringBuilder();
            System.out.println("Enter a secret key (-25 to 25):");
            int keyVal = 13;

            // Programming Challenge #3 - Solution 1
            boolean validKey = false;
            while (!validKey) {
                try {
                    keyVal = Integer.parseInt(scan.nextLine()) % 26;
                    validKey = true;
                } catch (Exception e) {
                    System.out.println("You entered and invalid key. Try again:");
                    validKey = false;
                }
            }
            // Programming Challenge #3 - Solution 2
            /*
             * try { keyVal = Integer.parseInt(scan.nextLine()) % 26; } catch (Exception e)
             * { System.out.println("You entered and invalid key. Using 13 instead."); }
             */

            char key = (char) keyVal;
            for (int i = message.length() - 1; i >= 0; i--) {
                char input = message.charAt(i);
                if (input >= 'A' && input <= 'Z') {
                    input += key;
                    if (input > 'Z')
                        input -= 26;
                    if (input < 'A')
                        input += 26;
                } else if (input >= 'a' && input <= 'z') {
                    input += key;
                    if (input > 'z')
                        input -= 26;
                    if (input < 'a')
                        input += 26;
                } else if (input >= '0' && input <= '9') {
                    input += (keyVal % 10);
                    if (input > '9')
                        input -= 10;
                    if (input < '0')
                        input += 10;
                }
                bld.append(input);
            }
            String output = bld.toString();
            System.out.println(output);
            System.out.println("Enter a message to encode or decode (or press Enter to quit):");
            message = scan.nextLine();
        }

        scan.close();
    }
}
