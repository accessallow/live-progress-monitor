package progressMonitor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ProgressReport greeting(ProgressReport progressReport) throws Exception {
        return progressReport;
    }

}
