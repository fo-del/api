<?php
include_once "EncryptUtil.php";
include_once "HttpRequestUtil.php";

$requestTime = time(); //timestamp of request
$secretKey = ""; //secretKey from Fodel
$params = array();
$params["app_key"] = "";//app key from fodel
$params["ts"] = $requestTime;
//select the tracking info by awb,
$params["awbs"] = "2480040736,2480039663";
//generate the sign from the paramters with eht secretKey
$signString = EncryptUtil::generateSign($params,$secretKey);
$params['sign'] = $signString;

//Set header ,add the api version to the parameter fodel_api_version
$header = array();
$header[] = "fodel_api_version: v1";
$httprequest = new HttpRequestUtil();
$httprequest->setBaseUrl("http://api.test.fo-del.com");
$respone = $httprequest->get("/parcel/pullTracking",$params,$header,false);
echo $respone->body;
