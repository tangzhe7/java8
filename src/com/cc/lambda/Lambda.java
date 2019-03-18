package com.cc.lambda;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.rmi.ssl.SslRMIClientSocketFactory;

public class Lambda
{
	
	
	//Question11
	
	interface I
	{
		public static void say()
		{
			System.out.println("this is I");
		}
	}
	interface J
	{
		public static void say()
		{
			System.out.println("this is J");
		}
	}
	
	
	public static class IJ implements I,J
	{
		public static void say()
		{
			System.out.println("this is IJ");
		}
	}
	
	
	//Question9
	private interface Collections2<E> extends Collection<E>
	{
		default void forEachIf(Consumer<E>action,Predicate<E> filter) 
		{
			forEach(item->
			{
				if(filter.test(item))
					action.accept(item);
			});
		}
	}

	public static void cmpStrLength(String s1, String s2) throws Exception
	{
		Runnable r = () -> System.out.println("hello lambda....");
		r.run();
		List<String> list = Collections.emptyList();
		Collections.sort(list, (p1, p2) -> p1.compareTo(p2));
		Listener<String> l = p -> System.out.println(p);
		l.listener("l1");
		Listener2<String, String> l2 = (p, t) -> System.out.println(p + " " + t);
		l2.listener("l2", "l3");
		MathOperation mp = (a, b) -> {
			int i = a + b;
			i++;
			a += i;
			return a;
		};
		System.out.println(mp.operation(1, 2));

		int num = 1;
		Converter<Integer, String> s = (param) -> System.out.println(String.valueOf(param + num));
		s.convert(2);

		// 方法引用
		l = (System.out::println);
		l.listener("method reference");

		/**
		 * use this or supper
		 */
		Greeter greeter = new ExtendsGreeter();
		greeter.greet();

		// 构造器引用
		Thread t = new Thread(Thread::new);
		t.start();
		// 变量作用域
		String str = "test scope";
		repeatMessage(str, Integer.valueOf(1));

		// String name = "dasd";
		// //name conflict
		// Comparator<String> cmp = (name,s2) ->
		// Integer.compare(name.length(),s2.length());

		// test this
		TestThis tt = new TestThis();
		tt.doWork();

		// for-each
		List<String> lists = new ArrayList<>();
		lists.add("s1");
		lists.add("s2");
		lists.add("s3");
		lists.add("s4");
		lists.forEach(System.out::println);

		// default method
		Person4 p = new Person4();
		System.out.println(p.getName());

		// 接口内的静态方法
		StaticInterface.nCopies(10, null);
		Comparator<String> reverseCmp = Comparator.reverseOrder();
		String name = "test name";
		Comparator.comparing(Person::getName);

		// ans
		/**
		 * 1.Arrays.sort方法中比较器代码的线程与调用sort的线程是同一个吗 ans:是同一个,lambda不改变调用线程
		 */
		Integer words[] = { 0, 1, 2, 3, 4, 5, 6 };
		Arrays.sort(words, (first, second) -> Integer.compare(first, second));

		/**
		 * 使用java.io.File类下的listFiles(FileFilter)和isDirectory方法,编写一个返回指定目录下所有子目录的方法.
		 * 使用lambda表达式代替FileFilter对象,再将他改写为一个方法引用
		 */
		String parentDirName = "/Users/caicai/work/space";
		Question2(parentDirName);

		/**
		 * 使用lambda列出目录下所有文件结尾相同的
		 */
		Question3("/Users/caicai/work/space/qingread-vip", ".jar");

		/**
		 * 先按照目录排序,再按照名字排序
		 * 
		 */
		File f = new File("/Users/caicai/work/space/qingread-vip");
		File files[] = f.listFiles();
		Question4(files);

		/**
		 * 编写一个捕获所有异常的uncheck方法
		 */
		Question6();

		/**
		 * 接收俩个Runable实例为参数,返回一个Runable
		 */
		Runnable r7 = Question7(() -> System.out.println("r1"), () -> System.out.println("r2"));
		r7.run();

		Question8();
		
		
		//question11
		IJ.say();
		I.say();
		J.say();
	}

	static class CmpDir
	{
		public boolean cmpare(File dir)
		{
			return dir.isDirectory();
		}
	}

