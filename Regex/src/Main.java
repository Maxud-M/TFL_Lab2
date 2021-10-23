import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static boolean check1(String word) {
        if(!word.substring(0, 4).equals("aabb")) {
            return false;
        }
        int itr = 4;
        while(itr < word.length()) {
            if(!word.substring(itr, itr + 3).equals("abc")) {
                return false;
            }
            itr += 3;
        }
        return true;
    }

    public static boolean check2(String word) {
        for(int i = 0; i < 2; ++i) {
            if(word.charAt(i) == 'b' || word.charAt(i) == 'c') {
                return false;
            }
        }
        for(int i = 2; i < 4; ++i) {
            if(word.charAt(i) == 'a' || word.charAt(i) == 'c') {
                return false;
            }
        }
        int itr = 4;
        while(itr < word.length()) {
            if(word.charAt(itr) == 'b' || word.charAt(itr) == 'c' || word.charAt(itr + 1) == 'a' || word.charAt(itr + 1) == 'c'
                                        || word.charAt(itr + 2) == 'a' || word.charAt(itr + 2) == 'b') {
                return false;
            }
            itr += 3;
        }
        return true;

    }

    public static boolean check3(String word) {
        if(!word.substring(0, 4).equals("aabb")) {
            return false;
        }
        if(!word.substring(4, 7).equals("abc")) {
            return false;
        }
        int itr = 7;
        while(itr < word.length()) {
            if(!word.substring(itr, itr + 3).equals("abc")) {
                return false;
            }
            itr += 3;
        }
        return true;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter path to test file: ");
        String pathToTest = in.nextLine();
        Scanner inputFile = new Scanner(new FileReader(pathToTest));
        String word = inputFile.nextLine();
        long m = System.nanoTime();
        if(check1(word)) {
            System.out.println("OK");
        }
        System.out.println((double) (System.nanoTime() - m) / 1000000);
        m = System.nanoTime();
        if(check2(word)) {
            System.out.println("OK");
        }
        System.out.println((double) (System.nanoTime() - m) / 1000000);
        m = System.nanoTime();
        if(check3(word)) {
            System.out.println("OK");
        }
        System.out.println((double) (System.nanoTime() - m) / 1000000);


    }
}
