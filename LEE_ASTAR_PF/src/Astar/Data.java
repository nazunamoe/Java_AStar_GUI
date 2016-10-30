package Astar;

/*
 * 가중치 저장용 데이타형 
 */
public class Data
{
	Point point;
	double g;
	double h;
	Data parent; // 부모 노드 
	
	public Data(Point p, double g, double h, Data parent)
	{
		this.point = p;
		this.g = g;
		this.h = h;
		this.parent = parent;
	}
	
	double f()
	{
		return g + h;
	}
}