package ShortestPath;

import Areas.Area;
import Managers.GridBuilder;

public class ShortestPath {

	public ShortestPath()
	{
		// Declare variables
		Node startNode = null;
		Node endNode = null;
		Node leftNode = null;
		Node bottomNode = null;
		int layoutLengthY = 10;
		int layoutLengthX = 8;
		
		
		// Set neighbours
		for(int y = 0; y < layoutLengthY; y++) {
			for(int x = 0; x < layoutLengthX; x++) {
				
				
				
				Area a = areas[x][y - 1];
				
				Node n = a;
				Node n = new Node("x" + x + ",y" + y);
				
				// add left node
				if (x > 0)
					n.neighbours.put(leftNode);
					
				// add bottomNode
				if (x == layout.length - 1)
					n.neighbours.put(bottomNode);
					
				// set the left and bottom node
				leftNode = n;
				if (x == layout.length - 1)
					bottomNode = n;
					
				// set start and end node if found
				if (startX == a.x() && startY = a.y())
					startNode = n;
				if (endX == a.x() && endY = a.y())
					endNode = n;
			}
		}

		// Create instance and print route
		Dijkstra _ds = new Dijkstra();
		System.out.println(_ds.Dijkstra(start, end)); 
	}
}
