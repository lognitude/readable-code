package cleancode.studycafe.tobe.machine.config;

import cleancode.studycafe.tobe.machine.StudyCafePassMachine;
import cleancode.studycafe.tobe.machine.finder.StudyCafeLockerPassFinder;
import cleancode.studycafe.tobe.machine.finder.StudyCafeLockerPassFinders;
import cleancode.studycafe.tobe.machine.finder.StudyCafeSeatPassFinders;
import cleancode.studycafe.tobe.machine.finder.locker.StudyCafeFixedLockerPassFinder;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafeLockerPassFileReader;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassFileReader;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassReader;
import cleancode.studycafe.tobe.machine.io.view.ConsoleInputHandler;
import cleancode.studycafe.tobe.machine.io.view.ConsoleOutputHandler;
import cleancode.studycafe.tobe.machine.InputHandler;
import cleancode.studycafe.tobe.machine.OutputHandler;
import cleancode.studycafe.tobe.machine.finder.seat.StudyCafeFixedSeatPassFinder;
import cleancode.studycafe.tobe.machine.finder.seat.StudyCafeHourlySeatPassFinder;
import cleancode.studycafe.tobe.machine.finder.StudyCafeSeatPassFinder;
import cleancode.studycafe.tobe.machine.finder.seat.StudyCafeWeeklySeatPassFinder;
import java.util.List;

public class MachineConfig {

    public StudyCafePassMachine studyCafePassMachine() {
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();
        StudyCafeSeatPassFinders seatPassFinders = getStudyCafeSeatPassFinders();
        StudyCafeLockerPassFinders lockerPassFinders = getStudyCafeLockerPassFinders();

        return StudyCafePassMachine.of(inputHandler, outputHandler, seatPassFinders, lockerPassFinders);
    }

    private StudyCafeSeatPassFinders getStudyCafeSeatPassFinders() {
        List<StudyCafeSeatPassFinder> passFinderList = getStudyCafeSeatPassFinderList();
        return StudyCafeSeatPassFinders.from(passFinderList);
    }

    private StudyCafeLockerPassFinders getStudyCafeLockerPassFinders() {
        List<StudyCafeLockerPassFinder> lockerPassFinderList = getStudyCafeLockerPassFinderList();
        return StudyCafeLockerPassFinders.from(lockerPassFinderList);
    }

    private List<StudyCafeSeatPassFinder> getStudyCafeSeatPassFinderList() {
        StudyCafePassReader passReader = new StudyCafePassFileReader();

        return List.of(
                StudyCafeHourlySeatPassFinder.from(passReader),
                StudyCafeWeeklySeatPassFinder.from(passReader),
                StudyCafeFixedSeatPassFinder.from(passReader)
        );
    }

    private List<StudyCafeLockerPassFinder> getStudyCafeLockerPassFinderList() {
        StudyCafePassReader lockerPassReader = new StudyCafeLockerPassFileReader();

        return List.of(StudyCafeFixedLockerPassFinder.from(lockerPassReader));
    }
}
