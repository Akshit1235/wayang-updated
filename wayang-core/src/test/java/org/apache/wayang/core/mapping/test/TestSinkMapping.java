package org.apache.wayang.core.mapping.test;

import org.apache.wayang.core.mapping.Mapping;
import org.apache.wayang.core.mapping.OperatorPattern;
import org.apache.wayang.core.mapping.PlanTransformation;
import org.apache.wayang.core.mapping.ReplacementSubplanFactory;
import org.apache.wayang.core.mapping.SubplanPattern;
import org.apache.wayang.core.plan.wayangplan.test.TestSink;
import org.apache.wayang.core.plan.wayangplan.test.TestSink2;
import org.apache.wayang.core.test.DummyPlatform;
import org.apache.wayang.core.types.DataSetType;

import java.util.Collection;
import java.util.Collections;

/**
 * Dummy {@link Mapping} implementation from {@link TestSink} to {@link TestSink2}.
 */
public class TestSinkMapping implements Mapping {

    @Override
    public Collection<PlanTransformation> getTransformations() {
        return Collections.singleton(new PlanTransformation(
                this.createSubplanPattern(),
                this.createReplacementSubplanFactory(),
                DummyPlatform.getInstance()
        ));
    }

    private SubplanPattern createSubplanPattern() {
        final OperatorPattern<TestSink<?>> operatorPattern = new OperatorPattern<>(
                "sink", new TestSink<>(DataSetType.none()), false
        );
        return SubplanPattern.createSingleton(operatorPattern);
    }


    private ReplacementSubplanFactory createReplacementSubplanFactory() {
        return new ReplacementSubplanFactory.OfSingleOperators<TestSink<?>>(
                (matchedOperator, epoch) -> new TestSink2<>(matchedOperator.getType()).at(epoch)
        );
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass().equals(obj.getClass());
    }
}