package com.d.music.transfer.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.d.lib.aster.callback.ProgressCallback;
import com.d.lib.common.view.dialog.AbsSheetDialog;
import com.d.lib.xrv.adapter.CommonAdapter;
import com.d.lib.xrv.adapter.CommonHolder;
import com.d.lib.xrv.adapter.MultiItemTypeSupport;
import com.d.music.R;
import com.d.music.component.media.HitTarget;
import com.d.music.component.media.controler.MediaControler;
import com.d.music.data.Constants;
import com.d.music.data.database.greendao.bean.TransferModel;
import com.d.music.transfer.fragment.TransferFragment;
import com.d.music.transfer.manager.Transfer;
import com.d.music.transfer.manager.TransferManager;
import com.d.music.transfer.manager.operation.Operater;
import com.d.music.view.CircleProgressBar;
import com.d.music.view.dialog.OperationDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TransferAdapter
 * Created by D on 2018/8/25.
 */
public class TransferAdapter extends CommonAdapter<TransferModel> {
    private int mType;

    public TransferAdapter(Context context, List<TransferModel> datas, int type,
                           MultiItemTypeSupport<TransferModel> multiItemTypeSupport) {
        super(context, datas, multiItemTypeSupport);
        this.mType = type;
    }

    @Override
    public void convert(final int position, final CommonHolder holder, final TransferModel item) {
        switch (holder.mLayoutId) {
            case R.layout.module_transfer_adapter_head_downloading:
                coverHeadDownloading(holder, item);
                break;
            case R.layout.module_transfer_adapter_head_downloaded:
                coverHeadDownloaded(position, holder, item);
                break;
            case R.layout.module_transfer_adapter_song:
                coverSong(position, holder, item);
                break;
            case R.layout.module_transfer_adapter_mv:
                coverMV(position, holder, item);
                break;
        }
    }

