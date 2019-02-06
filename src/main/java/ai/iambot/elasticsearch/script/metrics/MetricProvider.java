package ai.iambot.elasticsearch.script.metrics;

public interface MetricProvider {

    Metric getMetric(double[] inputVector);
}
