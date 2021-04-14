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


        //TODO Search for u value to understand it is unsigned integer

        for (int i = 0; i < stringArr.length; i++){
            if (stringArr[i] != null){
                String textValue = stringArr[i];
                for (int j = 0; j < stringArr.length; j++){
                    if (textValue.charAt(j) == ('u')){
                        System.out.println("found u in the element");
                    }
                }
            }
        }
    }
}
