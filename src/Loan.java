public class Loan extends Core {

    protected int id;
    protected int ISBN;
    protected int userid;
    protected int beginDate;        //written in millisecond
    protected int duration;
    protected boolean expired;

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public int getBeginDate() {
        return beginDate;
    }

    public int getID() {
        return id;
    }

    public int getISBN() {
        return ISBN;
    }

    public int getUserid() {
        return userid;
    }

    public boolean getExpired() {
        return expired;
    }
}

