import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static HashMap<String,Object> variables=new HashMap<>();
    static Scanner scanner=new Scanner(System.in);
    static  String entered_input;


    // take file name on the form xxxx
    public static String readFile(String path) throws IOException {
        StringBuilder result=new StringBuilder();
        System.out.println(path);
        try {
            if(variables.containsKey(path)) path=variables.get(path)+"";
            createFile(path);
            System.out.println(path);
            File file=new File(path+".txt");
            BufferedReader br=new BufferedReader(new FileReader(file));
            System.out.println(2);
            String st;
            while((st=br.readLine())!=null){
                if(result.length()!=0) result.append("\n");
                result.append(st);
            }
        }catch (Exception e){
            System.out.println("1");
        }
        return result.toString();
    }

    public static void writeFile(String path,String data){  // path is 1st variable data is 2nd one
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
            System.out.println("wrong file name");
        }
    }
    public static  void assign(String x,Object y) throws Exception {
        // objext y always be direct value
        if(variables.containsKey(x)){
            if((variables.get(x).getClass().getName()).equals(y.getClass().getName())){
                variables.put(x,y);
            }else{
                throw  new Exception("incorrect  assign data type");
            }
        }else{
            variables.put(x,y);
        }
    }
    public static void createFile(String filename){
        try {
           File file = new File(filename+".txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {

            System.out.println("An error occurred.");
        }
    }
    public static String add(String x,String y) throws Exception {
       int result=0;
       try {
           if(variables.containsKey(x)) x=variables.get(x)+"";
           if(variables.containsKey(y)) y=variables.get(y)+"";
           result=Integer.parseInt(x)+Integer.parseInt(y);
       }catch (Exception e){
           throw new Exception("error in add");
       }
        return  result+"";
    }

    // assign a ( add 4 (add 5 7) ) -> assign,a,add,4,add,5,7 -> a,add,4,add,5,7


    // add a (assign x print z)
    // print add 4 5
    public  static  void perform(StringTokenizer line) throws Exception {
        String first=line.nextToken();

        switch (first){
            case "print":
                    String second =line.nextToken();
                    switch (second){
                        case "readFile": print(readFile(line.nextToken())); break;
                        case "add" :print(add(line.nextToken(),line.nextToken())); break;
                        case "input" : print((String) input()); break;
                        default: print(second);
                    }
                    break;
            case "input" : input(); break;
            case "readFile":
                second=line.nextToken();
                if(second.equals("input")) readFile((String) input());
                else readFile(second);
                break;
            case "assign":
                   // assign input readFile a
                     second=line.nextToken();
                     switch (second){
                         case "input": second=(String)input(); break;
                     }
                     String third=line.nextToken();
                     switch (third){
                         case "input": third=input()+""; break;
                         case "readFile" : third=readFile(line.nextToken()); break;
                         case "add" : third=add(line.nextToken(),line.nextToken()); break;
                         default:
                             if(variables.containsKey(third))
                                third=(String) variables.get(third);
                             break;
                     }
                     assign(second,third);
                     break;
            case "writeFile":
                // writefile
                second=line.nextToken();
                switch (second){
                    case "input": second=(String)input(); break;
                }
                third=line.nextToken();
                switch (third){
                    case "input": third=(String)input(); break;
                    case "readFile" : third=readFile(line.nextToken()); break;
                    case "add" : third=add(line.nextToken(),line.nextToken()); break;
                    default:
                        if(variables.containsKey(third))
                            third=(String) variables.get(third);
                        break;
                }
                writeFile(second,third);
                break;
            case "add":
                // add add 4 5 add 4 5
                second=line.nextToken();
                switch (second){
                    case "add":
                        second=add(line.nextToken(),line.nextToken());
                        break;
                    case "input":
                        second=(String) input();
                        break;
                }
                third=line.nextToken();
                switch (third){
                    case "add":
                        third=add(line.nextToken(),line.nextToken());
                        break;
                    case "input":
                        third=(String) input();
                        break;
                }
                add(second,third);
                break;
        }
    }
//    public static void perform(StringTokenizer stringTokenizer) throws Exception {
//        if(!stringTokenizer.hasMoreTokens()) return "";
//        String temp=stringTokenizer.nextToken();
//        if(!(temp.equals("print") || temp.equals("assign") || temp.equals("add") || temp.equals("writeFile") || temp.equals("readFile"))){
//            return temp;
//        }
//        // print readfile a
//        if(temp.equals("print")) print((String) perform(stringTokenizer)); // readfile a
//        else if(temp.equals("assign")){
//            Object left=perform(stringTokenizer);
//            Object right=perform(stringTokenizer);
//            assign((String) left,right);
//        }
//        else if(temp.equals("readFile"))
//            return readFile((String) perform(stringTokenizer));
//        else if(temp.equals("add")) {
//            String left = perform(stringTokenizer);
//            String right = perform(stringTokenizer);
//            return add(left, right);
//        }else if(temp.equals("writeFile")) {
//            String left = perform(stringTokenizer);
//            String right = perform(stringTokenizer);
//            writeFile(left, right);
//        }
//        return "";
//    }
    public static void run(String path) throws Exception {
        File file = new File(System.getProperty("user.dir")+"/"+path);
        BufferedReader br=new BufferedReader(new FileReader(file));
        String st;
        while((st= br.readLine())!=null){
            StringTokenizer stringTokenizer=new StringTokenizer(st," ");
            perform(stringTokenizer);
        }
    }
    public static void print(String  x)
    {
        System.out.println(variables.containsKey(x) ? variables.get(x):x);
    }
    public static Object input(){
        Scanner sc=new Scanner(System.in);
        String str=sc.next();
        Object temp=null;
        try {
            temp=(Integer)Integer.parseInt(str);
        }catch (Exception e){
            temp=str;
        }
        return temp;
    }
    public static void main(String[] args) throws Exception {
       run("Program 3.txt");
        System.out.println(variables);
    }
}
