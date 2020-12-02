package com.chrisjaunes.communication.client.utils;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioTrackerHelper {
    public AudioTrack createAudioTrack(int sampleRate, int channels) {
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        int channelConfig;
        if (channels == 1) {//单声道
            channelConfig = android.media.AudioFormat.CHANNEL_OUT_MONO;
        } else if (channels == 2) {//立体声，即双声道
            channelConfig = android.media.AudioFormat.CHANNEL_OUT_STEREO;
        } else {
            channelConfig = android.media.AudioFormat.CHANNEL_OUT_STEREO;
        }

        int bufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);
        //第一个参数：声音的类型，有以下几种
        //STREAM_VOICE_CALL:电话声音
        //STREAM_SYSTEM:系统声音
        //STREAM_RING:铃声
        //STREAM_MUSIC:音乐声
        //STREAM_ALARM:警告声
        //STREAM_NOTIFICATION:通知声
        //第二个参数：采样频率，可选：8000，16000，22050，24000，32000，44100，48000等
        //第三个参数：声道数
        //第四个参数：采样格式，AudioFormat.ENCODING_PCM_16BIT，AudioFormat.ENCODING_PCM_8BIT
        //第五个参数：其配置AudioTrack内部的音频缓冲区大小，最好通过getMinBufferSize来计算
        //第六个参数：播放模式，MODE_STATIC需要一次性将所有的数据都写入播放缓冲区，简单高效，通常用于铃声，系统提示音的播放，MODE_STREAM需要按照一定的时间间隔不间断的写入音频数据，理论上可以用于任何音频场景，通常用来播放流媒体音频
        return new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, channelConfig, audioFormat,
                bufferSizeInBytes, AudioTrack.MODE_STREAM);
    }
    private void initAudioTracker() {
        // 扬声器播放
        int streamType = AudioManager.STREAM_MUSIC;//当前3(0-7)
        // 播放的采样频率 和录制的采样频率一样
        int sampleRate = 8000;
        // 和录制的一样的
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        // 流模式
        int mode = AudioTrack.MODE_STREAM;
        // 录音用输入单声道  播放用输出单声道
        int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        int recBufSize = AudioTrack.getMinBufferSize(
                sampleRate,//-------采样率，每秒8000个采样点-------
                channelConfig,//-------录音用输入单声道-------
                audioFormat);//-------设置音频数据块是8位还是16位。这里设置为16位。
        System.out.println("****playRecBufSize = " + recBufSize);
//        new AudioTrack()
//
//        audioTrk = new AudioTrack(
//                streamType,// -------指定流的类型--->当前3(0-7)-------
//                sampleRate,// -------设置音频数据的采样率，假设是44.1k就是8000-------
//                channelConfig,// -------设置输出声道为双声道立体声，而CHANNEL_OUT_MONO类型是单声道(录音用输入单声道  播放用输出单声道)-------
//                audioFormat,// -------设置音频数据块是8位还是16位。这里设置为16位。
//                recBufSize,//-------根据要播放的音频文件的参数获得最小的buffer大小-------
//                mode);//-------流模式-------
//        //audioTrk.setStereoVolume(AudioTrack.getMaxVolume(),
//        //        AudioTrack.getMaxVolume());//-------设置音量-------
//        buffer = new byte[recBufSize];//-------缓存数据-------

    }
}
