package com.cc.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Stream流水线
 * @author caicai
 *
 */
public class Streams
{

	public static void main()
	{
		test();

	}

	public static void test()
	{

		List<String> words = new ArrayList<>();
		words.add("s1");
		words.add("s2");
		words.add("s3");
		words.add("s4");
		words.add("s5");
		//只在count被调用的时候才会执行Stream操作
		long count = words.stream().filter(new Predicate<String>()
		{
			@Override
			public boolean test(String t)
			{
				return t.length() > 1;
			}

		}).count();
		System.out.println(count);
		long count2 = words.stream().filter(w -> w.length() > 1).count();
		System.out.println(count2);
		
		
		long count3 = words.parallelStream().filter(w->w.length()>1).count();
		System.out.println(count3);
		
		
		/**
		 * static array
		 * 
		 */
		String strArrays[] = {"666","5","4","3","2","1"};
		Stream.of(strArrays).forEach(w->System.out.print(" "+w.length()));
		
		//empty stream
		Stream<String> empty = Stream.empty();
		
	}

}
