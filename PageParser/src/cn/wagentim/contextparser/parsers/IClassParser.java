package cn.wagentim.contextparser.parsers;

import org.jsoup.nodes.Element;

public interface IClassParser extends IParserString
{
	void setParserElement(Element element);
}
