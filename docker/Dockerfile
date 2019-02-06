FROM elasticsearch:6.5.4

WORKDIR /tmp
RUN wget https://github.com/iambotHQ/elasticsearch-vector-plugin/releases/download/1.0.0-SNAPSHOT/elasticsearch-binary-vector-scoring-1.0.0-SNAPSHOT.zip
RUN elasticsearch-plugin install file:/tmp/elasticsearch-binary-vector-scoring-1.0.0-SNAPSHOT.zip