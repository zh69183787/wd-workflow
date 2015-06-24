<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.wonders.util.*"%>
<%@ page import="com.wonders.receive.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.receive.workflow.process.recv.constants.RecvMainStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.simulate.constants.SimulateSubStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.finish.constants.FinishSubStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.instruct.constants.InstructSubStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.sign.constants.SignSubStepConstants" %>
<%@ page import="com.wonders.receive.workflow.process.dept.constants.DeptSubStepConstants" %>



<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>打印</title>
	<!--<link href="<%=path%>/receive/css/formalize.css" rel="stylesheet">
	<link href="<%=path%>/receive/css/page.css" rel="stylesheet">
	<link href="<%=path%>/receive/css/default/imgs.css" rel="stylesheet">
	<link href="<%=path%>/receive/css/reset.css" rel="stylesheet">-->
	<link href="<%=path%>/receive/css/jquery.ui.css" rel="stylesheet">
	<link type="text/css" href="<%=path%>/receive/css/flick/jquery-ui-1.10.3.custom.css" rel="stylesheet"/>
	<!--[if IE 6.0]>
	<script src="js/iepng.js" type="text/javascript"></script>
	<script type="text/javascript">
	     EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
	</script>
	<![endif]-->
	<style>
/* CSS Version Default */
/* Version: 1.4.0  || Date: 2012-10-18 || Author: Cathy */
/* Cascading Style Sheets Level 2 Revision 1 */


/* CSS Document */ /*Header*/
header,.header {
	height: 77px;
	-moz-border-radius-topleft: 5px;
	-webkit-border-top-left-radius: 5px;
	-moz-border-radius-topright: 5px;
	-webkit-border-top-right-radius: 5px;
}

header .logo {
	height: 42px;
	font-size: 12px;
}

header .logo div,header .logo ul {
	float: right;
}

header .logo ul {
	padding-top: 1px;
	margin-left: 15px;
}

header .logo li {
	width: 50px;
	height: 41px;
	display: inline-block;
	float: left;
	line-height: 42px;
}

header .logo li a, header.Limis ul li a {
	text-indent: -9999px;
	display: block;
	height: 41px;
	background-repeat: no-repeat;
}

header .logo li a.home, header.Limis ul li a.home{
	background-position: 0px 0px;
}

header .logo li.selected a.home, header.Limis ul li.selected a.home{
	background-position: 0px -41px;
}

header .logo li a.help, header.Limis ul li a.help{
	background-position: -51px 0px;
}

header .logo li.selected a.help, header.Limis ul li.selected a.help{
	background-position: -51px -41px;
}

header .logo li a.download, header.Limis ul li a.download{
	background-position: -102px 0px;
}

header .logo li.selected a.download, header.Limis ul li.selected a.download{
	background-position: -102px -41px;
}

header .logo li a.mail, header.Limis ul li a.mail{
	background-position: -153px 0px;
}

header .logo li.selected a.mail, header.Limis ul li.selected a.mail{
	background-position: -153px -41px;
}

header .logo li a.logout, header.Limis ul li a.logout{
	background-position: -204px 0px;
}

header .logo li.selected a.logout, header.Limis ul li.selected a.logout{
	background-position: -204px -41px;
}

header .logo li a.sys, header.Limis ul li a.sys{
	background-position: -255px 0px;
}

header .logo li.selected a.sys, header.Limis ul li.selected a.sys{
	background-position: -255px -41px;
}


header .user{
/*	line-height: 42px;
*/
	padding-top:12px;
}

header .user select{
	border:none;
}

header .user b,header .user span,header .user a {
	float: left;
	line-height:24px;
}

header .user a {
	margin-left: 5px;
}

header .user a i {
	width: 0;
	height: 42px;
	padding-left: 16px;
	float: right;
	margin-left: 5px;
}

header .color {
	width: 31px;
	height: 42px;
}

/*nav_1*/
header nav {
	height: 33px;
	padding-left: 18px;
	line-height: 30px;
	font-size: 13px;
	font-weight: bold;
}

header nav ul {
	position: relative;
	padding-top: 4px;
}

header nav ul li {
	display: inline;
	float: left;
	padding-left: 6px;
	margin-right: 2px;
}

header nav ul li a,header nav ul li a span {
	height: 30px;
	line-height: 30px;
}

header nav ul li a {
	padding-right: 6px;
}

header nav ul li a span {
	padding: 0 6px;
}

header nav ul li:hover,header nav ul li.selected {
	background-position: left top;
	background-repeat: no-repeat;
}

header nav ul li:hover a,header nav ul li.selected a {
	background-position: right -60px;
	background-repeat: no-repeat;
}

header nav ul li:hover a span,header nav ul li.selected a span {
	background-position: left -30px;
	background-repeat: repeat-x;
}

/*Header End*/ /*Left*/
.gen_search {
	width: 205px;
	margin-right: 3px;
	position: relative;
	margin-bottom: 2px;
	margin-left: 1px;
}

.gen_search .l_t,.gen_search .r_t,.gen_search .l_b,.gen_search .r_b {
	background-repeat: no-repeat;
	position: absolute;
	_overflow: hidden;
}

.gen_search .l_t,.gen_search .r_t {
	width: 5px;
	height: 4px;
}

.gen_search .l_t {
	top: 0;
	left: 0;
	background-position: left top;
}

.gen_search .r_t {
	top: 0;
	right: 0;
	background-position: right top;
}

.gen_search .l_b,.gen_search .r_b {
	width: 5px;
	height: 5px;
}

.gen_search .l_b {
	bottom: 0;
	left: 0;
	background-position: left bottom;
}

.gen_search .r_b {
	bottom: 0;
	right: 0;
	background-position: right bottom;
}

.gen_search .s_t,.gen_search .s_b {
	background-repeat: repeat-x;
	margin-left: 5px;
	margin-right: 5px;
	_overflow: hidden;
}

.gen_search .s_l,.gen_search .s_r {
	background-repeat: repeat-y;
}

.gen_search .s_r {
	background-position: right top;
	margin-left: 5px;
	padding-right: 5px;
}

.gen_search .s_l {
	background-position: left top;
}

.gen_search .s_t {
	background-position: left top;
	height: 4px;
}

.gen_search .s_b {
	background-position: left bottom;
	height: 5px;
}

.gen_search .s_r .con {
	padding: 5px 8px;
}

.sidebar {
	width: 195px;
	padding: 1px;
	margin-bottom: 5px;
}

.sidebar,.support {
	margin-left: 4px;
}

.sidebar ul li h4 {
	line-height: 35px;
	font-weight: bold;
	display: block;
	height: 35px;
}

.sidebar ul li a {
	padding-left: 26px;
}

.sidebar .menuOne ul {
	padding: 1px 0;
}

.sidebar .menuOne ul li {
	line-height: 30px;
	font-weight: bold;
	font-size: 13px;
}

.sidebar .menuOne ul li span {
	height: 30px;
	width: 211px;
}

.sidebar .menuOne ul li a {
	display: block;
	background-repeat: no-repeat;
	background-position: 2px 5px;
	padding-left: 36px;
}

.sidebar .menuOne ul ul {
/*	position: relative;
*/}

.sidebar .menuOne ul ul i {
	width: 20px;
	height: 12px;
/*	position: absolute;
*/	top: 0;
	left: 25px;
	z-index: 99;
}

.sidebar .menuOne ul ul li {
	font-weight: normal;
	font-size: 12px;
	height: 30px;
	margin-bottom: 1px;
}

.sidebar .menuOne ul ul li a {
	display: block;
	padding-left: 51px;
	overflow: hidden;
}
.sidebar .menuOne ul li ul li.selected a{
	font-weight:bold;
}

/*Left End*/ /*Right*/
.main {
	margin:0 2px 2px 0px;
	min-width:800px;
}

.ctrl {
	height:35px;
	font-size: 12px;
	position:fixed;
	z-index:999;
	width:100%;
}

.ctrl .posi {
	line-height: 30px;
	margin-left: 12px;
	display:inline-block;
}
.ctrl .posi, .ctrl .lit_nav, .ctrl .posi ul, .ctrl .posi ul li, .ctrl .lit_nav ul, .ctrl .lit_nav li{
	display:inline-block;
	white-space:nowrap;
}

.ctrl .posi ul li {
	float: left;
	padding: 0 16px 0 4px;
}

.ctrl .posi ul li.fin {
	background: none;
}

.ctrl .lit_nav li {
	float: left;
	margin: 6px 6px 0px 6px;
	width: 22px;
	height: 21px;
}

.ctrl .lit_nav li a {
	display: block;
	overflow: hidden;
	text-indent: -999px;
	background-repeat: no-repeat;
}

.ctrl .lit_nav li a.print, .panel_6 li a.print{
	background-position: 0px -438px;
}

.ctrl .lit_nav li a.storage {
	background-position: 0px -478px;
}

.ctrl .lit_nav li a.chat {
	background-position: 0px -118px;
}

.ctrl .lit_nav li a.rss {
	background-position: 0px -518px;
}

.ctrl .lit_nav li a.query {
	background-position: 0px -558px;
}
.ctrl .lit_nav li a.express {
	background-position: 0px -1158px;
}

.ctrl .lit_nav li a.table {
	background-position: 0px -358px;
}

.ctrl .lit_nav li a.treeOpen {
	background-position: 0px -1198px;
}

.ctrl .lit_nav li a.filterClose {
	background-position: 0px -1114px;
}

.ctrl .lit_nav li a.filterOpen {
	background-position: 0px -1074px;
}


/*Right_Aside*/
aside {
	width: 25%;
	+width: 24.9%;
	float: left;
	display: inline;
}

aside .panel_1,aside .panel_2 {
	padding: 1px;
	margin-bottom: 10px;
	margin-right: 10px;
}

aside hgroup {
	height: 58px;
	position: relative;
	padding-left: 70px;
}
aside hgroup.weather{
	background-position:6px -106px;
}
aside hgroup.calendar{
	background-position:6px -3px;
}
aside hgroup.stats_2{
	background-position:6px -54px;
}

aside hgroup h3 {
	line-height: 35px;
	font-weight: 110;
}
.panel_1 h1{
	font-weight: normal;
}
aside hgroup h5 {
	padding-top: 16px;
	font-weight: normal;
}

aside hgroup h6, .view h6{
	font-weight: normal;
	line-height: 15px;
}

aside ul li a {
	font-size: 10.5pt;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	-o-text-overflow: ellipsis;
}

aside ul.topic {
	width: 88%;
	margin: 8px auto 15px auto;
}

aside ul.topic li {
	margin-bottom: 1px;
	line-height: 31px;
	overflow: hidden;
}

aside ul.topic li a, aside ul.qa li dt {
	padding-left: 25px;
}

aside ul.list, aside ul.qa, aside .con, aside ul.stats_2{
	padding: 8px 6px;
}
aside ul.qa li dd{
	font-size:12px;
	padding-left:37px;
}
aside ul.list li{
	line-height: 30px;
	overflow: hidden;
	padding: 0 5px 0 15px;
}
aside ul.list_2 li{
	line-height: 30px;
	margin-right:5px;
	overflow:visible;
}
aside ul.list_2 li a{
	padding-left:14px;
}
aside ul.list_2 i, aside hgroup b{
	display:inline;
}
aside ul.list li a.w{
	width:60%;
	margin-left:4px;
}

aside ul.qa li {
	line-height: 30px;
	overflow: hidden;
	padding: 0 5px;
}
aside ul.xx li{
	width:64px;
	padding:0 3px;
	height:80px;
	float:left;
	display:inline-block;
	text-align:center;
	font-size:11px;
	line-height:16px;
}
aside ul.xx li dt{
	height:64px;
	background-repeat:no-repeat;
}
aside ul.xx dt.d1{
	background-position:0 0;
}
aside ul.xx dt.d2{
	background-position:-64px 0;
}
aside ul.xx dt.d3{
	background-position:-128px 0;
}
aside ul.xx dt.d4{
	background-position:0 -64px;
}
aside ul.xx dt.d5{
	background-position:-64px -64px;
}
aside ul.xx dt.d6{
	background-position:-128px -64px;
}
aside ul.xx dt.d7{
	background-position:0 -128px;
}
aside ul.xx dt.d8{
	background-position:-64px -128px;
}
aside ul.xx dt.d9{
	background-position:-128px -128px;
}
aside ul.xx dt.d10{
	background-position:0 -192px;
}
aside a.more_1 {
	width: 55px;
	height: 18px;
	position: absolute;
	top: 0px;
	right: 4px;
	text-align: center;
	line-height: 14px;
	display: block;
	color: #fff;
}
aside ul.item li{
	height:48px;
	float:left;
	padding: 1px;
	margin-right: 10px;
}
aside ul.stats_2 li{
	filter: alpha(opacity = 70);
	-moz-opacity: 0.70;
	opacity: 0.70;
	background-repeat:no-repeat;
	padding-left:20px;
	margin-bottom:1px;
	font-size:14px;
}
aside ul.stats_2 li h1, aside ul.stats_2 li h6{
	display:inline;
}
aside ul.stats_2 li.green{
	background-position: 5px -900px;
}
aside ul.stats_2 li.yellow{
	background-position: 5px -940px;
}
aside ul.stats_2 li.red{
	background-position: 5px -980px;
}
aside ul.stats_2 li.blue{
	background-position: 5px -1020px;
}
/*aside ul.item li .bg{
	height:48px;
	background-position:left top;
	background-repeat:repeat-x;
}
aside ul.item li h3, aside ul.item li h5, aside ul.item li a.I1, aside ul.item li a.I2, aside ul.item li a.I3, aside ul.item li a.I4, aside ul.item li a.I5, aside ul.item li a.I6{
	background-repeat:no-repeat;
}
aside ul.item li h3, aside ul.item li h5{
	background-position:right -50px;
	text-align:center;
	line-height:47px;
	padding:0 13px 0 22px;
	text-shadow:0 1px 0 #fff ;
	min-width:72px;
}
aside ul.item li a.I1{
	background-position:left -100px;
}
aside ul.item li a.I2{
	background-position:left -150px;
}
aside ul.item li a.I3{
	background-position:left -200px;
}
aside ul.item li a.I4{
	background-position:left -250px;
}
aside ul.item li a.I5{
	background-position:left -300px;
}
aside ul.item li a.I6{
	background-position:left -350px;
}
*/
aside .con p{
	line-height: 24px;
	text-indent:24px;
	height:162px;
	overflow:hidden;
	padding:0 8px 8px 8px;
	font-size:14px;
}
.hy_button{
	height:25px;
	font-size:14px;
	line-height:24px;
	padding-left:12px;
	background-repeat:repeat-x;
	background-position:left -400px;
	font-weight:bold;
}
.hy_button a{
	background-repeat:no-repeat;
}
.hy_button a.pllr{
	background-position:right -320px;
}
.hy_button a.htzl{
	background-position:right -280px;
}
.hy_button a.wsht{
	background-position:right -360px;
}
.hy_button a.dtxx{
	background-position:right -240px;
}


/*Right_Aside End*/
.right_main {
	float: left;
	width: 75%;
	/*	margin-right:311px;
*/
}
.right_main .news,.right_main .panel_3,.right_main .panel_4 {
	margin-right: 10px;
}

/*Right_News*/
.news{
	margin-bottom: 10px;
	margin-left: 5px;
}
.news_l{
	height: 276px;
	padding: 12px 11px 0 12px;
}

.news .focusPic {
	width: 355px;
	margin-right: 16px;
	height: 264px;
	overflow: hidden;
	position: relative;
}

.news .focusPic .fp_list {
	height: 239px;
	overflow: hidden;
	position: relative;
}

.news .focusPic .fp_list li {
	display: inline;
}

.news .focusPic .fp_list img, .news .focusPic img{
	height: 237px;
	width: 353px;
	overflow: hidden;
	cursor: pointer;
}

