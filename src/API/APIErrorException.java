package API;

public class APIErrorException extends Exception{
    private int errorCode;
    public APIErrorException(int errorCode){
        this.errorCode=errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
