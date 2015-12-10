package cn.wagentim.contentparser.saver;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import cn.wagentim.basicutils.StringConstants;

@Entity
public class Product implements IItem, Serializable, IProduct
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4380857304277245465L;
	
	@Id
	private String itemID = StringConstants.EMPTY_STRING;
	private String introduction = StringConstants.EMPTY_STRING;
	private String site = StringConstants.EMPTY_STRING;
	private String imageLink = StringConstants.EMPTY_STRING;
	private String link = StringConstants.EMPTY_STRING;
	
	public String getItemId()
	{
		return itemID;
	}
	public void setItemId(String id)
	{
		this.itemID = id;
	}
	public String getIntroduction()
	{
		return introduction;
	}
	public void setIntroduction(String introduction)
	{
		this.introduction = introduction;
	}
	public String getSite()
	{
		return site;
	}
	public void setSite(String site)
	{
		this.site = site;
	}
	public String getImageLink()
	{
		return imageLink;
	}
	public void setImageLink(String imageLink)
	{
		this.imageLink = imageLink;
	}

	public String getLink()
	{
		return link;
	}
	public void setLink(String link)
	{
		this.link = link;
	}
	
	@Override
	public String toString()
	{
		return "Product [itemID=" + itemID + ", introduction="
				+ introduction + ", site=" + site + ", imageLink=" + imageLink
				+ ", link=" + link + "]";
	}
}
