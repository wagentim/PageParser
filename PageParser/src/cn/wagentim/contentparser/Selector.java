package cn.wagentim.contentparser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cn.wagentim.basicutils.StringConstants;

@XmlRootElement
public class Selector
{	
	private String key = StringConstants.EMPTY_STRING;;
	private String className = StringConstants.EMPTY_STRING;
	
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
	public void setClassName(String className)
	{
		this.className = className;
	}
	
	public String getClassName()
	{
		return this.className;
	}
}
