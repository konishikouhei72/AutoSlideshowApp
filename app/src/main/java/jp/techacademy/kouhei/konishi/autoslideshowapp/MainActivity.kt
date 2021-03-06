package jp.techacademy.kouhei.konishi.autoslideshowapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.provider.MediaStore
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Handler
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener  {

    private val PERMISSIONS_REQUEST_CODE = 100
    private val mHandler = Handler()

    lateinit var cursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button4.isEnabled = false
        // Android 6.0以降の場合
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // パーミッションの許可状態を確認する
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
                getContentsInfo()
            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_CODE)
            }
            // Android 5系以下の場合
        } else {
            getContentsInfo()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE ->
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContentsInfo()
                }
        }
    }
    private fun getContentsInfo() {
        // 画像の情報を取得する
        var resolver = contentResolver
        cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // データの種類
            null, // 項目(null = 全項目)
            null, // フィルタ条件(null = フィルタなし)
            null, // フィルタ用パラメータ
            null // ソート (null ソートなし)
        )

        if (cursor!!.moveToFirst()) {
            // indexからIDを取得し、そのIDから画像のURIを取得する
            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val id = cursor.getLong(fieldIndex)
            val imageUri =
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            Log.d("ANDROID", "URI : " + imageUri.toString())
            imageView.setImageURI(imageUri)
        }
    }
    private var mTimer: Timer? = null
    private var mTimerSec = 0.0
    var num = 0

    override fun onClick(v: View) {
        if (v.id == R.id.button1) {
            Log.d("ANDROID", "ボタン１押下" )
            if (cursor!!.moveToNext()) {
                // indexからIDを取得し、そのIDから画像のURIを取得する
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                Log.d("ANDROID", "URI : " + imageUri.toString())
                imageView.setImageURI(imageUri)
                Log.d("ANDROID", "次の画像へ！！" )
            } else {
                if (cursor!!.moveToFirst()) {
                    // indexからIDを取得し、そのIDから画像のURIを取得する
                    val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                    val id = cursor.getLong(fieldIndex)
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    Log.d("ANDROID", "URI : " + imageUri.toString())
                    imageView.setImageURI(imageUri)
                    Log.d("ANDROID", "次が無いので最初の画像へ！！")
                }
            }
        } else if (v.id == R.id.button2) {
            Log.d("ANDROID", "ボタン２押下" )
            if (cursor!!.moveToPrevious()) {
                // indexからIDを取得し、そのIDから画像のURIを取得する
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                Log.d("ANDROID", "URI : " + imageUri.toString())
                imageView.setImageURI(imageUri)
                Log.d("ANDROID", "前の画像へ！！" )
            }else {
                if (cursor!!.moveToLast()) {
                    // indexからIDを取得し、そのIDから画像のURIを取得する
                    val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                    val id = cursor.getLong(fieldIndex)
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    Log.d("ANDROID", "URI : " + imageUri.toString())
                    imageView.setImageURI(imageUri)
                    Log.d("ANDROID", "前が無いので最後の画像へ！！" )
                }
            }
        } else if (v.id == R.id.button3) {
            button1.isEnabled = false
            button2.isEnabled = false
            button3.isEnabled = false
            button4.isEnabled = true
            Log.d("ANDROID", "ボタン３押下" )
            // タイマーの作成
            mTimer = Timer()
            // タイマーの始動
            mTimer!!.schedule(object : TimerTask() {
                override fun run() {
                    mTimerSec += 0.1
                    num += 1
                    mHandler.post {
                        if (cursor!!.moveToNext()) {
                            // indexからIDを取得し、そのIDから画像のURIを取得する
                            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                            val id = cursor.getLong(fieldIndex)
                            val imageUri =
                                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                            Log.d("ANDROID", "URI : " + imageUri.toString())
                            imageView.setImageURI(imageUri)
                            Log.d("ANDROID", "次の画像へ！！" )
                        } else {
                            if (cursor!!.moveToFirst()) {
                                // indexからIDを取得し、そのIDから画像のURIを取得する
                                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                                val id = cursor.getLong(fieldIndex)
                                val imageUri =
                                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                                Log.d("ANDROID", "URI : " + imageUri.toString())
                                imageView.setImageURI(imageUri)
                                Log.d("ANDROID", "次が無いので最初の画像へ！！")
                            }
                        }
                    }
                }
            }, 100, 2000)
        } else if (v.id == R.id.button4) {
            Log.d("ANDROID", "ボタン４押下" )
            button1.isEnabled = true
            button2.isEnabled = true
            button3.isEnabled = true
            button4.isEnabled = false

            mTimer!!.cancel()
            mTimer = null
        }
    }
}