<?php

require_once('forge_response.class.php');
require_once('response_codes.class.php');



abstract class Response_OK extends Forge_Response {
	protected function set_response_code($response_code) {
		tangra_if_not_int_throw_e($response_code);
		if (Responses_Codes::is_valid_ok_code($response_code)) {
			parent::set_response_code($response_code);
		} else {
			throw new Exception('Invalid error code: '.$response_code);
		}
	}
}
