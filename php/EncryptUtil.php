<?php

/**
 * User: Alex Meng
 */
class EncryptUtil
{

    /**
     *  @description generate the sign of the parameters of request with the secretKey
     *  @param $params  request parameters of request
     *  @param $secretKey  secretKey from Fodel
     *
     */
    static function generateSign($params,$secretKey){
        $paraString = $secretKey;
        if (is_array($params)) {
            ksort($params);
            foreach ($params as $k => $v) {
                $paraString .= $k."=".$v;
            }
        }
        return strtoupper(md5($paraString));
    }
}