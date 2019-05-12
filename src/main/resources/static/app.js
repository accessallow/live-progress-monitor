var stompClient = null;


var progressItemStore = {};
var totalCount = 0;
var inProgressCount = 0;
var completedCount = 0;

var removeComplete = true;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;	
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            //showGreeting(JSON.parse(greeting.body).content);
        	//showGreeting(greeting.body);
        	let greetingObj = JSON.parse(greeting.body);
        	 
        	updateStats(greetingObj);
        	//console.log(">>"+greetingObj);
        	
        	progressItemStore[greetingObj.filePath] = greetingObj;
        	
        	paintProgressPanel();
        	
        	
        	
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(progressItem) {
	  let filePath = progressItem.filePath;
//	  console.log("filePath = "+filePath);
	  let containerName = progressItem.containerName;
	  let activityName = progressItem.activityName;
	  let activityId = progressItem.activityId;
	  let openStackId = progressItem.openStackId;
	  let fileSize = progressItem.fileSize;
	  let percentage = progressItem.progressPercentage;
	  
	  
    $("#greetings").append("<tr>" +
    				"<td>"+filePath+"</td>" +
    				"<td>"+activityName+"</td>" +
    				" <td>"+activityId+"</td>  " +
    				"<td>"+containerName+"</td>" +
					"<td>"+openStackId+"</td> " +
					"<td>"+fileSize+"</td> " +
					"<td>" + getProgressMessage(percentage) + "</td>" +
				"</tr>");
}

function getProgressMessage(progressPercentge){
	let progressMessage = '<div class="progress">'+
  '<div class="progress-bar" role="progressbar" style="width: '+progressPercentge+'%;" aria-valuenow="'+progressPercentge+'" aria-valuemin="0" aria-valuemax="100">'+progressPercentge+'%</div>'
'</div>"';
	return progressMessage;
}

function paintProgressPanel(){
	$("#greetings").empty();
	
	for (let progressItemKey in progressItemStore) {	  
		  let progressItem = progressItemStore[progressItemKey];	  
		  showGreeting(progressItem);	 
	}
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    
    connect();
    
    getMigrationStats();
    
    window.addEventListener("unload", function (event) {
    	  //disconnect socket
    	  console.log("Disconnecting socket!!!");
    	  disconnect();
	});
    
});



function updateStats(greetingObj){
	totalCount = Object.keys(progressItemStore).length;
	
	
//	console.log("Key presence = ")
//	console.log(!('"'+greetingObj.appUniqueIdentifier+'"' in progressItemStore));
//	console.log(progressItemStore);
	
	let appKey = greetingObj.filePath;
	//console.log(appKey);
	
	if ( (appKey in progressItemStore) == false){
		inProgressCount++;		
	}else{
//		console.log("2nd Block");
		if(greetingObj.progressPercentage==100){
			inProgressCount--;
			completedCount++;
			
		}
	}
	$( "#totalReporters" ).text(""+totalCount);
	$( "#inProgressReporters" ).text(""+inProgressCount);
	$( "#completedReporters" ).text(""+completedCount);
	
}

function getMigrationStats(){
	$.ajax({url: "/stats", success: function(result){
	    //console.log(result);    
	    $( "#totalSizeBadge" ).text(""+result.TOTAL_SIZE);
	    $( "#downloadSizeBadge" ).text(""+result.DOWNLOADED_SIZE);
	    $( "#migrationPercbadge" ).text(""+result.DOWNLOADED_PERCENTAGE);
	}});
}

function refreshGrid(){
	progressItemStore = {};
	totalCount = 0;
	inProgressCount = 0;
	completedCount = 0;
}

function reConnect(){
	disconnect();
	connect();
}

setInterval(() => {
	getMigrationStats();
}, 10000);
