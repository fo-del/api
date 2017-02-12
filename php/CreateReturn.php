<?php

/**
 * create an return waybill
 */
include_once "EncryptUtil.php";
include_once "HttpRequestUtil.php";

$productInfo = [
    [   "name"=>"Iphone 7 golden color",//name of the product
        "serial_number"=>"123456",//serial number of the product,
        "qty"=>1, //quantity of the product
        "price"=>2599, //price of the product
        "volume"=>"23 x 12 x 12" //shop id of the customer choose,Length x Width x Height
    ],
    [
        "name"=>"T shirt nike",//name of the product
        "serial_number"=>"123243",//serial number of the product,
        "qty"=>1, //quantity of the product
        "price"=>23,//price of the product
        "volume"=>"23 x 12 x 12" //shop id of the customer choose,Length x Width x Height
    ]
];

$requestTime = time(); //timestamp of request
$secretKey = ""; //secretKey from Fodel
$params = array();
$params["recipient_name"] = "Gloria Davies";//full name of customer
$params["phone"] = "0528144434";//phone of the customer
$params["address"] = "Dubai Opera ,Dubai,United Arab Emirates";//address of the customer
$params["app_key"] = "";//app key of the fodel
//return type :
//1,warranty(the customer is want to refund),
//2,return (the customer want to change the product or the product has problem, need to send back the warehouse),
//3,RTO (return to original)
$params["return_type"] = 1;
$params["weight"] = 1200;
$params["order_no"] = 232342233;//order number of the customer order
$params["shop_id"] = 36;//shop id of the customer choose
$params["product_info"] = json_encode($productInfo); //product information , just put the json format
$params["ts"] = $requestTime;
//generate the sign from the paramters with eht secretKey
$signString = EncryptUtil::generateSign($params,$secretKey);
$params['sign'] = $signString;

//Set header ,add the api version to the parameter fodel_api_version
$header = array();
$header[] = "fodel_api_version: v1";
$httprequest = new HttpRequestUtil();
$httprequest->setBaseUrl("http://api.test.fo-del.com");
$respone = $httprequest->post("/shipment/return",json_encode($params),true,$header);
echo $respone->body;



