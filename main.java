import java.util.*;
import java.lang.StringBuilder;
import java.io.*;
class HelloWorld {
    private final String[] hexValues = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    public static void main(String[] args) throws IOException {
        // TODO Read txt file _DONE_
        BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\asxdc\\Desktop\\Desktop\\Projects\\Java_projects\\Systems_Programming_Project\\input.txt"));
        String str;
        
        List<String> list = new ArrayList<String>();
        while((str = in.readLine()) != null){
            list.add(str);
        }
        
        String[] stringArr = list.toArray(new String[0]);

        // Char array to hold binary version of the input.(i.e 4u  0000 00011 1010 0000 ) . It is reused
        char[] binaryArray16 = new char[16];

        //TODO Search for 'u' value to understand it is unsigned integer

        for (int i = 0; i < stringArr.length; i++){
            if (stringArr[i] != null){
                String textValue = stringArr[i];
                for (int j = 0; j < stringArr[i].length(); j++){
                    //TODO unsigned convertion
                    if (textValue.charAt(j) == ('u')){
                        
                        // Take apart value from 'number-u' and make it 'number' 
                        //1- Find the location of the character 'u'
                        //2- Delete that chac and create
                        String unsignedInteger =stringArr[i].replace("u", ""); // 4u --> 4
                        convert2BinaryFromInteger(binaryArray16,Integer.parseInt(unsignedInteger));
                        System.out.println(Arrays.toString(binaryArray16));


                       
                       
                    }
                }
            }
        }
    }
    static void convert2BinaryFromInteger(char a[],int b){
       // int number = Integer.parseInt(b);
        for(int i=0;i<16;i++){
            if(b % 2 ==0)
                a[15-i] = '0';
            else if(b  % 2 !=0)
                a[15-i] = '1';
                b = b / 2; // To go to next digit

        }

    }

    // To hexa from binary
    public void binaryToHexadecimal(String binary){
        String hexadecimal;
        binary  = leftPad(binary);
        System.out.println(convertBinaryToHexadecimal(binary));
    
    }
    
    public String convertBinaryToHexadecimal(String binary){
        String hexadecimal = "";
        int sum = 0;
        int exp = 0;
        for (int i=0; i<binary.length(); i++){
            exp = 3 - i%4;
            if((i%4)==3){
                sum = sum + Integer.parseInt(binary.charAt(i)+"")*(int)(Math.pow(2,exp));
                hexadecimal = hexadecimal + hexValues[sum];
                sum = 0;
            }
            else
            {
                sum = sum + Integer.parseInt(binary.charAt(i)+"")*(int)(Math.pow(2,exp));
            }
        }
        return hexadecimal;
    }
    
    public String leftPad(String binary){
        int paddingCount =  0;
        if ((binary.length()%4)>0)
            paddingCount = 4-binary.length()%4;
    
        while(paddingCount>0) {
            binary = "0" + binary;
            paddingCount--;
        }
        return binary;
    } 

}
