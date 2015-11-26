package cn.wagentim.contentparser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wagentim.connection.GetPageContent;
import cn.wagentim.contextparser.parsers.IParser;
import cn.wagentim.xmlunits.Block;
import cn.wagentim.xmlunits.Selector;
import cn.wagentim.xmlunits.Site;

public class Runner implements IHTMLConstants
{
	private static final Logger logger = LogManager.getLogger(Runner.class);
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
		
		processSite(site, new GetPageContent());
	}

	private void processSite(Site site, GetPageContent pageLoader)
	{
		pageLoader.run(site.getLink());
		String content = pageLoader.getPageContent();
		parserPage(content, pageLoader, site);
	}
	
	private void parserPage(String pageConent, GetPageContent pageLoader, Site site)
	{
		Document doc = Jsoup.parse(pageConent);
		List<Block> blocks = site.getBlock();
		
		if( blocks.isEmpty() )
		{
			return;
		}
		
		for( int i = 0; i < blocks.size(); i++ )
		{
			handleBlock(doc, blocks.get(i), pageLoader, new ArrayList<String>());
		}
	}
	
	private void handleBlock(Document doc, Block block, GetPageContent pageLoader, List<String> result)
	{
		String blockKey = block.getKey();
		Elements elements = doc.select(blockKey);
		
		if(elements.isEmpty())
		{
			return;
		}
		
		for(int i = 0; i < elements.size(); i++)
		{
			String blockResult = parserBlock(elements.get(i), block.getSelector());
			
			if( blockResult.isEmpty() )
			{
				continue;
			}
			
			result.add(blockResult);
		}
		
		// this block will write the result to the file
		try
		{
			Files.write(Paths.get("c://temp/result.txt"), result, Charset.forName("utf8"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String parserBlock(Element element, List<Selector> selector)
	{
		if(selector.isEmpty())
		{
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < selector.size(); i++)
		{
			String selectResult = handleSelector(element, selector.get(i));
			
			if( selector.isEmpty() )
			{
				continue;
			}
			
			sb.append(selectResult);
			sb.append(" : ");
		}
		
		return sb.toString();
	}

	public static void main(String[] args)
	{
		new Runner().start();
	}
}
