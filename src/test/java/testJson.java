import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.oiue.tools.json.JSONUtil;

public class testJson {
	@Test
	public void testJS() {
		String st = "[{\"service_event_parameters_id\":\"be080664-27b4-497c-828e-97831313fe02\",\"data_type_class_id\":\"system_data_type_postgresql\",\"service_id\":\"fm_system_service_execute\",\"service_event_id\":\"be080664-27b4-497c-828e-97831313fe02\",\"desc\":\"(系统)路况区块\",\"remark\":\"系统数据源\",\"rule\":\"intelligent\",\"content\":\"select * from segment_rectangle\",\"expression\":\"{\\\"conjunction\\\":\\\"and\\\",\\\"filters\\\":[]}\",\"update_user_id\":\"fm_system_operation\",\"update_time\":1517479208,\"user_id\":\"fm_system_operation\",\"event_dbtype\":\"postgis\"}]";
		
		List<Map> events = (List<Map>) JSONUtil.parserStrToList(st);
		
		System.out.println(events);
	}
}