.news .focusPic .word {
	position: absolute;
	bottom: 24px;
	left: 1px;
	height: 28px;
	width: 343px;
	line-height: 28px;
	padding-left: 10px;
	z-index: 100;
	filter: alpha(opacity = 80);
	-moz-opacity: 0.80;
	opacity: 0.80;
	overflow: hidden;
}

.news .focusPic .word li {
	text-align: left;
	width: 375px;
}

.news .focusPic .word li {
	font-weight: bold;
	font-size: 14px;
}

.news .focusPic .scrollnav {
	z-index: 999;
	position: absolute;
	left: 110px;
	top: 246px;
}

.news .focusPic .scrollnav li {
	float: left;
	margin: 0 3px;
	cursor: pointer;
	width: 0px;
	height: 0px;
	overflow: hidden;
	padding-left: 12px;
	padding-top: 11px;
	background-repeat: no-repeat;
}

.news hgroup h2,.news hgroup h6 {
	font-weight: normal;
	line-height: 22px;
	padding-right: 16px;
}

.news hgroup h2 {
	font-style: italic;
}

.news hgroup p {
	height: 20px;
	line-height: 20px;
	margin: 1px 0;
	padding-right: 10px;
}

a.more_2 {
	padding-right: 15px;
}

.news section,.news .txt_list {
	line-height: 24px;
	padding-right: 24px;
}

.news section h5,.news .txt_list li a,.panel_3 .con li a {
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	-o-text-overflow: ellipsis;
}

.news section p {
	font-size: 14px;
	height: 48px;
	overflow: hidden;
	text-indent: 28px;
}
.news .txt_list li {
	padding-left: 10px;
	height: 24px;
}
.news .txt_list li a {
	display: block;
	margin-right: 12px;
	width: 80%;
	float: left;
}
.news .txt_list li span {
	font-size: 11px;
	width: 15%;
	float: right;
	white-space: nowrap;
	overflow: hidden;
	text-align: right;
}
/*Right_News End*/

/*Panel_3*/
.panel_3 {
	padding: 1px;
	margin: 0 10px;
}

.panel_3 header {
	height: 36px;
	border-bottom: #5999c1 1px solid;
}

.panel_3 header h5,.panel_4 header h5 {
	padding-left: 43px;
	line-height: 37px;
}
h5.meeting, h5.wlist1, h5.wlist2, h5.richeng, h5.announce{
	background-repeat:no-repeat;
}
h5.meeting{
	background-position:left top;
}
h5.wlist1{
	background-position:left -200px;
}
h5.wlist2{
	background-position:left -100px;;
}
h5.richeng{
	background-position:left -50px;
}
h5.announce{
	background-position:left -150px;
}

.panel_3 .con {
	overflow: hidden;
	padding: 10px 10px 4px 0px;
	min-height: 96px;
}


.panel_3 .con li{
	padding-left: 12px;
	line-height: 24px;
	width: 45%; /*20120207*/
	float: left;
	margin-left: 10px;
	display: inline;
}
.panel_3 .con ul.wd{
	height:160px;
	overflow:hidden;
}

.panel_3 .con ul.full li{
	width: 95%; /*20120618*/
}

.panel_3 .con ul.wd li{
	padding-left: 12px;
	line-height: 18px;
	width: 95%; /*20120618*/
	float: left;
	margin-left: 10px;
	margin-bottom:2px;
	max-height:36px;
	min-height:24px;
	
}
.panel_3 .con ul.wd li a {
	width:100%;
	word-wrap:break-word;
	white-space:normal;
	outline: none;
}
.panel_3 .up a, .panel_3 .down a{
	padding-left:16px;
	background-repeat:no-repeat;
	margin-right:5px;
}
.panel_3 .up a{
	background-position:left -754px;
}
.panel_3 .down a{
	background-position:left -794px;
}




.panel_3 .con li a {
	width: 80%;
	float: left;
}

.panel_3 .con li span, .News_list .list dt span{
	font-size:11px;
	float:right;
}
.panel_3 .con li span{
	width:40px;
}
#dtb tr.tit td{
	font-weight:bold;
}
#dtb{
	line-height: 24px;
}
#dtb a{
	padding-left: 12px;
}
#dtb tr.jgtit td{
	font-weight:bold;
	text-align:center;
}
#dtb tr.jgtit td{
	padding:0 3px;
}

a.more_3 {
	height: 18px;
	width: 40px;
	padding-left: 9px;
	line-height: 18px;
	margin-right: 9px;
}

/*Panel_3 End*/
/*Right End*/
/*Footer*/
footer {
	padding: 2px 3px;
	padding-bottom: 0;
	height: 32px;
	border: 1px solid #5a96e1;
	border-top: none;
	/*color: #3e3e3e;*/
	font-size: 12px;
	overflow:hidden;
	line-height: 30px;
}
footer div{
	padding:0 8px;
}
footer .counter{
	font-size:11px;
}
footer span{
	display:inline;
	font-weight:bold;
}

/*footer .con, footer .tel{
	display:inline;
}
*/
footer .tel {
	padding-left: 30px;
	margin-right: 20px;
	margin-top:0;
	position:absolute;
	top:1px;
	right:0;
	height:30px;
}
footer .tel b{
	display:inline;
}

/*Footer End*/

/*Tabs*/
/*Tabs_1*/

.tabs_1 {
	/*position: relative;*/
	margin-top: 10px;
	height: 27px;
	margin-right: 10px;
	line-height: 27px;
	/*+max-width:550px;
	+overflow:hidden;*/
}

.tabs_1 ul li,.tabs_2 ul li { /*	margin:0 1px;
*/
	padding-left: 25px;
	display:inline-block;
	float: left;
	font-size: 14px;
}

.tabs_1 .pre,.tabs_1 .next{
	width: 27px;
	padding: 0;
	text-align: center;
	background-repeat:no-repeat;
	text-indent:-9999px;
}
.tabs_1 .pre a,.tabs_1 .next a{
	width: 0px;
	padding-left: 27px;
	overflow: hidden;
	text-indent: -999px;
}

/*.tabs_1 ul li.pre a,.tabs_1 ul li.next a,.tabs_1 ul li.pre i,.tabs_1 ul li.next i
	{
	width: 0px;
	padding-left: 27px;
	overflow: hidden;
	text-indent: -999px;
}
*/
.tabs_1 ul li a {
	display: block;
	padding-right: 25px;
}

.tabs_1 ul li a span {
	height: 27px;
}

/*Tabs_1 End*/ /*Tabs_2*/
.tabs_2 {
	height: 25px;
	padding-left: 5px;
}

.tabs_2 ul {
	position: relative;
	bottom: -1px;
	white-space:nowrap;
}

.tabs_2 ul li {
	margin: 0 1px;
	line-height: 25px;
	display:inline-block;
}

.tabs_2 ul li a {
	display: block;
	padding-right: 25px;
}

.tabs_2 ul li a span {
	height: 25px;
	font-weight: bold;
}

/*Tabs_2 End*/ 
/*Tabs_3*/
.tabs_3{
	line-height:28px;
	padding-top:1px;
}
.tabs_3 ul, .tabs_3 li{
	background-repeat:repeat-x;
}
.tabs_3 ul{
	background-position:left -560px;
	height:28px;
}
.tabs_3 li{
	display:inline-block;
	float: left;
	font-size: 14px;
	padding-left:8px;
	background-position:left -520px;
	height:27px;
}
.tabs_3 li a{
	padding-right:30px;
	background-repeat:no-repeat;
	background-position:right -640px;
}
.tabs_3 li.selected, .tabs_3 li:hover{
	background-position:left -480px;
	height:28px;
}
.tabs_3 li:hover a, .tabs_3 li.selected a{
	background-position:right -600px;
}

/*Tabs_3 End*/ 
/*Tabs*/
 /*Column*/ /*.columns{
	-webkit-column-count:2; 
	-webkit-column-gap:normal;
	-webkit-column-fill:balance;
	-moz-column-count:2; 
	-moz-column-gap:normal;
	column-count:2;
	column-gap:normal;
	height:178px;
	overflow:hidden;
}
.columns li{
}*/ /*Column End*/ 
/*Filter*/
.filter {
	padding-bottom: 1px;
	font-size: 14px;
}
.filter_sandglass{
	background-position:right top;
}
.filter_search{
	background-position:right -320px;
}
.filter_tips{
	background-position:right -640px;
}



.filter .query td {
	padding: 3px 5px;
}

.filter .fn {
	height: 25px;
	padding: 5px 12px;
}
.filter .fn h5 a.colSelect{
	background-repeat:no-repeat;
	background-position:right -1236px;
	padding-right:22px; 
	font-weight: bold;
	font-size: 14px;
	line-height: 25px;
}

/*Filter End*/
/*Table*/
.table_1 .tfoot td {
	line-height: 25px;
	padding: 0 8px;
	height: 25px;
}

.table_1 td, .table_2 td{
	padding: 3px 5px;
}

/*.table_1 thead th{
	font-weight: bold;
	font-size: 14px;
	line-height: 25px;
	height: 25px;
}
.table_1 th a.colSelect{
	background-repeat:no-repeat;
	background-position:right -1236px;
	padding-right:22px;
}
*/
.table_1 .tit td {
	font-weight: bold;
	padding:0;
	white-space: nowrap;
	overflow:hidden;
}
.table_1 .tit td.sort{
	padding: 0 5px;
}
.table_1 .tit td.sort a i{
	width:10px;
	height:7px;
	float:right;
	background-position:left -680px;
	background-repeat: no-repeat;
	margin:6px 0;
}
.table_1 .tit td.sort a:hover i{
	background-position:left -687px;
}
/*.table_1 .item {
	width: 0;
	height: 0;
	padding-left: 16px;
	padding-top: 16px;
	overflow: hidden;
	margin: 4px auto;
}
*/
.table_1 .pager li {
	float: right;
	margin: 3px 2px;
	height: 18px;
	line-height: 18px;
}

.table_1 .pager li a {
	display: block;
	padding: 0 6px;
	font-weight: bold;
}

.table_1 .tit ul {
	height: 20px;
	padding: 2px 12px 0 12px;
	margin-bottom: -1px;
	position: relative;
}

.table_1 .tit ul li {
	float: left;
	display: inline;
	position: relative;
	bottom: -1px;
	margin-right: 10px;
	font-weight: normal;
	padding-left: 3px;
	background-position: left top;
	background-repeat: no-repeat;
	height: 20px;
	line-height: 20px;
}

.table_1 .tit ul li a {
	padding-right: 3px;
	background-position: right bottom;
	background-repeat: no-repeat;
}

.table_1 .tit ul li a span {
	padding-right: 12px;
	background-position: left -20px;
	background-repeat: repeat-x;
}

.table_1 .tit ul li a span i {
	font-style: normal;
	padding-left: 18px;
	background-repeat: no-repeat;
}

.table_1 .tit ul li.col {
	width: 1px;
	background-position: 0 -644px;
	padding-left: 0;
}

.table_1 .tit ul li a span i.add {
	background-position: 2px 3px;
}

.table_1 .tit ul li a span i.last {
	background-position: 2px -53px;
}

.table_1 .tit ul li a span i.next {
	background-position: 2px -25px;
}

.table_1 .tit ul li a span i.first {
	background-position: 2px -109px;
}

.table_1 .tit ul li a span i.fin {
	background-position: 2px -81px;
}

.table_1 .tit ul li a span i.pre {
	background-position: 2px -165px;
}

.table_1 .tit ul li a span i.bac {
	background-position: 2px -137px;
}

.table_1 .tit ul li a span i.save {
	background-position: 2px -221px;
}

.table_1 .tit ul li a span i.can {
	background-position: 2px -277px;
}

.table_1 .tit ul li a span i.sub {
	background-position: 2px -305px;
}

.table_1 .tit ul li a span i.del {
	background-position: 2px -193px;
}

.table_1 .tit ul li a span i.support {
	background-position: 2px -249px;
}

.hideLayer_1 p {
	text-align: left;
	padding: 8px 12px;
	font-weight: normal;
}

.table_1 .tit h5, .table_2 th h5, .table_2 th.disable h5{
	margin-right: 15px;
	float: left;
	padding-left: 16px;
}

.table_1 .tit li.button,.table_1 .tit li.button:hover {
	position: absolute;
	right: 12px;
	top: 0px;
	z-index: 10;
	background: none;
	width: 22px;
	height: 21px;
}

.table_1 .tit li.button a i.add {
	background-position: 4px 3px;
}

.table_1 .tit li.button a i {
	font-style: normal;
	padding-left: 22px;
	padding-top: 21px;
	background-repeat: no-repeat;
	width: 0px;
	height: 0px;
	text-indent: -9999px;
}

.table_1 td.lableTd, .table_2 .t_r{
	white-space: nowrap;
	width: 15%;
}
.b_font{
	font-weight:bold;
}

/*Table End*/ /*Line*/
.lineNum {
	background-repeat: no-repeat;
	padding-left: 22px;
	margin: 0 auto;
	width: 0px;
	text-indent: -9999px;
}

.l01 {
	background-position: left 5px;
}

.l02 {
	background-position: left -35px;
}

.l03 {
	background-position: left -75px;
}

.l04 {
	background-position: left -115px;
}

.l05 {
	background-position: left -155px;
}

.l06 {
	background-position: left -195px;
}

.l07 {
	background-position: left -235px;
}

.l08 {
	background-position: left -275px;
}

.l09 {
	background-position: left -315px;
}

.l10 {
	background-position: left -355px;
}

.l11 {
	background-position: left -395px;
}

/*Line End*/
/*Tree_1*/
.tree_1 {
	width: 208px;
	overflow: auto;
}

.bar_1 {
	width: 8px;
}

.tree_1 ul, .tree_1 ul li h5{
	margin-bottom:1px;
}
.tree_1 ul li h5 {
	height: 20px;
	line-height: 20px;
	padding: 1px 3px 1px 8px;
}

.tree_1 ul ul li, .tree_1 ul ul ul li{
	line-height: 19px;
	padding-left: 17px;
	background-position: 5px top;
	background-repeat: no-repeat;
}

.tree_1 ul ul li.fin, .tree_1 ul ul ul li.fin{
	background-position: 5px -18px;
}

.tree_1 ul ul li a, .tree_1 ul ul ul li a{
	padding-left:18px;
}
.tree_1 ul ul li, .tree_1 ul ul ul li{
	overflow:hidden;
	white-space:nowrap;
}
.tree_1 ul ul li.selected, .tree_1 ul ul ul li.selected{
	font-weight:bold;
}

/*Tree_1 End*/ /*Panel_4*/
.panel_4 {
	margin: 0 10px;
}

.panel_4 header {
	padding-left: 8px;
	height: 43px;
}

.panel_4 header .tit {
	padding-right: 8px;
}

.panel_4 header .bg {
	padding-top: 4px;
	height: 39px;
}

.panel_4 .con {
	padding: 8px 0px;
	overflow: hidden;
}
.panel_4 .con .border_gary{
	width:154px;
	height:42px;
	padding:1px;
}
.panel_8 .border_gary_mini{
	width:78px;
	height:78px;
	padding:1px;
}
.border_gary_mini_2{
	width:56px;
	height:56px;
	padding:1px;
}
.panel_4 .con .pie, .panel_7 .con .pie{
	width:122px;
	height:122px;
	background-repeat:no-repeat;
}
.panel_4 .con .pie1, .panel_7 .con .pie1{
	background-position:left top;
}
.panel_4 .con .pie2, .panel_7 .con .pie2{
	background-position:-150px top;
}
.panel_4 .con .pie3, .panel_7 .con .pie3{
	background-position:left top;
}
.panel_4 .con .pie4, .panel_7 .con .pie4{
	background-position:left top;
}
.panel_4 .con .pie5, .panel_7 .con .pie5{
	background-position:left top;
}
.panel_4 .con .pie6, .panel_7 .con .pie6{
	background-position:-750px top;
}
.panel_4 .con .pie7, .panel_7 .con .pie7{
	background-position:left top;
}
.panel_4 .con .pie8, .panel_7 .con .pie8{
	background-position:left top;
}



