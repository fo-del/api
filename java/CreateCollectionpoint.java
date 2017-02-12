import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateCollectionpoint {
	public static void main(String[] args) throws JSONException, IOException, ParseException, FodelAPIException{
		
		String secretKey = "";//secretKey from Fodel
		String requestTime = (System.currentTimeMillis()/1000)+"";
		Map<String ,String> params = new HashMap<String,String>();
		params.put("recipient_name", "EDer Uber");//full name of customer
		params.put("phone", "971522545710");//phone of the customer
		params.put("weight", "2000");//weight of the parcel,unit: g
		params.put("is_cod", "1");//is the cod or not : 1 yes,0:no
		params.put("price_cod", "1000");//amount of the cod,if it is not cod ,put 0,
		params.put("order_no", "2399453401123");//order number
		params.put("app_key", ""); //app key from fodel
		params.put("shop_id", "36"); //shop_id ,from the map of the
		params.put("ts", requestTime);
		List<ReturnGoodList> returnGoodLists = new ArrayList<>();
		returnGoodLists.add(new ReturnGoodList(Byte.parseByte("2"),"Iphone 7","123456","12.5","12 x 12 x 34"));
		params.put("product_info",JSONObject.toJSONString(returnGoodLists));
		String signString = EncryptUtil.generateSign(params, secretKey);
		params.put("sign", signString);
		String requestBody = JSONObject.toJSONString(params);
		JSONObject jsonObject = HttpRequestUtil.request("POST", "http://api.test.fo-del.com/shipment/create", requestBody);
		System.out.println(jsonObject);	
	}
}
