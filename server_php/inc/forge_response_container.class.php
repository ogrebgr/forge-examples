<?php

require_once('forge_response.class.php');

class Forge_Response_Container {
	public $code;
	public $payload;
	
	function  __construct(Forge_Response $resp) {
		$this->code = $resp->get_response_code();
		$this->payload = $resp->get_payload();
	}
}
