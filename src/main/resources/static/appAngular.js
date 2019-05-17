
var app = angular.module("progressMonitor", []);
app.controller("HomeController", function($scope) {
	
	//Counters
	$scope.totalCount = 0;
	$scope.inProgressCount = 0;
	$scope.completedCount = 0;
	
	$scope.stompClient = null;
	$scope.socket = null;
	
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    
    $scope.progressItems = {};
    
    $scope.connect = function(){
    	$scope.socket = new SockJS('/gs-guide-websocket');
        $scope.stompClient = Stomp.over($scope.socket);
        $scope.stompClient.debug = null;	
        $scope.stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            $scope.stompClient.subscribe('/topic/greetings', function (greeting) {

            	
            	let greetingObj = JSON.parse(greeting.body); 
//            	console.log(greetingObj);
            	$scope.updateStats(greetingObj);
            	$scope.progressItems[greetingObj.filePath] =  greetingObj;
//            	console.log($scope.progressItems);   
            	
            	$scope.$apply();
            });
        });
    }
    
    $scope.disconnect = function(){
        if ($scope.stompClient !== null) {
        	$scope.stompClient.disconnect();
        }
        console.log("Disconnected");
    }
    
    
    $scope.getMigrationStats = function(){
    	$.ajax({url: "/stats", success: function(result){
    	    // console.log(result);
    	    $( "#totalSizeBadge" ).text(""+result.TOTAL_SIZE);
    	    $( "#downloadSizeBadge" ).text(""+result.DOWNLOADED_SIZE);
    	    $( "#migrationPercbadge" ).text(""+result.DOWNLOADED_PERCENTAGE);
    	}});
    }
    
    $scope.refreshGrid = function (){
    	$scope.progressItems = {};
    	
    	$scope.totalCount = 0;
    	$scope.inProgressCount = 0;
    	$scope.completedCount = 0;
    }

    $scope.reConnect = function(){
    	$scope.disconnect();
    	$scope.connect();
    }

    setInterval(() => {
    	$scope.getMigrationStats();
    }, 10000);
    
    $scope.getMigrationStats();
    
    window.addEventListener("unload", function (event) {
    	  // disconnect socket
    	  console.log("Disconnecting socket!!!");
    	  $scope.disconnect();
	});
    
    $scope.updateStats = function(greetingObj){
    	var storeArray = Object.keys($scope.progressItems);
    	$scope.totalCount = storeArray.length;
    		
    	let appKey = greetingObj.filePath;
    	
    	//console.log(appKey);
//    	console.log(storeArray);
    	
    	if ((storeArray.includes(appKey)) == false){
    		$scope.inProgressCount++;		
    	}else{

    		if(greetingObj.progressPercentage==100){
    			$scope.inProgressCount--;
    			$scope.completedCount++; 			
    		}
    	}
    }
    
    
    $scope.connect();
});

