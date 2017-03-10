<?php
/**
cancel the waybill
*/
include_once "EncryptUtil.php";
include_once "HttpRequestUtil.php";

$requestTime = time(); //timestamp of request
$secretKey = ""; //secretKey from Fodel
$params = array();
$params["shop_id"] = "2";//awb number
$params["app_key"] = ""; //app key from Fodel
$params["awbs"] = "443351033,34535";
$params["ts"] = $requestTime;
//generate the sign from the paramters with eht secretKey
$signString = EncryptUtil::generateSign($params,$secretKey);
$params['sign'] = $signString;

//Set header ,add the api version to the parameter fodel_api_version
$header = array();
$header[] = "fodel_api_version: v1";
$httprequest = new HttpRequestUtil();
$httprequest->setBaseUrl("http://pos.test.fo-del.com");//host of fodel
$respone = $httprequest->post("/shipment/merchantCheckinConfirmReceived",json_encode($params),true,$header);
echo $respone->body;
