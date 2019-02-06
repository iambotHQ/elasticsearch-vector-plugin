package ai.iambot.elasticsearch.script.metrics;

public class CosineMetric extends DotMetric{
    public CosineMetric(double[] inputVector){
        super(inputVector);
    }

    @Override
    public double metric(double[] queryVector) {
        DotMetricResult dot = super.dotMetric(queryVector);
        if ( inputVectorMagnitude == 0 || dot.getQueryVectorNorm() == 0){
            return 0.0;
        } else {
            return dot.getDot() / (dot.getQueryVectorNorm() * inputVectorMagnitude);
        }
    }
}
