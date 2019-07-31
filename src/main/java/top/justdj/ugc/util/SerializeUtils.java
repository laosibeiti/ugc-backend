package top.justdj.ugc.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 19.7.31
 * Time: 9:18
 *
 * @author justdj
 * @email top90982@gmail.com
 * @Desc
 */
@Slf4j
public class SerializeUtils  {
	/**
	 * 序列化对象
	 *
	 */
	public static <T> String serialize(T seria)  {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		String serStr = "";
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					byteArrayOutputStream);
			objectOutputStream.writeObject(seria);
			serStr = byteArrayOutputStream.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
			objectOutputStream.close();
			byteArrayOutputStream.close();
		}catch (Exception e){
			log.error("序列化数据发生异常");
		}
		return serStr;
	}
	
	/**
	 * 反序列化对象
	 * @param <T>
	 *
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deSerialization(String str,T seria) throws IOException,
			ClassNotFoundException {
		String redStr = java.net.URLDecoder.decode(str, "UTF-8");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				redStr.getBytes("ISO-8859-1"));
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		seria= (T) objectInputStream.readObject();
		objectInputStream.close();
		byteArrayInputStream.close();
		return seria;
	}
}
