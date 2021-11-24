//Systems Programming Project 1
//Authors:
//Engin Bektas
//Mahmut Salman


//In this project, our goal is to convert decimal numbers to hexadecimal. While doing that we worked with
//signed, unsigned and floating point numbers.
//We converted signed values based on 2's complement concept.

import java.math.BigDecimal;
import java.util.*;
import java.lang.StringBuilder;
import java.io.*;

class main {

    private static final String[] hexValues = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
            "E", "F" };

    public static void main(String[] args) throws IOException {


        //region Prompting input
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the input file name:");
        String inputFileName = input.nextLine();


        System.out.println("Enter the byte ordering type: 'l' for Little Endian, 'b' for Big endian:");
        char endianAsString = input.next().charAt(0);
        int endian = 1;
        if (endianAsString == 'l')
            endian = 1;
        else
            endian = 2;

        System.out.println("Enter the floating point size: ");
        int byt = input.nextInt();
        if (byt < 1 || byt > 4) {
            System.out.print("You entered an invalid size.");
            java.lang.System.exit(0);
        }
        if (endianAsString != 'l' && endianAsString != 'b') {
            System.out.print("You entered an invalid endian type.");
            java.lang.System.exit(0);
        }
        //endregion

        //region Configuration of exponent bits and mantissa
        int expBit = 0, mantissa = 0;
        switch (byt) {
            case 1:
                expBit = 4;
                mantissa = 3;
                break;
            case 2:
                expBit = 6;
                mantissa = 9;
                break;
            case 3:
                expBit = 8;
                mantissa = 15;
                break;
            case 4:
                expBit = 10;
                mantissa = 21;
                break;
        }
        //endregion

        //region Reading file and creating output.txt
        Calculator calculator = new Calculator();
        BufferedReader in = null;
        File f = new File(inputFileName);
        if(f.exists() && !f.isDirectory()) {
            in = new BufferedReader(new FileReader(
                    inputFileName));
        } else {
            System.out.print("File '" + inputFileName + "' does not exist.");
            java.lang.System.exit(0);
        }

        String str;
        //Store lines in list as strings
        List<String> list = new ArrayList<String>();
        while ((str = in.readLine()) != null) {
            list.add(str);
        }
        //Convert list to an array
        String[] stringArr = list.toArray(new String[0]);
        // Initialize a char array to hold binary version of the input.(i.e 4u 0000 00011 1010 0000 )
        // It is reused
        char[] binaryArray16 = new char[16];
        // Creating output.txt file
        try {
            File myObj = new File("output.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //endregion

        //Numerical Calculations
        for (int i = 0; i < stringArr.length; i++) {
            //Read each line
            if (stringArr[i] != null) {
                String textValue = stringArr[i];

                //THIS BLOCK IS FOR UNSIGNED NUMBERS
                if (textValue.contains("u")) {
                    //for unsigned data
                    // Take apart value from 'number-u' and make it 'number'
                    // 1- Find the location of the character 'u'
                    // 2- Delete that char and create
                    String unsignedInteger = stringArr[i].replace("u", ""); // 4u --> 4
                    convert2BinaryFromInteger(binaryArray16, Integer.parseInt(unsignedInteger));
                    String binary = String.valueOf(binaryArray16);
                    try {
                        BufferedWriter myWriter = new BufferedWriter(new FileWriter("output.txt", true));
                        myWriter.write(binaryToHexadecimal(binary, endian) + "\n");
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
                //THIS BLOCK IS FOR SIGNED NUMBERS WITHOUT POINT
                if (!textValue.contains("u") && !textValue.contains(".")) { // For signed floating point
                    if (textValue.contains("-")) {
                        // For signed values
                        String signedInteger = stringArr[i];// textValue
                        String binary = convertNegativeNumberToBinary(Integer.parseInt(signedInteger), 2);

                        try {
                            BufferedWriter myWriter = new BufferedWriter(new FileWriter("output.txt", true));
                            myWriter.write(binaryToHexadecimal(binary, endian) + "\n");
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                    }
                    else {
                        String signedInteger = stringArr[i];// textValue
                        String binary = convertPositiveNumberToBinary(Integer.parseInt(signedInteger), 2, false);
                        try {
                            BufferedWriter myWriter = new BufferedWriter(new FileWriter("output.txt", true));
                            myWriter.write(binaryToHexadecimal(binary , endian) + "\n");
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                    }
                }
                //THIS BLOCK IS FOR FLOATING POINT NUMBERS
                if (textValue.contains(".")){ // for floating point data
                    //Use StringBuilder sb to manipulate the data more efficiently
                    StringBuilder sb = new StringBuilder(textValue);
                    boolean sign = true;
                    //Check if negative, if so, remove dash and set sign to false
                    if (textValue.charAt(0) == '-') {
                        sign = false;
                        sb.deleteCharAt(0);
                    }
                    //Convert to binary both decimal and fractional parts
                    //Ex: 2.5 >>> 10.101 >>> 1.0101 Left side is stored as floating number, and right as exponent
                    String floatingPoint = calculator.normalizer(calculator.decimalToBinary(new BigDecimal(sb.toString())))[0];
                    String E = calculator.normalizer(calculator.decimalToBinary(new BigDecimal(sb.toString())))[1];
                    //fraction = mantissa
                    //pointIndex is to store the index of the point, then we will manipulate it.
                    int pointIndex = 0;
                    for(int k = 0; k < floatingPoint.length()-1; k++) {
                        if (floatingPoint.charAt(k) == '.') {
                            pointIndex = k;
                            break;
                        }
                    }
                    //Create sb2 to store the part which is after the point as the fraction
                    StringBuilder sb2 = new StringBuilder(floatingPoint);
                    sb2 = new StringBuilder(sb2.substring(pointIndex+1, sb2.length()));
                    String fraction = sb2.toString();

                    //For clarity, we will do operations on the fraction and store it in a new variable newFraction
                    String newFraction = "";
                    //While calculating the mantissa to get the floating point value, we will
                    //only use the first 13 bits of the fraction part (for 3-byte and 4-byte data
                    //sizes). Stored original mantissa seperately, and changed mantissa to the desired value.
                    int originalMantissa = mantissa;
                    if (byt >= 3) {
                        mantissa = 13;
                    }
                    //No rounding if no need
                    if (fraction.length() == mantissa) {
                        newFraction = sb2.toString();
                    }
                    //If undernumbered, add zeros
                    else if (sb2.toString().length() < mantissa) {
                        while (sb2.toString().length() < mantissa){
                            sb2.append(0);
                            newFraction = sb2.toString();
                        }
                    }
                    //If overnumbered, do rounding
                    else if (sb2.toString().length() > mantissa ) {
                        //round down
                        if (fraction.charAt(mantissa) == '0')
                            newFraction = fraction;
                        //Round up
                        if (fraction.charAt(mantissa) == '1' && fraction.substring(mantissa+1, fraction.length()).contains("1")) {
                            newFraction = fraction.substring(0, mantissa);
                            String rounded = newFraction;

                            if(newFraction.contains("0")) {
                                rounded = adder(newFraction, "1");

                            }
                            newFraction = rounded;
                        }
                        //Halfway
                        else if (fraction.charAt(mantissa) == '1' && !fraction.substring(mantissa+1, fraction.length()).contains("1") ) {
                            if (fraction.charAt(mantissa-1) == '1') { // round up if end is odd
                                newFraction = fraction;
                                String rounded = adder(newFraction, "1");
                                StringBuilder sb3 = new StringBuilder(rounded);
                                sb3.deleteCharAt(sb3.length()-1);
                                rounded = sb3.toString();
                                newFraction = rounded;
                            }
                            //Round down if end is even
                            if (fraction.charAt(mantissa-1) == '0') {
                                newFraction = fraction;
                            }
                        }
                        //If the fraction is less than the mantissa size, complete it to its size.
                        newFraction = completer(newFraction, originalMantissa);
                    }

                    //Appending
                    String signValue = (sign) ? "0": "1";
                    sign = sign; //boolean
                    int bias = (int)Math.pow(2, expBit-1) -1;
                    int exp = Integer.parseInt(E) + bias;
                    char[] a = convert2BinaryFromInteger(exp, expBit);
                    String exponentString = new String(a);
                    StringBuilder ieee = new StringBuilder(new String());
                    //Get it all together
                    ieee.append(signValue);
                    ieee.append(exponentString);
                    ieee.append(newFraction);

                    String FP = binaryToHexadecimal(ieee.toString(), endian);

                    try {
                        BufferedWriter myWriter = new BufferedWriter(new FileWriter("output.txt", true));
                        myWriter.write(FP + "\n");
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //Converts from integer to binary FOR UNSIGNED CHUNK
    static void convert2BinaryFromInteger(char a[], int b) {
        for (int i = 0; i < 16; i++) {
            if (b % 2 == 0)
                a[15 - i] = '0';
            else if (b % 2 != 0)
                a[15 - i] = '1';
            b = b / 2; // To go to next digit

        }
    }
    //Converts from integer to binary FOR FLOATING POINT CHUNK
    static char[] convert2BinaryFromInteger(int b, int size){

        char[] a = new char[size];
        for(int i=0;i<size;i++){
            if(b % 2 ==0)
                a[size-1-i] = '0';
            else if(b  % 2 !=0)
                a[size-1-i] = '1';
            b = b / 2; // To go to next digit
        }
        return a;
    }
    //Convert hexadecimal from binary
    public static String binaryToHexadecimal(String binary, int endian) {
        String hexadecimal;
        binary = leftPad(binary);
        return convertBinaryToHexadecimal(binary, endian);
    }
    //Convert binary to hexadecimal
    public static String convertBinaryToHexadecimal(String binary, int endian) {
        String hexadecimal = "";
        int sum = 0;
        int exp = 0;
        for (int i = 0; i < binary.length(); i++) {
            exp = 3 - i % 4;
            if ((i % 4) == 3) {
                sum = sum + Integer.parseInt(binary.charAt(i) + "") * (int) (Math.pow(2, exp));
                hexadecimal = hexadecimal + hexValues[sum];
                sum = 0;
            } else {
                sum = sum + Integer.parseInt(binary.charAt(i) + "") * (int) (Math.pow(2, exp));
            }
        }
        //If type is endian, reorganize. Else do nothing.
        if (endian == 1) {
            String newString = "";
            StringBuilder sb = new StringBuilder(newString);
            ArrayList<String> list = new ArrayList<>();
            //Store two chars at a time in a list, then reverse it and assemble.
            for (int i = 0; i < hexadecimal.length();) {
                list.add(hexadecimal.substring(i, i+2));
                i = i+2;
            }
            for (int i = list.size(); i > 0; i--) {
                sb.append(list.get(i-1));
                hexadecimal = sb.toString();
            }
        }
        //Put spaces between them, for formatting purposes.
        String returnHexadecimal = "";
        for (int i = 0; i < hexadecimal.length(); i += 2) {
            returnHexadecimal += hexadecimal.substring(i, i+2) + " ";
        }
        return returnHexadecimal;

    }
    //Pads the binary number to the left. Ex: 1 > 0001, ex: 10001 > 00010001
    public static String leftPad(String binary) {
        int paddingCount = 0;
        if ((binary.length() % 4) > 0)
            paddingCount = 4 - binary.length() % 4;

        while (paddingCount > 0) {
            binary = "0" + binary;
            paddingCount--;
        }
        return binary;
    }
    //Pads the binary number to the right. Ex: 1 > 0001, ex: 10001 > 00010001
    public static String completer(String binary, int originalMantissa) {
        while ((binary.length() < originalMantissa)) {
            binary = binary + "0";
        }
        return binary;
    }
    //Convert a positive decimal number to binary
    public static String convertPositiveNumberToBinary(int n, int bytes, boolean reverse) {
        int bits = 8 * bytes;
        StringBuilder sb = new StringBuilder(bits); // in-bits
        if (n == 0) {
            sb.append("0");
        } else {
            while (n > 0) {
                sb.append(n % 2);
                n >>= 1; // this means divided by 2: n/2
            }
        }

        if (sb.length() < bits) {
            for (int i = sb.length(); i < bits; i++) {
                sb.append("0");

            }
        }
        if (reverse) {
            return sb.toString();
        } else {
            return sb.reverse().toString();
        }
    }
    //Convert a negative decimal number to binary
    public static String convertNegativeNumberToBinary(int n, int bytes) {
        int m = -n; // convert to positve
        String binary = convertPositiveNumberToBinary(m, bytes, true);
        int len = binary.length();
        StringBuilder sb = new StringBuilder(len); // in binary version
        boolean foundFirstOne = false;
        for (int i = 0; i < len; i++) {
            if (foundFirstOne) {
                if (binary.charAt(i) == '1') {
                    sb.append('0');
                } else {
                    sb.append('1');
                }
            } else {
                if (binary.charAt(i) == '1') {
                    foundFirstOne = true;
                }
                sb.append(binary.charAt(i));
            }
        }
        return sb.reverse().toString();

    }
    //Adds two binary numbers
    public static String adder(String b1, String b2) {

        long a1 = Long.parseLong(b1);
        long a2 = Long.parseLong(b2);

        int i = 0, carry = 0;

        //This is to hold the output binary number
        int[] sum = new int[b1.length()];

        while (a1 != 0 || a2 != 0)
        {
            sum[i++] = (int)((a1 % 10 + a2 % 10 + carry) % 2);
            carry = (int)((a1 % 10 + a2 % 10 + carry) / 2);
            a1 = a1 / 10;
            a2 = a2 / 10;
        }
        if (carry != 0) {
            sum[i++] = carry;
        }
        --i;
        String output = "";
        StringBuilder sb = new StringBuilder(output);
        for (int j = 0; j < sum.length; j++) {
            sb.append(sum[j]);
        }

        return sb.reverse().toString();
    }

}
