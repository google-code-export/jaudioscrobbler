package org.lastfm.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import de.umass.lastfm.Session;


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
		String username = user.getUsername();
		String password = user.getPassword();
		try {
			Session session = lastfmAuthenticator.login(username, password);
			if (session != null) {
				user.setSession(session);
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
