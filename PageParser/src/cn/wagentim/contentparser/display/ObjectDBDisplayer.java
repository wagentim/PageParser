package cn.wagentim.contentparser.display;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import cn.wagentim.basicutils.FileHelper;
import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.contentparser.saver.ISQLStatements;
import cn.wagentim.contentparser.saver.Product;

public class ObjectDBDisplayer implements IDisplayer, ISQLStatements
{
	private final EntityManager em;
	private final EntityManagerFactory emf;
	private FileHelper fh = null; 
	private static final String OUT_FILE = "c://temp//result.txt";
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public ObjectDBDisplayer()
	{
		emf = Persistence.createEntityManagerFactory(DB_PRODUCT);
		em = emf.createEntityManager();
		fh = new FileHelper();
	}

	@Override
	public void showAllProducts()
	{
		if( null != em && em.isOpen() )
		{
			TypedQuery<Product> query = em.createQuery(SELECT_ALL_PRODUCT, Product.class);
		    List<Product> results = query.getResultList();
//		    writeResultToFile(results);
		    writeResultToConsole(results);
		}
	}
	
	public void writeResultToConsole(List<Product> results)
	{
		System.out.println(createOutputString(results));
	}
	
	private String createOutputString(List<Product> results)
	{
		if( Validator.isNull(results) || results.size() <= 0 )
		{
			return StringConstants.EMPTY_STRING;
		}
		
		StringBuffer sb = new StringBuffer();
		
		for( int i = 0; i < results.size(); i++ )
		{
			Product p = results.get(i);
			
			sb.append(formatTime(p.getInTime()));
			sb.append(StringConstants.NEWLINE);
//			sb.append(p.getItemId());
//			sb.append(StringConstants.NEWLINE);
			sb.append(p.getIntroduction());
//			sb.append(StringConstants.NEWLINE);
//			sb.append(p.getSite());
//			sb.append(StringConstants.NEWLINE);
//			sb.append(p.getImageLink());
			sb.append(StringConstants.NEWLINE);
			sb.append(p.getLink());
			sb.append(StringConstants.NEWLINE);
			sb.append(StringConstants.NEWLINE);
		}
		
		return sb.toString();
	}

	private String formatTime(long inTime)
	{
		return dateFormat.format(new Date(inTime));
	}

	public void writeResultToFile(List<Product> results)
	{
		final String output = createOutputString(results);
		
		if( null == fh )
		{
			fh = new FileHelper();
		}
		
		fh.writeToFile(output, OUT_FILE);
	}
}
