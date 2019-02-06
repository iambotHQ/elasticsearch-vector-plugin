# Fast Elasticsearch Vector Scoring

Elasticsearch binary vector scoring plugin based on [lior-k](https://github.com/lior-k/fast-elasticsearch-vector-scoring)

In comparision to lior-k version:

 * Fixed cosine equation
 * Provide more metrics
 * Utilize new ScoreScript API
 * Some code cleanups
 * Support Elasticsearch 6.5.4

## General
* Supports Elasticsearch 6.5.4
* Provides multiple metrics (utilize metrics parameter, dot is default one):
    * Cosine
    * Dot
    * Chi-Squared
    * Euclidean
    * TS-SS
