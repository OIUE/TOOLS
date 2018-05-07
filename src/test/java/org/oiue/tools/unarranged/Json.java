package org.oiue.tools.unarranged;

import org.junit.Test;
import org.oiue.tools.json.JSONUtil;

public class Json {
	
	@Test
	public void testJson() {
		String js = "{\"layer_id\":\"129\",\"id\":\"129\",\"instance_layer_id\":\"129\",\"name\":\"POI-地名-岛名\",\"type\":\"symbol\",\"source\":\"basemap2\",\"source-layer\":\"poi\",\"minzoom\":1,\"filter\":[\"any\",[\"==\",\"class_4\",\"sF10301\"]],\"layout\":{\"text-size\":10,\"text-font\":[\"Microsoft YaHei Regular\"],\"visibility\":\"visible\",\"text-offset\":[0,1],\"icon-image\":\"{NewClass}\",\"text-anchor\":\"top\",\"text-field\":\"{Name}\",\"text-max-width\":8},\"paint\":{\"text-color\":\"#666\",\"text-halo-width\":1,\"text-halo-color\":\"rgba(255,255,255,0.75)\",\"text-halo-blur\":1}}";
		try {
			System.out.println(JSONUtil.parserStrToMap(js));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
}
