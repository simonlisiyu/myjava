package com.lsy.java.test.utils;

import java.io.*;
import java.util.*;

/**
 * 文件操作类
 *
 */
public class FileUtils {
	/**
	 * 功　能: 创建文件夹
	 *
	 * @param path
	 *            参　数:要创建的文件夹名称
	 * @return 返回值: 如果成功true;否则false 如：FileUtils.mkdir("/usr/apps/upload/");
	 */
	public static boolean makedir(String path) {
		File file = new File(path);
		if (!file.exists())
			return file.mkdirs();
		else
			return true;
	}

	/**
	 * 保存文件
	 *
	 * @param stream
	 * @param path
	 *            存放路径
	 * @param filename
	 *            文件名
	 * @throws IOException
	 */
	public static void SaveFileFromInputStream(InputStream stream, String path, String filename)
			throws IOException {
		FileOutputStream fs = new FileOutputStream(path + "/" + filename);
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
	 * 删除某个文件
	 * @param path:文件路径,需要提供完整的路径,如:/tmp/tmp.txt
	 * @return
	 */
	public static boolean deleteFile(String path){
		File f = new File(path);
		boolean b = false;
		if(f.exists()){
			b = f.delete();
		}
		return b;
	}

	/**
	 * 删除文件夹及文件
	 * @param folderPath:文件夹路径
	 * @return
	 */
	public static void deleteFolderAndFile(File file){
		if(file.exists()){
			if(file.isFile()){
				file.delete();
			}else if(file.isDirectory()){
				File[] files=file.listFiles();
				for(int i=0;i<files.length;i++){
					deleteFolderAndFile(files[i]);
				}
				file.delete();
			}
		}
	}
	
	/**
	 * 列出某个目录下的所有文件,子目录不列出
	 * @param folderPath:文件夹路径
	 * @return
	 */
	public static List<String> listFile(String folderPath){
		List<String> fileList = new ArrayList<String>(); //FileViewer.getListFiles(destPath, null, false);
		File f = new File(folderPath);
		File[] t = f.listFiles();
		for(int i = 0; i < t.length; i++){
			fileList.add(t[i].getAbsolutePath());
		}
		return fileList;
	}


	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean exists(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取当前路径
	 * 
	 * @return
	 */
	public static String getCurrentPath() {
		File directory = new File(".");
		String nowPath = "";
		try {
			nowPath = directory.getCanonicalFile().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nowPath;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 * */
	public static String getFileExtendName(String fileName) {
		if (fileName == null) {
			return "";
		} else {
			return fileName.substring(fileName.lastIndexOf(".") + 1, fileName
					.length());
		}
	}

	/**
	 * 创建一个新文件，如果存在则报错
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static void createFile(String filePath, String fileName)
			throws RuntimeException {
		String file = null;
		if (filePath == null) {
			file = fileName;
		} else {
			file = filePath + File.separator + fileName;
		}
		createFile(file);
	}

	/**
	 * 创建一个新文件(含路径)，如果存在则报错
	 * 
	 * @param fileName
	 *            含有路径的文件名
	 * @return
	 */
	public static void createFile(String fileName) throws RuntimeException {
		File f = new File(fileName);
		if (f.exists()) {
			throw new RuntimeException("FILE_EXIST_ERROR");
		} else {
			try {
				File fileFolder = f.getParentFile();
				if (!fileFolder.exists())
					fileFolder.mkdirs();
				f.createNewFile();
			} catch (IOException ie) {
				System.out.println("文件" + fileName + "创建失败：" + ie.getMessage());
				throw new RuntimeException("FILE_CREATE_ERROR");
			}
		}
	}
 

	/**
	 * 创建目录，如果存在则不创建
	 * 
	 * @param path
	 * @return 返回结果null则创建成功，否则返回的是错误信息
	 * @return
	 */
	public static String createDir(String path, boolean isCreateSubPah) {
		String msg = null;
		File dir = new File(path);

		if (dir == null) {
			msg = "不能创建空目录";
			return msg;
		}
		if (dir.isFile()) {
			msg = "已有同名文件存在";
			return msg;
		}
		if (!dir.exists()) {
			if (isCreateSubPah && !dir.mkdirs()) {
				msg = "目录创建失败，原因不明";
			} else if (!dir.mkdir()) {
				msg = "目录创建失败，原因不明";
			}
		}
		return msg;
	}
	
	/**
	 * 删除指定目录或文件。 如果要删除是目录，同时删除子目录下所有的文件
	 * 
	 * @file:File 目录
	 * */
	public static void delFileOrFolder(String fileName) {
		if (!exists(fileName))
			return;
		File file = new File(fileName);
		delFileOrFolder(file);
	}

	/**
	 * 删除指定目录或文件。 如果要删除是目录，同时删除子目录下所有的文件
	 * 
	 * @file:File 目录
	 * */
	public static void delFileOrFolder(File file) {
		if (!file.exists())
			return;
		if (file.isFile()) {
			file.delete();
		} else {
			File[] sub = file.listFiles();
			if (sub == null || sub.length <= 0) {
				file.delete();
			} else {
				for (int i = 0; i < sub.length; i++) {
					delFileOrFolder(sub[i]);
				}
				file.delete();
			}
		}
	}
	
	/**
	 * 从Properties格式配置文件中获取所有参数并保存到HashMap中。
	 * 配置中的key值即map表中的key值，如果配置文件保存时用的中文，则返回结果也会转成中文。
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static HashMap readPropertyFile(String file,String charsetName) throws IOException {
		if (charsetName==null || charsetName.trim().length()==0){
			charsetName="gbk";
		}
		HashMap map = new HashMap();
		InputStream is =null;
		if(file.startsWith("file:"))
			is=new FileInputStream(new File(file.substring(5)));
		else
			is=FileUtils.class.getClassLoader().getResourceAsStream(file);
		Properties properties = new Properties();
		properties.load(is);
		Enumeration en = properties.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String code = new String(properties.getProperty(key).getBytes(
					"ISO-8859-1"), charsetName);
			map.put(key, code);
		}
		return map;
	}
	/**
	 *
	 * @param path
	 *            文件路径
	 * @param suffix
	 *            后缀名
	 * @param isdepth
	 *            是否遍历子目录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getListFiles(String path, String suffix, boolean isdepth) {
		File file = new File(path);
		return FileUtils.listFile(file, suffix, isdepth);
	}

	/**
	 * @param f
	 * @param suffix：后缀名
	 * @param isdepth：是否遍历子目录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List listFile(File f, String suffix, boolean isdepth) {
		// 是目录，同时需要遍历子目录
		List<String> fileList = new ArrayList<String>();
		if (f.isDirectory() && isdepth == true) {
			File[] t = f.listFiles();
			for (int i = 0; i < t.length; i++) {
				listFile(t[i], suffix, isdepth);
			}
		} else {
			String filePath = f.getAbsolutePath();

			if (suffix != null) {
				int begIndex = filePath.lastIndexOf(".");// 最后一个.(即后缀名前面的.)的索引
				String tempsuffix = "";

				if (begIndex != -1)// 防止是文件但却没有后缀名结束的文件
				{
					tempsuffix = filePath.substring(begIndex + 1, filePath
							.length());
				}

				if (tempsuffix.equals(suffix)) {
					fileList.add(filePath);
				}
			} else {
				// 后缀名为null则为所有文件
				fileList.add(filePath);
			}

		}

		return fileList;
	}

	/**
	 * 方法追加文件：使用FileWriter
	 *
	 * @param fileName
	 * @param content
	 */
	public static void appendMethod(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content + "\r\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}










	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 */
	public static void readFileByBytes(String fileName) {
		File file = new File(fileName);
		InputStream in = null;
		try {
			System.out.println("以字节为单位读取文件内容，一次读一个字节：");
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				System.out.write(tempbyte);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			System.out.println("以字节为单位读取文件内容，一次读多个字节：");
			// 一次读多个字节
			byte[] tempbytes = new byte[100];
			int byteread = 0;
			in = new FileInputStream(fileName);
			FileUtils.showAvailableBytes(in);
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				System.out.write(tempbytes, 0, byteread);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 */
	public static void readFileByChars(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			System.out.println("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r') {
					System.out.print((char) tempchar);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("以字符为单位读取文件内容，一次读多个字节：");
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉\r不显示
				if ((charread == tempchars.length)
						&& (tempchars[tempchars.length - 1] != '\r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
						}
					}
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String tempString = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));

			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return tempString;
	}

	/**
	 * 随机读取文件内容
	 */
	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			System.out.println("随机读取一段文件内容：");
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置,position
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
//            beginIndex = 0;
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 */
	private static void showAvailableBytes(InputStream in) {
		try {
			System.out.println("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String fileName = "C:/abc.txt";
//        ReadFromFile.readFileByBytes(fileName);
//        ReadFromFile.readFileByChars(fileName);
//        ReadFromFile.readFileByLines(fileName);
		FileUtils.readFileByRandomAccess(fileName);
	}


	public static Set<String> readVmidFileByLines(String fileName) {
		Set<String> uuid = new HashSet<>();
		File file = new File(fileName);
		BufferedReader reader = null;
		String tempString = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				uuid.add(tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return uuid;
	}

}
