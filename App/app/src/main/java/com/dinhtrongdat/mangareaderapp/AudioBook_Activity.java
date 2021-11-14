package com.dinhtrongdat.mangareaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dinhtrongdat.mangareaderapp.model.Chapter;
import com.dinhtrongdat.mangareaderapp.model.Manga;
import com.makeramen.roundedimageview.RoundedImageView;

public class AudioBook_Activity extends AppCompatActivity {

    RoundedImageView imgDetail;
    TextView txtName, txtChapter, txtTime;
    ImageView imgPlay;
    SeekBar seekBar;
    Chapter chapter;
    Manga audio;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_book);
        chapter = (Chapter) getIntent().getSerializableExtra("audio");
        audio = (Manga) getIntent().getSerializableExtra("detail_audio");
        initUI();
    }

    private void initUI() {
        mediaPlayer = new MediaPlayer();
        imgDetail = findViewById(R.id.imgAudio);
        txtName = findViewById(R.id.txtNameAudio);
        txtChapter = findViewById(R.id.txtChapterAudio);
        txtTime = findViewById(R.id.txtTimeAudio);
        imgPlay = findViewById(R.id.imgPlayAudio);
        seekBar = findViewById(R.id.seekbarAudio);

        Glide.with(this).load(audio.getImage()).into(imgDetail);
        txtName.setText(audio.getName());
        txtChapter.setText(chapter.getName());
        seekBar.setMax(100);

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.icons8_play_50px);
                }
                else{
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.icons8_pause_50px);
                    updateSeekBar();
                }
            }
        });
        prepareMediaPlayer();

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar sb = (SeekBar) v;
                int playPosition = (mediaPlayer.getDuration() / 100) * sb.getProgress();
                mediaPlayer.seekTo(playPosition);
                txtTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
    }

    private void prepareMediaPlayer(){
        try{
            mediaPlayer.setDataSource(chapter.getLinks().get(0));
            mediaPlayer.prepare();

        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration =mediaPlayer.getCurrentPosition();
            txtTime.setText(milliSecondsToTimer(currentDuration));
        }
    };

    private  void updateSeekBar(){
        if(mediaPlayer.isPlaying()){
            seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration())* 100));
            handler.postDelayed(updater, 1000);
        }
    }

    private String milliSecondsToTimer(long milliSeconds){
        String timeString = "";
        String secondsString;

        int hours = (int)(milliSeconds / (1000 * 60 * 60));
        int minutes = (int)((milliSeconds % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int)((milliSeconds % (1000 * 60*60)) % (1000 * 60) /1000);

        if(hours > 0){
            timeString = hours + ":";
        }

        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;
        }

        timeString +=timeString + minutes + ":" + secondsString;
        return timeString;
    }
}