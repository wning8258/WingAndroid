package com.wning.demo.widget;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于viewpager的item fragment，可以实现viewpager真正在当前条目的时候，加载数据，而不是加载当前页和下一页的数据
 * 例：
 * public class ViewPagerLazyFragmentDemo extends ViewPagerLazyFragment{

    @Override
    protected View inflateView() {
        return null;
    }

    @Override
    protected void onVisible() {
        
    }

    @Override
    protected void onInvisible() {
        
    }
    

}
 * @author wning
 *
 */
public abstract class ViewPagerLazyFragment extends Fragment {
    
    
    private  boolean isUserVisibled=false;  
    private  boolean isViewInflated=false;  
    
    private View rootView;
    

   
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        rootView=inflateView();
        isViewInflated=true;
        estimateVisible();
        return rootView;
        
        
    }
    
    /** 
     * 在这里实现Fragment数据的缓加载. (在oncreateview，甚至oncreate之前被调用)
     * @param isVisibleToUser 
     */  
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {  
        super.setUserVisibleHint(isVisibleToUser);  
        Log.i("wning", getClass().getName()+" getUserVisibleHint: "+getUserVisibleHint());
        if(getUserVisibleHint()) {
            isUserVisibled=true;
            estimateVisible();  
        } else {
            isUserVisibled=false;
            onInvisible();  
        }  
    } 
    
    /**
     * 进行view的初始化
     * @return oncreateview的返回值
     */
    protected abstract View inflateView();
    
    /**
     * 页面真正的是用户可见的执行的操作
     * （viewpager嵌套fragment的时候，前两个fragment都同时执行了oncreate-->oncreateview-->onresume）
     * 但是第二个fragment并没有真正意义的可见，如果页面中有大量复杂操作，会很消耗内存，
     * 把数据的填充及复杂操作放入当前方法中，可以 确保用户真正看见当前页面时，才进行数据的加载。
     */
    protected abstract void onVisible();  

   /**
    * 页面真正的是用户不可见的执行的操作（Viewpager加载了两个页面，从第一个页面滑动到第二个页面时，第一个页面没有
    * 生命周期的变化，但是用户其实已经看不到了）
    * 可以取消一些复杂操作，释放内存
    */
    protected abstract void onInvisible();
    
    
    /**
     * 不能确定一定是visible,必须要oncreateView inflate，并且setUserVisibleHint true之后，才是真正的visible
     */
    private final void estimateVisible(){  
        
        if((isViewInflated&&isUserVisibled)) {
             onVisible();  
         }
    }
    
    

    protected View getRootView() {
        return rootView;
    }

}
