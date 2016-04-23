package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.entity.AvatarEntity;
import com.efan.notlonely_android.entity.UserEntity;

/**
 * Created by thinkpad on 2016/4/23.
 */
@ContentView(id = R.layout.activity_alteravatar)
public class AlteravatarActivity extends BaseActivity {

    @ViewInject(id = R.id.avatar)
    private ImageView avatar;

    private UserEntity user;
    private Bitmap bitmap;
    private String picturePath;

    @Override
    public void initView() {
        user = MainApplication.getInstance().getUser();
        picturePath = user.getAvatar().getUrl();
        avatar.setImageURI(Uri.parse(picturePath));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.alter_avatar,R.id.alteravatar_yes})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alter_avatar:
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 12);
                break;
            case R.id.alteravatar_yes:
                AvatarEntity avatarEntity = new AvatarEntity();
                avatarEntity.setUrl(picturePath);
                user.setAvatar(avatarEntity);
                Intent intent = new Intent(this,AlterdataActivity.class);
                setResult(1, intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            avatar.setImageURI(Uri.parse(picturePath));
            Log.d("haha",picturePath);
        }
    }
}
