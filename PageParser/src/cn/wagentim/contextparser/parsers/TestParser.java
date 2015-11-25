package cn.wagentim.contextparser.parsers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cn.wagentim.basicutils.FileHelper;

public class TestParser
{
	public static void main(String[] args)
	{
		FileHelper fHelper = new FileHelper();
		String content = fHelper.readTextFile("c://temp//temp.txt");
		Document d = Jsoup.parse(content);
		Element e = d.select("article").first();
		String result = e.select("div.entry a img").first().attr("alt");
		List<String> r = new ArrayList<String>();
		r.add(result);
		try
		{
			Files.write(Paths.get("c://temp//result.txt"), r, Charset.forName("utf8"));
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
