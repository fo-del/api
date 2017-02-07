<?php
/**
 * sample of create an collection point waybill,
 *
 */
include_once "../EncryptUtil.php";
include_once "../HttpRequestUtil.php";

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
$params["recipient_name"] = "Fiona Williams";//full name of customer
$params["phone"] = "0508873269";//phone of the customer
$params["address"] = "Dubai Opera,Dubai,United Arab Emirates";//address of the customer
$params["weight"] = 420;//weight of the parcel,unit: g
$params["is_cod"] = 1;//is the cod or not : 1 yes,0:no
$params["price_cod"] = 1000;//amount of the cod,if it is not cod ,put 0,
$params["order_no"] = "";//order number
$params["app_key"] = "";//app key of the fodel
$params["shop_id"] = 200001;//shop id of the customer choose
$params["ts"] = $requestTime;
$params["product_info"] = json_encode($productInfo); //product information , just put the json format
//generate the sign from the paramters with eht secretKey
$signString = EncryptUtil::generateSign($params,$secretKey);
$params['sign'] = $signString;

//Set header ,add the api version to the parameter fodel_api_version
$header = array();
$header[]="fodel_api_version: v1";
$httprequest = new HttpRequestUtil();
$httprequest->setBaseUrl("http://api.test.fo-del.com");
$respone = $httprequest->post("/parcel/create",$params,true,$header);
echo $respone->body;



