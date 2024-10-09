package cleancode.studycafe.tobe.machine.io.reader;

import cleancode.studycafe.tobe.machine.exception.AppServerException;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.StudyCafeSeatPass;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeSeatPassFileReader implements StudyCafePassReader {

    private static final String PASS_LIST_FILE_NAME = "src/main/resources/cleancode/studycafe/pass-list.csv";
    private static final String FILE_CONTENT_DELIMITER = ",";

    private static final int STUDY_CAFE_PASS_TYPE_INDEX = 0;
    private static final int DURATION_INDEX = 1;
    private static final int PRICE_INDEX = 2;
    private static final int DISCOUNT_RATE_INDEX = 3;

    @Override
    public List<StudyCafePass> readStudyCafePasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(PASS_LIST_FILE_NAME));

            return convertStudyCafeSeatPasses(lines);
        } catch (IOException e) {
            throw new AppServerException("파일을 읽는데 실패했습니다.", e);
        }
    }

    private List<StudyCafePass> convertStudyCafeSeatPasses(List<String> lines) {
        List<StudyCafePass> studyCafeSeatPasses = new ArrayList<>();

        for (String line : lines) {
            StudyCafePass studyCafeSeatPass = convertStudyCafeSeatPass(line);
            studyCafeSeatPasses.add(studyCafeSeatPass);
        }

        return studyCafeSeatPasses;
    }

    private StudyCafePass convertStudyCafeSeatPass(String line) {
        String[] contents = line.split(FILE_CONTENT_DELIMITER);

        StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(contents[STUDY_CAFE_PASS_TYPE_INDEX]);
        int duration = Integer.parseInt(contents[DURATION_INDEX]);
        int price = Integer.parseInt(contents[PRICE_INDEX]);
        double discountRate = Double.parseDouble(contents[DISCOUNT_RATE_INDEX]);

        return StudyCafeSeatPass.of(studyCafePassType, duration, price, discountRate);
    }
}
