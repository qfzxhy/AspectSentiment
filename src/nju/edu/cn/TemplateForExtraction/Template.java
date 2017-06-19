package nju.edu.cn.TemplateForExtraction;

import nju.edu.cn.label.PreProcessedItem;

public interface Template {

	abstract String extractFromEntityToAspect(PreProcessedItem item,int beginWordId);
	abstract String extractFromAspectToEntity(PreProcessedItem item,int beginWordId);
	
	abstract String extractFromAspectToOpinion(PreProcessedItem item,int beginWordId);
	abstract String extractFromOpinionToAspect(PreProcessedItem item,int beginWordId);
	
//	abstract String extractFromEntityToOpinion(PreProcessedItem item,int id);
//	abstract String extractFromOpinionToEntity(PreProcessedItem item,int id);
}
