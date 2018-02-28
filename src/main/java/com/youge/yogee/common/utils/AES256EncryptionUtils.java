package com.youge.yogee.common.utils;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;


/**
 * 根据公司的网站修改成追加的包??
 *
 * 参考http://my.oschina.net/nicsun/blog/95632
 *
 * @author yxtwang
 *
 */
public class AES256EncryptionUtils {

	/**
	 * 密钥算法
	 * java6支持56位密钥，bouncycastle支持64位
	 * */
	public static final String KEY_ALGORITHM="AES";

	/**
	 * 加密/解密算法/工作模式/填充方式
	 *
	 * JAVA6 支持PKCS5PADDING填充方式
	 * Bouncy castle支持PKCS7Padding填充方式
	 * */
	public static final String CIPHER_ALGORITHM="AES/ECB/PKCS7Padding";

	/**
	 *
	 * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
	 * @return byte[] 二进制密钥
	 * */
	public static byte[] initkey() throws Exception{

//	        //实例化密钥生成器
//	    	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//	        KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
//	        //初始化密钥生成器，AES要求密钥长度为128位、192位、256位
////	        kg.init(256);
//	        kg.init(128);
//	        //生成密钥
//	        SecretKey secretKey=kg.generateKey();
//	        //获取二进制密钥编码形式
//	        return secretKey.getEncoded();
		//为了便于测试，这里我把key写死了，如果大家需要自动生成，可用上面注释掉的代码
		return new byte[] { 0x08, 0x08, 0x04, 0x0b, 0x02, 0x0f, 0x0b, 0x0c,
				0x01, 0x03, 0x09, 0x07, 0x0c, 0x04, 0x07, 0x0a, 0x04, 0x0f,
				0x06, 0x0f, 0x0e, 0x09, 0x05, 0x01, 0x0a, 0x0a, 0x06, 0x09,
				0x06, 0x08, 0x09, 0x0d };
	}

	/**
	 *
	 * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
	 * @return byte[] 二进制密钥
	 * */
	public static byte[] initkeyM() throws Exception{

//	        //实例化密钥生成器
//	    	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//	        KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
//	        //初始化密钥生成器，AES要求密钥长度为128位、192位、256位
////	        kg.init(256);
//	        kg.init(128);
//	        //生成密钥
//	        SecretKey secretKey=kg.generateKey();
//	        //获取二进制密钥编码形式
//	        return secretKey.getEncoded();
		//为了便于测试，这里我把key写死了，如果大家需要自动生成，可用上面注释掉的代码
		return new byte[] { 0x01, 0x01, 0x04, 0x0b, 0x02, 0x0f, 0x0b, 0x0c,
				0x08, 0x03, 0x09, 0x07, 0x0c, 0x04, 0x07, 0x0a, 0x04, 0x0f,
				0x06, 0x0f, 0x0e, 0x09, 0x05, 0x08, 0x0a, 0x0a, 0x06, 0x09,
				0x06, 0x01, 0x09, 0x0d };
	}

	/**
	 * 转换密钥
	 * @param key 二进制密钥
	 * @return Key 密钥
	 * */
	public static Key toKey(byte[] key) throws Exception{
		//实例化DES密钥
		//生成密钥
		SecretKey secretKey=new SecretKeySpec(key,KEY_ALGORITHM);
		return secretKey;
	}

