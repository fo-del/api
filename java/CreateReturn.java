
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateReturn {
	
	public static void main(String[] args) throws JSONException, IOException, ParseException, FodelAPIException{
		String secretKey = "";//secretKey from Fodel
		String requestTime = (System.currentTimeMillis()/1000)+"";
		Map<String ,String> params = new HashMap<String,String>();
		params.put("recipient_name", "Alex Meng");//name of the customer
		params.put("phone", "971522545710");//phone number of the customer
		params.put("order_no", "23324234");//order number
		params.put("address", "Dubai Opera ,Dubai");//address of customer
		params.put("app_key", "");//app key from fodel
		params.put("weight", "1200");//weight of the parcels, unit:g
		params.put("return_type", "1");
		//return type,
		//1,warranty(the customer is want to refund),
		//2,return (the customer want to change the product or the product has problem, need to send back the warehouse),
		//3,RTO (return to original)
		params.put("shop_id", "2"); //shop_id ,from the map of the
		params.put("ts", requestTime);//request time

		List<ReturnGoodList> returnGoodLists = new ArrayList<>();
		//product information
		//
		returnGoodLists.add(new ReturnGoodList(Byte.parseByte("2"),"Iphone 7","123456","12.5","12 x 12 x 34"));
		returnGoodLists.add(new ReturnGoodList(Byte.parseByte("3"),"T shirt nike","1213123","12.5","12 x 12 x 34"));
		params.put("product_info",JSONObject.toJSONString(returnGoodLists));
		String signString = EncryptUtil.generateSign(params, secretKey);
		params.put("sign", signString);
		JSONObject jsonObject = HttpRequestUtil.request("POST", "/parcel/return", params);
		System.out.println(jsonObject);
		
	}
   
}
