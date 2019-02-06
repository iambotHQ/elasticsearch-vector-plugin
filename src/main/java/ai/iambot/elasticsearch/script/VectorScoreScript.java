/*
Based on: https://discuss.elastic.co/t/vector-scoring/85227/4
and https://github.com/MLnick/elasticsearch-vector-scoring

another slower implementation using strings: https://github.com/ginobefun/elasticsearch-feature-vector-scoring

storing arrays is no luck - lucine index doesn't keep the array members orders
https://www.elastic.co/guide/en/elasticsearch/guide/current/complex-core-fields.html

Delimited Payload Token Filter: https://www.elastic.co/guide/en/elasticsearch/reference/2.4/analysis-delimited-payload-tokenfilter.html


 */

package ai.iambot.elasticsearch.script;

import ai.iambot.elasticsearch.script.metrics.*;
import ai.iambot.elasticsearch.Util;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.store.ByteArrayDataInput;
import org.elasticsearch.script.ScoreScript;
import org.elasticsearch.search.lookup.SearchLookup;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class VectorScoreScript extends ScoreScript {

    public static final String SCRIPT_NAME = "binary_vector_score";

    private static final int DOUBLE_SIZE = 8;

    public final String field;
    private final SearchLookup lookup;
    private final LeafReaderContext leafContext;
    private  BinaryDocValues binaryValues;
    int docId ;
    @Override
    public void setDocument(int docId) {
        this.docId = docId;
    }

    private final double[] inputVector;


    private final HashMap<String, MetricProvider> metrics = new HashMap<String, MetricProvider>(){{
        put("cosine", new CosineMetricProvider());
        put("dot", new DotMetricProvider());
        put("tsss", new TSSSMetricProvider());
        put("euclidean", new EuclideanMetricProvider());
        put("chisquared", new ChiSquaredMetricProvider());
    }};

    private final Metric metric;


    @SuppressWarnings("unchecked")
    public VectorScoreScript(Map<String, Object> params, SearchLookup lookup, LeafReaderContext leafContext) {
        super(params, lookup, leafContext);
        this.lookup = lookup;
        this.leafContext = leafContext;
        Object metricConf = params.get("metric");
        if(metricConf==null){
            metricConf="dot";
        }
        if(!metrics.containsKey(metricConf)){
            throw new IllegalArgumentException("metric schould be one of %s".format(Arrays.toString(metrics.keySet().toArray())));
        }
        MetricProvider metricProvider = metrics.get(metricConf);
        final Object field = params.get("field");
        if (field == null)
            throw new IllegalArgumentException("binary_vector_score script requires field input");
        this.field = field.toString();
        try {
            this.binaryValues = this.leafContext.reader().getBinaryDocValues(this.field);
        } catch (Exception e){
            throw new RuntimeException("Failed to get binary doc values" , e);
        }

        // get query inputVector - convert to primitive
        final Object vector = params.get("vector");
        if(vector != null) {
            final ArrayList<Double> tmp = (ArrayList<Double>) vector;
            inputVector = new double[tmp.size()];
            for (int i = 0; i < inputVector.length; i++) {
                inputVector[i] = tmp.get(i);
            }
        } else {
            final Object encodedVector = params.get("encoded_vector");
            if(encodedVector == null) {
                throw new IllegalArgumentException("Must have at 'vector' or 'encoded_vector' as a parameter");
            }
            inputVector = Util.convertBase64ToArray((String) encodedVector);
        }

        metric = metricProvider.getMetric(inputVector);
    }



    @Override
    public final double execute() {
        final int size = inputVector.length;
        try{
            this.binaryValues.advance(docId);
            if(this.binaryValues.docID() != this.docId) {
                throw new RuntimeException("Got doc "+this.binaryValues.docID()+" expected "+docId);
            }
            byte[] bytes = this.binaryValues.binaryValue().bytes;
            final ByteArrayDataInput input = new ByteArrayDataInput(bytes);
            input.readVInt();
            final int len = input.readVInt();
            if(len != size * DOUBLE_SIZE) {
                return 0.0;
            }
            final int position = input.getPosition();
            final DoubleBuffer doubleBuffer = ByteBuffer.wrap(bytes, position, len).asDoubleBuffer();

            final double[] docVector = new double[size];
            doubleBuffer.get(docVector);

            double score = metric.metric(docVector);
            return score;
        } catch (Exception e){
            throw new RuntimeException("Failed to get binary doc values" , e);
        }

    }




}