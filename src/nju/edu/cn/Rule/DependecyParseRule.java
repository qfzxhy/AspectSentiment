package nju.edu.cn.Rule;

import java.util.ArrayList;
import java.util.List;

import nju.edu.cn.ltpTools.NLPBasicTool;
import nju.edu.cn.ltpTools.Pair;
/**
 * rule1_1: O -> O-dep ->A O-dep = {ATT}
 * rule1_2:	O -> O-dep ->A1 -> A1_dep -> A2
 * rule2_1: A -> A-dep ->O output{A} A-dep = {SBV}
 * rule2_2: A1 -> A1-dep -> A2 -> A2 ->dep -> O output{A1+A2} A1-dep = {ATT} A2-dep = {SBV}
 * rule3:	A1 -> A1-dep -> A2 A1-dep = {COO}
 * 
 * 
 * @author qf
 *
 */
public class DependecyParseRule {
	/**
	 * 
	 * @param posTags 
	 * @param depObjs 
	 * @param depRels
	 * @param i 观点词下标
	 * @return
	 */
	public List<String> getAspectWord(List<String> words,List<String> posTags,List<Integer> depObjs,List<String> depRels,int i)
	{
		List<String> aspectWordList = null;
		aspectWordList = getAspectWordByDepencyRule1(words, posTags, depObjs, depRels, i);
		if(aspectWordList == null || aspectWordList.size() == 0)
		{
			aspectWordList = getAspectWordByDepencyRule2(words, posTags, depObjs, depRels, i);
		}
		return aspectWordList;
	}
	/**
	 * rule : O -> O-dep ->A1{ -> A1_dep -> A2 }
	 * example : 开阔的驾驶位视野
	 * @param words
	 * @param posTags
	 * @param depObjs
	 * @param depRels
	 * @param i
	 * @return
	 */
	private List<String> getAspectWordByDepencyRule1(List<String> words,List<String> posTags,List<Integer> depObjs,List<String> depRels,int i)
	{
		int aspectWordId = -1;
		String aspectWord = "";
		if(depRels.get(i).equals("ATT") && posTags.get(depObjs.get(i)-1).matches("n.*"))
		{
			aspectWordId = depObjs.get(i)-1;
			aspectWord += words.get(aspectWordId);
//			int attWordId = getATTWordId(i,aspectWordId, depObjs, depRels,posTags);
//			if(attWordId != -1){aspectWord = words.get(attWordId) + aspectWord;}
		}
		List<String> aspectWordList = getAspectWordByDepencyRule3(aspectWordId, words, posTags, depObjs, depRels,i);
		if(aspectWord.trim().length() > 0) aspectWordList.add(aspectWord);
		return aspectWordList;
	}
	/**
	 * rule : {A1 -> A1-dep -> }A2 -> A2 ->dep -> O output{A1+A2} A1-dep = {ATT} A2-dep = {SBV}
	 * example : 驾驶位视野开阔             刹车灵敏度高
	 * @param words
	 * @param posTags
	 * @param depObjs
	 * @param depRels
	 * @param i opinWordId
	 * @return
	 */
	private List<String> getAspectWordByDepencyRule2(List<String> words,List<String> posTags,List<Integer> depObjs,List<String> depRels,int i)
	{
		int aspectWordId = -1;
		String aspectWord = "";
		for(int j = 0; j < i; j++)
		{
			if(depObjs.get(j) == i + 1 && depRels.get(j).equals("SBV") && posTags.get(j).matches("n.*"))
			{
				aspectWordId = j;
				aspectWord += words.get(aspectWordId);
//				int attWordId = getATTWordId(i,aspectWordId, depObjs, depRels,posTags);
//				if(attWordId != -1){aspectWord = words.get(attWordId) + aspectWord;}
				break;
			}
		}
		List<String> aspectWordList = getAspectWordByDepencyRule3(aspectWordId, words, posTags, depObjs, depRels,i);
		if(aspectWord.trim().length() > 0) aspectWordList.add(aspectWord);
		return aspectWordList;
	}
	/**
	 * 举例:{外观、车型和线条}都很漂亮
	 * @param firstAspectWordId
	 * @param words
	 * @param posTags
	 * @param depObjs
	 * @param depRels
	 * @return
	 */
	private List<String> getAspectWordByDepencyRule3(int firstAspectWordId,List<String> words,List<String> posTags,List<Integer> depObjs,List<String> depRels,int opinWordId)
	{
		
		List<String> aspectWordList = new ArrayList<String>();
		if(firstAspectWordId < 0 || firstAspectWordId >= words.size())return aspectWordList;
		for(int i = 0; i < depObjs.size(); i++)
		{
			if(depObjs.get(i) == firstAspectWordId + 1 && depRels.get(i).equals("COO") && posTags.get(i).matches("n.*"))
			{
				String aspectWord = words.get(i);
				int attWordId = getATTWordId(opinWordId, i, depObjs, depRels,posTags);
				if(attWordId != -1){aspectWord = words.get(attWordId) + aspectWord;}
				aspectWordList.add(aspectWord);
			}
		}
		return aspectWordList;
	}
	//向前找att word
	private int getATTWordId(int opinWordId,int aspectWordId,List<Integer> depObjs,List<String> depRels,List<String> posTags)
	{
		for(int i = aspectWordId - 1; i >= 0; i--)
		{
			if(i == opinWordId){continue;}
			if(depObjs.get(i) == aspectWordId + 1 && depRels.get(i).equals("ATT") && posTags.get(i).matches("n.*"))
			{
				return i;
			}
		}
		return -1;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sentence = "外观、车型和线条都很漂亮";
		List<String> words = NLPBasicTool.segment(sentence);
		List<String> tags = NLPBasicTool.posTag(words);
		Pair<List<Integer>, List<String>> deResult = NLPBasicTool.dependencyParse(words, tags);
		System.out.println(words);
		System.out.println(tags);
		System.out.println(deResult.getFirst());
		System.out.println(deResult.getSecond());
		DependecyParseRule dr = new DependecyParseRule();
		System.out.println(dr.getAspectWord(words, tags, deResult.first, deResult.second, 7));
	}
	

}

