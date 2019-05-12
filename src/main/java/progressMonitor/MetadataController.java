package progressMonitor;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetadataController {
	@GetMapping("/stats")
	public Map<String, String> getMigrationProgress(){
		return DataProvider.getMigrationStats();
	}
}
