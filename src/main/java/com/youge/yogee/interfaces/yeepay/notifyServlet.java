package com.youge.yogee.interfaces.yeepay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yeepay.g3.facade.yop.ca.dto.DigitalEnvelopeDTO;
import com.yeepay.g3.facade.yop.ca.enums.CertTypeEnum;
import com.yeepay.g3.frame.yop.ca.DigitalEnvelopeUtils;
import com.yeepay.g3.sdk.yop.utils.InternalConfig;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class notifyServlet  {
	//测试异步回调
	public static void main(String[] args) {
		
		//String response="NlylsGELAg2o8X2NqD9qf7kZdiiewcyUZ1PYQnxOFMVMtdmOyI0p2eY6agNSxhXu0sqxaCkoVS2vkBsF_mDQOLryIoT4D9tV3SwElT1uwB7z8vb4HpDewjVY10R1fG_LWjTZ7zU1pdX_q0uAlJOQfeARQmnt_72yGBHVAeBpKIi0Q7F6fgzZCM8HmLi2JJQkqxoEr5ZkP5EDR9DxJH1CKca9p0ezcM34mRVc7S_8XCNRrEGkCq1dyUwdrgrCNhyrVSTFKOAkjvO8xwWDhgwgnHzq9oP0xI8jVx-tcDtYRmxtxhOIwWhqn4LGWXYsAT4Fm61PPTgXVIivye0lbhliZA$8LVaWiT2RB660xY2EIxDttoH0IVYHuHSAnZ_VdVVk3iFZTCmSfjCOWn0NlON6pBNUgXizlpRtjU1sTWSyP7HRXK5uSe57u2u7diaGr-C-VBlitGM7ooxu_8WqrmRVWI9s1h9Dz-IEV-l5s9H1A-Mre6iolgORDlusqlTAr_Bn92fME5oqyRkyQs14b4poyzeXGWTYRkJLhmS-rvKQanqPHkzWqcwMU_gEMSCljjUjMTwB-S5wh5NboGVOoidnB3pSQpU2NyfPKG7Lv5jd6_1hcOx581Cv0XJg5lCgKNfLyho7HNKQCEhW38s9IqEjnjP4d2tpWMueueLU3TXONKh4YZsx2_MrA0g0uzrZjwLjSmgTAFVAULNVTMnCQnfLQtoLx-OYgaLTumEr2A9qtYgC5mm0-JobRn0tNeU6aTOvvqtHiUqHxVx6DIDPxFBVNspiwi4-4naiQiNj2H5w__AC5LYWqtnLr_uNFYqHhDvl9srUnCBl23OVFWfl0uPX2QOwi4ckHUy7hPTsXXNUngqO5FqTjpIzcslKfkBuZwH8Ulgcg_Vw1rADtvQKrFIHLw2GVxUYfCLpt82U5ymT6zgCbOVIf66nrUZxkwotUnh1V0hPU2FoTWe-HS6hstF2gpY-BY-TbZuervpFzmp2IKnISrbgUSbYDkaWFsfO2yc4wt7v7DnQppeHr_3lYZEv4-q6bbijX0WzvLDRlNqA1dqnRbOLTdo9gg4fhhWXiElpMua08eIcbvh5-yCB3wlZJm68SuwlkqY7KyV5TElr60JNg$AES$SHA256";
		//String response="KhRBupLqDCpKpwc2t_y8WvQQ6Ut3wVjnCFrwKMcT8dwblS-d7cyH7Qaf2tiirl1SwBdqT6AOBCoA_0mKbOzCvC5JNMYd2bxHFSB5KcofTw2pKi3FCg-zrsvru0ZfkvEhS5DaCUAnn76qnC0VuqbFERl_ub-odEa9QhGacqBhxMK9upt6-OrKwwkwwfcMqHn32bR_X-Ymyaw8qVWbOMNc_QGRK9L6vYQBZh991RjliYfTRQjVK4gfCsy9QKRghlei11ZWxOVNh36Vo_l1Rk4WRa6GzYxQEG8lTRmkLXmzzvwOtCyWmWeM0eSIH_F-3PMB3SJ453DFTyED8td0UR52Tw$TEc8ZS__d-EnuTPEcNgrXwvqid7qY0wYutXIM0GGXjkzNSHXt8V1WWnj_7QRwIDQPWLpkn5OWidP912S0JNZZCnlFqzlg3_nb7ySfG93ylB_3C-WQDvDhfoiKi4PWUbUBVv7Bc5PzHlrQ9jXm6nu6gCxzWQJG26XVqEnumyAqWBiBCl7vg6h8LboyVfFMIgs2knxV5xhMrZ8tKinVw-xkC-aPFVCf3t0FBLnduzA9d663GtKykIta90qoiMz4SGpRXbF3iVCvFplAYjik5fefepy6N_5P63U2K970kziJw6hlPQfEGrhqI-DpycLPSi5wMotNCOo-gfL-WA8kMhSMG80eOwCfEgIo2L4YHUow4o6Pys-hznNorXvYkx2vedxb_hvm-aZ9pTc_lTQ7sNeDe-b2MiRoxMZstSyxxqpzRBI-mfRgqB8SktB1HRcocTbPKQJNxZMg1nrpYuNdrNXWCuW4pDeqM_qN03QbBSC05vrt-bXexENTu3-AxL2mPmLVWz_4iq-rwTlc5Kz7nrvuc8m5zOTkTyJHMa8iFMa2Wl48sQgsR3YVsX7jSq9nytktAVhAiszdCOXlW5RwX4dWN7bBN0NWJDwFmeg-STdXjgbxxuYKKjU7xAoLI9NvPUWZRBpf7VXastZiMl1FtGsRZ09fWly0uLfiGO0bgQVFchlHWcp-qIz416-cEf5TrcTNQwAzR9G2L7HHPDJd_qBzO2_GfDMhyc3kfx-QL_-2DaGfesYxgDY4pH5jNElWTQsOiLauufAofVPhEGOvo5KQ1svsnbloeYoHL9_UBtJJzU0KqHmwzHehRELB1tEs7Z9$AES$SHA256";
		//String response="RN3lkuEXUYOQzbzOD5LCls9saXA6VztSAPDZtzv06_PCdKWnCgqsCLTaknCHysvzSByIZn-KAZ_guNdpQY_gdhZZYoWeTOqOWBE4761BQblUUznS2LmjLAYOFOglat2crnEMxUiT6VYxUfBlM4STW4_lewx6GoeIa2ZVX9G4e4K8WcfmNIDelNYtE-xocJvlF09cRMuO5kS5IKT8SvF-n9DeIUeVWdgRzpd2WQj8zf5zZ3cjegGpJzPpllI8AeIPTA2SyJEQ9Qcghkmv-1KuHRRcSLYUsE24qlFhBcMWb3ED0weVXnjZbRxtbhQ7Ak2oU0NuVUUIiteX_J1OC3TQkg$Ix8jz6pJLZ-hbtP92bGIjqpdZSjeZYTfu0_k67KtGXB1v7cwcKOfVxJJB1RFgfJU1UspwJWofUZ2QsuS_VSG90sbPyFsJP9RZTn6O2AWzHOUFfim-NP27-zhaR2ShSfWMaa6Mel8y5Y7znP-oNO_X29WY3b8LOIdxwqOLkB2Zvy-wDl0FjcZWNourcZZoTABFbUvuZWzo4s1LTq_1uI-tNLLJborX9btM2rBjdHDrpJEhf4obVmICFGhCv312mYDIONApCGOGSNP9YnQMcgaQalLogaH4MU-bD17bmTHJi8BPNLdqYZPD2ceelfkL95fN1Qv72D4F5Z6LI27cAFnYtUOeNLa7FIKkmfW2pRpbL_ZvvrcMbx0ZCUKPyUfB2ckRCi6cFM2qQzdXQR9hEcEOQkpRqKGeLpHDX7B9jeimZwZZSN5WHtvRZTmxlyatzocFQL_K50CDTN4cpC8Hg4ff4gUEcRW9j93YlpbgXxr1y3Bdn6vts9FpDdQWM68ot7r6-zvV_oVa2OOL8xkXcrXwqFv6y09xl_hzOqNlHB1X5EvYLX8LNCXWH3WGWJBJjf19nWscwWySR7zar5Vg3FiJCbEPMe-r57vidTqMhkRehlBb4xChGgL70RaqKXRyvOVd5sUCMCxiTdVLxnhNixlrByxJkxez88veEA-J635uMgGDI8xxdpUHWyy6lX1h2IPSqSXjLnJ3IDKG38cMjMhjaniU-oE1y7eiVIgKrfDir6L-K98TI-KfSj1UUSxxKgLQNuZe9UjPopcpK9poaCQHH59GlFKr2mwV_TXN30d6Vxr4zCAS3AinMNbYBTLag8UX2eDb4lhTpiAkAbcctWE5VPfuyFSfXhe_hH8j7g9zJQkz0nT6ElsQZeqNKnjap-LreHf_9rqGBUdvGyAtL5-bJv9o0dRNtevtCdKfjiHwACac4cy8nj9bB-xnlvEztLLtn1L6ELdOey0c1OpZ4R-xrmFDKnqoquMmUlrB700IDXzo8uo7JdRkniwDTjVeIN3nCeEdeZytc86sWxw4P4PTQ$AES$SHA256";
		//String response="qNjNqLTYaSK98W9F04PghhPishrb_Z6u4g6oADsKxc_pwmajFQJZQnhVH5PeKrjf7SyvYr0VmZ2qZ66ev2PZohf0rSbkrTdV8yaN4MSvt4jhv2qBAaB39N9tDzr2avJ4uLZQ_QNvGG2cp4HJAp9h6Xa5LNkSqJrnxqzHQWzWDDIylNKirNeXp99qfip5yQYRaHE9B8CiJ8FaoY44d8J9udzdJPyABjpYbbEtGV6ad7xcmdE6EsXq6pC5JEbl8DDa1RJuwVA_ZFWABW7wJRgqwmJg_eQgiJ83C8HMg8nL0TVk7wp8hPUXLseOWbq021a7TDddn9mC8_i7l85BIvGVxA$6NFh3P1wfzZhMMwce5FxengavYXb_woW7KGriA2slwq5DNKu7ydSjQ1YuBiicFyxVHdtclXNptwJPCT8lGWKSxVRa9GWyljqGeG7nHNLaqTv65h5WGzidSlB1Z6HUN655Mf8ZtoVqs7p6kMotEbjDcBNT7KQBYJlrPJS70v2A1fco-9jzl4Ptaf0JFGCxwQv9erUM72KAbsZBWAKxGE9mjhlOB6H5z3Sb2qbVN9Sf-OYI9TZd_wQrnYH16OrYqFIIbzEHFmZLfYV5bwXu682W8Ewk4RJ0MSAL8-I-cdMMP7gUFweBpK2EOZTRyvkUBb5-i-qt9stRxlThfzTIgLeHOyI17WE_bAq7_84wl5iAbBlUxUdT87ArKFwyBVXzygwQUDvNlafzo6uYYQQd-keExyiMOj3YDTfTvvOPjjWxLbv65h5WGzidSlB1Z6HUN65tl6_6BIOnFDRpuCdhV4aDQtDszcvOWKpTar8RTCIduq0h2Ukk2B6UpSCb_2lPPAcTe8JM0LDr5yz0aM8zPBJdbHo_AoY1X4_aJ2CojGOMXUvMIbj6dViu9dQoBQHzv6krCL40XxBs3NB8qsuAfDj3lrpMDCnr6pRZpE20mfNw67c_Vh2K9miFTtwC0Cfp7SOlc2LPOiA-mGx-wdmwUM4cgl7GIiyX1nafzqF4gSvNn1phKf0IcnXtXfRTEFeHIAgllNxaPb-YfsQ_slWLmMbnJxDQFnRkFNiITC4P1ZyPZKdjEGyfHOHiRpgV8nywudTfCB_hDh0PVRyijcXXfnWncEvBz8k80XM4JBWIvO-DqyUVCzDoyv7-yCdb3a2B-ehTHNel7QGNu-U6iIrTiUlk236CYqxCKCdH79g0cPiHez1kL3rgeWds0nlK_XYyD29Sa0l3WEBcrdK38Szh_pEV1K0AYgh_zOTv3J3VV4ssNG736AebkCSK3-HJAr88O_i952OFXsMpUsLuNoI8QQMjxQeiWNkEAg2IEBIx-Bs1TJbApZwD8X_LZh1SYXxOLjqJppz3CZ7ZPSkOAQuyciB4CBxn-q5OYE4fcD8fbuYzEs$AES$SHA256";
		String response="iZLsNqljI0S4Edin9NctaLxZZpF7coaX5GOB-jpYynO8Wyv2ELzZHMOStZm0vIZ-DQnX8m2E6LNv3AN2Z_k2-MGgFKAvFq2_BIM-JF3vSiaUkvW0YVyvJUSNBkv9v_JsFYDVsP5h4zbtZQbbB6Uavj53_LWWYYGriWl2qkyHQbxEqszDPgayGo45oeCoxVLZW8QWyClzKRIAuhAFp2IP7NtWhl7lFln_CO_g6KK6_QbUqYACUtxhfSznbXYfLPGtgc5uQIfUtSpJqWOx_zUd3-qEuFl27Z4vjSmoN93YlNDeDx6gMKJlKaPKuJp7aGX9ptj3w8PPN11TWEIpbd0t5A$sKGZIndTkBKi9kp8MAo-BVxqEk0UN_dlmmr5I3diLRmfqBs4E3LurNHBzhW1iZ9LO5sm5oP87r6-s7sYzZdmPtDayoLF7KkZaDlYyxuTRnFYUXo28dDR293fFuaTPSFfqH3omysxy2ncZyQPo2yky50RcMDxhLr2nwX714gnmk-cJBQ8MA6P87PfNgLoF5rPopFaj-PTXJlM6_coX2yo_6JDhG8TrB0Ig3ntLzA9kKOR_1vbCPizv0FtVaNKyfrxlUwabD-eIlxBXhmnt10NTuTcM18OFvUFTHxxBrR0dMEUJww0MvudKJEjVZwwh2Hmv4NOVAPk4c5zeVhiHTQHWG4JQmCxvpG-mltxbv-66dVbNi45julQN7AcrbCJwKChj6amyXVQUNkGkmQr53iCDFBG4GtxneKOQ9mIap27YtPihemFcuBF6vfii4m9bBpXvorIqGie4kDT_8U_IHAwByUHyTBZBYx6gmbDHPhqD4_pkSRKzs6lAN1z1A1fY-WCbEwCNMEPxr_r7-5yumrlXSrG4uiR_1wBt8vBTlv2ludl1rRKWVFxN5jaQQp0MahJIq1AssuORDF3o1fx0gwwf-gjkxkUfz39VNWW2vXPG4dEBFJgj8zgyUHrOxNsfTMT_6Mak77Qgej2d8fnh5DqNNSMrt8L4LRLC4gV98GiRjf8BfJXDgHhY3Wsskxr3EBLE1sKoV7bST5H9Fl0t3l0OKkHbHQyuVh7iYawyZwvpEXnHesbZr_ZaBieQfjUKHcvA0L0B_exFcWPDm8oQt01WZ9zP8a86Lc82l-Cll1gk0gwjtDw3cQZ5T20BdDm3TLfCRqWA9oEBprBpaHij6HJo0XB5Zw_V1YmDP1PSm9tQamXiwrNoP7FoI7JzOzVkq8YRXTaFlFDJBk8n8pRrlNVggui5mScWVPsmg-l2MZgR93IaVrETKzZTWfFlIy2Uebhs9WOfDgU7WgL9jUaZ28P2VzXs6r9EQgkK_D8iLKFVEE8vnMfLkm-41AWLFHMzl24XUkKKAzcwZaasJYeG_Ob-aGcnAOQSulMLPT0gymFvOOG6keM7zxxzie86usakW4Gpqziyv64VZ7HugfEvPE5C09KW7Ln4Tgbtagh1_x67iEEJKCCPuZhuUvkdMQ9OpYAhmCBkGaWCUFsNIqKO9_59NpYKny8xHklk1XXCWNsQCeE6VXXyWsrZywR4TgalLR6$AES$SHA256";
		try {
			//开始解密
		Map<String,String> jsonMap  = new HashMap<>();
			DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
			dto.setCipherText(response);
			//InternalConfig internalConfig = InternalConfig.Factory.getInternalConfig();
			PrivateKey privateKey = InternalConfig.getISVPrivateKey(CertTypeEnum.RSA2048);
			System.out.println("privateKey: "+privateKey);
			PublicKey publicKey = InternalConfig.getYopPublicKey(CertTypeEnum.RSA2048);
			System.out.println("publicKey: "+publicKey);
			
			dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
			System.out.println("解密结果:"+dto.getPlainText());
			jsonMap = parseResponse(dto.getPlainText());
			System.out.println(jsonMap);
		} catch (Exception e) {
			throw new RuntimeException("回调解密失败！");
	}

	}

	public static Map<String, String> parseResponse(String response){
		
		Map<String,String> jsonMap  = new HashMap<>();
		jsonMap	= JSON.parseObject(response, 
				new TypeReference<TreeMap<String,String>>() {});
		
		return jsonMap;
	}
}
