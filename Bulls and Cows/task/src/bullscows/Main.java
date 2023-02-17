package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        String lengthInput = sc.nextLine();
        int n;
        try {
            n = Integer.parseInt(lengthInput);
            if (n > 37) {
                System.out.println("Error cannot print longer than 37 digits.");
            } else if (n == 0) {
                System.out.println("Error cannot print a secret code with 0 digits.");
            } else {
                System.out.println("Input the number of possible symbols in the code:");
                int nSymbols = sc.nextInt();
                if (nSymbols < n){
                    System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", n, nSymbols);
                } else if (nSymbols > 36) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                } else {
                    char[] symbols = generateSymbols(nSymbols);

                    String genNumber = generateRandomNumber(n, symbols);
                    System.out.println("Okay, let's start a game!");
                    guessNumber(sc, genNumber);

                }
            }
        } catch (Exception e) {
            System.out.printf("Error: %s isn't a valid number.", lengthInput);
        }



    }

    public static char[] generateSymbols(int nSymbols) {
        char[] symbols = new char[nSymbols];
        int count = 0;
        for (int i = 0; i < nSymbols; i++) {
            if (i <= 9) {
                symbols[i] = (char) (i + '0');
            } else {
                symbols[i] = (char) (count + 97);
                count++;
            }

        }
        return symbols;
    }
    public static void guessNumber(Scanner sc, String secretCode) {
        int count = 0;
        do {
            int bulls = 0, cows = 0;
            System.out.printf("Turn %d:\n", count + 1);
            String ans = sc.next();
            for (int j = 0; j < secretCode.length(); j++) {
                if (secretCode.charAt(j) == ans.charAt(j)) {
                    bulls++;
                } else {
                    for (int k = 0; k < secretCode.length(); k++) {
                        if (k != j && secretCode.charAt(k) == ans.charAt(j)) {
                            cows++;
                        }
                    }
                }
            }

            if (bulls + cows == 0) {
                System.out.println("Grade: None.");
            } else if (bulls == secretCode.length()) {
                System.out.printf("Grade: %d cow(s) and %d bull(s). The secret code is %s.\n", cows, bulls, secretCode);
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else {
                System.out.printf("Grade: %d cow(s) and %d bull(s). The secret code is %s.\n", cows, bulls, secretCode);
            }
            count++;
        } while (true);

    }

    public static String generateRandomNumber(int numberLength, char[] symbols) {
        long pseudoRandomNumber = System.nanoTime();
        Random rand = new Random(pseudoRandomNumber);
        StringBuilder sb = new StringBuilder(numberLength);
        for (int i = 0; i < numberLength; i++) {
            do {
                int r = rand.nextInt(0, symbols.length);
                String s = String.valueOf(symbols[r]);
                if (sb.indexOf(s) == -1) {
                    sb.append(s);
                    break;
                }
            } while (true);
        }
        String hide = "*".repeat(symbols.length);
        System.out.printf("The secret is prepared: %s ", hide);
        if (symbols.length <= 10) {
            System.out.printf("(0-%c).", symbols[symbols.length - 1]);
        } else {
            System.out.printf("(0-9, a-%c).", symbols[symbols.length - 1]);
        }
        return sb.toString();

    }
}

