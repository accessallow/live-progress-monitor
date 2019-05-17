package progressMonitor;

import java.util.List;

public class ProgressReport{
	String filePath;
	String applicationInstanceType;
	String activityName;
	
	String containerName;
	String activityId;
	String openStackId;
	String fileSize;
	
	String progressState = NORMAL;
	
	public static String NORMAL = "";
	public static String INFO = "progress-bar-info";
	public static String SUCCESS = "progress-bar-success";
	public static String WARNING = "progress-bar-warning";
	public static String DANGER = "progress-bar-danger";
	
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getProgressState() {
		return progressState;
	}

	public void setProgressState(String progressState) {
		this.progressState = progressState;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getOpenStackId() {
		return openStackId;
	}

	public void setOpenStackId(String openStackId) {
		this.openStackId = openStackId;
	}
	List<String> appTags;
	int progressPercentage;
	
	public ProgressReport() {
		super();
	}
	
	public String getApplicationInstanceType() {
		return applicationInstanceType;
	}
	public void setApplicationInstanceType(String applicationInstanceType) {
		this.applicationInstanceType = applicationInstanceType;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public List<String> getAppTags() {
		return appTags;
	}
	public void setAppTags(List<String> appTags) {
		this.appTags = appTags;
	}
	public int getProgressPercentage() {
		return progressPercentage;
	}
	public void setProgressPercentage(int progressPercentage) {
		this.progressPercentage = progressPercentage;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "ProgressReport [filePath=" + filePath + ", applicationInstanceType=" + applicationInstanceType
				+ ", activityName=" + activityName + ", containerName=" + containerName + ", activityId=" + activityId
				+ ", openStackId=" + openStackId + ", fileSize=" + fileSize + ", progressState=" + progressState
				+ ", appTags=" + appTags + ", progressPercentage=" + progressPercentage + "]";
	}
	
	
}
