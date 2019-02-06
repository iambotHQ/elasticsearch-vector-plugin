package ai.iambot.elasticsearch.service;

import ai.iambot.elasticsearch.script.VectorScoreScript;
import org.apache.lucene.index.LeafReaderContext;
import org.elasticsearch.script.ScoreScript;
import org.elasticsearch.search.lookup.SearchLookup;

import java.io.IOException;
import java.util.Map;

public class VectorScoringScriptFactory implements ScoreScript.LeafFactory{
        private final Map<String, Object> params;
        private final SearchLookup lookup;


        public VectorScoringScriptFactory(
                Map<String, Object> params, SearchLookup lookup) {
            this.params = params;
            this.lookup = lookup;
        }

        @Override
        public boolean needs_score() {
            return false;
        }

        @Override
        public ScoreScript newInstance(LeafReaderContext leafReaderContext) throws IOException {
            return  new VectorScoreScript(params, lookup, leafReaderContext);
        }
}
