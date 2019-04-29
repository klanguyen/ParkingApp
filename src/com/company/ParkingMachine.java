package com.company;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class ParkingMachine {
    private Scanner keyboard = new Scanner(System.in);

    public static void printName(){
        System.out.println("Best Value Parking Garage\n" +
                "=========================\n");
    }

    public int randomize(int min, int max) {
        return min + (int)(Math.random()*((max - min) + 1));
    }

    public String getDate(){
        Date today = new Date();
        String startDate;
        DateFormat dateFormatter;
        dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT,
                Locale.US);
        startDate = dateFormatter.format(today);
        return startDate;
    }

    public static void separator(){
        System.out.println("_________________________\n\n");
    }

    public int getInt(String message) {
        System.out.print(message);
        String input = keyboard.nextLine();
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }
}
