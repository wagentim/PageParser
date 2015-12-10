package cn.wagentim.xmlunits;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cn.wagentim.basicutils.StringConstants;

@XmlRootElement
public class Selector
{	
	private String key = StringConstants.EMPTY_STRING;;
	private String parser = StringConstants.EMPTY_STRING;
	private String result = StringConstants.EMPTY_STRING;
	private String def = StringConstants.EMPTY_STRING;
	
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

	public String getDef()
	{
		return def;
	}

	@XmlAttribute
	public void setDef(String def)
	{
		this.def = def;
	}

}