.panel_4 .con ul{
	/*min-width:160px;
	margin-right:6px;*/
}

.panel_4 td {
	/*padding: 0 10px;*/
}
.panel_8 td{
	padding-left:2px;
	padding-right:2px;
}

.panel_4 h1, .panel_4 h3, .panel_7 h1, .panel_8 h3, .panel_8 h2{
	font-weight: lighter;
	line-height: 26px;
}

.totle {
	background-repeat: no-repeat;
	padding-left: 20px;
}

.t_m {
	background-position: -2px -640px;
}

.t_y {
	background-position: -2px -680px;
}

/*Panel_4 End*/ /*Alert_1*/
.Alert_1 {
	margin: 8px;
	margin-top: 0;
	position: relative;
}

.A_4 {
	padding: 8px 18px;
	height: 120px;
}
.A_9 {
	padding: 0px 18px;
}


.A_5,.A_6,.A_7,.A_8 {
	position: absolute;
	z-index: 10;
	width: 12px;
	height: 25px;
	background-repeat: no-repeat;
}

.A_5,.A_6 {
	top: 0;
}

.A_7,.A_8 {
	bottom: 0;
}

.A_5,.A_7 {
	left: 0;
}

.A_6,.A_8 {
	right: 0;
}

.A_5 {
	background-position: left top;
}

.A_6 {
	background-position: right -100px;
}

.A_7 {
	background-position: left -150px;
}

.A_8 {
	background-position: right -125px;
}

.Alert_1 header {
	height: 26px;
	line-height: 26px;
	backface-visibility: hidden;
}


.Alert_1 .line span, .Alert_1 .line i {
	float: left;
	font-style: normal;
}

.Alert_1 p b {
	font-size: 14px;
}

.Alert_1 p a {
	display: inline;
}

.A_dot li {
	width: 10px;
	height: 10px;
	margin: 6px 3px;
	float: left;
	display: inline-block;
	background-repeat: no-repeat;
	text-indent: -9999px;
}

.A_dot li.red {
	background-position: left top;
}

.A_dot li.orange {
	background-position: left -10px;
}

.A_dot li.yellow {
	background-position: left -20px;
}

.A_dot li.blue {
	background-position: left -30px;
}
.A_Highcharts{
	width:400px;
	height:240px;
}

/*Alert_1 ENd*/


/*Total*/
.total {
	height: 40px;
}

.total .i_em {
	position: relative;
	width: 154px;
	height: 40px;
}

.total .i_full {
	position: absolute;
	left: 2px;
	top: 0;
	height: 40px;
	width: 0px;
	z-index: 50;
	max-width: 150px;
}

.total h5,.total h6 {
	line-height: 20px;
	padding-top: 15px;
	margin-left: 12px;
}
/*Total End*/


/*News_list*/
.News_list{
/*	background-origin:padding-box;
	-moz-background-origin:padding;
	-webkit-background-origin:padding;
	-khtml-background-origin:padding;
*/	padding-top:8px;
	margin-left:6px;
}
.News_list header{
	height:37px;
	padding-left:15px;
	background:none;
	border-radius:0;
}
.News_list header h4, .News_list header h5{
	line-height:37px;
	font-style:normal;
	margin-right:16px;
}
.News_list .list, .News_list .pager, .stat_3{
	padding:5px 23px;
	line-height:24px;
}
.News_list .list li, .stat_3 dl li{
	margin-bottom:8px;
}
.News_list .list dt, .News_list .list dd{
	padding-left:12px;
}
.News_list .list dt a{
	width:70%;
	text-overflow:ellipsis;   
    white-space:nowrap;     
    overflow:hidden;     
    -o-text-overflow:ellipsis;
}
.News_list .list dt a, .News_list dt span.type{
	float:left;
	font-size:14px;
}

.News_list dt span.type{
	margin-right:8px;
}
.News_list .list dd{
	max-height:72px;
	overflow:hidden;
}

.News_list .list dd span,.News_list .list dd font ,.News_list .list dt a font, .News_list header span, .stat_3 dl li, .stat_3 dl li a, .stat_3 span, .view span, .zhaiyao a{
	display:inline;
}
.News_list .pager li{
	float:left;
	display:inline;
	margin-left:5px;
}
.News_list .pager li a, .stat_3 dl li span{
	padding:0 5px;
}
.view p{
	font-size:14px;
}
.view img{
	margin:auto;
}
.view .attach{
	background-position:10px -480px;
	background-repeat:no-repeat;
	padding-left:30px;
}
.view .attach dl{
	border:none;
	line-height:14px;
	font-size:11px;
}

aside.related{
	width:24%;
	float:left;
	display:inline;
}
aside.related ul.list{
	margin:0 12px;
}

.panel_5{
	background-position:0px -82px;
	background-repeat:repeat-x;
}
.panel_5 .tit{
	background-position:right -41px;
	background-repeat:no-repeat;
	height:41px;
}
.panel_5 h4{
	background-position:left top;
	background-repeat:no-repeat;
	line-height:41px;
	padding-left:32px;
}
.panel_5 .con{
	margin:1px;
	padding-bottom:8px;
}
.stat_3 dl{
	padding-top:8px;
	*padding-bottom:8px;
}
.stat_3 dl dt, .stat_3 dl dd, .stat_3 dl li{
	float:left;
	display:inline-block;
	font-size:14px;
}
.stat_3 dl.s3_d{
	border:none;
	padding:0;
}
.stat_3 dl.s3_d ul{
}
.stat_3 dl.s3_d dt, .stat_3 dl.s3_d dd{
}
.stat_3 dl.s3_d dt{
	font-weight:bold;
	min-width:180px;
	text-align:right;
	padding-right:8px;
}
.stat_3 dl.s3_d li{
	width:140px;
}
.stat_3 dl.s3_d li.mid{
	width:210px;
}
.stat_3 dl.s3_d li.long{
	width:280px;
}

.stat_3 dl dt{
	font-weight:bold;
	width:130px;
	text-align:right;
	padding-right:8px;
}
.stat_3 dl li{
	margin-right:10px;
}
.stat_3 dd li{
	width:120px;
}
.stat_3 dd li.mid{
	width:180px;
}
.stat_3 dd li.long{
	width:240px;
}
.stat_3 dl li a{
	padding:0 5px;
}

/*News_list End*/


/*Switch*/
.switch_table, .switch_table_1{
	width:9px;
	height:25px;
	margin-right:5px;
	background-repeat:no-repeat;
	margin-left:-2px;
}
.switch_table{
	background-position:left top;
}
.switch_table_1{
	background-position:left -25px;
}
.switch_table:hover{
	background-position:-9px top;
}
.switch_table_1:hover{
	background-position:-9px -25px;
}
/*Switch End*/


/*Flow*/
.Flow .f_bg{
	padding-top:16px;
	padding-left:5px;
	position:relative;
}
.Flow .logo_1{
	margin-left:21px;
	margin-bottom:22px;
	height:22px;
	width:238px;
}
.Flow .gray_bg{
	width:850px;
}
.Flow .gray_bg2{
	padding:26px 17px 18px 17px;
}
.Flow .w_bg{
	padding:0 9px 0 7px;
}
.Flow .Top, .Flow .Top_fw{
	padding:6px 21px;
	min-height:700px;
}

.Flow h1{
	font-family:榛戜綋;
	font-weight:bold;
	padding:20px;
}
.Flow .Step{
	border-radius:7px;
	padding:8px 18px;
}
.Flow .Step ul{
	max-width:720px;
	/*float:left;
	overflow:hidden;*/
	font-size:14px;
}
.Flow .Step li{
	display:inline-block;
	float:left;
	padding-right:38px;
}
.Flow .Step li dl{
	position:relative;
	height:101px;
}
.Flow .Step li dt{
	height:49px;
	width:32px;
	background-repeat:no-repeat;
	background-position:left -200px;
	text-align:center;
	margin-left:-3px;
}
.Flow .Step li dd{
	line-height:20px;
	text-align:center;
	position:absolute;
	z-index:300;
	left:-15px;
	width:60px;
	margin-left:-3px;
}

.Flow .Step li.pass dt{
	background-position:left -400px;
}
.Flow .Step li.pass{
	background-position:left -100px;
}
.Flow .Step li.on{
	background-position:left -100px;
	font-weight:bold;
}

.Flow .Step li.on dt{
	background-position:left -300px;
}

.Flow .Step li.fst dt{
	/*background-position:-3px -200px;*/
}
.Flow .Step li.fin{
	padding-right:0;
}
.Flow .Step li.fin dl{
	float:right;
}
.Flow .Step li.fin dt{
	margin-right:-4px;
	/*width:40px;
	float:right;*/
	/*background-position:4px -200px;*/
}
.Flow .footer{
	height:30px;
	padding-top:12px;
}

.f_bg .Divab1, .f_bg_fw .Divab1{
	position:absolute;
	top:212px;
	left:810px;
	right:0;
	width:273px;
	z-index:99;
}

.panel_6{
	margin-bottom:25px;
	padding-left:32px;
	position:relative;
	min-height:140px;
}
.panel_6 .divH{
	padding-top:8px;
	background-position:left top;
}
.panel_6 .divF{
	height:8px;
	background-position:-250px top;
}
.panel_6 .divH, .panel_6 .divF, .panel_6 .divT, .panel_6 ul.button li, .panel_6 li a.print, .panel_6 li a.tips, .panel_6 li a.ywbl,.panel_6 li a.prefill, .panel_6 li a.imp, .panel_6 li a.exp, .panel_6 li a.jk, .f_window .close{
	background-repeat:no-repeat;
}
.panel_6 .divB{
	background-position:-500px top;
	background-repeat:repeat-y;
	min-height:160px;
	padding:12px;
}
.panel_6 .divT, .panel_6 .divT_c{
	position:absolute;
	left:0;
	top:16px;
	z-index:199;
	width:40px;
	height:102px;
	background-repeat:no-repeat;
	padding-top:16px;
	text-align:center;
}
.panel_6 .divT{
	background-position:-750px top;
}

.panel_6 .divT_c{
	background-position:-960px top;
}

.panel_6 .more_4 a{
	padding-top:35px;
	text-indent:-9999px;
	width:0;
	height:0;
	padding-left:40px;
	background-position:-800px 16px;
	background-repeat:no-repeat;
}
.panel_6 a.icon{
	padding:2px 9px;
	background-repeat:no-repeat;
	height:14px;
}
.panel_6 a.icon_1{
	background-position:10px -758px;
}
.panel_6 a.icon_2{
	background-position:10px -798px;
}
.panel_6 h5{
	padding-bottom:4px;
}
.panel_6 h5 .fr, .panel_6 h5 .fr span{
	font-weight:normal;
	display:inline;
	font-size:12px;
	padding-top:1px;
}
.panel_6 ul.list li{
	line-height:22px;
	max-height:44px;
	overflow:hidden;
	padding-left:16px;
}
.panel_6 .con{
	padding-top:8px;
}
.panel_6 ul.button li{
	background-position:-850px top;
	margin:3px 6px;
	width:73px;
	height:21px;
	padding:1px 9px;
	line-height:20px;
	float:left;
}
.panel_6 ul.button li a{
	padding-left:20px;
}
.panel_6 li a.tips{
	background-position:0 -838px;
}
.panel_6 li a.ywbl{
	background-position:0 -958px;
}
.panel_6 li a.imp{
	background-position:0 -878px;
}
.panel_6 li a.exp{
	background-position:0 -918px;
}
.panel_6 li a.jk{
	background-position:0 -998px;
}
.panel_6 li a.prefill{
	background-position:0 -78px;
}
.panel_6 dl{
	padding:3px;
}

.References{
	padding:4px 15px;
	position:relative;
}
.References .arrow{
	width:20px;
	height:11px;
	position:absolute;
	top:-9px;
	left:20px;
	background-position:-450px -50px;
	background-repeat:no-repeat;
	z-index:100;
}
/*Flow End*/

/*Table_2*/
.table_2 th{
	height:30px;
	padding-left:25px;
	line-height:28px;
}
.table_2 th h5, .table_2 th.disable h5{
	line-height:28px;
}
.table_2 th ul{
	padding-top:11px;
}
.table_2 th li, .table_2 th li a{
	background-repeat:no-repeat;
}
.table_2 th li{
	float:left;
	padding-left:2px;
	background-position:left top;
}
.table_2 th li a{
	padding-right:2px;
	background-position:0px -38px;
}
.table_2 th li span{
	padding:0 8px;
	height:19px;
	background-position:0px -19px;
	background-repeat:repeat-x;
}
.table_2 th li.selected{
	background-position:0px -57px;
}
.table_2 th li.selected a{
	background-position:0px -95px;
}
.table_2 th li.selected span{
	background-position:0px -76px;
}

.table_2 td{
	line-height:22px;
}
.table_2 p, .view p{
	text-indent:24px;
}
.table_2 th span a, .table_2 span.plus a{
	width:15px;
	height:18px;
	text-indent:-9999px;
}
.table_2 th.disable span a{
	background-position:left -119px;
	background-repeat:no-repeat;
}
.table_2 th.disable span a.open{
	background-position:left -141px;
}
.table_2 span.plus a{
	background-repeat:no-repeat;
	background-position:left -158px;
}
.table_2 span.plus a.open{
	background-position:left -178px;
}


/*Table_2 End*/


/*Transparent*/
.transparent{
	position:absolute;
	/*min-height: 100%;  For Modern Browsers */
	/*height: auto !important;  For Modern Browsers */
	/*height: 100%;  For IE */
	filter:alpha(opacity=30);
	-moz-opacity:0.3;
	opacity:0.3;
	z-index:999;
	top:0;
	left:0;
	width:100%;
}

.f_window{
	position:absolute;
	z-index:9999;
	width:490px;
	margin:120px 150px 0 150px;
	border-radius:6px;
}
.f_window h3{
	height:44px;
	line-height:43px;
	padding:0 12px;
}
.f_window .close{
	background-position:left -1025px;
	height:20px;
	width:20px;
	padding-top:14px;
	text-indent:-9999px;
}
.f_window .con, .f_window .button{
	padding:12px;
}
.f_window .con{
	font-size:14px;
/*	line-height:30px;
*/}
.f_window .con td{
	padding:3px;} 
.f_window .con .td_1{
	width:18px;
}
.f_window .con .pl18{
	font-size:12px;
}
.f_window .con a{
	display:inline;
	margin-left:8px;
}
/*Transparent End*/

/*Node*/
.node{
	margin:0 12px;
	padding:5px 0;
}
.node .con{
	margin-left:30px;
}
.node .t_r{
	width:100%;
}
.node span, .node a{
	display:inline;
}
.node .dis, .node .fw{
	background-repeat:no-repeat;
	padding-left:20px;
}
.node .dis{
	background-position:left -120px;
}
.node .fw{
	background-position:left -960px;
}
/*Node End*/



/*Login*/
.Login .mainDiv{
	width:980px;
	height:566px;
	margin:0 auto;
	position:relative;
	background-position:center -301px;
}
.Login .logo{
	width:277px;
	height:77px;
	position:absolute;
	left:215px;
	top:158px;
	z-index:10;
	background-position:left -223px;
}
.Login .loginBox{
	width:300px;
	position:absolute;
	left:576px;
	top:116px;
	z-index:50;
	background-position:left 10px;
	min-height:300px;
}
.Login .mainDiv, .Login .logo, .Login .loginBox{
	background-repeat:no-repeat;
}
.Login hgroup{
	padding-left:45px;
	margin-bottom:32px;
}
.Login hgroup h3, .Login hgroup h6{
/*	font-weight:normal;
*/}
.Login .loginBox .field{
	padding-left:35px;
	padding-top:8px;
}
.Login .loginBox .field label{
	width:60px;
	text-align:right;
	display:inline-block;
	padding-right:10px;
	font-size:14px;
}
.Login .loginBox .saveCk{
	overflow:hidden;
	padding-top:8px;
	padding-left:109px;
	margin-bottom:8px;
}
.Login .loginBox .saveCk label{
	display:inline-block;
}
.Login .loginBox .subBox{
	padding-left:104px;
}
.Login .cr{
	position:absolute;
	top:512px;
	line-height:31px;
	height:31px;
	width:998px;
}
/*Login End*/

