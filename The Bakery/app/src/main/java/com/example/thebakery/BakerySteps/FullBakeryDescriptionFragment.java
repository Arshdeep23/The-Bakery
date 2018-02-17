package com.example.thebakery.BakerySteps;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebakery.Desc.BakeryDetailedList;
import com.example.thebakery.Desc.StepBakeryFragmentContent;
import com.example.thebakery.TheBakeryActivity;
import com.example.thebakery.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FullBakeryDescriptionFragment extends Fragment{




    private BandwidthMeter meter;
    private DefaultTrackSelector defaultTrackSelector;
    public static boolean bakery_shouldAutoPlay =true;
    private Timeline.Window bakery_window;
    private DataSource.Factory factory;
    private SimpleExoPlayer exoPlayer;

    public static long video_playbackPosition =0;
    public static final String STRING_SAVE_PLAYBACK_POSITION = "playback_position";
    private static final String RESTORE_VIDEO_PLAY_STATE ="playstate";
    private Unbinder my_unbinder;
    private static final String IS_VIDEO_AVAILABLE = "videoAvailablility";
    private static final String SELECT_ON_STATE = "SelectOn";
    @BindView(R.id.bakery_navigation) BottomNavigationView bakery_navigation;
    @BindView(R.id.bakeryThumbnailImageView) ImageView bakeryThumbnailImageView;
    @BindView(R.id.player_view) SimpleExoPlayerView player_view;
    @BindView(R.id.bakery_frameLayout) FrameLayout bakery_frameLayout;
    @BindView(R.id.bakery_complete_short_desc) TextView bakery_short_desc;
    @BindView(R.id.bakery_complete_desc) TextView bakery_complete_desc;
    @BindView(R.id.bakery_all_content_description) RelativeLayout bakery_all_content_description;
    private static boolean video_available_flag;
    public static int bakery_temp_flag = StepBakeryFragmentContent.my_bakery_ultimateFlag;
    public static int bakery_temp_selection = StepBakeryFragmentContent.bakery_current_selection;

    private void releaseVideoPlayerResources() {
        if (exoPlayer != null) {
            bakery_shouldAutoPlay = exoPlayer.getPlayWhenReady();
            exoPlayer.release();
            defaultTrackSelector = null;
            exoPlayer = null;
            player_view.destroyDrawingCache();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        update_populated_list();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releaseVideoPlayerResources();
            Log.e("STATUS:", bakery_temp_selection +" @onStop");
        }
    }

    private void update_player_settings() {
        player_view.requestFocus();
        TrackSelection.Factory factory1 =
                new AdaptiveTrackSelection.Factory(meter);
        defaultTrackSelector = new DefaultTrackSelector(factory1);
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), defaultTrackSelector);
        player_view.setPlayer(exoPlayer);
        DefaultExtractorsFactory defaultExtractorsFactory = new DefaultExtractorsFactory();
        exoPlayer.setPlayWhenReady(bakery_shouldAutoPlay);
        MediaSource extractorMediaSource = new ExtractorMediaSource(Uri.parse(TheBakeryActivity.videoURL[BakeryDetailedList.id][bakery_temp_selection]),
                factory, defaultExtractorsFactory, null, null);
        exoPlayer.seekTo(video_playbackPosition);
        exoPlayer.prepare(extractorMediaSource);
    }

    private void update_populated_list(){
        if(TheBakeryActivity.videoURL[BakeryDetailedList.id][bakery_temp_selection].equals("")){
            if(!TheBakeryActivity.thumbnailURL[BakeryDetailedList.id][bakery_temp_selection].equals("")){
                bakeryThumbnailImageView.setVisibility(View.VISIBLE);
                Picasso.with(getContext())
                        .load(TheBakeryActivity.thumbnailURL[BakeryDetailedList.id][bakery_temp_selection])
                        .into(bakeryThumbnailImageView);
            }
            player_view.setVisibility(View.GONE);
            video_available_flag =false;
        }else{
            bakeryThumbnailImageView.setVisibility(View.GONE);
            video_available_flag =true;
            player_view.setVisibility(View.VISIBLE);
            meter = new DefaultBandwidthMeter();
            factory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "mediaPlayerSample"), (TransferListener<? super DataSource>) meter);
            bakery_window = new Timeline.Window();
            if(exoPlayer ==null) {
                update_player_settings();
            }
        }
        if(TheBakeryActivity.bakery_tabletSize){
            bakery_frameLayout.setVisibility(View.GONE);
        }else{
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                bakery_all_content_description.setVisibility(View.VISIBLE);
                bakery_frameLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams player_viewLayoutParams = (LinearLayout.LayoutParams) player_view.getLayoutParams();
                player_viewLayoutParams.width = player_viewLayoutParams.MATCH_PARENT;
                player_viewLayoutParams.height = 700;
                player_view.setLayoutParams(player_viewLayoutParams);
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && video_available_flag) {
                bakery_all_content_description.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) player_view.getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = params.MATCH_PARENT;
                player_view.setLayoutParams(params);
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                bakery_frameLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_VIDEO_AVAILABLE, video_available_flag);
        outState.putInt(SELECT_ON_STATE, bakery_temp_selection);
        if(video_available_flag) {
            video_playbackPosition = exoPlayer.getCurrentPosition();
        }else{
            video_playbackPosition =0;
        }
        if(exoPlayer !=null) {
            bakery_shouldAutoPlay = exoPlayer.getPlayWhenReady();
        }
        outState.putLong(STRING_SAVE_PLAYBACK_POSITION, video_playbackPosition);
        outState.putBoolean(RESTORE_VIDEO_PLAY_STATE, bakery_shouldAutoPlay);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bakery_navi_left:
                    if(bakery_temp_selection >0) {
                        video_playbackPosition =0;
                        bakery_shouldAutoPlay = true;
                        bakery_temp_selection -= 1;
                        if(!(TheBakeryActivity.videoURL[BakeryDetailedList.id][bakery_temp_selection +1].equals(""))){
                            releaseVideoPlayerResources();
                        }
                        update_populated_list();
                    }else{
                        Toast.makeText(getContext(),"No Previous Contents",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.bakery_navi_right:
                    if(bakery_temp_selection < bakery_temp_flag -1) {
                        bakery_shouldAutoPlay = true;
                        bakery_temp_selection +=1;
                        video_playbackPosition =0;
                        if(!(TheBakeryActivity.videoURL[BakeryDetailedList.id][bakery_temp_selection -1].equals(""))){
                            releaseVideoPlayerResources();
                        }
                        update_populated_list();
                    }else{
                        Toast.makeText(getContext(),"Can't navigate furthur",Toast.LENGTH_SHORT).show();
                    }
                    return true;
            }
            return false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bakery_full_desc, container, false);
        my_unbinder = ButterKnife.bind(this,rootView);
        if(!TheBakeryActivity.bakery_tabletSize) {
            if (savedInstanceState != null) {
                video_playbackPosition = savedInstanceState.getLong(STRING_SAVE_PLAYBACK_POSITION);
                video_available_flag = savedInstanceState.getBoolean(IS_VIDEO_AVAILABLE);
                bakery_temp_selection = savedInstanceState.getInt(SELECT_ON_STATE);
                bakery_shouldAutoPlay = savedInstanceState.getBoolean(RESTORE_VIDEO_PLAY_STATE);
            }
        }
        bakery_complete_desc.setText(TheBakeryActivity.description[BakeryDetailedList.id][bakery_temp_selection]);
        bakery_short_desc.setText(TheBakeryActivity.shortDescription[BakeryDetailedList.id][bakery_temp_selection]);
        bakery_navigation.setOnNavigationItemSelectedListener(selectedListener);
        return rootView;
    }


    public FullBakeryDescriptionFragment() {
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        my_unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releaseVideoPlayerResources();
        }
    }

}
