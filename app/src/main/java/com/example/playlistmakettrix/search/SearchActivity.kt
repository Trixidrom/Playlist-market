package com.example.playlistmakettrix.search

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmakettrix.GeneralConstants
import com.example.playlistmakettrix.MyLinkedHashSet
import com.example.playlistmakettrix.R
import com.example.playlistmakettrix.databinding.ActivitySearchBinding
import com.example.playlistmakettrix.hideKeyboard
import com.example.playlistmakettrix.retrofit.ApiService
import com.example.playlistmakettrix.search.models.Track
import com.example.playlistmakettrix.search.models.TracksSearchListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var sharPrefListener: OnSharedPreferenceChangeListener
    private var hasFocus = false
    private val historyList: MyLinkedHashSet<Int> = MyLinkedHashSet(10)
    companion object {
        private const val EDIT_TEXT_VALUE = "edit_text_value"
        private const val TRACK_LIST = "track_list"

        private const val FLIPPER_STATE = "flipper_state"
        private const val SUCCESS = 0
        private const val NOTHING_FOUND = 1
        private const val COMMUNICATION_PROBLEM = 2
        private const val SEARCH_HISTORY = 3

        private var lastFailedRequest = ""
    }

    private var trackList = arrayListOf<Track>()
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        historyList.add(0)
        historyList.add(1)
        historyList.add(2)
        historyList.add(3)
        historyList.add(4)
        historyList.add(5)
        historyList.add(6)
        historyList.add(7)
        historyList.add(8)
        historyList.add(9)
        historyList.add(10)
        historyList.getList().forEach {
            println(it)
        }
        println(historyList)
        //SharedPreffs
        val sharedPrefs = getSharedPreferences(GeneralConstants.PLAY_LIST_MAKET_SHARED_PREFF, MODE_PRIVATE)
        sharPrefListener = OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if(key == GeneralConstants.HISTORY_SHAR_PREF_KEY){

            }
        }
        sharedPrefs.registerOnSharedPreferenceChangeListener(sharPrefListener)

        binding.searchText.setOnFocusChangeListener { view, hasFocus ->
            this.hasFocus = hasFocus
            setVisibleSearchHistory()
        }

        binding.topAppBar.setNavigationOnClickListener{ this.finish() }

        binding.clearButtonCross.setOnClickListener {
            binding.searchText.setText("")
            trackList.clear()
            binding.trackList.adapter?.notifyDataSetChanged()
            hideKeyboard()
            setVisibilityClearButton()
        }
        binding.includedCommunicationProblem.update.setOnClickListener {
            search(lastFailedRequest)
        }
        binding.searchText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if(binding.searchText.text.isNotEmpty()){
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search(binding.searchText.text.toString())
                }
            }
            false
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setVisibilityClearButton()
                setVisibleSearchHistory()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
        binding.searchText.addTextChangedListener(simpleTextWatcher)
        binding.trackList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.trackList.adapter = TrackSearchListAdapter(trackList)
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
        }else{
            trackList.addAll(savedInstanceState.getParcelableArrayList(TRACK_LIST)!!)
        }
        binding.trackList.adapter?.notifyDataSetChanged()
    }

    private fun setVisibilityClearButton(){
        if(binding.searchText.text.isEmpty()){
            binding.clearButtonCross.visibility = View.INVISIBLE
        }else{
            binding.clearButtonCross.visibility = View.VISIBLE
        }
    }

    fun setVisibleSearchHistory(){
        if(hasFocus && binding.searchText.text.isEmpty()){
            binding.includedSearchHistory.parentLayout.visibility = View.VISIBLE
        }else{
            binding.includedSearchHistory.parentLayout.visibility = View.INVISIBLE
        }
    }

    private fun search(text: String){
        ApiService.musicService.searchTracks(text).enqueue(object : Callback<TracksSearchListModel> {
            override fun onResponse(call: Call<TracksSearchListModel>,
                                    response: Response<TracksSearchListModel>
            ) {
                when (response.code()) {
                    200 -> {
                        binding.viewFlipper.displayedChild = SUCCESS
                        if(response.body()?.trackList?.isNotEmpty() == true){
                            trackList.clear()
                            trackList.addAll(response.body()?.trackList!!)
                            binding.trackList.adapter?.notifyDataSetChanged()
                        }else{
                            binding.viewFlipper.displayedChild = NOTHING_FOUND
                        }

                    }
                    else -> Toast.makeText(this@SearchActivity, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<TracksSearchListModel>, t: Throwable) {
                binding.viewFlipper.displayedChild = COMMUNICATION_PROBLEM
                lastFailedRequest = binding.searchText.toString()
                t.printStackTrace()
            }
        })
    }
}