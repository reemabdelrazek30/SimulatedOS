package MS2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SystemCalls
{
	 //static String path = "C:\\Users\\reem\\Desktop\\Subjects\\";
	   static String path = (System.getProperty("user.dir").toString()) +"/src/" + SystemCalls.class.getPackage().getName()+"//";
	   static HashMap<String,String> map = new HashMap<String,String>();
     public static String readFile(String fileOrVar) throws IOException 
     { 
    	 if (map.containsKey(fileOrVar)) 
    		 fileOrVar = map.get(fileOrVar);
    	 String line ;
    	 String content = "";
    	 BufferedReader myReader = new BufferedReader (new FileReader(path + fileOrVar + ".txt"));
    	 while ((line = myReader.readLine()) != null)
    	      content = content + line;
    	 myReader.close();
    	 return content;	 
     }
     
     public static void writeFile(String fileOrVar , String x) throws IOException
     {
    	 if (map.containsKey(x))
    		 x = map.get(x);
    	 FileWriter myWriter;
    	 if (map.containsKey(fileOrVar))
    	   myWriter = new FileWriter(new File(path + map.get(fileOrVar) + ".txt"));
    	 else
    	   myWriter = new FileWriter(new File(path + fileOrVar + ".txt"));
    	 myWriter.write(x);
  	     myWriter.close();
    		 
     }
     
     public static void print(String text) throws IOException
     {
    	 if (new File(path + text + ".txt").exists())
    		 System.out.print(readFile(text));
    	 else if (map.containsKey(text))
    		 System.out.print(map.get(text));
    	 else
        	 System.out.println(text);
     }
     
     public static String readInput() throws IOException 
     { 
    	 System.out.println("Please enter an input");
    	 Scanner sc = new Scanner (System.in);
    	 String input = sc.nextLine();
   	   	 return input;
     }
     
     public static void assign(String key , String value) throws IOException 
     {
    	 if(value.equals("input"))
    		 value = readInput();
    	 if (map.containsKey(key))
    		 map.replace(key, value);
    	 else 
    		 map.put(key, value);
     }

     public static void add(String a , String b) throws IOException 
     {
    	 if (map.containsKey(a))
    	 {
    		 if (map.containsKey(b))
    			 map.replace(a, Integer.parseInt(map.get(b)) + Integer.parseInt(map.get(a)) + "");
    		 else 
    			 map.replace(a, Integer.parseInt(b) + Integer.parseInt(map.get(a)) + "");	 
    	 }
    	 else 
    	     print("There exists an error!");
    	 
     }
     
     public static void interpretCode(String line) throws IOException 
     {
    	 StringTokenizer st = new StringTokenizer(line);
    	 String instruction = st.nextToken();
    		 if (instruction.equals("print")) 
    			 print(st.nextToken());
    		 else if (instruction.equals("writeFile"))
    			 writeFile(st.nextToken(),st.nextToken());
    		 else if (instruction.equals("readFile"))
    		 {
    			 String content = readFile(st.nextToken());
    			 print(content);
    		 }    	 
    		 else if (instruction.equals("assign"))
    		 {
    			 String x = st.nextToken();
    			 String next = st.nextToken();
    			 if (next.equals("readFile"))
    			 {
    				 String fname = st.nextToken();
       				 String y = readFile(fname);
    				 assign(x,y);
    			 }
    			 else 
    				 assign(x,next);
    		 }
    		 else if (instruction.equals("add"))
    			 add(st.nextToken(),st.nextToken());
    		 else 
    			 System.out.println("Unknown Instruction..");
    		    
     }
     
     public static void parseCode(File file) throws IOException
     {
    	try 
    	{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line ;
		while((line = br.readLine()) != null) 
			interpretCode(line);
		br.close();
    	}
    	catch (Exception e)
    	{
    		System.out.println("An error ocurred .. ");
    	}
     }
     
     public static void main (String [] args) throws IOException
     {
    	 parseCode(new File(path+"Program 1"+".txt"));
    	 parseCode(new File(path+"Program 2"+".txt"));
    	 parseCode(new File(path+"Program 3"+".txt"));
    	 
     }
}

