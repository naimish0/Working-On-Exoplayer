package com.animation.exoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.animation.exoplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class MainActivity : AppCompatActivity() {
    private val viewBinding by lazy(LazyThreadSafetyMode.NONE){
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var exoPlayer:ExoPlayer?=null
    private var playwhenReady=true
    private var playbackPosition=0L
    private var currentWindow=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        initPlayer()

    }
    private fun initPlayer()
    {
        exoPlayer=ExoPlayer.Builder(this)
            .build()
            .also {
                viewBinding.videoView.player=it
                val mediaItem=MediaItem.fromUri(getString(R.string.url_to_play))
                it.setMediaItem(mediaItem)
            }
        exoPlayer?.playWhenReady=playwhenReady
        exoPlayer?.seekTo(currentWindow,playbackPosition)
        exoPlayer?.prepare()

    }
    private fun hideSystemUI()
    {
        viewBinding.videoView.systemUiVisibility=(View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
    private fun releasePlayer()
    {
        exoPlayer?.run {
            playbackPosition=this.currentPosition
            currentWindow=this.currentMediaItemIndex
            playwhenReady=this.playWhenReady
            release()
        }
        exoPlayer=null
    }
    override fun onStart() {
        super.onStart()
        initPlayer()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

}