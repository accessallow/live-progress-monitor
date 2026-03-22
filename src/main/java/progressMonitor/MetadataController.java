package progressMonitor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetadataController {
	@GetMapping("/stats")
	public Map<String, String> getMigrationProgress(){
		Map<String,String> statsMap = new HashMap<String,String>();
		statsMap.put("TOTAL_SIZE", "0");
		statsMap.put("DOWNLOADED_SIZE", "0");
		statsMap.put("DOWNLOADED_PERCENTAGE", "0");
		return statsMap;
	}
}
