package com.richard.novel.common.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils {
	private static final int HOUR_OF_DAY = 24;
	private static final int DAY_OF_YESTERDAY = 2;
	private static final int TIME_UNIT = 60;


	//将时间转换成日期
	public static String dateConvert(long time,String pattern){
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}


	public static boolean isEmpty(String str){
		return null == str || "".equals(str.trim());
	}

	public static boolean containsEmpty(String ... strings){
		for (String str : strings){
			if(isEmpty(str)){
				return true;
			}
		}
		return false;
	}

	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static String trim(Object o)
	{
		String str = "";
		if (null != o)
		{
			str = o.toString().trim();
		}
		return str;
	}
	public static String toGBK(String unicodeStr) {
		try {
			String gbkStr = new String(unicodeStr.getBytes("ISO8859-1"), "GBK");
			return gbkStr;
		} catch (UnsupportedEncodingException e) {
			return unicodeStr;
		}
	}

	public static String contains(String unicodeStr) {
		try {
			String gbkStr = new String(unicodeStr.getBytes("ISO8859-1"), "GBK");
			return gbkStr;
		} catch (UnsupportedEncodingException e) {
			return unicodeStr;
		}
	}

	public static String splitReverse(String splitChar, String... strs){
		String result = "";
		for(String str : strs){
			result+=str;
			result+=splitChar;
		}
		return result.substring(0, result.length()-splitChar.length());
	}


	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * 使用正则表达式提取中括号中的内容
	 * @param msg
	 * @return
	 */
	public static List<String> extractBracketsContent(String msg){

		List<String> list=new ArrayList<String>();
		Pattern p = Pattern.compile("(\\【[^\\]]*\\】)");
		Matcher m = p.matcher(msg);
		while(m.find()){
			list.add(m.group().substring(1, m.group().length()-1));
		}
		return list;
	}

	// 判断一个字符串是否含有数字
	public static boolean HasDigit(String content) {
		boolean flag = false;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches()) {
			flag = true;
		}
		return flag;
	}

	public static boolean containIllegalChar(String   str)   throws PatternSyntaxException {
		// 只允许字母和数字
		// String   regEx  =  "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regSpecialEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern   p   =   Pattern.compile(regSpecialEx);
		Matcher   m   =   p.matcher(str);
		if(m.find()){
			return true;
		}
		String regExpressionEx=".*\\p{So}.*";
		Pattern   expressionPattern   =   Pattern.compile(regExpressionEx);
		Matcher   expressionMatcher   =   expressionPattern.matcher(str);
		if(expressionMatcher.find()){
			return true;
		}
		return false;
	}

	/**
	 * @param targetStr 要处理的字符串
	 * @description 切割字符串，将文本和img标签碎片化，如"ab<img>cd"转换为"ab"、"<img>"、"cd"
	 */
	public static List<String> cutStringByImgTag(String targetStr) {
		List<String> splitTextList = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<img.*?src=\\\"(.*?)\\\".*?>");
		Matcher matcher = pattern.matcher(targetStr);
		int lastIndex = 0;
		while (matcher.find()) {
			if (matcher.start() > lastIndex) {
				splitTextList.add(targetStr.substring(lastIndex, matcher.start()));
			}
			splitTextList.add(targetStr.substring(matcher.start(), matcher.end()));
			lastIndex = matcher.end();
		}
		if (lastIndex != targetStr.length()) {
			splitTextList.add(targetStr.substring(lastIndex, targetStr.length()));
		}
		return splitTextList;
	}

	/**
	 * 获取img标签中的src值
	 * @param content
	 * @return
	 */
	public static String getImgSrc(String content){
		String str_src = null;
		//目前img标签标示有3种表达式
		//<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
		//开始匹配content中的<img />标签
		Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
		Matcher m_img = p_img.matcher(content);
		boolean result_img = m_img.find();
		Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
		if (result_img) {
			while (result_img) {
				//获取到匹配的<img />标签中的内容
				String str_img = m_img.group(2);

				//开始匹配<img />标签中的src
				Matcher m_src = p_src.matcher(str_img);
				if (m_src.find()) {
					str_src = m_src.group(3);
				}
				//结束匹配<img />标签中的src

				//匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
				result_img = m_img.find();
			}
		}
		return str_src;
	}

	/**
	 * 获取img标签中的src值
	 * @param content
	 * @return
	 */
	public static Map<Integer,Object> getAllImgSrc(String content) {
		List<String> srcs = new ArrayList<>();
		List<String> imes = new ArrayList<>();
		//目前img标签标示有3种表达式
		//<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
		//开始匹配content中的<img />标签
		Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
		Matcher m_img = p_img.matcher(content);
		boolean result_img = m_img.find();
		Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
		if (result_img) {
			while (result_img) {
				//获取到匹配的<img />标签中的内容
				String str_img = m_img.group(0);
				if(!isEmpty(str_img)){
					imes.add(str_img);
				}
				//开始匹配<img />标签中的src
				Matcher m_src = p_src.matcher(str_img);
				if (m_src.find()) {
					String str_src = m_src.group(3);
					if (!isEmpty(str_src)) {
						srcs.add(str_src);
					}
				}
				//结束匹配<img />标签中的src
				//匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
				result_img = m_img.find();
			}
		}
		Map<Integer,Object> result = new HashMap<>();
		result.put(0, imes);
		result.put(1, srcs);
		return result;
	}

	/**
	 * 关键字高亮显示
	 * @param target  需要高亮的关键字
	 * @param text	     需要显示的文字
	 * @return spannable 处理完后的结果，记得不要toString()，否则没有效果
	 * SpannableStringBuilder textString = TextUtilTools.highlight(item.getItemName(), KnowledgeActivity.searchKey);
	 * vHolder.tv_itemName_search.setText(textString);
	 */
	public static SpannableStringBuilder highlight(String text, String target) {
		SpannableStringBuilder spannable = new SpannableStringBuilder(text);
		CharacterStyle span = null;

		Pattern p = Pattern.compile(target);
		Matcher m = p.matcher(text);
		while (m.find()) {
			span = new ForegroundColorSpan(Color.parseColor("#EE5C42"));// 需要重复！
			spannable.setSpan(span, m.start(), m.end(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return spannable;
	}

	public static String convertCC(String paragraph, Context mContext) {
		//TODO 繁简转换
		return paragraph;
	}

	/**
	 * 将文本中的半角字符，转换成全角字符
	 * @param input
	 * @return
	 */
	public static String halfToFull(String input)
	{
		char[] c = input.toCharArray();
		for (int i = 0; i< c.length; i++)
		{
			if (c[i] == 32) //半角空格
			{
				c[i] = (char) 12288;
				continue;
			}
			//根据实际情况，过滤不需要转换的符号
			//if (c[i] == 46) //半角点号，不转换
			// continue;

			if (c[i]> 32 && c[i]< 127)    //其他符号都转换为全角
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	//功能：字符串全角转换为半角
	public static String fullToHalf(String input)
	{
		char[] c = input.toCharArray();
		for (int i = 0; i< c.length; i++)
		{
			if (c[i] == 12288) //全角空格
			{
				c[i] = (char) 32;
				continue;
			}

			if (c[i]> 65280&& c[i]< 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String parseMoney(Integer price) {

		float fp = new BigDecimal(price).movePointLeft(2).floatValue();
		DecimalFormat df = new DecimalFormat("##0.00");
		return df.format(fp);
	}
}
