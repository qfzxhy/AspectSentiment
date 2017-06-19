package nju.edu.cn.FileDeal;

import java.util.ArrayList;
import java.util.List;

import Util.FileUtil;
import nju.edu.cn.ltpTools.NLPBasicTool;

public class FileDealingForSegment {
	public void deal(String inputPath,String outputPath)
	{
		FileUtil util = new FileUtil();
		List<String> sentences = util.loadSentence(inputPath);
		List<String> res = new ArrayList<String>();
		for(String sentence : sentences)
		{
			List<String> words = NLPBasicTool.segment(sentence);
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<words.size();i++)
			{
				sb.append(words.get(i)+" ");
			}
			res.add(sb.toString().trim());
		}
		util.writeListToFile(outputPath, res);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileDealingForSegment fd = new FileDealingForSegment();
		fd.deal("data/traindata", "data/traindataSeg");
//		String root1 = "G:\\Master2D\\NJUAdmaster\\data\\aspect_detect\\";
//		String file1 = root1 + "caokong_corpus.csv";
//		String file2 = root1 + "dongli_corpus.csv";
//		String file3 = root1 + "kongjian_corpus.csv";
//		String file4 = root1 + "neishi_corpus.csv";
//		String file5 = root1 + "shushixing_corpus.csv";
//		String file6 = root1 + "waiguan_corpus.csv";
//		String file7 = root1 + "xingjiabi_corpus.csv";
//		String file8 = root1 + "youhao_corpus.csv";
//		String root2 = "G:\\Master2D\\NJUAdmaster\\data\\segment_postag\\";
//		String file11 = root2 + "caokong_corpus.csv";
//		String file22 = root2 + "dongli_corpus.csv";
//		String file33 = root2 + "kongjian_corpus.csv";
//		String file44 = root2 + "neishi_corpus.csv";
//		String file55 = root2 + "shushixing_corpus.csv";
//		String file66 = root2 + "waiguan_corpus.csv";
//		String file77 = root2 + "xingjiabi_corpus.csv";
//		String file88 = root2 + "youhao_corpus.csv";
//		fd.deal(file1, file11);
//		fd.deal(file2, file22);
//		fd.deal(file3, file33);
//		fd.deal(file4, file44);
//		fd.deal(file5, file55);
//		fd.deal(file6, file66);
//		fd.deal(file7, file77);
//		fd.deal(file8, file88);
	}

}