/*Panel_7*/
.panel_7{
	background-position:left -132px;
	background-repeat:no-repeat;
/*	-moz-border-radius-topleft: 5px;
	-webkit-border-top-left-radius: 5px;
	-moz-border-radius-topright: 5px;
	-webkit-border-top-right-radius: 5px;
	-moz-border-radius-bottomleft: 5px;
	-webkit-border-bottom-left-radius: 5px;
	-moz-border-radius-bottomright: 5px;
	-webkit-border-bottom-right-radius: 5px;
*/}
.panel_7 .p_right{
	background-position:right -203px;
	background-repeat:no-repeat;
}
.panel_7 hgroup, .panel_7 .hgroup{
	height:67px;
	padding-left:4px;
	padding-right:4px;
	background-position:left -276px;
	background-repeat:repeat-x;
	margin-left:4px;
	margin-right:4px;
	+height:67px;
	+padding-left:4px;
	+padding-right:4px;
	+background-position:left -276px;
	+background-repeat:repeat-x;
	+margin-left:3px;
	+margin-right:3px;
	height:67px\0;
	padding-left:4px\0;
	padding-right:4px\0;
	background-position:left -276px\0;
	background-repeat:repeat-x\0;
	margin-left:3px\0;
	margin-right:3px\0;
}
.panel_7 .text{
	background-position:left -48px;
	background-repeat:no-repeat;
	padding-left:20px;
	padding-top:3px;
	padding-right:5px;
}
.panel_7 .text h3{
	padding-top:3px;
}
.panel_7 .setting{
	background-position:left -24px;
	background-repeat:no-repeat;
	width:42px;
	height:24px;
}
.panel_7 .setting:hover{
	background-position:left top;
}
.panel_7 .setting a{
	background-position:13px -909px;
	background-repeat:no-repeat;
	height:24px;
	text-indent:-9999px;
}
.panel_7 .setting:hover a{
	background-position:13px -937px;
}
.panel_7 .con{
	position:relative;
	padding:2px;
}
.panel_7 .con li span{
	width:60px;
	text-align:right;
	float:left;
}
/*.panel_4 .con li span{
	width:60px;
	text-align:left;
}
*/
.panel_7 .con li h6, /*.panel_4 .con li h6*/{
	font-style:normal;
	font-weight:lighter;
	width:48px;
	float:right;
	text-align:right;
}
.panel_7 span, .panel_7 b, .panel_7 i, .panel_4 span, .panel_4 b, .panel_4 i{
	display:inline-block;
	overflow:hidden;
}
.panel_7 p, .panel_4 p.gk{
	padding:10px 12px;
}
.w1280 .panel_7 p{
	width:200px;
}
.w1024 .panel_7 p{
	width:130px;
}
.w1280 .panel_7 ul{
	min-width:200px;
}
.w1024 .panel_7 ul{
	width:180px;
}
.panel_7 .list p{
	width:95%;
}
.panel_7 .list .con{
	position:relative;
	padding:6px 8px;
}

.panel_7 .con .conner{
	width:27px;
	height:27px;
	position:absolute;
	right:-4px;
	bottom:-4px;
	background-position:right -106px;
	background-repeat:no-repeat;
}
.panel_7 .data{
	cursor:pointer;
}
/*Panel_7 End*/

/*ItemFlow*/
.ItemFlow{
	min-width:933px;
	height:88px;
	overflow:hidden;
	background-position:left top;
	background-repeat:repeat-x;
	padding:13px 0px 13px 30px;
}
.ItemFlow .gray_bg{
	width:930px;
	height:88px;
	position:relative;
}
.ItemFlow .blueBg{
	height:88px;
	position:absolute;
	z-index:0;
	left:80px;
	max-width:747px;
	min-width:2px;
}
.ItemFlow .gray_bg .pie{
	position:relative;
	width: 117px; 
	height: 121px;
	float:left;
	display:inline-block;
	overflow:hidden;
	white-space:nowrap;
}
.ItemFlow .gray_bg .pie .zs{
	position:absolute;
	top:-14px;
	left:-17px;
	width: 121px; 
	height: 121px;
}
/*.ItemFlow .gray_bg .pie .tit{
	position:absolute;
	z-index:9999;
	top:24px;
	left:24px;
	width:40px;
	height:43px;
	background-repeat:no-repeat;
}
.ItemFlow .gray_bg .pie .t1{
	background-position:left top;
}
.ItemFlow .gray_bg .pie .t2{
	background-position:-118px top;
}
.ItemFlow .gray_bg .pie .t3{
	background-position:-235px top;
}
.ItemFlow .gray_bg .pie .t4{
	background-position:-351px top;
}
.ItemFlow .gray_bg .pie .t5{
	background-position:-468px top;
}
.ItemFlow .gray_bg .pie .t6{
	background-position:-585px top;
}
.ItemFlow .gray_bg .pie .t7{
	background-position:-701px top;
}
.ItemFlow .gray_bg .pie .t8{
	background-position:right top;
}

*/

/*Divider*/
.Divider{
	margin:0 10px;
	width:954px;
	background-position:left top;
	padding-top:10px;
	background-repeat:no-repeat;
	position:relative;
}
.Divider .arrow{
	width:20px;
	height:11px;
	position:absolute;
	top:-9px;
	left:60px;
	background-position:-450px top;
	background-repeat:no-repeat;
	z-index:100;
}
.Divider .bottom{
	background-position:-2000px bottom;
	padding-bottom:10px;
	background-repeat:no-repeat;
}
.Divider .con{
	background-position:-1000px top;
	background-repeat:repeat-y;
	padding:0 10px;
}
.Divider h5{
	background-position:-4px -418px;
	background-repeat:no-repeat;
	padding-left:12px;
}
.Divider p{
	font-size:12px;
	width:880px;
	word-break:break-all;
	white-space:nowrap;
	overflow:hidden;
	text-overflow:ellipsis;
}
.Divider ul{
	background-position:left -124px;
	background-repeat:repeat-x;
	max-width:880px;
	height:40px;
	float:left;
	overflow:hidden;
}
.Divider ul li{
	display:inline-block;
	float:left;
	white-space:nowrap;
	height:40px;
	background-repeat:no-repeat;
	line-height:38px;
	padding-left:34px;
	width:71px;
	font-size:12px;
	font-weight:bold;
	margin-right:5px;
}
.Divider ul li.fst{
	padding-left:24px;
	width:83px;
}
.Divider ul li.fin{
	margin-right:0;
	float:right;
}
.Divider ul li{
	background-position:-150px -50px;
}
.Divider ul li.selected{
	background-position:-150px -150px;
}
.Divider ul li.disable{
	background-position:-150px -100px;
}
.Divider ul li.alert{
	background-position:-150px top;
}
.Divider ul li.po1{
	background-position:left -50px;/*normal*/
}
.Divider ul li.fst_alert{
	background-position:left top;/*alert*/
}
.Divider ul li.fst_going{
	background-position:left -150px;/*going*/
}
.Divider ul li.fst_dis{
	background-position:left -100px;/*disable*/
}


.Divider ul li.fin_alert{
	background-position:-300px top;
}
.Divider ul li.fin_normal{
	background-position:-300px -50px;
}
.Divider ul li.fin_dis{
	background-position:-300px -100px;
}
.Divider ul li.fin_going{
	background-position:-300px -150px;
}

/*Divider End*/
.table_3 .tit{
	padding-left:8px;
	line-height:25px;
	font-weight:bold;
}
.table_3 li{
	width:230px;
	float: left;
	margin-right: 10px;
	display: inline;
	overflow:hidden;
	white-space:nowrap;
	text-overflow:ellipsis;
}
.table_3 li span{
	display:inline;
}
/*ItemFlow End*/

/*panel_8*/
.panel_8 .tit span{
	line-height:32px;
}
.block, .block_selected{
	background-repeat:repeat-x;
}
.block{
	background-position:left -500px;
}
.block_selected, .block:hover{
	background-position:left top;
}
.corner, .categories a{
	background-repeat:no-repeat;
}
.corner{
	background-position:right bottom;
}
.categories a{
	background-position:right -995px;
	padding-right:12px;
	margin-bottom:3px;
	margin-right:12px;
}
.gray_border{
	margin:0 1px 1px 0;
}
.panel_8 .con{
	padding:0;
}
.panel_8 dl{
	padding:2px 8px 2px 8px;
}
.panel_8 .con li span{
	text-align:left;
	font-size:11px;
}
.panel_8 .con li dt, .panel_8 .con li dd{
	float:left;
	height:27px;
	padding:3px 8px;
}
.panel_8 .con li dt{
	font-size:11px;
	line-height:26px;
}
.panel_8 .con td h6{
	font-style:normal;
	font-weight:lighter;
	/*width:48px;*/
	float:right;
	text-align:right;
}
.panel_8 li{
	line-height:20px;
}
.panel_8 .r_bor, .panel_8 .l_bor{
	height:40px;
	padding-top:10px;
	padding-left:8px;
	padding-right:8px;
}
.panel_8 .attention{
	padding-right:55px;
}
.panel_8 li.counter h1{
	line-height:55px;
}
.panel_8 li.counter span.c1, .panel_8 li.counter span.c2, .panel_8 li.counter span.c3{
	background-repeat:no-repeat;
	padding-left:44px;
	display:block;
	float:left;
	font-size:12px;
	/*line-height:55px*/
	line-height:25px;
}
.panel_8 li.counter span.c1{
	background-position:12px top;
}
.panel_8 li.counter span.c2{
	background-position:12px -80px;
}
.panel_8 li.counter span.c3{
	background-position:12px -162px;
}

.jttt{
	background-position:left -50px;
	background-repeat:repeat-x;
	margin-left:10px;
	margin-right:20px;
}
.jttt_l, .jttt_r{
	background-repeat:no-repeat;
}
.jttt_l{
	background-position:left top;
}
.jttt_r{
	background-position:right -100px;
	padding-left:150px;
	padding-right:10px;
	font-size:12px;
	height:44px;
	line-height:40px;
	overflow:hidden;
}
.jttt h5, .jttt .con{
	margin:0 20px 0 0;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	-o-text-overflow: ellipsis;
	height:40px;
}
.w1280 .jttt .con{
	/*max-width:475px;*/
	padding-right:20px;
}

.w1024 .jttt .con{
	max-width:180px;
}


/*20120820*/
.mail_1{
	background-repeat: no-repeat;
	width: 21px;
}
.mail_new{
	background-position: 7px -114px;
}
.mail_read{
	background-position: 7px 3px;
}
.mail_for{
	background-position: 7px -37px;
}
.mail_re{
	background-position: 7px -77px;
}

/*20120917*/
.close_2, .open_2{
	width:9px;
	height:43px;
	padding:4px 0;
	background-repeat:no-repeat;
	position:absolute;
	top:180px;
	z-index:999;
}
.close_2 a, .open_2 a{
	padding-top:43px;
	padding-left:9px;
	width:0;
	height:0;
	text-indent:-9999px;
}
.close_2{
	background-position:left -51px;
	float:right;
	left:202px;
}
.close_2:hover{
	background-position:left -153px;
}
.open_2{
	background-position:left top;
	float:left;
	left:0px;
}
.open_2:hover{
	background-position:left -102px;
}

/*20121023*/
.js_bg{
	padding:0 80px;
	background-repeat:no-repeat;
	background-position:80px 0px;
	margin:0 10px;
}
.js_bg .con{
	background-position:right -130px;
	padding:6px 30px;
	height:118px;
	background-repeat:no-repeat;
}

/*zccenter*/
.zc .num{
	width:15px;
	padding-left:1px;
	height:16px;
	background-repeat:no-repeat;
	background-position:left -880px;
	display:inline;
	line-height:16px;
	text-align:center;
	margin-top:5px;
}

/*xxcenter*/
h5.xx{
	padding-left:12px;
	line-height:14px;
	height:14px;
}
h5.xx span{
	display:inline;
	padding:0 10px;
}
.panel_3 .con div.xx img{
	width:161px;
	height:104px;
}
.panel_3 .liveuser{
	width:24.2%;
}
.panel_3 .liveuser h4, .panel_3 .liveuser ul{
	margin:8px;
}
.panel_3 .QAA{
	width:75.8%;
}
.panel_3 .QAA header input{
	margin:3px 5px;
	height:29px;
	*height:25px;
}
.panel_3 .QAA .con{
	padding:8px;
}
.panel_3 .QAA h4{
	padding-left:8px;
}
.panel_3 .QAA ul.full a{
	font-weight:bold;
}
.panel_3 .liveuser ul{
	line-height:23px;
	*line-height:20px;
	padding-left:20px;
}
.panel_3 .liveuser .ID{
	float:left;
	font-size:14px;
}
.panel_3 .liveuser span{
	display:inline;
}
.gk_1, .gk_2{
	background-repeat:no-repeat;
	padding-left:20px;
	font-size:12px;
}
.gk_1{
	background-position:left -240px;}
.gk_2{
	background-position:left -680px;}

/* CSS Version Default */
/* Version: 1.3.9  || Date: 2012-10-17 || Author: Cathy */
/* Cascading Style Sheets Level 2 Revision 1 */


body{
	background:#bfbfbf;
}

/*Header*/
header, .header{
	background:#fff url(images/top_button.png) left -82px repeat-x;
}
header .logo{
	background:url(images/top_left.jpg) left top no-repeat;
}

header .logo li:hover{
	background:url(images/top_button.png) left -41px no-repeat;
}
header .logo li.selected{
	background:url(images/top_button.png) left top no-repeat;
}
header .logo li a, header.Limis ul li a{
	background-image:url(images/top_icon.png);
}
header .user{
	color:#b8dbff;
}
header .user a, header .user select{
	color:#a1ec35;
}
header .user select{
	background-color:#10579c/*transparent*/;
	-webkit-background-image:url(images/select_arrow_1.gif);
	-webkit-background-repeat:no-repeat;
    -webkit-background-position: right center;
    -webkit-padding-right: 20px;
}
header .user a i{
	background:url(images/icon_2.png) left -510px no-repeat;
}
/*nav_1*/
header nav{
	border-left:#5a96e1 1px solid;
	border-right:#5a96e1 1px solid;
	border:#649ade 1px solid;
	background:#5f6f96 url(images/nav_1_left.jpg) left top no-repeat;
}
header nav ul li a{
	color:#d5edff;
	text-shadow:#3f4963 0 -1px 0, #8b9abe 0 1px 0;
}
header nav ul li:hover, header nav ul li:hover a, header nav ul li:hover a span, header nav ul li.selected, header nav ul li.selected a, header nav ul li.selected a span{
	background-image:url(images/nav_1_li.png);
}
header nav ul li:hover a span, header nav ul li.selected a span{
	color:#000;
	text-shadow:#eeeff1 1px 1px 0;
}
/*Header End*/

.frame_bl{
	border-left:1px solid #5a96e1;
}
.frame_br{
	border-right:1px solid #5a96e1;
}


/*Left*/
.gen_search .l_t, .gen_search .r_t, .gen_search .l_b, .gen_search .r_b{
	background-image:url(images/search_conner.png);
}
.gen_search .s_t, .gen_search .s_b{
	background-image:url(images/search_t_b.png);
}
.gen_search .s_l{
	background-image:url(images/s_l.png);
}
.gen_search .s_r{
	background-image:url(images/s_r.png);
}
.gen_search .s_r .con{
	background:#e1e2dd;
}

