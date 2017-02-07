
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

	   private static String apiHostString = "http://api.test.fo-del.com";//"http://devomigo.kingsouq.com";//"http://api.fo-del.com";
	   private static String apiVersionString = "v1.0.0";
	   
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
	   public static JSONObject request(String method, String url, Map<String, String> postForm)
	           throws IOException,ParseException,JSONException, FodelAPIException{
	       BufferedReader rd;
	       StringBuilder sb;
	       OutputStreamWriter wr;
	       HttpURLConnection connection;
	       
	       URL serverAddress= new URL(new URL(apiHostString), url);
	       connection= (HttpURLConnection)serverAddress.openConnection();
	       connection.setRequestMethod(method);
	       connection.setReadTimeout(10000);
	       connection.setRequestProperty("fodel_api_version", apiVersionString); //fodel api version
	       
	       if(postForm!=null){
	    	   connection.setDoOutput(true);
	       }

	       connection.connect();
	       StringBuffer params = new StringBuffer();
	       if(postForm!=null){
	    	   for(String key : postForm.keySet()){
	    		   params.append(key).append("=").append(postForm.get(key)).append("&");
	    	   }
	           wr = new OutputStreamWriter(connection.getOutputStream());
	           wr.write(params.toString());
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
