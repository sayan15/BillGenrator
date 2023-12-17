package com.example.gasbill_generator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StoreGetCustomer {

    private static final String FILE_NAME = "CustomerObjects.dat";
    private static final String csvFileName = "CustomersBillDetails.csv";
    public static void writeToFile(ArrayList<Customer> customerList) throws IOException {
        try (ObjectOutputStream objectOutputFile = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            objectOutputFile.writeObject(customerList);

        }
    }

    public static String addCustomerToFile(Customer newCustomer) throws IOException, ClassNotFoundException {
        // Read the existing list from the file
        ArrayList<Customer> existingCustomerList = readFromCustomerFile();

        // Get the last added Tariff values
        if (!existingCustomerList .isEmpty()) {
            Customer lastAddedCustomer = existingCustomerList.get(existingCustomerList.size() - 1);
            lastAddedCustomer.setCustomerId(lastAddedCustomer.getCustomerId()+1);
        }
        // Add the newTariff to the list
        existingCustomerList.add(newCustomer);

        // Write the updated list back to the file
        writeToFile(existingCustomerList);

        writeCustomerToCSV(newCustomer);

        return "Successfully Customer Registered";
    }
    public static ArrayList<Customer> readFromCustomerFile() throws IOException, ClassNotFoundException {
        File file = new File(FILE_NAME);

        // If the file doesn't exist yet, return an empty list
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream objectInputFile = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Customer>) objectInputFile.readObject();
        }
    }

    private static void writeCustomerToCSV(Customer customer) throws IOException {

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

}
