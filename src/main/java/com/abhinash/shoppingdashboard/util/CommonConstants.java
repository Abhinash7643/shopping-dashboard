package com.abhinash.shoppingdashboard.util;

public class CommonConstants {

    public static final String USER_PREFIX = "MDMC_";

    public static final String DOCUMENT_PREFIX = "ASF";

    public interface ErrorCode {
        int ENTITY_NOT_FOUND = 1000;
        int REQUEST_ARGUMENT_INVALID = 1001;
        int REQUEST_VALIDATION_ERROR = 1002;
        int SERVER_ERROR = 1003;
        int DB_ERROR = 1004;
        int BAD_REQUEST_ERROR = 1005;
        int SUCCESS = 200;
    }
}
