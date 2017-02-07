<?php
include_once "EncryptUtil.php";
include_once "HttpRequestUtil.php";

$requestTime = time(); //timestamp of request
$secretKey = ""; //secretKey from Fodel
$params = array();
$params["awb"] = "";//awb nubmer
$params["info"] = ""; //tracking information
$params["status_code"] = "";//tracking information code
$params["app_key"] = ; //
$params["ts"] = $requestTime;
//generate the sign from the paramters with eht secretKey
$signString = EncryptUtil::generateSign($params,$secretKey);
$params['sign'] = $signString;

//Set header ,add the api version to the parameter fodel_api_version
$header = array();
$header[] = "fodel_api_version: v1";
$httprequest = new HttpRequestUtil();
$httprequest->setBaseUrl("http://");
$respone = $httprequest->post("/parcel/updateTracking",$params,true,$header);
echo $respone->body;