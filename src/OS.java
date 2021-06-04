import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class OS {
    static Scanner scanner;
    static HashMap<String,String> variables;

    public OS (){
        variables=new HashMap<>();
        scanner=new Scanner(System.in);
    }

    public String readMemory (String var) {
        if(variables.containsKey(var))
            return variables.get(var);
        return null;
    }

    public void writeMemory (String var, String val){
        variables.put(var, val);
    }

    // take file name on the form xxxx
    public String readFile(String path) throws IOException {
        StringBuilder result=new StringBuilder();
        try {
            if(variables.containsKey(path)) path=variables.get(path)+"";
            createFile(path);
            File file=new File(path+".txt");
            BufferedReader br=new BufferedReader(new FileReader(file));
            String st;
            while((st=br.readLine())!=null){
                if(result.length()!=0) result.append("\n");
                result.append(st);
            }
        }catch (Exception e){
            System.out.println("error at readFile");
        }
        return result.toString();
    }

    public void createFile(String filename){
        try {
            File file = new File(filename+".txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {

            System.out.println("An error occurred.");
        }
    }

    public void writeFile(String path,String data){  // path is 1st variable, data is 2nd one
        if(variables.containsKey(path)) path=(String)variables.get(path);
        createFile(path);
        try {
            File file = new File(System.getProperty("user.dir") + "/" + path+".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append("\n");
            if(variables.containsKey(data)) data=(String)variables.get(data);
            writer.append(data);
            writer.close();
        }catch (Exception e){
            System.out.println("error at readFile");
        }
    }

    public void print(String  x)
    {
        x=variables.containsKey(x) ? variables.get(x):x;
        System.out.println(x.length()==0 ? "no data entered / empty file":x);
    }

    public String input(){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        return str;
    }

    public BufferedReader readProgram (String path) throws FileNotFoundException {
        File file = new File(System.getProperty("user.dir")+"/"+path+".txt");
        return new BufferedReader(new FileReader(file));
    }
}
