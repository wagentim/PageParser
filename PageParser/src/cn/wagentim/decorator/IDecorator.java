package cn.wagentim.decorator;

import cn.wagentim.xmlunits.Site;

public interface IDecorator<T>
{
	T decorate(T t);
	void setSite(Site site);
}
