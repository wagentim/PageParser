package cn.wagentim.decorator;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.contentparser.IHTMLConstants;
import cn.wagentim.xmlunits.Site;

public class WebLinkDecorator implements IDecorator<String>, IHTMLConstants
{
	private Site site = null;

	@Override
	public String decorate(String inputLink)
	{
		if( Validator.isNullOrEmpty(inputLink) )
		{
			return inputLink;
		}
		
		if( inputLink.startsWith(DOUBLE_SLASH) )
		{
			return "http:" + inputLink;
		}
		else if( inputLink.startsWith(SINGLE_SLASH) )
		{
			return site.getHost() + inputLink;
		}
		
		return inputLink;
	}

	@Override
	public void setSite(Site site)
	{
		this.site = site;
	}

}
