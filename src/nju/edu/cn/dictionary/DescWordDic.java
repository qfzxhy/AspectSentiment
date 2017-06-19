package nju.edu.cn.dictionary;

import java.util.HashSet;
import java.util.Set;

import Util.FileUtil;

public class DescWordDic {
	public  Set<String> dic = new HashSet();;
	private int wordMaxLen = 0;
	//private int wordLessMaxLen = 0;
	//����ģʽ
	private DescWordDic() {
		// TODO Auto-generated constructor stub
	}
	private static DescWordDic sigleInstance = new DescWordDic();
	public static DescWordDic getInstance()
	{
		return sigleInstance;
	}
	public void add(String word)
	{
		dic.add(word);
		wordMaxLen = Math.max(wordMaxLen, word.length());
//		if(word.length() >= wordMaxLen)
//		{
//			wordMaxLen = word.length();
//			wordLessMaxLen = wordMaxLen;
//		}
	}
	public void remove(String word)
	{
		dic.remove(word);
	}
	public boolean containsKey(String word)
	{
		return dic.contains(word);
	}
	public int getMaxLen()
	{
		return wordMaxLen;
	}
	public void writeDescWordToFile(String file)
	{
		FileUtil.writeToFile(file, dic);
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(String word : dic)
		{
			sb.append(word+"|");
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
