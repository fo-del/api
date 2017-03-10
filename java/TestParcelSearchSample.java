
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import util.EncryptUtil;
import util.FodelAPIException;
import util.HttpRequestUtil;
public class TestParcelSearchSample {
	public static void main(String[] args) throws JSONException, IOException, ParseException, FodelAPIException{
		String secretKey = "";//secretKey from Fodel
        String appKey = "";//appKey from Fodel
		String requestTime = (System.currentTimeMillis()/1000)+"";
		Map<String ,String> params = new HashMap<String,String>();
		params.put("app_key", appKey);
		params.put("shop_id", "1087");
		params.put("awb", "443351033");
		params.put("validate_code", "");
		params.put("ts", requestTime);
		String signString = EncryptUtil.generateSign(params, secretKey);
		params.put("sign", signString);
		JSONObject jsonObject = HttpRequestUtil.request("GET", "/parcel/search", params);
		System.out.println(jsonObject);
	}
}
