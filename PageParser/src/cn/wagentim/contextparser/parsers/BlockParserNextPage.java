package cn.wagentim.contextparser.parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.xmlunits.Block;

public class BlockParserNextPage implements IParser<String>
{
	private static final Logger logger = LogManager.getLogger(BlockParserNextPage.class);
	
	private static final int DEFAULT_CURRENT_PAGE = 1;
	private Block nextPageBlock;
	private final SelectorParser selectParser;
	private String siteInfo = StringConstants.EMPTY_STRING;
	private String pageContent;
	private int currentPage = DEFAULT_CURRENT_PAGE;
	private int pageLimitation = DEFAULT_CURRENT_PAGE;
	
	public BlockParserNextPage()
	{
		selectParser = new SelectorParser();
	}
	
	@Override
	public String parser()
	{
		if( siteInfo.isEmpty() )
		{
			logger.error("BlockParser#parser the current site infomation is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		if( Validator.isNull(nextPageBlock) )
		{
			logger.error(siteInfo + " : " + "BlockParserNextPage#parser the block object is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		if( Validator.isNull(selectParser) )
		{
			logger.error(siteInfo + " : " + "BlockParserNextPage#parser the seletor parser is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		if( Validator.isNullOrEmpty(pageContent) )
		{
			logger.error(siteInfo + " : " + "BlockParserNextPage#parser the page content is empty!");
			return StringConstants.EMPTY_STRING;
		}
		
		String key = nextPageBlock.getKey();
		String result = StringConstants.EMPTY_STRING;
		currentPage++;
		
		// using the fix link
		if( INameConstants.BLOCK_FIX.equalsIgnoreCase(key) )
		{
			result = nextPageBlock.getValue() + currentPage;
		}
		else if( INameConstants.BLOCK_IGNORE.equalsIgnoreCase(key) )
		{
			
		}
		else	// using the selector to get the next page link
		{
			Document doc = Jsoup.parse(pageContent);
			selectParser.setElement(doc.select(nextPageBlock.getKey()).first());
			selectParser.setSiteInfo(siteInfo);
			selectParser.setSelector(nextPageBlock.getSelector().get(0));
			result = selectParser.parser();
		}
		
		return result;
	}

	public void setBlock(Block block)
	{
		if( null == block )
		{
			return;
		}
		
		this.nextPageBlock = block;
		String pageLimit = nextPageBlock.getOther();
		if( !Validator.isNullOrEmpty(pageLimit) )
		{
			this.pageLimitation = Integer.parseInt(pageLimit);
		}
	}

	public String getPageContent()
	{
		return pageContent;
	}

	public void setPageContent(String pageContent)
	{
		this.pageContent = pageContent;
	}
	
	public String getSiteInfo()
	{
		return siteInfo;
	}

	public void setSiteInfo(String siteInfo)
	{
		this.siteInfo = siteInfo;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}
	
	public void finish()
	{
		this.currentPage = DEFAULT_CURRENT_PAGE;
	}

	public int getPageLimitation()
	{
		return pageLimitation;
	}
}
