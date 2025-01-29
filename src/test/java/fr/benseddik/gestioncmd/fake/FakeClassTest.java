package fr.benseddik.gestioncmd.fake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FakeClassTest {

    @Test
    void testAdd(){
        int a = 1;
        int b = 2;
        int c = 3;

        int result = FakeClass.add(a,b);

        assertEquals(result, c);
    }

}