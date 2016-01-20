package com.nikolamajcen.hasse.helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nikolamajcen on 20.01.16..
 */

public class CsvParser {

    private static final String SEPARATOR = ";";

    public static ArrayList<ArrayList<String>> toList(File file) {
        ArrayList<ArrayList<String>> values = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while((line = reader.readLine()) != null) {
                ArrayList<String> value = new ArrayList<>(Arrays.asList(line.split(SEPARATOR)));
                for(int i = 1; i < value.size(); i++) {
                    value.set(i, value.get(i).replace(" ", ""));
                }
                values.add(value);
            }
            return values;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void toCsv(String[] elements, int[][] values, String filename, String path) {
        if(elements.length == values.length) {
            String csv = "";
            int length = elements.length;

            for (int i = 0; i < length; i++) {
                String line = elements[i];
                for (int j = 0; j < length; j++) {
                    line = line + SEPARATOR + String.valueOf(values[i][j]);
                }
                line = line + "\n";
                csv = csv + line;
            }
            try {
                File file = new File(path + filename);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                Writer writer = new BufferedWriter(outputStreamWriter);
                writer.write(csv);
                writer.close();
                System.out.println("File is saved as " + filename + ".");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error: Cannot save file to disk.");
            }

        } else {
            System.out.println("Error: Names and values not same size.");
        }
    }

    public static String getRowName(ArrayList<String> row) {
        return row.get(0);
    }

    public static int[] getRowValues(ArrayList<String> row) {
        int rowSize = row.size() - 1;
        int[] values = new int[rowSize];
        for (int i = 0; i < rowSize; i++) {
            values[i] = Integer.parseInt(row.get(i + 1));
        }
        return values;
    }
}
