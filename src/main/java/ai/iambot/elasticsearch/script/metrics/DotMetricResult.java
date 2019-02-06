package ai.iambot.elasticsearch.script.metrics;

public class DotMetricResult {
    double queryVectorNorm;
    double dot;
    public DotMetricResult(double queryVectorNorm, double dot){
        this.queryVectorNorm = queryVectorNorm;
                this.dot = dot;
    }

    public double getQueryVectorNorm() {
        return queryVectorNorm;
    }

    public double getDot() {
        return dot;
    }
}
