package nju.edu.cn.TemplateForExtraction;

import nju.edu.cn.label.PreProcessedItem;

public class SyntaxBasedTemplate implements Template {

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
		
		String opinion = null;
		Template temp1 = new SyntaxBasedTemplate1();
		opinion = temp1.extractFromAspectToOpinion(item, beginWordId);
		if(opinion != null) return opinion;
		Template temp2 = new SyntaxBasedTemplate2();
		opinion = temp2.extractFromAspectToOpinion(item, beginWordId);
		if(opinion != null) return opinion;
		Template temp3 = new SyntaxBasedTemplate3();
		opinion = temp3.extractFromAspectToOpinion(item, beginWordId);
		if(opinion != null) return opinion;
		Template temp4 = new SyntaxBasedTemplate4();
		opinion = temp4.extractFromAspectToOpinion(item, beginWordId);
		if(opinion != null) return opinion;
		return null;
	}

	@Override
	public String extractFromOpinionToAspect(PreProcessedItem item,
			int beginWordId) {
		// TODO Auto-generated method stub
		String AspectWord = null;
		Template temp1 = new SyntaxBasedTemplate1();
		AspectWord = temp1.extractFromOpinionToAspect(item, beginWordId);
		if(AspectWord != null) return AspectWord;
		Template temp2 = new SyntaxBasedTemplate2();
		AspectWord = temp2.extractFromOpinionToAspect(item, beginWordId);
		if(AspectWord != null) return AspectWord;
		Template temp3 = new SyntaxBasedTemplate3();
		AspectWord = temp3.extractFromOpinionToAspect(item, beginWordId);
		if(AspectWord != null) return AspectWord;
		Template temp4 = new SyntaxBasedTemplate4();
		AspectWord = temp4.extractFromOpinionToAspect(item, beginWordId);
		if(AspectWord != null) return AspectWord;
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
