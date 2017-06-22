package nju.edu.cn.Model;

import java.util.List;

import nju.edu.cn.TemplateForExtraction.PosBasedTemplate;
import nju.edu.cn.TemplateForExtraction.SyntaxBasedTemplate;
import nju.edu.cn.TemplateForExtraction.Template;
import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;

public class OpinionExtractionModel implements Model{
	Template posBasedTemplate;
	Template synBasedTemplate;
	public OpinionExtractionModel() {
		// TODO Auto-generated constructor stub
		loadModel("");
	}
	@Override
	public void loadModel(String modelPath) {
		// TODO Auto-generated method stub
		posBasedTemplate = new PosBasedTemplate();
		synBasedTemplate = new SyntaxBasedTemplate();
	}

	@Override
	public String predict(PreProcessedItem preprocessedItem, LabelItem labelItem) {
		// TODO Auto-generated method stub
		int beginWordId = getEntityIdInTokens(labelItem.sentence, preprocessedItem.words, labelItem.getEntityExpression());
		String opinion = null;
		opinion = posBasedTemplate.extractFromAspectToOpinion(preprocessedItem, beginWordId);
		if(opinion == null)
		{
			opinion = synBasedTemplate.extractFromAspectToOpinion(preprocessedItem, beginWordId);
		}
		return opinion;
	}
	private int getEntityIdInTokens(String sentence,List<String> tokens, String entity)
	{
		int n = sentence.length();
		int startIndex = sentence.indexOf(entity);
		int endIndex = startIndex + entity.length();
		if(startIndex == -1)return -1;
		int len = 0;
		for(int i=tokens.size()-1;i>=0;i--)
		{
			if(n - len - 1 >= startIndex && n - len - 1 < endIndex)
			{
				return i;
			}
			len += tokens.get(i).length();
		}
		return -1;
	}
	public static void main(String[] args) {
		
	}
}
