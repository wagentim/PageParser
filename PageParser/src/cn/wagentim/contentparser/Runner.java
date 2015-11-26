package cn.wagentim.contentparser;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.connection.GetPageContent;
import cn.wagentim.contextparser.parsers.BlockParser;
import cn.wagentim.xmlunits.Block;
import cn.wagentim.xmlunits.Site;

public class Runner implements IHTMLConstants
{
	private static final Logger logger = LogManager.getLogger(Runner.class);
	private static final String[] xmlFiles = new String[]{"dazhe.xml"};
	private static final boolean readFromFile = false;
	
	private final XMLLoader loader;
	private final BlockParser blockParser;
	
	public Runner()
	{
		loader = new XMLLoader();
		blockParser = new BlockParser();
	}
	
	public void start()
	{
		if( null == loader )
		{
			logger.error("Runner#start the xml loader is invalid!");
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
			logger.error("Runner#handleFile site infomation is invalid!");
			return;
		}
		
		logger.info("Runner#handleFile handle Site [%1]", site);
		
		if( !readFromFile )
		{
			processSite(site, new GetPageContent());
		}
		else
		{
			
		}
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
			logger.error("Runner#parserPage in xml file defined block object is invalid!");
			return;
		}
		
		List<String> results = new ArrayList<String>();
		
		for( int i = 0; i < blocks.size(); i++ )
		{
			Block block = blocks.get(i);
			
			if( Validator.isNull(block) )
			{
				logger.error(site.getName() + "Runner#parserPage the block [%1] is null!", i);
				continue;
			}
			
			final String key = block.getKey();
			
			if( key.isEmpty() )
			{
				logger.error(site.getName() + "Runner#parserPage No key word is defined in the block [%1]!", i);
				continue;
			}
			
			blockParser.setBlock(block);
			blockParser.setSiteInfo(site.getName());
			blockParser.setParserElement(doc.select(key).first());
			
			String resultOfBlock = blockParser.parser();
			results.add(resultOfBlock);
		}
	}
	
	public static void main(String[] args)
	{
		new Runner().start();
	}
}
