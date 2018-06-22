package createnewrepodata;

public class RequestBodyForNewRepo {
    private String name;
    private boolean auto_init;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAuto_init() {
        return auto_init;
    }

    public void setAuto_init(boolean auto_init) {
        this.auto_init = auto_init;
    }
}