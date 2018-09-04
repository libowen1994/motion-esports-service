package one.motion.mall.dto;

public class JsonResult {
    private Boolean success = true;
    private String message;

    public JsonResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JsonResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}