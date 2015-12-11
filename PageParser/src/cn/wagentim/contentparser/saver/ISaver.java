package cn.wagentim.contentparser.saver;

import java.util.List;

public interface ISaver extends IDBName
{
	void save(Product p);
	void save(List<Product> list);
	void close();
}
