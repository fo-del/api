
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import util.EncryptUtil;
import util.FodelAPIException;
import util.HttpRequestUtil;
public class MerchantReceivedFromCustomer {
	public static void main(String[] args) throws JSONException, IOException, ParseException, FodelAPIException{
		String secretKey = "";//secretKey from Fodel
        String appKey = "";//appKey from Fodel
		String requestTime = (System.currentTimeMillis()/1000)+"";
		Map<String ,String> params = new HashMap<String,String>();
		params.put("app_key", appKey);
		params.put("shop_id", "2");
		params.put("ts", requestTime);
		params.put("validate_code", "370011");
		params.put("awb", "2480073675");
		params.put("amount", "39");
		String signString = EncryptUtil.generateSign(params, secretKey);
		params.put("sign", signString);
	    String paramJson = JSONObject.toJSONString(params);
		JSONObject jsonObject = HttpRequestUtil.request("POST", "/shipment/merchantReceivedFromCustomer", paramJson);
		System.out.println(jsonObject);
	}
}
