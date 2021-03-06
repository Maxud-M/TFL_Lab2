package ru.evistix28;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SolutionOfSystem {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter path to test file: ");
            String pathToTest = in.nextLine();
            Scanner inputFile = new Scanner(new FileReader(pathToTest));
            ArrayList<String> equations = new ArrayList<>(0);
            while (inputFile.hasNext()) {
                equations.add(inputFile.nextLine());
            }
            SOE s = new SOE(equations);
            s.solution();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

    }
}
