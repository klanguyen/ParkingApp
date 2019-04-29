package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.ParkingMachine.printName;
import static com.company.ParkingMachine.separator;

public class CheckOutMachine implements ParkingMachineRelated {
    private int vehicleId;
    private String endTime;
    private int endHour;
    private int endMinute;
    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private ParkingMachine checkout = new ParkingMachine();

    public CheckOutMachine(){

    }

    public void runCheckOutMachine() throws IOException {
        retrieveTicketList();
        String endDate = checkout.getDate();
        int input;
        generateEndTime();
        endTime = getEndTime();
        do {
            printName();
            printMenu();
            input = checkout.getInt("=>");
            separator();
            if (input == 1) {
                printName();
                checkOut();
                separator();
            } else if (input == 2) {
                printName();
                printLostTicket();
                separator();
            }
            else {
                System.out.println("Invalid input");
            }
        } while (input != 1 && input != 2);
    }


    private void printLostTicket() throws IOException {
        Ticket latest = tickets.get(tickets.size() - 1);
        latest.setEndTime(endTime);
        calculateParkingFee(latest.getStartTime(), latest.getEndTime());
        System.out.println("Receipt for a vehicle id " + latest.getId());
        System.out.println("\nLost Ticket");
        System.out.format("$%3.2f\n", 25f);
        updateEndTimeInFile(25);
    }

    private void checkOut() throws IOException {
        Ticket latest = tickets.get(tickets.size() - 1);
        latest.setEndTime(endTime);
        calculateParkingFee(latest.getStartTime(), latest.getEndTime());
        System.out.println("Receipt for a vehicle id " + latest.getId());
        System.out.println("\n" + getParkingTime() + " parked " + latest.getStartTime() + " - " + latest.getEndTime());
        System.out.format("$%3.2f\n", (float)getParkingFee());
        updateEndTimeInFile(getParkingFee());
    }

    private void updateEndTimeInFile(int parkingFee) throws IOException {
        File originalFile = new File("ticketLog.txt");
        BufferedReader br = new BufferedReader(new FileReader(originalFile));

        // Construct the new file that will later be renamed to the original filename
        File tempFile = new File("tempFile.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

        String line = null;
        // Read from the original file and write to the new one
        // unless content matches data to be removed.
        while ((line = br.readLine()) != null) {
            if (line.contains("N/A")) {
                String updateLine = line.substring(line.indexOf('N'));
                String strCurrentEndTime = updateLine.substring(0, updateLine.indexOf('|'));
                String strCurrentFee = updateLine.substring(updateLine.indexOf('|') + 1);
                if ((strCurrentEndTime != null && strCurrentFee != null) || (!strCurrentEndTime.trim().isEmpty() && !strCurrentFee.trim().isEmpty())) {
                    line = line.substring(0, ordinalIndexOf(line, '|', 1)) + "|" +  endTime + "|" + parkingFee;
                }
            }
            pw.println(line);
            pw.flush();
        }
        pw.close();
        br.close();

        // Delete the original file
        if (!originalFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }

        // Rename the new file to the filename the original file had
        if (!tempFile.renameTo(originalFile)) {
            System.out.println("Could not rename file");
        }
    }

    // method giving the index of nth occurrence of a character
    // int n is equal to n (in nth occurrence) - 1
    private static int ordinalIndexOf(String str, char character, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(character, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }

    private void generateEndTime() {
        setEndHour();
        setEndMinute();
        setEndTime();
    }

    private void setEndHour(){
        endHour = checkout.randomize(1, 11);
    }

    private void setEndMinute(){
        if (endHour == 11) {
            endMinute = 0;
        }
        else {
            endMinute = checkout.randomize(0, 59);
        }
    }

    private void setEndTime(){
        if (endMinute <= 9) {
            endTime = endHour + ":0" + endMinute + "pm";
        }
        else {
            endTime = endHour + ":" + endMinute + "pm";
        }
    }

    public String getEndTime(){
        return endTime;
    }

    @Override
    public void printMenu(){
        System.out.println("1 - Check/Out\n2 - Lost Ticket");
    }

    private void retrieveTicketList() throws IOException {
        //BufferedReader inFile = new BufferedReader(
        //        new FileReader("ticketLog.txt"));
        //String line = inFile.readLine();
        File file = new File("ticketLog.txt");
        Scanner inputFile = new Scanner(file);
        Ticket t;
        while (inputFile.hasNext()) {
            String line = inputFile.nextLine();
            vehicleId = Integer.parseInt(line.substring(0,3));
            line = line.substring(line.indexOf('|') + 1);
            String startTime = line.substring(0, line.indexOf('|'));
            String endTime = line.substring(line.indexOf('|') + 1, line.lastIndexOf('|'));
            t = new Ticket(vehicleId, startTime, endTime);
            tickets.add(t);
        }
        inputFile.close();
    }

    private int splitHour(String time) {
        return Integer.parseInt(time.substring(0, time.indexOf(':')));
    }

    private int splitMinute(String time) {
        return Integer.parseInt(time.substring(time.indexOf(':') + 1, time.indexOf(':') + 3));
    }

    private int differentMinutes;
    private int differentHours;
    private int parkingFee;

    private void calculateParkingTime(String startTime, String endTime){
        int startHour = splitHour(startTime);
        int startMinute = splitMinute(startTime);
        int endHour = splitHour(endTime) + 12;
        int endMinute = splitMinute(endTime);
        if (endMinute < startMinute){
            --endHour;
            endMinute += 60;
        }
        differentHours = endHour - startHour;
        differentMinutes = endMinute - startMinute;
    }

    private String getParkingTime(){
        return differentHours + " hour(s) " + differentMinutes + " minute(s) ";
    }

    private void calculateParkingFee(String startTime, String endTime) {
        calculateParkingTime(startTime, endTime);
        if((differentHours < 3) || (differentHours == 3 && differentMinutes == 0)) {
            parkingFee = 5;
        }
        else if (differentHours > 3 || (differentHours == 3 && differentMinutes != 0)) {
            parkingFee = 5 + differentHours*1;
            if (parkingFee > 15) {
                parkingFee = 15;
            }
        }
    }

    private int getParkingFee() {
        return parkingFee;
    }
}
