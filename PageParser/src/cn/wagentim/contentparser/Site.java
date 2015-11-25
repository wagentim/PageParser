package cn.wagentim.contentparser;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cn.wagentim.basicutils.StringConstants;

@XmlRootElement
public class Site
{
	private String name = StringConstants.EMPTY_STRING;
	private String link = StringConstants.EMPTY_STRING;
	private int id;
	private List<Block> block;
	
	public String getName()
	{
		return name;
	}
	
	@XmlElement
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getLink()
	{
		return link;
	}
	
	@XmlElement
	public void setLink(String link)
	{
		this.link = link;
	}
	

	@XmlElement
	public void setBlock(List<Block> block)
	{
		this.block = block;
	}
	
	public List<Block> getBlock()
	{
		return block;
	}

	public int getId()
	{
		return id;
	}

	@XmlElement
	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "Site [name=" + name + ", link=" + link + ", id=" + id
				+ ", block=" + block + "]";
	}
}
