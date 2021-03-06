package com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.externalplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.ExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.wrappers.server.MyContentServer;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.OnMediaFoundCallback;

import java.io.InputStream;

public class ExternalPlayerWrapper extends OnMediaFoundCallback {
    private static final String TAG = ExternalPlayerWrapper.class.getSimpleName();
    private final Context mContext;
    private final ExoInterceptor mInterceptor;
    private MyContentServer mServer;
    private static final int TYPE_DASH_CONTENT = 0;
    private static final int TYPE_DASH_URL = 1;
    private static final int TYPE_HLS_URL = 2;
    private int mContentType;
    private Uri mDashUrl;
    private Uri mHlsUrl;

    public ExternalPlayerWrapper(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
        initServer();
    }

    private void initServer() {
        mServer = new MyContentServer();
    }

    @Override
    public void onDashMPDFound(InputStream mpdContent) {
        mServer.setDashContent(mpdContent);
        mContentType = TYPE_DASH_CONTENT;
    }

    @Override
    public void onDashUrlFound(Uri dashUrl) {
        mDashUrl = dashUrl;
        mContentType = TYPE_DASH_URL;
    }

    @Override
    public void onHLSFound(Uri hlsUrl) {
        mHlsUrl = hlsUrl;
        mContentType = TYPE_HLS_URL;
    }

    @Override
    public void onDone() {
        openExternalPlayer();
    }

    private void openExternalPlayer() {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        switch (mContentType) {
            case TYPE_DASH_CONTENT:
                intent.setDataAndType(Uri.parse(mServer.getDashUrl()), "video/*");
                break;
            case TYPE_DASH_URL:
                intent.setDataAndType(mDashUrl, "video/*"); // mpd
                break;
            case TYPE_HLS_URL:
                intent.setDataAndType(mHlsUrl, "application/x-mpegURL"); // m3u8
                break;
            default:
                Log.d(TAG, "Unrecognized content type");
                break;
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
