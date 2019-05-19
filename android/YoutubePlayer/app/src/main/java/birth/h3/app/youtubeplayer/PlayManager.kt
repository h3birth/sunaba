package birth.h3.app.youtubeplayer

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.util.Log
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceEventListener
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.io.IOException

class PlayerManager(val context: Context, val appName: String) {
    lateinit var mediaSource: MediaSource
    lateinit var player: SimpleExoPlayer
    lateinit var playerView: PlayerView

    private val defaultRenderersFactory by lazy { DefaultRenderersFactory(context) }
    private val bandwidthMeter by lazy { DefaultBandwidthMeter() }
    private val videoTrackSelectionFactory by lazy { AdaptiveTrackSelection.Factory() }
    private val defaultTrackSelector by lazy { DefaultTrackSelector(videoTrackSelectionFactory) }
    private val dataSourceFactory by lazy { buildDataSourceFactory() }
    private val loadControl by lazy { DefaultLoadControl() }
    private val userAgent by lazy { Util.getUserAgent(context, appName) }
    private val handle by lazy { Handler() }

    private val eventListener = object : MediaSourceEventListener {
        override fun onLoadStarted(
            windowIndex: Int,
            mediaPeriodId: MediaSource.MediaPeriodId?,
            loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
            mediaLoadData: MediaSourceEventListener.MediaLoadData?
        ) {
            Log.d("ExoPlayer", "start")
        }

        override fun onLoadCompleted(
            windowIndex: Int,
            mediaPeriodId: MediaSource.MediaPeriodId?,
            loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
            mediaLoadData: MediaSourceEventListener.MediaLoadData?
        ) {
            Log.e("ExoPlayer", "complete")
        }

        override fun onLoadCanceled(
            windowIndex: Int,
            mediaPeriodId: MediaSource.MediaPeriodId?,
            loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
            mediaLoadData: MediaSourceEventListener.MediaLoadData?
        ) {
            Log.e("ExoPlayer", "cancel")
        }

        override fun onLoadError(
            windowIndex: Int,
            mediaPeriodId: MediaSource.MediaPeriodId?,
            loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
            mediaLoadData: MediaSourceEventListener.MediaLoadData?,
            error: IOException?,
            wasCanceled: Boolean
        ) {
            Log.e("ExoPlayer", "error " + error.toString())
        }

        override fun onUpstreamDiscarded(
            windowIndex: Int,
            mediaPeriodId: MediaSource.MediaPeriodId?,
            mediaLoadData: MediaSourceEventListener.MediaLoadData?
        ) {
            Log.e("ExoPlayer", "onUpstreamDiscarded")
        }

        override fun onDownstreamFormatChanged(
            windowIndex: Int,
            mediaPeriodId: MediaSource.MediaPeriodId?,
            mediaLoadData: MediaSourceEventListener.MediaLoadData?
        ) {
            Log.e("ExoPlayer", "onDownstreamFormatChanged")
        }

        override fun onMediaPeriodCreated(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {
            Log.e("ExoPlayer", "onMediaPeriodCreated")
        }

        override fun onMediaPeriodReleased(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {
            Log.e("ExoPlayer", "onMediaPeriodReleased")
        }

        override fun onReadingStarted(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {
            Log.e("ExoPlayer", "onReadingStarted")
        }
    }

    /** Returns a new DataSource factory.  */
    private fun buildDataSourceFactory(): DataSource.Factory {
        return DefaultDataSourceFactory(context, DefaultHttpDataSourceFactory(userAgent))
    }

    @SuppressLint("SwitchIntDef")
    fun buildMediaSource(uri: Uri, overrideExtension: String?) {
        @C.ContentType val type: Int = Util.inferContentType(uri, overrideExtension)
        mediaSource = when (type) {
            C.TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_SS -> SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_OTHER -> ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            else -> throw IllegalStateException("Unsupported type: " + type)
        }

        mediaSource.addEventListener(handle, eventListener)

        player.prepare(mediaSource)
        player.playWhenReady = true
    }

    fun playserNewInstatnce() {
        player = ExoPlayerFactory.newSimpleInstance(context, defaultTrackSelector)
    }

    fun execute() {
        playerView.player = player
    }

    fun release() {
        if (player != null) player.release()
    }

    fun stop() {
        if (player != null) player.playWhenReady = false
    }

    fun replay() {
        if (player != null) player.playWhenReady = true
    }
}
