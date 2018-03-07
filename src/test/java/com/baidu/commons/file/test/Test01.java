package com.baidu.commons.file.test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.baidu.commons.utils.BASE64Util;

import sun.misc.BASE64Encoder;


public class Test01 {
	
	/**
	 * 生成图片的Base64编码
	 * @throws Exception
	 */
	@Test
	public void test01() throws Exception{
		String imgStr = BASE64Util.fromImage2Base64(new File("E:\\004.png"));
		System.out.println(imgStr);
	}

}
