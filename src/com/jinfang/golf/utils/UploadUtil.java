package com.jinfang.golf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;


public class UploadUtil {
	/**
	 * 保存文件
	 * 
	 * @param filedata
	 * @param path
	 * @param fileName
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static void saveFile(MultipartFile filedata, String path, String fileName) throws IOException {
		InputStream stream = filedata.getInputStream();
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream fs = new FileOutputStream(path + "/" + fileName);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}
	
	/**
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath){
		File file = new File(filePath);
	    // 路径为文件且不为空则进行删除
	    if (file.isFile() && file.exists()) {
	        file.delete();
	        return true;
	    }
	    return false;
	}

	

	/**
	 * 验证上传的文件是否为空
	 * 
	 * @param filedata
	 * @return
	 */
	public static boolean validateFileEmpty(MultipartFile filedata) {
		boolean tag = false;
		if (null == filedata || filedata.getSize() <= 0) {
			tag = true;
		}
		return tag;
	}

	/**
	 * 验证上传的文件大小
	 * 
	 * @param filedata
	 * @param maxSize
	 * @return
	 */
	public static boolean validateFileSize(MultipartFile filedata, long maxSize) {
		boolean tag = false;
		if (filedata.getSize() > maxSize) {
			tag = true;
		}
		return tag;
	}
}
