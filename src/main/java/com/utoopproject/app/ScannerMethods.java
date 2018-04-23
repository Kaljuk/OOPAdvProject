package com.utoopproject.app;

import java.util.Scanner;

/**
 * ScannerMethods
 */         
public class ScannerMethods {

    // Integer input

    /**
     * @param  scan - Scanner entity
     * @return Integer as from scanner
     */
    public static int scannerInt(Scanner scan) {
        boolean gotInt = false;
        int scannedInt = 0;
        while (!gotInt) {
            try {
                scannedInt = Integer.valueOf(scan.nextLine());
                gotInt     = true;
            } catch (NumberFormatException e) {
                gotInt     = false;
                System.out.print("\nIntegers only, please try again: ");
            }
        }
        return scannedInt;
    }
    /**
     * @param  scan - Scanner entity
     * @param  errorMsg - Message to show when user input is different from needed
     * @return Integer as from scanner
     */
    public static int scannerInt(Scanner scan, String errorMsg) {
        boolean gotInt = false;
        int scannedInt = 0;
        while (!gotInt) {
            try {
                scannedInt = Integer.valueOf(scan.nextLine());
                gotInt     = true;
            } catch (NumberFormatException e) {
                gotInt     = false;
                System.out.print(errorMsg);
            }
        }
        return scannedInt;
    }

    // // // // String INPUT // // // // 

    /**
     * @param  scan     - Scanner entity
     * @param  errorMsg - Error to show when input doesn't fit to requirements
     * @return String from scanner that meets requirements
     */
    public static String scannerString(Scanner scan, String errorMsg) {
        boolean gotString    = false;
        String scannedString = "";
        while (!gotString) {
            try {
                scannedString = String.valueOf(scan.nextLine());
                if (scannedString != "") {
                    gotString = true;   
                } else {
                    gotString = false;
                    // Show errorMsg
                    System.out.println(errorMsg);
                }
            } catch (NumberFormatException e) {
                gotString     = false;
                System.out.print(errorMsg);
            }
        }
        return scannedString;
    }
}
