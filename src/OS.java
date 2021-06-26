import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class OS {
    static Scanner scanner;
    private Object[] memory;
    private int processes;
    private int completedProcesses ;

    public OS (){
        scanner=new Scanner(System.in);
        memory = new Object[2048]; // Memory management
        processes = 0;
        completedProcesses = 0 ;
    }

    public void writeMemoryProcess (String line, int location) {
        memory[location] = line;
    }

    public String readMemory (String var, int varStart, int varEnd) {
        for(int i=varStart; i<=varEnd; i++)
            if (((Pair)memory[i]).x != null && (((Pair)memory[i]).x).equals(var))
                return (String) (((Pair)memory[i]).y);
        return null;
    }

    public void writeMemory (String var, String val, int varPC) {
        memory[varPC] = new Pair(var,val);
    }

    public int searchMemory (String var, int varStart, int varEnd) {
        for(int i=varStart; i<=varEnd; i++)
            if ((((Pair)memory[i]).x).equals(var))
                return i;
        return -1;
    }

    // take file name on the form xxxx
    public String readFile(String path,PCB p) throws IOException {
        StringBuilder result=new StringBuilder();
        try {
            if(searchMemory(path,p.varStart,p.varEnd) != -1) path=readMemory(path,p.varStart,p.varEnd);
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

    public void writeFile(String path,String data,PCB p){  // path is 1st variable, data is 2nd one
        createFile(path);
        try {
            File file = new File(System.getProperty("user.dir") + "/" + path+".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append("\n");
            if(searchMemory(data,p.varStart,p.varEnd) != -1) data=readMemory(data,p.varStart,p.varEnd);
            writer.append(data);
            writer.close();
        }catch (Exception e){
            System.out.println("error at readFile");
        }
    }

    public void print(String  x)
    {
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

    // this method takes the process number and the index within that process and returns the memory object of this address

    public Object getMemoryOf(int process , int idx ) throws Exception {
         if (idx<process*100 || idx>=(process+1)*100)
            throw new Exception("Access not allowed out of process memory bound");
         return memory[process*100+idx];
    }

    public void processCompleted () {
        completedProcesses ++ ;
    }

    public boolean allProcessesCompleted () {
        return completedProcesses == processes ;
    }

    public int getProcesses() {
        return processes;
    }

    public void setProcesses(int processes) {
        this.processes = processes;
    }
}
