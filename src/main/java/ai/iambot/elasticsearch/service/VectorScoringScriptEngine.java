package ai.iambot.elasticsearch.service;

import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.script.ScoreScript;
import org.elasticsearch.script.ScriptContext;
import org.elasticsearch.script.ScriptEngine;

import java.util.Map;

public class VectorScoringScriptEngine extends AbstractComponent implements ScriptEngine {

    public static final String NAME = "iambot-knn";

    @Inject
    public VectorScoringScriptEngine(Settings settings) {
        super(settings);
    }

    @Override
    public String getType() {
        return NAME;
    }

    @Override
    public <FactoryType> FactoryType compile(String s, String s1, ScriptContext<FactoryType> scriptContext, Map<String, String> map) {
        ScoreScript.Factory factory = VectorScoringScriptFactory::new;

        return scriptContext.factoryClazz.cast(factory);
    }

}
