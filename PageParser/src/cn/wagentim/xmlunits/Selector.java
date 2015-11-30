package cn.wagentim.xmlunits;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cn.wagentim.basicutils.StringConstants;

@XmlRootElement
public class Selector
{	
	private String key = StringConstants.EMPTY_STRING;;
	private String parser = StringConstants.EMPTY_STRING;
	private String result = StringConstants.EMPTY_STRING;
	private String name = StringConstants.EMPTY_STRING;
	
	@XmlElement
	public void setKey(String key)
	{
		this.key = key;
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	@XmlElement
	public void setParser(String parser)
	{
		this.parser = parser;
	}
	
	public String getParser()
	{
		return this.parser;
	}
	
	@XmlElement
	public void setResult(String result)
	{
		this.result = result;
	}
	
	public String getResult()
	{
		return result;
	}

	public String getName()
	{
		return name;
	}

	@XmlElement
	public void setName(String name)
	{
		this.name = name;
	}
}
