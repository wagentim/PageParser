package cn.wagentim.contentparser.display;

import java.util.List;

import cn.wagentim.contentparser.saver.Product;

public interface IOutputFilter
{
	List<Product> filter(List<Product> results);
}
