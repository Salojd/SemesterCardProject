/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playingcards24;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Brett
 */
public class FXMLDocumentControllerTest {
    
    FXMLDocumentController fdc;
    
    public FXMLDocumentControllerTest() {
        fdc = new FXMLDocumentController();
    }

    @Test
    public void testInitialize() {
    }

    @Test
    public void testSetImage() {
    }

    @Test
    public void testCount() {
        assertEquals(1, fdc.count("5+4+2", "111"));
    }

    @Test
    public void testGetInstructions() {
    }

    @Test
    public void testGetEquation() {
    }

    @Test
    public void testEvaluate() {
    }
    
}
