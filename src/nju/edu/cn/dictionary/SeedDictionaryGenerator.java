package nju.edu.cn.dictionary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nju.edu.cn.Rule.PosTagRule;
import Util.FileUtil;

/**
 * 
 * @author qf
 *	2017-3-21
 *
 */
public class SeedDictionaryGenerator {
	private final static String root = "G:\\Master2D\\NJUAdmaster\\data\\";
	private DescWordDic descWordDic;
	private OpinionWordDic opinWordDic;
	public SeedDictionaryGenerator() {
		// TODO Auto-generated constructor stub
		descWordDic = DescWordDic.getInstance();
		opinWordDic = OpinionWordDic.getInstance();
		
	}
	/**
	 * ���Ӵ�����
	 */
	Map<String, Integer> posWordMap = new HashMap<String, Integer>();
	Map<String, Integer> negWordMap = new HashMap<String, Integer>();
	public void seedDictionaryGenerate(String filePath,Map<String,Boolean> posDic,Map<String,Boolean> negDic)
	{
		List<String> sentences = FileUtil.loadSentence(filePath);
		
		for(String sentence : sentences)
		{
			if(sentence.trim().length() == 0)continue;
			String[] wordAndTags = sentence.trim().split(" ");
			for(int i =0;i<wordAndTags.length;i++)
			{
				String word = wordAndTags[i].split("/")[0];
				if(posDic.containsKey(word))
				{
					if(PosTagRule.rule_NDA(wordAndTags, i) && word.length() > 1)
					{
						//System.out.println(posWord);
						//opinWordDic.addPosWord(word);
						if(!posWordMap.containsKey(word))
						{
							posWordMap.put(word, 0);
						}
						posWordMap.put(word, posWordMap.get(word)+1);
					}
				}else
				if(negDic.containsKey(word))
				{
					if(PosTagRule.rule_NDA(wordAndTags, i) && word.length() > 1)
					{
						//System.out.println(negWord);
						//opinWordDic.addNegWord(word);
						if(!negWordMap.containsKey(word))
						{
							negWordMap.put(word, 0);
						}
						negWordMap.put(word, negWordMap.get(word)+1);
					}
				}
				
			}
		}

		
	}
	public void writeToFile()
	{
		for(Map.Entry<String, Integer> entry : posWordMap.entrySet())
		{
			if(entry.getValue() > 2)opinWordDic.addPosWord(entry.getKey());
		}
		for(Map.Entry<String, Integer> entry : negWordMap.entrySet())
		{
			if(entry.getValue() > 2)opinWordDic.addNegWord(entry.getKey());
		}
		opinWordDic.writeNegWordToFile(root+"seed_8_aspect_Neg");
		opinWordDic.writePosWordToFile(root+"seed_8_aspect_Pos");
	}
	/**
	 * ��չ�ʵ�
	 */

	public static void main(String[] args) {
		String root = "G:\\Master2D\\NJUAdmaster\\data\\segment_postag\\";
		Map<String,Boolean> posDic = FileUtil.loadWordMap("G:\\Master2D\\NJUAdmaster\\data\\" + "pos.txt");
		Map<String,Boolean> negDic = FileUtil.loadWordMap("G:\\Master2D\\NJUAdmaster\\data\\" + "neg.txt");
		String file1 = root + "caokong_corpus.csv";
		String file2 = root + "dongli_corpus.csv";
		String file3 = root + "kongjian_corpus.csv";
		String file4 = root + "neishi_corpus.csv";
		String file5 = root + "shushixing_corpus.csv";
		String file6 = root + "waiguan_corpus.csv";
		String file7 = root + "xingjiabi_corpus.csv";
		String file8 = root + "youhao_corpus.csv";
		
		// TODO Auto-generated method stub
		SeedDictionaryGenerator dg = new SeedDictionaryGenerator();
		dg.seedDictionaryGenerate(file1, posDic, negDic);
		dg.seedDictionaryGenerate(file2, posDic, negDic);
		dg.seedDictionaryGenerate(file3, posDic, negDic);
		dg.seedDictionaryGenerate(file4, posDic, negDic);
		dg.seedDictionaryGenerate(file5, posDic, negDic);
		dg.seedDictionaryGenerate(file6, posDic, negDic);
		dg.seedDictionaryGenerate(file7, posDic, negDic);
		dg.seedDictionaryGenerate(file8, posDic, negDic);
		dg.writeToFile();
		
	}

}
