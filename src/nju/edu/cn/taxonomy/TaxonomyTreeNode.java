package nju.edu.cn.taxonomy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
enum levelInfo
{
	root,aspect,category,subcategory,keyword
}
public class TaxonomyTreeNode {
	levelInfo type;
	String aspectCategory;
	String featureAndPerfomance;
	List<TaxonomyTreeNode> childs;
	List<String> keywordList;
	public TaxonomyTreeNode(levelInfo type)
	{
		this.type = type;
		childs = new ArrayList<TaxonomyTreeNode>();
	}
	public TaxonomyTreeNode(levelInfo type,String val) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.featureAndPerfomance = val;
		if(!this.type.equals(levelInfo.keyword))childs = new ArrayList<TaxonomyTreeNode>();
		else
		{
			setKeywords(val);
		}
		
	}
	public void setAspectCategory(String aspectCategory)
	{
		this.aspectCategory = aspectCategory;
	}
	public void setKeywords(String keywordstr)
	{
		String[] keywords = keywordstr.split(",");
		keywordList = new ArrayList<>();
		for(String keyword : keywords)
		{
			keywordList.add(keyword);
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("<"+"type:"+type+">,");
		sb.append("<"+"aspectCategory:"+aspectCategory+">,");
		sb.append("<"+"featurePerformance:"+featureAndPerfomance+">");
		if(keywordList!=null)
		{
			sb.append("\n<keywords:");
			int i = 0;
			for(String keyword : keywordList)
			{
				sb.append(keyword);
				if(++i < keywordList.size()){sb.append(",");}
			}
			sb.append(">");
		}
		sb.append("}");
		return sb.toString();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//TaxonomyTreeNode root = new TaxonomyTreeNode(levelInfo.root);
		System.out.println(levelInfo.aspect.equals(levelInfo.aspect));
	}

}
