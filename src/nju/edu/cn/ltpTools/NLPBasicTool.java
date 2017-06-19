package nju.edu.cn.ltpTools;

import java.util.ArrayList;
import java.util.List;

import edu.hit.ir.ltp4j.NER;
import edu.hit.ir.ltp4j.Parser;
import edu.hit.ir.ltp4j.Postagger;
import edu.hit.ir.ltp4j.Segmentor;

public class NLPBasicTool {
	private final static String root = "G:/MasterTwoU/28proj/data/data/ltp/";
	static
	{
		System.load(root+"dll/segmentor.dll"); 
		System.load(root+"dll/postagger.dll"); 
		System.load(root+"dll/parser.dll");  
		System.load(root+"dll/srl.dll");  
		System.load(root+"dll/ner.dll");
		if (Parser.create(root+"ltp_data/parser.model") < 0||Segmentor.create(root+"ltp_data/cws.model","data/tokens") < 0||
				Postagger.create(root+"ltp_data/pos.model") < 0
				||NER.create(root+"ltp_data/ner.model")<0) 
		{
			System.err.println("load failed");
		}
		else System.out.println("ltp tool load succeed");
	}
	public static List<String> segment(String sentence)
	{
		List<String> words = new ArrayList<String>();
		Segmentor.segment(sentence, words);
		return words;
	}
	public static List<String> posTag(List<String> words)
	{
		List<String> postags = new ArrayList<String>();
		Postagger.postag(words, postags);
		return postags;
	}
	public static Pair<List<Integer>, List<String>> dependencyParse(List<String> words,List<String> tags)
	{
		List<Integer> heads = new ArrayList<Integer>();
	    List<String> deprels = new ArrayList<String>();
	    Parser.parse(words, tags, heads, deprels);//wordlist:�ִ��б�     tagList:�����б�         heads: ������ϵid    deprels:������ϵ
		return new Pair<List<Integer>, List<String>>(heads, deprels);
	}
	public static String getHeadWord(String sentence)
	{
		List<String> words = NLPBasicTool.segment(sentence);
		List<String> tags = NLPBasicTool.posTag(words);
		List<Integer> heads = NLPBasicTool.dependencyParse(words, tags).first;
		for(int i = 0; i < heads.size();i++)
		{
			if(heads.get(i) == 0)
			{
				return words.get(i);
			}
		}
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sentence = "方向盘真心不错";
		List<String> words = NLPBasicTool.segment(sentence);
		List<String> tags = NLPBasicTool.posTag(words);
		Pair<List<Integer>, List<String>> deResult = NLPBasicTool.dependencyParse(words, tags);
		System.out.println(words);
		System.out.println(tags);
		System.out.println(deResult.getFirst());
		System.out.println(deResult.getSecond());
	}

}
