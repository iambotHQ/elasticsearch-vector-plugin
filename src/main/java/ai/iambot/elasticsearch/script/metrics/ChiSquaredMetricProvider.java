package ai.iambot.elasticsearch.script.metrics;

public class ChiSquaredMetricProvider implements MetricProvider{
    @Override
    public Metric getMetric(double[] inputVector) {
        return new ChiSquaredMetric(inputVector);
    }
}
