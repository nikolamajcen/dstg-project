package com.nikolamajcen.hasse.math;

/**
 * Created by nikolamajcen on 22.01.16..
 */

import com.nikolamajcen.hasse.helpers.CsvParser;

import java.io.File;
import java.util.ArrayList;

public class BellmanFordAlgorithm {

    public static String[] elements;
    public static int elementDistances[];
    private static int numberOfElements;
    private static int distanceMatrix[][];
    private static int progressMatrix[][];

    public static void openFile(File file) {
        getMatrixFromFile(file);
        validateMatrix();
        validateelementDistances();
    }

    public static void calculate(int row) {
        elementDistances[row] = 0;
        for (int i = 0; i < numberOfElements; i++) {
            for (int j = 0; j < numberOfElements; j++) {
                if (distanceMatrix[i][j] == 9999) {
                    progressMatrix[i][j] = 9999;
                    continue;
                }

                if (elementDistances[j] > elementDistances[i] + distanceMatrix[i][j]) {
                    elementDistances[j] = elementDistances[i] + distanceMatrix[i][j];
                    // progressMatrix[j][i] = elementDistances[j];
                }
            }
            for(int j = 0; j < numberOfElements; j++) {
                progressMatrix[j][i] = elementDistances [j];
                if(j > 0) {
                    if(progressMatrix[j][i] == 9999 && progressMatrix[j-1][i] != 9999 ) {
                        progressMatrix[j][i] = progressMatrix[j-1][i];
                    }
                }
            }
        }

        // Now working propertly, but final result is showed.
        // System.out.println("Work in progress result:");
        // showResults(progressMatrix);
        // System.out.println("\nOfficial result:");
        showSimpleResults(row);
    }

    private static void showResults(int[][] matrix) {
        if (matrix == null) {
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
                int distance = matrix[i][j];

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

    private static void showSimpleResults(int row) {
        for (int i = 0; i < numberOfElements; i++) {
            System.out.print(elements[row] + " - ");

            if (elements[i].length() < 7) {
                System.out.print(elements[i] + "\t\t\t=> ");
            } else if (elements[i].length() < 11) {
                System.out.print(elements[i] + "\t\t=> ");
            } else {
                System.out.print(elements[i] + "\t=> ");
            }
            System.out.println(elementDistances[i]);
        }
    }

    private static void getMatrixFromFile(File file) {
        ArrayList<ArrayList<String>> values = CsvParser.toList(file);

        if (values != null && values.size() > 0) {
            numberOfElements = values.size();
            elementDistances = new int[numberOfElements];
            elements = new String[numberOfElements];
            distanceMatrix = new int[numberOfElements][numberOfElements];
            progressMatrix = new int[numberOfElements][numberOfElements];

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

    private static void validateelementDistances() {
        for (int i = 0; i < numberOfElements; i++) {
            elementDistances[i] = 9999;
        }
    }
}