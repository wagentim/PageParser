package cn.wagentim.contextparser.parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.contentparser.IHTMLConstants;

/**
 * Parser the Result tag 
 * 
 * @author bihu8398
 *
 */
public class ResultParserString implements IParserString, IHTMLConstants
{
	private static final Logger logger = LogManager.getLogger(ResultParserString.class);
	
	private String siteInfo;
	private String resultDef;
	private Element parserElement;
	private String resultName = StringConstants.EMPTY_STRING;
	
	public String getSiteInfo()
	{
		return siteInfo;
	}

	public void setSiteInfo(String siteInfo)
	{
		this.siteInfo = siteInfo;
	}

	public String getResultDef()
	{
		return resultDef;
	}

	public void setResultDef(String resultDef)
	{
		this.resultDef = resultDef;
	}

	public Element getParserElement()
	{
		return parserElement;
	}

	public void setParserElement(Element parserElement)
	{
		this.parserElement = parserElement;
	}

	@Override
	public String parser()
	{
		if( siteInfo.isEmpty() )
		{
			logger.error("ResultParser#parser the current site infomation is invalid!");
			return StringConstants.EMPTY_STRING;
		}

		if( resultDef.isEmpty() )
		{
			logger.error(siteInfo + " : " + "ResultParser#parser the result definition is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		if( Validator.isNull(parserElement) )
		{
			logger.error(siteInfo + " : " + "ResultParser#parser the parser element is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		String[] tokens = resultDef.split(StringConstants.COLON);
		
		if( null == tokens || tokens.length <= 0 || tokens.length > 2 )
		{
			logger.error(siteInfo + " : " + "ResultParser#parser the result definition is invalid!");
			return StringConstants.EMPTY_STRING;
		}
		
		final String key = tokens[0];
		final String value;
		
		if( tokens.length == 2 )
		{
			value = tokens[1];
		}
		else
		{
			value = StringConstants.EMPTY_STRING;
		}

		// continue to implement other tag or definitions
		if( ATTR.equals(key) && !value.isEmpty() )
		{
			return parserElement.attr(value);
		}
		else if( TEXT.equals(key) )
		{
			return parserElement.text();
		}
		
		return StringConstants.EMPTY_STRING;
	}

	public String getResultName()
	{
		return resultName;
	}

	public void setResultName(String resultName)
	{
		this.resultName = resultName;
	}
	
//	private String decorateResult(final String result)
//	{
//		StringBuffer sb = new StringBuffer();
//		sb.append("[");
//		sb.append(resultName);
//		sb.append("] ");
//		sb.append(result);
//		sb.append("\n");
//		return sb.toString();
//	}

}
