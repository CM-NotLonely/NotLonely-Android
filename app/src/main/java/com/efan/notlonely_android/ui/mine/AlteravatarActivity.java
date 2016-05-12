package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.EventBase;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.entity.AvatarEntity;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.notlonely_android.event.RefreshEvent;
import com.efan.notlonely_android.utils.PreferencesUtils;
import com.efan.notlonely_android.utils.ToastUtils;
import com.efan.request.RequestUtils;
import com.efan.request.callback.Callback;
import com.efan.request.okhttp.OkhttpClient;
import com.efan.request.request.FileParam;
import com.efan.request.response.Response;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by thinkpad on 2016/4/23.
 */
@ContentView(id = R.layout.activity_alteravatar)
public class AlteravatarActivity extends BaseActivity {

    @ViewInject(id = R.id.avatar)
    private ImageView avatar;

    private static final String TAG="AlteravatarActivity";
    private static final int SELECT_AVATAR=1;
    private UserEntity user;
    private Bitmap bitmap;
    private String picturePath;

    @Override
    public void initView() {
        Intent intent=getIntent();
        picturePath = intent.getStringExtra("picturePath");
        Log.e(TAG,picturePath);
        Uri uri=Uri.parse("file://"+picturePath);
        Glide.with(this).load(uri).into(avatar);
//        int width = 50, height = 50;
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                .setResizeOptions(new ResizeOptions(width, height))
//                .build();
//        PipelineDraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setOldController(mDraweeView.getController())
//                .setImageRequest(request)
//                .build();
//        avatar.setController(controller);
//        avatar.setImageURI(Uri.parse("file:///"+picturePath));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.tv_upload,R.id.iv_left,R.id.tv_repick})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_repick:
                Intent intent=  new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,SELECT_AVATAR );
                break;
            case R.id.tv_upload:
                ToastUtils.show(this,"上传头像");
                AvatarEntity avatarEntity = new AvatarEntity();
                avatarEntity.setUrl(picturePath);
                Log.e(TAG,picturePath);
                //上传头像，上传成功，将本地头像修改。
                File file = new File(picturePath);
                MediaType mediaType = MediaType.parse("image/*");
                FileParam fileParam = new FileParam("sss.png", file, mediaType);
                RequestUtils.patch()
                        .url(APIConfig.Update_head_image)
                        .addParams("avatar", fileParam)
                        .build()
                        .execute(new Callback() {
                            @Override
                            public void onError(Exception e) {
                                Log.d(TAG,"onError");
                            }

                            @Override
                            public void onResponse(Response response) {
                                String string = response.getBody();
                                //更新成功，得到新的url，发送EvenBus更新MinFragment和AlterDataActivity头像
                                Log.d(TAG,string);
                                PreferencesUtils.putString(AlteravatarActivity.this,SPConfig.USER_URL,string);
                                EventBus.getDefault().post(new RefreshEvent(RefreshEvent.RefreshType.ALTERAVATAR));
                            }
                        });
                finish();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SELECT_AVATAR:
                if (resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    //avatar.setImageURI(Uri.parse(picturePath));
                    Log.d(TAG,picturePath);
                }
                break;
        }
        Glide.with(this).load(Uri.parse("file:///"+picturePath)).into(avatar);
       // avatar.setImageURI(Uri.parse("file:///"+picturePath));
    }

}
