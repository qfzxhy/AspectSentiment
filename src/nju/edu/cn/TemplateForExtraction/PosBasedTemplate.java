package nju.edu.cn.TemplateForExtraction;

import nju.edu.cn.label.PreProcessedItem;

public class PosBasedTemplate implements Template {

	@Override
	public String extractFromEntityToAspect(PreProcessedItem item, int beginWordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String extractFromAspectToEntity(PreProcessedItem item, int beginWordId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String extractFromAspectToOpinion(PreProcessedItem item, int beginWordId) {
		// TODO Auto-generated method stub
		int n = item.size;
		if(beginWordId < 0 || beginWordId >= n)
		{
			System.err.println("something error in tempate1 extract beginWordId");
			return null;
		}
		//rule: Aspect + a(POS)
		//return a
		//example: 性能 给力
		if(beginWordId + 1 < n && item.getPosTag(beginWordId+1).equals("a"))
		{
			return item.getWord(beginWordId + 1);
		}
		//rule: Aspect + d(POS) + a(POS)
		//return a
		//example: 性能 没有 问题
		if(beginWordId + 1 < n && item.getPosTag(beginWordId + 1).equals("d"))
		{
			if(beginWordId + 2 < n && item.getPosTag(beginWordId + 2).equals("a"))
			{
				return item.getWord(beginWordId + 1) + item.getWord(beginWordId + 2);
			}
		}
		//a(POS) + Aspect
		//return a
		//expamle: 给力 配置
		if(beginWordId - 1 >= 0 && item.getWord(beginWordId - 1).equals("a"))
		{
			return item.getWord(beginWordId - 1);
		}
		//a(POS) + 的  + Aspect
		//return a
		//example: 给力 的 性能
		if(beginWordId - 1 >= 0 && beginWordId - 2 >= 0 && item.getWord(beginWordId - 1).equals("的") && item.getPosTag(beginWordId - 2).equals("a"))
		{
			return item.getWord(beginWordId - 2);
		}
		return null;
	}

	@Override
	public String extractFromOpinionToAspect(PreProcessedItem item, int beginWordId) {
		// TODO Auto-generated method stub
		int n = item.size;
		if(beginWordId < 0 || beginWordId >= n)
		{
			System.err.println("something error in tempate1 extract beginWordId");
			return null;
		}
		//rule: Aspect + a(POS)
		//return Aspect
		//example: 性能 给力
		if(item.getPosTag(beginWordId).equals("a") && beginWordId > 0)
		{
			return item.getWord(beginWordId-1);
		}
		//rule: Aspect + d(POS) + a(POS)
		//return a
		//example: 性能 没有 问题
		if(item.getPosTag(beginWordId).equals("a") && beginWordId > 1)
		{
			if(item.getPosTag(beginWordId - 1).equals("d"))
			{
				return item.getWord(beginWordId-2);
			}
		}
		//a(POS) + Aspect
		//return Aspect
		//expamle: 给力 配置
		if(beginWordId + 1 < n && item.getWord(beginWordId).equals("a"))
		{
			return item.getWord(beginWordId + 1);
		}
		//a(POS) + 的  + Aspect
		//return Aspect
		//example: 给力 的 性能
		if(beginWordId - 1 >= 0 && beginWordId - 2 >= 0 && item.getWord(beginWordId - 1).equals("的") && item.getPosTag(beginWordId - 2).equals("a"))
		{
			return item.getWord(beginWordId - 2);
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
