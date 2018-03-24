package wda.com.diplomawork.core.requets;

/**
 * Created by wedoapps on 3/17/18.
 */

public class RequestResult {
    private int code;
    private String name;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
