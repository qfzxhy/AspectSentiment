package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileUtil {
	public  File[] loadFileName(String dir)
	{
		List<String> fileNameList = new ArrayList<>();
		File dirFile = new File(dir);
		if(dirFile.isDirectory())
		{
			File[] files = dirFile.listFiles();
			return files;
		}
		return null;
	}
	public  List<String> loadSentence(String file)
	{
		List<String> sentences = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String sentence = null;
			while((sentence = br.readLine())!=null)
			{
				sentences.add(sentence);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sentences;
	}
	public  Set<String> loadWordSet(String file)
	{
		Set<String> set = new HashSet<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String word = null;
			while((word = br.readLine())!=null)
			{
				set.add(word);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
	}
	public  Map<String, Boolean> loadWordMap(String file)
	{
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String word = null;
			while((word = br.readLine())!=null)
			{
				map.put(word, true);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	public  void loadWordMap(Map<String, Integer> map,File file,int label)
	{
		;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String word = null;
			while((word = br.readLine())!=null)
			{
				map.put(word, label);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 
	 * @param map
	 * @param file
	 * @param label
	 */
	public  int loadWordMapAndMaxLen(Map<String, Integer> map,File file,int label)
	{
		int len = -1;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String word = null;
			while((word = br.readLine())!=null)
			{
				map.put(word, label);
				len = Math.max(len, word.length());
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return len;

	}
	public  void loadWord(String file,Set<String> set)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String word = null;
			while((word = br.readLine())!=null)
			{
				set.add(word);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void loadWord(String file,List<String> list)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String word = null;
			while((word = br.readLine())!=null)
			{
				list.add(word);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void loadWord_CSV(String fileCSV,List<String> list)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileCSV));
			String line = null;
			while((line = br.readLine())!=null)
			{
				String[] words = line.split(",");
				for(String word : words)
				{
					list.add(word);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void writeToFile(String file,Set<String> wordSet)
	{
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(String word : wordSet)
			{
				bw.write(word+"\n");
			}
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void writeListToFile(String file,List<String> list)
	{
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(String s : list)
			{
				bw.write(s+"\n");
			}
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
