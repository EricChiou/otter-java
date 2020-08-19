package ws.otter.constants;

public enum StatusCode {
    OK("ok"),

    ACC_INACTIVE("accInactive"),

    /** duplicate in DB table */
    DUPLICATE("duplicate"),

    PERMISSION_DENIED("permissionDenied"),

    TOKEN_ERROR("tokenError"),

    /** request parameters error */
    FORMAT_ERROR("formatError"),

    /** request body json parse error */
    PARSE_ERROR("parseError"),

    /** something error when execute DB */
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