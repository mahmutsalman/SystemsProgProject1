import java.util.*;
import java.lang.StringBuilder;
import java.io.*;

class HelloWorld {
    private static final String[] hexValues = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
            "E", "F" };

    public static void main(String[] args) throws IOException {
        // TODO Read txt file _DONE_
        BufferedReader in = new BufferedReader(new FileReader(
                "input.txt"));
        String str;

        List<String> list = new ArrayList<String>();
        while ((str = in.readLine()) != null) {
            list.add(str);
        }

        String[] stringArr = list.toArray(new String[0]);

        // Char array to hold binary version of the input.(i.e 4u 0000 00011 1010 0000 )
        // . It is reused
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

        // TODO Search for 'u' value to understand it is unsigned integer

        for (int i = 0; i < stringArr.length; i++) {
            boolean signedFlag = true; // To understan inğut is signed value
            int j = 0; // Instead of inside of for loop, itialize here to be able go to next digit when
            // progrmam finds '-'

            if (stringArr[i] != null) {
                String textValue = stringArr[i];


                // TODO unsigned convertion
                if (textValue.contains("u")) {

                    // Take apart value from 'number-u' and make it 'number'
                    // 1- Find the location of the character 'u'
                    // 2- Delete that chac and create
                    String unsignedInteger = stringArr[i].replace("u", ""); // 4u --> 4
                    convert2BinaryFromInteger(binaryArray16, Integer.parseInt(unsignedInteger));
                    System.out.println(Arrays.toString(binaryArray16));
                    String binary = String.valueOf(binaryArray16);
                    // binaryToHexadecimal(binary);
                    try {
                        BufferedWriter myWriter = new BufferedWriter(new FileWriter("output.txt", true));
                        myWriter.write(binaryToHexadecimal(binary) + "\n");
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
                // For signed floating point
                if (!textValue.contains("u") && !textValue.contains(".")) {
                    // TODO exctract ' - ' from input(textValue)
                    // TODO Check if there is '.'
                    if (textValue.contains("-")) {
                        // For signed values
                        String signedInteger = stringArr[i];// textValue
                        String binary = convertNegativeNumberToBinary(Integer.parseInt(signedInteger), 2);
                        //TODO Convert binary to hexa
                        try {
                            BufferedWriter myWriter = new BufferedWriter(new FileWriter("output.txt", true));
                            myWriter.write(binaryToHexadecimal(binary) + "\n");
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
                            myWriter.write(binaryToHexadecimal(binary) + "\n");
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
    }

    static void convert2BinaryFromInteger(char a[], int b) {
        // int number = Integer.parseInt(b);
        for (int i = 0; i < 16; i++) {
            if (b % 2 == 0)
                a[15 - i] = '0';
            else if (b % 2 != 0)
                a[15 - i] = '1';
            b = b / 2; // To go to next digit

        }

    }

    // To hexa from binary
    public static String binaryToHexadecimal(String binary) {
        String hexadecimal;
        binary = leftPad(binary);
        // System.out.println(convertBinaryToHexadecimal(binary));
        return convertBinaryToHexadecimal(binary);
    }

    public static String convertBinaryToHexadecimal(String binary) {
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
        return hexadecimal;
    }

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

    // Convert a positive decimal number to binary
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

}
