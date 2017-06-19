package nju.edu.cn.TemplateForExtraction;

import nju.edu.cn.label.PreProcessedItem;

public class SyntaxBasedTemplate1 implements Template {

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
		//Aspect -SBV- a(POS)
		//return a
		//油耗不错
		int parentid = item.getDpparentID(beginWordId);
		if(parentid < 0 || parentid >= n)return null;
		if(item.getPosTag(parentid).equals("a") && item.getDpParentRel(beginWordId).equals("SBV"))
		{
			return item.words.get(parentid);
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
		for(int i = beginWordId - 1; i >=0; i--)
		{
			if(item.getDpparentID(i) == beginWordId && item.getDpParentRel(i).equals("SBV"))
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
