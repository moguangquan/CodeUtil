package mo.pss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * 用于对备份文件的打包和压缩
 * @author Kevin
 */
public class TarAndGzipUtil {
	private volatile static TarAndGzipUtil uniqueInstance = null;
	private TarAndGzipUtil(){}
	
	public static TarAndGzipUtil getInstance() {
		if(uniqueInstance==null) {
			synchronized (TarAndGzipUtil.class) {
				if(uniqueInstance==null){
					uniqueInstance = new TarAndGzipUtil();	
				}
			}
		}
		return uniqueInstance;
	}
	
	public void tarFile(String path) {
		//自动将路径中的最后一个路径作为文件名
		String tarFile = path+".tar";
		tarFile(path,tarFile);
	}
	/**
	 * 打包成tar文件
	 * @param path 需要打包的文件夹或文件的路径
	 * @param tarFile 指定生成备份tar文件的路径
	 */
	public void tarFile(String path,String tarFile) {
		TarArchiveOutputStream taos = null;
		try {
			File f = new File(path);
			int len = f.getParent().length();
			taos = new TarArchiveOutputStream(new FileOutputStream(tarFile));
			taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
			tarFile(new File(path),taos,len);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(taos!=null) taos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		gzipFile(new File(tarFile));
	}
	/**
	 * 使用递归完成备份文件的打包
	 * @param file 需要备份的文件
	 * @param taos tar格式文件的输出流
	 * @param len  通过此来截取文件名称，有用？
	 */
	private void tarFile(File file, TarArchiveOutputStream taos,int len) {
		if(file.isDirectory()) {
			File[] fs = file.listFiles();
			for(File f:fs) {
				tarFile(f,taos,len);
			}
		} else {
			FileInputStream fis = null;
			try {
//				System.out.println(file.getAbsolutePath().substring(len)+File.separator+file.getName());
				TarArchiveEntry tae = new TarArchiveEntry(file.getParent().substring(len)+File.separator+file.getName());
				tae.setSize(file.length());
				fis = new FileInputStream(file);
				taos.putArchiveEntry(tae);
				IOUtils.copy(fis, taos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(fis!=null) fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					taos.closeArchiveEntry();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 压缩tar文件成gz文件
	 * @param file 要压缩的文件tar文件
	 */
	public void gzipFile(File file) {
		GZIPOutputStream gos = null;
		FileInputStream fis = null;
		try {
			gos = new GZIPOutputStream(new FileOutputStream(file.getAbsolutePath()+".gz"));
			fis = new FileInputStream(file);
			IOUtils.copy(fis, gos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(gos!=null) gos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(fis!=null) fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			file.delete();//必须在输入流关闭之后，才能删除执行
		}
	}
	/**
	 * 解压gz文件
	 * @param file 返回gz文件
	 * @return
	 */
	public File unGzipFile(File file) {
		GZIPInputStream gis = null;
		FileOutputStream fos = null;
		try {
			gis = new GZIPInputStream(new FileInputStream(file));
			String path = file.getAbsolutePath();
			path = path.substring(0,path.lastIndexOf("."));
			//要返回的文件
			File f = new File(path);
			fos = new FileOutputStream(f);
			IOUtils.copy(gis, fos);
			return f;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(gis!=null) gis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(fos!=null) fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	/**
	 * 解压tar文件
	 * @param file 需要解压的tar文件路径
	 * @param path 存放解压tar文件后的文件目录
	 */
	public void unTarFile(File file,String path) {
		TarArchiveInputStream tais = null;
		File tf = null;
		try {
			tf = unGzipFile(file);
			tais = new TarArchiveInputStream(new FileInputStream(tf));
			TarArchiveEntry tae = null;
			while((tae=tais.getNextTarEntry())!=null) {
				if(!tae.isDirectory()) {
					String name = path+File.separator+tae.getName();//d:/test/stu/.classpath
					FileOutputStream fos = null;
					File ff = new File(name);
					if(!ff.getParentFile().exists()) ff.getParentFile().mkdirs();
					try {
						fos = new FileOutputStream(ff);
						IOUtils.copy(tais, fos);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if(fos!=null) fos.close();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(tais!=null) tais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(tf!=null) { //必须在流关闭之后，才能执行删除
				tf.delete();
				tf.deleteOnExit();
			}
		}
	}
	
}
