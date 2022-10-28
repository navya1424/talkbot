package com.codepalace.Talkbot.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepalace.Talkbot.R
import com.codepalace.Talkbot.data.Message
import com.codepalace.Talkbot.utils.BotResponse
import com.codepalace.Talkbot.utils.Constants.OPEN_GALLERY
import com.codepalace.Talkbot.utils.Constants.OPEN_GOOGLE
import com.codepalace.Talkbot.utils.Constants.OPEN_INSTA
import com.codepalace.Talkbot.utils.Constants.OPEN_SEARCH
import com.codepalace.Talkbot.utils.Constants.OPEN_SNAPCHAT
import com.codepalace.Talkbot.utils.Constants.OPEN_SPOTIFY
import com.codepalace.Talkbot.utils.Constants.OPEN_WHATSAPP
import com.codepalace.Talkbot.utils.Constants.OPEN_YOUTUBE
import com.codepalace.Talkbot.utils.Constants.RECEIVE_ID
import com.codepalace.Talkbot.utils.Constants.SEND_ID
import com.codepalace.Talkbot.utils.Constants.SHOW_SOME_MEMES
import com.codepalace.Talkbot.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.message_item.*
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private val TAG = "MainActivity"
    private  var tts: TextToSpeech? = null

    companion object {
        private const val REQUEST_CODE_STT = 1
    }
//    private val RQ_SPEECH_REC = 102

    //You can ignore this messageList if you're coming from the tutorial,
    // it was used only for my personal debugging
    var messagesList = mutableListOf<Message>()

    private lateinit var adapter: MessagingAdapter
//    private val botList = listOf("Peter", "Sam", "Lusy", "Aven")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speak!!.isEnabled == false
        tts = TextToSpeech(this,this)
//        speak.setOnClickListener{
//            processTextToSpeech()
//        }

        speak.setOnClickListener {
            // Get the Intent action
            val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            // Language model defines the purpose, there are special models for other use cases, like search.
            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            // Adding an extra language, you can use any language from the Locale class.
            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            // Text that shows up on the Speech input prompt.
            sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")
            try {
                // Start the intent for a result, and pass in our request code.
                startActivityForResult(sttIntent, REQUEST_CODE_STT)
            } catch (e: ActivityNotFoundException) {
                // Handling error when the service is not available.
                e.printStackTrace()
                Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
            }
        }






//        speak.setOnClickListener{
//            askspeechinput()
//        }

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customBotMessage("Hello! Welcome to TALKBOT!!, how may I help?")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {


            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language Specified Is Not Supported!!")
            } else {
                speak!!.isEnabled == true

            }

        } else {
            Log.e("TTS", "initializtion failed!")
        }
    }

//    private  fun processTextToSpeech() {
//        val text = et_message.text.toString()
//        tts!!.speak(text, TextToSpeech.QUEUE_ADD, null, "")
//    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Handle the result for our request code.
            REQUEST_CODE_STT -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        // Do what you want with the recognized text.
                        et_message.setText(recognizedText)
                    }
                }
            }
        }
    }




//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
//            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//            et_message.text = result?.get(0).toString()
//        }
//    }

//    private fun askspeechinput() {
//        if(!SpeechRecognizer.isRecognitionAvailable(this)) {
//            Toast.makeText(this, "Speech recongnition is not available", Toast.LENGTH_SHORT).show()
//        } else {
//            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!")
//            startActivityForResult(i,RQ_SPEECH_REC)
//        }
//    }

    private fun clickEvents() {

        //Send a message
        btn_send.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message(message, SEND_ID, timeStamp))
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    fun openOtherApp(context: Context, packageName: String): Boolean {
        val manager: PackageManager = context.getPackageManager()
        return try {
            var intent = packageManager.getLaunchIntentForPackage(packageName)
            if (intent == null) {
                //the app is not installed
                try {
                    intent = Intent(Intent.ACTION_VIEW)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.data = Uri.parse("market://details?id=$packageName")
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    //throw new ActivityNotFoundException();
                    return false
                }
            }
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            context.startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when(response) {
                    OPEN_SPOTIFY -> {
                        openOtherApp(getApplicationContext(), "com.spotify.music")
                    }
                }

                when(response) {
                    OPEN_SNAPCHAT -> {

                        openOtherApp(getApplicationContext(), "com.snapchat.android")
                    }
                }

                when(response) {
                    OPEN_INSTA -> {
                        openOtherApp(getApplicationContext(), "com.instagram.android")
                    }
                }

                when(response) {
                    OPEN_WHATSAPP
                    -> {

                        openOtherApp(getApplicationContext(), "com.whatsapp.android")
                    }
                }

                when(response) {
                    OPEN_GALLERY -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        startActivity(intent)
                    }
                }

                when(response) {
                    SHOW_SOME_MEMES -> {
                        val activity = Intent(this@MainActivity, MainActivity2::class.java)
                        startActivity(activity)
                    }
                }

                when(response) {
                    OPEN_YOUTUBE -> {
                        openOtherApp(getApplicationContext(), "com.google.android.youtube")
                    }
                }


                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }




    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }


}


