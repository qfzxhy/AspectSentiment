package nju.edu.cn.TemplateForExtraction;

import nju.edu.cn.label.PreProcessedItem;

public class SyntaxBasedTemplate4 implements Template {

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
		//Aspect_ATT_{n}_SBV_{a}
				//return a
				//example: 过弯 极限 非常高
		int n = item.size;
		if(beginWordId < 0 || beginWordId >= n)
		{
			System.err.println("something error in tempate1 extract beginWordId");
			return null;
		}
		if(!item.getDpParentRel(beginWordId).equals("ATT"))
		{
			return null;
		}
		int parentId = item.getDpparentID(beginWordId);
		if(parentId < 0 || parentId >= n){return null;}
		if(item.getDpParentRel(parentId).equals("SBV"))
		{
			int id = item.getDpparentID(parentId);
			if(item.getPosTag(id).equals("a"))
			{
				return item.getWord(id);
			}
		}
		return null;
	}

	@Override
	public String extractFromOpinionToAspect(PreProcessedItem item,
			int beginWordId) {
		// TODO Auto-generated method stub
		//Aspect_ATT_{n}_SBV_{a}
		//example: 过弯 极限 非常高
		int n = item.size;
		if(beginWordId < 0 || beginWordId >= n)
		{
			System.err.println("something error in tempate1 extract beginWordId");
			return null;
		}
		for(int i = beginWordId - 1;i>=0;i--)
		{
			int pd = item.getDpparentID(i);
			if(pd < 0 || pd >=n){continue;}
			if(item.getDpParentRel(i).equals("ATT") && item.getDpparentID(pd) == beginWordId)
			{
				return item.getWord(i);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
