package com.wing.android.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.Nullable;

public class BookManagerService extends Service {

    private static final String TAG= "BookManagerService";

    //service要处理一对多，这是要处理并发
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private AtomicBoolean isServiceDestroyed = new AtomicBoolean(false);

    //private CopyOnWriteArrayList<IOnNewBookArrivedListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * RemoteCallbackList是系统提供的 用于删除跨进程listener的接口
     * ArrayMap<IBinder, Callback> mCallbacks = new ArrayMap<IBinder, Callback>();
     *
     *  public boolean register(E callback, Object cookie) {
     *         synchronized (mCallbacks) {
     *             ......
     *             IBinder binder = callback.asBinder();
     *             try {
     *                 Callback cb = new Callback(callback, cookie);
     *                 binder.linkToDeath(cb, 0);
     *                 mCallbacks.put(binder, cb);
     *                 return true;
     *             } catch (RemoteException e) {
     *                 return false;
     *             }
     *         }
     *     }
     */
    private RemoteCallbackList<IOnNewBookArrivedListener> remoteCallbackList = new RemoteCallbackList<IOnNewBookArrivedListener>();



    private Binder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!listeners.contains(listener)) {
//                listeners.add(listener);
//            } else {
//                Log.i(TAG, "registerListener already exists");
//            }
//            Log.i(TAG, "registerListener current size :" + listeners.size());

            remoteCallbackList.register(listener);

            final int N = remoteCallbackList.beginBroadcast();
            remoteCallbackList.finishBroadcast();
            Log.d(TAG, "registerListener, current size:" + N);
        }

        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (listeners.contains(listener)) {
//                listeners.remove(listener);
//            } else {
//                Log.i(TAG, "unRegisterListener not found");
//            }
//            Log.i(TAG, "unRegisterListener current size :" + listeners.size());

            boolean success = remoteCallbackList.unregister(listener);

            if (success) {
                Log.d(TAG, "unregister success.");
            } else {
                Log.d(TAG, "not found, can not unregister.");
            }
            final int N = remoteCallbackList.beginBroadcast();
            remoteCallbackList.finishBroadcast();
            Log.d(TAG, "unregisterListener, current size:" + N);
        }
    };
    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
//        Log.i(TAG,"onNewBookArrived notify listeners , current listeners size :" + listeners.size());
//        for (IOnNewBookArrivedListener listener : listeners) {
//            Log.i(TAG,"onNewBookArrived notify listener :" + listener);
//            listener.onNewBookArrived(book);
//        }

        final int N = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener l = remoteCallbackList.getBroadcastItem(i);
            if (l != null) {
                try {
                    l.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"Ios"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isServiceDestroyed.get()) {
                    SystemClock.sleep(5000);
                    int bookId  = mBookList.size()  + 1;
                    Book newBook = new Book(bookId,"new Book#" + bookId);
                    try {
                        onNewBookArrived(newBook);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceDestroyed.set(true);
    }
}
