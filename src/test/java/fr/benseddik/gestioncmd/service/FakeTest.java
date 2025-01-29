package fr.benseddik.gestioncmd.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FakeTest {

    @Test
    void test() {
        assertTrue(true);
    }

    @Test
    void test2() {
        assertFalse(false);
    }

    @Test
    void test3() {
        assertEquals(1, 1);
    }

    @Test
    void test4() {
        assertNotEquals(21, 2);
    }

    @Test
    void test5() {
        assertNotNull(new Object());
    }

    @Test
    void test6() {
        assertNull(null);
    }
}
