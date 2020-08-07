package ws.otter.constants;

public enum StatusCode {
    OK("ok"),

    DUPLICATE("duplicate"),

    PERMISSION_DENIED("permissionDenied"),

    TOKEN_ERROR("tokenError"),

    FORMAT_ERROR("formatError"),

    PARSE_ERROR("parseError"),

    DB_ERROR("dbError"),

    DATA_ERROR("dataError"),

    SERVER_ERROR("serverError"),

    UNKNOWN_ERROR("unknownError"),;

    private String code;

    private StatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}