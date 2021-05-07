/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playingcards24;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Brett
 */
public class FXMLDocumentControllerTest {
    
    Cards card = new Cards();

	@Test
	public void test1() {
		int v = 6;
		card.setValue(v);
		assertEquals(6, card.getValue());
	}

	@Test
	public void test2() {
		String number = "5";
		card.setNumber(number);
		Assert.assertTrue(card.getNumber().equals("5"));
	}

	@Test
	public void test3() {
		// setting setters and getters for type
		String type = "clubs";
		card.setType(type);
		Assert.assertTrue(card.getType().equals("clubs"));
    }
    
}
