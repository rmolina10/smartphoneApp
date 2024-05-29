package smartphone.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorsEnum {

    INTERNAL_SERVER_ERROR("GEN001", "Internal server error", ""),
    REPOSITORY_EXCEPTION(
            "REPOSITORY001",
            "Repository exception",
            "Error performing requested operation in repository."),

    SMARTPHONE_NOT_FOUND("SMA001", "Smartphone not Found", "There is no smartphone for this request.");

    private final String code;

    private final String description;

    private final String message;

    ErrorsEnum(String code, String description, String message) {
        this.code = code;
        this.description = description;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.valueOf(description);
    }
}
