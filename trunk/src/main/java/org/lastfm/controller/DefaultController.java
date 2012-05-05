package org.lastfm.controller;

import java.util.List;

import org.asmatron.messengine.annotations.RequestMethod;
import org.lastfm.action.ActionResult;
import org.lastfm.action.Actions;
import org.lastfm.controller.service.DefaultService;
import org.lastfm.helper.MetadataHelper;
import org.lastfm.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DefaultController {

	@Autowired
	private MetadataHelper metadataHelper;
	@Autowired
	private DefaultService defaultService;
	
	@RequestMethod(Actions.COMPLETE_DEFAULT_METADATA)
	public ActionResult complete(List<Metadata> metadatas) {
		boolean sameAlbum = metadataHelper.isSameAlbum(metadatas);
		if(sameAlbum){
			defaultService.complete(metadatas);
			return ActionResult.New;
		}
		return ActionResult.Complete;
	}

}
