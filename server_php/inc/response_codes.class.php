<?php

require_once('misc.php');

/**
 * Class Responses_Codes
 *
 * Defines core response codes for the Forge framework.
 * Forge reserves two intervals: 1 to 100 which are used for OKs and -1 to -100 which are reserved for Errors
 */
class Response_Codes {
    public static $oks = array(
        'OK' => 1, // used as general code that indicates success
        'REGISTER_AUTO_OK' => 2,
        'REGISTER_OK' => 3,
        'LOGIN_OK' => 4,
        'SCREEN_NAME_OK' => 5,
        'LOGOUT_OK' => 6,
        'GCM_TOKEN_OK' => 50,

        'SIMPLE_GET_OK' => 100,
        'GET_PARAMS_OK' => 101,
    );

    public static $errors = array(
        /**
         * General codes
         */
        'ERROR' => -1, // used as general error when we can't/don't want to specify more details
        'MISSING_PARAMETERS' => -2, // missing required parameters
        'REQUIRES_HTTPS' => -3, // HTTPS protocol must be used
        'INVALID_PARAMETER_VALUE' => -4, // parameter value is invalid. For example: string is passed where int is expected
        'INTERNAL_SERVER_ERROR' => -5, // some serious problem occurred on the server
        'UPGRADE_NEEDED' => -6, // client version is too old and unsupported

        /**
         * Registration related codes
         */
        'REGISTRATION_REFUSED' => -7,
        'USERNAME_EXISTS' => -8,
        'PASSWORD_TOO_SHORT' => -9,
        'UUID_ALREADY_EXIST' => -10,
        'INVALID_USERNAME' => -11,
        'INVALID_PASSWORD' => -12,
        'UUID_MISMATCH' => -13,


        /**
         * Login related codes
         */
        'MALFORMED_LOGIN' => -14, // when username or password or both are missing from the POST
        'INVALID_LOGIN' => -15, // user + password does not match valid account
        'INVALID_LOGIN_SSL' => -16,
        'NOT_LOGGED_IN' => -17, // not logged in


        /**
         * Misc codes
         */
        'INVALID_SCREEN_NAME' => -50,
        'SCREEN_NAME_EXISTS' => -51,
    );


    public static function is_valid_ok_tag($tag) {
        return array_key_exists($tag, self::$oks);
    }


    public static function is_valid_ok_code($code) {
        $ret = false;
        if (is_int_real($code)) {
            $ret = in_array($code, self::$oks);
        }

        return $ret;
    }


    public static function is_valid_error_tag($tag) {
        return array_key_exists($tag, self::$errors);
    }


    public static function is_valid_error_code($code) {
        $ret = false;
        if (is_int_real($code)) {
            $ret = in_array($code, self::$errors);
        }

        return $ret;
    }
}