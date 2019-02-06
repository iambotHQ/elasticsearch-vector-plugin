package ai.iambot.elasticsearch.script.metrics;

public class ChiSquaredMetric implements Metric{
    double[] inputVector;
    double epsilon = 1e-10;
    public ChiSquaredMetric(double[] inputVector){
        this.inputVector = inputVector;
    }

    @Override
    public double metric(double[] queryVector) {
        double result = 0.0;
        for (int i = 0; i < queryVector.length; i++) {
            double diff = queryVector[i]-this.inputVector[i];
            result += (diff * diff)/(queryVector[i]+inputVector[i]+epsilon);
        }
        return 0.5 * result;
    }
}
