package implementations;

class MyDLLNode<data>
{
	data data;
	MyDLLNode<data> next;
	MyDLLNode<data> prev;

	MyDLLNode(data argData)
	{
		this.data = argData;
	}
}