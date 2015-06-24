package com.wonders.send.model.bo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TMsgUserMassage {
	private long sid;		//内部流水号
	private long empidrec;		//接受短消息的用户编号，可以为空（短消息发送方式为广播的时候）
	private long empidsend;		//发送短消息的用户编号
	private String title;		//短消息标题
	private String content;		//短消息内容
	private String senddate;	//to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')		发送时间
	private String seedate;		//查看时间
	private long sendmode;		//发送短消息的方式   INFOTYPE=9
	private long parentid;		//所回复的短消息编号
	private int state;		//短消息的状态  0--未读短消息  1--已读短消息
	private String sendstate;	//'1'发送者当前行的状态，0-已删除 1-正常
	private String recrstate;	//'1'接收者当前行的状态，0-已删除 1-正常
	private String senddelete;	//'1'发送者是否物理删除，0-已删除 1-正常
	private String recrdelete;	//'1'接收者是否物理删除，0-已删除 1-正常
	private int autoalert;		//0	是否自动提醒过  <3-未提醒 
	private int msgcount;		//0	统计短信数量
	
	private Long eventId;
	
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public TMsgUserMassage(){
		this.senddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
		this.sendmode = 187L;
		this.state = 0;
		this.sendstate = "1";
		this.recrstate = "1";
		this.senddelete = "1";
		this.recrdelete = "1";
		this.autoalert = 0;
		this.msgcount = 0;
	}
	
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	public long getEmpidrec() {
		return empidrec;
	}
	public void setEmpidrec(long empidrec) {
		this.empidrec = empidrec;
	}
	public long getEmpidsend() {
		return empidsend;
	}
	public void setEmpidsend(long empidsend) {
		this.empidsend = empidsend;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	public String getSeedate() {
		return seedate;
	}
	public void setSeedate(String seedate) {
		this.seedate = seedate;
	}
	public long getSendmode() {
		return sendmode;
	}
	public void setSendmode(long sendmode) {
		this.sendmode = sendmode;
	}
	public long getParentid() {
		return parentid;
	}
	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getSendstate() {
		return sendstate;
	}
	public void setSendstate(String sendstate) {
		this.sendstate = sendstate;
	}
	public String getRecrstate() {
		return recrstate;
	}
	public void setRecrstate(String recrstate) {
		this.recrstate = recrstate;
	}
	public String getSenddelete() {
		return senddelete;
	}
	public void setSenddelete(String senddelete) {
		this.senddelete = senddelete;
	}
	public String getRecrdelete() {
		return recrdelete;
	}
	public void setRecrdelete(String recrdelete) {
		this.recrdelete = recrdelete;
	}
	public int getAutoalert() {
		return autoalert;
	}
	public void setAutoalert(int autoalert) {
		this.autoalert = autoalert;
	}
	public int getMsgcount() {
		return msgcount;
	}
	public void setMsgcount(int msgcount) {
		this.msgcount = msgcount;
	}
	public static void main(String... args){
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(System.currentTimeMillis())));
	}
}
