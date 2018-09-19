package ShortestPath;

public class ShortestPath {

	public ShortestPath()
	{
        //SETUP 1.
        /*Node start = new Node("Start");
        Node end = new Node("end");

        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node F = new Node("F");
        Node G = new Node("G");

        start.distance = 0;
        start.neighbours.put(A, 2);
        start.neighbours.put(B, 5);

        A.neighbours.put(C, 4);
        A.neighbours.put(D, 1);
        B.neighbours.put(D, 1);
        B.neighbours.put(E, 3);
        B.neighbours.put(F, 2);
        C.neighbours.put(E, 1);
        D.neighbours.put(C, 2);
        E.neighbours.put(G, 2);
        F.neighbours.put(end, 8);
        F.neighbours.put(G, 3);
        G.neighbours.put(end, 4);*/
        //SETUP 1.


        //SETUP 2.
        Node start = new Node("start");
        Node end = new Node("end");
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");

        start.distance = 0;
        start.neighbours.put(A, 1);
        A.neighbours.put(C, 2);
        A.neighbours.put(B, 8);
        C.neighbours.put(D, 2);
        D.neighbours.put(B, 5);
        B.neighbours.put(end, 1);
        //END SETUP 2.

        //make instance of pathfinding-class
        Dijkstra _ds = new Dijkstra();

        System.out.println(_ds.Dijkstra(start, end));

        //DEBUG INFO
        System.out.println("From start the distance to A:" + A.distance);
        System.out.println("From start the distance to B:" + B.distance);
        System.out.println("From start the distance to C:" + C.distance);
        System.out.println("From start the distance to D:" + D.distance);
//        System.out.println("From start the distance to E:" + E.distance);
//        System.out.println("From start the distance to F:" + F.distance);
//        System.out.println("From start the distance to G:" + G.distance);
        System.out.println("From start the distance to end:" + end.distance);
        String path = _ds.makePath(end);
        System.out.println("path: "+path);
	}
}
