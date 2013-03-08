package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author YOU$$EF
 */
public class Helper {
    static public String md5(String args){
		byte[] uniqueKey = args.getBytes();
		byte[] hash      = null;
		try {
			hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
		} catch (NoSuchAlgorithmException e) {
		}
		
		StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        
		return hashString.toString();
	}
}