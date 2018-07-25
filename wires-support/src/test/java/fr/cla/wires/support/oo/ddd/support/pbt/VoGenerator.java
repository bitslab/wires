package fr.cla.wires.support.oo.ddd.support.pbt;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import fr.cla.wires.support.oo.AbstractValueObject;
import fr.cla.wires.support.oo.ddd.examplevos.VO1;
import fr.cla.wires.support.oo.ddd.examplevos.VO1A;

//@formatter:off
public class VoGenerator extends Generator<VoSingleton> {

    public VoGenerator() {
        super(VoSingleton.class);
    }

    @Override
    public VoSingleton generate(SourceOfRandomness rand, GenerationStatus status) {
        return new VoSingleton(generate(rand));
    }

    private static final int NUMBER_OF_CANDIDATE_VO_TYPES = 6;

    static AbstractValueObject<?> generate(SourceOfRandomness rand) {
        switch (rand.nextInt(2)) {
            case 0: return VO1.random(rand);
            case 1: return VO1A.random(rand);
            default: throw new AssertionError();
        }

//        switch (rand.nextInt(NUMBER_OF_CANDIDATE_VO_TYPES)) {
//            case 0: return VO1.random(rand);
//            case 1: return VO2.random(rand);
//            case 2: return VO1A.random(rand);
//            case 3: return VO1B.random(rand);
//            case 4: return VO2A.random(rand);
//            case 5: return VO2B.random(rand);
//            default: throw new AssertionError();
//        }
    }

}
//@formatter:on