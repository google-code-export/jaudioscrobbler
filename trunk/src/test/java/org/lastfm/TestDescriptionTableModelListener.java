package org.lastfm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lastfm.gui.LoginWindow;
import org.lastfm.gui.MainWindow;
import org.lastfm.metadata.MetadataBean;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author josdem (joseluis.delacruz@gmail.com)
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml"} )
public class TestDescriptionTableModelListener extends BaseTestCase{
	List<MetadataBean> metadataBeanList;
	private List<File> fileList;

	@Autowired
	private MainWindow mainWindow;
	
	@Mock
	private HelperScrobbler helperScrobbler;
	@Mock
	private LoginWindow loginWindow;
	
	@Before
	public void initialize(){
		metadataBeanList = new ArrayList<MetadataBean>();
		fileList = new ArrayList<File>();
		File file = mock(File.class);
		fileList.add(file);
		
		mainWindow.getDescriptionTable().setEnabled(true);
		mainWindow.getCompleteButton().setText(MainWindow.APPLY);
		ScrobblerController controller = new ScrobblerController(this.helperScrobbler, mainWindow, this.loginWindow);
		
		controller.metadataBeanList = metadataBeanList;
		controller.fileList = fileList;
	}
	
	@After
	public void finalize(){
		mainWindow.getFrame().dispose();
		
	}

	@Test
	public void shouldPutUpdatedRowInMetadataBeanList() throws Exception {
		FrameFixture window = new FrameFixture(mainWindow.getFrame());

		String album = "Pushing Air";
		
		assertEquals(0, metadataBeanList.size());
		
		window.show();
		window.robot.doubleClick(mainWindow.getDescriptionTable());
		window.robot.enterText(album);
		window.robot.pressKey(KeyEvent.VK_ENTER);
		window.cleanUp();
		
		assertEquals(1, metadataBeanList.size());
		MetadataBean bean = metadataBeanList.get(0);
		assertEquals(album, bean.getAlbum());
	}
}
