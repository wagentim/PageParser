package cn.wagentim.contentparser;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.wagentim.basicutils.FileHelper;
import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.connection.GetPageContent;
import cn.wagentim.contentparser.saver.IProduct;
import cn.wagentim.contentparser.saver.ISaver;
import cn.wagentim.contentparser.saver.ObjectDBSaver;
import cn.wagentim.contextparser.parsers.BlockParser;
import cn.wagentim.xmlunits.Block;
import cn.wagentim.xmlunits.Site;

public class Runner implements IHTMLConstants
{
	private static final Logger logger = LogManager.getLogger(Runner.class);
	private static final String[] xmlFiles = new String[]{"dazhe.xml"};
	private static final boolean readFromFile = false;
	private static final String IN_FILE = "c://temp//temp.txt";
	private static final String OUT_FILE = "c://temp//result.txt";
	
	private final XMLLoader loader;
	private final BlockParser blockParser;
	private FileHelper fh = null; 
	private final ISaver saver;
	
	public Runner()
	{
		loader = new XMLLoader();
		blockParser = new BlockParser();
		saver = new ObjectDBSaver();
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
		
		logger.info("Runner#handleFile handle Site [" + site.getName() + "]");
		
		if( !readFromFile )
		{
			processSite(site, new GetPageContent());
		}
		else
		{
			if( null == fh )
			{
				fh = new FileHelper();
			}
			
			String content = fh.readTextFile(IN_FILE);
			
			if( content.isEmpty() )
			{
				logger.error("Runner#handleFile load html content from file is failed! Empty Data!");
				return;
			}
			
			parserPage(content, null, site);
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
		
		List<IProduct> results = new ArrayList<IProduct>();
		
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
			
			Elements elements = doc.select(key);
			
			if( elements.isEmpty() )
			{
				logger.warn(site.getName() + " : " + "Runner#parserPage cannot find any elements with the block key: " + key);
				continue;
			}
			
			for(int j = 0; j < elements.size(); j++)
			{
				blockParser.setParserElement(elements.get(j));
				IProduct product = blockParser.parser();
				results.add(product);
			}
		}
		
		writeResultToFile(results);
	}
	
	private void writeResultToFile(List<IProduct> results)
	{
		if( Validator.isNull(results) || results.size() <= 0 )
		{
			logger.error("Runner#writeResultToFile the results that need to write to file is empty!");
			return;
		}
		
		StringBuffer sb = new StringBuffer();
		
		for( int i = 0; i < results.size(); i++ )
		{
			IProduct p = results.get(i);
			
			sb.append(p.getItemId());
			sb.append(StringConstants.NEWLINE);
			sb.append(p.getIntroduction());
			sb.append(StringConstants.NEWLINE);
			sb.append(p.getSite());
			sb.append(StringConstants.NEWLINE);
			sb.append(p.getImageLink());
			sb.append(StringConstants.NEWLINE);
			sb.append(p.getLink());
			sb.append(StringConstants.NEWLINE);
			sb.append(StringConstants.NEWLINE);
		}
		
		if( null == fh )
		{
			fh = new FileHelper();
		}
		
		fh.writeToFile(sb.toString(), OUT_FILE);
	}
	
	public static void main(String[] args)
	{
		new Runner().start();
	}
}
