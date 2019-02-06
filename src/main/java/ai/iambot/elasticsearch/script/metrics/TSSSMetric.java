package ai.iambot.elasticsearch.script.metrics;

public class TSSSMetric implements Metric{
    double[] inputVector;
    public TSSSMetric(double[] inputVector){
        this.inputVector = inputVector;
    }

    @Override
    public double metric(double[] queryVector) {
        return TSSSHelper.TS_SS(this.inputVector, queryVector);
    }
}
