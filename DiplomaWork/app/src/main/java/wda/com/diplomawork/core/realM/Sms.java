package wda.com.diplomawork.core.realM;

/**
 * Created by wedoapps on 3/31/18.
 */

public class Sms {
    private String text;
    private String senderId;
    private String sendTime;

    public Sms(String text, String senderId, String sendTime) {
        this.text = text;
        this.senderId = senderId;
        this.sendTime = sendTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
