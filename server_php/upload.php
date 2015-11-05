<?php
/**
 * User: ogre
 * Created: 2015-11-04 14:35
 */

require_once('inc/response_ok.class.php');
require_once('inc/rerr_missing_parameters.class.php');
require_once('inc/rerr_invalid_parameter.class.php');


class Upload_Response_OK extends Response_OK {
    public static $tag = 'UPLOAD_OK';


    function __construct($size) {
        $this->set_response_code(Response_Codes::$oks[self::$tag]);
        $array = array('file_size' => $size);
        $this->set_payload($array);
    }
}


if (array_key_exists('file_upload', $_FILES)) {
    $image_file = $_FILES['file_upload']['tmp_name'];
    if (is_uploaded_file($image_file)) {
        $size = filesize($image_file);
        unlink($image_file);
        $resp = new Upload_Response_OK($size);
    } else {
        $resp = new RErr_Invalid_Parameter();
    }
} else {
    $resp = new RErr_Missing_Parameters();
}



$cont = new Forge_Response_Container($resp);

// artificial delay in order wait dialog on android to be shown for at least 2 seconds
sleep(2);

print(json_encode($cont));