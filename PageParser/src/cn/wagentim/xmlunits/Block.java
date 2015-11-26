package cn.wagentim.xmlunits;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Block is a 
 * 
 * @author bihu8398
 *
 */
@XmlRootElement
public class Block
{
	private String key;
	private List<Selector> selector;
	
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
}
