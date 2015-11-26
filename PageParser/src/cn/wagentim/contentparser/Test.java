package cn.wagentim.contentparser;

import cn.wagentim.xmlunits.Site;

public class Test
{
	public static void main(String[] args)
	{
		XMLLoader loader = new XMLLoader();
		Site site = loader.loadSiteDef("dazhe.xml");
		
	}
}
