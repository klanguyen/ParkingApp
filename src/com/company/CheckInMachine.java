package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.ParkingMachine.printName;
import static com.company.ParkingMachine.separator;

public class CheckInMachine implements ParkingMachineRelated {
    private String startTime;
    private int vehicleId;
    private ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
    private ParkingMachine checkin = new ParkingMachine();
    private int lostTicketCounter;
    private int totalParkingFee;


    public CheckInMachine(){

    }

    public void runCheckInMachine() throws IOException {
        CheckOutMachine co = new CheckOutMachine();
        StartTimeGenerator stg;
        String startDate = checkin.getDate();
        int input;
        int vehicleId;
        String ticketInfo = "";
        do {
            stg = new StartTimeGenerator();
            startTime = stg.getStartTime();
            printName();
            printMenu();
            do {
                input = checkin.getInt("=>");
                separator();
                if (input == 1) {
                    printName();
                    setVehicleId();
                    vehicleId = getVehicleId();
                    Ticket t = new Ticket(vehicleId, startTime);
                    ticketList.add(t);
                    ticketInfo = vehicleId + "|" + startTime + "|N/A" + "|0";
                    // save info into the file
                    writeTicketInfo(ticketInfo);
                    System.out.println(startDate + " - " + startTime);
                    System.out.println(vehicleId);
                    System.out.println("Please take your ticket");
                    separator();
                    co.runCheckOutMachine();
                } else if (input == 3) {
                    printName();
                    getTotal();
                    printSummary();
                }
                else {
                    System.out.println("Invalid input");
                }
            } while (input != 1 && input!= 3);
        } while (input != 3);
    }

    private void setVehicleId(){
        vehicleId = 100 + ticketList.size() + 1;
    }

    private int getVehicleId(){
        return vehicleId;
    }

    @Override
    public void printMenu(){
        System.out.println("1 - Check/In\n3 - Close Garage");
    }

    private void printSummary(){
        System.out.println("Activity to Date\n\n");
        System.out.format("$%d was collected from %d Check Ins\n", totalParkingFee, ticketList.size());
        System.out.format("$%d was collected from %d Lost Tickets", lostTicketCounter*25, lostTicketCounter);
        System.out.format("\n\n$%d was collected overall", totalParkingFee + lostTicketCounter*25);
    }

    private void writeTicketInfo(String log) throws IOException {
        PrintWriter outFile = new PrintWriter(
                new BufferedWriter(
                new FileWriter("ticketLog.txt", true)));
        outFile.println(log);
        outFile.close();
    }

    private void getTotal() throws IOException {
        File file = new File("ticketLog.txt");
        Scanner inputFile = new Scanner(file);
        Ticket t;
        while (inputFile.hasNext()) {
            String line = inputFile.nextLine();
            int fee = Integer.parseInt(line.substring(line.lastIndexOf('|') + 1));
            if (fee == 25) {
                lostTicketCounter++;
            }
            else {
                totalParkingFee += fee;
            }
        }
        inputFile.close();
    }
}
