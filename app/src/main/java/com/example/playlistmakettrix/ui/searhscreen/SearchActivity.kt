package com.example.playlistmakettrix.ui.searhscreen

import android.content.Intent
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmakettrix.ui.player.AudioPlayerScreenActivity
import com.example.playlistmakettrix.GeneralConstants
import com.example.playlistmakettrix.data.dto.TracksSearchRequest
import com.example.playlistmakettrix.databinding.ActivitySearchBinding
import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.data.searchhistory.SearchHistory
import com.example.playlistmakettrix.hideKeyboard
import com.google.gson.Gson

class SearchActivity : ComponentActivity() {

    private lateinit var sharPrefListener: OnSharedPreferenceChangeListener
    private lateinit var historyList: MutableList<Track>
    private var trackList = arrayListOf<Track>()
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchHistory: SearchHistory
    private lateinit var viewModel: SearchViewModel

    private var searchText = ""

    private var lastFailedRequest = ""


    companion object {
        private const val EDIT_TEXT_VALUE = "edit_text_value"
        private const val TRACK_LIST = "track_list"
        private const val FLIPPER_STATE = "flipper_state"
        private const val SEARCH_HISTORY_SIZE = 10

        private const val SUCCESS = 0
        private const val NOTHING_FOUND = 1
        private const val COMMUNICATION_PROBLEM = 2
        private const val PROGRESS = 3


    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SharedPrefs
        sharPrefListener = OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == SearchHistory.HISTORY_SHAR_PREF_KEY) {
                binding.includedSearchHistory.searchHistoryList.adapter?.notifyDataSetChanged()
            }
        }
        val sharedPrefs = getSharedPreferences(GeneralConstants.PLAY_LIST_MAKET_SHARED_PREFF, MODE_PRIVATE)

        sharedPrefs.registerOnSharedPreferenceChangeListener(sharPrefListener)
        searchHistory = SearchHistory(sharedPrefs)

        historyList = searchHistory.getHistory().toMutableList()

        viewModel = ViewModelProvider(this, SearchViewModel.getViewModelFactory())[SearchViewModel::class.java]
        viewModel.observeState().observe(this){loadingState ->
            when(loadingState) {
                is TrackState.Loading -> {
                    binding.viewFlipper.displayedChild = PROGRESS
                }
                is TrackState.Error -> {
                    binding.viewFlipper.displayedChild = COMMUNICATION_PROBLEM
                }
                is TrackState.Content -> {

                        if (loadingState.trackModel.isNotEmpty()) {
                            binding.viewFlipper.displayedChild = SearchActivity.SUCCESS
                            trackList.clear()
                            trackList.addAll(loadingState.trackModel)
                            binding.trackList.adapter?.notifyDataSetChanged()
                        } else {
                            binding.viewFlipper.displayedChild = SearchActivity.NOTHING_FOUND
                        }
                }
            }
        }
        binding.searchText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && historyList.isNotEmpty()) binding.includedSearchHistory.parentLayout.visibility = View.VISIBLE
            else binding.includedSearchHistory.parentLayout.visibility = View.GONE
        }

        binding.topAppBar.setNavigationOnClickListener { finish() }

        binding.clearButtonCross.setOnClickListener {
            binding.searchText.setText("")
            trackList.clear()
            binding.trackList.adapter?.notifyDataSetChanged()
            hideKeyboard()
            binding.clearButtonCross.visibility = View.INVISIBLE
        }

        binding.includedSearchHistory.searchHistoryList.layoutManager = LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false)
        binding.includedSearchHistory.searchHistoryList.adapter = TrackSearchListAdapter(historyList){ track ->
            navigateToAudioPlayer(track)
        }

        binding.includedSearchHistory.clearSearchHistoryButton.setOnClickListener {
            clearHistoryList()
            binding.includedSearchHistory.searchHistoryList.adapter?.notifyDataSetChanged()
            binding.includedSearchHistory.parentLayout.visibility = View.GONE
        }

        //установить фокус
        binding.searchText.requestFocus()


        //кнопка обновить в проблемах со связью
        binding.includedCommunicationProblem.update.setOnClickListener {
            search(TracksSearchRequest(lastFailedRequest))
        }

        //запуск поиска с клавиатуры
        binding.searchText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (binding.searchText.text.isNotEmpty()) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search(TracksSearchRequest(binding.searchText.text.toString()))
                }
            }
            false
        }

        binding.clearButtonCross.setOnClickListener {
            binding.searchText.setText("")
            trackList.clear()
            binding.trackList.adapter?.notifyDataSetChanged()
            hideKeyboard()
            binding.clearButtonCross.visibility = View.INVISIBLE
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true) binding.clearButtonCross.visibility = View.INVISIBLE else binding.clearButtonCross.visibility = View.VISIBLE

                if (binding.searchText.hasFocus() && s?.isEmpty() == true && historyList.isNotEmpty())
                    binding.includedSearchHistory.parentLayout.visibility = View.VISIBLE
                else
                    binding.includedSearchHistory.parentLayout.visibility = View.GONE

                searchText = s.toString()
                viewModel.searchDebounce(searchText)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }

        binding.searchText.addTextChangedListener(simpleTextWatcher)

        binding.trackList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.trackList.adapter = TrackSearchListAdapter (trackList = trackList){ track ->
            addTrackToHistoryList(track)
            navigateToAudioPlayer(track)
        }
    }

    private fun navigateToAudioPlayer(track: Track){
        if(viewModel.clickDebounce()){
            val intent = Intent (this, AudioPlayerScreenActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, Gson().toJson(track))
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT_VALUE, binding.searchText.text.toString())
        outState.putInt(FLIPPER_STATE, binding.viewFlipper.displayedChild)
        outState.putParcelableArrayList(TRACK_LIST, trackList)
    }

    @Suppress("DEPRECATION")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        //востанавливаем текст, флиппер и список треков
        binding.searchText.setText(savedInstanceState.getString(EDIT_TEXT_VALUE, ""))
        binding.viewFlipper.displayedChild = savedInstanceState.getInt(FLIPPER_STATE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            trackList.addAll(savedInstanceState.getParcelableArrayList(TRACK_LIST, Track::class.java)!!)
        } else {
            trackList.addAll(savedInstanceState.getParcelableArrayList(TRACK_LIST)!!)
        }
        binding.trackList.adapter?.notifyDataSetChanged()
    }

    private fun clearHistoryList(){
        historyList.clear()
        searchHistory.clearHistory()
    }

    private fun addTrackToHistoryList(track: Track) {

        if (historyList.contains(track)) {
            historyList.remove(track)
            historyList.add(0, track)
        } else {
            historyList.add(0, track)
        }

        if (historyList.size == SEARCH_HISTORY_SIZE + 1) {
            historyList.removeAt(SEARCH_HISTORY_SIZE)
        }

        searchHistory.saveHistory(historyList)
    }

    private fun search(expression: TracksSearchRequest) {
        binding.includedSearchHistory.parentLayout.visibility = View.GONE
        viewModel.search(expression.expression)
    }
}