package cn.wagentim.contentparser.display;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.contentparser.saver.Product;

public class TodayFilter implements IOutputFilter
{
	private final String data;
	
	public TodayFilter()
	{
		data = ObjectDBDisplayer.formatTime(System.currentTimeMillis());
	}
	
	@Override
	public List<Product> filter(List<Product> input)
	{

		if( Validator.isNull(input) || input.size() <= 0 )
		{
			return Collections.EMPTY_LIST;
		}
		
		List<Product> results = new ArrayList<Product>();

		for( int i = 0; i < input.size(); i++ )
		{
			Product p = input.get(i);
			
			if( data.equalsIgnoreCase(ObjectDBDisplayer.formatTime(p.getInTime())))
			{
				results.add(p);
			}
		}
		
		return results;
	}

}
