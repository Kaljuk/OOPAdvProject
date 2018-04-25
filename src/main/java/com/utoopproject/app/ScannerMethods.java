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
        return scannerInt(scan, "\nIntegers only, please try again: ");
    }


    /**
     * @param  scan - Scanner entity
     * @param  errorMsg - Message to show when user input is different from needed
     * @return Integer as from scanner
     */
    public static int scannerInt(Scanner scan, String errorMsg) {
        /*
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
        */
        return scannerInt(scan, errorMsg, new int[]{});
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
                // if no requirements, accept the input
                if (fittingOptions.length == 0) {
                    gotInt = true;
                } else {
                    // Check if input meets requirements
                    for(int i=0; i<fittingOptions.length;i++) {
                        if (fittingOptions[i] == scannedInt) {
                            gotInt = true;
                            break;
                        }
                    }

                    if(!gotInt) {
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
    ///TODO: add answer requirements and their checking in next overload method

    /**
     * @param  scan     - Scanner entity
     * @param  errorMsg - Error to show when input doesn't fit to requirements
     * @return String from scanner that meets requirements
     */
    public static String scannerString(Scanner scan, String errorMsg) {
        boolean gotString    = false;
        String scannedString = "";
        while (!gotString) {

            scannedString = String.valueOf(scan.nextLine());
            if (!scannedString.equals("")) {
                gotString = true;
            } else {
                // Go for another loop
                gotString = false;
                // Show errorMsg when input hasnt met requirements
                System.out.println(errorMsg);
            }
        }
        return scannedString;
    }
}
