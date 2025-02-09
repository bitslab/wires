package fr.cla.wires.support.pbt;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import fr.cla.wires.support.functional.Indexed;
import fr.cla.wires.support.oo.AbstractValueObject;
import fr.cla.wires.support.pbt.examplevos.*;

//@formatter:off
public class VoGenerator extends Generator<VoSingleton> {

    public VoGenerator() {
        super(VoSingleton.class);
    }

    @Override
    public VoSingleton generate(SourceOfRandomness rand, GenerationStatus status) {
        return new VoSingleton(generate(rand, generateEquatability(rand)));
    }

    static AbstractValueObject.Equatability generateEquatability(SourceOfRandomness rand) {
        return rand.choose(AbstractValueObject.Equatability.values());
    }

    static AbstractValueObject<?> generate(SourceOfRandomness rand, AbstractValueObject.Equatability e) {
        switch (rand.nextInt(7)) {
            case 0: return VO1.random(rand, e);
            case 1: return VO2.random(rand, e);
            case 2: return VO1A.random(rand, e);
            case 3: return VO1B.random(rand, e);
            case 4: return VO2A.random(rand, e);
            case 5: return VO2B.random(rand, e);
            case 6: return Indexed.index(rand.nextInt(), generate(rand, e));
            default: throw new AssertionError();
        }
    }

}
//@formatter:on