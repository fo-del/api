<?php
include_once "EncryptUtil.php";
include_once "HttpRequestUtil.php";

$requestTime = time(); //timestamp of request
$secretKey = "fbc0ba8250b813275c767390a4bc7d3b"; //secretKey from Fodel
$params = array();
$params["app_key"] = "2003";//app key from fodel
$params["ts"] = $requestTime;
//select the tracking info by awb,
$params["awbs"] = "728509258,534053190";
//generate the sign from the paramters with eht secretKey
$signString = EncryptUtil::generateSign($params,$secretKey);
$params['sign'] = $signString;

//Set header ,add the api version to the parameter fodel_api_version
$header = array();
$header[] = "fodel_api_version: v1";
$httprequest = new HttpRequestUtil();
$httprequest->setBaseUrl("http://api.test.fo-del.com");
$respone = $httprequest->post("/shipment/track",json_encode($params),$header,false);
echo $respone->body;
