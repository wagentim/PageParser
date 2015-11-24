package cn.wagentim.contentparser;

import org.jsoup.nodes.Element;

public final class DaZheArticleParser implements IPageParser
{

	@Override
	public void parser(Element element)
	{
		System.out.println(element);
	}
}
