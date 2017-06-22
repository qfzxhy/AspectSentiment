package nju.edu.cn.MainProcess;

import java.util.ArrayList;
import java.util.List;

import Util.FileUtil;
import nju.edu.cn.Model.AttributeClassifierModel;
import nju.edu.cn.Model.EntityExtractionModel;
import nju.edu.cn.Model.Model;
import nju.edu.cn.Model.OpinionExtractionModel;
import nju.edu.cn.Model.PolarityDetectionModel;
import nju.edu.cn.Model.ProductExtractionModel;
import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;

public class RunDetection {
	final static String productModelPath = "data/products";
	final static String entityModelPath = "data/entitySeeds";
	final static String AttributeModelPath = "";
	final static String PolarityModelPath = "data/opiniondic";
	Model productModel = new ProductExtractionModel(productModelPath);
	Model entityModel = new EntityExtractionModel(entityModelPath);
//	Model attriModel = new AttributeClassifierModel();
	Model opinionModel = new OpinionExtractionModel();
	Model polarityModel = new PolarityDetectionModel(PolarityModelPath);
	public LabelItem runEach(String sentence)
	{
		LabelItem labelItem = new LabelItem(sentence);
		PreProcessedItem pItem = new PreProcessedItem(sentence);
		String productName = productModel.predict(pItem, labelItem);
		labelItem.setProductName(productName);
		String entityAndExpression = entityModel.predict(pItem, labelItem);
		labelItem.setEntity(entityAndExpression.split("#")[0]);
		labelItem.setEntityExpression(entityAndExpression.split("#")[1]);
		String opinion = opinionModel.predict(pItem, labelItem);
		labelItem.setOpinionExpression(opinion);
		String ploarity = polarityModel.predict(pItem, labelItem);
		labelItem.setSentimentPolarity(Byte.parseByte(ploarity));
		return labelItem;
	}
	public void run(List<String> testDataList)
	{
		List<String> items = new ArrayList<String>();
		for(String sent : testDataList)
		{
			System.out.println(sent);
			items.add(runEach(sent).toString());
		}
		FileUtil util = new FileUtil();
		util.writeListToFile("data/result", items);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunDetection rundetection = new RunDetection();
//		FileUtil util = new FileUtil();
//		List<String> testDatas = util.loadSentence("data/traindata");
//		rundetection.run(testDatas);
		System.out.println(rundetection.runEach("空间、安全配置、高科技配置等"));;
	}

}
