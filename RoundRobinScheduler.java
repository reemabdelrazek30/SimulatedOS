package MS2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobinScheduler 
{

	private static String path = (System.getProperty("user.dir").toString()) + "/src/" + SystemCalls2.class.getPackage().getName() + "//";
	private static String [] memory = new String[100];  
	private static int memoryIndex = 0;
	private static int id = 1;
	private static Queue<PCB> processes = new LinkedList<PCB>(); 
	public static String[] getMemory() {
		return memory;
	}
	public static void setMemory(int index, String value) {
		memory[index] = value;
	}
	public static void executeNext() throws IOException
	{
		PCB dequeued = processes.remove();
		dequeued.setProcessState("running");
		System.out.println("ID" + memory[(dequeued.getFirstIndexInMemory())] + " here" + (dequeued.getFirstIndexInMemory() ));
		System.out.println(memory[(dequeued.getFirstIndexInMemory() + 2)] + " " + (dequeued.getFirstIndexInMemory() + 2)); // printing running state
		memory[dequeued.getFirstIndexInMemory()+2] = "State :running"; 
		System.out.println(memory[(dequeued.getFirstIndexInMemory() + 2)] + " " + (dequeued.getFirstIndexInMemory() + 2));
		System.out.println(memory[(dequeued.getFirstIndexInMemory() + 3)] + " " + (dequeued.getFirstIndexInMemory() + 3));
		int pc = dequeued.getPC();
		SystemCalls2.setPCB(dequeued);
		for (int i = 0 ; i < 3 && pc <= dequeued.getLastIndexInMemory() ; i++)
		{
			System.out.println("instruction " + (pc - dequeued.getFirstIndexInMemory() - 4) + " " + memory[pc]);
			SystemCalls2.interpretCode(memory[pc++]);
			memory[dequeued.getFirstIndexInMemory()+3] = "PC:" + pc + "";
			System.out.println(memory[(dequeued.getFirstIndexInMemory() + 3)] + " " + (dequeued.getFirstIndexInMemory() + 3));
		}
		if (pc <= dequeued.getLastIndexInMemory())
		{
			System.out.println(memory[(dequeued.getFirstIndexInMemory() + 2)] + " " + (dequeued.getFirstIndexInMemory() + 2));
			memory[dequeued.getFirstIndexInMemory()+2] = "State :not running";
			System.out.println(memory[(dequeued.getFirstIndexInMemory() + 2)] + " " + (dequeued.getFirstIndexInMemory() + 2));
			dequeued.setProcessState("not running");
			System.out.println(memory[(dequeued.getFirstIndexInMemory() + 3)] + " " + (dequeued.getFirstIndexInMemory() + 3));
			memory[dequeued.getFirstIndexInMemory()+3] = "PC:" + pc + "";
			System.out.println(memory[(dequeued.getFirstIndexInMemory() + 3)] + " " + (dequeued.getFirstIndexInMemory() + 3));
			dequeued.setPC(pc);
			processes.add(dequeued);
		}
		else 
		{
			System.out.println("Program Ended " + memory[dequeued.getFirstIndexInMemory()]);
			int numOfInst = dequeued.getLastIndexInMemory() - dequeued.getFirstIndexInMemory() - 4 ;
			System.out.println("It ran for" + (numOfInst / 2.0) + "quantas");
			System.out.println(memory[(dequeued.getFirstIndexInMemory() + 2)] + " " + (dequeued.getFirstIndexInMemory() + 2));
			memory[dequeued.getFirstIndexInMemory()+2] = "State :finished";
			dequeued.setProcessState("finished");
			System.out.println(memory[(dequeued.getFirstIndexInMemory() + 3)] + " " + (dequeued.getFirstIndexInMemory() + 3));
			memory[dequeued.getFirstIndexInMemory()+3] = "PC:" + (pc-1) + "";
			System.out.println(memory[(dequeued.getFirstIndexInMemory() + 3)] + " " + (dequeued.getFirstIndexInMemory() + 3));
			dequeued.setPC(pc-1);
			System.out.println(memory[(dequeued.getFirstIndexInMemory() + 3)] + " " + (dequeued.getFirstIndexInMemory() + 3));
		}

	}

	public static void schedule() throws IOException 
	{
		while(!processes.isEmpty())
			executeNext();
	}
	public static void start(File file) throws IOException
	{
		PCB currentProcess = new PCB();
		currentProcess.setProcessId(id);
		currentProcess.setFirstIndexInMemory(memoryIndex);
		memory[memoryIndex++] = "Id:" + id;
		id++;
		int startIndex = memoryIndex - 1 ;
		memory[memoryIndex] = "Start:" + startIndex;
		memoryIndex++;
		currentProcess.setProcessState("not running");
		memory[memoryIndex++] = "State :not running";
		int memoryNow = memoryIndex;
		memoryIndex += 2;                   
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		currentProcess.setPC(memoryIndex);
		memory[memoryNow++] = "PC:" + memoryIndex;
		while ((line = br.readLine()) != null)
			memory[memoryIndex++] = line;
		br.close();
		int endIndex = memoryIndex-1;
		memory[memoryNow] = "End:" + endIndex;
		currentProcess.setLastIndexInMemory(endIndex);
		currentProcess.setLastVariablesIndex(endIndex);
		memoryIndex += 5 ;
		for (int i = currentProcess.getFirstIndexInMemory() ; i <= currentProcess.getLastIndexInMemory() ; i++)
			System.out.print(i + "-" + memory[i] +" "); //here
		processes.add(currentProcess);
	}

	public static void main(String[] args) throws IOException 
	{
		//start(new File(path + "Program 1" + ".txt"));
		start(new File(path + "Program 2" + ".txt"));
		start(new File(path + "Program 1" + ".txt"));
		start(new File(path + "Program 3" + ".txt"));
		schedule();
	}

}
