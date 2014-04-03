/*
 *  Copyright 2014 Jonny Heggheim
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.jonnyware.timetracker;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

public final class NeutralDay implements SpecialDay {
    private final String comment;
    private final LocalDate day;

    public NeutralDay(LocalDate day, String comment) {
        Validate.notNull(day);
        Validate.notNull(comment);

        this.day = day;
        this.comment = comment;
    }

    @Override
    public LocalDate getDay() {
        return day;
    }

    @Override
    public String getComment() {
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
        return new EqualsBuilder().append(day, rhs.day).append(comment, rhs.comment).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 13).append(day).append(comment).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("day", day).append("comment", comment).toString();
    }
}
