package com.moraydata.general.management.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

	public static final String DEFAULT_CODING = "utf-8";

	public static String decrypt(String encrypted, String seed) throws Exception {
		byte[] keyb = seed.getBytes(DEFAULT_CODING);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(keyb);
		SecretKeySpec skey = new SecretKeySpec(thedigest, "AES");
		Cipher dcipher = Cipher.getInstance("AES");
		dcipher.init(Cipher.DECRYPT_MODE, skey);

		byte[] clearbyte = dcipher.doFinal(toByte(encrypted));
		return new String(clearbyte);
	}

	public static String encrypt(String content, String key) throws Exception {
		byte[] input = content.getBytes(DEFAULT_CODING);

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(key.getBytes(DEFAULT_CODING));
		SecretKeySpec skc = new SecretKeySpec(thedigest, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skc);

		byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);

		return parseByte2HexStr(cipherText);
	}

	private static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		}
		return result;
	}

	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex);
		}
		return sb.toString();
	}
	
//	public static void main(String[] args) throws Exception { 
//		System.out.println(encrypt("12", "skipLogin")); 
//		System.out.println(decrypt("91590a7c2f5dd3ada443a47d5e1ea2db", "skipLogin")); 
//	}
}
