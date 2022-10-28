package com.codepalace.Talkbot.utils

import android.annotation.SuppressLint
import com.codepalace.Talkbot.utils.Constants.OPEN_GALLERY
import com.codepalace.Talkbot.utils.Constants.OPEN_GOOGLE
import com.codepalace.Talkbot.utils.Constants.OPEN_INSTA
import com.codepalace.Talkbot.utils.Constants.OPEN_SEARCH
import com.codepalace.Talkbot.utils.Constants.OPEN_SNAPCHAT
import com.codepalace.Talkbot.utils.Constants.OPEN_SPOTIFY
import com.codepalace.Talkbot.utils.Constants.OPEN_WHATSAPP
import com.codepalace.Talkbot.utils.Constants.OPEN_YOUTUBE
import com.codepalace.Talkbot.utils.Constants.SHOW_SOME_MEMES
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat


object BotResponse {

    @SuppressLint("SimpleDateFormat")
    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {

            //Flips a coin
            message.contains("flip") || message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Math calculations
            message.contains("solve") || message.contains("calculate") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            //Hello
            message.contains("hy")||message.contains("hello") || message.contains("hey") || message.contains("Hie") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Hyy! What's up??"
                    2 -> "Hey! Nice to meet you..!"
                    else -> "error" }
            }

            message.contains("open gallery") || message.contains("gallery") || message.contains("Gallery") -> {
                OPEN_GALLERY
            }

            message.contains("open youtube") || message.contains("youtube") || message.contains("Youtube") -> {
                OPEN_YOUTUBE
            }


            //How are you?
            message.contains("how are you" ) || message.contains("you say..")-> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "Pretty good! How about you?"
                    2 -> "I am good..You can ask me something..!!"
                    else -> "error"
                }
            }


            message.contains("what food do you like") -> {
                when (random) {
                    0 -> "I love pizza what about you?"
                    1 -> "I love continental food!"
                    2 -> "I love to eat everything!!"
                    else -> "error" }
            }

            message.contains("can we talk") || message.contains("talk") -> {
                when (random) {
                    0 -> "yeah sure! let's talk"
                    1 -> "what do you wanna talk about"
                    2 -> "are you okay?"
                    else -> "error" }
            }

            message.contains("interesting facts") || message.contains("facts") -> {
                when (random) {
                    0 -> "The fastest gust of wind ever recorded on Earth was 253 miles per hour."
                    1 -> "The best place in the world to see rainbows is in Hawaii."
                    2 -> "Whale songs can be used to map out the ocean floor."
                    3 -> "Dentistry is the oldest profession in the world."
                    4 -> "North Korea and Cuba are the only places you can't buy Coca-Cola."
                    5 -> "The world's quietest room is located at Microsoft's headquarters in Washington state."
                    6 -> "The longest place name on the planet is 85 letters long."
                    7 -> "People who are currently alive represent about 7% of the total number of people who have ever lived."
                    8 -> "Copenhagen is the most bike-friendly city in the world."
                    9 -> "The global adult literacy rate is around 86%."
                    10 -> "Facebook has more users than the population of the U.S., China, and Brazil combined."

                    else -> "error" }
            }

            message.contains("coding") || message.contains("related facts") -> {
                when (random) {
                    0 -> "You know, The first programmer was a lady. The first ever programmer on this earth was a female, named Ada Lovelace . She was a writer and gifted mathematician and the first woman to devise an algorithm that could be processed a by a machine."
                    1 -> "Programmers spend approximately 30% of the time surfing the source code."
                    2 -> "Ctrl C, Ctrl V, and Ctrl-Z have saved more lives than batman. Yes, Batman, it is true. The amount of copied code is hundred times more than typing a code. The Ctrl-Z is better than a time machine."
                    3 -> "67% of all programming jobs are in industries outside of technology."
                    4 -> "Do you know, The word computer “bug” was inspired by a real bug. It was founded by Grace Hopper in 1947."
                    5 -> "Nowadays, there are over 700 different programming languages. All experts recommend for kids to start with a visual editor and a blockly based programming language for them to learn in a smoother and easier way."
                    6 -> "It took less code to send a man to space than to run a smartphone!"
                    7 -> "Computer programming played an important role in ending World War II"

                    else -> "error" }
            }


            //What time is it?
            message.contains("time") -> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                        val sdf = SimpleDateFormat("HH:mm")
                        val time = sdf.format(Date(timeStamp.time))
                        time.toString()
            }
            message.contains("date") -> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val date = sdf.format(Date(timeStamp.time))
                date.toString()
            }

            message.contains("time") && message.contains("date") -> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date_time = sdf.format(Date(timeStamp.time))
                date_time.toString()
            }

            message.contains("prime minister of India") || message.contains("pm") -> {
                OPEN_SEARCH
            }

            message.contains("president of India")  || message.contains("president")-> {
                OPEN_SEARCH
            }

            message.contains("movie") || message.contains(" suggestion ") || message.contains("movies") -> {
                when(random) {
                    0 -> "Social Network(2010)"
                    1 ->  "Mank(2020)"
                    2 -> "Extraction(2020)"
                    else -> "error"
                }

            }

            message.contains("jokes") || message.contains("joke") -> {
                when(random) {
                    0 -> "exan: “Where are you from?” Harvard graduate: “I come from a place where we do not end our sentences with prepositions.” Texan: “Ok, where are you from, jackass?”\n" +
                            "\n" +
                            "Women only call me ugly until they find out how much money I make. Then they call me ugly and poor.\n" +
                            "\n" +
                            "You’re not completely useless. You can always serve as a bad example."
                    1 -> "An alsatian went to a telegram office and wrote: “Woof. Woof. Woof. Woof. Woof. Woof. Woof. Woof. Woof.” The clerk examined the paper and told the dog: “There are only nine words here. You could send another ‘Woof’ for the same price.” “But,” the dog replied, “that would make no sense at all.”"
                    2 -> "A couple of New Jersey hunters are out in the woods when one of them falls to the ground. He doesn’t seem to be breathing and his eyes have rolled back in his head. The other guy whips out his mobile phone and calls the emergency services. He gasps to the operator: “My friend is dead! What can I do?” The operator, in a soothing voice, says: “Just take it easy. I can help. First, let’s make sure he’s dead.” There is a silence, then a shot is heard. The guy’s voice comes back on the line. He says: “OK, now what?”"
                    else -> "error"
                }

            }



            //Open Google
            message.contains("open") && message.contains("google") || message.contains("open") && message.contains("chrome") || message.contains("open") && message.contains("google chrome") ||  message.contains("Google")-> {
                OPEN_GOOGLE
            }
            //Open Meme
            message.contains("show some memes") || message.contains("meme")
                    || message.contains("memes")-> {
                SHOW_SOME_MEMES
            }

            message.contains("spotify") && message.contains("open")->{
                OPEN_SPOTIFY
            }
            message.contains("Instagram") && message.contains("open") || message.contains("ig") && message.contains("open") || message.contains("insta") && message.contains("open")->{
                OPEN_INSTA
            }
            message.contains("Snapchat") && message.contains("open") || message.contains("open") && message.contains("snap")->{
                OPEN_SNAPCHAT
            }
            message.contains("Whatsapp") && message.contains("open")->{
                OPEN_WHATSAPP
            }

            //Search on the internet
            message.contains("search") || message.contains("search for")-> {
                OPEN_SEARCH
            }
            message.contains("ok") -> {
                "okay! anything else?"
            }
            message.contains("no") -> {
                "okay! cool!"
            }
            message.contains("no thanks") -> {
                "okay fine! welcome."
            }
            message.contains("Thanks") -> {
                "You're welcome."
            }
            message.contains("bye")|| message.contains("by")-> {
                "leaving so soon? okk bye..have a nice day!"
            }
            message.contains("doing") -> {
                "nothing just talking to you!"
            }





            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "I don't know"
                    3 -> "This is out of my knowledge"
                    else -> "error"
                }
            }
        }
    }
}