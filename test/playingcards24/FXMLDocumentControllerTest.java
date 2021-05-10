
package playingcards24;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author @author Peter Schellhorn, Brett Silver, Jonathan Saloman, Aayushma Pal
 * @version 1.0
 * @since 2021-4-15
 */
public class FXMLDocumentControllerTest {
    
    Cards card = new Cards();

    /**
     * Tests the actual value the card holds
     */
	@Test
	public void testValue() {
		int v = 6;
		card.setValue(v);
		assertEquals(6, card.getValue());
	}
        
        /**
         * Tests the number associated with the card
         */

	@Test
	public void testNumber() {
		String number = "5";
		card.setNumber(number);
		Assert.assertTrue(card.getNumber().equals("5"));
	}

        /**
         * Test the suit of each card
         */
	@Test
	public void testType() {
		// setting setters and getters for type
		String type = "clubs";
		card.setType(type);
		Assert.assertTrue(card.getType().equals("clubs"));
    }
    
}
