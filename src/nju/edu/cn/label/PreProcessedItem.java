package nju.edu.cn.label;

import java.util.ArrayList;
import java.util.List;

import nju.edu.cn.ltpTools.NLPBasicTool;
import nju.edu.cn.ltpTools.Pair;

public class PreProcessedItem {
	public String sentence;
	public List<String> words;
	public List<String> posTags;
	public List<Integer> dpids;
	public List<String> dprels;
	public int size = - 1;
	public PreProcessedItem(String sentence) {
		// TODO Auto-generated constructor stub
		this.sentence = sentence;
		if(sentence == null)
		{
			this.sentence = "";
			size = 0;
			words = new ArrayList<String>();
			posTags = new ArrayList<>();
		}else
		{
			words = NLPBasicTool.segment(sentence);
			posTags = NLPBasicTool.posTag(words);
			Pair<List<Integer>, List<String>> pairtemp = NLPBasicTool.dependencyParse(words, posTags);
			dpids = pairtemp.first;
			dprels = pairtemp.second;
			size = words.size();
		}
		
	} 
	public String getWord(int index)
	{
		return words.get(index);
	}
	public String getPosTag(int index)
	{
		return posTags.get(index);
	}
	public int getDpparentID(int childindex)
	{
		return dpids.get(childindex) - 1;
	}
	public String getDpParentRel(int childIndex)
	{
		return dprels.get(childIndex);
	}
	public void print()
	{
		System.out.println(words);
		System.out.println(posTags);
		System.out.println(dpids);
		System.out.println(dprels);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
