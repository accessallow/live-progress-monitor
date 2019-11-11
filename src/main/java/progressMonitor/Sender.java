package progressMonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sender {
	
	public static String[] labels = {"BACKUP","Notifications","Delta","Zipping","EETS"};
	
	public static void main(String[] args) {
		ServerSettings.reportingServerPath = "ws://localhost:8080/gs-guide-websocket/";
		
		List<Thread> threadList = new ArrayList<>();
		
		for (int i = 1; i <= 20; i++) {			
			WorkerJob wj = new Sender(). new WorkerJob();  
			wj.processValue = "P-"+i;
			Thread t = new Thread(wj);
			t.start();
			//doLongProgress(500);
			threadList.add(t);
		}

		//join
		for(Thread t : threadList){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace(); 
			}
		}
		
		System.out.println("Sending done..."); 
	}
	
	
	public static int randomNumber(){	
		double randomDouble = Math.random();	
		randomDouble = randomDouble * 5 + 1;
		int randomInt = (int) randomDouble;
		return randomInt;
	}
	
	
	public static int randomFromRange() {	
		Random random = new Random();
		return random.nextInt(5);
	}
	

	public static void spawnProgress(String processName) {
		String identifier = null;
		
		synchronized (processName) {
			identifier = "I-" + System.nanoTime();
		}
		

		ProgressReport report = new ProgressReport();
		report.setFilePath(identifier);
		report.setActivityName("SA_DOWNLOAD:"+processName);
		
		report.setApplicationInstanceType(labels[randomFromRange()]);  
		report.setProgressPercentage(0);
		report.setActivityId(processName);
		report.setContainerName("SAMPLE_CONTAINER");
		report.setOpenStackId(processName);
		report.setFileSize("0/100 MB");
		
		ProgressReporter.reportProcess(report);
		int delay = randomNumber()*100;
		
		int shouldIPause = randomNumber();
		
//		int 
//		delay = 1000;
		

		for (int i = 1; i <= 100; i++) { 
			doLongProgress(delay);
			report.setProgressPercentage(i);
			report.setFileSize(i+"/100MB");
			
			//Flag code
			if(shouldIPause == 5) {
				report.setProgressState(ProgressReport.DANGER);
			}
			
			if(i==100) {
				report.setProgressState(ProgressReport.SUCCESS);
			}
			//End Flag Code
			ProgressReporter.reportProcess(report);
			
		}
	}

	private static void doLongProgress(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class WorkerJob implements Runnable{
		public String processValue;
		
		@Override
		public void run() {
			spawnProgress(processValue);			
		}
		
	}
	
}
