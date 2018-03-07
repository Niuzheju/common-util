package com.baidu.commons.mytest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class MyTest {

	private static final String RW = "rw";

	/**
	 * mark与reset
	 */
	@Test
	public void test01() {
		CharBuffer charBuffer = CharBuffer.allocate(8);
		charBuffer.put('1');
		charBuffer.put('2');
		// 标记当前位置position
		charBuffer.mark();
		charBuffer.put('3');
		// 把position变为标记的位置
		charBuffer.reset();
		charBuffer.put('4');

	}

	/**
	 * equals
	 */
	@Test
	public void test02() {
		CharBuffer charBuffer1 = CharBuffer.allocate(8);
		charBuffer1.put('1');
		charBuffer1.put('3');
		CharBuffer charBuffer2 = CharBuffer.allocate(8);
		charBuffer2.put('1');
		charBuffer2.put('2');
		System.out.println(charBuffer1.equals(charBuffer2));

	}

	/**
	 * compareTo 第一个不相等的元素小于另一个Buffer中对应的元素 。
	 * 所有元素都相等，但第一个Buffer比另一个先耗尽(第一个Buffer的元素个数比另一个少)。 小于,等于,大于分别返回-整数,0,正整数
	 */
	@Test
	public void test03() {
		CharBuffer charBuffer1 = CharBuffer.allocate(8);
		charBuffer1.put('1');
		charBuffer1.put('b');
		charBuffer1.flip();
		CharBuffer charBuffer2 = CharBuffer.allocate(8);
		charBuffer2.put('1');
		charBuffer2.put('c');
		charBuffer2.flip();
		System.out.println(charBuffer1.compareTo(charBuffer2));

	}

	/**
	 * scatter 分散, 一个Channel读到多个Buffer中 gather 聚集, 多个Buffer写入到一个Channel中
	 * @throws IOException
	 */
	@Test
	public void test04() throws IOException {
		// 分散,读到多个Buffer
		ByteBuffer[] buffers = { ByteBuffer.allocate(16), ByteBuffer.allocate(16) };
		RandomAccessFile file = new RandomAccessFile("E:\\test_file\\001.txt", RW);
		FileChannel channel = file.getChannel();
		channel.read(buffers);
		file.close();
		ByteBuffer buffer = buffers[0];
		buffer.flip();
		while (buffer.hasRemaining()) {
			System.out.print((char) buffer.get());
		}
	}

	/**
	 * gather, 聚集,写到一个Channel
	 * @throws IOException
	 */
	@Test
	public void test06() throws IOException {
		ByteBuffer b1 = ByteBuffer.allocate(16);
		b1.put((byte) 97);
		b1.put((byte) 98);
		b1.put((byte) 99);
		b1.put((byte) 100);
		ByteBuffer b2 = ByteBuffer.allocate(16);
		b2.put((byte) 101);
		b2.put((byte) 102);
		b2.put((byte) 103);
		b2.put((byte) 104);
		ByteBuffer[] buffers = {b1, b2};
		RandomAccessFile file1 = new RandomAccessFile("E:\\test_file\\001.txt", RW);
		FileChannel channel1 = file1.getChannel();
		b1.flip();
		b2.flip();
		channel1.write(buffers);
		file1.close();
	}
	
	/**
	 * 通道之间的数据传输(从其他通道传输)
	 * @throws IOException 
	 */
	@Test
	public void test07() throws IOException{
		RandomAccessFile srcFile = new RandomAccessFile(new File("E:\\test_file\\001.txt"), RW);
		RandomAccessFile destFile = new RandomAccessFile(new File("E:\\test_file\\002.txt"), RW);
		FileChannel srcChannel = srcFile.getChannel();
		FileChannel destChannel = destFile.getChannel();
		long i = destChannel.transferFrom(srcChannel, 0, srcChannel.size());
		System.out.println(i);
		srcFile.close();
		destFile.close();
		srcChannel.close();
		destChannel.close();
		
	}
	
	/**
	 * 通道之间的数据传输(从其他通道传输)
	 * @throws IOException 
	 */
	@Test
	public void test08() throws IOException{
		RandomAccessFile srcFile = new RandomAccessFile(new File("E:\\test_file\\001.txt"), RW);
		RandomAccessFile destFile = new RandomAccessFile(new File("E:\\test_file\\002.txt"), RW);
		FileChannel srcChannel = srcFile.getChannel();
		FileChannel destChannel = destFile.getChannel();
		long i = srcChannel.transferTo(0, srcChannel.size(), destChannel);
		System.out.println(i);
		srcFile.close();
		destFile.close();
		srcChannel.close();
		destChannel.close();
		
	}
	

}
