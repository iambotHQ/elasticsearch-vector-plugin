package ai.iambot.elasticsearch.script.metrics;

public class DotMetricProvider implements MetricProvider{
    @Override
    public Metric getMetric(double[] inputVector) {
        return new DotMetric(inputVector);
    }
}
