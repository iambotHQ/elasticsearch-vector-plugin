package ai.iambot.elasticsearch;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Created by Lior Knaany on 4/7/18.
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TestObject {
    int jobId;
    String embeddingVector;
    double[] vector;

    public int getJobId() {
        return jobId;
    }

    public String getEmbeddingVector() {
        return embeddingVector;
    }

    public double[] getVector() {
        return vector;
    }

    public TestObject(int jobId, double[] vector) {
        this.jobId = jobId;
        this.vector = vector;
        this.embeddingVector = Util.convertArrayToBase64(vector);
    }
}
