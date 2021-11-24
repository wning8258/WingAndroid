package com.wning.demo.gifteffect;


import com.guagua.qiqi.gifteffect.SceneInfo;

/**
 * 
 * 发送礼物时显示的Bean.
 * 如: 额定礼物效果用到.
 * @author Xue Wenchao
 *
 */
public class GiftAnim {
	public static final int ANIM_FRAME = 2;
	public static final int ANIM_SHAPE = 3;
	public static final int ANIM_SAM = 4;
	public static final int ANIM_GIF = 5;

	public String sender;
	public String receiver;
	public String unit;
	public int animType;
	public int num;
	public int effectLevel;//要播放礼物动画的级别
	public String resPath;//路径起始的scheme要符合以下规则
							//HTTP("http://"), HTTPS("https://"), FILE("file://"), 
							//CONTENT("content://"), ASSETS("assets://"), DRAWABLE("drawable://")
	public SceneInfo convert(){
		SceneInfo info=new SceneInfo();
		info.animType=animType;
		info.effectLevel=effectLevel;
		info.num=num;
		info.sender=sender;
		info.receiver=receiver;
		info.unit=unit;
		info.resPath=resPath;
		return info;
	}
}
