package smartphone.domain.exception;

public class SmartphoneRepositoryException extends SmartphoneException {

    public SmartphoneRepositoryException(ErrorsEnum error) {
        super(error);
    }

    public SmartphoneRepositoryException(ErrorsEnum error, Throwable t, Object... value) {
        super(error, t, value);
    }

    public SmartphoneRepositoryException(ErrorsEnum error, Object... value) {
        super(error, value);
    }
}
