<?php
/**
 * Created by: ogre
 * Date: 2014-04-05
 * Time: 16:03
 */


require_once('response_codes.class.php');


abstract class Forge_Response extends Tangra_Class {
    private $response_code;
    private $payload = '';


    protected function set_response_code($response_code) {
        tangra_if_not_int_throw_e($response_code);
        $this->response_code = $response_code;
    }


    public function get_response_code() {
        return $this->response_code;
    }


    public function set_payload($payload, $no_encode = false) {
        if ($no_encode) {
            $this->payload = $payload;
        } else {
            $this->payload = json_encode($payload);
        }
    }


    public function get_payload() {
        return $this->payload;
    }
}