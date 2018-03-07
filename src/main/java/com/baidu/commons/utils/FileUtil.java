package com.baidu.commons.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {
	private static final String MODE = "rw";

	/**
	 * 文件拷贝
	 * @param src 源文件
	 * @param dest 目标目录
	 * @throws IOException
	 */
	public static void copyFile(File src, File dest) throws IOException {
		if (src == null || !src.exists() || !src.canRead()) {
			throw new IllegalArgumentException(src + ",not exists or can not read");
		}
		if (dest == null) {
			throw new IllegalArgumentException(dest + "is null");
		}
		// 如果目录不存在
		if (!dest.exists()) {
			dest.mkdirs();
		}
		// 创建目标文件
		String srcFileName = src.getPath();
		srcFileName = srcFileName.substring(srcFileName.lastIndexOf("\\") + 1);
		srcFileName = "copyof_" + srcFileName;
		dest = new File(dest.getPath() + File.separator + srcFileName);
		dest.createNewFile();

		// 复制文件
		RandomAccessFile srcFile = null;
		RandomAccessFile destFile = null;
		FileChannel srcChannel = null;
		FileChannel destChannel = null;
		try {
			srcFile = new RandomAccessFile(src, MODE);
			destFile = new RandomAccessFile(dest, MODE);
			srcChannel = srcFile.getChannel();
			destChannel = destFile.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
			srcChannel.read(byteBuffer);
			while (srcChannel.read(byteBuffer) != -1) {
				// 切换为读模式
				byteBuffer.flip();
				destChannel.write(byteBuffer);
				byteBuffer.clear();
			}
		} finally {
			if (srcFile != null) {
				srcFile.close();
			}
			if (destFile != null) {
				destFile.close();
			}
			if (srcChannel != null) {
				srcChannel.close();
			}
		}
	}
	public static void main(String[] args) throws IOException {
		copyFile(new File("E:\\pic\\002.jpg"), new File("E:\\pic\\bbb\\"));
	}

}
