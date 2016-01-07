package cn.wagentim.contentparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wagentim.basicutils.FileHelper;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.connection.GetPageContent;
import cn.wagentim.contentparser.saver.FileSaver;
import cn.wagentim.contentparser.saver.ISaver;
import cn.wagentim.contentparser.saver.ObjectDBSaver;
import cn.wagentim.contentparser.saver.Product;
import cn.wagentim.contextparser.parsers.BlockParserNextPage;
import cn.wagentim.contextparser.parsers.BlockParserProduct;
import cn.wagentim.contextparser.parsers.INameConstants;
import cn.wagentim.xmlunits.Block;
import cn.wagentim.xmlunits.Site;

public class Runner implements IHTMLConstants
{
	private static final Logger logger = LogManager.getLogger(Runner.class);
	private static final String[] xmlFiles = new String[]{
		"dazhe.xml",
		"babymarkt.xml"
	};
	private static final boolean saveToFile = false;
	private static final String FILE_IN = "c://temp//temp.txt";
	private static final String FILE_OUT = "c://temp//out.txt";
	
	private final XMLLoader loader;
	private FileHelper fh = null; 
	private final ISaver saver;
	private final Map<String, Block> blockParsers;
	private final BlockParserProduct productParser;
	private final BlockParserNextPage nextPageParser;
	
	public Runner()
	{
		loader = new XMLLoader();
		if( saveToFile )
		{
			saver = new FileSaver(FILE_OUT);
		}
		else
		{
			saver = new ObjectDBSaver();
		}
		blockParsers = new HashMap<String, Block>(5);
		productParser = new BlockParserProduct();
		nextPageParser = new BlockParserNextPage();
	}
	
	public void start()
	{
		if( null == loader )
		{
			logger.error("Runner#start the xml loader is invalid!");
			return;
		}
		
		// handle all defined XML files
		for( int i = 0; i < xmlFiles.length; i++ )
		{
			handleFile(xmlFiles[i]);
		}
	}
	
	private void handleFile(String xmlFile)
	{
		// load Site instance
		Site site = loader.loadSiteDef(xmlFile);
		
		if( null == site)
		{
			logger.error("Runner#handleFile site infomation is invalid!");
			return;
		}
		
		logger.info("Runner#handleFile handle Site [" + site.getName() + "]");
		
		final String content;
		final GetPageContent loader = new GetPageContent();
		
		if( !saveToFile )
		{
			loader.run(site.getLink());
			content = loader.getPageContent();
		}
		else
		{
			if( null == fh )
			{
				fh = new FileHelper();
			}
			
			content = fh.readTextFile(FILE_IN);
			
			if( content.isEmpty() )
			{
				logger.error("Runner#handleFile load html content from file is failed! Empty Data!");
				return;
			}
		}
		
		parserPage(content, loader, site);
	}
	
	private void parserProduct(String pageContent, List<Product> products)
	{
		Document doc = Jsoup.parse(pageContent);
		Elements eles = doc.select(productParser.getBlock().getKey());
		
		if( eles.isEmpty() )
		{
			logger.error( "Runner#parserBlock the block [%1] cannot find elements", productParser.getBlock().getKey());
			return;
		}
		
		Iterator<Element> it = eles.iterator();
		
		while(it.hasNext())
		{
			productParser.setParserElement(it.next());
			products.add(productParser.parser());
		}
	}
	
	public boolean shouldStopParser()
	{
		return nextPageParser.getCurrentPage() > nextPageParser.getPageLimitation();
	}
	
	private void parserPage(String pageContent, GetPageContent pageLoader, Site site)
	{
		List<Block> blocks = site.getBlock();
		
		if( blocks.isEmpty() )
		{
			logger.error("Runner#parserPage in xml file defined block object is invalid!");
			return;
		}

		preProcessBlocks(blocks);
		
		productParser.setSite(site);
		productParser.setBlock(blockParsers.get(INameConstants.BLOCK_PRODUCT));
		
		nextPageParser.setSiteInfo(site.getName());
		nextPageParser.setBlock(blockParsers.get(INameConstants.BLOCK_NEXT_PAGE));
		
		List<Product> results = new ArrayList<Product>();
		
		while(!shouldStopParser())
		{
			System.out.println("################# " + site.getName() + " Current Page " + nextPageParser.getCurrentPage() + " #################");
			
			parserProduct(pageContent, results);
			nextPageParser.setPageContent(pageContent);
			String nextPage = nextPageParser.parser();
			pageLoader.run(nextPage);
			pageContent = pageLoader.getPageContent();
		}
		
		writeResult(results);
		
		nextPageParser.finish();
		
		System.out.println("################# " + site.getName() + " Process Finished #################");
	}

	private void preProcessBlocks(List<Block> blocks)
	{
		// before the start we need to clear the 
		blockParsers.clear();
		
		Iterator<Block> it = blocks.iterator();
		
		while(it.hasNext())
		{
			Block block = it.next();
			
			if( null == block )
			{
				continue;
			}
			
			blockParsers.put(block.getDef(), block);
		}
	}

	private void writeResult(List<Product> results)
	{
		if( Validator.isNull(results) || results.size() <= 0 )
		{
			logger.error("Runner#writeResultToDB the results that need to write to file is empty!");
			return;
		}
		saver.save(results);
	}
	
	public static void main(String[] args)
	{
		new Runner().start();
	}
}
