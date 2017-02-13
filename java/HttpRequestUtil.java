package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * Http request
 * @author Alex Meng
 *
 */
public class HttpRequestUtil {

	   private static String apiVersionString = "v1.0.0";
	   
	   public static JSONObject request(String method, String url, Map<String, String> postForm) throws JSONException, IOException, ParseException, FodelAPIException{
		   return request(method, url, postForm, null);
	   }
	   public static JSONObject request(String method, String url, String requestBody) throws JSONException, IOException, ParseException, FodelAPIException{
		   return request(method, url, null, requestBody);
	   }
	   /**
	    * make a http request
	    * @param method 
	    * @param url
	    * @param postForm
	    * @return
	    * @throws IOException
	    * @throws ParseException
	    * @throws JSONException
	    * @throws FodelAPIException
	    */
	   public static JSONObject request(String method, String url, Map<String, String> postForm,String requestBody)
	           throws IOException,ParseException,JSONException, FodelAPIException{
	       BufferedReader rd;
	       StringBuilder sb;
	       OutputStreamWriter wr;
	       HttpURLConnection connection;
	       
	       URL serverAddress= new URL(url);
	       connection= (HttpURLConnection)serverAddress.openConnection();
	       connection.setRequestMethod(method);
	       connection.setReadTimeout(10000);
	       connection.setRequestProperty("fodel_api_version", apiVersionString); //fodel api version
	       connection.setConnectTimeout(120000);
	       connection.setDoOutput(true);

	       connection.connect();
	       StringBuffer params = new StringBuffer();
	       wr = new OutputStreamWriter(connection.getOutputStream());
	       if(postForm!=null){
	    	   for(String key : postForm.keySet()){
	    		   params.append(key).append("=").append(postForm.get(key)).append("&");
	    	   }
	           
	           wr.write(params.toString());
	           wr.flush();
	       }else{
	           wr.write(requestBody);
	           wr.flush();
	       }

	       //if error from the server , throw FodelAPIException
	       if (connection.getResponseCode()>201){
	           BufferedReader errorReader;
	           StringBuilder errorSb;
	           errorReader  = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
	           errorSb = new StringBuilder();
	           String line;
	           while ((line = errorReader.readLine()) != null)
	           {
	        	   errorSb.append(line + '\n');
	           }
	           throw new FodelAPIException(errorSb.toString());
	       }
	       
	       //read the response
	       rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	       sb = new StringBuilder();
	       String line;
	       while ((line = rd.readLine()) != null)
	       {
	           sb.append(line + '\n');
	       }
		   JSONObject response;
	       response  = JSONObject.parseObject(sb.toString());
	       return response;
	   }
}
