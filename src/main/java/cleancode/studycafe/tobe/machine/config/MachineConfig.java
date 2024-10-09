package cleancode.studycafe.tobe.machine.config;

import cleancode.studycafe.tobe.machine.InputHandler;
import cleancode.studycafe.tobe.machine.OutputHandler;
import cleancode.studycafe.tobe.machine.StudyCafePassMachine;
import cleancode.studycafe.tobe.machine.finder.StudyCafeLockerPassFinders;
import cleancode.studycafe.tobe.machine.finder.StudyCafePassFinder;
import cleancode.studycafe.tobe.machine.finder.StudyCafeSeatPassFinders;
import cleancode.studycafe.tobe.machine.finder.locker.StudyCafeFixedPassFinder;
import cleancode.studycafe.tobe.machine.finder.seat.StudyCafeFixedSeatPassFinder;
import cleancode.studycafe.tobe.machine.finder.seat.StudyCafeHourlySeatPassFinder;
import cleancode.studycafe.tobe.machine.finder.seat.StudyCafeWeeklySeatPassFinder;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafeLockerPassFileReader;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassReader;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafeSeatPassFileReader;
import cleancode.studycafe.tobe.machine.io.view.ConsoleInputHandler;
import cleancode.studycafe.tobe.machine.io.view.ConsoleOutputHandler;
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
        List<StudyCafePassFinder> seatPassFinderList = getStudyCafeSeatPassFinderList();
        return StudyCafeSeatPassFinders.from(seatPassFinderList);
    }

    private StudyCafeLockerPassFinders getStudyCafeLockerPassFinders() {
        List<StudyCafePassFinder> lockerPassFinderList = getStudyCafeLockerPassFinderList();
        return StudyCafeLockerPassFinders.from(lockerPassFinderList);
    }

    private List<StudyCafePassFinder> getStudyCafeSeatPassFinderList() {
        StudyCafePassReader passReader = new StudyCafeSeatPassFileReader();

        return List.of(
                StudyCafeHourlySeatPassFinder.from(passReader),
                StudyCafeWeeklySeatPassFinder.from(passReader),
                StudyCafeFixedSeatPassFinder.from(passReader)
        );
    }

    private List<StudyCafePassFinder> getStudyCafeLockerPassFinderList() {
        StudyCafePassReader lockerPassReader = new StudyCafeLockerPassFileReader();

        return List.of(StudyCafeFixedPassFinder.from(lockerPassReader));
    }
}
