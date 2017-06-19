package nju.edu.cn.Rule;

import java.util.HashMap;
import java.util.Map;

import nju.edu.cn.ltpTools.Pair;

public class MutualInformation {
//	private HashMap<String, Map<Pair<String, String>, Integer>> mutualInfoMap;
	private HashMap<String, Integer> map1;
	private HashMap<Pair<String, String>, Integer> map2;
	public MutualInformation() {
		// TODO Auto-generated constructor stub
		map1 = new HashMap<>();
		map2 = new HashMap<>();
	}
	public boolean containsStringKey(String key){return map1.containsKey(key);}
//	public boolean containsStringKey(String key){return map1.containsKey(key);}
	public int getStringKeyValue(String key)
	{
		if(!map1.containsKey(key))return 0;
		return map1.get(key);
	}
	public int getPairKeyValue(Pair<String, String> key)
	{
		if(map2.containsKey(key))
		return map2.get(key);
		else
			return 0;
	}
	public void putString(String key)
	{
		if(!map1.containsKey(key)){map1.put(key, 0);}
	}
	public void putPair(Pair<String, String> key)
	{
		if(!map2.containsKey(key)){map2.put(key, 0);}
	}
	public void addString(String key)
	{
		if(!map1.containsKey(key)){map1.put(key, 1);}
		else map1.put(key, map1.get(key) + 1);
	}
	public void addPair(Pair<String, String> key)
	{
		if(!map2.containsKey(key))
		{
			map2.put(key, 1);
		}else
		{
			map2.put(key, map2.get(key)+1);
		}
	}
	public float getMutualInfoValue(int N,Pair<String, String> key)
	{
		if(!map2.containsKey(key)){return 0.0f;}
		int A = map2.get(key);
		int B = map1.get(key.first);
		int C = map1.get(key.second);
		//System.out.println(B+","+C);
		return (float) (A * Math.log((2 * A * 1.0) / B));
	}
//	public boolean containsKey(String word)
//	{
//		return mutualInfoMap.containsKey(word);
//	}
//	public void add(Pair<String, String> obj)
//	{
//		String key = obj.first;
//		if(!containsKey(key))
//		{
//			Map<Pair<String, String>, Integer> map = new HashMap<>();
//			mutualInfoMap.put(key, map);
//		}
//		if(!mutualInfoMap.get(key).containsKey(obj))
//		{
//			mutualInfoMap.get(key).put(obj, 1);
//		}else
//			mutualInfoMap.get(key).put(obj, mutualInfoMap.get(key).get(obj)+1);
//	}
//	public float getMultualInfoValue(int N, String firstWord, String secondWord)
//	{
//		Pair<String, String> obj = new Pair<String, String>(firstWord, secondWord);
//		if(!mutualInfoMap.containsKey(firstWord) || !mutualInfoMap.get(firstWord).containsKey(obj)){return 0.0f;}
//		float val = 0.0f;
//		int A = mutualInfoMap.get(firstWord).get(obj);
//		int B = 0;
//		for(){}
////		int B = mutualInfoMap.get(firstWord).size();
////		int C = mutualInfoMap.get(secondWord)
//		return 0.0;
//	}
//	String A;
//	String B;
//	private int numANotB;
//	private int numBNotA;
//	private int numAAndB;
//	private int numNotANotB;
//	public MutualInformation() {
//		// TODO Auto-generated constructor stub
//		numANotB = 0;
//		numBNotA = 0;
//		numAAndB = 0;
//		numNotANotB = 0;
//	}
//	public void addA(){numANotB++;}
//	public void addB(){numBNotA++;}
//	public void addAB(){numAAndB++;}
//	public void addNotAB(){numNotANotB++;}
//	public float getMI(int N)
//	{
//		//int N = numANotB + numBNotA + numAAndB + numNotANotB;
//		return (float) (numAAndB * Math.log((N * numAAndB)/((numAAndB + numANotB) * (numAAndB + numBNotA))));
//	}
//	@Override
//	public int hashCode() {
//		// TODO Auto-generated method stub
//		return A.hashCode() + B.hashCode();
//	}
//	@Override
//	public boolean equals(Object obj) {
//		// TODO Auto-generated method stub
//		return super.equals(obj);
//	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
