package cn.wagentim.contentparser.display;

public class ShowData
{
	public static void main(String[] args)
	{
		IDisplayer displayer = new ObjectDBDisplayer();
		displayer.showAllProducts();
	}
}
