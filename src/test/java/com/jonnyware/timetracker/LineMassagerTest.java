package com.jonnyware.timetracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineMassagerTest {
    @Test
    public void emptyLine() {
        assertEquals("", LineMassager.massage(""));
    }

    @Test
    public void onlyWhitespace() {
        assertEquals("", LineMassager.massage(" \t \t"));
    }

    @Test
    public void letters() {
        assertEquals("abc", LineMassager.massage("abc"));
    }

    @Test
    public void comment() {
        assertEquals("abc", LineMassager.massage("abc#comment"));
    }

    @Test
    public void complex() {
        assertEquals("a bc", LineMassager.massage("\t a bc\t  # comment  a "));
    }

    @Test
    public void startWithComment() {
        assertEquals("", LineMassager.massage("#comment"));
    }
}