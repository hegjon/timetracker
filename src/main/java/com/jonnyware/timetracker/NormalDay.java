package com.jonnyware.timetracker;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.Period;

public class NormalDay {
    private final LocalDate day;
    private final Period total;

    public NormalDay(LocalDate day, Period total) {
        this.day = day;
        this.total = total;
    }

    public LocalDate getDay() {
        return day;
    }

    public Period getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        NormalDay rhs = (NormalDay) obj;
        return new EqualsBuilder().append(day, rhs.day).append(total, rhs.total).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 13).append(day).append(total).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("day", day).append("total", total).toString();
    }
}
