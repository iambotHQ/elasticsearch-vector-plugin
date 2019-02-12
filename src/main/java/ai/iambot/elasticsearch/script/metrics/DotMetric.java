package ai.iambot.elasticsearch.script.metrics;

public class DotMetric implements Metric{
    protected final double inputVectorMagnitude;
    protected final double[] inputVector;
    public DotMetric(double[] inputVector){
        this.inputVectorMagnitude = TSSSHelper.VectorSize(inputVector);
        this.inputVector = inputVector;
    }

    @Override
    public double metric(double[] queryVector) {
        DotMetricResult result = dotMetric(queryVector);
        return result.getDot();
    }

    public DotMetricResult dotMetric(double[] queryVector) {
        double queryVectorNorm = 0.0;
        double dotProduct = 0.0;
        for (int i = 0; i < queryVector.length; i++) {
            queryVectorNorm += queryVector[i]*queryVector[i];
            dotProduct += Math.pow(queryVector[i], 2);
        }
        return new DotMetricResult(Math.sqrt(queryVectorNorm), dotProduct);
    }
}
