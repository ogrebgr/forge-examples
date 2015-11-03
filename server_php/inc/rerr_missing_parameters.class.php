<?php
/**
 * User: ogre
 * Created: 2015-11-03 15:24
 */


require_once('response_error.class.php');

class RErr_Missing_Parameters extends Response_Error {
    const TAG = 'MISSING_PARAMETERS';

    function __construct($msg = self::TAG) {
        $this->set_response_code(Response_Codes::$errors[self::TAG]);
        $this->set_payload($msg);
    }


}