import java.util.*;
import java.lang.StringBuilder;
import java.io.*;
class HelloWorld {
    public static void main(String[] args) throws IOException {
        // TODO Read txt file
        BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\asxdc\\Desktop\\Desktop\\Projects\\Java_projects\\Systems_Programming_Project\\input.txt"));
        String str;
        
        List<String> list = new ArrayList<String>();
        while((str = in.readLine()) != null){
            list.add(str);
        }
        
        String[] stringArr = list.toArray(new String[0]);
    }
}