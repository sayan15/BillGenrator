package com.example.gasbill_generator;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class StoreGetTariff {
    private static final String FILE_NAME = "TarriffObjects.dat";
    public static void writeToFile(ArrayList<Tariff> tariffList) throws IOException {
        try (ObjectOutputStream objectOutputFile = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            objectOutputFile.writeObject(tariffList);

        }
    }

    public static String addTariffToFile(Tariff newTariff) throws IOException, ClassNotFoundException {
        // Read the existing list from the file
        ArrayList<Tariff> existingTariffList = readFromFile();

        // Get the last added Tariff values
        if (!existingTariffList.isEmpty()) {
            Tariff lastAddedTariff = existingTariffList.get(existingTariffList.size() - 1);
            lastAddedTariff.setTarrifId(lastAddedTariff.getTarrifId()+1);
        }
        // Add the newTariff to the list
        existingTariffList.add(newTariff);

        // Write the updated list back to the file
        writeToFile(existingTariffList);

        return "Successfully Tariff created";
    }
    public static ArrayList<Tariff> readFromFile() throws IOException, ClassNotFoundException {
        File file = new File(FILE_NAME);

        // If the file doesn't exist yet, return an empty list
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream objectInputFile = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Tariff>) objectInputFile.readObject();
        }
    }

    public static void removeTariffFromFile(Tariff tariffToRemove) throws IOException, ClassNotFoundException {
        // Read all objects from the file into a list
        ArrayList<Tariff> existingTariffs = readFromFile();

        // Identify the object you want to remove
        Iterator<Tariff> iterator = existingTariffs.iterator();
        while (iterator.hasNext()) {
            Tariff tariff = iterator.next();
            if (tariff.getTarrifId()==tariffToRemove.getTarrifId()) {
                iterator.remove(); // Remove the identified object from the list
            }
        }

        // Write the updated list back to the file
        writeToFile(existingTariffs);
    }

    public static void print() throws IOException, ClassNotFoundException {
        ArrayList<Tariff> list=readFromFile();
        for (Tariff tariff : list) {
            System.out.println(tariff.getTarrifId());
        }
    }

}
