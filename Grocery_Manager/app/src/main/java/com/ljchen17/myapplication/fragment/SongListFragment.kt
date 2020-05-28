package com.ljchen17.myapplication.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ericchee.songdataprovider.Song
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.SongListAdapter
import kotlinx.android.synthetic.main.fragment_song_list.*

/**
 * A simple [Fragment] subclass.
 */
class SongListFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: SongListAdapter
    private var OnSongClickListener: OnSongClickListener? = null
    private var allSongs: MutableList<Song> = ArrayList<Song>()

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val ARG_SONGLIST = "arg_songlist"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val songlist = args.getParcelableArrayList<Song>(ARG_SONGLIST)
            if (songlist != null) {
                this.allSongs = songlist.toList() as MutableList<Song>
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSongClickListener) {
            OnSongClickListener = context
        }
    }

    fun shuffleList() {
        var newSong = allSongs.shuffled()
        adapter.shuffleList(newSong as MutableList<Song>)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(context)
        rvMusic.layoutManager = linearLayoutManager
        adapter = SongListAdapter(allSongs)

        adapter.onSongClickListener = { someSong: Song ->
            OnSongClickListener?.onSongClicked(someSong)
        }

        rvMusic.adapter = adapter
        rvMusic.setHasFixedSize(true)
    }
}

interface OnSongClickListener{
    fun onSongClicked(song: Song)
}
