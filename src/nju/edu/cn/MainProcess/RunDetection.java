package nju.edu.cn.MainProcess;

import nju.edu.cn.Model.Model;
import nju.edu.cn.Model.ProductExtractionModel;
import nju.edu.cn.ModelTrain.EntityExtractionModel;
import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;

public class RunDetection {
	final static String productModelPath = "";
	final static String entityModelPath = "";
	final static String AttributeModelPath = "";
	final static String OpinionModelPath = "";
	Model productModel = new ProductExtractionModel(productModelPath);
	Model entityModel = new nju.edu.cn.Model.EntityExtractionModel(entityModelPath);
	public void runEach(String sentence)
	{
		LabelItem labelItem = new LabelItem(sentence);
		PreProcessedItem pItem = new PreProcessedItem(sentence);
		String productName = productModel.predict(pItem, labelItem);
		labelItem.setProductName(productName);
		String entityAndExpression = entityModel.predict(pItem, labelItem);
		labelItem.setEntity(entityAndExpression.split("#")[0]);
		labelItem.setEntityExpression(entityAndExpression.split("#")[1]);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = null;
		String ss = s+'#'+"ii";
		System.out.println(ss.split("#")[0] == null);
	}

}
