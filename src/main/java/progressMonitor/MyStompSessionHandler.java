package progressMonitor;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

public class MyStompSessionHandler implements StompSessionHandler {
	StompSession commonSession = null;
	boolean connected = false;

	@Override
	public Type getPayloadType(StompHeaders arg0) {
		return null;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {	
		this.commonSession = session;	
		session.subscribe("/topic/greetings", this);		
		connected = true;
	}
	
	public void sendMessage(ProgressReport progressReport){
		if(connected){
			synchronized (commonSession) {
//				System.out.println(progressReport);
				commonSession.send("/app/hello", progressReport);
			}			
		}	
	}

	@Override
	public void handleException(StompSession arg0, StompCommand arg1, StompHeaders arg2, byte[] arg3, Throwable arg4) {
		
	}

	@Override
	public void handleTransportError(StompSession arg0, Throwable arg1) {

	}

}
