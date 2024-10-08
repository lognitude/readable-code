package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.machine.StudyCafePassMachine;
import cleancode.studycafe.tobe.machine.config.MachineConfig;

public class StudyCafeApplication {

    public static void main(String[] args) {
        MachineConfig machineConfig = new MachineConfig();
        StudyCafePassMachine studyCafePassMachine = machineConfig.studyCafePassMachine();
        studyCafePassMachine.run();
    }
}
