package cleancode.studycafe.tobe.machine.exception;

public class AppServerException extends AppException {

    public AppServerException(String message) {
        super(message);
    }

    public AppServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
