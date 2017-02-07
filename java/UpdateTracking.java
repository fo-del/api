
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class UpdateTracking {
	public static void main(String[] args) throws JSONException, IOException, ParseException, FodelAPIException{
		String secretKey = "";//secretKey from Fodel
		String requestTime = (System.currentTimeMillis()/1000)+"";
		Map<String ,String> params = new HashMap<String,String>();
		params.put("app_key", "");//app key
		params.put("awb", "675332741");//awb number
		params.put("info", "new parcels");//the tracking information
		params.put("status_code", "1");//the status code
		params.put("ts", requestTime);
		String signString = EncryptUtil.generateSign(params, secretKey);
		params.put("sign", signString);
		JSONObject jsonObject = HttpRequestUtil.request("POST", "/parcel/updateTracking", params);
		System.out.println(jsonObject);
	}
}
