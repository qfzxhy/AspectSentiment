package nju.edu.cn.Model;

import java.util.ArrayList;
import java.util.List;

import Util.FileUtil;
import nju.edu.cn.label.LabelItem;
import nju.edu.cn.label.PreProcessedItem;

public class ProductExtractionModel implements Model{

	Trie trie = null;
	public ProductExtractionModel(String modelPath) {
		// TODO Auto-generated constructor stub
		this.loadModel(modelPath);
	}
	@Override
	public void loadModel(String modelPath) {
		// TODO Auto-generated method stub
		List<String> productList = new ArrayList<>();
		FileUtil util = new FileUtil();
		util.loadWord(modelPath, productList);
		trie = new Trie();
		trie = trie.createTrie(productList);
	}

	@Override
	public String predict(PreProcessedItem preprocessedItem, LabelItem labelItem) {
		// TODO Auto-generated method stub
		String productName = trie.searchInSentence(labelItem.sentence, trie);
		//labelItem.setProductName(productName);
		return productName;
	}
	

}
