package MS2;
public class PCB
{
    private int processId;
    private String processState;
    private int pc;
    private int firstIndexInMemory;
    private int lastIndexInMemory;
    private int lastVariablesIndex;
    public PCB (int processId , String ProcessState , int pc , int firstIndexInMemory , int lastIndexInMemory) {
    	this.processId = processId;
    	this.processState = "ready";
    	this.pc = pc;
    	this.firstIndexInMemory = firstIndexInMemory ;
    	this.lastIndexInMemory = lastIndexInMemory ;
        
    }
	public PCB() {
		processId = 0;
		processState = "";
		pc = 0;
		firstIndexInMemory = lastIndexInMemory = lastVariablesIndex = 0;
	}
	public String getProcessState() {
		return processState;
	}
	public void setProcessState(String processState) {
		this.processState = processState;
	}
	public int getPC() {
		return pc;
	}
	public void setPC(int pc) {
		this.pc = pc;
	}
	public int getFirstIndexInMemory() {
		return firstIndexInMemory;
	}
	public void setFirstIndexInMemory(int firstIndexInMemory) {
		this.firstIndexInMemory = firstIndexInMemory;
	}
	public int getLastIndexInMemory() {
		return lastIndexInMemory;
	}
	public void setLastIndexInMemory(int lastIndexInMemory) {
		this.lastIndexInMemory = lastIndexInMemory;
	}
	public int getProcessId() {
		return processId;
	}
    public void setProcessId(int processId){
    	this.processId = processId;
    }
    public int getLastVariablesIndex(){
    	return lastVariablesIndex;
    }
    public void setLastVariablesIndex(int lastVariablesIndex) {
       this.lastVariablesIndex = lastVariablesIndex;
    }
    
}
