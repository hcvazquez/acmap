package ontology.alignment.view;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import ontology.alignment.definition.Link;
import ontology.alignment.definition.Node;
import edu.uci.ics.jung.algorithms.importance.MarkovCentrality;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.scoring.BarycenterScorer;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.scoring.DistanceCentralityScorer;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class TestGraphics {

	public static void main(String[] args) {
		// Graph<V, E> where V is the type of the vertices
		// and E is the type of the edges
		Graph<Node, Link> g = new DirectedSparseMultigraph<Node, Link>();
		// Create some OntologyNode objects to use as vertices
		Node n1 = new Node(1, "Nodo");
		Node n2 = new Node(2, "Nodo");
		Node n3 = new Node(3, "Nodo");
		Node n4 = new Node(4, "Nodo");
		Node n5 = new Node(5, "Nodo"); // note n1-n5 declared
														// elsewhere.
		// Add some directed edges along with the vertices to the graph

		Link l0 = new Link(0, "link", n1, n2);
		Link l1 = new Link(1, "link", n2, n3);
		Link l2 = new Link(2, "link", n3, n5);
		Link l3 = new Link(3, "link", n5, n4);
		Link l4 = new Link(4, "link", n4, n3); 
		Link l5 = new Link(5, "link", n3, n1); 
		Link l6 = new Link(6, "link", n2, n5);
																// the

		g.addEdge(l0, l0.getSource(), l0.getDestiny()); // This// method
		g.addEdge(l1, n2, n3);
		g.addEdge(l2, n3, n5);
		g.addEdge(l3, n5, n4); // or we can //use
		g.addEdge(l4, n4, n2); // In a directed // graph the
		g.addEdge(l5, n3, n1); // first node is // the source
		g.addEdge(l6, n2, n5);// and the second // the
		// destination
		// SimpleGraphView sgv = new SimpleGraphView(); //We create our graph in
		// here
		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<Integer, String> layout = new CircleLayout(g);
		layout.setSize(new Dimension(300, 300));

		VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(
				layout);
		vv.setPreferredSize(new Dimension(350, 350));
		// Show vertex and edge labels
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		// Create a graph mouse and add it to the visualization component
		DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		vv.setGraphMouse(gm);
		JFrame frame = new JFrame("Interactive Graph View 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
		
		
		//Metrics
		
		System.out.println("ClosenessCentrality"+"\r\n");
		DistanceCentralityScorer<Node,Link> alg = new ClosenessCentrality(g);
		System.out.println(alg.getVertexScore(n1));
		System.out.println(alg.getVertexScore(n2));
		System.out.println(alg.getVertexScore(n3));
		System.out.println(alg.getVertexScore(n4));
		System.out.println(alg.getVertexScore(n5));
		
		System.out.println();
		System.out.println("BarycenterScorer"+"\r\n");
		DistanceCentralityScorer<Node,Link> alg2 = new BarycenterScorer(g);
		System.out.println(alg2.getVertexScore(n1));
		System.out.println(alg2.getVertexScore(n2));
		System.out.println(alg2.getVertexScore(n3));
		System.out.println(alg2.getVertexScore(n4));
		System.out.println(alg2.getVertexScore(n5));
		
		System.out.println();
		System.out.println("BetweennessCentrality"+"\r\n");
		BetweennessCentrality<Node,Link> alg3 = new BetweennessCentrality(g);
		System.out.println("Vertex");
		System.out.println(alg3.getVertexScore(n1));
		System.out.println(alg3.getVertexScore(n2));
		System.out.println(alg3.getVertexScore(n3));
		System.out.println(alg3.getVertexScore(n4));
		System.out.println(alg3.getVertexScore(n5));
		System.out.println();
		System.out.println("Edge");
		System.out.println(alg3.getEdgeScore(l0));
		System.out.println(alg3.getEdgeScore(l1));
		System.out.println(alg3.getEdgeScore(l2));
		System.out.println(alg3.getEdgeScore(l3));
		System.out.println(alg3.getEdgeScore(l4));
		System.out.println(alg3.getEdgeScore(l5));
		System.out.println(alg3.getEdgeScore(l6));
		
		System.out.println();
		System.out.println("MarkovCentrality"+"\r\n");
		Set<Node> set = new HashSet();
		set.add(n1);set.add(n2);set.add(n3);set.add(n4);set.add(n5);
		MarkovCentrality<Node,Link> alg4 = new MarkovCentrality((DirectedGraph) g, set);
		System.out.println(alg4.getVertexRankScore(n1));
		System.out.println(alg4.getVertexRankScore(n2));
		System.out.println(alg4.getVertexRankScore(n3));
		System.out.println(alg4.getVertexRankScore(n4));
		System.out.println(alg4.getVertexRankScore(n5));		

	}
}
