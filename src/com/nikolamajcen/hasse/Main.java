package com.nikolamajcen.hasse;

import com.nikolamajcen.hasse.helpers.CsvParser;
import com.nikolamajcen.hasse.math.FloydWarshallAlgorithm;

import java.io.File;
import java.util.Scanner;

public class Main {

    // Change to your path :)
    private static final String PATH = "/home/nikolamajcen/Development/HasseProject/src/com/nikolamajcen/hasse/files/";

    public static void main(String[] args) {

        File file = new File(PATH + "distances.csv");
        // File file = new File(PATH + "example1.csv");
        // File file = new File(PATH + "example2.csv");
        // File file = new File(PATH + "example3.csv");

        FloydWarshallAlgorithm.openFile(file);
        FloydWarshallAlgorithm.calculate();

        System.out.print("\nSave results to file? (Y/N): ");
        Scanner scanner = new Scanner(System.in);
        String saveResults = scanner.next();

        if(saveResults.toUpperCase().equals("Y")) {
            System.out.print("Filename: ");
            String filename = scanner.next();
            CsvParser.toCsv(FloydWarshallAlgorithm.elements,
                    FloydWarshallAlgorithm.distanceMatrix, filename, PATH);
        }
    }
}
