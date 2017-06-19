package nju.edu.cn.config;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
/**
 * 外部文件配置文件信息获取
 * @author qianf
 *
 */
public class FileConfig {
	/**
	 * 配置文件路径
	 */
	public static String fileConfigPath = "src/fileconfig.properties";
	private static Properties props = new Properties();
	private static String root = "";
	static
	{
		try {
			props.load(new FileInputStream(new File(fileConfigPath)));
			root = props.getProperty("root").trim();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getAspectDicPath()
	{
		return root + props.getProperty("aspectdicPath").trim();
	}
	public static String getOpinionWordPath()
	{
		return root + props.getProperty("opiniondicPath").trim();
	}
	public static void main(String[] args) {
		System.out.println(FileConfig.getAspectDicPath());
	}
	

}

