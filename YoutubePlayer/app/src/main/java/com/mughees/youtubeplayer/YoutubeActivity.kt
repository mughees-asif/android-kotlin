package com.mughees.youtubeplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "F9UC9DY-vIU" // sample video
const val YOUTUBE_PLAYLIST = "PLDnx7w_xuguFTxcfiM11bB1JchtHclEJg"  // sample playlist

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "YoutubeActivity"
    private val DIALOG_REQUEST_CODE = 1
    // lazy added to delay until YouTube is instantiated
    val playerView by lazy { YouTubePlayerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)
        // youtube layout view widget
        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.addView(playerView)
        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this) // initialisation
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.d(TAG, "onInitialisationSuccess: provider is ($provider?.javaClass")
        Log.d(TAG, "onInitialisationSuccess: youTubePlayer is ($youTubePlayer?.javaClass")
        Toast.makeText(this, "Initialised Youtube Player successfully", Toast.LENGTH_SHORT).show()

        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)
        if (!wasRestored) {
            youTubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        // not dealing with a boolean here; nullable boolean hence, the ?
        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error in initialising the YoutubePlayer " +
                    "($youTubeInitializationResult)"
            Toast.makeText(this, errorMessage,Toast.LENGTH_LONG).show()
        }
    }

    // event listeners for the screen
    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity, "Good, video is playing fine", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity, "Video has stopped", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "Video has paused", Toast.LENGTH_SHORT).show()
        }
    }

    // event listeners for state changes
    private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity, "Click Ad!", Toast.LENGTH_SHORT).show()
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity, "Video started", Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity, "Congratulations, you have completed another video", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult called with responseCode $resultCode for request $requestCode")
        if(requestCode == DIALOG_REQUEST_CODE)
            Log.d(TAG, intent?.toString())
            Log.d(TAG, intent?.extras.toString())
            playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
    }
}
