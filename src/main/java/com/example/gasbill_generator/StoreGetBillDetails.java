package com.example.gasbill_generator;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class StoreGetBillDetails {
    private static final String csvFileName = "CustomersBillDetails.csv";
    private static final String paymentCsvFileName="paymentHistory.csv";

    public static ArrayList<BillGenerator> readFromCustomerBillFile() throws IOException, ClassNotFoundException {
        File file = new File(csvFileName);
        ArrayList<BillGenerator> billList = new ArrayList<>();
        // If the file doesn't exist yet, return an empty list
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (CSVReader reader = new CSVReader(new FileReader(csvFileName))) {
            String[] nextRecord;

            // Skip the header row
            reader.skip(1);

            while ((nextRecord = reader.readNext()) != null) {
                // CSV columns are in order: customerId, dateJoined, lastElectricityUnitReading, gasUnitReading, accountBalance, gasBill, electricityBill, total, due
                int customerId = Integer.parseInt(nextRecord[0]);
                // Define the expected date format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateJoined =LocalDate.parse(nextRecord[1],formatter);
                double lastElectricityUnitReading = Double.parseDouble(nextRecord[2]);
                double gasUnitReading = Double.parseDouble(nextRecord[3]);
                double accountBalance = Double.parseDouble(nextRecord[4]);
                double gasBill = Double.parseDouble(nextRecord[5]);
                double electricityBill = Double.parseDouble(nextRecord[6]);
                double total = Double.parseDouble(nextRecord[7]);
                double due = Double.parseDouble(nextRecord[8]);

                BillGenerator bill = new BillGenerator(customerId, dateJoined, lastElectricityUnitReading, gasUnitReading, accountBalance, gasBill, electricityBill, total, due);
                billList.add(bill);
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return billList;
    }

    //update bill details
    public static boolean UpdateDetails(BillGenerator updatedBill) throws IOException, ClassNotFoundException {
        ArrayList<BillGenerator> billList=readFromCustomerBillFile();

        // Find the record with the matching customer ID
        int index = findRecordIndex(updatedBill.getCustomerId(), billList);

        // Update the details if the record exists
        if (index != -1) {
            billList.set(index, updatedBill);

            // Write the updated list back to the CSV file
            writeToExistingCustomerBillFile(billList);
            //write to payment history
            if(updatedBill.getPayment()>0){
                writeToPaymentHistoryFile(updatedBill);
            }
            return true;
        } else {
            // Customer ID not found
            return false;
        }
    }
    //delete bill details
    public static boolean DeleteDetails(int customerId) throws IOException, ClassNotFoundException {
        // Read the existing list from the file
        ArrayList<BillGenerator> existingBillList = readFromCustomerBillFile();

        // Find the customer to delete based on ID
        for (BillGenerator billGenerator : existingBillList) {
            if (billGenerator .getCustomerId() == customerId) {
                // Remove the customer from the list
                existingBillList.remove(billGenerator );

                // Write the updated list back to the file
                writeToExistingCustomerBillFile(existingBillList);

                return true;
            }
        }

        return false;
    }
    private static int findRecordIndex(int customerId, ArrayList<BillGenerator> billList) {
        for (int i = 0; i < billList.size(); i++) {
            if (billList.get(i).getCustomerId()==customerId) {
                return i; // Found the record
            }
        }
        return -1; // Customer ID not found
    }

    public static void writeNewCustomerToCSV(Customer customer) throws IOException {

        boolean isNewFile = !Files.exists(Paths.get(csvFileName));

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName, true))) {
            // If it's a new file, write headers
            if (isNewFile) {
                writer.println("CustomerId,LastBillDate,LastElectricityunitReading,LastGasunitReading,AccountBalance,GasBill,ElectricityBill,Total,Due");
            }

            // Append customer data to the CSV file
            writer.println(customer.getCustomerId() + "," +
                    customer.getDateJoined() + "," +
                    customer.getElectricityunitKwhField() + "," +
                    customer.getGasunitKwhField()+ "," +
                    0.0+ "," +
                    0.0+ "," +
                    0.0+ "," +
                    0.0+ "," +
                    0.0);
        }
    }

    private static void writeToExistingCustomerBillFile(ArrayList<BillGenerator> billList) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName))) {
            writer.println("CustomerId,LastBillDate,LastElectricityunitReading,LastGasunitReading,AccountBalance,GasBill,ElectricityBill,Total,Due");
            for (BillGenerator bill : billList) {
                // BillGenerator has appropriate getter methods
                String line = bill.getCustomerId() + "," +
                        bill.getLastBillDate() + "," +
                        bill.getLastElectricityUnitReading() + "," +
                        bill.getGasUnitReading() + "," +
                        bill.getAccountBalance() + "," +
                        bill.getGasBill() + "," +
                        bill.getElectricityBill() + "," +
                        bill.getTotal()+ "," +
                        bill.getDue();

                writer.println(line);
            }
        }
    }

    private static void writeToPaymentHistoryFile(BillGenerator bill) throws IOException {
        boolean isNewFile = !Files.exists(Paths.get(paymentCsvFileName));
        try (PrintWriter writer = new PrintWriter(new FileWriter(paymentCsvFileName, true))) {
            // Set the second parameter to true to append to the existing file
            // If the file doesn't exist, a new file will be created
            if (isNewFile) {
                writer.println("CustomerId,LastBillDate,GasBill,ElectricityBill,Total,Payment");
            }


                String line = bill.getCustomerId() + "," +
                        bill.getLastBillDate() + "," +
                        bill.getGasBill() + "," +
                        bill.getElectricityBill() + "," +
                        bill.getTotal() + "," +
                        bill.getPayment();

                writer.println(line);

        }
    }

    public static ArrayList<BillGenerator> readFromCustomerPaymentFile() throws IOException, ClassNotFoundException {
        File file = new File(paymentCsvFileName);
        ArrayList<BillGenerator> billList = new ArrayList<>();
        // If the file doesn't exist yet, return an empty list
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (CSVReader reader = new CSVReader(new FileReader(paymentCsvFileName))) {
            String[] nextRecord;

            // Skip the header row
            reader.skip(1);

            while ((nextRecord = reader.readNext()) != null) {
                // CSV columns are in order: customerId, dateJoined, lastElectricityUnitReading, gasUnitReading, accountBalance, gasBill, electricityBill, total, due
                int customerId = Integer.parseInt(nextRecord[0]);
                // Define the expected date format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate lastBillDate  =LocalDate.parse(nextRecord[1],formatter);
                double lastElectricityUnitReading = 0.0;
                double gasUnitReading = 0.0;
                double accountBalance = 0.0;
                double gasBill = Double.parseDouble(nextRecord[2]);
                double electricityBill = Double.parseDouble(nextRecord[3]);
                double total = Double.parseDouble(nextRecord[4]);
                double due = Double.parseDouble(nextRecord[5]);

                BillGenerator bill = new BillGenerator(customerId, lastBillDate , lastElectricityUnitReading, gasUnitReading, accountBalance, gasBill, electricityBill, total, due);
                billList.add(bill);
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return billList;
    }



}
