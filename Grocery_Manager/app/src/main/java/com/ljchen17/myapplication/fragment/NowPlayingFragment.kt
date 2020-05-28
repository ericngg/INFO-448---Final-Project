package com.ljchen17.myapplication.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ericchee.songdataprovider.Song
import com.ljchen17.myapplication.R
import kotlinx.android.synthetic.main.now_playing.*
import kotlinx.android.synthetic.main.navigation.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class NowPlayingFragment : Fragment() {

    var randomNumber = Random.nextInt(1000, 10000)
    var imageColorEdit = false
    private var song: Song? = null

    companion object {

        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val ARG_SONG = "arg_song"
        const val PLAY_TIMES = "play_times"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                randomNumber = getInt(NowPlayingFragment.PLAY_TIMES)
                setPlayTimes()
            }
        }

        arguments?.let { args ->
            val song = args.getParcelable<Song>(ARG_SONG)
            if (song != null) {
                this.song = song
            }
        }
    }

    fun updateSong(song: Song?) {
        this.song = song
        updateSongViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateSongViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState?.run {
            putInt(PLAY_TIMES, randomNumber)
        }
        super.onSaveInstanceState(outState)
    }

    private fun updateSongViews() {
        song?.let {
            playTimes?.text = "$randomNumber plays"
            cover?.setImageResource(song!!.largeImageID)

            cover.setOnLongClickListener {

                imageColorEdit = if (imageColorEdit) {
                    currentsong?.setTextColor(Color.BLACK)
                    artists.setTextColor(Color.BLACK)
                    playTimes.setTextColor(Color.GRAY)
                    false
                } else {
                    currentsong.setTextColor(Color.BLUE)
                    artists.setTextColor(Color.BLUE)
                    playTimes.setTextColor(Color.BLUE)
                    true
                }
                true
            }

            currentsong.text = song!!.title
            artists.text = song!!.artist

            play.setOnClickListener {
                randomNumber += 1
                playTimes.text = "$randomNumber plays"
            }

            previous.setOnClickListener {
                val text = "Skipping to previous track"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
                toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 0)
            }

            next.setOnClickListener {
                val text = "Skipping to next track"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
                toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 0)
            }
        }
    }

    fun setPlayTimes() {
        playTimes?.text = "$randomNumber plays"
    }
}
