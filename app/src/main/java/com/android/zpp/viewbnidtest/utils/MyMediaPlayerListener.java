package com.android.zpp.viewbnidtest.utils;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest.utils
 * @ClassName: MyMediaPlayerListener
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/23 14:45
 * @UpdateUser:
 * @UpdateDate: 2022/5/23 14:45
 * @UpdateRemark:
 */
public interface MyMediaPlayerListener {
    /**
     * 获取播放状态
     * @param status 0播放  1 暂停
     */
    void onPlayStatus(int status);

    /**
     * 获取播放进度
     * @param  duration 总时长
     * @param press 播放进度 time
     */
    void onInPress(int duration ,int press);

    /**
     * 获取缓冲百分比
     * @param percent 缓冲百分比
     */
    void onBufferingUpdate(int percent);

    /**
     * 播放完成
     */
    void onFinish();

    /**
     * 出错
     * @param e
     */
    void onError(Exception e);
}
