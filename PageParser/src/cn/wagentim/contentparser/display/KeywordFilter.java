package cn.wagentim.contentparser.display;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.contentparser.saver.Product;

public class KeywordFilter implements IOutputFilter
{
	private final String keyword;
	
	public KeywordFilter(String keyword)
	{
		this.keyword = keyword;
	}
	
	@Override
	public List<Product> filter(List<Product> input)
	{
		if( Validator.isNull(input) || input.size() <= 0 )
		{
			return Collections.EMPTY_LIST;
		}
		
		if( Validator.isNullOrEmpty(keyword) )
		{
			return input;
		}
		
		List<Product> results = new ArrayList<Product>();

		for( int i = 0; i < input.size(); i++ )
		{
			Product p = input.get(i);
			
			if( p.getIntroduction().toLowerCase().contains(keyword.toLowerCase().trim()))
			{
				results.add(p);
			}
		}
		
		return results;
	}

}
