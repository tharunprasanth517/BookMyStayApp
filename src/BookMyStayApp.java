import java.util.*;

// Palindrome Service Class
class PalindromeChecker {

    public boolean checkPalindrome(String input) {

        if (input == null) return false;

        input = input.replaceAll("\\s+", "").toLowerCase();

        int start = 0;
        int end = input.length() - 1;

        while (start < end) {
            if (input.charAt(start) != input.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }

        return true;
    }
}

// ✅ YOUR MAIN CLASS NAME (CORRECT)
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PalindromeChecker checker = new PalindromeChecker();

        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        if (checker.checkPalindrome(input)) {
            System.out.println("It is a Palindrome ✅");
        } else {
            System.out.println("Not a Palindrome ❌");
        }

        sc.close();
    }
}