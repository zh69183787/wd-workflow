/*
 * 创建日期 2005-9-27
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.wonders.send.util;

/**
 * @author zougao
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
import java.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.*;
import java.util.Date;
public class Donull {
	public Donull(){}
	
	public static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat FULL_YMD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat FULL_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final Date the1stDay = new Date(0L);
    private static final SimpleDateFormat CHINESE_DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
    private static final String replaceStr[] = {
        "<", "&lt", "/>", "/&gt", "\r\n", "<br>", " ", "&nbsp;"
    };
    
	public int Ofint(String ss)
	{
		
		int i=0;
		if(ss!=null)
		{
			if(ss.equals(""))
				i=1;
			if(ss.equals("null"))
				i=1;
		}
		if(ss==null)
			i=1;
		return i;
	}
	public String dealNull(String ss)
	{
		
		String i="";
		Donull donull=new Donull();
		if(donull.Ofint(ss)==0)i=ss;
		return i;
	}
	public String OfBlank(String ss)
	{
		
		String i="&nbsp;";
		Donull donull=new Donull();
		if(donull.Ofint(ss)==0)i=ss;
		return i;
	}
	public String dealNull(String head,String ss,String tail)
	{
		
		String i="";
		Donull donull=new Donull();
		if(donull.Ofint(ss)==0)i=head+ss+tail;
		return i;
	}
	public String dealNull(String head,String ss)
	{
		
		String i="";
		Donull donull=new Donull();
		if(donull.Ofint(ss)==0)i=head+ss;
		
		
		return i;
	}
	public String TranAge(String ss)
	{
		if(dealNull(ss).equals(""))
			return "";
		else{
			try{
				int a=Integer.parseInt(ss.substring(2));
	   			int b=a/5*5;
	   			String re="";
	   			if(b>=10)
	   				re=String.valueOf(b);
	   			else
	   				re="0"+String.valueOf(b);
	   			return re;	
			}catch(Exception e){
				
				System.out.println("TranAge has an error ::"+e);return "";
			}
		}
	}
	public String TranTime(String ss)
	{
		String re="";
		if(dealNull(ss).equals(""))
			return "";
		else{
			try{
				if(ss.length()>=9){
					re=ss.substring(2).replaceAll("-","/");
				}
	   			return re;	
			}catch(Exception e){
				
				System.out.println("TranAge has an error ::"+e);return "";
			}
		}
	}
	public String DateToString(String ss)
	{
		Donull donull=new Donull();
		String re="";
		if(!donull.dealNull(ss).equals("")){
			Date Begin=new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			try{
				Begin=format.parse(ss);
				re=format.format(Begin);
			}catch(Exception e){}
		}
		if(donull.dealNull(re).equals("1900-01-01"))
			re="";
		return re;
	}
	/*add by zougao on 20070315 */
	public String DateToString(String ss,Date date)
	{
		Donull donull=new Donull();
		String re="";
		if(!donull.dealNull(ss).equals("")){
			Date Begin=new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			try{
				Begin=format.parse(ss);
				re=format.format(Begin);
			}catch(Exception e){}
		}else{
			re= donull.DateToString(date);
		}
		if(donull.dealNull(re).equals("1900-01-01"))
			re="";
		return re;
	}
	/*end of add*/
	public String DateToStringLong(String ss)
	{
		Donull donull=new Donull();
		String re="";
		if(donull.Ofint(ss)==0){
		Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		try{
			Begin=format.parse(ss);
			re=format.format(Begin);
		}
		catch(Exception e){}
		}

		return re;
	}
	public String DateToStringLong(Date ss)
	{
		Donull donull=new Donull();
		String re="";
		//if(donull.Ofint(ss)==0){
		//Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		try{
			//Begin=format.parse(ss);
			re=format.format(ss);
		}
		catch(Exception e){}
		

		return re;
	}
	
	public String DateToString(Date ss)
	{
		Donull donull=new Donull();
		String re="";
		//if(donull.Ofint(ss)==0){
		//Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8")); 
		try{
			//Begin=format.parse(ss);
			re=format.format(ss);
		}
		catch(Exception e){}
		

		return re;
	}

	public String MonToString(String ss)
	{
		Donull donull=new Donull();
		String re="";
		if(donull.Ofint(ss)==0){
		Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
		try{
			Begin=format.parse(ss);
			re=format.format(Begin);
		}
		catch(Exception e){}
		}
		return re;
	}
	public String DateToString_8code(String ss)
	{
		Donull donull=new Donull();
		
		String re="";
		if(donull.Ofint(ss)==0){
		Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try{
			Begin=format.parse(ss);
			re=format.format(Begin);
			String a="-";
			String b="";
			re=re.replaceAll(a,b); 
		}
		catch(Exception e){}
		}

		return re;
	}
	public String StartToString(String ss)
	{
		Donull donull=new Donull();
		String re="";
		if(donull.Ofint(ss)==0){
			ss=donull.MonToString(ss)+"-1";
		Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try{
			Begin=format.parse(ss);
			re=format.format(Begin);
		}
		catch(Exception e){}
		}
		return re;
	}
	
	public String EndToString(String ss)
	{
		Donull donull=new Donull();
		String re="";
		if(donull.Ofint(ss)==0){
			ss=donull.MonToString(ss)+"-31 23:59:59";
		Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		try{
			Begin=format.parse(ss);
			re=format.format(Begin);
		}
		catch(Exception e){}
		}
		return re;
	}
	
	public String NumberToStringForBlank(float x){
		String blank="&nbsp;";
		if(x>0.001){
			DecimalFormat myformat = new DecimalFormat("###0.00");
			blank=myformat.format(x);
		}
			//myformat.setDecimalSeparatorAlwaysShown(true);
		return blank;
	}
	public String NumberToStringForBlank(double x){
		String blank="&nbsp;";
		
		if(x>0.001){
			DecimalFormat myformat = new DecimalFormat("###0.00");
			blank=myformat.format(x);
		}
			//myformat.setDecimalSeparatorAlwaysShown(true);
		return blank;
	}
	public String NumberToString(double x){
		String blank="0";
		
		if(x>0.001){
			DecimalFormat myformat = new DecimalFormat("###0.00");
			blank=myformat.format(x);
		}
			//myformat.setDecimalSeparatorAlwaysShown(true);
		return blank;
	}
	public String NumberToString(float x){
		String blank="0";
		
		if(x>0.001){
			DecimalFormat myformat = new DecimalFormat("###0.00");
			blank=myformat.format(x);
		}
			//myformat.setDecimalSeparatorAlwaysShown(true);
		return blank;
	}
	public String setMon(String ss,int i)
	{
		
		Donull donull=new Donull();
		String re="";
		if(donull.Ofint(ss)==0){
		Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try{
			Begin=format.parse(ss);//System.out.print("---Begin--"+Begin);
			Begin.setMonth(i-1);
			re=format.format(Begin);
		}
		catch(Exception e){}
		}
		//System.out.print("---"+i+"--"+re);
		return re;
	}
	public String setYear(String ss,int i)
	{
		Donull donull=new Donull();
		String re="";
		if(donull.Ofint(ss)==0){
		Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try{
			Begin=format.parse(ss);
			Begin.setYear(i-1);
			re=format.format(Begin);
		}
		catch(Exception e){}
		}

		return re;
	}
	public String ChangeDateForMon(String ss,int i)
	{

		Donull donull=new Donull();
		String re="";
		if(donull.Ofint(ss)==0){
		Date Begin=new Date();
		DateFormat formate = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar calend=Calendar.getInstance();
		try{
			Begin=formate.parse(ss);
			calend.setTime(Begin);
			calend.add(Calendar.MONTH,-i);
			re=formate.format(calend.getTime());
			//re=format.format(Begin);
		}
		catch(Exception e){}
		}

		return re;
	}
	public String ChangeDateForDay(String ss,int i)
	{

		Donull donull=new Donull();
		String re="";
		if(donull.Ofint(ss)==0){
		Date Begin=new Date();
		DateFormat formate = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar calend=Calendar.getInstance();
		try{
			Begin=formate.parse(ss);
			calend.setTime(Begin);
			calend.add(Calendar.DATE,i);
			re=formate.format(calend.getTime());
			//re=format.format(Begin);
		}
		catch(Exception e){}
		}

		return re;
	}
	public String ChangeDateForMonBack(String ss,int i)
	{
		Donull donull=new Donull();
		String re="";
		if(donull.Ofint(ss)==0){
			Calendar calend=Calendar.getInstance();
			DateFormat formate = new SimpleDateFormat("yyyy-MM-dd"); 
			Date date=new Date();
		try{
			date=formate.parse(ss);
			calend.setTime(date);
			calend.add(Calendar.MONTH,i);
			re=formate.format(calend.getTime());
		}
		catch(Exception e){}
		}

		return re;
	}
	public String DateToCn(String ss)
	{

		Donull donull=new Donull();
		String re="";
		int month=0;
		int year=0;
		int date=0;
		if(donull.Ofint(ss)==0){
		Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try{
			Begin=format.parse(ss);
			month=Begin.getMonth()+1;
			year=Begin.getYear()+1900;
			date=Begin.getDate();
			re=String.valueOf(year)+"年"+String.valueOf(month)+"月"+String.valueOf(date)+"日";
			//re=format.format(Begin);
		}
		catch(Exception e){}
		}

		return re;
	}
	public String DateToCnEndOfMon(String ss)
	{

		Donull donull=new Donull();
		String re="";
		int month=0;
		int year=0;
		//int date=0;
		if(donull.Ofint(ss)==0){
		Date Begin=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
		try{
			Begin=format.parse(ss);
			month=Begin.getMonth()+1;
			year=Begin.getYear()+1900;
			//date=Begin.getDate();
			re=String.valueOf(year)+"年"+String.valueOf(month)+"月";
			//re=format.format(Begin);
		}
		catch(Exception e){}
		}

		return re;
	}
	public int DateCompare(String ss,String xx)
	{

		Donull donull=new Donull();
		int re=9999;
		if(donull.Ofint(ss)==0&&donull.Ofint(xx)==0){
			Date Begin=new Date();
			Date end=new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			try{
				Begin=format.parse(ss);
				end=format.parse(xx);
				re=Begin.compareTo(end);
			//re=format.format(Begin);
			}
		catch(Exception e){}
		}

		return re;
	}
	  /**
	   * 分割字串
	   * @param source 原始字符
	   * @param div 分割符
	   * @return 字符串数组
	   */
	  public String[] split(String source,String div) {
	    int arynum = 0, intIdx=0, intIdex=0, div_length = div.length();
	    if(source.compareTo("") != 0) {
	      if(source.indexOf(div) != -1) {
	        intIdx = source.indexOf(div);
	        for(int intCount =1 ; ; intCount++) {
	          if(source.indexOf(div,intIdx+div_length)!=-1){
	            intIdx= source.indexOf(div,intIdx+div_length);
	            arynum = intCount;
	          }
	          else { arynum += 2; break;}
	        }
	          }else arynum =1;
	      }else arynum = 0;

	      intIdx=0;
	      intIdex=0;
	      String[] returnStr = new String[arynum];

	      if(source.compareTo("")!=0){
	          if(source.indexOf(div)!=-1){
	              intIdx = (int)source.indexOf(div);
	              returnStr[0]= (String)source.substring(0,intIdx);
	              for(int intCount =1 ; ; intCount++){
	                  if(source.indexOf(div,intIdx+div_length)!=-1){
	                      intIdex=(int)source.indexOf(div,intIdx+div_length);
	                      returnStr[intCount] = (String)source.substring(intIdx+div_length,intIdex);
	                      intIdx = (int)source.indexOf(div,intIdx+div_length);
	                  }
	                  else {
	                      returnStr[intCount] = (String)source.substring(intIdx+div_length,source.length());
	                      break;
	                  }
	              }
	          }
	          else {returnStr[0] = (String)source.substring(0,source.length());return returnStr;}
	      }
	      else {return returnStr;}
	      return returnStr;
	  }

	  public float dealNull(Double dbl) {
	    float returnflt = 0;
	    if (dbl == null)
	      returnflt = 0;
	    else
	      returnflt = dbl.floatValue();
	    return returnflt;
	  }

	  public Object dealNull(Object obj) {
	    Object returnstr = null;
	    if (obj == null)
	      returnstr = (Object)("");
	    else
	      returnstr = obj;
	    return returnstr;
	  }

	  
	  /**
	   * 字符串替换函数
	   * @param str 原始字符串
	   * @param substr 要替换的字符
	   * @param restr 替换后的字符
	   * @return 替换完成的字符串
	   */
	  /*public String replace(String str,String substr,String restr) {
	    String[] tmp = split(str,substr);
	    String returnstr = null;
	    if(tmp.length!=0) {
	      returnstr = tmp[0];
	      for(int i = 0 ; i < tmp.length - 1 ; i++)
	        returnstr =dealNull(returnstr) + restr +tmp[i+1];
	    }
	    return dealNull(returnstr);
	  }
*/
	  /**
	   * 将回车符替换成Html中的换行符
	   * @param txt 原始文本
	   * @return 替换之后的文本
	   */
	  public String addBr(String txt){
	    if (txt != null) {
	      int intLen=txt.length();
	      if(intLen>30)
	          {txt=txt.substring(0,29)+"...";}
	          txt = replace(txt,"\n","<br>");
	          txt = replace(txt," ","&nbsp;");
	    }
	    return txt;
	  }

	  public String addBrShow(String txt){
	    if (txt != null) {
	      txt = replace(txt,"\n","<br>");
	      txt = replace(txt," ","&nbsp;");
	    }
	    return txt;
	  }

	  public String addColon(String txt) {
	    if (txt != null) {
	      txt = replace(txt,"<br>",":<br>");
	    }
	    return txt;
	  }

	  public String changeColor(String txt) {
	    if (txt != null) {
	      txt = replace(txt,"<br>","</font><br>");
	      txt = replace(txt,"<br>:","<br><font color=#408080>:");
	    }
	    return txt;
	  }

	  /**
	   * 将Html中的换行符去掉
	   * @param txt 原始文本
	   * @return 替换之后的文本
	   */
	  public String delBr(String txt){
	    if (txt != null) {
	      txt = replace(txt,"<br>","");
	    }
	    return txt;
	  }

	  /**
	   * 为'和\增加转移符，以便加入数据库，'替换为\'，\替换为\\
	   * @param txt 原始文本
	   * @return 增加转移符后的文本
	   */
	  public String addSlashes(String txt){
	    if (txt != null) {
	      txt = replace(txt,"\\","\\\\");
	      txt = replace(txt,"\'","\\\'");
	    }
	    return txt;
	  }

	  /**
	   * 取消转移符
	   * @param txt 原始文本
	   * @return 取消转移符后的文本
	   */
	  public String stripslashes(String txt){
	    if (txt != null) {
	      txt = replace(txt,"\\\\","\\");
	      txt = replace(txt,"\'","'");
	      txt = replace(txt,"\\\"","\"");
	      txt = replace(txt,"\\&quot;","\"");
	    }
	    return txt;
	  }

	  /**
	   * 取消Html标记
	   * @param txt 原始文本
	   * @return 取消Html标记后的文本
	   */
	 /* public String htmlEncode(String txt){
	    if (txt != null) {
	      txt = replace(txt,"&","&amp;");
	      txt = replace(txt,"&amp;amp;","&amp;");
	      txt = replace(txt,"&amp;quot;","&quot;");
	      txt = replace(txt,"\"","&quot;");
	      txt = replace(txt,"&amp;lt;","&lt;");
	      txt = replace(txt,"<","&lt;");
	      txt = replace(txt,"&amp;gt;","&gt;");
	      txt = replace(txt,">","&gt;");
	      txt = replace(txt,"&amp;nbsp;","&nbsp;");
	      //txt = replace(txt," ","&nbsp;");
	    }
	    return txt;
	  }*/

	  /**
	   * 返回Html标记
	   * @param txt 原始文本
	   * @return 返回Html后的文本
	   */
	  public String unHtmlEncode(String txt){
	    if (txt != null) {
	      txt = replace(txt,"&amp;","&");
	      txt = replace(txt,"&quot;","\"");
	      txt = replace(txt,"&lt;","<");
	      txt = replace(txt,"&gt;",">");
	      txt = replace(txt,"&nbsp;"," ");
	    }
	    return txt;
	  }

	  /**
	   * 去除Html中脚本标记
	   * @param txt 原始文本
	   * @return 去除脚本后的文本
	   */
	  public String ScriptEncode(String txt){
	    if (txt != null) {
	      txt = replace(txt,"script"," ");
	      txt = replace(txt,"SCRIPT"," ");
	      txt = replace(txt,"Script"," ");
	      txt = replace(txt,"SCript"," ");
	    }
	    return txt;
	  }
	  /**
	   * 转换字符串编码为GBK
	   * @param txt 字符串参数
	   * @return 转换后的字符串
	   */
	  public String toGBK(String txt) {
	    String reStr = null;
	    try {
	      if (txt==null)
	        reStr = null;
	      else
	        reStr = new String(txt.getBytes("ISO8859-1"),"GBK");
	    } catch (Exception ex) {
	      System.err.println(ex.getMessage());
	    }
	    return reStr;
	  }
	  public static int str2int(String strValue)
	    {
	        if(strValue == null)
	            return 0;
	        int nValue = 0;
	        try
	        {
	            String temp = strValue.trim();
	            nValue = (new Double(temp)).intValue();
	        }
	        catch(NumberFormatException numberformatexception) { }
	        return nValue;
	    }

	    public static long str2long(String strValue)
	    {
	        if(strValue == null)
	            return 0L;
	        long nValue = 0L;
	        try
	        {
	            String temp = strValue.trim();
	            nValue = (new Double(temp)).longValue();
	        }
	        catch(NumberFormatException numberformatexception) { }
	        return nValue;
	    }

	    public static int str2int(String strValue, int nDefault)
	    {
	        int nValue = nDefault;
	        if(strValue == null)
	            return nValue;
	        try
	        {
	            String temp = strValue.trim();
	            nValue = (new Double(temp)).intValue();
	        }
	        catch(NumberFormatException e)
	        {
	            nValue = nDefault;
	        }
	        return nValue;
	    }

	    public static double str2double(String strValue)
	    {
	        if(strValue == null)
	            return 0.0D;
	        double nValue = 0.0D;
	        try
	        {
	            String temp = strValue.trim();
	            nValue = Double.parseDouble(temp);
	        }
	        catch(NumberFormatException numberformatexception) { }
	        return nValue;
	    }

	    public static int double2int(double strValue)
	    {
	        if(strValue < 0.0D)
	            return 0;
	        int nValue = 0;
	        try
	        {
	            String temp = String.valueOf(strValue);
	            nValue = str2int(temp);
	        }
	        catch(NumberFormatException numberformatexception) { }
	        return nValue;
	    }

	    public static long double2long(double strValue)
	    {
	        if(strValue < 0.0D)
	            return 0L;
	        long nValue = 0L;
	        try
	        {
	            String temp = String.valueOf(strValue);
	            nValue = str2long(temp);
	        }
	        catch(NumberFormatException numberformatexception) { }
	        return nValue;
	    }

	    public static double str2double(String strValue, double nDefault)
	    {
	        double nValue = nDefault;
	        if(strValue == null)
	            return nValue;
	        if(strValue.equals(""))
	            return nValue;
	        try
	        {
	            String temp = strValue.trim();
	            nValue = Double.parseDouble(temp);
	        }
	        catch(NumberFormatException numberformatexception) { }
	        return nValue;
	    }

	    public static Calendar str2calendar(String strValue)
	    {
	        if(strValue == null)
	            return null;
	        Calendar theDate;
	        try
	        {
	            String str = strValue.substring(4, 5);
	            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy" + str + "MM" + str + "dd HH:mm:ss");
	            theFormat.parse(strValue);
	            theDate = theFormat.getCalendar();
	        }
	        catch(ParseException ex)
	        {
	            theDate = null;
	        }
	        catch(IndexOutOfBoundsException ex)
	        {
	            theDate = null;
	        }
	        return theDate;
	    }

	    public static java.util.Date str2date(String strValue)
	    {
	        if(strValue == null)
	            return null;
	        if(strValue.equals(""))
	            return null;
	        java.util.Date theDate;
	        try
	        {
	            String str = strValue.substring(4, 5);
	            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy" + str + "MM" + str + "dd" + " " + "HH" + ":" + "mm" + ":" + "ss");
	            theDate = theFormat.parse(strValue);
	        }
	        catch(Exception ex)
	        {
	            theDate = null;
	        }
	        return theDate;
	    }

	    public static java.util.Date yyyymmdd2date(String strValue)
	    {
	        if(strValue == null)
	            return null;
	        if(strValue.equals(""))
	            return null;
	        java.util.Date theDate;
	        try
	        {
	            String str = strValue.substring(4, 5);
	            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy" + str + "MM" + str + "dd");
	            theDate = theFormat.parse(strValue);
	        }
	        catch(Exception ex)
	        {
	            theDate = null;
	        }
	        return theDate;
	    }

	    public static String strdate2str(String strValue)
	    {
	        if(strValue != null && !strValue.equals(""))
	        {
	            if(str2date(strValue) != null)
	                return FULL_YMD_FORMAT.format(str2date(strValue));
	            else
	                return strValue;
	        } else
	        {
	            return "";
	        }
	    }

	    public static String strdate2str(String strValue, String dateFormat)
	    {
	        if(strValue != null && !strValue.equals("") && dateFormat != null && !dateFormat.equals(""))
	        {
	            if(str2date(strValue) != null)
	                return (new SimpleDateFormat(dateFormat)).format(str2date(strValue));
	            else
	                return strValue;
	        } else
	        {
	            return "";
	        }
	    }

	    public static String shortstrdate2str(String strValue)
	    {
	        if(strValue != null && !strValue.equals(""))
	            return FULL_YMD_FORMAT.format(shortstr2date(strValue));
	        else
	            return "";
	    }

	   /* public static Date sqlstr2date(String strValue)
	    {
	        if(strValue == null)
	            return null;
	        if(strValue.equals(""))
	            return null;
	        Date theDate;
	        try
	        {
	            theDate = Date.valueOf(strValue);
	        }
	        catch(Exception ex)
	        {
	            theDate = null;
	        }
	        return theDate;
	    }*/

	    public static java.util.Date shortstr2date(String strValue)
	    {
	        if(strValue == null)
	            return null;
	        if(strValue.equals(""))
	            return null;
	        java.util.Date theDate;
	        try
	        {
	            String str = strValue.substring(4, 5);
	            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy" + str + "MM" + str + "dd");
	            theDate = theFormat.parse(strValue);
	        }
	        catch(Exception ex)
	        {
	            theDate = null;
	        }
	        return theDate;
	    }

	    public static boolean isDate(String strValue)
	    {
	        java.util.Date checkdate = shortstr2date(strValue);
	        return checkdate != null;
	    }

	    public static java.util.Date str2date(String strValue, java.util.Date theDefault)
	    {
	        if(strValue == null)
	            return theDefault;
	        SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        java.util.Date theDate;
	        try
	        {
	            theDate = theFormat.parse(strValue);
	        }
	        catch(ParseException ex)
	        {
	            theDate = theDefault;
	        }
	        return theDate;
	    }

	    public static String date2str(java.util.Date aDate)
	    {
	        if(aDate == null)
	        {
	            return "";
	        } else
	        {
	            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            return theFormat.format(aDate);
	        }
	    }

	    public static String date2shortstr(java.util.Date date)
	    {
	        if(date == null)
	        {
	            return "";
	        } else
	        {
	            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd");
	            return theFormat.format(date);
	        }
	    }

	    public static String date2sqlstr(Date date)
	    {
	        if(date == null)
	        {
	            return "";
	        } else
	        {
	            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd");
	            return theFormat.format(date);
	        }
	    }

	    public static String blankNull(String str)
	    {
	        if(str == null)
	            return "";
	        else
	            return str;
	    }

	    public static String blankNull(String str, String strDefault)
	    {
	        if(str == null)
	            return strDefault;
	        else
	            return str;
	    }

	    public static String strNull(String str)
	    {
	        if(str == null)
	            return "";
	        else
	            return str;
	    }

	    public static String strNullUl(String str)
	    {
	        if(str == null || str.equals(""))
	            return "_";
	        else
	            return str;
	    }

	    public static String strNullEx(String str)
	    {
	        if(str == null || str.equals("") || str.equals("_"))
	            return copy("　", 2);
	        if(str.equals("__"))
	            return "_";
	        else
	            return str;
	    }

	    public static String strNullEx(String str, int count)
	    {
	        if(str == null || str.equals("") || str.equals("_"))
	            return copy("　", count);
	        if(str.equals("__"))
	            return "_";
	        else
	            return str;
	    }

	    public static String copy(String str, int count)
	    {
	        StringBuffer sbf = new StringBuffer("");
	        if(count >= 0)
	        {
	            for(int i = 1; i <= count; i++)
	                sbf.append(str);

	        }
	        return sbf.toString();
	    }

	    public static String strNull(String str, String strDefault)
	    {
	        if(str == null)
	            return strDefault;
	        else
	            return str;
	    }

	    public static String strNullEx(String str, String strDefault)
	    {
	        if(str == null || str.equals("") || str.equals("_"))
	            return strDefault;
	        if(str.equals("__"))
	            return "_";
	        else
	            return str;
	    }

	    public static String int2str(int intValue)
	    {
	        return String.valueOf(intValue);
	    }

	    public static String int2str(long longValue)
	    {
	        return String.valueOf(longValue);
	    }

	    public static String int2str(double intValue)
	    {
	        return String.valueOf(intValue);
	    }

	    public static String intDefault(int value, int nDefault)
	    {
	        if(value == nDefault)
	            return null;
	        else
	            return int2str(value);
	    }

	    public static String intNull(int intValue)
	    {
	        if(intValue == 0x1869f || intValue == 0x186a0)
	            return "";
	        else
	            return int2str(intValue);
	    }

	    public static String intNullUl(int intValue)
	    {
	        if(intValue == 0x1869f || intValue == 0x186a0)
	            return "_";
	        else
	            return int2str(intValue);
	    }

	    public static String intNull(double intValue)
	    {
	        if(intValue == 99999D || intValue == 100000D)
	            return "";
	        else
	            return int2str(intValue);
	    }

	    public static String intNullUl(double intValue)
	    {
	        if(intValue == 99999D || intValue == 100000D)
	            return "_";
	        else
	            return int2str(intValue);
	    }

	    public static String intNullEx(int intValue)
	    {
	        if(intValue == 0x1869f || intValue == 0x186a0)
	            return copy("　", 2);
	        else
	            return int2str(intValue);
	    }

	    public static String intNullEx(int intValue, int count)
	    {
	        if(intValue == 0x1869f || intValue == 0x186a0)
	            return copy("　", count);
	        else
	            return int2str(intValue);
	    }

	    public static String intNullEx(int intValue, String strValue)
	    {
	        if(intValue == 0x1869f || intValue == 0x186a0)
	            return strValue;
	        else
	            return int2str(intValue);
	    }

	    public static String intNullEx(double intValue)
	    {
	        if(intValue == 99999D || intValue == 100000D)
	            return copy("　", 2);
	        else
	            return int2str(intValue);
	    }

	    public static String intNullEx(double intValue, int count)
	    {
	        if(intValue == 99999D || intValue == 100000D)
	            return copy("　", count);
	        else
	            return int2str(intValue);
	    }

	    public static String intNullEx(double intValue, String strValue)
	    {
	        if(intValue == 99999D || intValue == 100000D)
	            return strValue;
	        else
	            return int2str(intValue);
	    }

	    public static boolean isNull(String strValue)
	    {
	        return strValue == null;
	    }

	    public static boolean isNull(String strValue[])
	    {
	        return strValue == null;
	    }

	    public static boolean isNull(int intValue)
	    {
	        return intValue == 0x1869f;
	    }

	    public static boolean isNull(double intValue)
	    {
	        return intValue == 99999D;
	    }

	    public static boolean isInt(String strValue)
	    {
	        try
	        {
	            double nValue = Double.parseDouble(strValue);
	            return nValue - (double)Integer.parseInt(strValue) == 0.0D;
	        }
	        catch(NumberFormatException e)
	        {
	            return false;
	        }
	    }

	    public static boolean isInt(double nValue)
	    {
	        return nValue - (double)(new Double(nValue)).intValue() == 0.0D;
	    }

	    public static boolean isDouble(double nValue)
	    {
	        return nValue - (double)(new Double(nValue)).intValue() != 0.0D;
	    }

	    public static boolean isDouble(String strValue)
	    {
	        try
	        {
	            double nValue = Double.parseDouble(strValue);
	            return nValue - (double)Integer.parseInt(strValue) != 0.0D;
	        }
	        catch(NumberFormatException e)
	        {
	            return false;
	        }
	    }

	    public static boolean isNumber(String strValue)
	    {
	        boolean isInt;
	        try
	        {
	            int nValue = Integer.parseInt(strValue);
	            isInt = true;
	        }
	        catch(NumberFormatException e)
	        {
	            isInt = false;
	        }
	        boolean isDouble;
	        try
	        {
	            double nValue2 = Double.parseDouble(strValue);
	            isDouble = true;
	        }
	        catch(NumberFormatException e)
	        {
	            isDouble = false;
	        }
	        return isInt || isDouble;
	    }

	    public static boolean isError(int intValue)
	    {
	        return intValue == 0x186a0;
	    }

	    public static boolean isError(double intValue)
	    {
	        return intValue == 100000D;
	    }

	    public static String getWeek(Calendar theDate)
	    {
	        if(theDate == null)
	            return "";
	        switch(theDate.get(7))
	        {
	        case 1: // '\001'
	            return "星期日";

	        case 2: // '\002'
	            return "星期一";

	        case 3: // '\003'
	            return "星期二";

	        case 4: // '\004'
	            return "星期三";

	        case 5: // '\005'
	            return "星期四";

	        case 6: // '\006'
	            return "星期五";

	        case 7: // '\007'
	            return "星期六";
	        }
	        return "";
	    }

	    public static long getLong(String theValue)
	    {
	        int nPos = theValue.indexOf(46);
	        long lValue;
	        if(nPos == -1)
	        {
	            lValue = Long.parseLong(theValue);
	            lValue *= 100L;
	        } else
	        {
	            String strInt;
	            if(nPos != 0)
	                strInt = theValue.substring(0, nPos);
	            else
	                strInt = "0";
	            nPos++;
	            int nLen = theValue.length();
	            int nXS;
	            if(nLen - nPos > 2)
	                nXS = Integer.parseInt(theValue.substring(nPos, nPos + 2));
	            else
	            if(nLen == nPos)
	                nXS = 0;
	            else
	                nXS = Integer.parseInt(theValue.substring(nPos));
	            if(nXS > 0 && nXS < 10 && !theValue.substring(nPos, nPos + 1).equals("0"))
	                nXS *= 10;
	            lValue = Long.parseLong(strInt) * 100L;
	            lValue += nXS;
	        }
	        System.out.println("lValue=" + lValue);
	        return lValue;
	    }

	    public static String getToday()
	    {
	        java.util.Date theDate = new java.util.Date();
	        return CHINESE_DATE_FORMAT.format(theDate);
	    }

	    public static String format(double col_value, int precision)
	    {
	        if(precision >= 0)
	        {
	            DecimalFormat dataFormat = new DecimalFormat("0" + (precision == 0 ? "" : ".") + copy("0", precision));
	            String value_str_final = dataFormat.format(col_value);
	            return value_str_final;
	        }
	        if(isInt(col_value))
	        {
	            DecimalFormat dataFormat = new DecimalFormat("0");
	            String value_str_final = dataFormat.format(col_value);
	            return value_str_final;
	        } else
	        {
	            DecimalFormat dataFormat = new DecimalFormat("0." + copy("0", Math.abs(precision)));
	            String value_str_final = dataFormat.format(col_value);
	            return value_str_final;
	        }
	    }

	    public static String fMoney(long col_value)
	    {
	        String value_str_final = "";
	        long jstr = 0L;
	        long fstr = 0L;
	        if(col_value >= 100L)
	        {
	            if(col_value % 100L == 0L)
	            {
	                value_str_final = col_value / 100L + ".00";
	                return value_str_final;
	            } else
	            {
	                jstr = (col_value / 10L) % 10L;
	                fstr = col_value % 10L;
	                value_str_final = col_value / 100L + "." + jstr + fstr;
	                return value_str_final;
	            }
	        } else
	        {
	            jstr = col_value / 10L;
	            fstr = col_value % 10L;
	            value_str_final = "0." + jstr + fstr;
	            return value_str_final;
	        }
	    }

	    public static String combineStr(String strValue[], String splitStr)
	    {
	        if(strValue != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < strValue.length; i++)
	                if(strValue[i] != null && !strValue[i].equals(""))
	                {
	                    if(i > 0)
	                        str.append(splitStr);
	                    str.append(strValue[i]);
	                }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String combineStr(String strValue[], String splitStr, String qutoStr)
	    {
	        if(strValue != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < strValue.length; i++)
	                if(strValue[i] != null && !strValue[i].equals(""))
	                {
	                    if(i > 0)
	                        str.append(splitStr);
	                    str.append(qutoStr);
	                    str.append(strValue[i]);
	                    str.append(qutoStr);
	                }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String combineStr(String strValue[])
	    {
	        String splitStr = "#";
	        if(strValue != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < strValue.length; i++)
	                if(!strValue[i].equals("") && strValue[i] != null && !strValue[i].equals(""))
	                {
	                    if(i > 0)
	                        str.append(splitStr);
	                    str.append(strValue[i]);
	                }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String combineInt(int intArr[])
	    {
	        String splitStr = "#";
	        if(intArr != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < intArr.length; i++)
	            {
	                if(i > 0)
	                    str.append(splitStr);
	                str.append(intArr[i]);
	            }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String combineLong(long intArr[])
	    {
	        String splitStr = "#";
	        if(intArr != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < intArr.length; i++)
	            {
	                if(i > 0)
	                    str.append(splitStr);
	                str.append(intArr[i]);
	            }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String combineInt(int intArr[], String splitStr)
	    {
	        if(intArr != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < intArr.length; i++)
	            {
	                if(i > 0)
	                    str.append(splitStr);
	                str.append(intArr[i]);
	            }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String combineLong(long intArr[], String splitStr)
	    {
	        if(intArr != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < intArr.length; i++)
	            {
	                if(i > 0)
	                    str.append(splitStr);
	                str.append(intArr[i]);
	            }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String combineLong(long strValue[], String splitStr, String qutoStr)
	    {
	        if(strValue != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < strValue.length; i++)
	                if(strValue[i] != 0L)
	                {
	                    if(i > 0)
	                        str.append(splitStr);
	                    str.append(qutoStr);
	                    str.append(strValue[i]);
	                    str.append(qutoStr);
	                }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String combineInt(int intArr[][], String splitStr)
	    {
	        if(intArr == null || splitStr == null || splitStr.length() == 0)
	            return "";
	        StringBuffer sbCombined = new StringBuffer();
	        boolean bConverted = false;
	        for(int i = 0; i < intArr.length; i++)
	        {
	            String str = combineInt(intArr[i], splitStr);
	            if(str.length() != 0)
	            {
	                if(bConverted)
	                    sbCombined.append(splitStr);
	                else
	                    bConverted = true;
	                sbCombined.append(str);
	            }
	        }

	        return sbCombined.toString();
	    }

	    public static String combineStrEx(String strValue[])
	    {
	        String splitStr = "#";
	        if(strValue != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < strValue.length; i++)
	            {
	                if(i > 0)
	                    str.append(splitStr);
	                if(strValue[i] == null || strValue[i].equals(""))
	                    str.append("_");
	                else
	                    str.append(strValue[i]);
	            }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String combineStrEx(String strValue[], String splitStr)
	    {
	        if(strValue != null && splitStr != null && !splitStr.equals(""))
	        {
	            StringBuffer str = new StringBuffer("");
	            for(int i = 0; i < strValue.length; i++)
	            {
	                if(i > 0)
	                    str.append(splitStr);
	                if(strValue[i] == null || strValue[i].equals(""))
	                    str.append("_");
	                else
	                    str.append(strValue[i]);
	            }

	            return str.toString();
	        } else
	        {
	            return "";
	        }
	    }

	    public static String[] splitStr(String strValue, String splitStr)
	    {
	        if(strValue != null && splitStr != null)
	        {
	            Vector vc = new Vector();
	            for(StringTokenizer strToken = new StringTokenizer(strValue, splitStr); strToken.hasMoreTokens(); vc.add(strToken.nextToken()));
	            int size = vc.size();
	            String returnStr[] = new String[size];
	            for(int j = 0; j < size; j++)
	                returnStr[j] = (String)vc.get(j);

	            return returnStr;
	        } else
	        {
	            return new String[0];
	        }
	    }

	    public static String[] splitStr(String strValue)
	    {
	        if(strValue != null)
	        {
	            Vector vc = new Vector();
	            String splitStr = "#";
	            for(StringTokenizer strToken = new StringTokenizer(strValue, splitStr); strToken.hasMoreTokens(); vc.add(strToken.nextToken()));
	            int size = vc.size();
	            String returnStr[] = new String[size];
	            for(int j = 0; j < size; j++)
	                returnStr[j] = (String)vc.get(j);

	            return returnStr;
	        } else
	        {
	            return new String[0];
	        }
	    }

	    public static String[] splitStrEx(String strValue)
	    {
	        if(strValue != null)
	        {
	            Vector vc = new Vector();
	            String splitStr = "#";
	            for(StringTokenizer strToken = new StringTokenizer(strValue, splitStr); strToken.hasMoreTokens(); vc.add(strToken.nextToken()));
	            int size = vc.size();
	            String returnStr[] = new String[size];
	            for(int j = 0; j < size; j++)
	                if(((String)vc.get(j)).equals("_"))
	                    returnStr[j] = "";
	                else
	                if(((String)vc.get(j)).equals("__"))
	                    returnStr[j] = "_";
	                else
	                    returnStr[j] = (String)vc.get(j);

	            return returnStr;
	        } else
	        {
	            return new String[0];
	        }
	    }

	    public static String[] splitStrEx(String strValue, String splitStr)
	    {
	        if(strValue != null)
	        {
	            Vector vc = new Vector();
	            for(StringTokenizer strToken = new StringTokenizer(strValue, splitStr); strToken.hasMoreTokens(); vc.add(strToken.nextToken()));
	            int size = vc.size();
	            String returnStr[] = new String[size];
	            for(int j = 0; j < size; j++)
	                if(((String)vc.get(j)).equals("_"))
	                    returnStr[j] = "";
	                else
	                if(((String)vc.get(j)).equals("__"))
	                    returnStr[j] = "_";
	                else
	                    returnStr[j] = (String)vc.get(j);

	            return returnStr;
	        } else
	        {
	            return new String[0];
	        }
	    }

	    public static boolean contain(String strValue[], String strToCheck)
	    {
	        if(strValue != null)
	        {
	            for(int i = 0; strValue != null && i < strValue.length; i++)
	                if(strValue != null && strToCheck != null && strValue[i] != null && strValue[i].equals(strToCheck))
	                    return true;

	        }
	        return false;
	    }

	    public static boolean contain(int intValue[], int intToCheck)
	    {
	        if(intValue != null)
	        {
	            for(int i = 0; i < intValue.length; i++)
	                if(intValue[i] == intToCheck)
	                    return true;

	        }
	        return false;
	    }

	    public static String nextChar(String orgChar)
	    {
	        if(orgChar != null && !orgChar.equals(""))
	        {
	            char ch = orgChar.charAt(0);
	            return String.valueOf(++ch);
	        } else
	        {
	            return "";
	        }
	    }

	    public static String[] intArrToStrArr(int intArr[])
	    {
	        String theStr[] = new String[intArr.length];
	        for(int i = 0; i < intArr.length; i++)
	            theStr[i] = int2str(intArr[i]);

	        return theStr;
	    }

	    public static Integer[] intArrToIntegerArr(int intArr[])
	    {
	        Integer theInt[] = new Integer[intArr.length];
	        for(int i = 0; i < intArr.length; i++)
	            theInt[i] = new Integer(intArr[i]);

	        return theInt;
	    }

	    public static boolean isAll(String orgStr, char desChar)
	    {
	        for(int i = 0; i < orgStr.length(); i++)
	            if(orgStr.charAt(i) != desChar)
	                return false;

	        return true;
	    }

	    public static String replace(String strSource, String strFrom, String strTo)
	    {
	        if(strSource != null)
	        {
	            if(strFrom != null && strTo != null)
	            {
	                String strDest = "";
	                int intFromLen = strFrom.length();
	                int intPos;
	                while((intPos = strSource.indexOf(strFrom)) != -1) 
	                {
	                    strDest = strDest + strSource.substring(0, intPos);
	                    strDest = strDest + strTo;
	                    strSource = strSource.substring(intPos + intFromLen);
	                }
	                strDest = strDest + strSource;
	                return strDest;
	            } else
	            {
	                return strSource;
	            }
	        } else
	        {
	            return strSource;
	        }
	    }

	    public static String return2br(String str)
	    {
	        if(str == null)
	            return "";
	        else
	            return replace(str, "\r\n", "<br>\r\n");
	    }

	    public static int[] strArrToIntArr(String strArr[])
	    {
	        if(strArr != null)
	        {
	            int intStr[] = new int[strArr.length];
	            for(int i = 0; i < strArr.length; i++)
	                intStr[i] = str2int(strArr[i]);

	            return intStr;
	        } else
	        {
	            return new int[0];
	        }
	    }

	    public static int[] strArr2IntArr(String strArr[])
	    {
	        if(strArr != null)
	        {
	            int intStr[] = new int[strArr.length];
	            for(int i = 0; i < strArr.length; i++)
	                intStr[i] = str2int(strArr[i]);

	            return intStr;
	        } else
	        {
	            return new int[0];
	        }
	    }

	    public static long[] strArrToLongArr(String strArr[])
	    {
	        if(strArr != null)
	        {
	            long longStr[] = new long[strArr.length];
	            for(int i = 0; i < strArr.length; i++)
	                longStr[i] = str2long(strArr[i]);

	            return longStr;
	        } else
	        {
	            return new long[0];
	        }
	    }

	    public static long[] strArr2LongArr(String strArr[])
	    {
	        if(strArr != null)
	        {
	            long longStr[] = new long[strArr.length];
	            for(int i = 0; i < strArr.length; i++)
	                longStr[i] = str2long(strArr[i]);

	            return longStr;
	        } else
	        {
	            return new long[0];
	        }
	    }

	    public static double[] strArrToDoubleArr(String strArr[])
	    {
	        if(strArr != null)
	        {
	            double doubleStr[] = new double[strArr.length];
	            for(int i = 0; i < strArr.length; i++)
	                doubleStr[i] = str2double(strArr[i]);

	            return doubleStr;
	        } else
	        {
	            return new double[0];
	        }
	    }

	    public static double[] strArr2DoubleArr(String strArr[])
	    {
	        if(strArr != null)
	        {
	            double doubleStr[] = new double[strArr.length];
	            for(int i = 0; i < strArr.length; i++)
	                doubleStr[i] = str2double(strArr[i]);

	            return doubleStr;
	        } else
	        {
	            return new double[0];
	        }
	    }

	    public static String replaceQutoEx(String strSource)
	    {
	        if(strSource != null)
	        {
	            strSource = replace(strSource, "'", "\\'");
	            strSource = replace(strSource, "\"", "\\\"");
	        }
	        return strSource;
	    }

	    public static String replaceQuto(String strSource)
	    {
	        if(strSource != null)
	            strSource = replace(strSource, "'", "''");
	        return strSource;
	    }

	    public static String htmlEncode(String strSource)
	    {
	        if(strSource != null)
	        {
	            for(int i = 0; i < replaceStr.length;)
	                if(i + 1 < replaceStr.length)
	                {
	                    strSource = replace(strSource, replaceStr[i], replaceStr[i + 1]);
	                    i += 2;
	                }

	        }
	        return strSource;
	    }
	    public static String unhtmlEncode(String strSource)
	    {
	        if(strSource != null)
	        {
	            for(int i = 0; i < replaceStr.length;)
	                if(i + 1 < replaceStr.length)
	                {
	                    strSource = replace(strSource, replaceStr[i + 1], replaceStr[i]);
	                    i += 2;
	                }

	        }
	        return strSource;
	    }

	    public static String createImgButton(String strImg, String strTitle, String strClickFunc)
	    {
	        StringBuffer sbHtml = new StringBuffer("<table width=\"50\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">");
	        sbHtml.append("<tr>");
	        sbHtml.append("<td class=\"btntd\" style=\"cursor:hand\" nowrap");
	        sbHtml.append(" onselectstart=\"return false\"");
	        sbHtml.append(" onMouseDown=\"this.className='btntd_mousedown'\"");
	        sbHtml.append(" onMouseUp=\"this.className='btntd'\"");
	        sbHtml.append(" onMouseOut=\"this.className='btntd'\"");
	        sbHtml.append(" onclick=\"");
	        sbHtml.append(strClickFunc);
	        sbHtml.append("\"");
	        sbHtml.append(" height=\"21\">");
	        if(strImg != null)
	        {
	            sbHtml.append("<img src=\"");
	            sbHtml.append(strImg);
	            sbHtml.append("\" width=\"16\" height=\"16\" align=\"absmiddle\">&nbsp;");
	        }
	        sbHtml.append(strTitle);
	        sbHtml.append("</td></tr></table>");
	        return sbHtml.toString();
	    }

	    public static String createDefImgButton(String strImg, String strTitle, String strClickFunc)
	    {
	        StringBuffer sbHtml = new StringBuffer("<table width=\"50\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">");
	        sbHtml.append("<tr>");
	        sbHtml.append("<td class=\"btntd\" style=\"cursor:hand\" nowrap");
	        sbHtml.append(" onselectstart=\"return false\"");
	        sbHtml.append(" onMouseDown=\"this.className='btntd_mousedown'\"");
	        sbHtml.append(" onMouseUp=\"this.className='btntd'\"");
	        sbHtml.append(" onMouseOut=\"this.className='btntd'\"");
	        sbHtml.append(" onclick=\"");
	        sbHtml.append(strClickFunc);
	        sbHtml.append("\"");
	        sbHtml.append(" height=\"21\">");
	        if(strImg != null)
	        {
	            sbHtml.append("<input type=\"image\" src=\"");
	            sbHtml.append(strImg);
	            sbHtml.append("\" width=\"16\" height=\"16\" align=\"absmiddle\">&nbsp;");
	        }
	        sbHtml.append(strTitle);
	        sbHtml.append("</td></tr></table>");
	        return sbHtml.toString();
	    }

	    public static String toJSArray(String astrValue[], String strJSVar)
	    {
	        if(astrValue == null || astrValue.length == 0)
	            return "";
	        StringBuffer sbHTML = new StringBuffer(strJSVar);
	        sbHTML.append("=new Array();\r\n");
	        for(int i = 0; i < astrValue.length; i++)
	        {
	            sbHTML.append(strJSVar);
	            sbHTML.append('[');
	            sbHTML.append(i);
	            sbHTML.append("]='");
	            sbHTML.append(replaceQuto(blankNull(astrValue[i], "&nbsp;")));
	            sbHTML.append("';\r\n");
	        }

	        return sbHTML.toString();
	    }

	    public static String right(String str, int nRight)
	    {
	        if(str == null)
	        {
	            return null;
	        } else
	        {
	            int nLen = str.length();
	            int nStart = nLen > nRight ? nLen - nRight : 0;
	            return str.substring(nStart, nStart + nRight);
	        }
	    }

	    public static int indexOfChars(String str, char aChars[], int nBeginIndex)
	    {
	        if(nBeginIndex < 0)
	            nBeginIndex = 0;
	        if(aChars == null || aChars.length == 0 || str == null || str.length() <= nBeginIndex)
	            return -1;
	        int nMin = -1;
	        int nIndex = -1;
	        for(int i = 0; i < aChars.length; i++)
	        {
	            nIndex = str.indexOf(aChars[i], nBeginIndex);
	            if(nIndex == nBeginIndex)
	                return nIndex;
	            if(nIndex != -1)
	                if(nMin == -1)
	                    nMin = nIndex;
	                else
	                if(nIndex < nMin)
	                    nMin = nIndex;
	        }

	        return nMin;
	    }

	    public static String getLocalIP()
	    {
	        try
	        {
	            String ip = InetAddress.getLocalHost().toString();
	            ip = ip.substring(ip.indexOf("/") + 1);
	            return ip;
	        }
	        catch(UnknownHostException e)
	        {
	            return "未知IP";
	        }
	    }

	    public static String addPrefix(String str, String pre, int len)
	    {
	        String tmpPre = copy(pre, len);
	        String tmpStr = tmpPre + str;
	        return right(tmpStr, len);
	    }

	    public static String addPrefix(int i, String pre, int len)
	    {
	        String tmpPre = copy(pre, len);
	        String tmpStr = tmpPre + String.valueOf(i);
	        return right(tmpStr, len);
	    }

	    public static boolean isValidateDay(String date1)
	    {
	        if(date1 == null || date1.equals(""))
	            return false;
	        date1 = replace(date1, ".", "-");
	        date1 = replace(date1, "/", "-");
	        int year = 0;
	        int month = 0;
	        int day = 0;
	        boolean flag = true;
	        if(isDate(date1))
	        {
	            String strDate = "";
	            int index = date1.indexOf("-");
	            year = str2int(date1.substring(0, index));
	            date1 = date1.substring(index + 1);
	            index = date1.indexOf("-");
	            month = str2int(date1.substring(0, index));
	            day = str2int(date1.substring(index + 1));
	            if(month < 1 || month > 12)
	                flag = false;
	            if((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day < 1 || day > 31))
	                flag = false;
	            if((month == 4 || month == 6 || month == 9 || month == 11) && (day < 1 || day > 30))
	                flag = false;
	            if(month == 2)
	                if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
	                {
	                    if(day < 1 || day > 29)
	                        flag = false;
	                } else
	                if(day < 1 || day > 28)
	                    flag = false;
	            return flag;
	        } else
	        {
	            return false;
	        }
	    }

	    public static String getTime(int year, int month, int day)
	    {
	        String time = "";
	        time = int2str(year);
	        if(month > 0 && month < 10)
	            time = time + "0" + int2str(month);
	        else
	            time = time + int2str(month);
	        if(day > 0 && day < 10)
	            time = time + "0" + int2str(day);
	        else
	            time = time + int2str(day);
	        return time;
	    }
	    public static void main(String args[]){
	    	Donull d=new Donull();
	    	System.out.println(d.TranTime("2007-02-04"));
	    }
}
