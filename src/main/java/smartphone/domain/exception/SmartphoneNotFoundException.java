package smartphone.domain.exception;

public class SmartphoneNotFoundException extends SmartphoneException {

    public SmartphoneNotFoundException(ErrorsEnum error, Object... value) {
        super(error, value);
    }
}
