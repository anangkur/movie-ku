package com.anangkur.madesubmission1.feature.languageSetting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anangkur.madesubmission1.R
import com.anangkur.madesubmission1.data.local.SharedPreferenceHelper
import com.anangkur.madesubmission1.feature.main.MainActivity
import com.anangkur.madesubmission1.utils.Utils
import kotlinx.android.synthetic.main.activity_language_setting.*

class LanguageSettingActivity : AppCompatActivity(), LanguageSettingActionListener {

    private lateinit var presenter: LanguageSettingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_setting)

        setupToolbar()
        setupPresenter()
        presenter.loadLanguageSetting()
        setupChangeRadioLanguage()
        btn_simpan.setOnClickListener { this.onBtnSimpanClick() }
    }

    private fun setupToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.toolbar_setting_language)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupChangeRadioLanguage(){
        rg_language.setOnCheckedChangeListener { radioGroup, selectedId ->
            when(selectedId){
                R.id.rb_in -> presenter.saveLanguageSetting(getString(R.string.language_indonesian))
                R.id.rb_en -> presenter.saveLanguageSetting(getString(R.string.language_english))
            }
        }
    }

    private fun setupRadioLanguage(language: String){
        when (language){
            getString(R.string.language_indonesian) -> rg_language.check(R.id.rb_in)
            getString(R.string.language_english) -> rg_language.check(R.id.rb_en)
        }
    }

    private fun setupLanguage(language: String){
        when (language){
            getString(R.string.language_indonesian) -> Utils.setLocale(getString(R.string.language_indonesian), this)
            getString(R.string.language_english) -> Utils.setLocale(getString(R.string.language_english), this)
        }
    }

    private fun setupPresenter(){
        presenter = LanguageSettingPresenter(object : LanguageSettingView{
            override fun onLanguageReady(language: String?) {
                if (language != null){
                    setupLanguage(language)
                    setupRadioLanguage(language)
                }else{
                    setupLanguage(getString(R.string.language_english))
                    setupRadioLanguage(getString(R.string.language_english))
                }
            }
        }, SharedPreferenceHelper(this))
    }

    fun startActivity(context: Context){
        val intent = Intent(context, LanguageSettingActivity::class.java)
        context.startActivity(intent)
    }

    override fun onBtnSimpanClick() {
        MainActivity().startActivityClearBackToHome(this)
    }
}
