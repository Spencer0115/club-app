package club.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	public static String time13To16_dMy(String time13) {
		try {
		   SimpleDateFormat original = new SimpleDateFormat("yyyyMMdd HHmm");
		   Date dateO = original.parse(time13);
		   SimpleDateFormat newSf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		   time13 = newSf.format(dateO);
		}catch(Exception e) {		
		}
		   return time13;
	}
	
	public static String time15To19_dMy(String time15) {
		 String time19 = time15;
		try {
		   SimpleDateFormat original = new SimpleDateFormat("yyyyMMdd HHmmss");
		   Date dateO = original.parse(time15);
		   SimpleDateFormat newSf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		   time19 = newSf.format(dateO);
		}catch(Exception e) {		
		}
		   return time19;
	}
	
	public static String time15To17_tdMy(String time15) {
		 String time19 = time15;
		try {
		   SimpleDateFormat original = new SimpleDateFormat("yyyyMMdd HHmmss");
		   Date dateO = original.parse(time15);
		   SimpleDateFormat newSf = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
		   time19 = newSf.format(dateO);
		}catch(Exception e) {		
		}
		   return time19;
	}
	public static String time15To19_tdMy(String time15) {
		 String time19 = time15;
		try {
		   SimpleDateFormat original = new SimpleDateFormat("yyyyMMdd HHmmss");
		   Date dateO = original.parse(time15);
		   SimpleDateFormat newSf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
		   time19 = newSf.format(dateO);
		}catch(Exception e) {		
		}
		   return time19;
	}
	
	public static String time13To16_yMd(String time13) {
		try {
		   SimpleDateFormat original = new SimpleDateFormat("yyyyMMdd HHmm");
		   Date dateO = original.parse(time13);
		   SimpleDateFormat newSf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		   time13 = newSf.format(dateO);
		}catch(Exception e) {		
		}
		   return time13;
	}
	
	public static String time15To19_yMd(String time15) {
		String time19 = time15;
		try {
		   SimpleDateFormat original = new SimpleDateFormat("yyyyMMdd HHmmss");
		   Date dateO = original.parse(time15);
		   SimpleDateFormat newSf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   time19 = newSf.format(dateO);
		}catch(Exception e) {		
		}
		   return time19;
	}
	
}
