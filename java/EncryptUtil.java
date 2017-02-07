
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class EncryptUtil {
	/**
	 * generate the sign from the parameters
	 * @param params   the parameters of the request.
	 * @param secretKey   secretKey from Fodel
	 * @return
	 */
	public static String generateSign(Map<String, String> params,String secretKey){
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
	    Set<Entry<String, String>> entrys = sortedParams.entrySet();
	    StringBuilder basestring = new StringBuilder();
	    basestring.append(secretKey);
	    for (Entry<String, String> param : entrys) {
	        basestring.append(param.getKey()).append("=").append(param.getValue());
	    }
		String newSignature = md5(basestring.toString()).toUpperCase();
		return newSignature;
	}
	
	/**
	 * md5 encrypt
	 * 
	 * @author Alex Meng
	 * @param str
	 * @param method
	 * @return
	 */
	public static String md5(String str) {
		MessageDigest md = null;
		String dstr = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			dstr = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {

		}
		return dstr;
	}
}
