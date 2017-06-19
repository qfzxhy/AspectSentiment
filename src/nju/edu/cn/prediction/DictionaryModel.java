package nju.edu.cn.prediction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.FileUtil;
import nju.edu.cn.config.FileConfig;

import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;
import nju.edu.cn.ltpTools.NLPBasicTool;

/**
 * 抽取 aspectword and opinionWord
 * @author qf
 *2017/04/26
 */
public class DictionaryModel {
	public Map<String,Integer> aspectDic;
	public Map<String, Integer> opinionDic;
	public DictionaryModel() {
		// TODO Auto-generated constructor stub
		aspectDic = new HashMap<String, Integer>();
		opinionDic = new HashMap<>();
	}
	public void loadModel()
	{
		System.out.println(FileConfig.getAspectDicPath());
		File[] aspectFiles = loadDir(FileConfig.getAspectDicPath());
		int label = 1;
		for(File aspectFile : aspectFiles)
		{
			FileUtil.loadWordMap(aspectDic,aspectFile, label++);
		}
		File[] opinionFiles = loadDir(FileConfig.getOpinionWordPath());
		for(File opininFile : opinionFiles)
		{
			if(opininFile.getName().matches("neg"))
			{
				FileUtil.loadWordMap(opinionDic, opininFile, -1);
			}else
				FileUtil.loadWordMap(opinionDic,opininFile,1);
		}
	}
	File[] loadDir(String path)
	{
		List<String> paths = new ArrayList<String>();
		File dir = new File(path);
		
		if(!dir.isDirectory())return null;
		File[] files = dir.listFiles();
		return files;
	}
	/**
	 * 预测一句话中的所有角度词以及评价以及极性
	 * @param item
	 */
	public void predict(LabelItem item)
	{
		String sentence = item.sentence;
		PreProcessedItem preItem = new PreProcessedItem(sentence);
		preItem.print();
		for(int i = 0; i < preItem.size; i++)
		{
			String word = preItem.getWord(i);
			String phrase = (i > 0 ? preItem.getWord(i-1) : "") + word;
			if(aspectDic.containsKey(phrase))
			{
				for(int j = i - 3 > 0 ? i - 3 : 0; j < i + 3;j++)
				{
					if(opinionDic.containsKey(preItem.getWord(j)))
					{
						item.addAspectWord(phrase, preItem.getWord(j), opinionDic.get(preItem.getWord(j)));
					}
				}
			}else if(aspectDic.containsKey(word))
			{
				for(int j = i - 3 > 0 ? i - 3 : 0; j < (i + 3 < preItem.size ? i + 3 : preItem.size);j++)
				{
					if(opinionDic.containsKey(preItem.getWord(j)))
					{
						item.addAspectWord(word, preItem.getWord(j), opinionDic.get(preItem.getWord(j)));
					}
				}
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DictionaryModel dm = new DictionaryModel();
		dm.loadModel();
		LabelItem item = new LabelItem("发动机不错啊！我很喜欢它的动力");
		dm.predict(item);
		System.out.println(item.toString());
		System.out.println(dm.aspectDic.get("的动力"));
		
	}

}
