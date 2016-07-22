package py.com.marcelo.pescaapp;

import org.junit.Test;

import java.lang.Exception;

import dalvik.annotation.TestTargetClass;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /**
     * Prueba de la exportacion
     */
    @Test
    public void export_isCorrect() throws Exception {
        //id, nombre
        String sTest = "id,nombre";
        String sTest2 = "id,nombre";

        assertEquals(sTest,sTest2);
    }
}