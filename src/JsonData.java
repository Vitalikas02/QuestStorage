public class JsonData {
    private String name;
    private String type;
    private String date;
    private String rollback;

    JsonData(String name, String type, String date, String rollback) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.rollback = rollback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRollback() {
        return rollback;
    }

    public void setRollback(String rollback) {
        this.rollback = rollback;
    }

    public String toString() {
        return "name=" + name
                + "type=" + type
                + ",date=" + date
                + ",rollback=" + rollback;
    }
}
