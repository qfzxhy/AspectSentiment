package nju.edu.cn.FileDeal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nju.edu.cn.ltpTools.NLPBasicTool;
import Util.FileUtil;

public class FileDealingForMaxEntData {
	public void deal(String inputPath,String outputPath)
	{
		FileUtil util = new FileUtil();
		List<String> sentenceAndLabels = util.loadSentence(inputPath);
		List<String> res = new ArrayList<String>();
		for(String sentenceAndLabel : sentenceAndLabels)
		{
			String[] splitArray = sentenceAndLabel.split("\t");
			assert(splitArray.length == 2);
			String sentence = splitArray[0];
			String[] labels = splitArray[1].split(",");
			assert(labels.length > 0) : "something goes wrong here, labels.length cannot be less than 0";
			
			List<String> words = NLPBasicTool.segment(sentence);
			List<String> postags = NLPBasicTool.posTag(words);
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<words.size();i++)
			{
				sb.append(words.get(i)+"/"+postags.get(i)+" ");
			}
			sb.append("label:");
			
			for(int i = 0;i<labels.length;i++)
			{
				if(labels[i].equals("以上都不属于"))
				{
					sb.append("NIL");
					assert(labels.length == 1);
					break;
				}else
				{
					sb.append(labels[i] + " ");
				}
				
			}
			res.add(sb.toString().trim());
		}
		util.writeListToFile(outputPath, res);
	}
	final String[] labels = {
			"动力",
			"性能",
			"价格",
			"服务",
			"外观"
	};
	public void randomLabelAssign(String inputPath,String outputPath)
	{
		Random random = new Random();
		FileUtil util = new FileUtil();
		List<String> sentenceAndLabels = util.loadSentence(inputPath);
		List<String> res = new ArrayList<String>();
		for(String sentenceAndLabel : sentenceAndLabels)
		{
			String label = labels[(int)random.nextInt(labels.length)];
			System.out.println(label);
			sentenceAndLabel = sentenceAndLabel + " " + label;
			res.add(sentenceAndLabel);
		}
		util.writeListToFile(outputPath, res);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileDealingForMaxEntData fd = new FileDealingForMaxEntData();
		//fd.randomLabelAssign("data/traindata", "data/traindata1");
		fd.deal("data/traindata", "data/maxent/traindata");
	}

}
