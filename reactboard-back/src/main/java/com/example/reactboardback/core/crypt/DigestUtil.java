package com.example.reactboardback.core.crypt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component("DigestUtil")
public class DigestUtil {

	
	private static final Log logger = LogFactory.getLog(DigestUtil.class);
	
	private static String algorithm = "SHA-256";

    /**
     * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
     *
     * @param data 암호화될 패스워드
     * @param salt salt로 사용될 사용자 ID 지정
     * @return
     * @throws Exception
     */
    public static String digest(String data, String salt) throws Exception {

    	if (data == null) {
		    return "";
		}
	
		byte[] hashValue = null; // 해쉬값
	
		MessageDigest md = MessageDigest.getInstance(algorithm);
		
		md.reset();
		md.update(salt.getBytes());
		
		hashValue = md.digest(data.getBytes());
		if( logger.isDebugEnabled() ) {
			logger.debug("Digested DATA = [" + hashValue + "]");
		}
		return new String(Base64.encodeBase64(hashValue, false));
    }
	
    
    /**
     * 비밀번호를 암호화된 패스워드 검증(salt가 사용된 경우만 적용).
     * 
     * @param data 원 패스워드
     * @param encoded 해쉬처리된 패스워드(Base64 인코딩)
     * @return
     * @throws Exception
     */
    public static boolean matches(String data, String encoded, String salt) throws Exception {
    	byte[] hashValue = null; // 해쉬값
    	
    	MessageDigest md = MessageDigest.getInstance(algorithm);
    	
    	md.reset();
    	md.update(salt.getBytes());
    	hashValue = md.digest(data.getBytes());
    	
//		if( logger.isDebugEnabled() ) {
//			logger.debug("hashValue Byte DATA = [" + hashValue + "]" );
//			logger.debug("hashValue String DATA = [" + new String(Base64.encodeBase64(hashValue, false)) + "]" );
//			//logger.debug("encoded byte DATA = [" + Base64.decodeBase64(encoded.getBytes()) + "]");
//			//logger.debug("encoded String DATA = [" + encoded + "]");
//		}

		//new String(Base64.encodeBase64(hashValue, false));

		byte[] decode = Base64.decodeBase64(encoded);

    	return java.util.Arrays.equals(hashValue,decode);
    }
    
    public static String extractStringHashSHA256(String str){
        String SHA = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
             
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }
    
    public static String extractByteHashSHA256(byte[] str){
        String SHA = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str);
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
             
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }
/*    
    public static String extractFileHashSHA256(String filename) throws Exception {
        
        String SHA = "";
        int buff = 16384;
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "r");
 
            MessageDigest hashSum = MessageDigest.getInstance("SHA-256");
 
            byte[] buffer = new byte[buff];
            byte[] partialHash = null;
 
            long read = 0;
 
            // calculate the hash of the hole file for the test
            long offset = file.length();
            int unitsize;
            while (read < offset) {
                unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
                file.read(buffer, 0, unitsize);
 
                hashSum.update(buffer, 0, unitsize);
 
                read += unitsize;
            }
 
            file.close();
            partialHash = new byte[hashSum.getDigestLength()];
            partialHash = hashSum.digest();
             
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < partialHash.length ; i++){
                sb.append(Integer.toString((partialHash[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
         
        return SHA;
    }
*/    
    public static String encryptAES(String message) throws Exception {
//    	String key = PropertiesUtil.getProperty("xml.encryption.key");
    	String key = "abcdefghijklmnop";
    	SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(),"AES");
    	
    	Cipher cipher = Cipher.getInstance("AES");
    	cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    	byte[] encrypted = cipher.doFinal(message.getBytes());
    	
    	return byteArrayToHex(encrypted);
    }
    
    
    public static String decryptAES(String encrypted) throws Exception {
//    	String key = PropertiesUtil.getProperty("xml.encryption.key");
    	String key = "abcdefghijklmnop";
    	
    	SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(),"AES");
    	Cipher cipher = Cipher.getInstance("AES");
    	cipher.init(Cipher.DECRYPT_MODE, skeySpec);
    	
    	byte[] original = cipher.doFinal(hexToByteArray(encrypted));
    	String originalString = new String(original);
    	
    	return originalString;
    }
    
    public static String serverStartdecryptAES(String encrypted) throws Exception {
//    	String key = FileUtil.readString(fileName)
    	String key = "abcdefghijklmnop";
    	
    	SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(),"AES");
    	Cipher cipher = Cipher.getInstance("AES");
    	cipher.init(Cipher.DECRYPT_MODE, skeySpec);
    	
    	byte[] original = cipher.doFinal(hexToByteArray(encrypted));
    	String originalString = new String(original);
    	
    	return originalString;
    }    
    
    public static byte[] hexToByteArray(String hex) {
    	if(hex == null || hex.length() == 0) {
    		return null;
    	}
    	byte[] ba = new byte[hex.length() / 2];
    	for(int i = 0; i<ba.length; i++) {
    		ba[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2),16);
    	}
    	return ba;
    }
    
    public static String byteArrayToHex(byte[] ba) {
    	if(ba == null || ba.length == 0) {
    		return null;
    	}
    	
    	StringBuffer sb = new StringBuffer(ba.length * 2);
    	String hexNumber;
    	for(int i=0;i<ba.length;i++) {
    		hexNumber = "0"+Integer.toHexString(0xff & ba[i]);
    		
    		sb.append(hexNumber.substring(hexNumber.length() - 2));
    	}
    	return sb.toString();
    }
    
}

