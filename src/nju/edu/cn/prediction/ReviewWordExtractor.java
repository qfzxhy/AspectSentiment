package nju.edu.cn.prediction;

import java.util.List;

import nju.edu.cn.label.PreProcessedItem;
import nju.edu.cn.ltpTools.NLPBasicTool;
import Template.TemplateFactory;
import Template.TemplateInterface;
import Template.Template_POS_DP_3;

public class ReviewWordExtractor {
	TemplateInterface template;
	public String extract(PreProcessedItem item, int leftid, int rightid)
	{
		int n = item.size;
		if(leftid > rightid || leftid < 0 || rightid >= n)
		{
			System.err.println("sonething error in ... aspectword margin");
			return null;
		}
		//leftid == rightid
		if(leftid == rightid)
		{
			String reviewWord = null;
			reviewWord = extractByPosTemplate(item, leftid, 2);
			if(reviewWord != null){return reviewWord;}
			reviewWord = extractByDepTemplate(item, leftid);
			return reviewWord;
		}
		//leftid < rightid
		//1 find the head word for dependecy template
		//2 can not find head word , choose the rightest word
		//for example: 前脸 设计
		//method1: dependency parse on phrase, may have problem that segment word can not be consistent
		//method2: use the previous dependency result, it is difficult for find the head word
		String reviewWord = null;
		int headwordid = rightid;
		String phrase = "";
		for(int i = leftid;i<=rightid;i++)
		{
			phrase += item.getWord(i);
		}
		String phraseHeadWord = NLPBasicTool.getHeadWord(phrase);
		for(int i = leftid;i<=rightid;i++)
		{
			if(item.getWord(i).equals(phraseHeadWord))
			{
				headwordid = i;
				break;
			}
		}
		reviewWord = extractByPosTemplate(item, rightid, 1);
		if(reviewWord != null){return reviewWord;}
		reviewWord = extractByPosTemplate(item, leftid, 0);
		if(reviewWord != null){return reviewWord;}
		reviewWord = extractByDepTemplate(item, headwordid);
		return reviewWord;
		
	}
	public String extractByDepTemplate(PreProcessedItem item, int aspectHeadwordId)
	{
		String reviewWord;
		reviewWord = extractByTemplate(TemplateFactory.N_SBV_A, item, aspectHeadwordId);
		if(reviewWord != null){return reviewWord;}
		reviewWord = extractByTemplate(TemplateFactory.N_SBV_V_VOB_N, item, aspectHeadwordId);
		if(reviewWord != null){return reviewWord;}
		reviewWord = extractByTemplate(TemplateFactory.A_ATT_N, item, aspectHeadwordId);
		if(reviewWord != null){return reviewWord;}
		reviewWord = extractByTemplate(TemplateFactory.N_ATT_N_SBV_A, item, aspectHeadwordId);
		if(reviewWord != null){return reviewWord;}
		return reviewWord;
	}
	/**
	 * 
	 * @param item
	 * @param id aspect word(phrase) begin id , may be left margin or right margin
	 * @param dir 0:left margin 1: right margin 2: single word(left and right is ok)
	 * @return
	 */
	public String extractByPosTemplate(PreProcessedItem item,int id, int dir)
	{
		String reviewWord = null;
		if(dir == 0)
		{
			reviewWord = extractByTemplate(TemplateFactory.A_N_POS, item, id);
		}else if(dir == 1)
		{
			reviewWord = extractByTemplate(TemplateFactory.N_D_A_POS, item, id);
		}else
		{
			reviewWord = extractByTemplate(TemplateFactory.A_N_POS, item, id);
			if(reviewWord != null) return reviewWord;
			reviewWord = extractByTemplate(TemplateFactory.N_D_A_POS, item, id);
			if(reviewWord != null) return reviewWord;
		}
		return reviewWord;
		
	}
	private String extractByTemplate(int tid,PreProcessedItem item, int aspectwordid)
	{
		String reviewWord;
		template = TemplateFactory.generateTemplate(tid);
		reviewWord = template.extract(item, aspectwordid);
		return reviewWord;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ReviewWordExtractor re = new ReviewWordExtractor();
		PreProcessedItem item = new PreProcessedItem("前脸设计很棒");
//		System.out.println(item.dpids);
//		System.out.println(item.dprels);
		int id = 0;
		String extracted = re.extract(item, 0, 1);
		System.out.println(extracted);
	}

}
