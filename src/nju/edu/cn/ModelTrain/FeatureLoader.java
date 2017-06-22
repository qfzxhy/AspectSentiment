package nju.edu.cn.ModelTrain;

import java.util.ArrayList;
import java.util.List;

import nju.edu.cn.label.PreProcessedItem;

public class FeatureLoader {

	public List<String> load(String[][] sentence, String label,boolean isTraining) {
		// TODO Auto-generated method stub
		List<String> features = new ArrayList<String>();
		if(isTraining)
			features.add(label);
		for(int i = 0;i < sentence.length;i++)
		{
			features.addAll(word2features(sentence, i));
		}
		int entityStartId = -1,entityEndId = -1;
		
		features.addAll(entity2features(sentence, entityStartId, entityEndId));
		return features;
	}
	private List<String> word2features(String[][] sentence, int i)
	{
		List<String> features = new ArrayList<String>();
		String word = sentence[i][0];
		String postag = sentence[i][1];
		String preWord = i > 0 ? sentence[i-1][0] : "#";
		String prePostag = i > 0 ? sentence[i-1][1]: "#";
		features.add(word);
		features.add(postag);
		features.add(preWord+word);
		features.add(prePostag+postag);
		return features;
	}
	private List<String> entity2features(String[][] sentence, int entityStartId,int entityEndId)
	{
		List<String> features = new ArrayList<String>();
		if(entityEndId == -1 || entityStartId == -1){return features;}
		String entity = "";
		for(int i =entityEndId;i<entityEndId;i++)
		{
			entity += sentence[i][0];
		}
		//features = 
		{
			features.add("entity:"+entity);
			features.add("entityLen:"+(entityEndId-entityStartId));
			//window = 2
			if(entityStartId > 0)
			{
				features.add("-1:word:"+sentence[entityStartId-1][0]);
				features.add("-1:postag:"+sentence[entityStartId-1][1]);
			}else
				features.add("-1:BOS");
			if(entityStartId > 1)
			{
				features.add("-2:word:"+sentence[entityStartId-2][0]);
				features.add("-2:postag:"+sentence[entityStartId-2][1]);
			}else
				features.add("-2:BOS");
			
			if(entityEndId < sentence.length - 1)
			{
				features.add("+1:word:"+sentence[entityEndId+1][0]);
				features.add("+1:postag:"+sentence[entityEndId+1][1]);
			}else
				features.add("+1:EOS");
			if(entityEndId < sentence.length - 2)
			{
				features.add("+2:word:"+sentence[entityEndId+2][0]);
				features.add("+2:postag:"+sentence[entityEndId+2][1]);
			}else
				features.add("+2:EOS");
		}
		return features;
	}

}
