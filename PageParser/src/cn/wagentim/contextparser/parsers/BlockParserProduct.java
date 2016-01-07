package cn.wagentim.contextparser.parsers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.contentparser.saver.Product;
import cn.wagentim.decorator.IDecorator;
import cn.wagentim.decorator.WebLinkDecorator;
import cn.wagentim.xmlunits.Block;
import cn.wagentim.xmlunits.Selector;
import cn.wagentim.xmlunits.Site;

/**
 * Attention!! not thread safe. Please use single thread
 * 
 * @author bihu8398
 *
 */
public class BlockParserProduct implements IParser<Product>, INameConstants
{
	private static final Logger logger = LogManager.getLogger(BlockParserProduct.class);
	private Block block = null;
	private Site site = null;
	private Element parserElement = null;
	private final SelectorParser selectParser;
	private final IDecorator<String> linkDecorator;
	private String siteInfo = StringConstants.EMPTY_STRING;
	
	public BlockParserProduct()
	{
		selectParser = new SelectorParser();
		linkDecorator = new WebLinkDecorator();
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
	public Product parser()
	{
		if( siteInfo.isEmpty() )
		{
			logger.error("BlockParser#parser the current site infomation is invalid!");
			return null;
		}
		
		if( Validator.isNull(parserElement) )
		{
			logger.error(siteInfo + " : " + "BlockParser#parser the parser element is invalid!");
			return null;
		}

		if( Validator.isNull(block) )
		{
			logger.error(siteInfo + " : " + "BlockParser#parser the block object is invalid!");
			return null;
		}
		
		if( Validator.isNull(selectParser) )
		{
			logger.error(siteInfo + " : " + "BlockParser#parser the seletor parser is invalid!");
			return null;
		}
		
		final List<Selector> selectors = block.getSelector();
		
		if( selectors.isEmpty() )
		{
			logger.error(siteInfo + " : " + "BlockParser#parser defined selectors is empty!");
			return null;
		}
		
		Product prod = new Product();
		selectParser.setElement(parserElement);
		selectParser.setSiteInfo(siteInfo);
		
		for(int i = 0; i < selectors.size(); i++)
		{
			Selector selector = selectors.get(i);
			selectParser.setSelector(selector);
			String result = selectParser.parser();
			assignValue(selector.getDef(), result, prod);
		}
		
		return prod;
	}

	private void assignValue(String def, String result, Product prod)
	{
		if( PRODUCT_NAME_ID.equalsIgnoreCase(def) )
		{
			prod.setItemId(result);
		}
		else if( PRODUCT_NAME_IMAGE.equalsIgnoreCase(def) )
		{
			prod.setImageLink(linkDecorator.decorate(result));
		}
		else if( PRODUCT_NAME_LINK.equalsIgnoreCase(def) )
		{
			prod.setLink(linkDecorator.decorate(result));
		}
		else if( PRODUCT_NAME_SITE.equalsIgnoreCase(def) )
		{
			prod.setSite(result);
		}
		else if( PRODUCT_NAME_TITLE.equalsIgnoreCase(def) )
		{
			prod.setIntroduction(result);
		}
		else if( PRODUCT_NAME_NEW_PRICE.equalsIgnoreCase(def) )
		{
			prod.setNewPrice(result);
		}
		else if( PRODUCT_NAME_OLD_PRICE.equalsIgnoreCase(def) )
		{
			prod.setOldPrice(result);
		}
	}

	public Site getSite()
	{
		return site;
	}

	public void setSite(Site site)
	{
		this.site = site;
		siteInfo = site.getName();
		linkDecorator.setSite(site);
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
