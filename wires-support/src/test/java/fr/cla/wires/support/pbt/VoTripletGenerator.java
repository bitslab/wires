package fr.cla.wires.support.pbt;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import fr.cla.wires.support.oo.AbstractValueObject;

//@formatter:off
public class VoTripletGenerator extends Generator<VoTriplet> {

    public VoTripletGenerator() {
        super(VoTriplet.class);
    }

    @Override
    public VoTriplet generate(SourceOfRandomness rand, GenerationStatus status) {
        AbstractValueObject.Equatability e = VoGenerator.generateEquatability(rand);
        return new VoTriplet(
            VoGenerator.generate(rand, e),
            VoGenerator.generate(rand, e),
            VoGenerator.generate(rand, e)
        );
    }

}
//@formatter:on