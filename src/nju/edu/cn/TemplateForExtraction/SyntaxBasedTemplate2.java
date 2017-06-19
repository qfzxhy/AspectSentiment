package nju.edu.cn.TemplateForExtraction;

import nju.edu.cn.label.PreProcessedItem;

public class SyntaxBasedTemplate2 implements Template{

	@Override
	public String extractFromEntityToAspect(PreProcessedItem item,
			int beginWordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String extractFromAspectToEntity(PreProcessedItem item,
			int beginWordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String extractFromAspectToOpinion(PreProcessedItem item,
			int beginWordId) {
		// TODO Auto-generated method stub
		int n = item.size;
		if(beginWordId < 0 || beginWordId >= n)
		{
			System.err.println("something error in tempate1 extract beginWordId");
			return null;
		}
//		 * {n,np,v,vp}_SBV_v_VOB_{n,a}
//		 *	起步 没有 任何 问题
		String extractedWord = "";
		int parentid = item.getDpparentID(beginWordId);
		if(parentid < 0 || parentid >= n)return null;
		if(item.getPosTag(parentid).equals("v") && item.getDpParentRel(beginWordId).equals("SBV"))
		{
			extractedWord += item.getWord(parentid);
			//from  cur id
			for(int i = parentid + 1; i < n;i++)
			{
				if(item.getDpparentID(i)  == parentid && item.getDpParentRel(i).equals("VOB"))
				{
					
					if(item.getPosTag(i).equals("a") || item.getPosTag(i).equals("n"))
					{
						extractedWord += item.getWord(i);
						return extractedWord;
					}
				}
			}
		}
		return null;
	}

	@Override
	public String extractFromOpinionToAspect(PreProcessedItem item,
			int beginWordId) {
		// TODO Auto-generated method stub
		int n = item.size;
		if(beginWordId < 0 || beginWordId >= n)
		{
			System.err.println("something error in tempate1 extract beginWordId");
			return null;
		}
//		 * {n,np,v,vp}_SBV_v_VOB_{n,a}
//		 *	起步 没有 任何 问题
		for(int i = beginWordId -1;i>=0;i--)
		{
			int parentid = item.getDpparentID(i);
			if(parentid<0 || parentid >=n){continue;}
			if(item.getPosTag(parentid).equals("v") && item.getDpParentRel(i).equals("SBV"))
			{
				if(item.getDpparentID(parentid) == beginWordId && item.getDpParentRel(parentid).equals("VOB"))
				{
					return item.getWord(i);
				}
			}
		}
		return null;
	}

}
