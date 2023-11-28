package core;

import java.io.*;

public class LoadSave {
    private static final String FILE = "SaveSlots.txt";

    public static void save(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
