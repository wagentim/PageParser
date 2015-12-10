package cn.wagentim.contentparser.saver;

import java.util.List;

public interface ISaver
{
	void save(IProduct p);
	void save(List<IProduct> list);
	void close();
}
