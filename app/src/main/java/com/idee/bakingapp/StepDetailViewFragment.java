package com.idee.bakingapp;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static com.idee.bakingapp.RecipeDetailViewFragment.EXTRA_STEP_MODEL;
import static com.idee.bakingapp.RecipeDetailViewFragment.POSITION;

/**
 * A placeholder fragment containing a simple view.
 */
public class StepDetailViewFragment extends Fragment implements ExoPlayer.EventListener {

    private static final java.lang.String CURRENT_POSITION = "position";

    public StepDetailViewFragment() {}

    int currentPosition;
    int maxPosition;

    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView exoPlayerView;
    ImageButton forwardButton, backwardButton;
    ArrayList<StepModel> arrayList = new ArrayList<>();

    TextView textView;

    void bindNewDetails(){

        textView.setText(arrayList.get(currentPosition).getDescription());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_POSITION,currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_step_detail_view, container, false);

        textView = (TextView) view.findViewById(R.id.description);
        Bundle args = getArguments();
        if (args!=null) {
            currentPosition = args.getInt(POSITION);
            maxPosition = args.getParcelableArrayList(EXTRA_STEP_MODEL).size();
            arrayList = args.getParcelableArrayList(EXTRA_STEP_MODEL);
        }

        if (savedInstanceState!=null){
            currentPosition = savedInstanceState.getInt(CURRENT_POSITION);
        }

        exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.ep_video_instruction);
        //exoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),null));

        bindNewDetails();

        forwardButton = (ImageButton) view.findViewById(R.id.ib_forward);
        backwardButton = (ImageButton) view.findViewById(R.id.ib_backward);

        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition!=0){
                    currentPosition--;
                    bindNewDetails();
                }
                else
                    Toast.makeText(getActivity(), "You cant go back again", Toast.LENGTH_SHORT).show();
            }
        });


        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition!=maxPosition){
                    bindNewDetails();
                    currentPosition++;
                }
                else
                    Toast.makeText(getActivity(), "You cannot move further", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
