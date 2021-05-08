package com.common.system.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.activation.ActivationException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * Title: DateUtil
 * Description： 日期工具类
 * Author: 陈睿超
 * Date: 2021/4/20 10:19
 * Updater: 陈睿超
 * Date: 2021/4/20 10:19
 * Company: 天职国际
 * Version: 1.0
 **/
public class DateUtil {

    private static final Logger log= LoggerFactory.getLogger(DateUtil.class);
    private static int DAY = 24 * 60 * 60 * 1000;
    private static int WEEK=DAY*7;
    private static final SimpleDateFormat formater_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdf_yyyy_MM=new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat sdf_yyyy=new SimpleDateFormat("yyyy");


    /**
     * 得到当前年
     */
    public static String getCurrentYear(){
        return sdf_yyyy.format(new Date());
    }
    /**
     * 获得某年的所有月份
     * @param year
     */
    public static List<String> getCurrentYearMonth(int year){
        List<String> listYm=new ArrayList<String>();
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR, year);
        cl.set(Calendar.MONTH, 0);
        for (int i =0 ;i<12;i++) {
            listYm.add(new SimpleDateFormat("yyyy-MM").format(cl.getTime()));
            cl.add(Calendar.MONTH, 1);
        }
		/*cl.set(Calendar.YEAR,year);
		cl.set(Calendar.MONTH,month-1);
		cl.add(Calendar.MONTH,month-1);
		listYm.add(sdf_yyyy_MM.format(cl.getTime()));*/
        return listYm;
    }
    /**
     * 得到最近n年
     */
    public static String[] getLastYears(int n){
        int year = Integer.valueOf(getCurrentYear());
        String[] years = new String[n];
        for (int i = 0; i < n; i++) {
            years[i] = String.valueOf(year);
            year--;
        }
        return years;
    }

    /**
     * 得到指定年最近n年
     */
    public static String[] getLastYears1(int n,String selectedYear){
        int year = Integer.valueOf(selectedYear);
        String[] years = new String[n];
        for (int i = 0; i < n; i++) {
            years[i] = String.valueOf(year);
            year--;
        }
        return years;
    }
    /**
     * 得到最近3年
     * @return
     */
    public static String[] getLastThreeYears(){
        String year=sdf_yyyy.format(new Date());
        int yearint=Integer.parseInt(year);
        String[] lastThreeYears=new String[]{yearint+"",(yearint-1)+"",(yearint-2)+""};
        return lastThreeYears;
    }
    /**
     * 根据年、月参数推算前5个月的年月数据
     * @param year
     * @param month
     * @return
     */
    public static List<String> getYm(int year,int month){
        List<String> listYm=new ArrayList<String>();
        try {
            for (int i = 5; i >= 0; i--) {
                Calendar cl = Calendar.getInstance();
                cl.set(Calendar.YEAR,year);
                cl.set(Calendar.MONTH,month-1);
                cl.set(Calendar.DAY_OF_MONTH,1);
                cl.add(Calendar.MONTH,-i);
                listYm.add(sdf_yyyy_MM.format(cl.getTime()));
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("",e);
        }
        return listYm;
    }

    /**
     * 根据年、月参数推算前11个月的年月数据
     * @param year
     * @param month
     * @return
     */
    public static List<String> getYm12(int year,int month){
        List<String> listYm=new ArrayList<String>();
        try {
            for (int i = 11; i >= 0; i--) {
                Calendar cl = Calendar.getInstance();
                cl.set(Calendar.YEAR,year);
                cl.set(Calendar.MONTH,month-1);
                cl.set(Calendar.DAY_OF_MONTH,1);
                cl.add(Calendar.MONTH,-i);
                listYm.add(sdf_yyyy_MM.format(cl.getTime()));
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("",e);
        }
        return listYm;
    }

    /**
     * 获取n个月之前的最近几个月的年月数据
     * @param n n个月前的
     * @param t 最近几个月
     * @return
     */
    public static List<String> getYearMonth(int n,int t){
        List<String> listYm=new ArrayList<String>();
        try{
            for (int i = t; i >=0; i--) {
                Calendar cl=Calendar.getInstance();
                cl.add(Calendar.MONTH, -(i+n));
                listYm.add(sdf_yyyy_MM.format(cl.getTime()));
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("",e);
        }
        return listYm;
    }

    /**
     * 获取当前年月格式为yyyy-MM
     * @return
     */
    public static String getCurrentYearMonth(){
        return sdf_yyyy_MM.format(new Date());
    }

    /**
     * 判断当前月份属于哪个季度
     * @param month
     * @return
     */
    public static int judgeSeasonByMonth(int month) {
        if(month==1 || month==2 || month==3){
            return 1;
        }
        else if(month==4 || month==5 || month==6){
            return 2;
        }
        else if(month==7 || month==8 || month==9){
            return 3;
        }
        else if(month==10 || month==11 || month==12){
            return 4;
        }
        return -1;
    }
    /**
     * 通过月份数字返回月份中文
     */
    public static String getMonthCN(int month){
        String str = "";
        if(month==0){
            str = "一月";
        }else if(month==1){
            str = "二月";
        }else if(month==2){
            str = "三月";
        }else if(month==3){
            str = "四月";
        }else if(month==4){
            str = "五月";
        }else if(month==5){
            str = "六月";
        }else if(month==6){
            str = "七月";
        }else if(month==7){
            str = "八月";
        }else if(month==8){
            str = "九月";
        }else if(month==9){
            str = "十月";
        }else if(month==10){
            str = "十一月";
        }else if(month==11){
            str = "十二月";
        }
        return str;
    }

    /**
     * 输入年月，返回前n个月(包括当月)的yyyy-MM格式字符集合
     * @param year
     * @param month
     * @param n
     */
    public static List<String> getYearMonthsStr(Integer year, Integer month, int n){
        if (!(year>1900) || !(-1<month && month<12) || !(n>-1)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        List<String> res = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        while (n>0) {
            cal.add(Calendar.MONTH, -1);
            res.add(sdf.format(cal.getTime()));
            --n;
        }
        return res;
    }

    /**
     * 输入年月，返回前第n个月的字符串
     * @param year
     * @param month
     * @param n
     */
    public static String getAgoDateStr(Integer year, Integer month, int n){
        if (!(year>1900) || !(-1<month && month<12) || !(n>-1)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, -n);
        String str = sdf.format(cal.getTime());
        return str;
    }

    /**
     * 计算两个日期之间相差几天
     * 例如：DateUtils.getDaySpan(Date 2008-9-11, Date  2008-9-15)=4
     * **/
    public static int getDaySpan(Date r_MinDate, Date r_MaxDate)
    {
        Calendar t_Calendar = Calendar.getInstance();

        long t_Span = r_MaxDate.getTime() - r_MinDate.getTime();
        t_Calendar.setTime(new Date(t_Span));

        return  Math.abs(Integer.parseInt(Long.toString(t_Span / DAY)));
    }
    /**
     * 计算两个日期之间相差几天(有负数)
     * 例如：DateUtils.getDaySpan(Date 2008-9-11, Date  2008-9-15)=4
     * **/
    public static int getDaySpanNoAbs(Date r_MinDate, Date r_MaxDate)
    {
        Calendar t_Calendar = Calendar.getInstance();

        long t_Span = r_MaxDate.getTime() - r_MinDate.getTime();
        t_Calendar.setTime(new Date(t_Span));

        return  (Integer.parseInt(Long.toString(t_Span / DAY)));
    }

    /**
     * 计算两个日期之间相差几个星期
     * 例如：DateUtils.getDaySpan(Date 2008-9-11, Date  2008-9-15)=0
     * DateUtils.getDaySpan(Date 2008-9-11, Date  2008-9-18)=1
     * **/
    public static int getWeekSpan(Date r_MinDate, Date r_MaxDate)
    {
        long t_Span = r_MaxDate.getTime() - r_MinDate.getTime();
        return  Math.abs(Integer.parseInt(Long.toString(t_Span / WEEK)));
    }
    /**
     * 将一个日期转换成yyyy-MM-dd形式的字符串
     **/
    public static String formatDate(Date r_Date)
    {
        if (null == r_Date)
        {
            return null;
        }
        else
        {
            DateFormat t_DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            return t_DateFormat.format(r_Date);
        }
    }
    /**
     * 将一个yyyy-MM-dd或yyyy-MM形式的字符串转换成日期

     **/
    public static Date parseDate(String r_Value)
    {
        if( r_Value == null ) {
            return null;
        }
        try
        {
            if( r_Value.lastIndexOf( '-' ) == 4 )
            {
                r_Value += "-01";
            }
            Date t_Date = DateFormat.getDateInstance(DateFormat.MEDIUM).parse(r_Value);
            return t_Date;
        }
        catch (Exception e)
        {

        }

        return null;
    }
    /**
     * 将一个制指定格式的字符串转化成日期

     **/
    public static Date parseDate(String r_Value,String r_format)
    {
        if( r_Value == null ) {
            return null;
        }
        try
        {
            if( r_Value.lastIndexOf( '-' ) == 4 )
            {
                r_Value += "-01";
            }
            SimpleDateFormat sd=new SimpleDateFormat(r_format);
            Date t_Date = sd.parse(r_Value);
            return t_Date;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    /**
     * 得到日期指定field的值

     **/
    public static int getValue(Date r_Date, int r_Field)
    {
        Calendar t_Calendar = Calendar.getInstance();
        t_Calendar.setTime(r_Date);
        return t_Calendar.get(r_Field);
    }
    /**
     * 得到当前年

     **/
    public static int getThisYear()
    {
        Calendar t_Calendar = Calendar.getInstance();
        return t_Calendar.get(Calendar.YEAR);
    }
    /**
     * 得到当前月

     **/
    public static int getMonth()
    {
        Calendar t_Calendar = Calendar.getInstance();

        return  t_Calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 得到当前日

     **/
    public static int getDay()
    {
        Calendar t_Calendar = Calendar.getInstance();

        return  t_Calendar.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 根据指定的格式，将日期转化成字符串

     **/
    public static String formatDate(Date r_Date, String r_Style)
    {
        if (null == r_Date)
        {
            return "";
        }

        SimpleDateFormat t_DateFormat = new SimpleDateFormat();
        t_DateFormat.applyPattern(r_Style);

        return t_DateFormat.format(r_Date);
    }
    /**
     * 将日期转化成带时间的字符串

     **/
    public static String formatDateTime(Date r_Date)
    {
        return formatDate( r_Date, "yyyy-MM-dd hh:mm:ss" );
    }
    /**
     * 将日期转化成只有年月的字符串
     **/
    public static String formatYearMonth(Date r_Date)
    {
        return formatDate( r_Date, "yyyy-MM" );
    }
    /**
     * 得到当前日期
     **/
    public static Date today()
    {
        Calendar t_Calendar = Calendar.getInstance();
        t_Calendar.setTime(new Date());

        t_Calendar.set(Calendar.HOUR_OF_DAY, 0);
        t_Calendar.set(Calendar.MINUTE, 0);
        t_Calendar.set(Calendar.SECOND, 0);
        t_Calendar.set(Calendar.MILLISECOND, 0);

        return t_Calendar.getTime();
    }
    /**
     * 将日期转化成当前年月日的字符串

     **/
    public static String getYMD()
    {
        return formatDate(today(),"yyyy-MM-dd");
    }
    /**
     * 得到当前月的一号的字符串

     **/
    public static String getFirstDayAndCurrMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return formatDate(cal.getTime(),"yyyy-MM-dd");
    }

    /*
     *  如add(int 1,int 2,Date 2009-10-1) 指在年上加2 结果是2011-10-1
     * */
    public static Date add(int field,int amount,Date date){
        if(date==null) {
            return date;
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return new Date(calendar.getTimeInMillis());
    }
    /*
     *  如add(int 1,int 2,Date 2009-10-1) 指在年上加2 结果是2011-10-1
     * */
    public static String add(int field,int amount,String dateStr){
        if(dateStr==null || dateStr.length()==0) {
            return dateStr;
        }
        Date date=add(field,amount,parseDate(dateStr));
        return formatDate(date, "yyyy-MM-dd");
    }
    /*
     *  得到年月日和星期 格式的当前时间

     *
     * */
    public static String getFullDate(boolean hasWeek)
    {
        Calendar t_Calendar = Calendar.getInstance();
        String fullDate = new SimpleDateFormat("yyyy-MM-dd").format(t_Calendar.getTime());
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        if(hasWeek)
        {
            fullDate += " " + weeks[t_Calendar.get(Calendar.DAY_OF_WEEK) - 1];;
        }
        return fullDate;
    }
    /*
     *  可以得到指定日期是星期几
     *
     * */
    public static String getWeekDate(Date date)
    {
        Calendar t_Calendar = Calendar.getInstance();
        t_Calendar.setTime(date);
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};

        return weeks[t_Calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }
    public static String getWeekDate(int year,int month,int day)
    {
        Calendar t_Calendar = Calendar.getInstance();
        t_Calendar.set(Calendar.YEAR,year);
        t_Calendar.set(Calendar.MONTH, month-1);
        t_Calendar.set(Calendar.DAY_OF_MONTH, day);


        return getWeekDate(t_Calendar.getTime());
    }

    public static String getWeekDate(String year,String month,String day)
    {
        int _year=new Integer(year).intValue();
        int _month=new Integer(month).intValue();
        int _day=new Integer(day).intValue();


        return getWeekDate(_year,_month,_day);
    }
    //判断指定的年月日是否是工作日
    public static boolean isWorkDay(int year,int month,int day)
    {
        boolean flag=true;
        Calendar t_Calendar = Calendar.getInstance();
        t_Calendar.set(Calendar.YEAR,year);
        t_Calendar.set(Calendar.MONTH, month-1);
        t_Calendar.set(Calendar.DAY_OF_MONTH, day);
        //  System.out.println(year+"-"+month+"-"+day+"-"+"-DAY_OF_WEEK--"+t_Calendar.get(Calendar.DAY_OF_WEEK));
        if(t_Calendar.get(Calendar.DAY_OF_WEEK)==1 || t_Calendar.get(Calendar.DAY_OF_WEEK)==7)
        {
            flag=false;
        }
        return flag;
    }
    //判断指定的年月日是否是工作日
    public static boolean isWorkDay(String year,String month,String day)
    {
        int _year=new Integer(year).intValue();
        int _month=new Integer(month).intValue();
        int _day=new Integer(day).intValue();
        return isWorkDay(_year,_month,_day);
    }
//    根据年月得到本月的总天数


    public static int getTotalDays(String year,String month){

        int year_=0;
        int month_=0;
        if(year==null || year.equals(""))
        {
            return 0;
        }else
        {
            try{
                year_=new Integer(year).intValue();
            }catch(Exception e)
            {
                //e.printStackTrace();
                //System.out.println("数字转化错误year：==="+year);
                return 0;
            }
        }
        if(month==null || month.equals(""))
        {
            return 0;
        }else
        {
            try{
                month_=new Integer(month).intValue();
            }catch(Exception e)
            {
                //e.printStackTrace();
                //System.out.println("数字转化错误month：==="+month);
                return 0;
            }
        }
        return getTotalDays(year_,month_);

    }
    /*根据年月得到本月的总天数*/
    public static int getTotalDays(int year,int month){
        int total_days=0;
        try{
            Calendar t_Calendar = Calendar.getInstance();
            t_Calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(year+"-"+month+"-01"));
            total_days=t_Calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        }catch(Exception e){
            //System.out.println("getTotalDays 日期转化错误：==="+month);
        }

        return total_days;
    }

    /*根据日期时间判断现在是上午还是下午
     * 0:表示上午0:00-11:59，

     * 1:表示下午12:00-23:59
     * 3:异常
     * */
    public static int getPmorAm(Date date){
        try{
            Calendar t_Calendar = Calendar.getInstance();
            t_Calendar.setTime(date);
            // System.out.println(date+" "+t_Calendar.get(Calendar.AM));
            return t_Calendar.get(Calendar.AM_PM);

        }catch(Exception e){
            //System.out.println("getTotalDays 日期转化错误：=date=="+date);

        }

        return 3;
    }

    /*根据日期字符串判断现在是上午还是下午
     * 0:表示上午0:00-11:59，

     * 1:表示下午12:00-23:59
     * 3:异常
     * */
    public static int getPmorAm(String date){

        Date t_date = parseDate(date, "yyyy-MM-dd HH:mm");
        return getPmorAm(t_date);

    }

    /**
     * 获取一个固定时间
     * @return
     */
    public static Date getOpRuleTaskDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse("2013-01-01 09:00");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前七天后的时间
     * @param
     * @return
     */
    public static Date getAfterSevenDaysTime(){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            c.add(Calendar.DATE, 7);
            date=sdf.parse(sf.format(c.getTime())+" 20:00:00");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return date;
    }
    /**
     * andalee 20170416  计算两个时间相差多少秒
     * 获取今天开始时间
     * @return
     */
    public static Long dateDiffSecond(String endtime,String begintime){
        Date beginTimeDate=str2Date(begintime, "yyyy-MM-dd HH:mm:ss");
        Date endTimeDate=str2Date(endtime, "yyyy-MM-dd HH:mm:ss");
        Calendar beginTimecalendar = Calendar.getInstance();
        beginTimecalendar.setTime(beginTimeDate);
        Calendar endTimecalendar = Calendar.getInstance();
        endTimecalendar.setTime(endTimeDate);
        Long seconds=(endTimecalendar.getTimeInMillis()-beginTimecalendar.getTimeInMillis())/1000;
        return seconds;
    }

    /**
     * 获取今天开始时间
     * @return
     */
    public static Date getTodayStartTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        return start;
    }


    /**
     * 获取本周第一天时间
     * @return
     */
    public static Date getFirstDayCurrWeek(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        return start;
    }

    /**
     * 获取指定时间当月的第一天
     * @return
     */
    public static Date getFirstDayCurrMonth(String nowdate){
        Date tempdate=null;
        try {
            tempdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(nowdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(tempdate);
        //本月第一天
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        return start;
    }

    /**
     * 获取指定时间当月的最后一天
     * @return
     */
    public static Date getLastDayCurrMonth(String nowdate){
        Date tempdate=null;
        try {
            tempdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(nowdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(tempdate);
        //本月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        Date start = calendar.getTime();
        /*
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();
        */
        return start;
    }

    /**
     * 获取今天剩余秒速
     * @return
     */
    public static Long getTodayRemainSeconds(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        //Date nextday = calendar.getTime();
        Long seconds=calendar.getTimeInMillis()-System.currentTimeMillis();
        return seconds/1000;
    }



    /**
     * Date->String
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToStr(Date date, String pattern) {
        SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
        if(date==null){
            return "";
        }
        return formater2.format(date);
    }

    /**
     * 获取当前时间并转换成String
     *
     * @param pattern
     * @return
     */
    public static String getNowDate(String pattern) {
        Date date = new Date();
        SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
        if(date==null){
            return "";
        }
        return formater2.format(date);
    }

    /**
     * 获取指定日期的前后日期 i为正数 向后推迟i天，负数时向前提前i天
     * @Project IBReport
     * @param specifiedDay  指定日期
     * @param Formatparameters 返回的时间格式
     * @param i
     * @return
     * @author hejin
     * @date 2017-11-2 下午03:58:52 
     * @version V 1.0
     */
    public static String getBeForeNDate(String specifiedDay,String Formatparameters,int i)
    {
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+i);

        String dayBefore=new SimpleDateFormat(Formatparameters).format(c.getTime());
        return dayBefore;
    }

    /**
     * 获取指定日期到前后日期 的所有日期；i为正数 向后推迟i天，负数时向前提前i天
     * @Project IBReport
     * @param specifiedDay  指定日期
     * @param Formatparameters 返回的时间格式
     * @param i
     * @return String[]
     * @author hejin
     * @date 2017-11-2 下午03:58:52 
     * @version V 1.0
     */
    public static String[] getBeForeNDateStr(String specifiedDay,String Formatparameters,int i)
    {
        int sum=Math.abs(i);
        String[] dateStr=new String[sum];
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        for (int j = 0; j < sum; j++) {
            c.set(Calendar.DATE,day+j);
            String dayBefore=new SimpleDateFormat(Formatparameters).format(c.getTime());
            dateStr[j]=dayBefore;
        }

        return dateStr;
    }

    /**
     * 获取当前时间的前一天并转换成String
     *
     * @param pattern
     * @return
     */
    public static String getNextDay(String pattern) {
        Date date = new Date();
        SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return formater2.format(date);
    }


    /**
     * convert string to date ,with custom pattern,throws
     * IllegalArgumentException
     *
     * @param str
     * @param format
     * @return
     */
    public static Date str2Date(String str, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formater.parse(str);
        } catch (ParseException e) {
            //
        }
        return date;
    }

    /**
     * convert string to date, with default pattern,throws ParseException
     *
     * @param str
     * @return
     */
    public static Date str2Date(String str) {
        Date date = null;
        try {
            date = formater_yyyy_MM_dd.parse(str);
        } catch (ParseException e) {
            //
        }
        return date;
    }

    /**
     * convert date to string,with custom pattern,throws
     * IllegalArgumentException
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        if(date!=null){
            return formater.format(date);
        }
        else{
            return "";
        }
    }

    /**
     *
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        if(date!=null){
            return formater_yyyy_MM_dd.format(date);
        }
        else{
            return "";
        }

    }

    /**
     * 由日期,小时,分,钟组合成一个 yyyy-MM-dd hh:mm:ss 格式的 Date object
     *
     * @param date
     * @param hours
     * @param minutes
     * @param seconds
     * @return
     */
    public static Date str2Date(String date, String hours, String minutes, String seconds) {
        String secondsNew = seconds;
        if (null == date || "".equals(date) || null == hours || "".equals(hours) || null == minutes
                || "".equals(minutes)) {
            return null;
        }
        if (null == secondsNew) {
            secondsNew = "00";
        }
        Date retDate = null;
        StringBuffer dateBuffer = new StringBuffer(date);
        dateBuffer.append(" ").append(hours).append(":").append(minutes).append(":").append(secondsNew);
        retDate = str2Date(dateBuffer.toString(), "yyyy-MM-dd HH:mm:ss");
        return retDate;
    }


    /**
     * String->Date
     *
     * @param str
     * @param pattern
     * @return
     */
    public static Date strToDate(String str, String pattern) {
        Date dateTemp = null;
        SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
        try {
            dateTemp = formater2.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTemp;
    }

    /**
     *
     * @author andalee 2017/3/31
     * @param dateStr
     * @return
     */
    public static Date getStartMonth(String dateStr) {
        return strToDate(dateStr, "yyyy-MM");
    }

    /**
     *
     * @author andalee 2017/3/31
     * @param dateStr
     * @return
     */
    public static Date getEndMonth(String dateStr) {
        Date d = strToDate(dateStr, "yyyy-MM");
        Calendar date = Calendar.getInstance();
        date.setTime(d);
        date.add(Calendar.MONTH, 1);
        return new Date(date.getTimeInMillis());
    }

    /**
     *
     * @author andalee 2017/3/31
     * @param dateStr
     * @return
     */
    public static String getBeforeMonth(String dateStr) {
        Date d = strToDate(dateStr, "yyyy-MM");
        Calendar date = Calendar.getInstance();
        date.setTime(d);
        date.add(Calendar.MONTH, -1);
        return dateToStr(new Date(date.getTimeInMillis()), "yyyy-MM");
    }


    public static String getStringDate(String formart) {
        SimpleDateFormat df = new SimpleDateFormat(formart);// 日期格式
        String dateFormart = df.format(new Date());
        return dateFormart;
    }


    /**
     *
     * @author andalee  2017/3/31
     * @param intYear
     * @param intMonth
     * @param intDay
     * @return
     */
    public static String getStandardStringDate(int intYear, int intMonth, int intDay) {
        String str = intToStandardString(intYear) + "-" + intToStandardString(intMonth) + "-"
                + intToStandardString(intDay);
        return str;
    }

    /**
     * input 2 output "02"
     *
     * @author andalee 2017/3/31
     * @param intNum
     * @return
     */
    public static String intToStandardString(int intNum) {
        if (intNum >= 0 && intNum < 10) {
            return "0" + intNum;
        } else {
            return "" + intNum;
        }
    }


    /**
     * 将星期的数字转换为汉语
     *
     * @author andalee
     * @param intWeek
     * @return
     */
    public static String convWeek(int intWeek) {
        String strWeek = null;
        switch (intWeek) {
            case 0:
                strWeek = "日";
                break;
            case 1:
                strWeek = "一";
                break;
            case 2:
                strWeek = "二";
                break;
            case 3:
                strWeek = "三";
                break;
            case 4:
                strWeek = "四";
                break;
            case 5:
                strWeek = "五";
                break;
            case 6:
                strWeek = "六";
                break;
            default:
                break;
        }

        return strWeek;
    }

    /**
     * @author andalee
     * @return Return a timestamp object
     */
    @SuppressWarnings("deprecation")
    public static Timestamp getATimeStamp() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.YEAR, 10);
        return new Timestamp(date.getTimeInMillis());
    }

    /**
     * @author andalee
     * @param startTime
     * @param duringTime
     * @return Return a Date object
     */
    public static Date getEndTime(Date startTime, Integer duringTime) {
        // duringTime :以分钟为单位
        Calendar date = Calendar.getInstance();
        date.setTime(startTime);
        date.add(Calendar.MINUTE, duringTime);
        return date.getTime();
    }


    public static Timestamp date2DateTime(Date date){
        Timestamp time = new Timestamp(date.getTime());
        return time;
    }

    public static long DateDiff(Date nowdate,Date inputdate) throws DataFormatException, ActivationException {
        long nowTime;
        long diffTime;
        long days = 0;
        try {
            nowTime = (nowdate.getTime() / 1000);
            diffTime = (inputdate.getTime() / 1000);
            if (nowTime > diffTime) {
                days = (nowTime - diffTime) / (1 * 60 * 60 * 24);
            } else {
                days = (diffTime - nowTime) / (1 * 60 * 60 * 24);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;

    }


    /*
     * 当天日期增加或减少几天
     */
    public static Date DateAdd(Date date,int datediff){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, datediff);
        Date times=calendar.getTime();
        return times;
    }

    /**
     * 2017/3/31
     * 注释：type：年份，月份，日期，  向前或者向后推几个单位
     * andalee
     */
    public static String dateAdd(String type,int diff){
        Calendar calendar=Calendar.getInstance();
        if("year".equalsIgnoreCase(type)){
            calendar.add(Calendar.YEAR, diff);
        }else if("month".equalsIgnoreCase(type)){
            calendar.add(Calendar.MONTH, diff);
        }else if("date".equalsIgnoreCase(type)){
            calendar.add(Calendar.DATE, diff);
        }
        Date date = calendar.getTime();
        return date2Str(date);
    }

    /**
     * @date 2017/3/31
     * 向前推一个月
     * andalee
     */
    public static String lastMonth() {
        Date date = new Date();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date)) - 1;
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));

        if (month == 0) {
            year -= 1;
            month = 12;
        } else if (day > 28) {
            if (month == 2) {
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                    day = 29;
                } else {
                    day = 28;
                }
            } else if ((month == 4 || month == 6 || month == 9 || month == 11)
                    && day == 31) {
                day = 30;
            }
        }
        String y = year + "";
        String m = "";
        String d = "";
        if (month < 10) {
            m = "0" + month;
        } else {
            m = month + "";
        }
        if (day < 10) {
            d = "0" + day;
        } else {
            d = day + "";
        }
        return y + "-" + m + "-" + d;
    }

    public static String getFormatDateTime(Date dateValue, String strFormat)
    {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        //format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//初始化当前时区解决去到的时间比当前时间少8小时
        return format.format(dateValue);
    }

    public static String getFormatDateTime(String strDateValue, String strFormat1, String strFormat2)
            throws ParseException
    {
        SimpleDateFormat sdfFormat = new SimpleDateFormat(strFormat1);
        Date dateValue = sdfFormat.parse(strDateValue);
        return getFormatDateTime(dateValue, strFormat2);
    }

    /**
     *  某一月份有多少工作日
     * @Project IBReport
     * @param year
     * @param month
     * @return
     * @author andalee
     * @date 2017-3-13 下午04:14:24 
     * @version V 1.0
     */
    public static List<Date> getDates(int year,int month){
        List<Date> dates = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH,  month - 1);
        cal.set(Calendar.DATE, 1);


        while(cal.get(Calendar.YEAR) == year &&
                cal.get(Calendar.MONTH) < month){
            int day = cal.get(Calendar.DAY_OF_WEEK);

            if(!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)){
                dates.add((Date)cal.getTime().clone());
            }
            cal.add(Calendar.DATE, 1);
        }
        return dates;

    }

    /*
     * 指定年月有多少工作日
     */
    public static int getDutyDays(String strStartDate,String strEndDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate=null;
        Date endDate = null;

        try {
            startDate=df.parse(strStartDate);
            endDate = df.parse(strEndDate);
        } catch (ParseException e) {
            System.out.println("非法的日期格式,无法进行转换");
            e.printStackTrace();
        }
        int result = 0;
        while (startDate.compareTo(endDate) <= 0) {
            if (startDate.getDay() != 6 && startDate.getDay() != 0) {
                result++;
            }
            startDate.setDate(startDate.getDate() + 1);
        }

        return result;
    }

    /**
     * 得到几天前的时间 
     * @param d
     * @param day
     * @return
     */
    public static String getDateBefore(Date d,int day,String pattern){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
        SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
        Date date = now.getTime();
        if(date==null){
            return "";
        }
        return formater2.format(date);
    }

    /**
     * 得到几天后的时间 
     * @param d
     * @param day
     * @return
     */
    public static String getDateAfter(Date d,int day,String pattern){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
        SimpleDateFormat formater2 = new SimpleDateFormat(pattern);
        Date date = now.getTime();
        if(date==null){
            return "";
        }
        return formater2.format(date);
    }

    /**
     * 判断日期区间的重复天数
     * @params	realDates	起始真实时间	yyyy-MM-dd
     * @params	realDatee	结束真实时间	yyyy-MM-dd
     * @params	testDates	起始测试时间	MM-dd
     * @params	testDatee	结束测试时间	MM-dd
     * @return 	重复天数,不重复天数
     */
    public static int[] calcRepeatDate(Date realDates, Date realDatee, Date testDates, Date testDatee){

        //检验
        if (!(realDates != null && realDatee != null && testDates != null && testDatee != null)) {
            return null;
        }
        //初始化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> realList = new ArrayList<>();	//真实时间区间段
        List<String> testList = new ArrayList<>();	//测试时间区间段
        //获得真实时间段
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(realDates);
            long d1 = realDates.getTime();
            long d2 = realDatee.getTime();
            long d = cal.getTimeInMillis();
            do {
                String str = sdf.format(new Date(d));
                realList.add(str);
                //天数加1
                cal.add(Calendar.DAY_OF_YEAR, 1);
                d = cal.getTimeInMillis();
            } while (d1 < d && d < d2);
			/*cal.add(Calendar.DAY_OF_YEAR, 1);
			d = cal.getTimeInMillis();
			String str = sdf.format(new Date(d));
			realList.add(str);*/
        }
        //获得测试区间段
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(testDates);
            long d1 = testDates.getTime();
            long d2 = testDatee.getTime();
            long d = cal.getTimeInMillis();
            do {
                String str = sdf.format(new Date(d));
                testList.add(str);
                //天数加1
                cal.add(Calendar.DAY_OF_YEAR, 1);
                d = cal.getTimeInMillis();
            } while (d1 < d && d < d2);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            d = cal.getTimeInMillis();
            String str = sdf.format(new Date(d));
            testList.add(str);
        }
        //判断重复和非重复天数
        int repeatNum = 0;	//重复天数
        int noRepeatNum = 0;//非重复天数
        if (realList.size() > 0) {
            for (String str: realList) {
                if (testList.contains(str)) {
                    repeatNum++;
                }else{
                    noRepeatNum++;
                }
            }
        }

        return new int[]{repeatNum,noRepeatNum};
    }

    public static void main(String[] args)
    {

        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-15");
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-26");
            Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-10");
            Date date4 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-24");
            int[] array = calcRepeatDate(date1,date2,date3,date4);
            System.out.println(array[0]);
            System.out.println(array[1]);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author 陈睿超
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if ((date.after(begin)) && (date.before(end))) {
            return true;
        } else {
            return false;
        }
    }
}
