package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.restrictcodec;

import android.content.Context;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.SingleDialogSource;

import java.util.ArrayList;
import java.util.List;

public class RestrictFormatDialogSource implements SingleDialogSource {
    private final ExoPreferences mPrefs;
    private final Context mContext;
    private final List<DialogItem> mItems;

    public RestrictFormatDialogSource(Context ctx) {
        mContext = ctx;
        mPrefs = ExoPreferences.instance(ctx);

        mItems = new ArrayList<>();
        mItems.add(new RestrictFormatDialogItem(mContext.getString(R.string.no_restrictions), ExoPreferences.FORMAT_ANY, mPrefs));
        mItems.add(new RestrictFormatDialogItem("4K     60fps    vp9", "2160|60|vp9", mPrefs));
        mItems.add(new RestrictFormatDialogItem("4K     30fps    vp9", "2160|30|vp9", mPrefs));
        mItems.add(new RestrictFormatDialogItem("FHD    60fps    avc", "1080|60|avc", mPrefs));
        mItems.add(new RestrictFormatDialogItem("FHD    60fps    vp9", "1080|60|vp9", mPrefs));
        mItems.add(new RestrictFormatDialogItem("FHD    30fps    avc", "1080|30|avc", mPrefs));
        mItems.add(new RestrictFormatDialogItem("FHD    30fps    vp9", "1080|30|vp9", mPrefs));
        mItems.add(new RestrictFormatDialogItem("FHD    60fps    avc+vp9", "1080|60|", mPrefs));
        mItems.add(new RestrictFormatDialogItem("FHD    30fps    avc+vp9", "1080|30|", mPrefs));
        mItems.add(new RestrictFormatDialogItem("HD     30fps    avc", "720|30|avc", mPrefs));
        mItems.add(new RestrictFormatDialogItem("HD     30fps    vp9", "720|30|vp9", mPrefs));
    }

    @Override
    public List<DialogItem> getItems() {
        return mItems;
    }

    @Override
    public String getTitle() {
        return mContext.getString(R.string.select_preferred_codec);
    }
}
