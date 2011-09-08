package org.lastfm.model;

import static org.lastfm.model.ModelType.readOnly;

public interface Model {
	
	String CURRENT_USER_ID = "application.currentUser";
	ModelType<User> CURRENT_USER = readOnly(CURRENT_USER_ID);

}
