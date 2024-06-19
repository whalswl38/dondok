package com.trip.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jsoup.helper.HttpConnection;

public class ReviewUtil {

	
	public static String randomName(String ext, String name) {
		
		int number = (int)(Math.random() * (1000000 + 1) +1);
		
		return name + "_" + number + "." + ext;
	}
	
	public static boolean renameDir(String serverPath, String reviewPath,String u_id, String reName) {
		String oldPath = serverPath + "images/" + reviewPath + "/tmp_" + u_id;
		String newPath = serverPath + "images/" + reviewPath + "/" + reName;
		System.out.println("[oldPath] "+oldPath);
		System.out.println("[newPath] "+newPath);
		File oldDir = new File(oldPath);
		File newDir = new File(newPath);
		if(oldDir.exists())
			return oldDir.renameTo(newDir);
		else
			return false;
	}
	
	public static String searchDir(String serverPath, String reviewPath, String nowDir) {
		
		String path = serverPath + "images/" + reviewPath + "/" + nowDir;
		
		File dir = new File(path);
		
		String cr_path = null;
		if(dir.exists()) {
			for(String out : dir.list()) {
				String url =  "images/" + reviewPath +"/"+ nowDir + "/" + out;
				if(cr_path == null) {
					cr_path = url;
				} else {
					cr_path += "|" + url;
				}
			}
		}
		return cr_path;
		
	}
	
	public String tmpImagesUpload(HttpServletRequest request, String serverPath, String reviewPath, String u_id) {
		
		String path = serverPath + "images/" + reviewPath + "/tmp_" + u_id;
		
		System.out.println("[path] " + path);
		
		File dir = new File(path);
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(dir);
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		String fileName = null;
		
		if (ServletFileUpload.isMultipartContent(request)) {
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);

				System.out.println(items);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem fileItem = iter.next();

				System.out.println(fileItem.getName());
				
				int pos = fileItem.getName().lastIndexOf(".");
				
				String ext = fileItem.getName().substring(pos + 1);
				
				String name = fileItem.getName().substring(0,pos);
				
				System.out.println("[pos] " + pos);
				System.out.println("[ext] " + ext);
				System.out.println("[name] " + name);
				
				fileName = randomName(ext, name);
				
				System.out.println("[fileName] " + fileName);
				
				try {
					File file = new File(path + "/" + fileName);
					
					if(!file.exists()) {
						fileItem.write(file);
					} else {
						tmpImagesUpload(request, serverPath, reviewPath, u_id);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("실패");
		}
		
		String url =  "images/" + reviewPath +"/tmp_"+ u_id + "/" + fileName;
		
		System.out.println("[URL] " + url);
		
		return url;
	}
	
	public static boolean imagesDelete(String serverPath, String url) {
		String path = serverPath + "/" + url;
		
		System.out.println(path);
		
		File file = new File(path);
		
		if(file.exists()) {
			return file.delete();
		} else {
			return true;
		}
	}
	
	public static boolean tmpImagesDelete(String serverPath, String reviewPath, String u_id, String url) {

		int slide = url.lastIndexOf("/");
		String fileName = url.substring(slide + 1);
		
		System.out.println("[fileName] " + fileName);
		
		String path = serverPath + "images/" + reviewPath + "/tmp_" + u_id + "/" + fileName;
		
		File file = new File(path);
		
		if(file.exists()) {
			return file.delete();
		} else {
			return true;
		}
		
	}
	
	public static boolean tmpDirDelete(String serverPath, String reviewPath, String u_id) {
		
		String path = serverPath + "images/" + reviewPath + "/tmp_" + u_id;
		File file = new File(path);
		System.out.println(path);
		File[] files = file.listFiles();
		if(file.exists()) {
			for(File outFile : files) {
				outFile.delete();
			}
			return file.delete();
		} else {
			return true;
		}
		
	}
	
	public static String tmpImgSave(String serverPath, String targetURL, String user) throws IOException {
		
		URL url = new URL(targetURL);
		URLConnection con =  url.openConnection();
		
		con.setReadTimeout(3000);
		con.setConnectTimeout(3000);
		
		if(con.getContentType() == null) {
			return "false";
		}
		
		String ext = con.getContentType().split("/")[1];
		
		int number = (int)(Math.random() * (100000000 + 1) +1);
		
		String tmpDirPath = serverPath + "images/tmpSearchImg";
		
		File tmpDir = new File(tmpDirPath);
		
		if(!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		
		String path = serverPath + "images/tmpSearchImg/tmp_" + ((user != null)? user : " ") + "_" + number + "." + ext;
		
		File filePath = new File(path);
		
		BufferedImage bI = ImageIO.read(url);
		
		ImageIO.write(bI, ext, filePath);
		
		
		String urlPath = "images/tmpSearchImg/tmp_" + ((user != null)? user : " ") + "_" + number + "." + ext;
		
		System.out.println("[urlPath] : " + urlPath);
		
		return urlPath;
	}
	
	public static boolean tmpImgDirDelete(String serverPath) {
		
		String tmpDirPath = serverPath + "images/tmpSearchImg";
		
		File tmpDir = new File(tmpDirPath);
		
		File[] files = tmpDir.listFiles();
		
		if(tmpDir.exists()) {
			for(File outFile : files) {
				outFile.delete();
			}
			return tmpDir.delete();
		} else {
			return true;
		}
	}
	
}
