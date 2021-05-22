package com.wing.android.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.wing.android.databinding.ActivityBookManagerBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private ActivityBookManagerBinding inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = ActivityBookManagerBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        Intent intent = new Intent (this, BookManagerService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    IBookManager remoteManager;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //转换
            remoteManager = IBookManager.Stub.asInterface(service);

            try {
                List<Book> bookList = remoteManager.getBookList(); //java.util.ArrayList
                Log.i(TAG, "query book list, list type :" + bookList.getClass().getCanonicalName());
                Log.i(TAG, "query book list, list :" + bookList.toString());

                Book newBook = new Book(3, "h55555555");
                remoteManager.addBook(newBook);
                Log.i(TAG,"add book :" +newBook);
                bookList = remoteManager.getBookList();
                Log.i(TAG, "query book new list, list :" + bookList.toString());

                remoteManager.registerListener(newBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteManager = null;
            Log.i(TAG,"onServiceDisconnected binder died");
        }
    };

    private IOnNewBookArrivedListener newBookArrivedListener = new IOnNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            handler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.i(TAG, "receive new Book :" + msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (remoteManager != null && remoteManager.asBinder().isBinderAlive()) {
            try {
                Log.i(TAG,"onDestroy unRegisterListener :" + newBookArrivedListener);
                //unregister会提示 unRegisterListener not found（因为binder 多进程传递后，反序列化 会生成跟registerListener不同的对象）
                remoteManager.unRegisterListener(newBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            unbindService(connection);
        }
        super.onDestroy();
    }
}