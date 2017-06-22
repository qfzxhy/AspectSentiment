package nju.edu.cn.ModelTrain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.edu.cn.TemplateForExtraction.PosBasedTemplate;
import nju.edu.cn.TemplateForExtraction.SyntaxBasedTemplate;
import nju.edu.cn.TemplateForExtraction.Template;
import nju.edu.cn.label.*;

import org.apache.xmlbeans.PrePostExtension;

import com.hankcs.hanlp.corpus.document.sentence.Sentence;

import Util.FileUtil;
import nju.edu.cn.ltpTools.*;
class Axis
{
	int row;
	int col;
	public Axis(int row,int col) {
		// TODO Auto-generated constructor stub
		this.row = row;
		this.col = col;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return row + col;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Axis axisObj = (Axis) obj;
		return axisObj.row == row && axisObj.col == col;
	}
}
public class GXMatrixGenerator {
	final static String file = "data/entitySeeds";
	final static int batchSize = 5000;
	final static int thres_countOfNewWords = 100;
	final static int thres_modification = 200;
	final static int thres_Xrealtion = 5;
	final static int thres_Yrealtion = 5;
	List<String> entitySeeds = null;
	//Map<String,Map<String, Integer>> MatrixX = null;
	//Map<String, Map<String, Integer>> MatrixY = null;
	Map<String, Integer> entityMap = null;
	Map<String, Integer> opinionMap = null;
	Map<Axis, Integer> matrix = null;
	public GXMatrixGenerator() {
		// TODO Auto-generated constructor stub
		entityMap = new HashMap<>();
		opinionMap = new HashMap<>();
		matrix = new HashMap<>();
		entitySeeds = new ArrayList<String>();
		loadEntitySeeds();
		for(String entity : entitySeeds)
		{
			if(!entityMap.containsKey(entity))
			{
				entityMap.put(entity, entityMap.size());
			}
		}
	}
	private void loadEntitySeeds() {
		// TODO Auto-generated method stub
		FileUtil util = new FileUtil();
		util.loadWord_CSV(file, entitySeeds);
	}
	public void display()
	{
		System.out.println("display");
//		for(Axis axis : matrix.keySet())
//		{
//			System.out.println(entityMap.get(axis.row) + '#' + opinionMap.get(axis.col) + matrix.get(axis));
//		}
		List<String> res = new ArrayList<>();
		for(String entity : entityMap.keySet())
		{
			for(String opinion : opinionMap.keySet())
			{
				int row = entityMap.get(entity);
				int col = opinionMap.get(opinion);
				if(matrix.containsKey(new Axis(row, col)))
				{
					res.add(entity+"#"+opinion+":"+matrix.get(new Axis(row, col)));
				}
			}
		}
		FileUtil util = new FileUtil();
		util.writeListToFile("src/nju/edu/cn/ModelTrain/GXMatrixDisplay", res);
	}
	/*
	 *  a) 选择一些种子实体词集合O = O0
		b)  对每一个实体词ej∈ O，扫描所有评论，利用模板和规则抽取出其对应的角度词和评价词。当角度词a_i或评价词r_i抽到，则矩阵M1中e_j和a_i之间的频率加一。
		c) 对每一个角度词，同样扫描所有评论，利用规则抽取对应的实体词，M1同样加一处理
		d) 停止条件：1、每次新发现的词小于阈值alpha1、矩阵加一修改次数小于alpha2
		e) 剪枝
		e) 矩阵生成后，需要人筛选实体词和评价词是否规范标准以及是否合理
	 */
	public void batchTrain(List<String> trainDataBatch,Template posBasedTemplate,Template syntaxBasedTemplate)
	{
		int countOfNewWords = Integer.MAX_VALUE,modifiction = Integer.MAX_VALUE;
		//预处理
		PreProcessedItem[] pitems = new PreProcessedItem[trainDataBatch.size()];
		for(int i = 0; i < trainDataBatch.size(); i++)
		{
			pitems[i] = new PreProcessedItem(trainDataBatch.get(i));
		}
		while(countOfNewWords > thres_countOfNewWords && modifiction > thres_modification)
		{
			//置0
			countOfNewWords = 0;
			modifiction = 0;
			//from entity/aspect to opinion 
			for(String entity : entityMap.keySet())
			{
				int row = entityMap.get(entity);
				for(PreProcessedItem pitem : pitems)
				{
					int tokenId = this.getEntityIdInTokens(pitem.sentence,pitem.words, entity);
					if(tokenId == -1){continue;}
					String opinion = posBasedTemplate.extractFromAspectToOpinion(pitem, tokenId);
					if(opinion == null)
					{
						opinion = syntaxBasedTemplate.extractFromAspectToOpinion(pitem, tokenId);
					}
					if(opinion != null)
					{
						int col = -1;
						if(opinionMap.containsKey(opinion))
						{
							col = opinionMap.get(opinion);
						}else
						{
							col = opinionMap.size();
							opinionMap.put(opinion, col);
							countOfNewWords++;
						}
						Axis axis = new Axis(row, col);
						if(matrix.containsKey(axis))
						{
							matrix.put(axis, matrix.get(axis)+1);
							modifiction ++;
						}else
						{
							matrix.put(axis, 1);
						}
					}
				}
			}
//			for(String opinion : opinionMap.keySet())
//			{
//				int col = opinionMap.get(opinion);
//				for(PreProcessedItem pitem : pitems)
//				{
//					int tokenId = this.getEntityIdInTokens(pitem.sentence,pitem.words, opinion);
//					if(tokenId == -1){continue;}
//					String entity = posBasedTemplate.extractFromOpinionToAspect(pitem, tokenId);
//					if(entity == null)
//					{
//						entity = syntaxBasedTemplate.extractFromOpinionToAspect(pitem, tokenId);
//					}
//					if(entity != null)
//					{
//						int row = -1;
//						if(entityMap.containsKey(entity))
//						{
//							row = entityMap.get(entity);
//						}else
//						{
//							row = entityMap.size();
//							entityMap.put(entity, row);
//							countOfNewWords++;
//						}
//						Axis axis = new Axis(row, col);
//						if(matrix.containsKey(axis))
//						{
//							matrix.put(axis, matrix.get(axis)+1);
//							modifiction ++;
//						}else
//						{
//							matrix.put(axis, 1);
//						}
//					}
//				}
//			}
			break;
		}
		
	}
	private int getEntityIdInTokens(String sentence,List<String> tokens, String entity)
	{
		int n = sentence.length();
		int startIndex = sentence.indexOf(entity);
		int endIndex = startIndex + entity.length();
		if(startIndex == -1)return -1;
		int len = 0;
		for(int i=tokens.size()-1;i>=0;i--)
		{
			if(n - len - 1 >= startIndex && n - len - 1 < endIndex)
			{
				return i;
			}
			len += tokens.get(i).length();
		}
		return -1;
	}
	private void prune()
	{
		
	}
	public void train(List<String> trainDataList)
	{
		Template posBasedTemplate = new PosBasedTemplate();
		Template syntaxBasedTemplate = new SyntaxBasedTemplate();
		int numIterator = (int) Math.ceil(trainDataList.size()*1.0 / batchSize);
		for(int i = 0; i < numIterator;i++)
		{
			int startIndex = i*batchSize;
			int endIndex = Math.min(trainDataList.size(), startIndex+batchSize);
			List<String> trainDataBatch = trainDataList.subList(startIndex, endIndex);
			batchTrain(trainDataBatch,posBasedTemplate,syntaxBasedTemplate);
		}
	}
	public void gridSearch(){}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		GXMatrixGenerator cm  = new GXMatrixGenerator();
//		List<String> tokens = new ArrayList<>();
//		String[] ts = {"我","爱","中国","共产党"};
//		for(String t : ts){tokens.add(t);}
//		int  re = cm.getEntityIdInTokens("我爱中国共产党", tokens, "中国");
//		System.out.println(re);
		GXMatrixGenerator generator  = new GXMatrixGenerator();
		FileUtil util = new FileUtil();
		List<String> trainDataList = util.loadSentence("data/traindata");
		generator.train(trainDataList);
		generator.display();
	}
	/**
	 * 思路：实体之后切开
	 */
}
