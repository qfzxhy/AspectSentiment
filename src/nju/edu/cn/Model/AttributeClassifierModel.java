package nju.edu.cn.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import opennlp.tools.ml.maxent.io.GISModelReader;
import opennlp.tools.ml.model.DataReader;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.ml.model.PlainTextFileDataReader;
import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;

public class AttributeClassifierModel implements Model{

	public MaxentModel classifier_maxent; 
	public AttributeClassifierModel(String modelPath) {
		// TODO Auto-generated constructor stub
		this.loadModel(modelPath);
	}
	@Override
	public void loadModel(String modelPath) {
		// TODO Auto-generated method stub
		try {
			DataReader modelReader = new PlainTextFileDataReader(new BufferedReader(new FileReader(modelPath)));
			this.classifier_maxent = new GISModelReader(modelReader).getModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String predict(PreProcessedItem pItem, LabelItem labelItem) {
		// TODO Auto-generated method stub
		String[] features = this.loadFeature(pItem,labelItem);
		double[] outcomes = this.classifier_maxent.eval(features);
		String val = classifier_maxent.getBestOutcome(outcomes);
		return val;
	}
	
	private String[] loadFeature(PreProcessedItem pItem, LabelItem labelItem) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}


}
