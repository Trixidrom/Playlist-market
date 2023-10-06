package com.example.playlistmakettrix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.playlistmakettrix.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener{
            this.finish()
        }

        binding.clearButton.setOnClickListener {
            binding.searchText.setText("")
            setVisibilityClearButton()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setVisibilityClearButton()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
        binding.searchText.addTextChangedListener(simpleTextWatcher)
    }

    private fun setVisibilityClearButton(){
        if(binding.searchText.text.isEmpty()){
            binding.clearButton.visibility = View.INVISIBLE
        }else{
            binding.clearButton.visibility = View.VISIBLE
        }
    }
}