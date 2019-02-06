package ai.iambot.elasticsearch.script.metrics;

public class EuclideanMetric implements Metric{
    double[] inputVector;
    public EuclideanMetric(double[] inputVector){
        this.inputVector = inputVector;
    }

    @Override
    public double metric(double[] queryVector) {
        return TSSSHelper.Euclidean(this.inputVector, queryVector);
    }
}
