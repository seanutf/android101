package com.seanutf.android.base.media.select;

import androidx.lifecycle.MutableLiveData;

import com.seanutf.android.base.media.MediaUtil;
import com.seanutf.android.base.media.data.AlbumData;
import com.seanutf.android.base.media.data.Media;
import com.seanutf.android.base.media.data.MediaInfo;
import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.base.media.data.VideoEditLimitInfo;
import com.seanutf.android.commonutil.util.CollectionUtils;
import com.seanutf.android.commonutil.util.StringUtil;
import com.seanutf.cmmonui.arch.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseMediaSelectViewModel extends BaseViewModel {

    //private List<AlbumData> albumList = new ArrayList<>();// 扫描拿到所有的图片文件夹
    public List<MediaItem> mediaItemList = new ArrayList<>();//所有的图片


    private AlbumData mSelectedAlbumData;


    private String currentAlbum; //当前选中显示的文件夹，可以为null,为null时，默认获取全部文件
    private boolean initScan = true;


    private MutableLiveData<MediaInfo> mediaListInfo;


    MediaSelectManager.ScanMode scanMode; //扫描模式
    MediaSelectManager.AdapterMode adapterMode;
    VideoEditLimitInfo videoEditLimitInfo; //视频的宽高和码率
    int selectPhotoMaxNum;
    List<MediaItem> selectedImgList;//已选取图片列表
    String mediaSavePath; //自定义选中视频所保存的路径(视频编辑后)
    boolean needImgCrop; //单图模式下，图片是否需要裁剪
    boolean selectForChat; //是否为聊天选择的媒体
    int cropImgW; //剪切图片宽
    int cropImgH; //剪切图片高


    private List<AlbumData> albumList; // 扫描拿到所有的图片文件夹

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public List<AlbumData> getAlbumList() {
        if (albumList == null) {
            albumList = new ArrayList<AlbumData>();
        }
        return albumList;
    }

    public void setAlbumList(List<AlbumData> albumList) {
        this.albumList = albumList;
    }

    public void setCurrentAlbum(String albumDir) {
        this.currentAlbum = albumDir;
    }

    public MutableLiveData<MediaInfo> getMediaData() {
        if (mediaListInfo == null) {
            mediaListInfo = new MutableLiveData<MediaInfo>();
        }
        loadMediaList();
        return mediaListInfo;
    }

    private void loadMediaList() {
        final Observable<MediaInfo> observable = Observable.create(new ObservableOnSubscribe<MediaInfo>() {

            @Override
            public void subscribe(ObservableEmitter<MediaInfo> e) throws Exception {
                MediaInfo mediaInfo = new MediaInfo();
                switch (scanMode) {
                    case IMG:
                        //获取所有图片
                        mediaInfo = MediaUtil.scanImageFileAndAlbum(currentAlbum);
                        if (mediaInfo.imageList == null || mediaInfo.albumList == null) {
                            break;
                        }
                        if (CollectionUtils.isNotEmptyList(mediaInfo.imageList)) {

                            if (initScan) {
                                //设置"所有图片"这个虚拟相册的数据
                                AlbumData allImgAlbum = new AlbumData();
                                if (mediaInfo.imageList.get(0) != null) {
                                    allImgAlbum.setFirstImagePath(mediaInfo.imageList.get(0).mediaPath);
                                }
                                allImgAlbum.setDir(AlbumData.DIR_ALBUM_IMG);
                                allImgAlbum.setName("所有图片");
                                allImgAlbum.setCount(mediaInfo.imageList.size());

                                //将"所有图片"这个虚拟相册加入相册列表
                                mediaInfo.albumList.add(0, allImgAlbum);
                            }

                            for (AlbumData albumData : mediaInfo.albumList) {
                                if (albumData != null) {
                                    if (StringUtil.isEquals(currentAlbum, albumData.getDir())) {
                                        //设置当前选中的数据
                                        mSelectedAlbumData = albumData;
                                    }
                                }
                            }

                            initScan = false;
                        }
                        break;
                    case VIDEO:
                        //获取所有视频
                        List<MediaItem> videoList = MediaUtil.scanVideoFile(currentAlbum);

                        //设置图片列表
                        mediaItemList = videoList;

                        if (CollectionUtils.isNotEmptyList(videoList)) {

                            if (initScan) {
                                //设置"所有视频"这个虚拟相册的数据
                                AlbumData allVideoAlbum = new AlbumData();
                                if (videoList.get(0) != null) {
                                    allVideoAlbum.setFirstImagePath(videoList.get(0).mediaPath);
                                }
                                allVideoAlbum.setDir(AlbumData.DIR_ALBUM_VIDEO);
                                allVideoAlbum.setName("所有视频");
                                allVideoAlbum.setCount(videoList.size());

                                //将"所有视频"这个虚拟相册加入相册列表
                                albumList.add(allVideoAlbum);
                            }

                            for (AlbumData albumData : albumList) {
                                if (albumData != null) {
                                    if (StringUtil.isEquals(currentAlbum, albumData.getDir())) {
                                        //设置当前选中的数据
                                        mSelectedAlbumData = albumData;
                                    }
                                }
                            }
                            initScan = false;
                        }
                        break;
                    case ALL:
                        //获取所有
                        List<MediaItem> allVideoList = MediaUtil.scanVideoFile(currentAlbum);
                        if (!StringUtil.isEquals(currentAlbum, AlbumData.DIR_ALBUM_VIDEO)) {
                            mediaInfo = MediaUtil.scanMediaFile(allVideoList, currentAlbum);
                        }
                        if (StringUtil.isEquals(currentAlbum, AlbumData.DIR_ALBUM_VIDEO)) {
                            mediaItemList = allVideoList;
                            mediaInfo.imageVideoList = allVideoList;
                        } else {
                            mediaItemList = mediaInfo.imageVideoList;
                        }

                        if (initScan) {
                            AlbumData allVideoAlbum = new AlbumData();
                            AlbumData allAlbum = new AlbumData();

                            if (CollectionUtils.isNotEmptyList(allVideoList)) {
                                //设置"所有视频"这个虚拟相册的数据
                                if (allVideoList.get(0) != null) {
                                    allVideoAlbum.setFirstImagePath(allVideoList.get(0).mediaPath);
                                }
                                allVideoAlbum.setDir(AlbumData.DIR_ALBUM_VIDEO);
                                allVideoAlbum.setName("所有视频");
                                allVideoAlbum.setCount(allVideoList.size());
                            }

                            if (CollectionUtils.isNotEmptyList(mediaInfo.imageVideoList)) {
                                //设置"图片和视频"这个虚拟相册的数据
                                if (mediaInfo.imageVideoList.get(0) != null) {
                                    if (mediaInfo.imageVideoList.get(0).mediaType == Media.TYPE_MEDIA_VIDEO) {
                                        allAlbum.setFirstImagePath(mediaInfo.imageVideoList.get(0).mediaPath);
                                    } else {
                                        allAlbum.setFirstImagePath(mediaInfo.imageVideoList.get(0).mediaPath);
                                    }
                                }

                                allAlbum.setName("图片和视频");
                                allAlbum.setCount(mediaInfo.imageVideoList.size());
                                allAlbum.setDir(AlbumData.DIR_ALBUM_ALL);
                            }

                            if (allAlbum.getCount() > 0) {
                                //将"图片和视频"这个虚拟相册加入相册列表
                                albumList.add(0, allAlbum);
                            }
                            if (allVideoAlbum.getCount() > 0) {
                                //将"所有视频"这个虚拟相册加入相册列表
                                albumList.add(1, allVideoAlbum);
                            }

                            //获取所有相册，加入相册列表
                            albumList.addAll(mediaInfo.albumList);
                            initScan = false;
                        }

                        for (AlbumData albumData : albumList) {
                            if (albumData != null) {
                                if (StringUtil.isEquals(currentAlbum, albumData.getDir())) {
                                    //设置当前选中的数据
                                    mSelectedAlbumData = albumData;
                                }
                            }
                        }

                        break;
                }

                e.onNext(mediaInfo);
                e.onComplete();
            }

        });
        DisposableObserver<MediaInfo> disposableObserver = new DisposableObserver<MediaInfo>() {

            @Override
            public void onNext(MediaInfo info) {
                mediaListInfo.setValue(info);
            }

            @Override
            public void onError(Throwable e) {
                //                Log.d("BackgroundActivity", "onError=" + e);
                //                mTvDownloadResult.setText("Download Error");
            }

            @Override
            public void onComplete() {
                //                Log.d("BackgroundActivity", "onComplete");
                //                mTvDownloadResult.setText("Download onComplete");
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }

    public List<MediaItem> getSelectedImageList() {
        return selectedImgList;
    }

    public void setConfig(MediaSelectManager.ScanMode scanMode,
                          MediaSelectManager.AdapterMode adapterMode,
                          VideoEditLimitInfo videoEditLimitInfo,
                          int selectPhotoMaxNum,
                          List<MediaItem> selectedImgList,
                          String mediaSavePath,
                          boolean needImgCrop,
                          boolean selectForChat,
                          int cropImgW,
                          int cropImgH) {
        this.scanMode = scanMode;
        this.adapterMode = adapterMode;
        this.videoEditLimitInfo = videoEditLimitInfo;
        this.selectPhotoMaxNum = selectPhotoMaxNum;
        this.selectedImgList = selectedImgList;
        this.mediaSavePath = mediaSavePath;
        this.needImgCrop = needImgCrop;
        this.selectForChat = selectForChat;
        this.cropImgW = cropImgW;
        this.cropImgH = cropImgH;

    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.clear();
    }
}
