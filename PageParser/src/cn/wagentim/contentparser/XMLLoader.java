package cn.wagentim.contentparser;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public final class XMLLoader
{
	private static final String DIR = "pages/";
	
	public Site loadSiteDef(final String fileName)
	{
		try
		{
			File file = new File(DIR + fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(Site.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Site site = (Site) jaxbUnmarshaller.unmarshal(file);
			return site;
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			return null;
		}

	}
}