//public static List<String> getAspectWordByDependencyTree(String sentence,String opinWord)
//{
//	List<String> res = new ArrayList<String>();
//	if(sentence.indexOf(opinWord) == -1){return res;}
//	int id = sentence.indexOf(opinWord);
//	int left = id -1, right = id+1;
//	while(left >=0)
//	{
//		if(sentence.charAt(left) == '，' || sentence.charAt(left) == '。')
//		{
//			break;
//		}
//		left--;
//	}
//	while(right < sentence.length())
//	{
//		if(sentence.charAt(right) == '，' || sentence.charAt(right) == '。')
//		{
//			break;
//		}
//		right++;
//	}
//	
//	sentence = sentence.substring(left+1, right);
//	List<String> words = NLPBasicTool.segment(sentence);
//	List<String> tags = NLPBasicTool.posTag(words);
//	Pair<List<Integer>, List<String>> deResult = NLPBasicTool.dependencyParse(words, tags);
////	System.out.println(words);
////	System.out.println(tags);
////	System.out.println(deResult.getFirst());
////	System.out.println(deResult.getSecond());
//	int index = -1;
//	for(int i =words.size()-1;i>=0;i--)
//	{
//		if(opinWord.indexOf(words.get(i)) != -1)
//		{
//			index = i;
//			break;
//		}
//	}
//	//rule
//	for(int i = index - 1;i >= 0; i--)
//	{
//		if(deResult.getFirst().get(i) == index + 1)
//		{
//			if(tags.get(i).matches("n.*")&&words.get(i).length() > 1)
//			{
//				res.add(words.get(i));
//				break;
//			}
//		}
//	}
//	System.out.println(res);
//	return res;
//}
