<?php
/**
 * User: ogre
 * Created: 2015-11-01 13:10
 */


require_once('inc/response_ok.class.php');

class Get_Simple_Response_OK extends Response_OK {
    public static $tag = 'SIMPLE_GET_OK';


    function __construct() {
        $this->set_response_code(Response_Codes::$oks[self::$tag]);
        $array = array('var1' => 111, 'var2' => "var2value");
        $this->set_payload($array);
    }
}


$resp = new Get_Simple_Response_OK();

$cont = new Forge_Response_Container($resp);

// artificial delay in order wait dialog on android to be shown for at least 2 seconds
sleep(2);

print(json_encode($cont));