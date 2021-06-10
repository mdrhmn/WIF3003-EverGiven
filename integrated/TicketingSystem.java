/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketingSystem implements Runnable {

    private ArrayList<String[]> readList;
    private String filename;

    public TicketingSystem(String filename) {
        this.filename = filename;
    }

    public void readFile() throws FileNotFoundException, IOException {
        String thisLine = null;
        BufferedReader br = new BufferedReader(new FileReader(this.filename));
        this.readList = new ArrayList<>();

        // Create read list
        while ((thisLine = br.readLine()) != null) {
            String line = thisLine;
            String[] container = line.trim().split(";");
            this.readList.add(container);
        }
        br.close();
    }

    public ArrayList<String[]> getVisitorList() {
        return this.readList;
    }

    @Override
    public void run() {
        try {
            readFile();
        } catch (IOException ex) {
            Logger.getLogger(TicketingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}