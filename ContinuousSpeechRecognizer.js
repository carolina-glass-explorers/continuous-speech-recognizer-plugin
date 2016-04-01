var exec = require('cordova/exec');

function ContinuousSpeechRecognizer() {
	console.log( "Cool plugin made." );
}

ContinuousSpeechRecognizer.prototype.recognize  = function(successCallback, errorCallback) {
	return cordova.exec(successCallback, errorCallback, "ContinuousSpeechRecognizer", "recognize", []);
}

module.exports = new ContinuousSpeechRecognizer();