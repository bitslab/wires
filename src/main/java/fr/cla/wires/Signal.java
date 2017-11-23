package fr.cla.wires;

import fr.cla.support.functional.Monads;
import fr.cla.support.oo.ddd.AbstractValueObject;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

//@formatter:off
/**
 * A signal each transiting on a Wire.
 */
public final class Signal<V> extends AbstractValueObject<Signal<V>> {
    private final V value;

    private Signal(V v, boolean acceptNull) {
        super(signalOfV());
        this.value = acceptNull ? v : requireNonNull(v);
    }

    public static <V> Signal<V> of(V v) {
        return new Signal<>(v, false);
    }

    public static <V> Signal<V> none() {
        return new Signal<>(null, true);
    }

    @Override
    protected List<Object> equalityCriteria() {
        return singletonList(value);
    }

    public Optional<V> getValue() {
        return Optional.ofNullable(value);
    }

    public <W> Signal<W> map(Function<V, W> mapping) {
        return getValue().map(mapping).map(Signal::of).orElse(Signal.none());
    }

    public static <V1, V2, W> Signal<W> map(Signal<V1> s1, Signal<V2> s2, BiFunction<V1, V2, W> mapping) {
        if(!s1.getValue().isPresent()) return Signal.none();
        if(!s2.getValue().isPresent()) return Signal.none();
        return Signal.of(mapping.apply(s1.getValue().get(), s2.getValue().get()));
    }

    private static <V> Class<Signal<V>> signalOfV() {
        Class<?> unbounded = Signal.class;

        //Doesn't matter, as this is only used in AbstractValueObject::equals, for the isInstance check.
        //This unchecked cast means that Signals of all types are compared together without ClassCastException,
        // but this doesn't matter because Signals with equal values should be equal.
        //This is proved by SignalTest::should_not_get_classcast_when_calling_equals_on_signals_of_different_types
        // and SignalTest::equals_should_be_true_for_signals_of_different_types_but_same_value
        @SuppressWarnings("unchecked")
        Class<Signal<V>> signalOfV = (Class<Signal<V>>) unbounded;

        return signalOfV;
    }

    /**
     * @param allInputs
     * @param accumulationValue
     * @param reducer
     * @param neutralElement
     * @param <T>
     * @param <O>
     * @return
     */
    static <T, O> Signal<T> mapAndReduce(
        Collection<Wire<O>> allInputs,
        Function<O, T> accumulationValue,
        BinaryOperator<T> reducer,
        T neutralElement
    ) {
        return allInputs.stream()
            .map(Wire::getSignal)
            .map(Signal::getValue)
            .map(Monads.liftOptional(accumulationValue))
            .reduce(
                Optional.of(neutralElement),
                Monads.liftOptional(reducer)
            ).map(Signal::of)
            .orElse(Signal.none())
        ;
    }

    /**
     *
     * @param allInputs
     * @param collector
     * @param <T>
     * @param <O>
     * @return
     */
    static <T, O> Signal<T>  collect(
        Collection<Wire<O>> allInputs,
        Collector<Optional<O>, ?, Optional<T>> collector
    ) {
        return allInputs.stream()
            .map(Wire::getSignal)
            .map(Signal::getValue)
            .collect(collector)
            .map(Signal::of)
            .orElse(Signal.none())
        ;
    }

}
//@formatter:on
