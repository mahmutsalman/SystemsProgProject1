import java.math.BigDecimal;
import java.util.*;
import java.lang.StringBuilder;
import java.io.*;

class HelloWorld {
    private static final String[] hexValues = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
            "E", "F" };

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the byte ordering type: ");

        System.out.println("Enter the floating point size: ");
        int byt = input.nextInt();


        int expBit = 0, mantissa = 0;
        switch (byt) {
            case 1:
                expBit = 3;
                mantissa = 4;
                break;
            case 2:
                expBit = 8;
                mantissa = 7;
                break;
            case 3:
                expBit = 10;
                mantissa = 13;
                break;
            case 4:
                expBit = 12;
                mantissa = 19;
                break;
        }
        Calculator calculator = new Calculator();
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
            boolean isSigned = false;



            boolean signedFlag = true; // To understan inğut is signed value
            int j = 0; // Instead of inside of for loop, itialize here to be able go to next digit when
            // progrmam finds '-'

            if (stringArr[i] != null) {
                String textValue = stringArr[i];


                if (textValue.contains(".")) {
                    String[] tokens = textValue.split("\\.");
                    if (tokens[1].length() == 1 && tokens[1].contains("0")) { // if number is like 1.0
                        isSigned = true;
                        String textValue0 = tokens[0];
                        if (!textValue0.contains("u")) {
                            // TODO exctract ' - ' from input(textValue)
                            // TODO Check if there is '.'
                            if (textValue0.contains("-")) {
                                // For signed values
                                String signedInteger = textValue0;// textValue
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
                            } else {
                                String signedInteger = textValue0;// textValue
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
                        continue;
                    }
                }

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

                if(textValue.contains(".")){ //if floating point
                    //TODO Check for floating point numbers
                    StringBuilder sb = new StringBuilder(textValue);
                    boolean sign = true;
                    if (textValue.charAt(0) == '-') {
                        sign = false;
                        sb.deleteCharAt(0);
                    }
                    String floatingNumber = calculator.normalizer(calculator.decimalToBinary(new BigDecimal(sb.toString())))[0];
                    String exponent = calculator.normalizer(calculator.decimalToBinary(new BigDecimal(sb.toString())))[1];



                    String s = floatingNumber;

                    StringBuilder sb2 = new StringBuilder(s);
                    String newS = "";
                    int size = mantissa;
                    int k = size +2;
                    // no rounding
                    if (s.length() == size+2) {
                        newS = s;
                        //System.out.println(newS);
                    }
                    //if undernumbered, add zeros
                    else if (sb2.toString().length() < size+2) {
                        while (sb2.toString().length() < size+2){
                            sb2.append(0);
                            newS = sb2.toString();
                        }

                    }
                    //if overnumbered, do rounding
                    //_ROUNDING
                    else if (s.charAt(k) == '0' ) { //round down
                        newS = s.substring(2, k);

                    }
                    else if ( s.charAt(k) == '1' && s.substring(k+1, s.length()).contains("1") ) { //round up
                        newS = s.substring(2, k);
                        String rounded = adder(newS, "1");

                        newS = rounded;
                    }
                    else if ( s.charAt(k) == '1' && !s.substring(k+1, s.length()).contains("1") ) { //halfway
                        if (s.charAt(k-1) == '1') { // round up
                            newS = s.substring(2, k);
                            String rounded = adder(newS, "1");

                            newS = rounded;
                        }
                        if (s.charAt(k-1) == '0') { // round down

                        }
                    }
                    //appending
                    String signValue = (sign) ? "0": "1";
                    String fraction = newS.substring(2, newS.length());
                    sign = sign; //boolean
                    int bias = (int)Math.pow(2, expBit-1) -1;
                    int exp = Integer.parseInt(exponent) + bias;
                    char[] a = convert2BinaryFromInteger(exp, expBit);
                    String exponentString = new String(a);
                    StringBuilder ieee = new StringBuilder(new String());
                    ieee.append(signValue);
                    ieee.append(exponentString);
                    ieee.append(fraction);
                    //System.out.println("ieee representation is: " + ieee.toString());
                    String FP = binaryToHexadecimal(ieee.toString());



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

    static char[] convert2BinaryFromInteger(int b, int size){
        // int number = Integer.parseInt(b);
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
