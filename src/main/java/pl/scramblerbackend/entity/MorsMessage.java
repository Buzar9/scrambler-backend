package pl.scramblerbackend.entity;

public class MorsMessage {

    private String message;

    public MorsMessage() {};

    public MorsMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
