package fr.cla.wires.boxes.exampleusage;

import fr.cla.wires.Delay;
import fr.cla.wires.Time;
import fr.cla.wires.Wire;
import fr.cla.wires.boxes.CollectHomogeneousInputsToOutputOfSameType;

import java.util.Set;
import java.util.function.BinaryOperator;

import static java.util.Objects.requireNonNull;

//@formatter:off

/**
 * Discussion of possible design choices:
 *
 * This class is an alternative implementation to CollectMultipleAnd.
 * It takes the other design trade-off compared to CollectMultipleAnd:
 *  -It is shorter and has a better "Payload / Builder code ratio"
 *  -On the other hand it does not expose a DSL to instantiate it like this:
 *      CollectMultipleAnd.ins(ins).out(out).time(time);
 *   So clients of this API need to do this:
 *      SimpleCollectMultipleAnd.create(ins, out, time);
 *
 * It could thus be considered a less expressive API,
 *  and makes it more likely that the client of this API will
 *  switch in1/in2 by mistake if several inputs have the same type.
 *
 * The trade-off depends on:
 *  -how many in/out wires the box has,
 *  -the taste of the implementer,
 *  -actual occurences of switching the wires by mistake,
 *  -whether this class is itself the business logic
 *   or a component used by the real business logic (in that case favor the DSL).
 */
public final class SimpleCollectMultipleAnd
extends CollectHomogeneousInputsToOutputOfSameType<Boolean> {

//Do your business-specific magic here:
//-------------Payload section of the class-------------VVVVVVVVVVVVVVVVVVV
    @Override protected BinaryOperator<Boolean> combiner() {
        return this::and;
    }

    private boolean and(boolean b1, boolean b2) {
        return b1 && b2;
    }
//-------------Payload section of the class-------------^^^^^^^^^^^^^^^^^^^

//This implementation wants to keep this as short as possible:
//-------------Builder section of the class-------------VVVVVVVVVVVVVVVVVVV
    /**
     * Even though the purpose of this class is to show a CollectMultipleAnd implementation
     *  with less "builder code", it is still worth having at least a factory method to hide the startup:
     *
     * This method is used to not do the startup in the constructor,
     * to not let "this" escape through the method ref,
     * so that the Box is "properly constructed".
     *
     * @return a started Box.
     */
    public static SimpleCollectMultipleAnd create(
        Set<Wire<Boolean>> ins, Wire<Boolean> out, Time time
    ) {
        SimpleCollectMultipleAnd gate = new SimpleCollectMultipleAnd(ins, out, time, DEFAULT_DELAY);
        gate.startup();
        return gate;
    }

    private SimpleCollectMultipleAnd(Set<Wire<Boolean>> ins, Wire<Boolean> out, Time time, Delay delay) {
        super(ins, out, time, delay);
    }
//-------------Builder section of the class-------------^^^^^^^^^^^^^^^^^^^

}
//@formatter:on
