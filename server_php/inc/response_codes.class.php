<?php


class Responses_Codes {
    public static $oks = array(
            'USER_LOGIN_OK' => 1,
            'USER_REGISTER_OK' => 2,
            'USER_REGISTER_AUTO_OK' => 3,
            'USER_SCREEN_NAME_OK' => 4,
            'LOGOUT_OK' => 5,
            'LOAD_ADI_VOUCHERS_OK' => 50,
            'CREATE_ADI_VOUCHER_OK' => 51,
            'CLAIM_VOUCHER_OK' => 52,
            'USER_VOUCHERS_OK' => 53,
            'CONSUME_VOUCHER_OK' => 54,
            'CURRENT_SUBSCRIPTION_OK' => 55,
            'USER_GCM_TOKEN_OK' => 56,
    );

    public static $errors = array(
            'INVALID_REQUEST' => -1,
        // used as general error when we can't/want to specify more details
            'MALFORMED_LOGIN' => -2,
        // when username or password or both are missing from the POST
            'INVALID_LOGIN' => -3,
        // user + password does not match valid account
            'INVALID_LOGIN_SSL' => -4,
        // SSL protocol must be used
            'NOT_LOGGED_IN' => -5,
        // not logged in
            'USER_NO_SUCH_UUID' => -6,
            'USER_INVALID_USERNAME' => -7,
            'USER_USERNAME_EXISTS' => -8,
            'PASSWORD_TOO_SHORT' => -9,
            'MISSING_PARAMETERS' => -10,
            'UUID_ALREADY_EXIST' => -11,
            'INVALID_USERNAME' => -12,
            'INVALID_PASSWORD' => -13,
            'REQUIRES_HTTPS' => -14,
            'UUID_MISMATCH' => -15,
            'INVALID_SCREEN_NAME' => -16,
            'USER_SCREEN_NAME_EXISTS' => -17,
            'UPGRADE_NEEDED' => -18,
            'REGISTRATION_REFUSED' => -19,
            'INVALID_PARAMETER_VALUE' => -20,
        // wrong type or other violation of the business rules
            'INTERNAL_SERVER_ERROR' => -21,
    );


    public static function is_valid_ok_tag($tag) {
        return array_key_exists($tag, self::$oks);
    }


    public static function is_valid_ok_code($code) {
        $ret = false;
        if (tangra_is_int($code)) {
            $ret = in_array($code, self::$oks);
        }

        return $ret;
    }


    public static function is_valid_error_tag($tag) {
        return array_key_exists($tag, self::$errors);
    }


    public static function is_valid_error_code($code) {
        $ret = false;
        if (tangra_is_int($code)) {
            $ret = in_array($code, self::$errors);
        }

        return $ret;
    }
}