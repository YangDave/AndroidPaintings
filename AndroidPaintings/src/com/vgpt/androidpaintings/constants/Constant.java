package com.vgpt.androidpaintings.constants;

import java.util.HashMap;
import java.util.Map;

import android.os.Environment;

/**
 * @author Charles
 *
 */
public class Constant {
	
//	118.99.21.173
	public static String BASE_URL="http://182.92.226.185:9000/cv/1/pv/1/";
	//public static String BASE_URL="http://yuandunlong.imzone.in:9000/cv/1/pv/1/";
	
	public interface Api{
		
		String CHECk_USER=BASE_URL+"user/check_user/";
		String  SIGN_UP=BASE_URL+"user/sign_up/";
		String SIGN_IN=BASE_URL+"user/sign_in/";
		String USER_DATA=BASE_URL+"user/ordinary_user_data/";
		String INFO=BASE_URL+"user/info/";
		String GET_OTHERS_INFO=BASE_URL+"user/get_others_info/";
		String SET_FOCUS=BASE_URL+"user/set_focus/";
		String GET_FOCUS=BASE_URL+"user/info_focus/";
		String DEL_FOCUS = BASE_URL+"user/del_focus/";
		String GET_PHOTO=BASE_URL+"user/get_photo?user_id=";
		String CHANGE_PHOTO = BASE_URL+"user/change_headphoto/";
		 
	}
	public interface Painting{
		String PAINTING_LOAD=BASE_URL+"picture/querylist/";
		String PAINTING_SHOW=BASE_URL+"picture/picture_show/";
		String SET_COL=BASE_URL+"picture/set_col/";
		String GET_COL=BASE_URL+"picture/get_col/";
		String DEL_COL = BASE_URL+"picture/del_col/";
		String UPLOAD=BASE_URL+"picture/up_picture/";
		String GET_PICTURE=BASE_URL+"picture/get_picture?pic_id=";
		String GET_MYPIC=BASE_URL+"picture/find_myPic/";
		String SET_AUCTION=BASE_URL+"picture/set_auction/";
		String GET_COMMENT=BASE_URL+"picture/get_comment/";
		String SET_COMMENT=BASE_URL+"picture/set_comment/";
		String ALTER_COMMENT=BASE_URL+"picture/alter_comment/";
	}
	
	public interface Association{
		String GET_ASSOCIATIONLIST=BASE_URL+"association/get_associationlist/";
		String GET_MEMBER=BASE_URL+"association/get_member/";
		String GET_ASSO_INFO=BASE_URL+"association/get_asso_info/";
		String GET_ASSO_NOTICE=BASE_URL+"association/get_asso_notice/";
		String GET_ALL_ASSOLIST =BASE_URL+"association/get_all_assolist/";
		String GET_APPLIED_ASSOLIST=BASE_URL+"association/get_applied_assolist/";
		String REGISTER_ASSO=BASE_URL+"association/register_asso/"; 
		String GET_MARK=BASE_URL+"association/get_symbol?asso_id=";
		String SET_ASSO_NOTICE = BASE_URL+"association/set_asso_notice/";
		String GET_ASSO_MANEUVER_LIST = BASE_URL+"association/query_asso_exercise/";
		String GET_EX_PIC = BASE_URL + "association/get_ex_pic?exer_id=";
		String GET_EXERCISE_INFO = BASE_URL + "association/exercise_info/";
		String CREATE_EXER = BASE_URL+"association/create_exer/";
		String EXER_APPLY = BASE_URL+"association/user_ex_apply/";
		String EXER_APPLIED_LIST = BASE_URL + "association/user_applied/";
	}
	
	public static String MainDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/paintings/";
	
	public interface FileDir{
		String PIC=MainDir+"picture/";
		String HEADPHOTO=MainDir+"headphoto/";
		String ASSOMARK=MainDir+"asso_mark/";
	}
	
	public interface Search{
		String PIC=BASE_URL+"search/picture/";
		String USER=BASE_URL+"search/user/";
		String FIND_MYPIC=BASE_URL+"search/find_myPic/";
	}
	
	public interface Auction{
		String GET_AUCTION_LIST=BASE_URL+"auction/get_auction_list/";
		String SET_PRICE = BASE_URL + "auction/set_price/";
		String GET_PRICE_RECORD = BASE_URL+"auction/get_price_record/";
		
	}
	
	public static Map<String,String> categoryMap = new HashMap<String, String>();
	
	public static String[] categorys = {"gongbi","xieyi","xihua","shufa","others"};
	
	static {
		categoryMap.put("gongbi","工笔");
		categoryMap.put("xieyi","写意");
		categoryMap.put("xihua","西画");
		categoryMap.put("shufa","书法");
		categoryMap.put("others", "其他");
	}
	

}
