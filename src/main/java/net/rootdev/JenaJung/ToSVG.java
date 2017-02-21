package net.rootdev.JenaJung;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

import java.awt.Dimension;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.rootdev.JenaJung.JenaJungGraph;
import net.rootdev.JenaJung.Transformers;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

/**
 * Hello world!
 *
 */
public class ToSVG {

    public static void main(String[] args) throws UnsupportedEncodingException, SVGGraphics2DIOException, IOException, ParserConfigurationException {
        
        //String resource = "http://homepages.cwi.nl/~media/publications/ins.rdf";
        
       // Model model = FileManager.get().loadModel(resource);
        Model model = ModelFactory.createDefaultModel();//FileManager.get().loadModel(resource);
        
        model.read( "file:/C:/benchmarks/101/onto.rdf" );

        Graph<RDFNode, Statement> g = new JenaJungGraph(model);

        Layout<RDFNode, Statement> layout = new FRLayout(g);

        int width = 1400;
        int height = 800;

        layout.setSize(new Dimension(width, height));
        VisualizationImageServer<RDFNode, Statement> viz =
               new VisualizationImageServer(layout, new Dimension(width, height));
        RenderContext context = viz.getRenderContext();
        context.setEdgeLabelTransformer(Transformers.EDGE);
        context.setVertexLabelTransformer(Transformers.NODE);


        viz.setPreferredSize(new Dimension(width, height));
        
        DocumentBuilder dbf = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        DOMImplementation domImpl = dbf.getDOMImplementation();
        
        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        // Create an instance of the SVG Generator.
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        
        viz.paint(svgGenerator); // Hrm, is this right??
        
        Writer out = new FileWriter("graph.svg");
        svgGenerator.stream(out, true);
    }
}
