package nju.edu.cn.FileDeal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

public class FileDealingForWord2vec {
	Segment seg;
	public FileDealingForWord2vec() {
		// TODO Auto-generated constructor stub
		seg = HanLP.newSegment().enableCustomDictionary(false).enableAllNamedEntityRecognize(false);
	}
	public String seg(String sentence)
	{
		List<Term> ls = seg.seg(sentence);
		String s = "";
		for(int i =0;i<ls.size();i++)
		{
			s = s + ls.get(i).word;
			if(i < ls.size() - 1)
			{
				s += " ";
			}
		}
		return s;
	}
	public void firstProcessing() throws IOException
	{
		Set<String> set = new HashSet<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("G:\\Master2D\\NJUAdmaster\\admasterdata\\car_bos.csv"), "GBK"));
		String str = "";
		while((str=br.readLine())!=null)
		{
			String s1 = str.split(",")[8].trim();
			String s2 = str.split(",")[10].trim();
			if(s1.length() < 4)continue;
			int i =0,j = s1.length();
			if(s1.charAt(0)=='"')i++;
			if(s1.charAt(j-1)=='"')j--;
			s1 = s1.substring(i, j);	
			if(s2.length() < 4)continue;
			i =0;j = s2.length();
			if(s2.charAt(0)=='"')i++;
			if(s2.charAt(j-1)=='"')j--;
			s2 = s2.substring(i, j);
			set.add(s1);
			set.add(s2);
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("car_bos_cut_word2vec.txt")));
		for(String s : set)
		{
			bw.write(s+"\n");
		}
		bw.flush();
		bw.close();
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileDealingForWord2vec fd = new FileDealingForWord2vec();
		//System.out.println(fd.seg("我爱中国"));
		try {
			fd.firstProcessing();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
