package cn.wagentim.contentparser.saver;

import java.util.List;

import cn.wagentim.basicutils.FileHelper;
import cn.wagentim.basicutils.StringConstants;

public class FileSaver implements ISaver
{
	private final String file;
	private final FileHelper fileHelper;
	
	public FileSaver(final String file)
	{
		this.file = file;
		fileHelper = new FileHelper();
	}

	@Override
	public void save(Product p)
	{
		
	}

	@Override
	public void save(List<Product> results)
	{
		if( results.isEmpty() )
		{
			return;
		}
		
		StringBuffer sb = new StringBuffer();
		
		for( int i = 0; i < results.size(); i++ )
		{
			Product p = results.get(i);
			
			sb.append(p.getItemId());
			sb.append(StringConstants.NEWLINE);
			sb.append(p.getIntroduction());
			sb.append(StringConstants.NEWLINE);
			sb.append(p.getNewPrice());
			sb.append(StringConstants.NEWLINE);
			sb.append(p.getOldPrice());
			sb.append(StringConstants.NEWLINE);
//			sb.append(p.getSite());
//			sb.append(StringConstants.NEWLINE);
			sb.append(p.getImageLink());
			sb.append(StringConstants.NEWLINE);
			sb.append(p.getLink());
			sb.append(StringConstants.NEWLINE);
			sb.append(StringConstants.NEWLINE);
		}
		
		fileHelper.writeToFile(sb.toString(), file);
	}

	@Override
	public void close()
	{
		
	}
}
