package nju.edu.cn.prediction;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.FileUtil;
import nju.edu.cn.config.FileConfig;
import nju.edu.cn.label.AspectItem;
import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;
import nju.edu.cn.taxonomy.Taxonomy;

public class TaxonomyModel {
	final int window = 3;
	Taxonomy taxonomy;
	public Map<String, Integer> opinionDic;
	ReviewWordExtractor reviewWordExtractor;
	int opinionDicMaxLen = -1;
	public TaxonomyModel() {
		// TODO Auto-generated constructor stub
		opinionDic = new HashMap<>();
		reviewWordExtractor = new ReviewWordExtractor();
	}
	public void learnModel()
	{
		//filepath
		taxonomy = new Taxonomy();

		File[] opinionFiles =FileUtil.loadFileName(FileConfig.getOpinionWordPath());

		for(File opininFile : opinionFiles)
		{
			if(opininFile.getName().matches("neg"))
			{
				opinionDicMaxLen = FileUtil.loadWordMapAndMaxLen(opinionDic, opininFile, -1);
			}else
				opinionDicMaxLen = FileUtil.loadWordMapAndMaxLen(opinionDic,opininFile,1);
		}
		//System.out.println(opinionDic.size());
	}
	void extractReview(AspectItem aspect, int keywordLeftId, int keywordRightId, PreProcessedItem preItem)
	{
		//keyword 左右分别 匹配评价词
		//问题：分词错误
		//评价词多个term组成//如果利用词典匹配，那么最大正向匹配//如果利用规则抽取，那么需要利用句法分析抽取短语
		boolean hasReviewFlag = false;
		int rightMargin = Math.min(preItem.size - 1, keywordRightId+window);
		for(int i = keywordRightId + 1; i <= rightMargin; i++)
		{
//			String word = preItem.getWord(i);
//			if(opinionDic.containsKey(word))
//			{
//				aspect.setReview(word);
//				aspect.setReviewPolarity(opinionDic.get(word));
//				hasReviewFlag = true;
//				break;
//			}
			//System.out.println(preItem.getWord(i));
			String phrase = "";
			String opinionWord = "";
			for(int j = i; j <= rightMargin && (phrase += preItem.getWord(j)).length() <= opinionDicMaxLen;j++)
			{
				//System.out.println(phrase);
				if(opinionDic.containsKey(phrase))
				{
					opinionWord = phrase;
					hasReviewFlag = true;
				}
			}
			if(hasReviewFlag)
			{
				aspect.setReview(opinionWord);
				aspect.setReviewPolarity(opinionDic.get(opinionWord));
				break;
			}
			
		}
//		int leftMargin = Math.max(0, keywordLeftId-window);
//		if(!hasReviewFlag)
//		{
//			for(int i = leftMargin; i < keywordLeftId; i++)
//			{
//				//curword
////				String word = preItem.getWord(i);
////				if(opinionDic.containsKey(word))
////				{
////					aspect.setReview(word);
////					aspect.setReviewPolarity(opinionDic.get(word));
////					hasReviewFlag = true;
////					break;
////				}
//				String phrase = "";
//				String opinionWord = "";
//				for(int j = i; j < keywordLeftId && (phrase += preItem.getWord(j)).length() <= opinionDicMaxLen;j++)
//				{
//					//System.out.println(phrase);
//					if(opinionDic.containsKey(phrase))
//					{
//						opinionWord = phrase;
//						hasReviewFlag = true;
//					}
//				}
//				if(hasReviewFlag)
//				{
//					aspect.setReview(opinionWord);
//					aspect.setReviewPolarity(opinionDic.get(opinionWord));
//					break;
//				}
//			}
//		}
		//gold 数据 对于情感极性为NULL的数据全部将NULL变为0
		if(!hasReviewFlag)
		{
			aspect.setReviewPolarity(0);
		}
	}
	/**
	 * 
	 * @param aspect
	 * @param keywordLeftId
	 * @param keywordRightId
	 * @param item
	 */
	void extractReviewByTemplate(AspectItem aspect, int keywordLeftId, int keywordRightId, PreProcessedItem item)
	{
		String reviewWord = reviewWordExtractor.extract(item, keywordLeftId, keywordRightId);
		aspect.setReview(reviewWord);
	}
	
	public void predict(LabelItem item)
	{
		String sentence = item.sentence;
		PreProcessedItem preItem = new PreProcessedItem(sentence);
		preItem.print();
		int maxLen = taxonomy.maxLen;
		int n = preItem.words.size();
		int left = 0;
		int keywordLeftId = -1,keywordRightId = -1;
		while(left < n)
		{
			String keyWord = "";
			String word = "";
			for(int j = left; j < n && (word += preItem.words.get(j)).length() <= maxLen; j++)
			{
				if(taxonomy.containsKey(word))
				{
					keyWord = word;
					keywordLeftId = left;
					keywordRightId = j;
					left = j;
					
				}
			}
			left++;
			if(keyWord.length() > 0)
			{
				AspectItem aspect = new AspectItem();
				aspect.setAspectWord(keyWord);
				aspect.setAspectCategory(taxonomy.containsKey(keyWord)?taxonomy.getMapValue(keyWord)[0]:"NULL");
				//extractReview(aspect, keywordLeftId,keywordRightId, preItem);
				extractReviewByTemplate(aspect, keywordLeftId, keywordRightId, preItem);
				if(aspect.getReview() == null)
				{
					extractReview(aspect, keywordLeftId, keywordRightId, preItem);
				}
				item.addAspectWord(aspect);
			}
		}
	}
	public void predictWhenApsectWordIsCorrect(LabelItem item)
	{
		String sentence = item.sentence;
		PreProcessedItem preItem = new PreProcessedItem(sentence);
		//preItem.print();
		int maxLen = taxonomy.maxLen;
		int n = preItem.words.size();
		int left = 0;
		int keywordLeftId = -1,keywordRightId = -1;
		String goldAspectWord = item.aspectList.get(0).getAspectWord();
		while(left < n) 
		{
			String keyWord = "";
			String phrase = "";
			for(int j = left; j < n && (phrase += preItem.words.get(j)).length() <= maxLen; j++)
			{
				if(phrase.equals(goldAspectWord))
				{
					keyWord = phrase;
					keywordLeftId = left;
					keywordRightId = j;
					left = j;
					break;
				}
			}
			left++;
			if(keyWord.length() > 0)
			{
				AspectItem aspect = item.aspectList.get(0);
				extractReviewByTemplate(aspect, keywordLeftId, keywordRightId, preItem);
				//extract by dictionary
				if(aspect.getReview() == null)
				{
					extractReview(aspect, keywordLeftId, keywordRightId, preItem);
				}
			}
		}
	}
	public static void main(String[] args) {
		TaxonomyModel tm = new TaxonomyModel();
		tm.learnModel();
		//多个词，找到核心词啊
		LabelItem item = new LabelItem("过弯极限非常高");
		tm.predict(item);
		item.show();
	}
}
