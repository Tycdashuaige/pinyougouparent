package entity;

/**
 * @author tangyucong
 * @Title: Result
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/49:08
 */
public class Result {

    private boolean success;

    private String message;

    public Result(boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }

    public Result(){}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
