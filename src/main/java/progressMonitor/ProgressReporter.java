package progressMonitor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

public class ProgressReporter {
	public static MyStompSessionHandler sessionHandler;
	
	
	public static void connectToReportingServer(){
		
		
		if(ServerSettings.reportingServerPath!=null){
			
			if(sessionHandler==null){
				List<Transport> transports = new ArrayList<>(1);
				transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
				WebSocketClient transport = new SockJsClient(transports);
				WebSocketStompClient stompClient = new WebSocketStompClient(transport);	
				stompClient.setMessageConverter(new MappingJackson2MessageConverter());		 
				sessionHandler = new MyStompSessionHandler();
				stompClient.connect(ServerSettings.reportingServerPath, sessionHandler);	
			}
		
		}else{
			System.out.println("Server Path not provided. No download-reporting will occur.");
		}	
	}
	
	static{
		try{
			connectToReportingServer();
		}catch(Exception ex){
			//Do nothing, application should not even know that monitoring is going on
		}		
	}
	
	
	public static void reportProcess(ProgressReport progressReportObject){	
		if(ServerSettings.reportingServerPath!=null){
			try{
				sessionHandler.sendMessage(progressReportObject);
			}catch(Exception ex){
				connectToReportingServer();
				sessionHandler = null;
			}
		}	
	}
}