.sidebar{
	background:#d7dfe4 url(images/sidebar_bg.png) repeat-y;
	border:#699ddf 1px solid;
}
.sidebar ul li{
	color:#fff;
	margin-bottom:1px;
}
.sidebar ul li h4{
	background:url(images/nav_2.jpg) 0px -35px no-repeat;
}
.sidebar ul li h4.open{
	background:url(images/nav_2.jpg) 0px 0px no-repeat;
}
.sidebar ul li a{
	color:#fff;
}
.sidebar .menuOne ul li span{
	background:url(images/nav_2_bg.png) 0px 29px no-repeat;
}
.sidebar .menuOne ul li.selected span, .sidebar .menuOne ul li:hover span{
	-webkit-box-shadow:0px 3px 6px #9ea4a7;
	-moz-box-shadow:0px 3px 6px #9ea4a7;
	background:url(images/nav_2_bg.png) 0px -1px no-repeat;
}
.sidebar .menuOne ul li a{
	background-image:url(images/icon_1.png);
	color:#031134;
}
.sidebar .menuOne ul li a:hover, .sidebar .menuOne ul li.selected a{
	color:#fff;
}
.sidebar .menuOne ul ul i{
	background:url(images/nav_2_bg.png) 0px -61px no-repeat;
}

.sidebar .menuOne ul ul li{
	background:url(images/nav_2_bg.png) 0px -31px no-repeat;
}
.sidebar .menuOne ul li ul li a{
	background:url(images/icon_2.png) 35px 8px no-repeat;
	color:#323232;
}
.sidebar .menuOne ul li.selected ul li a{
	color:#000;
}
.sidebar .menuOne ul li ul li a:hover, .sidebar .menuOne ul li ul li.selected a{
	color:#075aa2;
}
/*Left End*/



/*Right*/
.main{
	background-color:#f2f2f2;
	background-image:url(images/main_b_bg.png) , url(images/main_45_32.png);
	background-repeat: repeat-x, repeat-x;
	background-position:left bottom, left top;
	background-origin:padding-box, padding-box;
	-moz-background-origin:padding, padding; 
	-webkit-background-origin:padding, padding; 
	-khtml-background-origin:padding, padding;
}
.ctrl{
	background:url(images/ctrl30_cr32.png) left top repeat-x



}
.ctrl .posi ul li{
	background:url(images/icon_2.png) right -350px no-repeat;
}
.ctrl .posi ul li a{
	color:#959595;
}
.ctrl .posi ul li.fin{
	color:#0a438d;
	font-weight:bold;
}
.ctrl .lit_nav li:hover, .ctrl .lit_nav li.selected, .table_1 .tit li.button a:hover{
	background:url(images/icon_1.png) 0px -600px no-repeat;
}
.ctrl .lit_nav li a, .totle, .panel_6 .icon, .panel_6 li a.print, .panel_6 li a.tips, .panel_6 li a.ywbl,.panel_6 li a.prefill, .panel_6 li a.imp, .panel_6 li a.exp, .panel_6 li a.jk, .f_window .close, .node .dis, .node .fw, .filter .fn h5 a.colSelect, .gk_1, .gk_2
{
	background-image:url(images/icon_1.png);
}


/*Right_Aside*/
aside .panel_1{
	border:#cecece 1px solid;
/*	background:url(../images/aside_p_bg1.png) left bottom repeat-x, url(../images/aside_p_bg2_01.png) 1px 1px repeat-x, url(../images/aside_p_bg2.png) right 1px no-repeat;
*/	background-color:#fff;
	background:#fff url(images/aside_p_bg1.png) left bottom repeat-x;
}
.panel_1 .bg_2{
	background:url(images/aside_p_bg2_01.png) 1px 1px repeat-x;
}
.panel_1 .bg_3{
	background:url(images/aside_p_bg2.png) right 1px no-repeat;
}
aside .panel_2, aside ul.item li{
	border:#cecece 1px solid;
	background-color:#fff;
}
aside hgroup{
	background:url(images/hgroup_bg.png) 6px 6px no-repeat;
}
aside hgroup.weather, aside hgroup.calendar, aside hgroup.stats_2{
	background-image:url(images/hgroup_bg2.png);
	background-repeat:no-repeat;
}
aside hgroup h3, aside hgroup h5{
	color:#000;
}
aside hgroup h6, .news hgroup h6, .news .txt_list li span, .panel_3 .con li span, .panel_4 .con span, .panel_4 h6, .News_list .list dt span, .node .con .date_02, .node .con .clearfix, .node .con .clearfix a, .panel_6 dl span, .panel_6 h5 .fr, .sidebar .menuOne ul li a.disable, .panel_7 h6, #dtb td, aside ul.qa li dd, aside ul.stats_2 li h6, footer .counter, .panel_1 span{
	color:#9a9a9a;
}
aside hgroup a.selected{
	color:#009dff;}
aside ul.topic li{
	background:url(images/topic_list_bg.png) center bottom no-repeat;
}
aside ul.topic li a, aside ul.qa li dt{
	background:url(images/icon_2.png) 0px -150px no-repeat;
}
aside ul.topic li a b, aside ul.qa li dt{
	color:#ba0d0d;
}

aside ul.list li{
	border-bottom:#b4bcc4 1px dotted;
	background:url(images/icon_2.png) 0px -110px no-repeat;
}
aside ul.qa li, aside ul.list_2 li{
	border-bottom:#b4bcc4 1px dotted;
}
aside ul.xx li{
	/*margin:0 5px 0 0;*/
	margin-right:5px;
}
aside ul.xx li a{
	color:#1e1e1e;
}
aside ul.xx li a:hover{
	color:#3488e4;
}
aside ul.list_2 li a{
	color:#000;
	background:url(images/icon_2.png) left -74px no-repeat;
}
aside ul.xx dt{
	background-image:url(images/apps.png);
}
aside a.more_1{
	background:url(images/hgroup_bg.png) 0px -56px no-repeat;
}
aside ul.stats_2_bg1{
	background:url(images/tj-1.png) right bottom no-repeat;
}/*笑脸*/
aside ul.stats_2_bg2{
	background:url(images/tj-2.png) right bottom no-repeat;
}/*一般*/
aside ul.stats_2_bg3{
	background:url(images/tj-3.png) right bottom no-repeat;
}/*不好*/
aside ul.stats_2 li{
	background-color:#f9f9f9;
	*background-color:transparent;
	border:#ebebeb 1px solid;
}
.hy_button{
	border:#cecece 1px solid;
}
.hy_button a{
	color:#586369;
}


/*aside ul.item li .bg, aside ul.item li h3, aside ul.item li h5, aside ul.item li a.I1, aside ul.item li a.I2, aside ul.item li a.I3, aside ul.item li a.I4, aside ul.item li a.I5, aside ul.item li a.I6{
	background-image:url(images/item.png);
	color:#17436f;
}*/
/*Right_Aside End*/


/*Right_News*/
/*.news{
	background:url(images/news_left.jpg) left top no-repeat, url(images/news_right.jpg) right top no-repeat, url(images/news_bg.jpg) left top repeat-x;
}html5支持的多图片背景属性*/
.news{
	background:url(images/news_bg.jpg) left top repeat-x;
}
.news_r{
	background:url(images/news_right.jpg) right top no-repeat;
}
.news_l{
	background:url(images/news_left.jpg) left top no-repeat;
}


.news .focusPic .fp_list{
	border:#e1ecf6 1px solid;
}
.news .focusPic .word {
	background-color:#000;
	color:#fff;
}

.news .focusPic .word li a, .news .focusPic .word li a:hover{
	color:#fff;
}
.news .focusPic .scrollnav li{
	background:url(images/scrollnav.png) left top no-repeat;
}
.news .focusPic .scrollnav li.current, .news .focusPic .scrollnav li:hover{
	background-position:left -11px;
}


.news hgroup h2{
	color:#5f6f96;
	background:#edf2f9 url(images/icon_2.png) right -555px no-repeat;
}
.news hgroup h6{
	background:#eaf0f8;}
.news hgroup p{
	background:#bce3ef;
}
a.more_2{
	background:url(images/icon_2.png) right -238px no-repeat;
	color:#0080bc;
}
.news section a h5, .jttt a h5{
	color:#0a438d;
}
.news section a p, .panel_3 .up a:hover, .panel_3 .down a:hover, .Alert_1 p{
	color:#5e5e5e;
}
.news .txt_list li{
	background:url(images/icon_2.png) -6px -36px no-repeat;
}
/*News End*/
/*Panel_3*/
.panel_3, .panel_4 .con{
	border:#b8d4df 1px solid;
	background:#fff url(images/aside_p_bg1.png) left bottom repeat-x;
	background-origin:padding-box;
	-moz-background-origin:padding; 
	-webkit-background-origin:padding; 
	-khtml-background-origin:padding;
}
.panel_3 header{
/*	background:url(../images/panel_4_bg1.png) left top no-repeat, url(../images/panel_4_bg2.png) repeat-x;
*/
	background:url(images/panel_4_bg2.png) repeat-x;
}
.panel_3 header .bg_3{
	background:url(images/panel_4_bg1.png) left top no-repeat;
}
h5.file{
	background:url(images/hgroup_bg.png) left -74px no-repeat;
	color:#001e40;
}
h5.meeting, h5.wlist1, h5.wlist2, h5.richeng, h5.announce{
	background-image:url(images/hgroup_bg3.png);
	color:#001e40;
}
.panel_3 .con li, #dtb tr.dtbTR{
	background:url(images/icon_2.png) -4px -75px no-repeat;
	border-bottom:#b6b6b6 1px dashed;
}
#dtb tr.jgTR td{
	border-bottom:#b6b6b6 1px dashed;
}
#dtb tr.tit td, #dtb tr.jgtit td, aside hgroup b{
	color:#000;
}
a.more_3{
	background:url(images/hgroup_bg.png) left -109px no-repeat;
	color:#115485;
}
.panel_3 .up a, .panel_3 .down a{
	color:#115485;
}
/*Panel_3 End*/
/*Right End*/



/*Footer*/
footer{
/*	border:1px solid #5a96e1;
	border-top:none;
*/	color:#3e3e3e;
	background:url(images/ctrl30_cr32.png) 0px -35px repeat-x;
}
footer .con{
/*	background:url(images/ctrl30_cr32.png) 0px -35px repeat-x;
*/}
footer .tel{
	background:url(images/ctrl30_cr32.png) 0px -67px no-repeat;
}
footer b{
	color:#000;
}
/*Footer End*/


/*Tabs*/
/*Tabs_1*/
.tabs_1 ul li, .tabs_1 ul li a, .tabs_1 ul li a span, .tabs_2 ul li, .tabs_2 ul li a, .tabs_2 ul li a span{
	background-image:url(images/tabs_1_on.png);
}
.tabs_1 ul li, .tabs_1 ul li a, .tabs_2 ul li, .tabs_2 ul li a{
	background-repeat:no-repeat;
}
.tabs_1 ul li{
	background-position:0px -162px;
}
.tabs_1 ul li a{
	background-position:right -216px;
	color:#000;
}
.tabs_1 ul li a span{
	background-position:0px -189px;
	background-repeat:repeat-x;
}
.tabs_1 ul li:hover{
	background-position:0px -81px;
}
.tabs_1 ul li:hover a{
	background-position:right -135px;
}
.tabs_1 ul li:hover a span{
	background-position:0px -108px;
}
.tabs_1 ul li.selected{
	background-position:0px 0px;
}
.tabs_1 ul li.selected a{
	background-position:right -54px;
	color:#00416a;
}
.tabs_1 ul li.selected a span{
	background-position:0px -27px;
}
.tabs_1 .pre a,.tabs_1 .next a, .tabs_1 .pre i, .tabs_1 .next i{
	background-image:url(images/tabs_1_2.png);
}
.tabs_1 .pre a{
	background-position:left -54px;
}
.tabs_1 .pre a:hover{
	background-position:left -81px;
}
.tabs_1 .next a{
	background-position:left top;
}
.tabs_1 .next a:hover{
	background-position:left -27px;
}

.tabs_1 .pre i, .tabs_1 .next i{
	background-position:0px -108px;
}
/*Tabs_1 End*/

/*Tabs_2*/
.tabs_2{
	border-bottom:#b5b5b5 1px solid;
}
.tabs_2 ul li{
	background-position:0px -372px;
}
.tabs_2 ul li a{
	background-position:right -422px;
	color:#f5f5f5;
}
.tabs_2 ul li a span{
	background-position:0px -397px;
	background-repeat:repeat-x;
}
.tabs_2 ul li:hover, .tabs_2 ul li.selected{
	background-position:0px -297px;
}
.tabs_2 ul li:hover a, .tabs_2 ul li.selected a{
	background-position:right -347px;
}
.tabs_2 ul li:hover a span, .tabs_2 ul li.selected a span{
	background-position:0px -322px;
	color:#114371;
}
/*Tabs_2 End*/
/*Tabs_3*/
.tabs_3 li{
	border-left:#fff 1px solid;
	border-right:#cecece 1px solid;
	border-bottom:#e1e1e1 1px solid;
}
.tabs_3 li:hover, .tabs_3 li.selected{
	border-bottom:none;
}
.tabs_3 li a{
	color:#484848;
}
.tabs_3 li:hover a, .tabs_3 li.selected a{
	color:#000;
}
/*Tabs_3 End*/ 


/*Tabs*/

