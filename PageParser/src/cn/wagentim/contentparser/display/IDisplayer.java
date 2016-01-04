package cn.wagentim.contentparser.display;

import cn.wagentim.contentparser.saver.IDBName;

public interface IDisplayer extends IDBName
{
	void showAllProducts();
	void showAllProductsForToday();
	void addFilter(IOutputFilter todayFilter);
	void removeFilter(Class<IOutputFilter> filter);
}
