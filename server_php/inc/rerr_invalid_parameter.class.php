<?php
/**
 * User: ogre
 * Created: 2015-11-03 15:31
 */

require_once('response_error.class.php');

class RErr_Invalid_Parameter extends Response_Error {
    const TAG = 'INVALID_PARAMETER_VALUE';

    function __construct($msg = self::TAG) {
        $this->set_response_code(Response_Codes::$errors[self::TAG]);
        $this->set_payload($msg);
    }


}