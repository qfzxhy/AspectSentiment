package nju.edu.cn.Model;

import java.util.HashMap;
import java.util.Map;

import Util.FileUtil;
import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;

public class PolarityDetectionModel implements Model{
	Map<String, Integer> positiveMap;
	Map<String, Integer> negativeMap;
	public PolarityDetectionModel(String modelPath) {
		// TODO Auto-generated constructor stub
		loadModel(modelPath);
	}
	@Override
	public void loadModel(String modelPath) {
		// TODO Auto-generated method stub
		positiveMap = new HashMap<String, Integer>();
		negativeMap = new HashMap<>();
		FileUtil util = new FileUtil();
		util.loadWordMap(positiveMap, (modelPath.endsWith("/") ? modelPath + "pos" : modelPath + "/pos"), 1);
		util.loadWordMap(negativeMap, (modelPath.endsWith("/") ? modelPath + "neg" : modelPath + "/neg"), -1);
	}

	@Override
	public String predict(PreProcessedItem preprocessedItem, LabelItem labelItem) {
		// TODO Auto-generated method stub
		String opinWord = labelItem.getOpinionExpression();
		if(positiveMap.containsKey(opinWord))
		{
			return "1";
		}
		if(negativeMap.containsKey(opinWord))
		{
			return "-1";
		}
		return "0";
	}

}
