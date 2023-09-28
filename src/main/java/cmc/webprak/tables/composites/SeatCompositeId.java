package cmc.webprak.tables.composites;

import cmc.webprak.tables.Buses;
import cmc.webprak.tables.Seats;
import java.util.Objects;

import java.io.Serializable;

public class SeatCompositeId implements Serializable {

    private String seat;

    private Buses bus;

    public SeatCompositeId() {}

    public SeatCompositeId(String _seat, Buses _bus) {
        seat = _seat;
        bus = _bus;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        SeatCompositeId other = (SeatCompositeId) _other;
        return Objects.equals(seat, other.seat)
                && Objects.equals(bus, other.bus);
    }

    @Override
    public int hashCode() {
        return bus.getId().hashCode() * 1000 + seat.hashCode();
    }
}
