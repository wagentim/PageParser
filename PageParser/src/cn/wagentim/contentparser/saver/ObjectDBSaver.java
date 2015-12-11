package cn.wagentim.contentparser.saver;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ObjectDBSaver implements ISaver
{
	private final EntityManager em;
	private final EntityManagerFactory emf;

	public ObjectDBSaver()
	{
		emf = Persistence.createEntityManagerFactory(DB_PRODUCT);
		em = emf.createEntityManager();
	}
	
	@Override
	public void save(Product p)
	{
		if( null == p )
		{
			return;
		}
		
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
	}

	@Override
	public void save(List<Product> list)
	{
		if( list.isEmpty() )
		{
			return;
		}
		
		em.getTransaction().begin();
		
		for(int i = 0; i < list.size(); i++ )
		{
			Product prod = list.get(i);
			
			if( null != prod && null == em.find(Product.class, prod))
			{
				em.persist(list.get(i));
			}
		}
		
		em.getTransaction().commit();
	}

	@Override
	public void close()
	{
		if( null != em )
		{
			em.close();
		}
		
		if( null != emf )
		{
			emf.close();
		}
	}
}