	/**
	 * 加密数据
	 * @param data 待加密数据
//	 * @param key 密钥
	 * @return byte[] 加密后的数据
	 * */
	public static byte[] encrypt(byte[] data) throws Exception{
		//还原密钥
		byte[] key = AES256EncryptionUtils.initkey();

		Key k=toKey(key);
		/**
		 * 实例化
		 * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		//初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		//执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 加密数据
	 * @param data 待加密数据
	//	 * @param key 密钥
	 * @return byte[] 加密后的数据
	 * */
	public static byte[] encryptStr(String data) throws Exception{
		//还原密钥
		byte[] key = AES256EncryptionUtils.initkey();

		Key k=toKey(key);
		/**
		 * 实例化
		 * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		//初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		//执行操作
		return cipher.doFinal(data.getBytes("utf-8"));
	}
	/**
	 * 解密数据
	 * @param data 待解密数据
//	 * @param key 密钥
	 * @return byte[] 解密后的数据
	 * */
	public static String decrypt(String data){
		//欢迎密钥

		try{
			byte[] key = AES256EncryptionUtils.initkey();

			Key k =toKey(key);
			/**
			 * 实例化
			 * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
			 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
			 */

			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM, "BC");

			//初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, k);
			//执行操作
			byte [] data1 = cipher.doFinal(parseHexStr2Byte(data));
			return new String(data1,"utf-8");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	/**
	 * 解密数据
	 * @param data 待解密数据
	//	 * @param key 密钥
	 * @return byte[] 解密后的数据
	 * */
	public static String decryptM(String data){
		//欢迎密钥

		try{
			byte[] key = AES256EncryptionUtils.initkeyM();

			Key k =toKey(key);
			/**
			 * 实例化
			 * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
			 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
			 */

			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM, "BC");

			//初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, k);
			//执行操作
			byte [] data1 = cipher.doFinal(parseHexStr2Byte(data));
			return new String(data1,"utf-8");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}



	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static void main(String[] args) throws UnsupportedEncodingException{


		String str="AES";
		System.out.println("原文："+str);
		try{
//			byte[] data=AES256EncryptionUtils.encrypt(str.getBytes());
//			System.out.println(parseByte2HexStr(data));

//			byte [] dd = parseHexStr2Byte("u/eurSaElUujuhd/ouVT2oKx2hKK6UMzcDSXI76w5owtn7HVyH427zy7HTee8g/lwFGkjAVlvx58zgWDSAFaSbYbxjiZccjzVtT1ma69cPzHvGmjoXtHkMZUgkRgS/k0PPTt0o7dvCUgsDL9FmIm0KkuTxx4kIjHeEonxK4yA75UMK1nO33i5dYCz+C4uN4tovoKOadqOE1DItRkP0Kll29fl5lxLNl5ayEcuk32bkQwIfdWp/RWCXM2dfV4J15jkGcEJdXSzuXgaPgJOrvoGPxemq7CuCTdrGB7fLrDXaFwNKfEhZ8er2Y4ba+OPl7S9INxrRMXIYeG3hPU58A+0Au1706yjijzj9kTDLdKRuwufMIlvl+IiEZkJ2xqwnrQh/UmYmcZjvCXE6baPbtHiA61Ri7K0xO4ztX2Bn8cdDmplj5RIG956oo0piysykFgL7e1kbQz88bCX6XV568j3VXFRps5RGHGFVZBn0VYfA/SXJDIKyaozJWz1SW46Cmc3TjrvvUFm71ebv147acsEE/ZLi2co+AV3dJYu2xdh8nds9WAVeMS5THs5uFAVROxLRknlOnVesEpVWKeMNFreuxzvB/4j9iIAx3ncwaN0adMVlIhSlxfvaJ2uhcHf2LkywfTx0/xAOjGWN8KlG/E3jSsffHcmx1Vw5qjn8B/YiKd8b3KImvHMVYocp7G/Q9Is69yjUBcIvyzyyeO1Vfr3fGGkaCMED3+MLosTYfjRJvTTrtcxBXLsDYsARKgDlsyw8ucexIMksKJpFiERse4Pm2+R8CKj9j2it3Iwp77IjOPvwB62H4NzE1C4erqdUJdGcaaMYEwlT2U/YsYKsEQeD4NTW9YLTG1RYsMDZ3v0OTdCWYPBhgQ2HBbId7G4PoQcDfy3AmoYzVdyM7EXH+9pSWwwP9EFjK4vgp8SUg2gZHDv2D4KIqRwM1viV7EXm8Ez/NEHUAreojmRcXGZ2NO68ChK0BBpvBeIR0KzUcN6Sk88NobP0+QhMS44DQCxWpyXnNziWQiKGz8MrFBZvL0O3aY4HgUr8TKYVeBvmq0V5bbdM3Kn8AlIdgBOTRUyuOpw6zeinuK3urh13Iq7hm/kskO0B35fSAEIfhb4t+MRv4VOrZeS5tC7gr5aqrXg7frkanHu/alSKEHoQANvNslmskd3VLHxGMoOfkJv+FUZHBCeXPvOB06n4+YyV7m1tLjP/4hBJXK3uUuNUwtTHfpPUxIPZ3c74cb+BQs2vqWZ0+sB2dTizxGnzRM1bjJt1Yt");
//			parseByte2HexStr(data);
			String secript = "da141e5bed6556875fc25dac19bf5e6286e7964527e748de1ea08fc4fe0a4819855ed38dfac07494589dee72704f4a13b984049975244d5d8228a8a1a793aed5fb2efdf62f0281bb4eeb18e4843b909f83a8bcfe3c2eb25bbf8d47e4a5ed98fb";
			String aadd = AES256EncryptionUtils.decrypt(secript);
			System.out.println(aadd);

//			String aa ="u/eurSaElUujuhd/ouVT2oKx2hKK6UMzcDSXI76w5owtn7HVyH427zy7HTee8g/lwFGkjAVlvx58zgWDSAFaSbYbxjiZccjzVtT1ma69cPzHvGmjoXtHkMZUgkRgS/k0PPTt0o7dvCUgsDL9FmIm0KkuTxx4kIjHeEonxK4yA75UMK1nO33i5dYCz+C4uN4tovoKOadqOE1DItRkP0Kll29fl5lxLNl5ayEcuk32bkQwIfdWp/RWCXM2dfV4J15jkGcEJdXSzuXgaPgJOrvoGPxemq7CuCTdrGB7fLrDXaFwNKfEhZ8er2Y4ba+OPl7S9INxrRMXIYeG3hPU58A+0Au1706yjijzj9kTDLdKRuwufMIlvl+IiEZkJ2xqwnrQh/UmYmcZjvCXE6baPbtHiA61Ri7K0xO4ztX2Bn8cdDmplj5RIG956oo0piysykFgL7e1kbQz88bCX6XV568j3VXFRps5RGHGFVZBn0VYfA/SXJDIKyaozJWz1SW46Cmc3TjrvvUFm71ebv147acsEE/ZLi2co+AV3dJYu2xdh8nds9WAVeMS5THs5uFAVROxLRknlOnVesEpVWKeMNFreuxzvB/4j9iIAx3ncwaN0adMVlIhSlxfvaJ2uhcHf2LkywfTx0/xAOjGWN8KlG/E3jSsffHcmx1Vw5qjn8B/YiKd8b3KImvHMVYocp7G/Q9Is69yjUBcIvyzyyeO1Vfr3fGGkaCMED3+MLosTYfjRJvTTrtcxBXLsDYsARKgDlsyw8ucexIMksKJpFiERse4Pm2+R8CKj9j2it3Iwp77IjOPvwB62H4NzE1C4erqdUJdGcaaMYEwlT2U/YsYKsEQeD4NTW9YLTG1RYsMDZ3v0OTdCWYPBhgQ2HBbId7G4PoQcDfy3AmoYzVdyM7EXH+9pSWwwP9EFjK4vgp8SUg2gZHDv2D4KIqRwM1viV7EXm8Ez/NEHUAreojmRcXGZ2NO68ChK0BBpvBeIR0KzUcN6Sk88NobP0+QhMS44DQCxWpyXnNziWQiKGz8MrFBZvL0O3aY4HgUr8TKYVeBvmq0V5bbdM3Kn8AlIdgBOTRUyuOpw6zeinuK3urh13Iq7hm/kskO0B35fSAEIfhb4t+MRv4VOrZeS5tC7gr5aqrXg7frkanHu/alSKEHoQANvNslmskd3VLHxGMoOfkJv+FUZHBCeXPvOB06n4+YyV7m1tLjP/4hBJXK3uUuNUwtTHfpPUxIPZ3c74cb+BQs2vqWZ0+sB2dTizxGnzRM1bjJt1Yt";
//			byte [] bb = Encodes.decodeBase64(aa);
//			String cc= parseByte2HexStr(bb);
//			System.out.println("cccc---------"+cc);
//			String aaddc = AES256EncryptionUtils.decrypt(cc);
//			System.out.println(aaddc);

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}

	/**将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length()/2];
		for (int i = 0;i< hexStr.length()/2; i++) {
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}