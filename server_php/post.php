<?php
/**
 * User: ogre
 * Created: 2015-11-04 10:20
 */

require_once('inc/response_ok.class.php');
require_once('inc/rerr_missing_parameters.class.php');
require_once('inc/rerr_invalid_parameter.class.php');


class Post_Response_OK extends Response_OK {
    public static $tag = 'POST_OK';


    function __construct($val) {
        $this->set_response_code(Response_Codes::$oks[self::$tag]);
        $array = array('param' => $val);
        $this->set_payload($array);
    }
}


$param = $_POST['param'];
if ($param) {
    if (is_int_real($param)) {
        $resp = new Post_Response_OK($param);
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