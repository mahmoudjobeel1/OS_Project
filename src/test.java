import java.io.*;
import java.util.*;

public class test {
	public static void main(String[] args) throws NumberFormatException, IOException {
		HashMap<String, Object> var = new HashMap<>();
		File file = new File("Program 1.txt");
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String st;
		while ((st = br.readLine()) != null) {
	        String[] line = st.split(" ");
	        switch (line[0]) {
	        	case "print":
	        		if (var.containsKey(line[1]))
	        			System.out.println(var.get(line[1]));
	        		else
	        			System.out.println(line[1]);
	        		break;
	        	case "assign":
	        		if (line[2].equals("input")) {
	        			Scanner sc = new Scanner(System.in);
		        		var.put(line[1], sc.next());
	        		}
	        		else if (line[2].equals("readFile")) {
	        			File file1 = new File(line[3]);
	        			BufferedReader br1 = new BufferedReader(new FileReader(file1));
	        			String st1;
	        			String f = "";
	        			while ((st1 = br1.readLine()) != null)
	        				f = f + st1 + "\n";
		        		var.put(line[1], f);
	        		}
	        		else if (var.containsKey(line[2])) {
	        			var.put(line[1], var.get(line[2]));
	        		}
	        		else {
	        			var.put(line[1], line[2]);
	        		}
	        		break;
	        	case "add":
	        		int x;
	        		int y;
	        		if (var.containsKey(line[1]))
	        			x = Integer.parseInt((String) var.get(line[1]));
	        		else
	        			x = Integer.parseInt(line[1]);
	        		if (var.containsKey(line[2]))
	        			y = Integer.parseInt((String) var.get(line[2]));
	        		else
	        			y = Integer.parseInt(line[2]);
	        		var.put(line[1], x+y);
	        		break;
	        	case "writeFile":
	        		try {
	        			String a;
	        			String b;
		        		if (var.containsKey(line[1]))
		        			a = (String) var.get(line[1]);
		        		else
		        			a = line[1];
		        		if (var.containsKey(line[2]))
		        			b = (String) var.get(line[2]);
		        		else
		        			b = line[2];
	        			FileWriter myWriter = new FileWriter(a);
	        		    myWriter.write(b);
	        		    myWriter.close();
	        		} catch (IOException e) {
	        		    System.out.println("An error occurred.");
	        		    e.printStackTrace();
	        		}
	        		break;
	        	default:
	        		break;
	        }
		}
	}

}
