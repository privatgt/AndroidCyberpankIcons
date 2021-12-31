package com.cuberpunk.iconpack;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.util.Log;
import java.lang.Exception;

import android.os.Environment;
import java.io.File;
import java.util.Random;
import java.io.FileOutputStream;
import android.net.Uri;
import android.graphics.drawable.BitmapDrawable;
import android.view.View.MeasureSpec;
import android.graphics.Canvas;
import java.util.concurrent.TimeUnit;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.Drawable;
public class IconPack extends Activity implements OnItemClickListener {
    private static final String ACTION_ADW_PICK_ICON = "org.adw.launcher.icons.ACTION_PICK_ICON";
    private static final String ACTION_ADW_PICK_RESOURCE = "org.adw.launcher.icons.ACTION_PICK_ICON_RESOURCE";

    private boolean mPickerMode = false;
    private boolean mResourceMode = false;
    private void SaveImage(Bitmap finalBitmap, String filename){
           String fname = filename +".png";
           try {
                FileOutputStream out = new FileOutputStream(getApplicationContext().getFilesDir()+fname);
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
           } catch (Exception e) {
               e.printStackTrace();
               Log.e("cyberpunk",e.toString());
           }
    }
    public static Bitmap loadBitmapFromView(Context context,int id) {
        Drawable d=ContextCompat.getDrawable(context, id);
        Bitmap b = ((BitmapDrawable)d).getBitmap();       
        return b;
   }
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int in =0;
        String[] fils={"ic_calculator.xml" , "ic_community.xml" , "ic_cplogo.xml" , "ic_gamespace.xml" , "ic_note.xml" , "ic_screen_recorder.xml" , "ic_zenmode.xml" , "ic_camera.xml" , "ic_connect.xml" , "ic_filemanager.xml" , "ic_message.xml" , "ic_phone.xml" , "ic_setting.xml" , "ic_browser.xml" , "ic_cardpackage.xml" , "ic_contact.xml" , "ic_gallery.xml" , "ic_moving.xml" , "ic_recorder.xml" , "ic_store.xml"};
        int[] fi={R.drawable.ic_calculator,R.drawable.ic_community,R.drawable.ic_cplogo,R.drawable.ic_gamespace,R.drawable.ic_note,R.drawable.ic_screen_recorder,R.drawable.ic_zenmode,R.drawable.ic_camera,R.drawable.ic_connect,R.drawable.ic_filemanager,R.drawable.ic_message,R.drawable.ic_phone,R.drawable.ic_setting,R.drawable.ic_browser,R.drawable.ic_cardpackage,R.drawable.ic_contact,R.drawable.ic_gallery,R.drawable.ic_moving,R.drawable.ic_recorder,R.drawable.ic_store};
        for (int fil : fi) {

            Bitmap icon = loadBitmapFromView(getApplicationContext(),fil);
            SaveImage(icon,fils[in]);
            in+=1;
        }
        try{
            Intent startServiceIntent = new Intent(getApplicationContext(), BackgroundService.class);
            getApplicationContext().startService(startServiceIntent);
        } catch (Exception e) {
            Log.e("cyberpunk" ,e.getMessage());
        }
        int iconSize = getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);
        setContentView(R.layout.main);

        GridView g = (GridView) findViewById(R.id.icon_grid);
        g.setNumColumns(GridView.AUTO_FIT);
        g.setColumnWidth(iconSize);
        g.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
        g.setVerticalSpacing(iconSize / 3);
        g.setOnItemClickListener(this);
        IconsAdapter adapter = new IconsAdapter(this, iconSize);
        g.setAdapter(adapter);
        if (getIntent().getAction().equals(ACTION_ADW_PICK_ICON)) {
            mPickerMode = true;
        }
        if (getIntent().hasExtra(ACTION_ADW_PICK_RESOURCE)) {
            mResourceMode = true;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (mPickerMode) {
            Intent intent = new Intent();
            if (!mResourceMode) {
                Bitmap bitmap = null;
                try {
                    bitmap = (Bitmap) adapterView.getAdapter().getItem(position);
                } catch (Exception e) {
                    /* nothing */
                }

                if (bitmap != null) {
                    intent.putExtra("icon" , bitmap);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CANCELED, intent);
                }
            } else {
                ShortcutIconResource res = ((IconsAdapter)adapterView.getAdapter()).getResource(position);
                if (res != null) {
                    intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, res);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CANCELED, intent);
                }
            }
            finish();
        }
    }
    private class IconsAdapter extends BaseAdapter {
        private Context mContext;
        private int mIconSize;
        public IconsAdapter(Context mContext, int iconsize) {
            super();

            this.mContext = mContext;
            this.mIconSize = iconsize;

            loadIcons();
        }

        @Override
        public int getCount() {
            return mThumbs.size();
        }

        public ShortcutIconResource getResource(int position) {
            return ShortcutIconResource.fromContext(IconPack.this, mThumbs.get(position));
        }
        @Override
        public Object getItem(int position) {
            Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeResource(mContext.getResources(), mThumbs.get(position), opts);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(mIconSize, mIconSize));
            } else {
                imageView = (ImageView) convertView;
            }
            
            imageView.setImageResource(mThumbs.get(position));
            return imageView;
        }

        private ArrayList<Integer> mThumbs;

        private void loadIcons() {
            mThumbs = new ArrayList<Integer>();

            final Resources resources = getResources();
            final String packageName = getApplication().getPackageName();

            addIcons(resources, packageName, R.array.icon_pack);
        }

        private void addIcons(Resources resources, String packageName, int list) {
            final String[] extras = resources.getStringArray(list);
            for (String extra : extras) {
                int res = resources.getIdentifier(extra, "drawable" , packageName);
                if (res != 0) {
                    final int thumbRes = resources.getIdentifier(extra, "drawable" , packageName);
                    if (thumbRes != 0) {
                        mThumbs.add(thumbRes);
                    }
                }
            }
        }
    }
}
