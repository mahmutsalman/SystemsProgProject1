import java.util.*;
import java.lang.StringBuilder;
import java.io.*;
class HelloWorld {
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

}
