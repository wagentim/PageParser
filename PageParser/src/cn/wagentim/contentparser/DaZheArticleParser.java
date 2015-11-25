package cn.wagentim.contentparser;

import org.jsoup.select.Elements;

public final class DaZheArticleParser implements IPageParser
{

	@Override
	public String parser(Elements element)
	{
		System.out.println(element);
		return null;
	}
}
