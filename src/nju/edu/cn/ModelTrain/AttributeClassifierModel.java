package nju.edu.cn.ModelTrain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Util.FileUtil;
import opennlp.tools.ml.maxent.GIS;
import opennlp.tools.ml.maxent.io.SuffixSensitiveGISModelWriter;
import opennlp.tools.ml.model.AbstractModelWriter;
import opennlp.tools.ml.model.DataIndexer;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.ml.maxent.GIS;
import opennlp.tools.ml.maxent.io.GISModelReader;
import opennlp.tools.ml.maxent.io.SuffixSensitiveGISModelWriter;
import opennlp.tools.ml.model.AbstractModel;
import opennlp.tools.ml.model.AbstractModelWriter;
import opennlp.tools.ml.model.DataIndexer;
import opennlp.tools.ml.model.DataReader;
import opennlp.tools.ml.model.FileEventStream;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.ml.model.OnePassDataIndexer;
import opennlp.tools.ml.model.PlainTextFileDataReader;
/**
 * use python train model
 * @author qf
 *
 */
public class AttributeClassifierModel {
	
	private void loadTrainData(String trainDataPath,List<String> sentences,List<String> labels)
	{
		FileUtil util = new FileUtil();
		List<String> sentAndLabels = util.loadSentence(trainDataPath);
		for(String sentAndLabel : sentAndLabels)
		{
			String[] splitByLabel = sentAndLabel.split("label:");
			sentences.add(splitByLabel[0]);
			labels.add(splitByLabel[1]);
		}
	}
	void trainModel(List<List<String>> allSentenceFeatures, String modelPath)
	{
		this.writeFeature(allSentenceFeatures, "data/maxent/features.txt");
		DataIndexer indexer = null;
		try {
			indexer = new OnePassDataIndexer(new FileEventStream("data/maxent/features.txt"));
			MaxentModel maxEn = GIS.trainModel(20, indexer);
			AbstractModelWriter writer = new SuffixSensitiveGISModelWriter((AbstractModel) maxEn, new File(modelPath));
			writer.persist();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<List<String>> allSentenceFeatureGeneration(List<String> sentences,List<String> labels,FeatureLoader featureLoader,boolean isTraining)
	{
		List<List<String>> allFeatures = new ArrayList<>();
		for(int i = 0;i<sentences.size();i++)
		{
//			System.out.println(sentences.get(i));
			String[] sentence = sentences.get(i).split(" ");
			//label可能有多个label
			String[] labelArray = labels.get(i).split(" ");
			for(String label : labelArray)
			{

				String[][] sent = new String[sentence.length][2];
				for(int j =0;j<sentence.length;j++)
				{
					sent[j][0] = sentence[j].split("/")[0];
					sent[j][1] = sentence[j].split("/")[1];
				}
				List<String> features = featureLoader.load(sent,label,isTraining);
//				System.out.println(features);
				if(features != null && features.size() > 0)
				{
					allFeatures.add(features);
				}
			}
			
		}
		return allFeatures;
		
	}
	/**
	 * 将抽取的特征写入文件
	 * @param featureList
	 * @param filePath
	 */
	public void writeFeature(List<List<String>> allSentenceFeatures,String filePath)
	{
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(filePath)));
			for(List<String> features : allSentenceFeatures)
			{
				if(features.size() == 0){continue;}
//				bw.write(features.get(0)+" ");
				for(String feature : features)
				{
					bw.write(feature+" ");
				}
				bw.write("\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<String> test(List<List<String>> allSentenceFeatures, String modelPath)
	{
		MaxentModel classifier_maxent = null;
		try {
			DataReader modelReader = new PlainTextFileDataReader(new BufferedReader(new FileReader(modelPath)));
			classifier_maxent = new GISModelReader(modelReader).getModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> predicts = new ArrayList<>();
		for(List<String> features : allSentenceFeatures)
		{
			double[] outcomes = classifier_maxent.eval(features.toArray(new String[features.size()]));
			String val = classifier_maxent.getBestOutcome(outcomes);
			predicts.add(val);
		}
		return predicts;
		
	}
	public float evaluation(List<String> goldY,List<String> predY)
	{
		int n = goldY.size();
		int numRight = 0;
		int numPred = 0;
		int numGold = 0;
		for(int i =0;i < n;i++)
		{
			System.out.println(goldY.get(i)+"........"+predY.get(i));
			if(!goldY.get(i).equals("NIL"))
			{
				numGold++;
			}
			if(!predY.get(i).equals("NIL"))
			{
				numPred++;
			}
//			if(predY.get(i).equals(goldY.get(i)) && !predY.equals("NIL"))
//			{
//				numRight++;
//			}
			if(goldY.get(i).indexOf(predY.get(i)) != -1 && !predY.equals("NIL") && !goldY.get(i).equals("NIL")){numRight++;}
		}
		System.out.println("precision:"+numRight*1.0/numPred);
		System.out.println("recall:"+numRight*1.0/numGold);
		float p = (float) (numRight*1.0/numPred);
		float r = (float) (numRight*1.0/numGold);
		float f = 2*p*r/(p+r);
		System.out.println("f-score:"+2*p*r/(p+r));
		return f;
	}
	public void run(String trainDataPath,String modelPath)
	{
		FeatureLoader featureLoader = new FeatureLoader();
		List<String> sentences_train = new ArrayList<>();
		List<String> labels_train = new ArrayList<>();
		loadTrainData(trainDataPath, sentences_train, labels_train);
		List<List<String>> allSentenceFeatures = this.allSentenceFeatureGeneration(sentences_train, labels_train, featureLoader,true);
		trainModel(allSentenceFeatures, modelPath);
		List<List<String>> allTestSentenceFeatures = this.allSentenceFeatureGeneration(sentences_train, labels_train, featureLoader,false);
		List<String> predY =  test(allTestSentenceFeatures, modelPath);
//		for(String y : labels_train)
//		{
//			System.out.println(y);
//		}
//		System.out.println("......................................................");
//		for(String y : predY)
//		{
//			System.out.println(y);
//		}
		evaluation(labels_train, predY);
		
	}
	public void crossValidate(String trainDataPath,String modelPath)
	{
		FeatureLoader featureLoader = new FeatureLoader();
		List<String> sentences = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		loadTrainData(trainDataPath, sentences, labels);
		int splitNum = 5;
		int batchSize = sentences.size() / 5;
		float[] scores = new float[splitNum];
		float avgScore = 0.0f;
		for(int i =0;i<splitNum;i++)
		{
			int startIndex = i * batchSize;
			int endIndex = (i+1)*batchSize;
			if(i == splitNum - 1){endIndex = sentences.size();}
			List<String> evalSentences = sentences.subList(startIndex, endIndex);
			List<String> evalLabels = labels.subList(startIndex, endIndex);
			List<String> trainSentences = new ArrayList<>();
			trainSentences.addAll(sentences.subList(0,startIndex ));
			trainSentences.addAll(sentences.subList(endIndex, sentences.size()));
			List<String> trainLabels = new ArrayList<>();
			trainLabels.addAll(labels.subList(0,startIndex ));
			trainLabels.addAll(labels.subList(endIndex, labels.size()));
			
			List<List<String>> allTrainSentenceFeatures = this.allSentenceFeatureGeneration(trainSentences, trainLabels, featureLoader,true);
			trainModel(allTrainSentenceFeatures, modelPath);
			List<List<String>> allTestSentenceFeatures = this.allSentenceFeatureGeneration(evalSentences, evalLabels, featureLoader,false);
			List<String> predY =  test(allTestSentenceFeatures, modelPath);
			float fscore = evaluation(evalLabels, predY);
			scores[i] = fscore;
			avgScore += fscore;
		}
		System.out.println(avgScore/splitNum);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//调用trainmodel1()生成第一个模型
		//调用trainmodel2()生成第一个模型
		AttributeClassifierModel am = new AttributeClassifierModel();
		am.run("data/maxent/traindata", "data/maxent/model");
	}
}
