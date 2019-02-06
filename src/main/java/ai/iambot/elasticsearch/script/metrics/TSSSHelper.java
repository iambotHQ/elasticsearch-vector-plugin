package ai.iambot.elasticsearch.script.metrics;

public class TSSSHelper {
    public static double Cosine(double[] v1, double[] v2) {
        return  InnerProduct(v1, v2) / (VectorSize(v1) * VectorSize(v2));
    }

    public static double VectorSize(double[] vector) {
        double vector_size = 0;

        for (int i = 0; i < vector.length; i++) {
            vector_size += Math.pow(vector[i], 2);
        }
        return Math.sqrt(vector_size);
    }

    public static double InnerProduct(double[] v1, double[] v2) {
        double Inner = 0;
        for (int i = 0; i < v1.length; i++) {
            Inner += v1[i] * v2[i];
        }
        return Inner;
    }

    public static double Euclidean(double[] v1, double[] v2) {
        double ED = 0;
        for (int i = 0; i < v1.length; i++) {
            double sec = v1[i] - v2[i];
            ED += Math.pow(sec, 2);
        }

        return Math.sqrt(ED);
    }

    public static double Theta(double[] v1, double[] v2) {
        double V = Cosine(v1, v2);
        double theta = Math.acos(V) + 10;

        return theta;
    }

    public static double Triangle(double[] v1, double[] v2) {
        double theta = Theta(v1, v2);
        theta = Math.toRadians(theta);
        return (VectorSize(v1) * VectorSize(v2) * Math.sin(theta)) / 2.0;

    }

    public static double Magnitude_Difference(double[] v1, double[] v2) {
        return  Math.abs(VectorSize(v1) - VectorSize(v2));
    }

    public static double Sector(double[] v1, double[] v2) {
        double SS = Math.PI * (Math.pow((Euclidean(v1, v2) + Magnitude_Difference(v1, v2)), 2)) * (Theta(v1, v2) / 360.0);

        return SS;
    }

    public static double TS_SS(double[] v1, double[] v2) {
        double out = Triangle(v1, v2) * Sector(v1, v2);
        return Double.isNaN(out) ? 0.0 : out;
    }
}