/*Filter*/
.filter{
	background-color:#e2e7f3;
	border-bottom:#c4c5c6 1px solid;
}
.filter_sandglass, .filter_search, .filter_tips{
	background-image:url(images/filter.png);
	background-repeat:no-repeat;
}
.filter .query{
	background:url(images/query.png) repeat-x;
	border-bottom:#cdcdcd 1px solid;
}
.filter .fn{
	background:#d6d9e5;}
/*Filter End*/

/*Table*/
.table_1, .table_3{
	border:#9dade3 1px solid;
	border-right:none;
	background:#fff;
}
.table_1 thead, .table_1 .tfoot td, .table_1 .tfoot_1, .table_1 tr:hover td.tfoot_1{
	background:#c9d4f3 url(images/tabs_1_on.png) left -472px repeat-x;
}
.table_1 td{
	border-bottom:#d0d0d3 1px solid;
	border-right:#fff 1px solid;
	border-top:#fff 1px solid;
}
.table_1 tfoot td, .table .tfoot{
	border:none;
}
.table_1 thead, .table_1 th a, .filter .fn h5 a{
	color:#083d74;
}
.table_1 tr:hover td{
	/*border-bottom:#8b96bc 1px dashed;
	border-top:#8b96bc 1px dashed;*/
	background:#d9def2;
}
.table_1 .tit td, .table_3 .tit{
	background:url(images/tabs_1_on.png) left -512px repeat-x;
	color:#656565;
}
.table_1 .tit td.sort a i, .panel_3 .up a, .panel_3 .down a, .zc .num, aside ul.stats_2 li, .view .attach{
	background-image:url(images/icon_2.png);
}
.table_1 .item{
	background:url(images/icon_2.png) left -600px no-repeat;
}
.table_1 .pager li{
	background:#fff;
}
.table_1 .pager li a{
	color:#426d7d;
}
.table_1 .pager li.selected a, .table_1 .pager li a:hover{
	color:#fff;
	background:#003d66;
}
.table_1 .tit ul{
	border-bottom:#b6babf 1px solid;
}
.table_1 .tit ul li a{
	color:#232323;
}
.table_1 .tit ul li:hover, .table_1 .tit ul li:hover a, .table_1 .tit ul li:hover a span, .table_1 .tit ul li.selected, .table_1 .tit ul li.selected a, .table_1 .tit ul li.selected a span{
	background-image:url(images/tit_li.gif)
}
.table_1 .tit ul li.col, .table_1 .tit ul li a span i,.table_1 .tit li.button a i, .panel_7 .setting a, .Divider h5{
	background-image:url(images/button_bg.png);
}

.hideLayer_1{
	background:#fff url(images/aside_p_bg1.png) left bottom repeat-x;
}

.table_1 .lableTd, .table_2 .lableTd{
	background-color:rgb(191, 220, 235);
}

.table_1 .tit h5, .table_2 th h5{
	color:#000;
	background:url(images/button_bg.png) left -420px no-repeat;
}

/*Table End*/

/*Line*/
.lineNum{
	background-image:url(images/line.png);
}
/*Line End*/
/*Tree_1*/
.tree_1{
	border:#8db1e3 1px solid;
	background:#fff;
	border-right:none;
/*	border:#dfe8f7 5px solid;
*/}
.tree_1 a, .table_1 .b_font{
	color:#000;
}
.bar_1{
	background-color:#dfe8f7;}
.tree_1 ul li h5{
	background:url(images/tree_1.png) left top repeat-x;
}
.tree_1 ul li h5 a, .table_2 th span a{
	background:url(images/tree_1.png) right -45px no-repeat;
}
.tree_1 ul li h5 a.open, .table_2 th span a.open{
	background:url(images/tree_1.png) right -23px no-repeat;
}
.table_2 th.disable span a, .table_2 span.plus a{
	background-image:url(images/tree_1.png);
}
.tree_1 ul ul li, .tree_1 ul ul ul li{
	background-image:url(images/tree_1_2.png);
}

.tree_1 ul ul li.selected, .tree_1 ul ul ul li.selected{
	background-color:#d9e8fb;
}
.tree_1 ul ul li a{
	background:url(images/tree_1.png) 0px -66px no-repeat;
}
.tree_1 ul ul ul li a{
	color:#4f4f4f;
}
.tree_1 ul ul ul li a:hover{
	color:#2551d0;
}

/*Tree_1 End*/

/*Panel_4*/
.panel_4 header{
	background:url(images/panel_1tit.png) left top no-repeat;
}
.panel_4 header .tit{
	background:url(images/panel_1tit.png) right -86px no-repeat;
}
.panel_4 header .bg{
	background:url(images/panel_1tit.png) left -43px repeat-x;
}
h5.train{
	background:url(images/hgroup_bg.png) left -127px no-repeat;
	color:#001e40;
}
h5.stats{
	background:url(images/hgroup_bg.png) left -157px no-repeat;
	color:#001e40;
}
.panel_4 .con{
	border-top:none;
}
.panel_4 h3{
	color:#000;
}
.panel_4 .con .pie, .panel_7 .con .pie{
	background-image:url(images/pies.png);
}

/*Panel_4 End*/

.r_dash{
	border-right:#a0a4a8 1px dashed;
}

/*Line Color*/
.L_01{
	color:#E70012;
}
.L_02{
	color:#8DC11F;
}
.L_03, .stat_3 dl li.selected span, .stat_3 dl li a:hover span{
	color:#FED700;
}
.L_04{
	color:#471E86;
}
.L_05{
	color:#944B9A;
}
.L_06{
	color:#D30067;
}
.L_07, .stat_3 dl li span{
	color:#ED6D00;
}
.L_08, .hy_button a:hover{
	color:#0194D9;
}
.L_09{
	color:#7ACDF5;
}
.L_010{
	color:#c7afd3;
}
.L_011{
	color:#871B2B;
}
.L_012{
	color:#10614A;
}
.L_013{
	color:#F4B8D2;
}
.L_014{
	color:#0654A9;
}
.L_015{
	color:#18944C;
}
.L_016{
	color:#91A40C;
}
.L_017{
	color:#97CFAA;
}
.L_018{
	color:#8A0F4E;
}
.L_099{
	color: #019392;
}


.Lbg_01{
      background:#E70012;
}
.Lbg_02{
      background:#8DC11F;
}
.Lbg_03{
      background:#FED700;
}
.Lbg_04{
      background:#471E86;
}
.Lbg_05{
      background:#944B9A;
}
.Lbg_06{
      background:#D30067;
}
.Lbg_07{
      background:#ED6D00;
}

.Lbg_08{
      background:#0194D9;
}

.Lbg_09{
      background:#7ACDF5;
}
.Lbg_010{
      background:#871B2B;
}
.Lbg_011{
      background:#8e2323;
}
.Lbg_012{
	background:#10614A;
}
.Lbg_013{
	background:#F4B8D2;
}
.Lbg_014{
	background:#0654A9;
}
.Lbg_015{
	background:#18944C;
}
.Lbg_016{
	background:#91A40C;
}
.Lbg_017{
	background:#97CFAA;
}
.Lbg_018{
	background:#8A0F4E;
}
.Lbg_099{
	background: #019392;
}

.ys, .table_1 tr:hover .ys{
	background:#efefef;
}
.ht, .table_1 tr:hover .ht{
	background:#d6d6d6;
}
.zf, .table_1 tr:hover .zf{
	background:#b3b3b3;
}

 


.Lb_01{
	border-bottom:#E70012 2px solid;
}
.Lb_02{
	border-bottom:#8DC11F 2px solid;
}
.Lb_03{
	border-bottom:#FED700 2px solid;
}
.Lb_04{
	border-bottom:#471E86 2px solid;
}
.Lb_05{
	border-bottom:#944B9A 2px solid;
}
.Lb_06{
	border-bottom:#D30067 2px solid;
}
.Lb_07{
	border-bottom:#ED6D00 2px solid;
}
.Lb_08{
	border-bottom:#0194D9 2px solid;
}
.Lb_09{
	border-bottom:#7ACDF5 2px solid;
}
.Lb_010{
	border-bottom:#c7afd3 2px solid;
}
.Lb_011{
	border-bottom:#871B2B 2px solid;
}
.Lb_012{
	border-bottom:#10614A 2px solid;
}
.Lb_013{
	border-bottom:#F4B8D2 2px solid;
}
.Lb_014{
	border-bottom:#0654A9 2px solid;
}
.Lb_015{
	border-bottom:#18944C 2px solid;
}
.Lb_016{
	border-bottom:#91A40C 2px solid;
}
.Lb_017{
	border-bottom:#97CFAA 2px solid;
}
.Lb_018{
	border-bottom:#8A0F4E 2px solid;
}
.Lb_099{
	border-bottom: #019392 2px solid;
}

.fieldError {
	border: 1px solid red !important;
	background: #FBFCDA !important;
}
.fieldErrorDate {
	background-image: url("images/icon_1.png");
	background-position: right -718px;
    background-repeat: no-repeat;
	border: 1px solid red !important;
}


/*Line Color End*/


/*Alert_1*/
/*.Alert_1{
	background:url(../images/alert_top.png) left top repeat-x, url(../images/alert_right.png) right top repeat-y, url(../images/alert_bottom.png) left bottom repeat-x, url(../images/alert_left.png) left top repeat-y, #f1f1f1 ;
}CSS3代码，ie8不认
*/
.Alert_1{
	background:#f1f1f1 url(images/alert_top.png) left top repeat-x;
}
.A_2{
	background:url(images/alert_right.png) right top repeat-y;
}
.A_3{
	background:url(images/alert_bottom.png) left bottom repeat-x;
}
.A_4, .A_9{
	background:url(images/alert_left.png) left top repeat-y;
}
.A_5, .A_6, .A_7, .A_8{
	background-image:url(images/alert_conner.png);
}
.Alert_1 h4{
	color:#0d2659;
}
.Alert_1 p b{
	color:#000;
}
.Alert_1 p a{
	color:#2d8bdd;
}
.A_dot li{
	border:#45abc9 1px solid;
	background-image:url(images/alert_dot.jpg);
}


/*Alert_1 ENd*/
/*Total*/
.total .i_em{
      background:url(images/totle_empty.png) left top no-repeat;
}
.total .i_full{
      background:url(images/totle_full.png) -2px top no-repeat;
}
/*Total End*/



/*News_list*/
.News_list{
	background:#fff url(images/hgroup_bg.png) left -190px repeat-x;
	border:#c7c7c7 1px solid;
}
.News_list header{
	border-left:#5a84a7 9px solid;
}
.News_list header h4{
	color:#4e4e4e;
}
.News_list header h5{
	color:#4e4e4e;
}
.News_list dl, .stat_3 dl, .node .b_dash, .panel_6 .b_dash, .panel_4 .b_dash, .b_dash{
	border-bottom:#b6b6b6 1px dashed;
}
.News_list .list dt{
	background:url(images/icon_2.png) -4px -75px no-repeat;
}
.News_list dt a, .News_list dt span.type{
	color:#495e94;
}
.News_list dd, .News_list .pager span, .News_list .pager a:hover{
	color:#9f9f9f;
}
.News_list .pager{
	border-top:#5a84a7 2px solid;
}
.News_list .pager a{
	color:#126ebb;
}

/*.panel_5{
	background:url(../images/panel_5.png) left top no-repeat,url(../images/panel_5.png) left -41px no-repeat,url(../images/panel_5.png) left -82px repeat-x, #fff;
}
*/
.panel_5, .panel_5 .tit, .panel_5 h4{
	background-image:url(images/panel_5.png);
}
.panel_5{
	background-color:#fff;
	border:#c7c7c7 1px solid;
	border-left:none;
}
.panel_5 .con{
	background:#f9f9f9;
	border:#f3f3f3 1px solid;
}

.stat_3 dl li a{
	color:#1d4668;
}
.stat_3 dl li a:hover, .stat_3 dl li.selected a{
	color:#fff;
	background:#7b8ebc;
}

/*News_list End*/

/*Switch*/
.switch_table, .switch_table_1{
	background-image:url(images/switch_table.png);
}

/*Switch End*/


/*Flow*/
/*CSS3语句，ie8不识别.Flow{
	background:url(../images/flow_bg2.png) right top no-repeat, url(../images/flow_bg1.png) left top repeat-x, #fff;
}
*/
.Flow{
	background:url(images/flow_bg1.png) left top repeat-x fixed #fff;
}
.Flow .f_bg{
	background:url(images/flow_bg2.png) right top no-repeat fixed;
}
.Flow .f_bg_fw{
	background:url(images/flow_bg3.png) right top no-repeat fixed;
}
.Flow .logo_1{
	background:url(images/flow_logo.png) left top no-repeat;
}
.Flow .gray_bg{
	/**background:url(images/flow_p1.png) 33px top no-repeat;*/
}
.Flow .gray_bg2{
	/**background:url(images/flow_p1.png) -887px bottom no-repeat;*/
}
.Flow .w_bg{
	background:url(images/flow_logo.png) -238px repeat-y;
}
.Flow .Top{
	/**background:url(images/flow_gray1.png) top repeat-x;*/
	border-bottom:#d3d3d3 1px solid;
	border-top:#d3d3d3 1px solid;
	background-color:rgb(255, 255, 255);
}
.Flow .Bottom{
	/**background:url(images/flow_gray.png) bottom repeat-x;*/
}
.Flow h1{
	color:#ef4801;
	text-shadow:#fff 1px -1px 0;
	/**border-bottom:#b5b9be 2px solid;*/
}
.Flow .Step, .zhaiyao{
	background:#e5e5e5;
}
.Flow .Step ul li, .Flow .Step li dt{
	background-image:url(images/workflow.png);
}
.Flow .footer{
	background:url(images/flow_logo.png) 535px -22px no-repeat;
	border-top:#b5b9be 2px solid;
}

.panel_6 .divH, .panel_6 .divF, .panel_6 .divB, .panel_6 .divT,.panel_6 .more_4 a, .panel_6 ul.button li, .panel_6 .divT_c{
	background-image:url(images/block.png);
}
.panel_6 h5{
	color:#1b467f;
	border-bottom:#adbcea 2px solid;
}
.panel_6 .con{
	border-top:#dbe4fb 1px solid;
}
.panel_6 ul.list li{
	border-bottom:#a9bbc3 1px dashed;
	background:url(images/icon_2.png) left -630px no-repeat;
}
.panel_6 ul.button li a{
	color:#56737f;
}
.panel_6 .stu_1{
	color:#416cb4;
}
/*Flow End*/


/*Table_2*/
.table_2{
	border-left:#e4e4e4 1px solid;
	border-right:#e4e4e4 1px solid;
}
.table_2 th{
	border-top:#0a53af 3px solid;
	background:url(images/flow_thead.png) repeat-x;
}
.table_2 th.disable{
	border-top:#e0e0e0 3px solid;
	background:url(images/flow_thead.png) left -30px repeat-x;
}
.table_2 th.disable h5{
	color:#9c9c9c;
	background:url(images/button_bg.png) left -476px no-repeat;
}
.table_2 th li, .table_2 th li a, .table_2 th li a span{
	background-image:url(images/tabs_4.png);
}
.table_2 th li.selected a{
	color:#0a53af;
}
.table_2 tr td{
	/*border-bottom:#e4e4e4 1px solid;*/
	border:#4A4A4A 1px solid;
	background:#fff;
	color:#000;
}

.table_suggest tr td{
	/*border-bottom:#e4e4e4 1px solid;*/
	border:#4A4A4A 0px solid;
	background:#fff;
	color:#000;
}

.table_2 td.suggest{
	border-top:#4A4A4A 0px solid;
	border-left:#4A4A4A 0px solid;
	border-bottom:#4A4A4A 0px solid;
}

.table_2 tr.disable, aside li.hlight{
	background:#fafafa;
}
.table_2 tr.disable td{
	color:#5c5c5c;
}
/*Table_2 End*/


/*Transparent*/
.transparent{
	background:#000;
}
.f_window{
	background:#fff;
	border:#e0e0e0 6px solid;
}
.f_window h3{
	color:#306291;
	background:#f5f5f5;
	border-bottom:#e0e0e0 1px solid;
}
.f_window .con{
	border-bottom:#b3b7bc 1px dashed;
	color:#000;
}
.f_window .con a{
	color:#4d77b0;
}
.f_window .con .pl18{
	color:#9d9d9d;
}

/*Transparent End*/
/*Node*/
.node{
	background:url(images/tabs_1_2.png) right -150px no-repeat;
	border-bottom:#d5d5d5 1px solid;
}
.node b{
	border-bottom:#0a53af 2px solid;
}
.node .con a:hover, .node .con a:hover{
	color:#83C2E4;
}
.node .con i, .panel_6 .stu_2{
	color:#c56464;
}
/*Node End*/




/*Login*/
.Login{
	background:url(images/LOGIN_bg.png) left top repeat-x #bfbfbf;
}
.Login .mainDiv, .Login .logo{
	background-image:url(images/LOGIN.png);
}
.Login .loginBox{
	background-image:url(images/login_line.png);
}
.Login hgroup h3{
	color:#000;
}
.Login hgroup h6{
	color:#afafaf;
}
.Login .loginBox .field label{
	color:#333;
}
.Login .cr{
	color:#6a6a6a;
}
/*Login End*/


/*Panel_7*/
.panel_7 hgroup{
}
.panel_7, .panel_7 hgroup, .panel_7 .hgroup, .panel_7 .p_right, .panel_7 .text, .panel_7 .setting, .panel_7 .conner{
	background-image:url(images/panel_7.png);
}
.panel_7 .con{
	background-color:#fff;
	border-top:#e6e6e6 1px solid;
}
.panel_7 .con li span, .Divider p, .table_3 li span, .panel_4 .con li span, .panel_8 .con li dt{
	color:#979797;
}
.panel_7 .con li h6, .panel_4 .con li h6{
	color:#9e9c9b;
}
.panel_7 p, .panel_4 p.gk, .References{
	background:#fafafa;
	border:#b7b7b7 1px dotted;
	color:#a3a3a3;
}

/*Panel_7 End*/


/*ItemFlow*/
.ItemFlow, .Divider ul{
	background-image:url(images/ItemFlow_bg2.png);
}
.ItemFlow .gray_bg{
	background:url(images/itemflow_bg1.png) left top no-repeat;
}
.ItemFlow .gray_bg .pie .tit{
	background-image:url(images/ItemFlow_tit.png);
}
.ItemFlow .blueBg{
	background:url(images/itemflow_bg1.png) left -100px repeat-x;
}

/*Divider*/
.Divider, .Divider .bottom, .Divider .con{
	background-image:url(images/Divider_bg.png);
}
.Divider li, .Divider li.fst, .Divider li.fin, .Divider .arrow, .References .arrow{
	background-image:url(images/Divider.png);
	text-shadow:#fff 1px 1px 1px;
}
.Divider li.disable, .Divider li.po4, .Divider li.fin_dis{
	color:#9c9c9c;
	text-shadow:#fff 1px 0 1px;
}
.Divider li a, .zc span strong{
	color:#000;
}
/*Divider End*/
/*ItemFlow End*/
.l_dash{
	border-left:#a0a4a8 1px dashed;
}
.border_gary, .border_gary_mini, .panel_3 .con .xx img, .border_gary_mini_2{
	border:#cecece 1px solid;
}

/*panel_8*/
.panel_8 .tit span{
	color:#a1a1a1;
}
.block, .block_selected, .corner, .categories a{
	background-image:url(images/panel_8.png);
}
.block{
	background-color:#efeeee;
	border:#fbfaf8 1px solid;
}
.block_selected, .block:hover{
	background-color:#eae8e3;
	border:#f7f7f6 1px solid;
}
.panel_8 .block_selected h1, .panel_8 .block:hover h1, .panel_8 .block_selected b, .panel_8 .block:hover b, .panel_1 h1, .panel_4 h1, .panel_7 h1, .panel_4 h3{
	color:#016fbb;
}
.panel_8 .block_selected td span, .panel_8 .block:hover td span, .panel_8 .block_selected td h6, .panel_8 .block:hover td h6{
	color:#545150;
}
.panel_8 .gray_border, .gray_border{
	border-bottom:#d4d2cd 1px solid;
	border-right:#d4d2cd 1px solid;
}
.panel_8 .b_bor, .b_bor{
	border-bottom:#e2e0dd 1px solid;
}
.panel_8 .t_bor, .t_bor{
	border-top:#f9f8f4 1px solid;
}

.panel_8 .categories a, .categories a{
	color:#737e83;
}
.panel_8 li.counter span.c1, .panel_8 li.counter span.c2, .panel_8 li.counter span.c3, .tabs_3 ul, .tabs_3 li, .tabs_3 li a, .hy_button, .hy_button a{
	background-image:url(images/icon_3.png);
}
.r_bor{
	border-right:#e2e0dd 1px solid;
}
.l_bor{
	border-left:#f9f8f4 1px solid;
}
.attention{
	background:url(images/attention.png) right 3px no-repeat;
}

.jttt, .jttt_l, .jttt_r{
	background-image:url(images/jttt.png);
}


/*20120820*/
.mail_1{
	background-image: url(images/mail.png);
}


/*20120917*/
.close_2, .open_2{
	background-image:url(images/arrow.png);
}

/*20121023*/
.js_bg{
	background-color:#d7d7d7;
	background-image:url(images/js_bg.png);
}
.js_bg .con{
	background-image:url(images/js_bg.png);
}
/*zccenter*/
.zc .num, .panel_4 .con .zc span.num, .panel_8 .con li h2, .panel_4 .con dd h6{
	color:#fff;
}
.zc{
	background:url(images/list_1.png) 98% bottom no-repeat;
}
.panel_8 .con li dd{
	border-left:#c9c7c2 1px solid;
}

/*xxcenter*/
h5.xx{
	background:#9dbadb;
	color:#000;
}
h5.xx span{
	background:#fff;
}
.panel_3 .liveuser{
	background:#f8f8f8 url(images/liveuser.png) right bottom no-repeat;
}
.panel_3 .liveuser h4{
	color:#2094de;
	text-shadow:#fff 1px 1px 0;
}
.panel_3 .liveuser ul{
	background:url(images/liveuser_num.png) left 6px no-repeat;
}
.panel_3 .liveuser .ID{
	color:#174790;
}
.panel_3 .QAA h4{
	color:#375e95;
}

/* CSS Version Default */
/* Version: 1.3.1  || Date: 2012-08-20 || Author: Cathy */
/* Cascading Style Sheets Level 2 Revision 1 */

body {
  font:12px/1.5 '宋体','Helvetica Neue', 'Microsoft YaHei', Arial, 'Liberation Sans', FreeSans, sans-serif;
}
pre,
code {
  font-family: '宋体','DejaVu Sans Mono', Menlo, Consolas, monospace;
}

hr {
  border: 0 #ccc solid;
  border-top-width: 1px;
  clear: both;
  height: 0;
}

/* `Headings
----------------------------------------------------------------------------------------------------*/

h1 {
  font-size: 26px;
}

h2 {
  font-size: 20px;
}

h3 {
  font-size: 16px;
}

h4 {
  font-size: 15px;
}

h5 {
  font-size: 14px;
}

h6 {
  font-size: 11px;
}

/* `Spacing
----------------------------------------------------------------------------------------------------*/

ol {
  list-style: decimal;
}

ul, li{
  list-style: none;
}

li {
  margin:0;
}

/*p,
dl,
hr,
h1,
h2,
h3,
h4,
h5,
h6,
ol,
ul,
pre,
table,
address,
fieldset,
figure {
  margin-bottom: 20px;
}

*/


/* `XHTML, HTML4, HTML5 Reset
----------------------------------------------------------------------------------------------------*/

a,
abbr,
acronym,
address,
applet,
article,
/*aside,*/
audio,
b,
big,
blockquote,
body,
canvas,
caption,
center,
cite,
code,
dd,
del,
details,
dfn,
dialog,
div,
dl,
dt,
em,
embed,
fieldset,
figcaption,
figure,
font,
footer,
form,
h1,
h2,
h3,
h4,
h5,
h6,
header,
hgroup,
hr,
html,
i,
iframe,
img,
ins,
kbd,
/*label,*/
legend,
li,
mark,
menu,
meter,
nav,
object,
ol,
output,
p,
pre,
progress,
q,
rp,
rt,
ruby,
s,
samp,
section,
small,
span,
strike,
/*strong,*/
sub,
summary,
sup,
/*table,
tbody,
td,
tfoot,
th,
thead,
time,
tr,
tt,
*/u,
ul,
var,
video,
xmp {
  border: 0;
  margin: 0;
  padding: 0;
  text-decoration:none;
/*
  Override the default (display: inline) for
  browsers that do not recognize HTML5 tags.

  IE8 (and lower) requires a shiv:
  http://ejohn.org/blog/html5-shiv
*/
  display: block;
}

html,
body {
  height: 100%;
}


b,
strong {
/*
  Makes browsers agree.
  IE + Opera = font-weight: bold.
  Gecko + WebKit = font-weight: bolder.
*/
  font-weight: bold;
}

img {
  color: transparent;
  font-size: 0;
  vertical-align: middle;
/*
  For IE.
  http://css-tricks.com/ie-fix-bicubic-scaling-for-images
*/
  -ms-interpolation-mode: bicubic;
}

ol,
ul {
  list-style: none;
}

li {
/*
  For IE6 + IE7:

  "display: list-item" keeps bullets from
  disappearing if hasLayout is triggered.
*/
  display: list-item;
}

table {
  border-collapse: collapse;
  border-spacing: 0;
}

th,
td,
caption {
  font-weight: normal;
  vertical-align: top;
  text-align: left;
}

q {
  quotes: none;
}

q:before,
q:after {
  content: '';
  content: none;
}

sub,
sup,
small {
  font-size: 75%;
}

sub,
sup {
  line-height: 0;
  position: relative;
  vertical-align: baseline;
}

sub {
  bottom: -0.25em;
}

sup {
  top: -0.5em;
}

svg {
/*
  For IE9. Without, occasionally draws shapes
  outside the boundaries of <svg> rectangle.
*/
  overflow: hidden;
}




/* http://sonspring.com/journal/clearing-floats */

.clear {
  clear: both;
  display: block;
  overflow: hidden;
  visibility: hidden;
  width: 0;
  height: 0;
}

/* http://www.yuiblog.com/blog/2010/09/27/clearfix-reloaded-overflowhidden-demystified */

.clearfix:before,
.clearfix:after {
  content: '.';
  display: block;
  overflow: hidden;
  visibility: hidden;
  font-size: 0;
  line-height: 0;
  width: 0;
  height: 0;
}

.clearfix:after{
  clear: both;
}

/*
  The following zoom:1 rule is specifically for IE6 + IE7.
  Move to separate stylesheet if invalid CSS is a problem.
*/

.clearfix {
  zoom: 1;
}

.wrapfix::after {
	height: 0px; clear: both; display: block; visibility: hidden; content: ".";
}
.wrapfix {
	display: inline-table;
}
* html .wrapfix {
	height: 0%;
}
.wrapfix {
	display: block;
}





/*position*/
.fl{
	float:left;
}
.fr{
	float:right;
}

.t_r{
	text-align:right;
}
.t_v{
	vertical-align: middle;
}
.t_c{
	text-align:center;
}
.mb10{
	margin-bottom:10px;
}
.m10{
	margin:10px;
}
.mlr10{
	margin-left:10px;
	margin-right:10px;
}
.m6{
	margin:6px;
}
.mr_5{
	margin-right:5px;
}
.ml_5{
	margin-left:5px;
}
.mr5{
	margin:0 5px;}
.p10{
	padding:10px;}
.p8{
	padding:8px;}
.plr8{
	padding:0 8px;}
.pt5{
	padding-top:5px;}
.pt10{
	padding-top:10px;}
.pt16{
	padding-top:16px;}
.pt24{
	padding-top:24px;}
.ml210{
	margin-left:210px;
}
.C_fff{
	color:#fff;
}
.mb1{
	margin-bottom:1px;
}
.pl18{
	padding-left:18px;
}
.pt45{
	padding-top:45px;
}
.pl8{
	padding-left:8px;
}
.p0, .p0 td{
	padding:0;}

a{
	text-decoration:none;
	color:#605f5f;
}
a:hover{
	color:#0a438d;
}
.d_il{
	display:inline;
}
.mr8{
	margin-right:8px;
}
.nwarp{
	white-space:nowrap;
}
.bor{
	border:#c7c7c7 1px solid;
}
.w1280{
	width:1280px;
	margin:auto;
}
.w1280 .panel_7{
	width:620px;
	margin:10px;
}
.w1280 .panel_7 .Hcharts, .w360{
	width:360px;
	padding:1px;
}

.w1024{
	width:1002px;
	margin:auto;
}
.w1024 .panel_7{
	width:490px;
	margin:5px;
}
.w1024 .panel_7 .Hcharts{
	width:290px;
	padding:1px;
}
.mw1002{
	min-width:1001px;
}
.mw1070{
	min-width:1070px;
}
.mw1280{
	min-width:1280px;
}
.w700{
	width:490px;
}
.w49p{
	width:47%;
}
.w50p{
	width:50%;
	*width:49.8%;
}
.w55p{
	width:55%}
.w47p{
	width:49.4%;
}
.w25p{
	width:25%;
	*width:24.95%;
}
.w45p{
	width:44%;
}
.w48p{
	width:48%;
}

.w70p{
	width:70%;
}
.w28p{
	width:28%;
	*width:27.8%
}
.w33p{
	width:33%;
}
.w34p{
	width:34%;
}
.w27p{
	width:27%;
	*width:26.9%;}
.w23p{
	width:23%;}
.h390{
	height:378px;
	*height:424px;
}
.h195{
	height:187px;
	*height:210.5px;
}
.h280{
	height:280px;
}
.h175{
	height:175px;
}
.h56{
	height:56px;
}
.h40{
	height:40px;}
.w56{
	width:56px;
}
.h125{
	height:125px;}

.bd, .bd td{
  font-weight: bold;
}
.mauto{
	margin:0 auto;
}
.lh{
	line-height:30px;
}
.table_2 tr td.b0{
	border : 0 none;
}

/*suggest*/
.latestSuggest{
	border-bottom:#4A4A4A 1px dotted;
}

	/* CSS Version Default */
/* Version: 1.0  || Date: 2012-03-27 || Author: Cathy */
/* Cascading Style Sheets Level 2 Revision 1 */
.tr_height {
  height: 100px;
}


.input_none {
  width: 30px;
}

.input_tiny {
  width: 50px;
}

.input_small {
  width: 100px;
}

.input_medium {
  width: 127px;
}

.input_large {
  width: 160px;
}

.input_xlarge {
  width: 250px;
}

.input_xxlarge {
  width: 300px;
}

.input_full {
  width: 100%;
}

.input_full_wrap {
  display: block;
  padding-right: 8px;
}

::-moz-focus-inner {
  border: 0;
  padding: 0;
}

input[type="search"]::-webkit-search-decoration {
  display: none;
}

input[type="radio"],
input[type="checkbox"] {
  position: relative;
  vertical-align: top;
  top: 3px;
  top: 0 \0;
  *top: -3px;
}

@media (-webkit-min-device-pixel-ratio: 1) and (max-device-width: 1024px) {
  input[type="radio"],
  input[type="checkbox"] {
    vertical-align: baseline;
    top: 2px;
  }
}
@media (-webkit-min-device-pixel-ratio: 1) and (max-device-width: 480px) {
  input[type="radio"],
  input[type="checkbox"] {
    vertical-align: baseline;
    top: 0;
  }
}
@media (-webkit-min-device-pixel-ratio: 2) and (max-device-width: 480px) {
  input[type="radio"],
  input[type="checkbox"] {
    vertical-align: baseline;
    top: 0;
  }
}
input,
button,
select,
textarea {
  margin: 0;
  vertical-align: middle;
}

button:focus,
input:focus,
select:focus,
textarea:focus {
  -moz-box-shadow: #0066ff 0 0 7px 0;
  -webkit-box-shadow: #0066ff 0 0 7px 0;
  -o-box-shadow: #0066ff 0 0 7px 0;
  box-shadow: #0066ff 0 0 7px 0;
  z-index: 1;
}

input[type="file"]:focus, input[type="file"]:active,
input[type="radio"]:focus,
input[type="radio"]:active,
input[type="checkbox"]:focus,
input[type="checkbox"]:active {
  -moz-box-shadow: none;
  -webkit-box-shadow: none;
  -o-box-shadow: none;
  box-shadow: none;
}

button,
input[type="reset"],
input[type="submit"],
input[type="button"] {
  -webkit-appearance: none;
  -moz-border-radius: 4px;
  -webkit-border-radius: 4px;
  -o-border-radius: 4px;
  -ms-border-radius: 4px;
  -khtml-border-radius: 4px;
  border-radius: 4px;
  -moz-background-clip: padding;
  -webkit-background-clip: padding;
  -o-background-clip: padding-box;
  -ms-background-clip: padding-box;
  -khtml-background-clip: padding-box;
  background-clip: padding-box;
  background: #dddddd url('default/images/button.png') left top repeat-x;
  background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #ffffff), color-stop(100%, #dddddd));
  background-image: -webkit-linear-gradient(#ffffff, #dddddd);
  background-image: -moz-linear-gradient(#ffffff, #dddddd);
  background-image: -o-linear-gradient(#ffffff, #dddddd);
  background-image: -ms-linear-gradient(#ffffff, #dddddd);
  background-image: linear-gradient(#ffffff, #dddddd);
  border: 1px solid;
  border-color: #dddddd #bbbbbb #999999;
  cursor: pointer;
  color: #333333;
  font: bold 12px/1.3 "Helvetica Neue", Arial, "Liberation Sans", FreeSans, sans-serif;
  outline: 0;
  overflow: visible;
  padding: 3px 10px;
  text-shadow: white 0 1px 1px;
  width: auto;
  *padding-top: 2px;
  *padding-bottom: 0;
}
button:hover,
input[type="reset"]:hover,
input[type="submit"]:hover,
input[type="button"]:hover {
  background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #ffffff), color-stop(1px, #eeeeee), color-stop(100%, #cccccc));
  background-image: -webkit-linear-gradient(#ffffff, #eeeeee 1px, #cccccc);
  background-image: -moz-linear-gradient(#ffffff, #eeeeee 1px, #cccccc);
  background-image: -o-linear-gradient(#ffffff, #eeeeee 1px, #cccccc);
  background-image: -ms-linear-gradient(#ffffff, #eeeeee 1px, #cccccc);
  background-image: linear-gradient(#ffffff, #eeeeee 1px, #cccccc);
}
button:active,
input[type="reset"]:active,
input[type="submit"]:active,
input[type="button"]:active {
  background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #ffffff), color-stop(1px, #dddddd), color-stop(100%, #eeeeee));
  background-image: -webkit-linear-gradient(#ffffff, #dddddd 1px, #eeeeee);
  background-image: -moz-linear-gradient(#ffffff, #dddddd 1px, #eeeeee);
  background-image: -o-linear-gradient(#ffffff, #dddddd 1px, #eeeeee);
  background-image: -ms-linear-gradient(#ffffff, #dddddd 1px, #eeeeee);
  background-image: linear-gradient(#ffffff, #dddddd 1px, #eeeeee);
  -moz-box-shadow: inset rgba(0, 0, 0, 0.25) 0 1px 2px 0;
  -webkit-box-shadow: inset rgba(0, 0, 0, 0.25) 0 1px 2px 0;
  -o-box-shadow: inset rgba(0, 0, 0, 0.25) 0 1px 2px 0;
  box-shadow: inset rgba(0, 0, 0, 0.25) 0 1px 2px 0;
  border-color: #999999 #bbbbbb #dddddd;
}

button {
  *padding-top: 1px;
  *padding-bottom: 1px;
}

textarea,
select,
input[type="date"],
input[type="datetime"],
input[type="datetime-local"],
input[type="email"],
input[type="month"],
input[type="number"],
input[type="password"],
input[type="search"],
input[type="tel"],
input[type="text"],
input[type="time"],
input[type="url"],
input[type="week"] {
  -moz-box-sizing: border-box;
  -webkit-box-sizing: border-box;
  -ms-box-sizing: border-box;
  box-sizing: border-box;
  -moz-background-clip: padding;
  -webkit-background-clip: padding;
  -o-background-clip: padding-box;
  -ms-background-clip: padding-box;
  -khtml-background-clip: padding-box;
  background-clip: padding-box;
  -moz-border-radius: 0;
  -webkit-border-radius: 0;
  -o-border-radius: 0;
  -ms-border-radius: 0;
  -khtml-border-radius: 0;
  border-radius: 0;
  -webkit-appearance: none;
  background-color: white;
  border: 1px solid;
  border-color: #848484 #c1c1c1 #e1e1e1;
  color: black;
  outline: 0;
  padding: 2px 3px;
  text-align: left;
  font-size: 13px;
  font-family: Arial, "Liberation Sans", FreeSans, sans-serif;
  height: 1.8em;
  *padding-top: 2px;
  *padding-bottom: 1px;
  *height: auto;
}
textarea[disabled],
select[disabled],
input[type="date"][disabled],
input[type="datetime"][disabled],
input[type="datetime-local"][disabled],
input[type="email"][disabled],
input[type="month"][disabled],
input[type="number"][disabled],
input[type="password"][disabled],
input[type="search"][disabled],
input[type="tel"][disabled],
input[type="text"][disabled],
input[type="time"][disabled],
input[type="url"][disabled],
input[type="week"][disabled] {
  background-color: #eeeeee;
}

button[disabled],
input[disabled],
select[disabled],
select[disabled] option,
select[disabled] optgroup,
textarea[disabled] {
  -moz-box-shadow: none;
  -webkit-box-shadow: none;
  -o-box-shadow: none;
  box-shadow: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
  color: #888888;
  cursor: default;
}

::-webkit-input-placeholder {
  color: #888888;
}

input:-moz-placeholder,
textarea:-moz-placeholder {
  color: #888888;
}

input.placeholder_text,
textarea.placeholder_text {
  color: #888888;
}

:invalid {
  -moz-box-shadow: none;
  -webkit-box-shadow: none;
  -o-box-shadow: none;
  box-shadow: none;
}

textarea,
select[size],
select[multiple] {
  height: auto;
}

@media (-webkit-min-device-pixel-ratio: 0) {
  select {
    background-image: url('default/images/select_arrow.gif');
    background-repeat: no-repeat;
    background-position: right center;
    padding-right: 20px;
  }

  select[size],
  select[multiple] {
    background-image: none;
    padding: 0;
  }

  ::-webkit-validation-bubble-message {
    -webkit-box-shadow: rgba(0, 0, 0, 0.5) 0 0 5px 0;
    box-shadow: rgba(0, 0, 0, 0.5) 0 0 5px 0;
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0, #666666), color-stop(1, black));
    border: 1px solid;
    border-color: #747474 #5e5e5e #4f4f4f;
    color: white;
    font: 13px/17px "Lucida Grande", Arial, "Liberation Sans", FreeSans, sans-serif;
    overflow: hidden;
    padding: 15px 15px 17px;
    text-shadow: black 0 0 1px;
    height: 16px;
  }

  ::-webkit-validation-bubble-arrow,
  ::-webkit-validation-bubble-top-outer-arrow,
  ::-webkit-validation-bubble-top-inner-arrow {
    -webkit-box-shadow: none;
    box-shadow: none;
    background: #666666;
    border: 0;
  }
}
textarea {
  min-height: 40px;
  overflow: auto;
  resize: vertical;
  width: 100%;
}

optgroup {
  color: black;
  font-style: normal;
  font-weight: normal;
}

.ie6_button,
* html button {
  background: #dddddd url('default/images/tabs_1_on.png') left -597px repeat-x;
  border: 1px solid;
  border-color: #dddddd #bbbbbb #999999;
  cursor: pointer;
  color: #333333;
  font: bold 12px/1.2 Arial, sans-serif;
  padding: 2px 10px 0px;
  overflow: visible;
  width: auto;
}

* html button {
  padding-top: 1px;
  padding-bottom: 1px;
}

.ie6_input,
* html textarea,
* html select {
  background: white;
  border: 1px solid;
  border-color: #848484 #c1c1c1 #e1e1e1;
  color: black;
  padding: 2px 3px 1px;
  font-size: 13px;
  font-family: Arial, sans-serif;
  vertical-align: top;
}

* html select {
  margin-top: 1px;
}

.placeholder_text,
.ie6_input_disabled,
.ie6_button_disabled {
  color: #888888;
}

.ie6_input_disabled {
  background: #eeeeee;
}





.gen_search input[type="search"]{
	background-image:url(default/images/icon_1.png);
	background-position:0px -558px;
	background-repeat:no-repeat;
	padding-left:20px;
	height:25px;
}
/*.gen_search input:focus[type="search"] {
  -moz-box-shadow: none;
  -webkit-box-shadow: none;
  -o-box-shadow: none;
  box-shadow: none;
}*/
.gen_search button, .gen_search input[type="button"], .gen_search input[type="submit"]{
	background:url(default/images/g_s_button.png);
	width:51px;
	height:25px;
	border:none;
	-moz-border-radius: 0px;
	-webkit-border-radius: 0px;
	-o-border-radius:0px;
	-ms-border-radius: 0px;
	-khtml-border-radius: 0px;
	border-radius: 0px;
	color:#fff;
	text-shadow:1px 1px 3px #44748d;
}

.table_1 .pager button,
.table_1 .pager input[type="reset"],
.table_1 .pager input[type="submit"],
.table_1 .pager input[type="button"] {
	height:16px;
	width:18px;
	background:#7caad5;
	border:none;
	line-height:16px;
	padding:0;
}
input[type="number"]{
	height:17px;
	border:#7caad5 1px solid;
	line-height:12px;
	font-size:11px;
}


/*button.new, input.new{
	background:url(../images/button_bg.png) 5px 5px no-repeat;
	padding-left:20px;
}

.InputBtn { background: #eee no-repeat 2px 3px;}
.InputBtn:hover { background: #f2f2f2;}
.add {background-image: ; padding-left:}

<input class="InputBtn Del"
*/
.date{
	background-image:url(default/images/icon_1.png);
	background-position:right -718px;
	background-repeat:no-repeat;
	padding-right:20px;
}


.Login .loginBox button,
.Login .loginBox input[type="submit"],
.Login .loginBox input[type="reset"],
.Login .loginBox input[type="button"]{
	width:100px;
	height:28px;
	color:#fff;
	background:url(default/images/LOGIN.png) left top no-repeat;
	text-shadow:#333 0 0 1px;
	margin:5px;
	font-size:14px;
}
.Login .loginBox button:hover,
.Login .loginBox input[type="submit"]:hover,
.Login .loginBox input[type="reset"]:hover,
.Login .loginBox input[type="button"]:hover{
	background-position:-120px top;
}
/*.Login .loginBox button,
.Login .loginBox input[type="submit"],
.Login .loginBox input[type="reset"],
.Login .loginBox input[type="button"]{
	width:100px;
	height:28px;
	color:#000;
	background:url(default/images/LOGIN.png) left -30px no-repeat;
	text-shadow:#f1f1f1 0 0 1px;
	margin:5px;
	font-size:14px;
}
.Login .loginBox button:hover,
.Login .loginBox input[type="submit"]:hover,
.Login .loginBox input[type="reset"]:hover,
.Login .loginBox input[type="button"]:hover{
	background-position:-120px -30px;
}*/
input[type="text"].b_n{
	border-top:none;
	border-left:none;
	border-right:none;
}


	
	.redMark{color:red;display:inline;}
	</style>
	<style>
		@media print{
		.print{display:block;}
		.nprint{display:none;}
		}
	</style>
	<script src="<%=path %>/receive/js/contextpath.js"></script>
	<script src="<%=path %>/receive/js/html5.js"></script>   
	<!--<script src="<%=path %>/receive/js/jquery.formalize.js"></script>  -->
	<script src="<%=path %>/receive/js/jquery-1.10.2.min.js"></script>
	<script src="<%=path %>/receive/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path %>/receive/js/jquery-print/jquery.jqprint-0.3.js"></script>
	
	<script type="text/javascript">
	$(function(){
		$("#printBtn").click(function(){
			$("#main").jqprint();  
			//window.print(); 
		});
		
		$("#closeBtn").click(function(){
			window.opener=null;
			window.open("","_self");
			window.close();
		});
		
		$(".noleader").show();
		$(".showDetail").hide();
		
	})	;
	
	
	
	</script>
	
</head>
<body id="printBody" class="Flow">

<!--startprint-->
	<div class="f_bg" >
    	<div class="logo_1"></div>
        <div class="gray_bg">
        	<div class="gray_bg2" id="main">
            	<div class="w_bg">
                	<div class="Bottom">
                    	<div class="Top">
                        	<h1 class="t_c"><b style="display:inline;">上海申通地铁集团有限公司</b><br><b>收文处理单</b></h1>

                        	<div class="mb10" >
	                          <table id="form_detail_zone" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
									
								  	<tbody>
									
									<tr class="content7">
	                                    <td style="width:15%;" colspan="1" class="b0">收文编号：</td>
	                            	    <td class="b0" colspan="5">
	                            	   	<s:property value="vo.swId"/>
	                            	   	</td>
	                            	    <td colspan="1" class="b0">收文日期：</td>
	                            	    <td class="b0" colspan="5">
	                            	    <s:property value="vo.swDate"/>
	                            	    </td>
	                          	    </tr>
								
									<tr class="content7">
	                                    <td colspan="1" class="lableTd t_v">来文单位：</td>
	                            	    <td colspan="5">
	                            	   	 <s:property value="vo.swUnit"/>
	                            	    </td>
	                            	    <td colspan="1" class="lableTd t_v">文件日期：</td>
	                            	    <td colspan="5"><s:property value="vo.fileDate"/></td>
	                                </tr>
	                                
	                                <tr class="content7">
	                                    <td colspan="1" class="lableTd t_v">密级：</td>
	                            	    <td colspan="1">
	                            	    <s:property value="vo.secretClass"/>
	                            	    </td>
	                            	    <td colspan="1" class="lableTd t_v">份数：</td>
	                            	    <td colspan="1">
	                            	    <s:property value="vo.num"/>
	                            	    </td>
	                            	    <td colspan="1" class="lableTd t_v">缓急：</td>
	                            	    <td colspan="1">
	                            	    <s:property value="vo.priorities"/>
	                            	    </td>    
	                            	    <td colspan="1" class="lableTd t_v">文件字号：</td>
	                            	    <td colspan="5">
	                            	    <s:property value="vo.filezh"/>
	                            	    </td>
	                                </tr>
	                          	    
	                          	  
	                          	    
                                  	<tr class="content6">
	                                    <td colspan="1" class="lableTd t_v">文件标题：</td>
	                                    <td colspan="11" colspan="3" class="content6">
	                                    <s:property value="vo.title"  escape="0"/>
	                                    </td>
                                  	</tr>
                              
	                          	  <tr class="content7"  height="150">
										<td colspan="12">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
										<tr>
										<td>
										拟办意见：
										</td>
										</tr>
										<tr>
										<td>
										<s:action name="simulateRounds" namespace="/receive-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'拟办人', 
											'拟办意见汇总',
											'拟办领导批示',
											'拟办建议',
											'拟办办结'}">
										</s:param>
										</s:action>
										</td>
										</tr>
										</table>
										</td>
									</tr>
									<tr class="content7"  height="250">
										<td colspan="12">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
										<tr>
										<td>
										领导批示：
										</td>
										</tr>
										<tr>
										<td>
										<s:action name="leaderRounds" namespace="/receive-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'领导', 
											'批示领导'
											}">
										</s:param>
										</s:action>
										</td>
										</tr>
										</table>
										</td>
									</tr>
									
									<tr class="content7"  height="250">
										<td colspan="12">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
										<tr>
										<td>
										部门意见：
										</td>
										</tr>
										<tr>
										<td>
										<s:action name="deptRounds" namespace="/receive-approve" executeResult="true">
										<s:param name="pname" value="params.processParam['pname']"></s:param>
										<s:param name="pincident" value="params.processParam['pincident']"></s:param>
										<s:param name="stepname" 
											value="new java.lang.String[]{
											'部门接受人工作分发',
											'部门业务人员处理',
											'部门领导审核'
											}">
										</s:param>
										</s:action>
										</td>
										</tr>
										</table>
										</td>
									</tr>
									
									<tr class="content7"  height="150">
										
										<td colspan="12">
										<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table_suggest">
											<tr>
											<td>
											办理结果：
											</td>
											</tr>
											<tr>
											<td>
											<s:action name="finish" namespace="/receive-approve" executeResult="true">
											<s:param name="pname" value="params.processParam['pname']"></s:param>
											<s:param name="pincident" value="params.processParam['pincident']"></s:param>
											<s:param name="stepname" 
												value="new java.lang.String[]{
												'办结'
												}">
											</s:param>
											</s:action>
											</td>
											</tr>
											<tr>
											<td>
											<s:action name="secretary" namespace="/receive-approve" executeResult="true">
											<s:param name="pname" value="params.processParam['pname']"></s:param>
											<s:param name="pincident" value="params.processParam['pincident']"></s:param>
											<s:param name="stepname" 
												value="new java.lang.String[]{
												'秘书',
												'批示秘书'
												}">
											</s:param>
											</s:action>
											</td>
											</tr>
											<tr>
											<td>
											<s:action name="dealResult" namespace="/oa" executeResult="true">
											<s:param name="pname" value="params.processParam['pname']"></s:param>
											<s:param name="pincident" value="params.processParam['pincident']"></s:param>
											<s:param name="type">DocRe_notice</s:param>
											</s:action>
											</td>
											</tr>
										</table>
										</td>
									
									</tbody>
                          	  	</table>
                        	</div>
                        	
                        	<div>
								注：<br>1.纸质影印件仅供参考，实际内容以综合业务协同平台网上为准。<br>2.纸质归档件需加盖本单位电子文件打印专用章。
							</div>
							
	                      <!--endprint-->
	                       
							
							
						
						</div>
                    </div>
                </div>
            </div>
             <div class="mb10 t_c">
				<input type="button" id="printBtn" value="打印"/>&nbsp;
				<input type="button" id="closeBtn" value="关闭"/>&nbsp;				
			</div>	
        </div>
       
 	</div>
</div>	
		 
</body>
</html>