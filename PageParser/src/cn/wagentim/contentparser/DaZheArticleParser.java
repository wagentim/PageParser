package cn.wagentim.contentparser;

import org.jsoup.nodes.Element;

import cn.wagentim.contextparser.parsers.IParserString;

public final class DaZheArticleParser implements IParserString
{
	
	private Element parserElement = null;

	public void setParserElement(Element element)
	{
		this.parserElement = element;
	}
	
	@Override
	public String parser()
	{
		System.out.println(parserElement);
		return null;
	}
}
