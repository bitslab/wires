package fr.cla.wires.support.pbt;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import fr.cla.wires.support.oo.AbstractValueObject;

//@formatter:off
public class VoPairGenerator extends Generator<VoPair> {

    public static AbstractValueObject.Equatability current = null;

    public VoPairGenerator() {
        super(VoPair.class);
    }

    @Override
    public VoPair generate(SourceOfRandomness rand, GenerationStatus status) {
        current = VoGenerator.generateEquatability(rand);
        return new VoPair(
            VoGenerator.generate(rand, current),
            VoGenerator.generate(rand, current)
        );
    }

}
//@formatter:on