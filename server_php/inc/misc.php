<?php
/**
 * User: ogre
 * Created: 2015-10-28 5:56 PM
 */

function if_not_int_throw_e($var) {
    if (!is_int($var)) {
        throw new Exception('Variable must be integer. Current value: '.$var);
    }
}


function is_int_real($var) {
    $ret = false;

    if (is_numeric($var)) {
        $var = (string) $var;
        $var_int = (string) ((int) $var);
        if ($var_int == $var) {
            $ret = true;
        }
    }

    return $ret;
}