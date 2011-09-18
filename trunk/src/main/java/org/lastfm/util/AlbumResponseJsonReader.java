package org.lastfm.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.lastfm.model.AlbumResponse;
import org.lastfm.model.Image;

public class AlbumResponseJsonReader implements JsonReader<AlbumResponse> {
	private static final AlbumResponseJsonReader INSTANCE = new AlbumResponseJsonReader();
	
	private AlbumResponseJsonReader(){
	}

	public static JsonReader<AlbumResponse> getInstance() {
		return INSTANCE;
	}

	@Override
	public AlbumResponse read(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		AlbumResponse response = (AlbumResponse) JSONObject.toBean(jsonObject, AlbumResponse.class);
		if (jsonObject.has("image")) {
			JSONArray jsonArray = jsonObject.getJSONArray("image");
			@SuppressWarnings("rawtypes")
			Iterator iterator = jsonArray.iterator();
			List<Image> images = new ArrayList<Image>();
			while (iterator.hasNext()) {
				images.add((Image) JsonConverter.toBean(iterator.next().toString(), Image.class));
			}
			response.setImage(images);
		}
		return response;
	}

}
