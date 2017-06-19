package nju.edu.cn.taxonomy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Util.FileUtil;

public class Taxonomy {
	TaxonomyTreeNode taxTree;
	HashMap<String, String[]> keywordsMap;
	/**
	 * 最大字符个数
	 */
	public int maxLen = -1;
	public Taxonomy() {
		// TODO Auto-generated constructor stub
		try {
			constructFromExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		keywordsMap = new HashMap<>();
		loadKeywords(taxTree);
	}
	public void loadKeywords(TaxonomyTreeNode root)
	{
		Queue<TaxonomyTreeNode> queue = new LinkedList<>();
		queue.add(root);
		//最后一层是keywords
		while(!queue.isEmpty())
		{
			TaxonomyTreeNode node = queue.poll();
			if(node.childs != null && node.childs.get(0).type == levelInfo.keyword)
			{
				TaxonomyTreeNode child = node.childs.get(0);
				for(String keyword : child.keywordList)
				{
					if(!keywordsMap.containsKey(keyword))
					{
						keywordsMap.put(keyword, new String[]{node.aspectCategory,node.featureAndPerfomance});
						maxLen = Math.max(maxLen, keyword.length());
					}
				}
				continue;
			}
			for(int i = 0; node.childs!=null && i < node.childs.size();i++)
			{
				queue.add(node.childs.get(i));
			}
		}
	}
	public boolean containsKey(String keyword)
	{
		return keywordsMap.containsKey(keyword);
	}
	public String[] getMapValue(String keyword)
	{
		if(this.containsKey(keyword))
		{
			return keywordsMap.get(keyword);
		}else
			return null;
	}
	public void constructFromExcel() throws IOException
	{
		InputStream is = new FileInputStream(new File("Social keywords list_SVW2.xlsx"));//读取event.xlsx
		XSSFWorkbook readwb = new XSSFWorkbook(is);
		XSSFSheet sheet = readwb.getSheet("Auto Related");//读取event.xlsx的sheet1表
		taxTree = construct(3, sheet.getLastRowNum()-1, -1, sheet);
		TaxonomyExpand taxExpander = new TaxonomyExpand();
		//Map<String, List<String>> aspects = new HashMap<String, List<String>>();
		for(File file : FileUtil.loadFileName("C:\\Users\\qf\\Desktop\\AdMaster\\aspectword"))
		{
			String filename = file.getName();
			
			String absolutePath = file.getAbsolutePath();
			List<String> aspectWords = new ArrayList<>(FileUtil.loadWordSet(absolutePath));
			switch (filename) {
				//1
				
			case "ck.txt":
				
				taxExpander.expandByDic(taxTree, aspectWords, "操控");
				break;
				//2
			case "dl.txt":
				taxExpander.expandByDic(taxTree, aspectWords, "动力");
				break;
				//3
			case "kj.txt":
				taxExpander.expandByDic(taxTree, aspectWords, "空间");
				break;
				//4
			case "ns.txt":
				taxExpander.expandByDic(taxTree, aspectWords, "内饰");
				break;
				//5
			case "safe.txt":
				taxExpander.expandByDic(taxTree, aspectWords, "安全");
				break;
				//6
			case "shfw.txt":
				taxExpander.expandByDic(taxTree, aspectWords, "售后服务");
				break;
				//7
			case "ssx.txt":
				taxExpander.expandByDic(taxTree, aspectWords, "舒适性");
				break;
				//8
			case "wg.txt":
				taxExpander.expandByDic(taxTree, aspectWords, "外观");
				break;
				//9
			case "xjb.txt":
				taxExpander.expandByDic(taxTree, aspectWords, "性价比");
				break;
				//10
			case "yh.txt":
				taxExpander.expandByDic(taxTree, aspectWords, "油耗");
				break;
			default:
				break;
			}
		}
		//taxExpander.expandByDic(taxTree, aspects );
	}
	TaxonomyTreeNode construct(int beginRow,int endRow,int col,XSSFSheet sheet)
	{
		TaxonomyTreeNode root = null;
		if(beginRow == endRow && this.readCell(2, col, sheet).equals("keywords"))
		{
			root = new TaxonomyTreeNode(levelInfo.keyword,this.readCell(beginRow, col, sheet));
			return root;
		}
		if(col >=0)
		{
			root = new TaxonomyTreeNode(levelInfo.category,this.readCell(beginRow, col, sheet));
			root.setAspectCategory(this.readRowLastCell(beginRow, sheet));
		}
		else
		{
			root = new TaxonomyTreeNode(levelInfo.root);
		}
			
		col++;
		int k = beginRow;

		for(int i = beginRow+1; i <= endRow; i++)
		{
			if(!this.readCell(i, col, sheet).equals(""))
			{
				root.childs.add(
							construct(k, i-1, col, sheet
									
											));
				k = i;
			}
		}
		if(k == endRow)
		{
			root.childs.add(construct(k, endRow, col, sheet));
		}
		return root;
	}
	private static String readRowLastCell(int row,XSSFSheet sheet)
	{
		XSSFRow xr = sheet.getRow(row);
		if(xr != null)
		{
			return xr.getCell(xr.getLastCellNum()-1).toString();
		}
		return null;
	}
	public String readCell(int i, int j, XSSFSheet sheet)
	{
		XSSFRow xr = sheet.getRow(i);
		if(xr != null)
		{
			return xr.getCell(j).toString();
		}
		return null;
	}
	public void levelTraverse(TaxonomyTreeNode root)
	{
		traverse(root, 0);
	}
	private void traverse(TaxonomyTreeNode root, int level)
	{
		if(root == null || root.childs == null){return;}
		for(int i = 0; i < root.childs.size();i++)
		{
			TaxonomyTreeNode child = root.childs.get(i);
			for(int j = 0;j<level;j++)
			{
				System.out.print("#");
			}
			System.out.println(child.toString());
			traverse(child, level+1);
		}
	}
	void keywordMapShow()
	{
		for(Entry<String, String[]> entry : keywordsMap.entrySet())
		{
			System.out.print(entry.getKey()+":");
			for(String str : entry.getValue())
			{
				System.out.print(str + "\t");
			}
			System.out.println();
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		InputStream is = new FileInputStream(new File("Social keywords list_SVW2.xlsx"));//读取event.xlsx
//		XSSFWorkbook readwb = new XSSFWorkbook(is);
//		XSSFSheet sheet = readwb.getSheet("Auto Related");//读取event.xlsx的sheet1表
//		System.out.println(Taxonomy.readRowLastCell(6, sheet));
		Taxonomy t = new Taxonomy();
		t.keywordMapShow();
		//t.levelTraverse(t.taxTree);
		
	}

}
