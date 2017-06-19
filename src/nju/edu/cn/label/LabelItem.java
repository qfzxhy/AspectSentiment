package nju.edu.cn.label;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author qf
 *
 */

public class LabelItem {
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getAttribute() {
		return Attribute;
	}
	public void setAttribute(String attribute) {
		Attribute = attribute;
	}
	/*
	 * 句子
	 */
	public String sentence = null;
	/**
	 * 品牌
	 */
	String ProductName;
	/**
	 * entity # attribute
	 */
	String AspectCategory;
	String entity;
	/**
	 * entity
	 */
	String EntityExpression;
	
	
	String Attribute;
	
	/**
	 * 观点
	 */
	String OpinionExpression;
	/**
	 * 情感极性
	 */
	byte SentimentPolarity;
	
	public LabelItem(String sentence) {
		// TODO Auto-generated constructor stub
		this.sentence = sentence;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//return aspectWord + "|" + review + "|" + reviewPolarity;
		return 	"sentence:\t"+sentence+"\n"
				+"ProductName:\t"+ProductName+'\n'
				+"AspectCategory:\t"+AspectCategory+"\n"
				+"EntityExpression:\t"+EntityExpression+"\n"
				+"OpinionExpression:\t"+OpinionExpression+"\n"
				+"SentimentPolarity:\t"+SentimentPolarity+"\n";
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getAspectCategory() {
		return AspectCategory;
	}
	public void setAspectCategory(String aspectCategory) {
		AspectCategory = aspectCategory;
	}
	public String getEntityExpression() {
		return EntityExpression;
	}
	public void setEntityExpression(String entityExpression) {
		EntityExpression = entityExpression;
	}
	public String getOpinionExpression() {
		return OpinionExpression;
	}
	public void setOpinionExpression(String opinionExpression) {
		OpinionExpression = opinionExpression;
	}
	public byte getSentimentPolarity() {
		return SentimentPolarity;
	}
	public void setSentimentPolarity(byte sentimentPolarity) {
		SentimentPolarity = sentimentPolarity;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
