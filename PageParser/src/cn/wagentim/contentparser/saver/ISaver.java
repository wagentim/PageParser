package cn.wagentim.contentparser.saver;

import java.util.List;

public interface ISaver
{
	void save(Product p);
	void save(List<Product> list);
}
