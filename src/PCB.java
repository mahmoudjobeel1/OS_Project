public class PCB {
    int processID;
    ProcessState processState;
    int PC;
    int codeStart;
    int codeEnd;
    int PCB_location;
    int varStart;
    int varEnd;

    public PCB(int processID, ProcessState processState, int PC, int codeStart, int codeEnd,
               int PCB_location, int varStart, int varEnd) {
        this.processID = processID;
        this.processState = processState;
        this.PC = PC;
        this.codeStart = codeStart;
        this.codeEnd = codeEnd;
        this.PCB_location = PCB_location;
        this.varStart = varStart;
        this.varEnd = varEnd;
    }
}
