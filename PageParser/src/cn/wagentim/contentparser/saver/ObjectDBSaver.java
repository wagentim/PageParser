package cn.wagentim.contentparser.saver;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ObjectDBSaver implements ISaver
{
	private final EntityManager em;

	public ObjectDBSaver()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/product.odb");
		em = emf.createEntityManager();
	}
	
	@Override
	public void save(Product p)
	{
		
	}

	@Override
	public void save(List<Product> list)
	{
		
	}

}
