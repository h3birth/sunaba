package birth.h3.app.youtubeplayer

import android.content.Context
import android.util.Pair
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil
import com.google.android.exoplayer2.util.ErrorMessageProvider

class PlayerErrorMessageProvider(val context: Context) : ErrorMessageProvider<ExoPlaybackException> {
    override fun getErrorMessage(e: ExoPlaybackException): Pair<Int, String> {
        var errorString = context.getString(R.string.error_generic)
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            val cause = e.rendererException
            if (cause is MediaCodecRenderer.DecoderInitializationException) {
                // Special case for decoder initialization failures.
                if (cause.decoderName == null) {
                    if (cause.cause is MediaCodecUtil.DecoderQueryException) {
                        errorString = context.getString(R.string.error_querying_decoders)
                    } else if (cause.secureDecoderRequired) {
                        errorString = context.getString(
                            R.string.error_no_secure_decoder)
                    } else {
                        errorString = context.getString(R.string.error_no_decoder)
                    }
                } else {
                    errorString = context.getString(R.string.error_instantiating_decoder)
                }
            }
        }
        return Pair.create(0, errorString)
    }
}
