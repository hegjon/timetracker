package com.jonnyware.timetracker;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NeutralDay {
    private final String comment;

    public NeutralDay(String comment) {
        Validate.notNull(comment);
        this.comment = comment;
    }

    public final String getComment() {
        return comment;
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
        NeutralDay rhs = (NeutralDay) obj;
        return new EqualsBuilder().append(comment, rhs.comment).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 13).append(comment).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("comment", comment).toString();
    }
}
