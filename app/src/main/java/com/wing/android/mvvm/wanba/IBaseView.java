package com.wing.android.mvvm.wanba;


/**
 * Title: IBaseView<br>
 * Description: <br>
 *
 * @author fxd
 * @link fuxiaodan@moqipobing.com
 * @since JDK 1.8
 */
public interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();

    /**
     * 初始化皮肤资源
     */
    void initSkin();
}
