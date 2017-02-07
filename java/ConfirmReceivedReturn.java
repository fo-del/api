
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ConfirmReceivedReturn {
	public static void main(String[] args) throws JSONException, IOException, ParseException, FodelAPIException{
		String secretKey = "";//secretKey from Fodel
		String requestTime = (System.currentTimeMillis()/1000)+"";
		Map<String ,String> params = new HashMap<String,String>();
		params.put("app_key", ""); //app key from fodel
		params.put("awb", "");//awb number
		params.put("ts", requestTime);
		String signString = EncryptUtil.generateSign(params, secretKey);
		params.put("sign", signString);
		JSONObject jsonObject = HttpRequestUtil.request("POST", "/parcel/confirmReceivedReturn", params);
		System.out.println(jsonObject);
	}
}
