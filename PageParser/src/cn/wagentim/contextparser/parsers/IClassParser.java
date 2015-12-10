package cn.wagentim.contextparser.parsers;

import org.jsoup.nodes.Element;

public interface IClassParser extends ProductParser
{
	void setParserElement(Element element);
}
