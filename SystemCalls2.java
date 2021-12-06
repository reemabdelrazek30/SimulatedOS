package MS2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SystemCalls2
{
	 //static String path = "C:\\Users\\reem\\Desktop\\Subjects\\";
	 static String path = (System.getProperty("user.dir").toString()) +"/src/" + SystemCalls2.class.getPackage().getName()+"//";
	 // static HashMap<String,String> map = new HashMap<String,String>();
	 private static PCB currentPCB = null;
	 static String [] memory = RoundRobinScheduler.getMemory();
	 public static void setPCB(PCB current) {
		 currentPCB = current;
	 }
		/* ----------------- old version ------------------------
		 * public static String readFile(String fileOrVar) throws IOException { if
		 * (map.containsKey(fileOrVar)) fileOrVar = map.get(fileOrVar); String line ;
		 * String content = ""; BufferedReader myReader = new BufferedReader (new
		 * FileReader(path + fileOrVar + ".txt")); while ((line = myReader.readLine())
		 * != null) content = content + line; myReader.close(); return content; }
		 */
	 public static String readFile(String fileOrVar) throws IOException 
	 {
	     int returnIndex = checkContainment(fileOrVar);
	     System.out.println(returnIndex);
	     if (returnIndex != -1)
	     {  
	    	 System.out.println(memory[returnIndex] + " " + returnIndex);
	    	 int startIndex = memory[returnIndex].lastIndexOf(":");
	    	 fileOrVar = memory[returnIndex].substring(startIndex+1);
//	    	 if (!(new File(path + fileOrVar + ".txt").exists()))
//	    		 fileOrVar = otherFileOrVar;
	     }
	     String content = "" , line = "" ;
	     BufferedReader myReader = new BufferedReader(new FileReader(path + fileOrVar + ".txt"));
	     while ((line = myReader.readLine()) != null)
	    	 content = content + line; 
	     myReader.close(); 
	     return content;	 
	     
	    
	 }
	    
		/* ----------------- old version ------------------------
		 * public static void writeFile(String fileOrVar , String x) throws IOException
		 * { if (map.containsKey(x)) x = map.get(x); FileWriter myWriter; if
		 * (map.containsKey(fileOrVar)) myWriter = new FileWriter(new File(path +
		 * map.get(fileOrVar) + ".txt")); else myWriter = new FileWriter(new File(path +
		 * fileOrVar + ".txt")); myWriter.write(x); myWriter.close();
		 * 
		 * }
		 */
	     public static void writeFile(String fileOrVar , String x) throws IOException
	     {
	    	 int returnIndex = checkContainment(x);
		     if (returnIndex != -1)
		     {  
		    	 System.out.println(memory[returnIndex] + " " + returnIndex);
		    	 int startIndex = memory[returnIndex].lastIndexOf(":");
		    	 x = memory[returnIndex].substring(startIndex+1);
		     }
		     FileWriter myWriter;
		     int returnIndex2 = checkContainment(fileOrVar);
		     if (returnIndex2 != -1)
		     {  
		    	 System.out.println(memory[returnIndex2] + " " + returnIndex2);
		    	 int startIndex = memory[returnIndex2].lastIndexOf(":");
		    	 fileOrVar = memory[returnIndex2].substring(startIndex+1);
		     }
		     myWriter = new FileWriter(new File(path + fileOrVar + ".txt")); 
		     myWriter.write(x); 
		     myWriter.close();
		     
	     }
     
		/* --------------------- old version ---------------------
		 * public static void print(String text) throws IOException { if (new File(path
		 * + text + ".txt").exists()) System.out.print(readFile(text)); else if
		 * (map.containsKey(text)) System.out.print(map.get(text)); else
		 * System.out.println(text); }
		 */
	 /* ------------- new version -------------- */
	 public static void print(String text) throws IOException
	 {
		 
		 if (new File(path + text + ".txt").exists()) 
			 System.out.println(readFile(text));
		 else 
		 {
			 int returnIndex = checkContainment(text);
			 //System.out.println(returnIndex);
			 if (returnIndex != -1)
			 {
				 System.out.println(memory[returnIndex] + " " + returnIndex);
				 System.out.println(memory[returnIndex].substring(memory[returnIndex].lastIndexOf(":")+1));
			 }
			 else 
				 System.out.println(text);
		 }
		 
	 }
     
     public static String readInput() throws IOException 
     { 
    	 @SuppressWarnings("resource")
		 Scanner sc = new Scanner (System.in);
    	 System.out.println("please enter an input");
    	 String input = sc.nextLine();
   	   	 return input;
     }
     
		/* --------- This is the old version ------------------
		 * public static void assign(String key , String value) throws IOException {
		 * if(value.equals("input")) value = readInput(); if (map.containsKey(key))
		 * map.replace(key, value); else map.put(key, value); }
		 */
		
     public static int checkContainment(String key)
     {
    	 int firstVariablesIndex = currentPCB.getLastIndexInMemory() + 1;
    	 int lastVariablesIndex = currentPCB.getLastVariablesIndex();
    	 for (int i = firstVariablesIndex ; i <= lastVariablesIndex ; i++)
    	 {
    		 System.out.println(memory[i] + " " + i);
    		 if (memory[i].startsWith("_" + key))
    			return i;
    	 }
    	 //System.out.print("here 2");
    	 return -1;
     }
     public static void assign(String key , String value) throws IOException 
     {
    	 if(value.equals("input")) 
    		 value = readInput();
    	 String [] memory = RoundRobinScheduler.getMemory();
    	 int returnIndex = checkContainment(key);
    	 if (returnIndex != -1)
    	 {
    		 System.out.println(memory[returnIndex] + " " + returnIndex);
    		 int delimiterIndex = memory[returnIndex].indexOf(":");
    		 String keyAndReplacedValue = memory[returnIndex].replace(memory[returnIndex].substring(delimiterIndex+1), value);
    		 System.out.println(memory[returnIndex] + " " + returnIndex);
    		 RoundRobinScheduler.setMemory(returnIndex, keyAndReplacedValue);
    	 } 
    	 else 
    	 {
    		 String newKeyAndValue = "_" + key + ":" + value;
    		 RoundRobinScheduler.setMemory(currentPCB.getLastVariablesIndex()+1, newKeyAndValue);
    	     currentPCB.setLastVariablesIndex(currentPCB.getLastVariablesIndex()+1);
    	     System.out.println(memory[currentPCB.getLastVariablesIndex()] + " " + currentPCB.getLastVariablesIndex());
    	 }
    		  
    	 
     }
     /* public static void add(String a , String b) throws IOException {
      *  if
		 * (map.containsKey(a)) { 
		 * if (map.containsKey(b)) 
		 * map.replace(a, Integer.parseInt(map.get(b)) + Integer.parseInt(map.get(a)) + "");
		 *  else
		 * map.replace(a, Integer.parseInt(b) + Integer.parseInt(map.get(a)) + ""); }
		 * else print("There exists an error!");
		 * 
		 * }
		 */
     public static void add(String a , String b) throws IOException
     {
    	 int returnIndex = checkContainment(a);
    	 int returnIndex2 = checkContainment(b);
    	 //System.out.println(returnIndex +" " + returnIndex2);
    	 if (returnIndex != -1 && returnIndex2 != -1)
    	 {
    		 System.out.println(memory[returnIndex] + " " + returnIndex);
    		 int valueOfA = Integer.parseInt(memory[returnIndex].substring(memory[returnIndex].lastIndexOf(":")+1));
    		 System.out.println(memory[returnIndex2] + " " + returnIndex2);
    		 int valueOfB = Integer.parseInt(memory[returnIndex2].substring(memory[returnIndex2].lastIndexOf(":")+1));
    		 int val = valueOfA + valueOfB ;
    		 String newVar = memory[returnIndex].replace(memory[returnIndex].substring(memory[returnIndex].lastIndexOf(":")),":"+val+"");
    		 RoundRobinScheduler.setMemory(returnIndex,newVar);
    		 System.out.println(memory[returnIndex] + " " + returnIndex);
    	 }
    	 else if (returnIndex != -1)
    	 {
    		 System.out.println(memory[returnIndex] + " " + returnIndex);
    		 int valueOfA = Integer.parseInt(memory[returnIndex].substring(memory[returnIndex].lastIndexOf(":")+1));
    		 int val = Integer.parseInt(b) + valueOfA;
    		 RoundRobinScheduler.setMemory(returnIndex, val +"");
    		 System.out.println(memory[returnIndex] + " " + returnIndex);
     	 }	 
    	 else 
    		 System.out.println("There exists an error");
     }
     
     /*------------ here is the new Version -------------------*/
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
    				 System.out.print("here");
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
     
		/*
		 * public static void parseCode(File file) throws IOException { try {
		 * BufferedReader br = new BufferedReader(new FileReader(file)); String line ;
		 * while((line = br.readLine()) != null) interpretCode(line); br.close(); }
		 * catch (Exception e) { System.out.println("An error ocurred .. "); } }
		 */
     
}

