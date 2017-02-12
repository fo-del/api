import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


public class CheckinFinalized {
	public static void main(String[] args) throws JSONException, IOException, ParseException, FodelAPIException{
		String secretKey = "";//secretKey from Fodel
		String requestTime = (System.currentTimeMillis()/1000)+""; //request time
		Map<String ,String> params = new HashMap<String,String>();
		params.put("app_key", "");//app key from fodel
		params.put("awb", "757873898");//awb number
		params.put("checkin_type", "2");//1: ecommerce, 2: collection point
		params.put("ts", requestTime);
		String signString = EncryptUtil.generateSign(params, secretKey);
		params.put("sign", signString);
		JSONObject jsonObject = HttpRequestUtil.request("POST", "http://api.test.fo-del.com/shipment/checkinfinalized", JSONObject.toJSONString(params));
		System.out.println(jsonObject);
	}
}
