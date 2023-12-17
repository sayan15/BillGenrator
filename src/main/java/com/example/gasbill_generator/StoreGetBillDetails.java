package com.example.gasbill_generator;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class StoreGetBillDetails {
    private static final String csvFileName = "CustomersBillDetails.csv";

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
                // Assuming the CSV columns are in order: customerId, dateJoined, lastElectricityUnitReading, gasUnitReading, accountBalance, gasBill, electricityBill, total, due
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
}
