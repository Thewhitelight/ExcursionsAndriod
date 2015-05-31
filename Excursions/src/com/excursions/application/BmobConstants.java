package com.excursions.application;

import android.os.Environment;

public class BmobConstants {
	public static String INDEXURL = "http://10.64.130.129:10240/Attraction/AttractionList?flag=0";
	public static String MOREURL = "http://10.64.130.129:10240/Attraction/AttractionList?flag=";
	public static String ATTPOSITION = "ATTPOSITION";
	/**
	 * ��ŷ���ͼƬ��Ŀ¼
	 */
	public static String BMOB_PICTURE_PATH = Environment
			.getExternalStorageDirectory() + "/Excursions/image/";

	/**
	 * �ҵ�ͷ�񱣴�Ŀ¼
	 */
	public static String MyAvatarDir = Environment
			.getExternalStorageDirectory() + "/Excursions/avatar/";
	/**
	 * ���ջص�
	 */
	public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;// �����޸�ͷ��
	public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;// ��������޸�ͷ��
	public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;// ϵͳ�ü�ͷ��

	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;// ����
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;// ����ͼƬ
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;// λ��
	public static final String EXTRA_STRING = "extra_string";
	/**
	 * ע��ɹ�֮���½ҳ���˳�
	 */
	public static final String ACTION_REGISTER_SUCCESS_FINISH = "register.success.finish";

}
