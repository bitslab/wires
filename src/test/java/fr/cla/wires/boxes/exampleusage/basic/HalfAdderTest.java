package fr.cla.wires.boxes.exampleusage.basic;


import fr.cla.wires.Signal;
import fr.cla.wires.Time;
import fr.cla.wires.Wire;
import fr.cla.wires.boxes.exampleusage.HalfAdder;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

//@formatter:off
/**
 * An example of the expected behaviour of Boxes, and of how to tick the clock.
 * @see fr.cla.wires.boxes.exampleusage
 */
public class HalfAdderTest {

    private Wire<Boolean> inA, inB, sum, carry;
    private Time time;

    @Before public void setup() {
        inA = Wire.make();
        inB = Wire.make();
        sum = Wire.make();
        carry = Wire.make();
        time = Time.create();
        HalfAdder.inA(inA).inB(inB).sum(sum).carry(carry).time(time);
    }

    //-------------------Sum-------------------VVVVVVVVVVVVVVVVVVVVVVV
    @Test
    public void sum_should_be_false_when_A_and_B_are_false() {
        given: {
            inA.setSignal(Signal.of(false));
            inB.setSignal(Signal.of(false));
        }
        when: {
            time.tick();
        }
        then: {
            assertThat(sum.getSignal()).isEqualTo(Signal.of(false));
        }
    }

    @Test
    public void sum_should_be_false_when_A_and_B_are_true() {
        given: {
            inA.setSignal(Signal.of(true));
            inB.setSignal(Signal.of(true));
        }
        when: {
            time.tick();
        }
        then: {
            assertThat(sum.getSignal()).isEqualTo(Signal.of(false));
        }
    }

    @Test
    public void sum_should_be_true_when_A_is_false_and_B_is_true() {
        given: {
            inA.setSignal(Signal.of(false));
            inB.setSignal(Signal.of(true));
        }
        when: {
            time.tick();
        }
        then: {
            assertThat(sum.getSignal()).isEqualTo(Signal.of(true));
        }
    }

    @Test
    public void sum_should_be_true_when_A_is_true_and_B_is_false() {
        given: {
            inA.setSignal(Signal.of(true));
            inB.setSignal(Signal.of(false));
        }
        when: {
            time.tick();
        }
        then: {
            assertThat(sum.getSignal()).isEqualTo(Signal.of(true));
        }
    }
    //-------------------Sum-------------------^^^^^^^^^^^^^^^^^^^^^^^

    //-------------------Carry-------------------VVVVVVVVVVVVVVVVVVVVVVV
    @Test
    public void carry_should_be_false_when_A_and_B_are_false() {
        given: {
            inA.setSignal(Signal.of(false));
            inB.setSignal(Signal.of(false));
        }
        when: {
            time.tick();
        }
        then: {
            assertThat(carry.getSignal()).isEqualTo(Signal.of(false));
        }
    }

    @Test
    public void carry_should_be_true_when_A_and_B_are_true() {
        given: {
            inA.setSignal(Signal.of(true));
            inB.setSignal(Signal.of(true));
        }
        when: {
            time.tick();
        }
        then: {
            assertThat(carry.getSignal()).isEqualTo(Signal.of(true));
        }
    }

    @Test
    public void carry_should_be_false_when_A_is_false_and_B_is_true() {
        given: {
            inA.setSignal(Signal.of(false));
            inB.setSignal(Signal.of(true));
        }
        when: {
            time.tick();
        }
        then: {
            assertThat(carry.getSignal()).isEqualTo(Signal.of(false));
        }
    }

    @Test
    public void carry_should_be_false_when_A_is_true_and_B_is_false() {
        given: {
            inA.setSignal(Signal.of(true));
            inB.setSignal(Signal.of(false));
        }
        when: {
            time.tick();
        }
        then: {
            assertThat(carry.getSignal()).isEqualTo(Signal.of(false));
        }
    }
    //-------------------Carry-------------------^^^^^^^^^^^^^^^^^^^^^^^

}
//@formatter:on
