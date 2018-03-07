package com.baidu.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.misc.BASE64Encoder;

public class BASE64Util {
	
	/**
	 * 生成图片的Base64编码
	 */
	public static String fromImage2Base64(File image) throws IOException{
		InputStream in = null;
		String imgStr;
		try {
			in = new FileInputStream(image);
			byte[] b = new byte[in.available()];
			in.read(b);
			imgStr = new BASE64Encoder().encode(b);
		} finally {
			in.close();
		}
		return imgStr;
	}

}
