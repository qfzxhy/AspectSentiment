package nju.edu.cn.ltpTools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Pair<T, U>
{
    public T first;
    public U second;

    public Pair(T first, U second)
    {
        this.first = first;
        this.second = second;
    }

    public T getFirst()
    {
        return first;
    }

    public T getKey()
    {
        return first;
    }

    public U getSecond()
    {
        return second;
    }

    public U getValue()
    {
        return second;
    }
    @Override
    public boolean equals(Object obj) {
    	// TODO Auto-generated method stub
    	Pair<T, U> o = (Pair<T, U>)obj;
    	return first.equals(o.first) && second.equals(o.second);
    }
    @Override
    public int hashCode() {
    	// TODO Auto-generated method stub
    	return first.hashCode() + second.hashCode();
    }
    @Override
    public String toString()
    {
        return first + "=" + second;
    }
    public static void main(String[] args) {
//		Map<Pair<String, String>, Integer> map = new HashMap<Pair<String,String>, Integer>();
//		map.put(new Pair<String, String>("11", "222"), 1);
//		System.out.println(map.containsKey(new Pair<String, String>("11", "222")));
//		System.out.println(String.valueOf(true).equals("true"));
    	Set<String> set = new HashSet<>();
    	set.add("1");
    	set.add("2");
    	set.add("3");
    	Iterator<String> itor = set.iterator();
    	while(itor.hasNext())
    	{
    		String s = itor.next();
    		if(s.equals("1"))itor.remove();;
    	}
    	System.out.println(set.size());
	}
}