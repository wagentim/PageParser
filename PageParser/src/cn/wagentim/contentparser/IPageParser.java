package cn.wagentim.contentparser;
import org.xml.sax.helpers.DefaultHandler;


public interface IPageParser
{
	void parser(String content, Site site);
}
