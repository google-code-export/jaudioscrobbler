package org.lastfm;

import static org.junit.Assert.assertEquals;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.lastfm.metadata.MetadataBean;
import org.mockito.Mockito;


public class TestDescriptionTableModelListener {
	List<MetadataBean> metadataBeanList;
	private MainWindow mainWindow;
	private FrameFixture window;
	
	@Before
	public void initialize(){
		metadataBeanList = new ArrayList<MetadataBean>();
		HelperScrobbler helperScrobbler = Mockito.mock(HelperScrobbler.class);
		LoginWindow loginWindow = Mockito.mock(LoginWindow.class);
		mainWindow = new MainWindow();
		mainWindow.getCompleteButton().setText(MainWindow.APPLY);
		ScrobblerController controller = new ScrobblerController(helperScrobbler, mainWindow, loginWindow);
		
		controller.metadataBeanList = metadataBeanList;
	}
	
	@After
	public void finalize(){
		mainWindow.getFrame().dispose();
		window.cleanUp();
	}

	//FIXME: Frozen window when run this test with others
	@Test
	public void shouldPutUpdatedRowInMetadataBeanList() throws Exception {
		window = new FrameFixture(mainWindow.getFrame());

		String album = "Pushing Air";
		
		assertEquals(0, metadataBeanList.size());
		
		window.show();
		window.robot.doubleClick(mainWindow.getDescritionTable());
		window.robot.enterText(album);
		window.robot.pressKey(KeyEvent.VK_ENTER);
		
		assertEquals(1, metadataBeanList.size());
		MetadataBean bean = metadataBeanList.get(0);
		assertEquals(album, bean.getAlbum());
	}
}
