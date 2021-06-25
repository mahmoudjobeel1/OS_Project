public class PCB {
    int processID;
    ProcessState processState;
    int PC;
    int codeStart;
    int codeEnd;
    int PCB_location;
    int varStart;
    int varEnd;
    int varPC;

    public PCB(int processID, ProcessState processState, int PC, int codeStart, int codeEnd,
               int PCB_location, int varStart, int varEnd) {
        this.processID = processID;
        this.processState = processState;
        this.PC = codeStart;
        this.codeStart = codeStart;
        this.codeEnd = codeEnd;
        this.PCB_location = PCB_location;
        this.varStart = varStart;
        this.varEnd = varEnd;
        this.varPC = varStart;
    }
}
