package nju.edu.cn.taxonomy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TaxonomyExpand {
	public void expandByDic(TaxonomyTreeNode root,List<String> aspectWords, String aspectCategory)
	{
		//Iterator<Entry<String, List<String>>> itor = aspects.entrySet().iterator();
		//while(itor.hasNext())
		//{
			//Entry<String, List<String>> entry = itor.next();
			//String aspect = entry.getKey();
			
			//List<String> aspectWords = entry.getValue();
			
			Map<TaxonomyTreeNode, int[]> disOfAspectAndKeywords = new HashMap<TaxonomyTreeNode, int[]>();
			getDisOfAspectWordAndFeaturePerformance(root, aspectCategory, aspectWords, disOfAspectAndKeywords);
			//选择最小距离，添加相应节点
			TaxonomyTreeNode[] taxNodeArrayForAspectWord = new TaxonomyTreeNode[aspectWords.size()];
			for(Entry<TaxonomyTreeNode, int[]> disOfAspectAndKeywordsEntry : disOfAspectAndKeywords.entrySet())
			{
				int[] distances = disOfAspectAndKeywordsEntry.getValue();
				for(int i = 0; i < distances.length;i++)
				{
					if(taxNodeArrayForAspectWord[i] == null)
					{
						taxNodeArrayForAspectWord[i] = disOfAspectAndKeywordsEntry.getKey();
					}else
					{
						if(distances[i] < disOfAspectAndKeywords.get(taxNodeArrayForAspectWord[i])[i])
						{
							taxNodeArrayForAspectWord[i] = disOfAspectAndKeywordsEntry.getKey();
						}
					}
				}
			}
			//添加相应节点，taxNodeArrayForAspectWord, aspectWords
			
			for(int i = 0; i < aspectWords.size();i++)
			{
				//System.out.println(aspectWords.get(i));
				//System.out.println(taxNodeArrayForAspectWord[i]);
				insertApsectWordIntoTaxTree(aspectWords.get(i), taxNodeArrayForAspectWord[i]);
			}
			
		//}
	}
	public void insertApsectWordIntoTaxTree(String aspectWord, TaxonomyTreeNode node)
	{
		if(node == null || node.childs == null || node.childs.size() == 0){return;}
		node.childs.get(0).keywordList.add(aspectWord);
	}
	public void getDisOfAspectWordAndFeaturePerformance(TaxonomyTreeNode root, String aspect, List<String> aspectWords,Map<TaxonomyTreeNode, int[]> disOfAspectAndKeywords)
	{
		if(root.childs == null || root.childs.size() == 0){return;}
		if(root.type == null || (root.aspectCategory == null && root.type != levelInfo.root))return;
		if(root.type != levelInfo.root && !root.aspectCategory.equals(aspect)){return;}
		if(root.aspectCategory != null &&  root.aspectCategory.equals(aspect) && root.childs.get(0).type.equals(levelInfo.keyword))
		{
			
			int[] disOfAspect = new int[aspectWords.size()];
			for(int i =0;i<aspectWords.size();i++)
			{
				disOfAspect[i] = minCharacterDis(root.featureAndPerfomance, aspectWords.get(i));
			}
			if(!disOfAspectAndKeywords.containsKey(root.featureAndPerfomance))
			{
				disOfAspectAndKeywords.put(root, disOfAspect);
			}
			return;
		}
		for(int i = 0; i < root.childs.size();i++)
		{
			getDisOfAspectWordAndFeaturePerformance(root.childs.get(i), aspect, aspectWords, disOfAspectAndKeywords);
		}
	}
	public int minCharacterDis(String s,String t)
	{
		int m = s.length(),n = t.length();
		int[][] dp = new int[m+1][n+1];
		dp[0][0] = 0;
		for(int i = 1;i <= m;i++)
		{
			dp[i][0] = i;
		}
		for(int i = 1;i <= n;i++)
		{
			dp[0][i] = i;
		}
		for(int i = 1;i<=m;i++)
		{
			for(int j =1;j<=n;j++)
			{
				dp[i][j] = Math.min(Math.min(dp[i-1][j]+1, dp[i][j-1]+1), dp[i-1][j-1] + (s.charAt(i-1) == t.charAt(j-1)?0:1));
			}
		}
		if(dp[m][n] == Math.max(s.length(), t.length())){dp[m][n] = Integer.MAX_VALUE;}
		return dp[m][n];
	}
	public int minWordDis(List<String> words1,List<String> words2)
	{
		return 0;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TaxonomyExpand te = new TaxonomyExpand();
		//System.out.println(te.minCharacterDis("abcd", t));
		//System.out.println("发动机de 性能".length());
		Taxonomy tree = new Taxonomy();
		//Map<String, List<String>> aspects = new HashMap<>();
		List<String> l1 = new ArrayList<String>();
		l1.add("发动机动力");
		l1.add("耗油");
		//aspects.put("动力", l1);
		te.expandByDic(tree.taxTree, l1,"动力");
		tree.levelTraverse(tree.taxTree);
	}

}
