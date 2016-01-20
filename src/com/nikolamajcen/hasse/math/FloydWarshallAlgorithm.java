package com.nikolamajcen.hasse.math;

import com.nikolamajcen.hasse.helpers.CsvParser;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nikolamajcen on 20.01.16..
 */

public class FloydWarshallAlgorithm {

    public static String[] elements;
    private static int numberOfElements;
    public static int distanceMatrix[][];

    public static void openFile(File file) {
        getMatrixFromFile(file);
        validateMatrix();
    }

    public static void calculate() {
        if (distanceMatrix != null) {
            System.out.println();
            System.out.println("STEP 0.:");
            System.out.println("--------");
            showResults();

            for (int m = 0; m < numberOfElements; m++) {
                for (int i = 0; i < numberOfElements; i++) {
                    for (int j = 0; j < numberOfElements; j++) {
                        if(i == j || i == m || j == m) {
                            continue;
                        }
                        if (distanceMatrix[i][m] + distanceMatrix[m][j] < distanceMatrix[i][j]) {
                            distanceMatrix[i][j] = distanceMatrix[i][m] + distanceMatrix[m][j];
                        }
                    }
                }
                System.out.println();
                System.out.println("STEP " + String.valueOf(m + 1) + ".:");
                if(m > 9) {
                    System.out.println("---------");
                } else {
                    System.out.println("--------");
                }
                showResults();
            }
        } else {
            System.out.println("No file provided.");
        }
    }

    private static void showResults() {
        if (distanceMatrix == null) {
            System.out.println("No data provided.");
            return;
        }

        for (int i = 0; i < numberOfElements; i++) {
            if (elements[i].length() < 7) {
                System.out.print(elements[i] + ":\t\t\t");
            } else if (elements[i].length() < 11) {
                System.out.print(elements[i] + ":\t\t");
            } else {
                System.out.print(elements[i] + ":\t");
            }

            for (int j = 0; j < numberOfElements; j++) {
                int distance = distanceMatrix[i][j];

                String value;
                if (distance > 999) {
                    value = Integer.toString(distance);
                } else if (distance > 99) {
                    value = " " + Integer.toString(distance);
                } else if (distance > 9) {
                    value = "  " + Integer.toString(distance);
                } else {
                    value = "   " + Integer.toString(distance);
                }
                System.out.print("|" + value + "|");
            }
            System.out.println();
        }
    }

    private static void getMatrixFromFile(File file) {
        ArrayList<ArrayList<String>> values = CsvParser.toList(file);

        if (values != null && values.size() > 0) {
            numberOfElements = values.size();
            elements = new String[numberOfElements];
            distanceMatrix = new int[numberOfElements][numberOfElements];

            for (int i = 0; i < numberOfElements; i++) {
                elements[i] = CsvParser.getRowName(values.get(i));
                distanceMatrix[i] = CsvParser.getRowValues(values.get(i));
            }
            System.out.println("Matrix initialized.");
        } else {
            System.out.println("Error: Something is wrong with file.");
        }
    }

    private static void validateMatrix() {
        for (int i = 0; i < numberOfElements; i++) {
            for (int j = 0; j < numberOfElements; j++) {
                if (distanceMatrix[i][j] == -1) {
                    distanceMatrix[i][j] = 9999;
                }
            }
        }
    }
}
