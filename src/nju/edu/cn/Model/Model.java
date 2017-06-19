package nju.edu.cn.Model;

import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;

public interface Model {
	abstract void loadModel(String modelPath);
	abstract String predict(PreProcessedItem preprocessedItem,LabelItem labelItem);
}
