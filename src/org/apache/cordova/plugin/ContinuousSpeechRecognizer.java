package org.apache.cordova.plugin;

/**
 * Created by adam on 2/26/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import org.json.JSONArray;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONException;

import java.util.ArrayList;

public class ContinuousSpeechRecognizer extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("recognize".equals(action)) {
            recognizeSpeech();
            callbackContext.success();
            return true;
        }
        Log.e("nope", "did not recognize command");
        return false;  // Returning false results in a "MethodNotFound" error.
    }

    public void recognizeSpeech(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, new Long(15000));
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, new Long(15000));
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, new Long(15000));
        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        recognizer.setRecognitionListener(new VoiceRecognitionListener(recognizer, intent));
        recognizer.startListening(intent);
    }

    class VoiceRecognitionListener implements RecognitionListener {
        SpeechRecognizer recognizer;
        Intent intent;

        public VoiceRecognitionListener(SpeechRecognizer recognizer, Intent intent){
            this.recognizer = recognizer;
            this.intent = intent;
        }

        public void onResults(Bundle data) {
            //Log.d(TAG, "onResults " + data);
            ArrayList<String> matches = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.e("ffff", matches.toString());
            recognizeSpeech();
        }


        String TAG = "FFfff";
        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");

        }
        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error) {
            Log.d(TAG, "error " + error);
            //error 6 = speech timeout, so start it on that again
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }
        public void onReadyForSpeech(Bundle params) {
//            Log.d(TAG, "onReadyForSpeech");
        }
        public void onRmsChanged(float rmsdB) {
//            Log.d(TAG, "onRmsChanged");
        }
    }
}
