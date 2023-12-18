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
            newCustomer.setCustomerId(lastAddedCustomer.getCustomerId()+1);
        }
        // Add the newTariff to the list
        existingCustomerList.add(newCustomer);

        // Write the updated list back to the file
        writeToFile(existingCustomerList);
        //store date in csv file to get the bill details
        StoreGetBillDetails.writeNewCustomerToCSV(newCustomer);

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
    public static boolean updateCustomerInFile(Customer updatedCustomer) throws IOException, ClassNotFoundException {
        // Read the existing list from the file
        ArrayList<Customer> existingCustomerList = readFromCustomerFile();

        // Find the customer to update based on ID
        for (int i = 0; i < existingCustomerList.size(); i++) {
            Customer currentCustomer = existingCustomerList.get(i);
            if (currentCustomer.getCustomerId() == updatedCustomer.getCustomerId()) {
                // Update the customer details
                existingCustomerList.set(i, updatedCustomer);

                // Write the updated list back to the file
                writeToFile(existingCustomerList);

                return true;
            }
        }

        return false;
    }

    public static boolean deleteCustomerFromFile(int customerId) throws IOException, ClassNotFoundException {
        // Read the existing list from the file
        ArrayList<Customer> existingCustomerList = readFromCustomerFile();

        // Find the customer to delete based on ID
        for (Customer customer : existingCustomerList) {
            if (customer.getCustomerId() == customerId) {
                // Remove the customer from the list
                existingCustomerList.remove(customer);

                // Write the updated list back to the file
                writeToFile(existingCustomerList);

                return true;
            }
        }

        return false;
    }



}
