import java.io.*;
import java.util.StringTokenizer;

public class Parser {
    static OS os;

    public Parser() {
        os = new OS();
    }

    public void assign(String x,String y,PCB p) throws Exception {
	    int s = os.searchMemory(x,p.varStart,p.varEnd);
        if(s == -1) {
	      if(p.varStart-1==p.codeEnd){
	          throw new Exception("there is no more space for this variable");
          }
	      os.writeMemory(x,y,--p.varStart);
	    }
	    else
	        os.writeMemory(x,y,s);
    }

    public String add(String x,String y,PCB p) throws Exception {
       int result=0;
       try {
           String x1=x;
           if(os.searchMemory(x,p.varStart,p.varEnd) != -1) x=os.readMemory(x,p.varStart,p.varEnd);
           if(os.searchMemory(y,p.varStart,p.varEnd) != -1) y=os.readMemory(y,p.varStart,p.varEnd);
           result=Integer.parseInt(x)+Integer.parseInt(y);
           // TODO
           assign(x1,result+"",p);
       }catch (Exception e){
           throw new Exception("error in add");
       }
        return result+"";
    }

    public void perform(String line, PCB p) throws Exception {
        String[] Line = line.split(" ");
        String first=Line[0];

        switch (first){
            case "print":
                    String second =Line[1];
                    switch (second){
                        case "readFile": os.print(os.readFile(Line[2],p)); break;
                        case "add" :os.print(add(Line[2],Line[3],p)); break;
                        case "input" : os.print((String) os.input()); break;
                        default:
                            if(os.searchMemory(second,p.varStart,p.varEnd) != -1) second=os.readMemory(second,p.varStart,p.varEnd);
                            os.print(second);
                    }
                    break;
            case "input" : os.input(); break;
            case "readFile":
                second=Line[1];
                if(second.equals("input")) os.readFile(((String) os.input()),p);
                else if (second.equals("add")) os.readFile((add(Line[2],Line[3],p)),p);
                else os.readFile(second,p);
                break;
            case "assign":
                   // assign input readFile a
                     second=Line[1];
                     switch (second){
                         case "input": second=(String)os.input(); break;
                     }
                     String third=Line[2];
                     switch (third){
                         case "input": third=os.input()+""; break;
                         case "readFile" : third=os.readFile(Line[3],p); break;
                         case "add" : third=add(Line[3],Line[4],p); break;
                         default:
                             if(os.searchMemory(third,p.varStart,p.varEnd) != -1)
                                third=os.readMemory(third,p.varStart,p.varEnd);
                             break;
                     }
                     assign(second,third,p);
                     break;
            case "writeFile":
                // writefile
                second=Line[1];
                switch (second){
                    case "input": second=(String)os.input(); break;
                    default:
                        if(os.searchMemory(second,p.varStart,p.varEnd) != -1)
                            second=os.readMemory(second,p.varStart,p.varEnd);
                }
                third=Line[2];
                switch (third){
                    case "input": third=(String)os.input(); break;
                    case "readFile" : third=os.readFile(Line[3],p); break;
                    case "add" : third=add(Line[3],Line[4],p); break;
                    default:
                        if(os.searchMemory(third,p.varStart,p.varEnd) != -1)
                            third=os.readMemory(third,p.varStart,p.varEnd);
                        break;
                }
                os.writeFile(second,third,p);
                break;
            case "add":
                // add add 4 5 add 4 5
                second=Line[1];
                switch (second){
                    case "add":
                        second=add(Line[2],Line[3],p);
                        break;
                    case "input":
                        second=(String) os.input();
                        break;
                }
                third=Line[2];
                switch (third){
                    case "add":
                        third=add(Line[3],Line[4],p);
                        break;
                    case "input":
                        third=(String) os.input();
                        break;
                }
                add(second,third,p);
                break;
        }
    }

    public void create(String path) throws Exception {
        BufferedReader br = os.readProgram(path);
        String st;
        int pc=os.processes*100+1;
        int codeStart=pc;
        int codeEnd=os.processes*100;
        int pcbLocation=pc-1;
        int varStart=(os.processes+1)*100;
        int varEnd=((os.processes+1)*100)-1;
        if(varEnd>=2000){
            throw new Exception("No more space for this program");
        }
        PCB pcb=new PCB(os.processes,ProcessState.NotRunning,pc,codeStart,codeEnd,pcbLocation,varStart,varEnd);
        while((st=br.readLine())!=null){
            if(pcb.codeEnd+1==varStart){
                throw new Exception("No more space for this input instruction");
            }
            os.writeMemoryProcess(st,++pcb.codeEnd);
        }
        os.processes++;
    }

    public void run() {
        // put the scheduler code here
    }

    public static void main(String[] args) throws Exception {
        Parser p = new Parser();
        for(int i=1;i<=3;i++){
            p.create("Program "+i);
        }
        p.run();
    }
}
