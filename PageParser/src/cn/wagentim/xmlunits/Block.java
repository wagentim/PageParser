package cn.wagentim.xmlunits;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author bihu8398
 *
 */
@XmlRootElement
public class Block
{
	private String key;
	private List<Selector> selector;
	private String clazz;
	private String def;
	private String value;
	private String other;
	
	@XmlElement
	public void setKey(String key)
	{
		this.key = key;
	}
	
	@XmlElement
	public void setSelector(List<Selector> selector)
	{
		this.selector = selector;
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	public List<Selector> getSelector()
	{
		return this.selector;
	}

	public String getClazz()
	{
		return clazz;
	}

	@XmlAttribute
	public void setClazz(String clazz)
	{
		this.clazz = clazz;
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

	public String getValue()
	{
		return value;
	}

	@XmlElement
	public void setValue(String value)
	{
		this.value = value;
	}

	public String getOther()
	{
		return other;
	}

	@XmlElement
	public void setOther(String other)
	{
		this.other = other;
	}
}
