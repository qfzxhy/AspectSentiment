package nju.edu.cn.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.FileUtil;
import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;

public class EntityExtractionModel implements Model{
	
	Trie trie = null;
	Map<String, String> entitysTypeMap = null;
	public EntityExtractionModel(String modelPath) {
		// TODO Auto-generated constructor stub
		loadModel(modelPath);
	}
	/**
	 * modelPath : csv type
	 */
	@Override
	public void loadModel(String modelPath) {
		// TODO Auto-generated method stub
		entitysTypeMap = new HashMap<String, String>();
		trie = new Trie();
		List<String> entityList = new ArrayList<>();
		loadCSV(modelPath, entityList);
		for(String entity:entityList)
		{
			System.out.println(entity);
		}
		
		trie = trie.createTrie(entityList);
	}
	private void loadCSV(String modelPath,List<String> entityList)
	{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(modelPath)));
			String line = "";
			while((line = br.readLine()) != null)
			{
				if(line.trim().equals("")){continue;}
				String[] entityArray = line.split(",");
				if(entityArray.length == 0){continue;}
				entityList.add(entityArray[0]);
				for(int i = 1;i<entityArray.length;i++)
				{
					entityList.add(entityArray[i]);
					if(!entitysTypeMap.containsKey(entityArray[i]))
					{
						entitysTypeMap.put(entityArray[i], entityArray[0]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String predict(PreProcessedItem preprocessedItem, LabelItem labelItem) {
		// TODO Auto-generated method stub
		String entityExpression = trie.searchInSentence(labelItem.sentence, trie);
		//labelItem.setEntityExpression(entityExpression);
		String entity = null;
		if(entitysTypeMap.containsKey(entityExpression))
		{
			entity = entitysTypeMap.get(entityExpression);
			//labelItem.setEntity(entitysTypeMap.get(entityExpression));
		}
		return entity+"#"+entityExpression;

	}
	public static void main(String[] args) {
		EntityExtractionModel m = new EntityExtractionModel("data/entitySeeds");
	}
}
