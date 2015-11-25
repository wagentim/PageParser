package cn.wagentim.contentparser;

import org.jsoup.select.Elements;


public interface IPageParser
{
	String parser(Elements element);
}
