import java.io.IOException;
import org.apache.giraph.Algorithm;
import org.apache.giraph.conf.LongConfOption;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * Demonstrates the basic Pregel shortest paths implementation.
 */

@Algorithm(
        name = "Shortest paths",
        description = "Finds all shortest paths from a selected vertex"
)

public class SimpleShortestPathsComputationDetails extends BasicComputation<
        LongWritable, Text, FloatWritable, Text> {

    private static long startVertexId = 2L;

	/*
	public static void main(String[] args) {
		startVertexId = Long.valueOf(args[0]);
	}
	*/
    /** The shortest paths id */
    public static final LongConfOption SOURCE_ID =
            new LongConfOption("SimpleShortestPathsVertexDetails.sourceId", startVertexId,
                    "The shortest paths id");

    /** Class logger */
    private static final Logger LOG =
            Logger.getLogger(SimpleShortestPathsComputationDetails.class);

    /**
     * Is this vertex the source id?
     *
     * @param vertex Vertex
     * @return True if the source id
     */
    private boolean isSource(Vertex<LongWritable, ?, ?> vertex) {
        return vertex.getId().get() == SOURCE_ID.get(getConf());
    }

    @Override
    public void compute(
            Vertex<LongWritable, Text, FloatWritable> vertex,
            Iterable<Text> messages) throws IOException {

        String[] tokens;
        String[] tokk;
        String chemin=new String();
        double minDist;

        // StringBuilder result = new StringBuilder();
        if (getSuperstep() == 0) {
            vertex.setValue(new Text(String.valueOf(Double.MAX_VALUE)+"|"+startVertexId));
        }

        minDist = isSource(vertex) ? 0d : Double.MAX_VALUE;

        for (Text message : messages) {
            tokk= message.toString().split("\\|");

            if (minDist>Double.parseDouble(tokk[0])) {
                minDist = Double.parseDouble(tokk[0]);
                chemin = new String(tokk[1]);
            }
        }
        if ((getSuperstep() == 0) && isSource(vertex)) chemin=new String(""+startVertexId);


        LOG.setLevel((Level) Level.DEBUG);
        //if (LOG.isDebugEnabled()) {
        LOG.debug("#########Vertex " + vertex.getId() + " got minDist = " + minDist +
                " vertex value = " + vertex.getValue().toString()+"@@@"+getSuperstep()+"@@@"+chemin);
        //}

        tokens=vertex.getValue().toString().split("\\|");
        if (minDist < Double.parseDouble(tokens[0])) {

            vertex.setValue(new Text(minDist+"|"+chemin));
            for (Edge<LongWritable, FloatWritable> edge : vertex.getEdges()) {
                double distance = minDist + edge.getValue().get();
                //if (LOG.isDebugEnabled()) {
                LOG.debug("||||||||||||||| Vertex " + vertex.getId() + " sent to " +
                        edge.getTargetVertexId() + " = " + distance+"####"+getSuperstep()+"###"+chemin);
                //}

                sendMessage(edge.getTargetVertexId(), new Text(distance+"|"+chemin+"-"+edge.getTargetVertexId().get()));
            }
        }

        vertex.voteToHalt();
    }
}

