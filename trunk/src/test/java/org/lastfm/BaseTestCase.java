package org.lastfm;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */
public abstract class BaseTestCase {
	@Before
	public void initMockAnnotations() {
		MockitoAnnotations.initMocks(this);
	}
}