    private void coverHeadDownloading(final CommonHolder holder, final TransferModel item) {
        holder.setViewOnClickListener(R.id.tv_clear_task, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOperater().clear(0);
            }
        });
        holder.setViewOnClickListener(R.id.tv_pause_all, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOperater().pauseAll();
            }
        });
    }

    private void coverHeadDownloaded(final int position, final CommonHolder holder, final TransferModel item) {
        holder.setViewOnClickListener(R.id.tv_clear_task, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOperater().clear(1);
            }
        });
    }

    private void coverSong(final int position, final CommonHolder holder, final TransferModel item) {
        coverMedia(position, holder, item);
        holder.setViewOnClickListener(R.id.iv_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperationDialog.getOperationDialog(mContext, OperationDialog.TYPE_NORMAL, "",
                        Arrays.asList(new OperationDialog.Bean().with(mContext, OperationDialog.Bean.TYPE_ADDLIST, false),
                                new OperationDialog.Bean().with(mContext, OperationDialog.Bean.TYPE_DELETE, false),
                                new OperationDialog.Bean().with(mContext, OperationDialog.Bean.TYPE_INFO, false)),
                        new AbsSheetDialog.OnItemClickListener<OperationDialog.Bean>() {
                            @Override
                            public void onClick(Dialog dlg, int position, OperationDialog.Bean bean) {
                                if (bean.type == OperationDialog.Bean.TYPE_ADDLIST) {
                                    com.d.music.component.operation.Operater.addToList(mContext,
                                            -1, TransferModel.convertTo(item));
                                } else if (bean.type == OperationDialog.Bean.TYPE_DELETE) {
                                    getOperater().remove(item);
                                } else if (bean.type == OperationDialog.Bean.TYPE_INFO) {
                                    com.d.music.component.operation.Operater.showInfo(mContext,
                                            TransferModel.convertTo(item));
                                }
                            }

                            @Override
                            public void onCancel(Dialog dlg) {

                            }
                        });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<List<TransferModel>> lists = getOperater().pipe().lists();
                List<TransferModel> list = new ArrayList<>();
                list.addAll(lists.get(0));
                list.addAll(lists.get(1));
                int div = lists.get(0).size() > 0 ? 1 : 0;
                div += item.transferState == TransferModel.TRANSFER_STATE_DONE ? 1 : 0;
                MediaControler.getIns(mContext).init(TransferModel.convertTo(list), position - div, true);
            }
        });
    }

    private void coverMV(final int position, final CommonHolder holder, final TransferModel item) {
        coverMedia(position, holder, item);
        final String mvUrl = HitTarget.secondPassMV(item) ?
                Constants.Path.mv + File.separator + item.songName + Transfer.PREFIX_MV : item.songUrl;
        Glide.with(mContext).load(mvUrl)
                .apply(new RequestOptions().dontAnimate())
                .into((ImageView) holder.getView(R.id.iv_cover));
        holder.setViewOnClickListener(R.id.iv_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperationDialog.getOperationDialog(mContext, OperationDialog.TYPE_NORMAL, "",
                        Arrays.asList(new OperationDialog.Bean().with(mContext, OperationDialog.Bean.TYPE_DELETE, false),
                                new OperationDialog.Bean().with(mContext, OperationDialog.Bean.TYPE_INFO, false)),
                        new AbsSheetDialog.OnItemClickListener<OperationDialog.Bean>() {
                            @Override
                            public void onClick(Dialog dlg, int position, OperationDialog.Bean bean) {
                                if (bean.type == OperationDialog.Bean.TYPE_DELETE) {
                                    getOperater().remove(item);
                                } else if (bean.type == OperationDialog.Bean.TYPE_INFO) {
                                    com.d.music.component.operation.Operater.showInfo(mContext,
                                            TransferModel.convertTo(item));
                                }
                            }

                            @Override
                            public void onCancel(Dialog dlg) {

                            }
                        });
            }
        });
    }

    private void coverMedia(final int position, final CommonHolder holder, final TransferModel item) {
        holder.setViewVisibility(R.id.tv_singer, item.transferState == TransferModel.TRANSFER_STATE_PROGRESS ?
                View.INVISIBLE : View.VISIBLE);
        holder.setViewVisibility(R.id.flyt_transfer_info, item.transferState == TransferModel.TRANSFER_STATE_PROGRESS ?
                View.VISIBLE : View.INVISIBLE);
        holder.setViewVisibility(R.id.iv_more, item.transferState == TransferModel.TRANSFER_STATE_DONE ?
                View.VISIBLE : View.GONE);
        holder.setText(R.id.tv_title, item.songName);
        holder.setText(R.id.tv_singer, item.artistName);
        holder.setText(R.id.tv_speed, Transfer.Speed.formatSpeed(item.transferSpeed));
        holder.setText(R.id.tv_progress, Transfer.Speed.formatInfo(item.transferCurrentLength, item.transferTotalLength));
        final CircleProgressBar circleBar = holder.getView(R.id.cpbar_bar);
        circleBar.setVisibility(item.transferState == TransferModel.TRANSFER_STATE_DONE ?
                View.GONE : View.VISIBLE);
        circleBar.setState(item.transferState);
        circleBar.setOnClickListener(new CircleProgressBar.OnClickListener() {
            @Override
            public void onRestart() {
                getOperater().remove(item);
            }

            @Override
            public void onResume() {
                getOperater().start(item);
                circleBar.setState(item.transferState);
            }

            @Override
            public void onPause() {
                getOperater().pause(item);
                circleBar.setState(item.transferState);
            }
        });
        item.setProgressCallback(item.transferState == TransferModel.TRANSFER_STATE_DONE ? null
                : new ProgressCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(long currentLength, long totalLength) {
                holder.setText(R.id.tv_speed, Transfer.Speed.formatSpeed(item.transferSpeed));
                holder.setText(R.id.tv_progress, Transfer.Speed.formatInfo(item.transferCurrentLength, item.transferTotalLength));
                circleBar.setState(CircleProgressBar.STATE_PROGRESS).progress(1f * currentLength / totalLength);
            }

            @Override
            public void onError(Throwable e) {
                circleBar.setState(CircleProgressBar.STATE_ERROR);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onSuccess() {

            }
        });
    }

    private Operater getOperater() {
        if (mType == TransferFragment.TYPE_MV) {
            return TransferManager.getIns().optMV();
        } else {
            return TransferManager.getIns().optSong();
        }
    }
}
