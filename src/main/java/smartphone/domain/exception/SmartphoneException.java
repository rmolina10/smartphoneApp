package smartphone.domain.exception;

import lombok.Getter;

@Getter
public class SmartphoneException extends RuntimeException {

    private final ErrorsEnum error;

    private final transient Object[] value;

    public SmartphoneException(ErrorsEnum error) {
        super(error.getMessage());
        this.error = error;
        value = new Object[0];
    }

    public SmartphoneException(ErrorsEnum error, Object... value) {
        super(error.getMessage());
        this.error = error;
        this.value = value;
    }

    public SmartphoneException(ErrorsEnum error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
        this.value = new Object[0];
    }

    public SmartphoneException(ErrorsEnum error, Throwable t, Object... value) {
        super(error.getMessage(), t);
        this.error = error;
        this.value = value;
    }
}