	/**
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public static void Question2(String fileName) throws FileNotFoundException
	{
		File f = new File(fileName);
		File[] children = f.listFiles((file) -> {
			return file.isDirectory();
		});

		System.out.println("use lamdba express list all children dir...");
		for (File str : children)
		{
			System.out.print(" " + str.getName());
		}
		System.out.println();
		CmpDir cmp = new CmpDir();
		children = f.listFiles(cmp::cmpare);
		System.out.println("use method reference list all children dir...");
		for (File str : children)
		{
			System.out.print(" " + str.getName());
		}
		System.out.println();
	}

	public static void Question3(String fileName, String extend) throws FileNotFoundException
	{

		File f = new File(fileName);
		String[] children = f.list((dir, name) -> {
			// Tests if a specified file should be included in a file list.
			// true include
			// false not include
			// 当前父目录和传入的dirName一致即在dirName
			return name.endsWith(extend);
		});
		System.out.println("use lamdba express...");
		for (String str : children)
		{
			System.out.print(" " + str);
		}
		System.out.println();
	}

	public static void Question4(File[] files)
	{
		Arrays.sort(files, (file1, file2) -> {
			if (file1.getParentFile().getName().equals(file2.getParentFile().getName()))
				return file1.getName().compareTo(file2.getName());
			return file1.getParentFile().getName().compareTo(file2.getParentFile().getName());
		});
		System.out.println();
		for (File str : files)
		{
			System.out.print(" " + str.getName());
		}
	}

	interface RunableEx
	{
		void run() throws Exception;
	}

	public static void Question6()
	{
		System.out.println();
		new Thread(uncheck(() -> {
			System.out.println("Zzzzz");
			Thread.sleep(1000);
		})).start();

		// call
		new Thread(uncheck(new Callable<Void>()
		{
			@Override
			public Void call() throws Exception
			{
				System.out.println("Zzzzz....call");
				Thread.sleep(1000);
				return null;
			}

		})).start();
		new Thread(uncheck(() -> {
			System.out.println("Zzzzz....call");
			Thread.sleep(1000);
			return null;
		})

		).start();
	}

	public static Runnable Question7(Runnable r1, Runnable r2)
	{
		return () -> {
			r1.run();
			r2.run();
		};
	}

	static class Test
	{
		public int i;
	}

	public static void Question8()
	{
		System.out.println("");
		System.out.println("");
		System.out.println("Question 8.....");
		String[] names = { "1", "2", "3" };
		List<Runnable> runers = new ArrayList<>();
		for (String name : names)
		{
			//每个lambda都捕获了不同值.
			runers.add(() -> System.out.println(name));
		}
		//下面这行代码没有起作用
		names[0] = String.valueOf("123123213");
		for (Runnable r : runers)
			r.run();
		//以上输出
		//1
		//2
		//3 
		runers.clear();
		// 传统for循环,编译时报错.i不是final变量.
		for (int i = 0; i < names.length; i++)
		{
			final int k = i;
			runers.add(() -> {
				//names+k*sizeof(names[0])
				System.out.println(names[k]);
			});
		}
		System.out.println();
		System.out.println();
		//起作用了
		names[0] = String.valueOf("123...........");
		for (Runnable r : runers)
			r.run();
		
		//为什么会不同,注意,语义不同，注意看下面这个代码
		runers.clear();
		//还是传统的for循环
		for (int i = 0; i < names.length; i++)
		{
			final int k = i;
			//name2为names[k]内的值
			final String name2 = names[k];
			runers.add(() -> {
				System.out.println(name2);
			});
		}
		System.out.println();
		System.out.println();
		//未起作用
		names[0] = String.valueOf("fghjkl");
		for (Runnable r : runers)
			r.run();
	}

	public static Runnable uncheck(Callable<Void> runner)
	{
		return () -> {
			try
			{
				runner.call();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		};
	}

	public static Runnable uncheck(RunableEx runner)
	{
		return () -> {
			try
			{
				runner.run();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		};
	}

	// 接口内的静态方法
	interface StaticInterface<T>
	{
		public static <T> List<T> nCopies(int n, List<T> o)
		{
			if (o == null) return Collections.emptyList();
			List<T> t = new ArrayList<T>();
			t.addAll(o);
			return t;
		}
	}

	// default method
	interface Person2
	{
		long getid();

		default String getName()
		{
			return "this is default method";
		};
		// A default method cannot override a method from java.lang.Object
		// default String toString() {return "default toString";};
	}

	// default method
	interface Person
	{
		long getid();

		default String getName()
		{
			return "this is default method";
		};
	}

	// default method getName
	static class Person4 extends Person3 implements Person2
	{
		@Override
		public long getid()
		{
			return 0;
		}
	}

	static class Person3 implements Person, Person2
	{

		@Override
		public long getid()
		{
			return 0;
		}

		@Override
		public String getName()
		{
			return Person2.super.getName();
		}

	}

	static class TestThis
	{

		public TestThis()
		{

		}

		public void doWork()
		{
			System.out.println();
			Runnable r = () -> System.out.println(this.toString());
			r.run();
		}

		public String toString()
		{
			return "this is " + this.getClass().getName();
		}

	}

	public static void repeatMessage(String text, Integer count) throws InterruptedException
	{
		Runnable r = () -> {
			// text 和 count为捕获变量
			int i = 0;
			while (i < count)
			{
				System.out.println(text);
				i++;
			}
			Thread.yield();
		};
		new Thread(r).start();

		// unsafe
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			new Thread(() -> {
				System.out.print(" " + list.size());
				list.add("123123");
			}).start();
		}

	}

	static class Greeter
	{
		public void greet()
		{
			System.out.println("greet");
		}
	}

	static class ExtendsGreeter extends Greeter
	{
		@Override
		public void greet()
		{
			Thread t = new Thread(super::greet);
			t.start();
		}

	}

	public interface Converter<T1, T2>
	{
		void convert(int i);
	}

	interface MathOperation
	{
		int operation(int a, int b);
	}

	private interface Listener<P>
	{
		void listener(P p);
	}

	private interface Listener2<P, T>
	{
		void listener(P p, T t);
	}
}
