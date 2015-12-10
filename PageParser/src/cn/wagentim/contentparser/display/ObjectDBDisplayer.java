package cn.wagentim.contentparser.display;

import javax.persistence.EntityManager;

public class ObjectDBDisplayer implements IDisplayer
{
	private final EntityManager em;
	
	public ObjectDBDisplayer(EntityManager em)
	{
		this.em = em;
	}

	@Override
	public void show()
	{
		if( null != em && em.isOpen() )
		{
		}
	}

}
