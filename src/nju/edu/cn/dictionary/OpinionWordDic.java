package nju.edu.cn.dictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Util.FileUtil;

public class OpinionWordDic implements Iterable<String>{
	private static OpinionWordDic instance = new OpinionWordDic();
	private OpinionWordDic() {
		// TODO Auto-generated constructor stub
	}
	public static OpinionWordDic getInstance()
	{
		return instance;
	}
	public Set<String> dicPos = new HashSet<String>();
	public Set<String> dicNeg = new HashSet<String>();
	private int maxLenPos = 0;
	private int maxLenNeg = 0;
	public void addPosWord(String word)
	{
		dicPos.add(word);
		maxLenPos = Math.max(maxLenPos, word.length());
	}
	public void addNegWord(String word)
	{
		dicNeg.add(word);
		maxLenNeg = Math.max(maxLenNeg, word.length());
	}
	public boolean containPosWord(String word)
	{
		return dicPos.contains(word);
	}
	public boolean containNegWord(String word)
	{
		return dicNeg.contains(word);
	}
	public void writePosWordToFile(String file)
	{
		FileUtil.writeToFile(file, dicPos);
	}
	public void writeNegWordToFile(String file)
	{
		FileUtil.writeToFile(file, dicNeg);
	}
	public void loadNegWordFromFile(String file)
	{
		FileUtil.loadWord(file, dicNeg);
	}
	public void loadPosWordFromFile(String file)
	{
		FileUtil.loadWord(file, dicPos);
	}
	public boolean containsWord(String word)
	{
		return dicPos.contains(word) || dicNeg.contains(word);
	}
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<String>() {
			private int index = 0;

			public boolean hasNext() {
				// TODO Auto-generated method stub
				return index < dicPos.size();
			}

			public String next() {
				// TODO Auto-generated method stub
				return (new ArrayList<String>(dicPos)).get(index++);
			}
			
		};
	}
	public static void main(String[] args) {
		OpinionWordDic t = new OpinionWordDic();
		t.addPosWord("qf");
		t.addPosWord("gex");
		t.addPosWord("gj");
		t.addPosWord("..");
		Iterator<String> itor = t.iterator();
		while(itor.hasNext())
		{
			System.out.println(itor.next());
		}
	}

}
