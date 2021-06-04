import java.io.*;
import java.util.StringTokenizer;

public class Parser {
    static OS os;

    public Parser (){
        os = new OS();
    }

    public static  void assign(String x,String y) throws Exception {
	if(os.variables.containsKey(y))
	    y=os.variables.get(y);
	os.variables.put(x,y);
    }

    public static String add(String x,String y) throws Exception {
       int result=0;
       try {
           String x1=x;
           if(os.variables.containsKey(x)) x=os.variables.get(x)+"";
           if(os.variables.containsKey(y)) y=os.variables.get(y)+"";
           result=Integer.parseInt(x)+Integer.parseInt(y);
           os.variables.put(x1,result+"");
       }catch (Exception e){
           throw new Exception("error in add");
       }
        return  result+"";
    }

    public  static  void perform(StringTokenizer line) throws Exception {
        String first=line.nextToken();

        switch (first){
            case "print":
                    String second =line.nextToken();
                    switch (second){
                        case "readFile": os.print(os.readFile(line.nextToken())); break;
                        case "add" :os.print(add(line.nextToken(),line.nextToken())); break;
                        case "input" : os.print((String) os.input()); break;
                        default: os.print(second);
                    }
                    break;
            case "input" : os.input(); break;
            case "readFile":
                second=line.nextToken();
                if(second.equals("input")) os.readFile((String) os.input());
                else if (second.equals("add")) os.readFile(add(line.nextToken(),line.nextToken()));
                else os.readFile(second);
                break;
            case "assign":
                   // assign input readFile a
                     second=line.nextToken();
                     switch (second){
                         case "input": second=(String)os.input(); break;
                     }
                     String third=line.nextToken();
                     switch (third){
                         case "input": third=os.input()+""; break;
                         case "readFile" : third=os.readFile(line.nextToken()); break;
                         case "add" : third=add(line.nextToken(),line.nextToken()); break;
                         default:
                             if(os.variables.containsKey(third))
                                third=(String) os.variables.get(third);
                             break;
                     }
                     assign(second,third);
                     break;
            case "writeFile":
                // writefile
                second=line.nextToken();
                switch (second){
                    case "input": second=(String)os.input(); break;
                }
                third=line.nextToken();
                switch (third){
                    case "input": third=(String)os.input(); break;
                    case "readFile" : third=os.readFile(line.nextToken()); break;
                    case "add" : third=add(line.nextToken(),line.nextToken()); break;
                    default:
                        if(os.variables.containsKey(third))
                            third=(String) os.variables.get(third);
                        break;
                }
                os.writeFile(second,third);
                break;
            case "add":
                // add add 4 5 add 4 5
                second=line.nextToken();
                switch (second){
                    case "add":
                        second=add(line.nextToken(),line.nextToken());
                        break;
                    case "input":
                        second=(String) os.input();
                        break;
                }
                third=line.nextToken();
                switch (third){
                    case "add":
                        third=add(line.nextToken(),line.nextToken());
                        break;
                    case "input":
                        third=(String) os.input();
                        break;
                }
                add(second,third);
                break;
        }
    }

    public static void run(String path) throws Exception {
        File file = new File(System.getProperty("user.dir")+"/"+path+".txt");
        BufferedReader br=new BufferedReader(new FileReader(file));
        String st;
        while((st= br.readLine())!=null){
            StringTokenizer stringTokenizer=new StringTokenizer(st," ");
            perform(stringTokenizer);
        }
    }

    public static void main(String[] args) throws Exception {
        Parser p = new Parser();
        for(int i=1;i<=3;i++){
            p.run("Program "+i);
        }
    }
}
