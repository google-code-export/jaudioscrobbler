package org.lastfm.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.ApplicationState;
import org.lastfm.action.Actions;
import org.lastfm.action.control.ActionMethod;
import org.lastfm.action.control.ControlEngineConfigurator;
import org.lastfm.event.Events;
import org.lastfm.event.ValueEvent;
import org.lastfm.helper.LastFMAuthenticator;
import org.lastfm.model.Model;
import org.lastfm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who control Login process
 */

@Controller
public class LoginController {
	private LastFMAuthenticator lastfmAuthenticator = new LastFMAuthenticator();
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ControlEngineConfigurator configurator;

	@ActionMethod(Actions.LOGIN_ID)
	public void login(User user) {
		int result = ApplicationState.ERROR;
		String username = user.getUsername();
		String password = user.getPassword();
		try {
			result = lastfmAuthenticator.login(username, password);
			if (result == ApplicationState.OK) {
				configurator.getControlEngine().set(Model.CURRENT_USER, user, null);
				configurator.getControlEngine().fireEvent(Events.LOGGED, new ValueEvent<User>(user));
			} else {
				configurator.getControlEngine().fireEvent(Events.LOGIN_FAILED);
			}
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		}
	}

}
