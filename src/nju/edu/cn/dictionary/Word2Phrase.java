package nju.edu.cn.dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Util.FileUtil;
import nju.edu.cn.ltpTools.NLPBasicTool;

public class Word2Phrase {
	//共现
	//提取常用词
	//n + { v +　d} + a
	public String trainfile;
	public int threshold;
	Map<String, Integer> vocabHash;
	Map<Double, List<String>> phraseHash;
	int vocabCount = 0;
	public Word2Phrase(String trainFile,int threshold) {
		// TODO Auto-generated constructor stub
		this.trainfile = trainFile;
		vocabHash = new HashMap<>();
		this.threshold = threshold;
		phraseHash = new TreeMap<>(new Comparator<Double>() {

			@Override
			public int compare(Double o1, Double o2) {
				// TODO Auto-generated method stub
				if(o2 > o1)return 1;
				else return 0;
			}
		});
	}
	public void learnVocabFromTrainFile() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(this.trainfile));
		String sentence = "";
		while((sentence = br.readLine()) != null)
		{
			if(sentence.trim().length() == 0)continue;
			String[] words = sentence.split("\\s+");
			String preWord = "</b>",word = "";
			for(int i =0;i<words.length;i++)
			{
				vocabCount++;
				word = words[i];
				if(!vocabHash.containsKey(word))
				{
					vocabHash.put(word, 1);
				}else
					vocabHash.put(word, vocabHash.get(word)+1);
				if(!preWord.equals("</b>"))
				{
					String biWord = preWord + "_" + word;
					if(!vocabHash.containsKey(biWord))
					{
						vocabHash.put(biWord, 1);
					}else
						vocabHash.put(biWord, vocabHash.get(biWord)+1);
				}
				preWord = word;
			}
			
		}
		br.close();
	}
	void trainModel() throws IOException
	{
		BufferedReader br = null;
		learnVocabFromTrainFile();
		br = new BufferedReader(new FileReader(this.trainfile));
		String sentence = "";
		while((sentence = br.readLine()) != null)
		{
			if(sentence.trim().length() == 0){continue;}
			String[] words = sentence.split("\\s+");
			String preWord = "</b>",word = "";
			for(int i =0;i<words.length;i++)
			{
				word = words[i];
				if(!preWord.equals("</b>"))
				{
					double score = (float)vocabHash.get(preWord+"_"+word) / (float)vocabHash.get(preWord) / (float)vocabHash.get(word) * vocabCount;
					if(score >= threshold)
					{
						if(!phraseHash.containsKey(score))
						{
							phraseHash.put(score, new ArrayList<String>());
						}
						phraseHash.get(score).add(preWord+"_"+word);
					}
				}
				preWord = word;
			}
		}
		
		br.close();
	}
	public void printSeeds()
	{
		for(Map.Entry<Double, List<String>> entry : phraseHash.entrySet())
		{
			System.out.println(entry.getKey()+":");
			List<String> phrases = entry.getValue();
			for(int i =0;i<phrases.size();i++)
			{
				System.out.print(phrases.get(i));
				if(i < phrases.size() - 1)
				{
					System.out.print(" | ");
				}
			}
			System.out.println();
		}
	}
	public  void getSeeds() throws IOException
	{
		Map<String, Integer> map = new HashMap<>();
		Map<Integer, List<String>> treeMap = new TreeMap<>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o2 - o1;
			}
		});
		BufferedReader br = new BufferedReader(new FileReader(this.trainfile));
		String sentence = "";
		while((sentence = br.readLine()) != null)
		{
			if(sentence.trim().length() == 0)continue;
			if(sentence.trim().equals(""))continue;
			String[] wordArray = sentence.split("\\s+");
			int i = 1;
			while(i < wordArray.length)
			{
				String word = wordArray[i].split("/")[0];
				String tag = wordArray[i].split("/")[1];
				String preWord = wordArray[i-1].split("/")[0];
				String preTag = wordArray[i-1].split("/")[1];
				if(!(tag.equals("n")&&preTag.equals("n")))
				{
					i+=1;
					continue;
				}
				vocabCount++;
				if(!vocabHash.containsKey(preWord))
				{
					vocabHash.put(preWord, 1);
				}else
				{
					vocabHash.put(preWord,vocabHash.get(preWord)+1);
				}
				if(!vocabHash.containsKey(word))
				{
					vocabHash.put(word, 1);
				}else
				{
					vocabHash.put(word,vocabHash.get(word)+1);
				}
				String phrase = preWord+word;
				if(!map.containsKey(phrase))
				{
					map.put(phrase,1);
				}else
					map.put(phrase, map.get(phrase)+1);
				i++;
			}
		}
		br.close();
		for(java.util.Map.Entry<String, Integer> entry : map.entrySet())
		{
			String key = entry.getKey();
			int value = (int) (entry.getValue());
			if(value < 5)continue;
			//value = (int) (value*1.0/vocabHash.get(key.split("_")[0])/vocabHash.get(key.split("_")[1]) * vocabCount);
			if(!treeMap.containsKey(value))
			{
				treeMap.put(value, new ArrayList<String>());
			}
			treeMap.get(value).add(key);
		}
		for(Map.Entry<Integer, List<String>> entry : treeMap.entrySet())
		{
			//System.out.println(entry.getKey()+":");
			List<String> phrases = entry.getValue();
			for(int i =0;i<phrases.size();i++)
			{
				System.out.println(phrases.get(i));
//				if(i < phrases.size() - 1)
//				{
//					System.out.print(" | ");
//				}
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String filePath = "G:\\Master2D\\NJUAdmaster\\data\\aspect_detect\\shushixing_corpus.csv";
//		List<String> sentences = FileUtil.loadSentence(filePath);
////		List<String> sentences = new ArrayList<>();
////		sentences.add("悬挂硬，过坑洼路段跟跳舞似的。");
////		sentences.add("我觉得还可以，我爸说悬挂硬有点颠，不过我挺喜欢这种感觉的");
//		Word2Phrase.getSeeds(sentences);
		Word2Phrase word2phrase = new Word2Phrase("dongli.txt", 10);
		try {
//			word2phrase.trainModel();
//			word2phrase.printSeeds();
			word2phrase.getSeeds();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
