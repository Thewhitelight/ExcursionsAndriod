package com.excursions.application;

import android.os.Environment;

public class BmobConstants {
	public static String INDEXURL = "http://182.92.5.6:80/Attraction/AttractionList?flag=0";
	public static String MOREURL = "http://182.92.5.6:80/Attraction/AttractionList?flag=";
	public static String ATTDETAILURL = "http://182.92.5.6:80/attraction/attractiondetail?id=";
	public static String ATTPOSITION = "ATTPOSITION";
	public static String TOURURL = "http://182.92.5.6:80/Community/CommentCommunityList?flag=0";
	public static String TOURMORE = "http://182.92.5.6:80/Community/CommentCommunityList?flag=";
	public static String DEALURL = "http://182.92.5.6:80/Community/DealCommunityList?flag=0";
	/**
	 * 存放发送图片的目录
	 */
	public static String BMOB_PICTURE_PATH = Environment
			.getExternalStorageDirectory() + "/Excursions/image/";

	/**
	 * 我的头像保存目录
	 */
	public static String MyAvatarDir = Environment
			.getExternalStorageDirectory() + "/Excursions/avatar/";
	/**
	 * 拍照回调
	 */
	public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;// 拍照修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;// 本地相册修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;// 系统裁剪头像

	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;// 拍照
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;// 本地图片
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;// 位置
	public static final String EXTRA_STRING = "extra_string";
	/**
	 * 注册成功之后登陆页面退出
	 */
	public static final String ACTION_REGISTER_SUCCESS_FINISH = "register.success.finish";

}
