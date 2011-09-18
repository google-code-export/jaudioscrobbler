package org.lastfm.controller;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lastfm.model.AlbumResponse;
import org.lastfm.util.JsonConverter;
import org.springframework.web.client.RestTemplate;

public class LastfmController {
	private static final String URL = "http://ws.audioscrobbler.com/2.0/?method=album.getInfo&album=The%20Joshua%20Tree&artist=U2&api_key=250d02d1a21e78488d79ad1a73e88c72&format=json";
	private RestTemplate defaultTemplate = new RestTemplate();
	private Log log = LogFactory.getLog(this.getClass());
	
	public void getCoverArt(){
		String json = defaultTemplate.getForObject(URL, String.class);
		//String json = "{\"album\":{\"name\":\"The%20Joshua%20Tree\",\"artist\":\"U2\",\"id\":\"71376040\",\"mbid\":\"\",\"url\":\"ht\",\"releasedate\":\"sdsd\",\"image\":[{\"#text\":\"http:12640013.png\",\"size\":\"small\"},{\"#text\":\"http:12640013.png\",\"size\":\"medium\"}],\"listeners\":\"575972\"}}";
		log.info("json: " + json);
		AlbumResponse result = (AlbumResponse) JsonConverter.toBean(json, AlbumResponse.class);
		log.info("Result: " + ToStringBuilder.reflectionToString(result));
	}
	
	public static void main(String[] args) {
		new LastfmController().getCoverArt();
	}

}
 