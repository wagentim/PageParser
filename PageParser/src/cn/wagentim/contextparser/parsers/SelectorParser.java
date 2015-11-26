package cn.wagentim.contextparser.parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.xmlunits.Selector;

public class SelectorParser implements IParser
{
	private static final Logger logger = LogManager.getLogger(SelectorParser.class);
	private String siteInfo = StringConstants.EMPTY_STRING;
	private Element parserElement = null;
	private Selector selector = null;
	private final ResultParser resultParser;
	
	public SelectorParser()
	{
		resultParser = new ResultParser();
	}
	
	public Element getElement()
	{
		return parserElement;
	}

	public void setElement(Element element)
	{
		this.parserElement = element;
	}

	public String getSiteInfo()
	{
		return siteInfo;
	}

	public void setSiteInfo(String siteInfo)
	{
		this.siteInfo = siteInfo;
	}

	public void reset()
	{
		siteInfo = StringConstants.EMPTY_STRING;
		parserElement = null;
		selector = null;
	}

	@Override
	public String parser()
	{
		if( siteInfo.isEmpty() )
		{
			logger.error("SelectorParser#parser the current site infomation is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		if( Validator.isNull(parserElement) )
		{
			logger.error(siteInfo + "SelectorParser#parser the parser element is invalid!");
			return StringConstants.EMPTY_STRING;
		}

		if( Validator.isNull(selector) )
		{
			logger.error(siteInfo + "SelectorParser#parser the selector object is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		if( Validator.isNull(resultParser) )
		{
			logger.error(siteInfo + "SelectorParser#parser the result parser is invalid!");
			return StringConstants.EMPTY_STRING;
		}

		final String key = selector.getKey();

		if( key.isEmpty() )
		{
			logger.error(siteInfo + "SelectorParser#parser the key definition is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		Element	selectedElement = parserElement.select(key).first();
		
		if( Validator.isNull(selectedElement) )
		{
			logger.error(siteInfo + "SelectorParser#parser No Element is found with the given select key");
			return StringConstants.EMPTY_STRING;
		}
		
		IClassParser parser = null;
		final String className = selector.getParser();
		
		if( !className.isEmpty() )
		{
			try
			{
				parser = (IClassParser)Class.forName(className).newInstance();
				parser.setParserElement(selectedElement);
				
			}
			catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e1)
			{
				parser = null;
			}
		}

		if( null != parser )
		{
			return parser.parser();
		}

		final String resultDef = selector.getResult();
		
		resultParser.setParserElement(selectedElement);
		resultParser.setResultDef(resultDef);
		resultParser.setSiteInfo(siteInfo);
		
		return resultParser.parser();
	}

	public Selector getSelector()
	{
		return selector;
	}

	public void setSelector(Selector selector)
	{
		this.selector = selector;
	}
	
	

}
