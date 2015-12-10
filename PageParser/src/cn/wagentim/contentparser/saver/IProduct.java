package cn.wagentim.contentparser.saver;

public interface IProduct
{
	String getItemId();
	void setItemId(String id);
	
	String getImageLink();
	void setImageLink(String imageLink);
	
	String getIntroduction();
	void setIntroduction(String introduction);
	
	String getSite();
	void setSite(String site);
	
	String getLink();
	void setLink(String link);
}
