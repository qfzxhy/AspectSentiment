package nju.edu.cn.TemplateForExtraction;

import nju.edu.cn.label.PreProcessedItem;

public class SyntaxBasedTemplate3 implements Template {

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
		//{a} -> O-dep ->{n} O-dep = {ATT}
				//{a} -> O-dep ->{n} -> A1_dep -> A2
				//return a
				//真的很不错的邮箱
		int n = item.size;
		if(beginWordId < 0 || beginWordId >= n)
		{
			System.err.println("something error in tempate1 extract beginWordId");
			return null;
		}
	
		for(int i = 0; i < beginWordId; i++)
		{
			if(item.getDpparentID(i) == beginWordId && item.getDpParentRel(i).equals("ATT") && item.getPosTag(i).equals("a"))
			{
				return item.getWord(i);
			}
		}
		return null;
	}

	@Override
	public String extractFromOpinionToAspect(PreProcessedItem item,
			int beginWordId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				//{a} -> O-dep ->{n} O-dep = {ATT}
						//{a} -> O-dep ->{n} -> A1_dep -> A2
						//return a
						//真的很不错的邮箱
				int n = item.size;
				if(beginWordId < 0 || beginWordId >= n)
				{
					System.err.println("something error in tempate1 extract beginWordId");
					return null;
				}
				int parentId = item.getDpparentID(beginWordId);
				if(parentId < 0 || parentId >=n)return null;
				if(item.getDpParentRel(beginWordId).equals("ATT"))
				{
					return item.getWord(parentId);
				}
				return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
