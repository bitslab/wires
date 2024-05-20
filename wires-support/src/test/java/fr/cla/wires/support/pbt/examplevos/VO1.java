package fr.cla.wires.support.pbt.examplevos;

import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import fr.cla.wires.support.oo.AbstractValueObject;

import java.util.List;

import static java.util.Collections.singletonList;

//@formatter:off
public class VO1 extends AbstractValueObject<VO1> {

    protected final Value x;

    public VO1(Value x, Equatability e) {
        super(VO1.class, e);
        this.x = x;
    }

    public static VO1 random(SourceOfRandomness rand, Equatability e) {
        return new VO1(Value.random(rand), e);
    }

    @Override
    protected List<Object> equalityCriteria() {
        return singletonList(x);
    }

    @Override
    protected boolean canEqual(AbstractValueObject<?> that) {
        return super.canEqual(that) && that instanceof VO1;
    }

}
//@formatter:on