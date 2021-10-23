package ru.RG;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter path to test file: ");
            String pathToTest = in.nextLine();
            Scanner inputFile = new Scanner(new FileReader(pathToTest));
            ArrayList<String> rules = new ArrayList<>(0);
            while (inputFile.hasNext()) {
                rules.add(inputFile.nextLine());
            }
            RG rg = new RG(rules);
            rg.system.solution();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

    }
}
