package cn.wagentim.contentparser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.wagentim.connection.GetPageContent;

public class Runner
{
	private static final String[] xmlFiles = new String[]{"dazhe.xml"};
	private final XMLLoader loader;
	
	public Runner()
	{
		loader = new XMLLoader();
	}
	
	public void start()
	{
		if( null == loader )
		{
			return;
		}
		
		for( int i = 0; i < xmlFiles.length; i++ )
		{
			handleFile(xmlFiles[i]);
		}
	}

	private void handleFile(String xmlFile)
	{
		Site site = loader.loadSiteDef(xmlFile);
		
		if( null == site)
		{
			return ;
		}
		
		List<String> result = new ArrayList<String>();
		
		processSite(site, new GetPageContent(), result);
	}

	private void processSite(Site site, GetPageContent pageLoader, List<String> result)
	{
		pageLoader.run(site.getLink());
		String content = pageLoader.getPageContent();
		recusiveParserPages(content, pageLoader, site, result);
	}
	
	private void recusiveParserPages(String pageConent, GetPageContent pageLoader, Site site, List<String> result )
	{
		Document doc = Jsoup.parse(pageConent);
		List<Selector> selectors = site.getSelector();
		
		for(int i = 0; i < selectors.size(); i++)
		{
			Selector selector = selectors.get(i);
			IPageParser parser = null;
			try
			{
				parser = (IPageParser) Class.forName(selector.getParser()).newInstance();
			}
			catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
			Elements elements = doc.select(selector.getKey());
			
			if( null != parser && !elements.isEmpty() )
			{
				for( int j = 0; j < elements.size(); j++ )
				{
					parser.parser(elements.get(j));
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Runner().start();
	}
}
