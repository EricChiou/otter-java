package ws.otter.constants;

public enum UserStatus {

    Active("active"),

    Inactive("inactive"),

    Blocked("blocked"),;

    private String status;

    private UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}