package nju.edu.cn.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nju.edu.cn.Rule.*;
import Util.FileUtil;
import nju.edu.cn.ltpTools.NLPBasicTool;
import nju.edu.cn.ltpTools.Pair;

public class DictionaryExpander {
	DependecyParseRule dprule;
	private OpinionWordDic opinWordDic;
	private DescWordDic aspectWordDic;
	private MutualInformation mutualInfo;
	private int N;
	//String fileName = "";
	public DictionaryExpander() {
		// TODO Auto-generated constructor stub
		dprule = new DependecyParseRule();
		opinWordDic = OpinionWordDic.getInstance();
		opinWordDic.loadNegWordFromFile("G:\\Master2D\\NJUAdmaster\\data\\seed_8_aspect_Neg");
		opinWordDic.loadPosWordFromFile("G:\\Master2D\\NJUAdmaster\\data\\seed_8_aspect_Pos");
		aspectWordDic = DescWordDic.getInstance();
		mutualInfo = new MutualInformation();
		N = 0;
	}
	public void mutualExpandDictionary()
	{
		List<String> sentences = FileUtil.loadSentence("G:\\Master2D\\NJUAdmaster\\data\\caokong_corpus.csv");
		for(String sentence : sentences)
		{
			List<String> words = NLPBasicTool.segment(sentence);
			Set<String> opinWords = new HashSet<String>();
			for(String word : words)
			{
				if(opinWordDic.containsWord(word))
				{
					opinWords.add(word);
				}
			}
			
			for(String opinWord : opinWords)
			{
				List<String> segmentList = getSegmentList(opinWord, sentence);
				for(String segment : segmentList)
				{
					extend(segment,opinWord);
				}
			}
		}
		getMutualInfo(sentences);
		Iterator<String> itor = aspectWordDic.dic.iterator();
		while(itor.hasNext())
		{
			String aspectWord = itor.next();
			if(aspectWord.endsWith("感")){continue;}
			//if(!mutualInfo.containsStringKey(aspectWord)){itor.remove();continue;}
			Pair<String, String> key1 = new Pair<String, String>(aspectWord, "true");
			Pair<String, String> key2 = new Pair<String, String>(aspectWord, "false");
			
			System.out.println(aspectWord);
			System.out.println(mutualInfo.getPairKeyValue(key1) + " " + mutualInfo.getPairKeyValue(key2));
			float v1 = mutualInfo.getMutualInfoValue(N, key1);
			float v2 = mutualInfo.getMutualInfoValue(N, key2);
			System.out.println(v1 + " " + v2);
			if((v1 <= v2 && mutualInfo.getPairKeyValue(key1)<5)||mutualInfo.getPairKeyValue(key1) == 1)
			{
				System.out.println(aspectWord);
				itor.remove();
			}
		}
		aspectWordDic.writeDescWordToFile("G:\\Master2D\\NJUAdmaster\\data\\aspectWordDic.txt");
	}
	public void getMutualInfo(List<String> sentences)
	{
		for(String sentence : sentences)
		{
			String[] segments = sentence.split("[,|.|，|。|\\s]");
			for(String segment : segments)
			{
				List<String> words = NLPBasicTool.segment(segment);
				//List<String> opinWordList = new ArrayList<>();
				List<String> posTags = NLPBasicTool.posTag(words);
				Pair<List<Integer>, List<String>> deResult = NLPBasicTool.dependencyParse(words, posTags);
				boolean hasOpinWord = false;
				int opinWordId = -1;
				List<Integer> aspectWordList = new ArrayList<>();
				for(int i =0;i<words.size();i++)
				{
					String word = words.get(i);
					if(aspectWordDic.containsKey(word)){aspectWordList.add(i);}
					if(opinWordDic.containsWord(word)){opinWordId = i;}
				}
				for(int wordId : aspectWordList)
				{
					String word = words.get(wordId);
					mutualInfo.addString(word);
					if(opinWordId != - 1 && (deResult.first.get(wordId) == opinWordId + 1 || deResult.first.get(opinWordId) == wordId + 1))
					{
						mutualInfo.addPair(new Pair<String, String>(word, "true"));
						mutualInfo.addString("true");
					}else
					{
						mutualInfo.addPair(new Pair<String, String>(word, "false"));
						mutualInfo.addString("false");
					}
						
				}
				
				N++;
			}
		}
	}
	private List<String> getSegmentList(String opinWord,String sentence)
	{
		List<String> segmentList = new ArrayList<>();
		int fromIndex = 0;
		while(fromIndex != - 1)
		{
			int id = sentence.indexOf(opinWord,fromIndex);
			if(id == -1){break;}
			fromIndex = id + 1;
			int left = id -1, right = id+1;
			while(left >=0)
			{
				if(sentence.charAt(left) == '，' || sentence.charAt(left) == '。' || sentence.charAt(left) == ' '
					|| sentence.charAt(left) == ',')
				{
					break;
				}
				left--;
			}
			while(right < sentence.length())
			{
				if(sentence.charAt(right) == '，' || sentence.charAt(right) == '。'|| sentence.charAt(right) == ' '
					|| sentence.charAt(right) == ',')
				{
					break;
				}
				right++;
			}
			String newSentence = sentence.substring(left+1, right);
			segmentList.add(newSentence);
		}
		return segmentList;
	} 
	private void expand(String opinWord,String sentence)
	{
		int fromIndex = 0;
		while(fromIndex != - 1)
		{
			int id = sentence.indexOf(opinWord,fromIndex);
			if(id == -1){break;}
			fromIndex = id + 1;
			int left = id -1, right = id+1;
			while(left >=0)
			{
				if(sentence.charAt(left) == '，' || sentence.charAt(left) == '。' || sentence.charAt(left) == ' '
					|| sentence.charAt(left) == ',')
				{
					break;
				}
				left--;
			}
			while(right < sentence.length())
			{
				if(sentence.charAt(right) == '，' || sentence.charAt(right) == '。'|| sentence.charAt(right) == ' '
					|| sentence.charAt(right) == ',')
				{
					break;
				}
				right++;
			}
			String newSentence = sentence.substring(left+1, right);
			//System.out.println(newSentence);
			List<String> words = NLPBasicTool.segment(newSentence);
			List<String> posTags = NLPBasicTool.posTag(words);
			Pair<List<Integer>, List<String>> deResult = NLPBasicTool.dependencyParse(words, posTags);
			for(int i = 0; i < words.size(); i++)
			{
				if(opinWord.equals(words.get(i)))
				{
					List<String> aspectWords = dprule.getAspectWord(words, posTags, deResult.first, deResult.second, i);
					for(String aspectWord : aspectWords)
					{
						if(aspectWord.length() < 2)continue;
//						if(aspectWord.equals("感觉感觉"))
//						{
//							System.out.println(opinWord);
//							System.out.println(sentence);
//							System.out.println(newSentence);
//							System.out.println(words);
//							System.out.println(deResult.first);
//							System.out.println(deResult.second);
//							return;
//						}
						aspectWordDic.add(aspectWord);
//						Pair<String, String> obj = new Pair<String, String>(aspectWord, opinWord);
//						mutualInfo.addString(obj.first);
//						mutualInfo.addString(obj.second);
//						mutualInfo.addPair(obj);
					}
					break;
				}
			}
		}
		//System.out.println(descWordDic.toString());
		
	}
	private void extend(String sentence, String opinWord)
	{
		List<String> words = NLPBasicTool.segment(sentence);
		List<String> posTags = NLPBasicTool.posTag(words);
		Pair<List<Integer>, List<String>> deResult = NLPBasicTool.dependencyParse(words, posTags);
		for(int i = 0; i < words.size(); i++)
		{
			if(opinWord.equals(words.get(i)))
			{
				List<String> aspectWords = dprule.getAspectWord(words, posTags, deResult.first, deResult.second, i);
				for(String aspectWord : aspectWords)
				{
					if(aspectWord.length() < 2)continue;
					aspectWordDic.add(aspectWord);
				}
				break;
			}
			
		}
		//System.out.println(descWordDic.toString());
	}
	
	
	//喜欢 + 奔驰
	//奔驰 + 好看
	//n + adv* + adj
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DictionaryExpander dicExpander = new DictionaryExpander();
		DependecyParseRule dprule = new DependecyParseRule();
		//dicExpander.expand("轻松", "可以做到轻松愉快的出门",dprule);
		//dicExpander.extend("底盘感觉很结实没有松散的感觉", "结实");
		dicExpander.mutualExpandDictionary();
//		Pattern p = Pattern.compile("([.|,]*?.*?中国[,|.]?)");
//		Matcher m = p.matcher("我爱中国,我爱国,我爱中国,我爱国,我爱中,");
//		while(m.find())
//		{
//			System.out.println(m.group());
//		}
	}

}
