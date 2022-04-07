package com.inferno.mobile.bedon_waseet.utils

import android.content.Context
import android.content.SharedPreferences


private const val SHARED_NAME = "shared"
private const val LOGGED_IN = "logged_in"
private const val USER_TYPE = "user_type"
private const val TOKEN = "token"
fun isUserLoggedIn(context: Context): Boolean {
    return shared(context).getBoolean(LOGGED_IN, false)
}

fun logIn(context: Context, token: String, type: UserType) {
    sharedEdit(shared(context))
        .putString(TOKEN, token)
        .putString(USER_TYPE, type.name)
        .putBoolean(LOGGED_IN, true)
        .apply()
}

fun getUserType(context: Context): UserType? {
    return if (isUserLoggedIn(context))
        UserType.valueOf(shared(context).getString(USER_TYPE, UserType.Gust.name)!!)
    else null
}

fun logOut(context: Context) {
    sharedEdit(shared(context))
        .remove(TOKEN)
        .remove(USER_TYPE)
        .remove(LOGGED_IN)
        .apply()
}

private fun shared(context: Context): SharedPreferences {
    return context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
}

private fun sharedEdit(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
    return sharedPreferences.edit()
}


/* private fun initPlayer() {
        player = SimpleExoPlayer.Builder(this)
            .build()
            .also { simpleExoPlayer ->
                val rootPath = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                )
                val path = getString(R.string.file_path, rootPath.path)
                println(path)

                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    this,)
                val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.fromFile(File(path)))
                simpleExoPlayer.prepare(videoSource)
                (viewBinding.videoView.videoSurfaceView as SphericalGLSurfaceView?)!!
                    .setDefaultStereoMode(
                        C.STEREO_MODE_TOP_BOTTOM
                    )
                viewBinding.videoView.player = simpleExoPlayer
            }
    }
*/
