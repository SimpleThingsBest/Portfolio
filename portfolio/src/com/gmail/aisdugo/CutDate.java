package com.gmail.aisdugo;

public class CutDate {
	public String cutAndStick(String pubDateData) {
		if(pubDateData.length() == 17) {
			return pubDateData;
		}else {
		//Fri, 02 Nov 2018 06:25:15 GMT
		//일
		String days = pubDateData.substring(5, 7);
		//월
		String months = pubDateData.substring(8, 11);
		switch(months) {
		case "Jan" : 
			months = "1"; break;
		case "Feb" : 
			months = "2"; break;
		case "Mar" : 
			months = "3"; break;
		case "Apr" : 
			months = "4"; break;
		case "May" : 
			months = "5"; break;
		case "Jun" : 
			months = "6"; break;
		case "Jul" : 
			months = "7"; break;
		case "Aug" : 
			months = "8"; break;
		case "Sep" : 
			months = "9"; break;
		case "Oct" : 
			months = "10"; break;
		case "Nov" : 
			months = "11"; break;
		case "Dec" : 
			months = "12"; break;
		}
		//년
		String years = pubDateData.substring(12, 16);
		//시간
		String clocks = pubDateData.substring(17, 25);
		
		pubDateData = String.format("%s-%s-%s %s", years,months,days,clocks);
		
		return pubDateData;
		}
	}
	
	public int transData(String data) {
			data = this.cutAndStick(data);
			String [] ar = new String [4];
			ar = data.split("-");
			String [] subAr = ar[2].split(" ");
			ar[2] = subAr[0];
			ar[3] = subAr[1];
			StringBuilder sb = new StringBuilder();
			for(String imsi : ar) {
			sb.append(imsi);
			}
			String sResult = sb.toString();
			int result = Integer.parseInt(sResult);
		return result;
	}
}
