package cn.wagentim.contextparser.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.xmlunits.Block;
import cn.wagentim.xmlunits.Selector;

/**
 * Attention!! not thread safe. Please use single thread
 * 
 * @author bihu8398
 *
 */
public class BlockParser implements IParser
{
	private static final Logger logger = LogManager.getLogger(BlockParser.class);
	private Block block = null;
	private String siteInfo = StringConstants.EMPTY_STRING;
	private Element parserElement = null;
	private final SelectorParser selectParser;
	
	public BlockParser()
	{
		selectParser = new SelectorParser();
	}
	
	public Block getBlock()
	{
		return block;
	}

	public void setBlock(Block block)
	{
		this.block = block;
	}

	@Override
	public String parser()
	{
		if( siteInfo.isEmpty() )
		{
			logger.error("BlockParser#parser the current site infomation is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		if( Validator.isNull(parserElement) )
		{
			logger.error(siteInfo + "BlockParser#parser the parser element is invalid!");
			return StringConstants.EMPTY_STRING;
		}

		if( Validator.isNull(block) )
		{
			logger.error(siteInfo + "BlockParser#parser the block object is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		if( Validator.isNull(selectParser) )
		{
			logger.error(siteInfo + "BlockParser#parser the seletor parser is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		final List<Selector> selectors = block.getSelector();
		
		if( selectors.isEmpty() )
		{
			logger.error(siteInfo + "BlockParser#parser defined selectors is empty!");
			return StringConstants.EMPTY_STRING;
		}
		
		List<String> results = new ArrayList<String>();
		selectParser.setElement(parserElement);
		selectParser.setSiteInfo(siteInfo);
		
		for(int i = 0; i < selectors.size(); i++)
		{
			selectParser.setSelector(selectors.get(i));
			results.add(selectParser.parser());
		}
		
		final String sResult = processResult(results);
		
		return sResult;
	}

	private String processResult(List<String> results)
	{
		if( results.isEmpty() )
		{
			return StringConstants.EMPTY_STRING;
		}
		
		StringBuffer sb = new StringBuffer();
		
		for( int i = 0; i < results.size(); i++ )
		{
			sb.append(results.get(i));
		}
		
		return sb.toString();
	}

	public String getSiteInfo()
	{
		return siteInfo;
	}

	public void setSiteInfo(String siteInfo)
	{
		this.siteInfo = siteInfo;
	}

	public Element getParserElement()
	{
		return parserElement;
	}

	public void setParserElement(Element parserElement)
	{
		this.parserElement = parserElement;
	}
}
