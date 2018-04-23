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

    
    /**
     * TODO: Add errorMsg as an anonymous hashset to show error msgs on different errors
     * TODO: Add error handling on nonNumeric errors (what are the other errors that can surface?)  
     * @param  scan - Scanner entity
     * @param  errorMsg - Message to show when user input is different from needed
     * @param  fittingOptions - Answers that meet requrements
     * @return Integer as from scanner
     */
    public static int scannerInt(Scanner scan, String errorMsg, int[] fittingOptions) {
        boolean gotInt = false;
        int scannedInt = 0;
        while (!gotInt) {
            try {
                scannedInt = Integer.valueOf(scan.nextLine());
                for(int i=0; i<fittingOptions.length;i++) {
                    if (fittingOptions[i] == scannedInt) {
                        gotInt = true;
                        break;
                    } else {
                        gotInt = false;
                        System.out.println(errorMsg);
                    }
                }
                
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
                    // Go for another loop
                    gotString = false;
                    // Show errorMsg when input hasnt met requirements
                    System.out.println(errorMsg);
                }
            } catch (NumberFormatException e) {
                gotString = false;
                System.out.print(errorMsg);
            }
        }
        return scannedString;
    }
}
