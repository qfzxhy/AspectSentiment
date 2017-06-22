package nju.edu.cn.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
	Map<Character, Trie> nodes;
	public Trie() {
		// TODO Auto-generated constructor stub
		nodes = new HashMap<Character, Trie>();
	}
	public  Trie createTrie(List<String> words)
	{
		Trie trie = new Trie();
		for(String word : words)
		{
			if(word.trim().length() == 0)continue;
			char[] characters = word.toCharArray();
			helper_create(characters,trie,0);
		}
		return trie;
	}
	private  void helper_create(char[] characters,Trie trie,int i) {
		// TODO Auto-generated method stub
		if(i == characters.length)
		{
			if(!trie.nodes.containsKey('#'))
				trie.nodes.put('#',null);
			return;
		}
		char c = characters[i];
		if(trie.nodes.containsKey(c))
		{
			helper_create(characters, trie.nodes.get(c), i+1);
		}else
		{
			Trie tempTrie = new Trie();
			helper_create(characters, tempTrie, i+1);
			trie.nodes.put(c, tempTrie);
		}
	}
	public boolean search(String word,Trie trie)
	{
		if(word == null || word.trim().length() == 0){return false;}
		return helper_search(word.toCharArray(),trie,0);
		
	}
	
	private boolean helper_search(char[] charArray, Trie trie, int i) {
		// TODO Auto-generated method stub
		if(trie == null || trie.nodes == null)return false;
		if(i == charArray.length && trie.nodes.containsKey('#'))
		{
			return true;
		}
		if(trie.nodes.containsKey(charArray[i]))
		{
			return helper_search(charArray, trie.nodes.get(charArray[i]), i+1);
		}else
		{
			return false;
		}
	}
	public String searchInSentence(String sentence,Trie trie)
	{
		char[] charArray = sentence.toCharArray();
		for(int i =0;i<charArray.length;i++)
		{
			int j = helper_searchInSentence(charArray,i,trie);
			if(j == -1){continue;}
			else
			{
				return sentence.substring(i,j);
			}
		}
		return null;
		
	}
	private int helper_searchInSentence(char[] charArray, int i, Trie trie) {
		// TODO Auto-generated method stub
		if(trie.nodes.containsKey('#'))
		{
			return i;
		}
		if(i >= charArray.length){return -1;}
		char c = charArray[i];
		if(trie.nodes.containsKey(c))
		{
			return helper_searchInSentence(charArray, i+1, trie.nodes.get(c));
		}else
		{
			return -1;
		}
	}
	public static void main(String[] args) {
		List<String> words = new ArrayList<String>();;
		words.add("中国人");
		words.add("美国人");
		words.add("南京大学");
		words.add("北京大学");
		// TODO Auto-generated method stub
		Trie trie = new Trie();
		trie = trie.createTrie(words);
		System.out.println(trie.search("清华大学", trie));
		System.out.println(trie.search("#", trie));
	}

}
