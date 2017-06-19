package nju.edu.cn.Rule;

import java.util.List;

public class PosTagRule {
	/**
	 * n + d* + a
	 * @param wordAndTags
	 * @param i ： 评价词在wordAndTags中下标
	 * @return
	 */
	
	public static boolean rule_NDA(String[] wordAndTags,int i)
	{
		if(wordAndTags == null || wordAndTags.length == 0)return false;
		String word = wordAndTags[i].split("/")[0];
		String tag = wordAndTags[i].split("/")[1];
		if(i > 1 && tag.equals("a") && wordAndTags[i-1].split("/")[1].equals("d")&&wordAndTags[i-2].split("/")[1].matches("n.*"))
		{
			return true;
		}
		if(i > 0 && tag.equals("a") && wordAndTags[i-1].split("/")[1].matches("n.*"))
		{
			return true;
		}
		return false;
	}
	public static String getAspectWordByRuleNda(List<String> words,List<String> postags,int i)
	{
		if(i > 2 && postags.get(i).equals("a") && postags.get(i-1).equals("d")&&words.get(i-2).equals("感觉"))
		{
			return words.get(i-3);
		}
		if(i > 1 && postags.get(i).equals("a") && postags.get(i-1).equals("d")&&postags.get(i-2).matches("n.*"))
		{
			return words.get(i-2);
		}
		if(i > 0 && postags.get(i).equals("a") && postags.get(i-1).matches("n.*"))
		{
			return words.get(i-1);
		}
		return "";
	}
	/**
	 * A + 的  + N
	 * @param wordAndTags
	 * @param i
	 * @return
	 */
	public static boolean rule_AN(String[] wordAndTags,int i)
	{
		if(wordAndTags == null || wordAndTags.length == 0)return false;
		String word = wordAndTags[i].split("/")[0];
		String tag = wordAndTags[i].split("/")[1];
		if(i < wordAndTags.length-1 && tag.equals("a") && wordAndTags[i+1].split("/")[0].equals("的")&&wordAndTags[i+2].split("/")[1].matches("n.*"))
		{
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		String[] wordAndTags = new String[]{"美丽/a","的/u","车子/nz"};
		// TODO Auto-generated method stub
		System.out.println(PosTagRule.rule_NDA(wordAndTags ,2));
		System.out.println(PosTagRule.rule_AN(wordAndTags ,0));
	}

}
