package com.wonders.util;

import jxl.JXLException;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.util.HtmlUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class FileUtil {
	protected static final Log logger = LogFactory.getLog(FileUtil.class);

	public static boolean moveFile(String srcFile, String destPath) {
		boolean r = false;
		File file = new File(srcFile);
		if (file.exists() && file.isFile()) {
			File dir = new File(destPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			r = file.renameTo(new File(dir, file.getName()));
		}
		return r;
	}

	public static void moveDirFiles(String srcDirStr, String destDirStr,
			boolean isDeleteSrcDir) {
		File srcDir = new File(srcDirStr);
		if (srcDir.exists() && srcDir.isDirectory()) {
			File[] files = srcDir.listFiles();
			if (files != null && files.length > 0) {
				File destDir = new File(destDirStr);
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					file.renameTo(new File(destDir, file.getName()));
				}
			}
			if (isDeleteSrcDir) {
				srcDir.delete();
			}
		}
	}

	/**
	 * 生成excel数据流
	 * 
	 * @param os
	 * @param title
	 * @param head
	 * @param data
	 * @throws IOException
	 * @throws JXLException
	 */

	@SuppressWarnings("rawtypes")
	public static void createXls(OutputStream os, String title, List head,
			List data) throws IOException, JXLException {
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet sheet = wwb.createSheet("sheet1", 0);

		// 合并标题单元格

		sheet.mergeCells(0, 0, head.size() - 1, 0);
		// 设置标题行高
		sheet.setRowView(0, 30);
		sheet.setRowView(1, 18);

		// title
		WritableFont wfTitle = new WritableFont(WritableFont.ARIAL, 18,
				WritableFont.BOLD);
		WritableCellFormat wcfTitle = new WritableCellFormat(wfTitle);
		wcfTitle.setAlignment(Alignment.CENTRE);
		Label labTitle = new Label(0, 0, title, wcfTitle);
		sheet.addCell(labTitle);

		// head
		WritableFont wfHead = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD);
		WritableCellFormat wcfHead = new WritableCellFormat(wfHead);
		for (int i = 0; i < head.size(); i++) {
			Label labHead = new Label(i, 1, (String) head.get(i), wcfHead);
			sheet.addCell(labHead);
		}

		// data
		if (data != null && data.size() > 0) {
			if (data.get(0) instanceof Map) {
				// list中是Map
				for (int i = 0; i < data.size(); i++) {
					Map m = (Map) data.get(i);
					Set s = m.keySet();
					Iterator itr = s.iterator();
					for (int j = 0; itr.hasNext(); j++) {
						Object key = itr.next();
						try {
							Label labData = new Label(j, i + 2, m.get(key)
									.toString());
							sheet.addCell(labData);
						} catch (NullPointerException e) {
						}
					}
				}
			} else if (data.get(0) instanceof List) {
				// list中是list
				for (int i = 0; i < data.size(); i++) {
					List lst = (List) data.get(i);
					for (int j = 0; j < lst.size(); j++) {
						try {
							Label labData = new Label(j, i + 2, lst.get(j)
									.toString());
							sheet.addCell(labData);
						} catch (NullPointerException e) {
						}
					}
				}
			} else if (data.get(0) instanceof Object[]) {
				// list中是数组
				for (int i = 0; i < data.size(); i++) {
					Object[] obj = (Object[]) data.get(i);
					for (int j = 0; j < obj.length; j++) {
						try {
							Label labData = new Label(j, i + 2,
									obj[j].toString());
							// System.out.println(obj[j]);
							sheet.addCell(labData);
						} catch (NullPointerException e) {
						}
					}
				}
			} else {
				logger.warn("行包含的数据不能处理");
			}

		}

		wwb.write();
		wwb.close();
	}

	public int getMiddle(Integer[] list, int low, int high) {
		int tmp = list[low]; // 数组的第一个作为中轴
		while (low < high) {
			while (low < high && list[high] >= tmp) {
				high--;
			}
			list[low] = list[high]; // 比中轴小的记录移到低端
			while (low < high && list[low] <= tmp) {
				low++;
			}
			list[high] = list[low]; // 比中轴大的记录移到高端
		}
		list[low] = tmp; // 中轴记录到尾
		return low; // 返回中轴的位置
	}

	public void _quickSort(Integer[] list, int low, int high) {
		if (low < high) {
			int middle = getMiddle(list, low, high); // 将list数组进行一分为二
			_quickSort(list, low, middle - 1); // 对低字表进行递归排序
			_quickSort(list, middle + 1, high); // 对高字表进行递归排序
		}
	}

	public void quick(Integer[] str) {
		if (str.length > 0) { // 查看数组是否为空
			_quickSort(str, 0, str.length - 1);
		}
	}

	public static void main(String[] args) throws Exception {
		String specialStr = "<div id=\"testDiv\">test1;test2</div>"; 
        String str1 = HtmlUtils.htmlEscape(specialStr);
        String str2 = specialStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;").
        		replaceAll("\"", "&quot;");
        System.out.println(str1); 
        System.out.println(str2); 
        System.out.println(getType(0xff));
        
        Integer[] list = { 2,3 ,3, 7 ,10, 14 ,34, 53 };
		FileUtil qs = new FileUtil();
		qs.quick(list);
		for (int i = 0; i < list.length; i++) {
			System.out.print(list[i] + " ");
		}
        String urlStr = "http://10.1.48.101:8080/workflow/attach/downloadFile.action?fileId=5029497";
        URL url = new URL(urlStr);
        URLConnection uc = url.openConnection();
        BufferedInputStream bi = new BufferedInputStream(uc.getInputStream());
        BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream("G:\\发的萨芬"));
        System.out.println();
        byte[] buffer = new byte[1024 * 6];
        int len = 0;
        while((len = bi.read(buffer)) !=-1){
            bo.write(buffer,0,len);
        }
        bo.flush();
        bo.close();
        bi.close();
	}
	// ...
	public static void main1(String[] args) throws Exception {
		 String specialStr = "<div id=\"testDiv\">test1;test2</div>"; 
	        String str1 = HtmlUtils.htmlEscape(specialStr);
	        String str2 = specialStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;").
	        		replaceAll("\"", "&quot;");
	        System.out.println(str1); 
	        System.out.println(str2); 
//		Integer[] list = { 34, 3, 53, 2, 3, 7, 14, 10 };
//		FileUtil qs = new FileUtil();
//		qs.quick(list);
//		for (int i = 0; i < list.length; i++) {
//			System.out.print(list[i] + " ");
//		}
//		System.out.println(System.getProperty("file.separator"));
//
//		File f = new File("d:\\source.xml");
//		BufferedReader r = new BufferedReader(new InputStreamReader(
//				new FileInputStream(f)));
//		String s = "";
//		while ((s = r.readLine()) != null) {
//			System.out.println(s);
//		}
//		r.close();
		System.out.println(0xff);
		byte[] b_gbk = "中".getBytes("GBK"); 
		int t1 = b_gbk[0];
		int t2 = b_gbk[1];
		System.out.println(Integer.toBinaryString(-1));
		System.out.println(Integer.toBinaryString(42));
		System.out.println(Math.pow(2.0, 6));
		System.out.println(0x0fffffff);
		System.out.println(getType(0xff));
		System.out.println(-3 & -5);
		System.out.println((byte)(-3 & -5));
		System.out.println(-1 & 0xff);
		System.out.println(getType(-1 & 0xff));
		System.out.println(t1);
		System.out.println(t1 & 0xff);
		System.out.println(Integer.toHexString(t1 & 0xff));
		System.out.println(Integer.toHexString(t2 & 0xff));
		byte[] b_utf8 = "中".getBytes("UTF-8"); 
		byte[] b_iso88591 = "a".getBytes("ISO8859-1"); 
		String s_iso88591 = new String("中".getBytes("UTF-8"),"ISO8859-1");
		byte[] temp = s_iso88591.getBytes("ISO8859-1");
		String s_utf8 = new String(s_iso88591.getBytes("ISO8859-1"),"UTF-8");
		char c = '中';
		int b = c;
		// zip打包
        String fileId = "65173,65881,66114,68929,78590,80122,90657,5003886,5004499,5004901,5006084,5008640,5014683,5014696,5016001,5016016,5018461,5018438,5018462,5018605,5018646,5020367,5020578,5020574,5020579,5020520,5020561,5020572,5020575,5020596,5020588,5020566,5020569,5020564,5023417,5024387,5031286,5036907,5301122,64790,66638,65483,65459,65464,65697,66636,86421,68210,68550,69266,69291,69889,69870,69816,70043,70669,70450,70950,71392,72796,76223,76772,76885,77116,77672,80194,81364,81819,81635,81835,82443,82464,82781,86480,87404,87883,88560,88527,88617,89784,90037,90316,90654,90764,90807,5000360,5000334,5001367,5002604,5002539,5003033,5003827,5006087,5006340,5007165,5006230,5006248,5007140,5007166,5007265,5007250,5009871,5014509,5014563,5014984,5015638,5017871,5018245,5018614,5018677,5018676,5018674,5018678,5020862,5020881,5020927,5028343,5020928,5023412,5022234,5022143,5024943,5025261,5025269,5025272,5025273,5025290,5025291,5025292,5025294,5025295,5025506,5030391,5030390,5030525,5031496,5033092,5034253,5037296,5037209,5038238,5293315,5293320,5299002,5298167,5298156,5300464,5302783,5303917,5303932,65059,70402,70447,76599,77096,88720,90427,90661,90744,5008290,5008681,5009503,5014942,5014966,5017413,5017980,5026801,5028704,5029483,5037318,5038842,5293314,5297843,5303212,5009884,68431,5290980,64733,66008,67949,67936,89005,5018270,5018464,5018543,87006";
        String fileName = "沪地铁机关委【2013】01号——关于中共上海申通地铁集团有限公司党委办公室、保卫部支部委员会选举情况的批复()沪地铁机关委【2013】02号——关于表彰2012年度集团机关优秀员工的决定()沪地铁机关委【2013】03号——关于2012年度机关先进部门优秀等次员工评定结果的通知[1]()沪地铁机关委【2013】04号——2012年度机关党委工作总结暨2013年工作要点的通知()沪地铁机关委[2013]05号——机关团总支换届党委批复()沪地铁机关委[2013]6号——机关党委文明创建考评办法()沪地铁机关委[2013]8号—关于开展2013年度机关员工考核述职暨优秀员工评选工作的通知[1]()沪地铁机关委[2014]1号——关于表彰2013年度机关优秀等级优秀员工的通知[1]()沪地铁机关委[2014]2号——关于印发集团机关党委2013年工作总结暨2014年工作计划的通知()沪地铁机关委[2014]3号——关于建设管理中心支部的批复()沪地铁委[2014]4号——关于调整机关部分党支部组织结构及部分支部换届改选的通知[1]()沪地铁机关委[2014]5号——机关党委改进作风专项整改活动通知()新沪地铁机关委[2014]6号——机关党支部换届改选通知--()沪地铁机关委[2014]6号——附件-集团机关党委-----公推直选党支部()沪地铁机关委[2014]7号——关于增补曾华同志为党支部委员的批复()沪地铁机关委[2014]8号——关于召开中国共产党上海申通地铁集团有限公司机关第二届一次代表大会的请示()沪地铁机关委[2014]9号——关于开展2011年-2014年两优一先评比通知1()沪地铁机关委[2014]9号——关于开展2011年-2014年两优一先评比通知1()沪地铁机关委[2014]9号——关于开展2011年-2014年两优一先评比通知1()沪地铁机关委[2014]10号——关于同意中共上海申通地铁股份有限公司()沪地铁机关委[2014]11号——关于认真做好上海申通地铁集团有限公司机关党委换届选举工作的通知()沪地铁机关委[2014]12号——关于中共上海申通地铁股份有限公司支部委员会()沪地铁机关委[2014]13号——关于组织人事部支部委员会改选的批复()沪地铁机关委[2014]14号——关于党办团委支部委员会改选的批复()沪地铁机关委[2014]15号——.关于企管总体信息支部委员会改选的批复()沪地铁机关委[2014]16号——关于投资合约支部委员会改选的批复()沪地铁机关委[2014]17号——关于财务审计支部委员会改选的批复()沪地铁机关委[2014]18号——关于工会纪检支部委员会改选的批复()沪地铁机关委[2014]19号——关于安监保卫支部委员会改选的批复()沪地铁机关委[2014]20号——关于申松线党支部委员会改选的批复()沪地铁机关委[2014]21号——关于申嘉线党支部委员会改选的批复()沪地铁机关委[2014]22号——关于12号线支部委员会改选的批复()沪地铁机关委[2014]23号——关于13号线支部委员会改选的批复()沪地铁机关委[2014]24号——关于人力资源公司支部委员会改选的批复()沪地铁机关委[2014]25号——关于表彰2011—2014年度先进党支部、优秀共产党员和优秀党务工作者决定()沪地铁机关委[2014]26号——关于第二届中共上海申通地铁集团有限公司机关委员会选举预备候选人名单的请示()沪地铁机关委[2014]28号——关于印发《集团机关党委2014年下半年度工作要点》通知()沪地铁机关委[2014]29号——关于印发申通地铁集团机关党委关于改进作风提高效率意见的通知()沪地铁机关委[2014]30号——关于开展2014年度机关部室机关员工考核述职暨优秀员工评选工作的通知()沪地铁委【2013】01号——关于李骏等同志任职的通知[1]()沪地铁委【2013】02号——关于上海市连续性内部出版物()沪地铁委【2013】03号——关于表彰2012年度集团公司“优秀员工”的决定()沪地铁委【2013】04号——关于2012年度上海申通地铁集团有限公司领导班子民主生活会情况报告（2013-1-12上报）[1]()沪地铁委【2013】05号——关于上海磁浮交通发展有限公司党委和纪委委员候选人预备人选的批复[1]()沪地铁委【2013】06号——关于印发《上海申通地铁集团有限公司2012年工作总结和2013年工作要点》的通知[1][1]()沪地铁委【2013】07号——关于磁浮公司党员大会选举结果的批复[1]()机关委【2013】7号--党批复-关于同意共青团上海申通地铁集团有限公司机关[1][1]()沪地铁委【2013】08号——关于印发《上海申通地铁集团有限公司党委2012年度工作总结暨2013年工作要点》的通知[1]()沪地铁委【2013】09号——关于进一步改进工作作风、密切联系群众的若干措施（正式稿）[1]()沪地铁委【2013】10号——关于实施上海申通地铁集团有限公司班组建设三年行动计划的通知[1]()沪地铁委【2013】11号—关于印发《2013年上海申通地铁集团有限公司党委中心组学习计划》的通知[1]()沪地铁委【2013】12号—地铁一号线20周年纪念活动通知()沪地铁委【2013】13号—上海申通地铁集团有限公司干部交流暂行办法[1]()沪地铁委【2013】14号——关于印发《干部选拔任用实施办法》的通知[1]()沪地铁委【2013】15号—关于黄建等同志职务任免的请示[1]()沪地铁委【2013】16号——上海地铁公共文化建设纲要()沪地铁委【2013】17号—关于成立上海地铁公共文化建设管理委员会的通知[1]()沪地铁委【2013】18号——关于增设纪检监察室的批复()沪地铁委【2013】19号关于磁浮公司调整组织机构、定员等事项的批复()沪地铁委【2013】20号——（党政）关于下发《2013年申通地铁集团党政领导班子成员党风廉政建设任务分解一览表》的通知[1]()沪地铁委【2013】22号——关于上海申通地铁集团有限公司维护保障中心更名等事项的通知(修改）[1]()沪地铁委【2013】23号——关于开展纪念中国共产党成立92周年活动的通知()沪地铁委【2013】24号——关于上海地铁第三运营有限公司增补党委委员候选人预备人选的批复()沪地铁委[2013]25号——关于组建中共上海地铁电子科技有限公司支部委员会的通知()沪地铁委[2013]26号——关于组建中共申通庞巴迪（上海）轨道交通车辆维修有限公司支部委员会的通知()沪地铁委[2013]27号——关于调整与充实集团公司各直属单位后备干部队伍的通知[1][1]()沪地铁委[2013]29号——关于中共上海申通地铁集团有限公司委员会分工的通知[1]()沪地铁委[2013]30号——关于王巍峰等同志任职的通知-党委发2[1][1]()沪地铁委[2013]31号——关于建立中共上海申通地铁集团有限公司委员会领导小组[1]()沪地铁委[2013]32号——关于陈鞍龙等同志任职的通知-党委发[1]()沪地铁委[2013]33号——关于上海轨道交通技术研究中心增补党委委员候选人预备人选的批复[1]()沪地铁委[2013]34号——关于上海申通地铁资产经营管理有限公司增补党委委员候选人预备人选的批复[1]()沪地铁委[2013]35号----申通地铁集团党委深入开展党的群众路线教育实践活动的实施方案（印发稿）1[2]()沪地铁委[2013]36号---关于组建中共申通北车（上海）轨道交通车辆维修有限公司总支部委员会的通知[1][1]()沪地铁委[2013]37号----关于王育才等同志任免的通知[1]()沪地铁委[2013]38号——关于印发《上海地铁维护保障有限公司机构编制及定员标准（试行）》的通知(发文稿2)[1]+(1)[1]()沪地铁委[2013]39号——关于开好党的群众路线教育实践活动民主生活会通知（2013-10-28终稿）[1]()沪地铁委[2013]40号——关于召开申通地铁集团专题民主生活的请示（上报终稿）[1][1]()沪地铁委[2013]41号——关于落实巡视反馈意见整改方案的报告[1]()沪地铁委[2013]45号——关于2013年申通地铁集团党政领导班子贯彻落实党风廉政建设责任制情况的自查报告[1][1]()沪地铁委[2013]46号——关于熊炜等同志任免的通知()沪地铁委[2013]47号——申通地铁集团领导班子民主生活会通报（11-27终稿发文,修改）[1]()沪地铁委[2013]48号——关于2013年度上海申通地铁集团有限公司专题民主生活会报告(1)[1]()沪地铁委[2013]49号——关于开展2013年度党风廉政建设责任制专项检查的通知[1][1]()沪地铁委[2013]50号——关于印发《上海申通地铁集团有限公司下属运营公司机构编制及定员标准（试行）》的通知（发文12-13）[1][1]()沪地铁委[2013]51号——关于上海申通地铁集团党委党的群众路线教育实践活动开展“四风”突出问题专项整治情况的报告[1]()沪地铁委[2013]52号——关于上海申通地铁集团党委党的群众路线教育实践活动制度建设推进及完成情况的报告[1]()沪地铁委[2013]53号——关于王益群同志职务任免的通知(2)[1]()沪地铁委[2014]1号——关于印发《上海申通地铁 集团有限公司2013年工作总结和2014年工作要 点》的通知()沪地铁委[2014]2号——关于上海申通地铁集团公司党的群众路线教育实践活动整改落实方案的报告()沪地铁委[2014]3号——关于组织开展重点课题研究的通知[1]()沪地铁委[2014]4号——关于同意翟鸣、沙玉五党委委员、纪委委员的批复[1]()沪地铁委[2013]5号——关于印发《2014年申通地铁集团党政领导班子成员党风廉政建设任务分解一览表》的通知[1]()沪地铁委[2014]6----关于梁慧萍同志免职的通知()沪地铁委[2014]7号-----关于蔡洪宇等同志职务任免的通知()沪地铁委[2014]8号——申通地铁集团党委2013年总结和2014年工作计划-发文稿（2014[1][1]()沪地铁委[2014]9号——关于贺阿东同志退休的请示[1]()沪地铁委[2014]10号——关于凌春霞同志任职的通知()沪地铁委[2014]11号——关于李君俊同志职务任免的通知()沪地铁委[2014]12号——开展上海申通地铁集团有限公司成立十周年系列宣传教育活动的通知()沪地铁委[2014]12号——关于开展上海申通地铁集团有限公司成立十周年系列宣传教育活动的通知()沪地铁委[2014]13号——2014年3月19日建设工程纪录片工作会议纪要()沪地铁委[2014]14号——关于贺阿东同志免职的通知[1]()沪地铁委[2014]15号——关于张一松同志免职的通知[1][1]()沪地铁委[2014]16号——关于涉密单机转公务网用机的批复()沪地铁委[2014]17号——关于印发《2014年度上海申通地铁集团有限公司党委中心组学习计划》的通知()沪地铁委[2014]18号——关于开展2011年-2014年两优一先评比通知1()沪地铁委[2014]19号——关于推荐王瑾同志为运营四公司工会委员、主席人选的通知()沪地铁委[2014]20号——关于资产经营公司第二届党委和纪委书记候选人预备人选的批复()沪地铁委[2014]21号——关于上海地铁维护保障有限公司第二届党委和纪委人选的批复()沪地铁委[2014]22号——关于上海轨道交通技术研究中心第二届党委和纪委人选的批复()沪地铁委[2014]23 号——关于上海轨道交通运营管理中心第二届党委和纪委人选的批复()沪地铁委[2014]24 号——关于上海地铁第二运营有限公司第二届党委和纪委人选的批复()沪地铁委[2014]25号——关于上海市隧道工程轨道交通设计研究院两委换届选举批复()沪地铁委[2014]26号——关于上海黄浦江大桥建设有限公司两委换届选举批复()沪地铁委[2014]27号——关于上海地铁第三运营有限公司两委换届选举批复()沪地铁机关委[2014]27号——关于中共上海申通地铁集团有限公司机关委员会选举情况及徐建群等同志任职的请示()沪地铁委 [2014]28号——关于上海地铁第四运营有限公司两委换届选举批复()沪地铁委【2014】29号——关于印发《关于创建地铁车站党建联建新模式实施方案》的通知()沪地铁委【2014】30号——关于印发《关于加强基层服务型党组织建设的实施意见》的通知()沪地铁委【2014】31号——关于表彰申通地铁集团2011--2014年“两优一先”表彰决定()沪地铁委[2014]32号——关于印发《上海申通地铁集团有限公司关于全面推进工资集体协商制度的意见》的通知（1）()沪地铁委[2014]33号——运营中心两委批复()沪地铁委[2014]34 号——隧道院两委批复()沪地铁委[2014]35号——运三公司两委批复()沪地铁委[2014]36号——技术中心两委批复()沪地铁委[2014]37号——大桥公司两委批复()沪地铁委[2014]38号——资产公司两委批复()沪地铁委[2014]39——运四公司两委批复()沪地铁委[2014]40号——维护公司两委批复()沪地铁委[2014]41号——运二公司两委批复()沪地铁委[2014]42号——关于上海申通地铁集团有限公司机关第二届党委候选人预备人选的批复()沪地铁委[2014]43号——关于上海地铁第一运营有限公司两委候选人批复()沪地铁委[2014]44 号——关于中国共产党上海申通地铁集团有限公司机关第二届一次代表大会选举结果的批复()沪地铁委[2014]45 号——关于加强基层党组织开展“公推直选”工作的指导意见()沪地铁委[2014]46 号——关于宋理强同志职务任免的通知()沪地铁委[2014]47号——关于加强集团公司领导干部垂直兼职监管的规定（发文用）()沪地铁委[2014]48号—关于加强集团公司特定身份人员因私出国（境）证照管理若干规定的通知()沪地铁委[2014]49号——运一公司两委批复()沪地铁委[2014]50号——关于印发申通地铁集团党委主体责任进一步做实党风廉政建设责任制意见的工作方案通知()沪地铁委[2014]51号——关于印发《上海申通地铁集团有限公司关于全面推行车站民主管理的实施意见》的通知()沪地铁委[2014]52号——关于赵天虹等同志职务任免的通知()沪地铁委[2014]53号——关于深入开展“改革当先锋、为民作表率”党建主题活动的实施意见()沪地铁委[2014]54号——关于中共上海轨道交通培训中心第二届总支部委员会候选人预备人选的批复()沪地铁委[2014]55 号——关于中共上海申通地铁集团有限公司委员会党委书记、副书记、委员分工的通知()沪地铁委[2014]56 号——关于俞光耀等同志职务任免的通知()沪地铁委[2014]57 号——关于成立纪检监察室的批复()沪地铁委[2015]1号——关于认真开好2014年度民主生活会的通知（2014-12-28）()沪地铁委【2015】2号——关于召开上海申通地铁集团有限公司领导班子2014年度民主生活会的请示（2015-1-12）()沪地铁委【2015】3号——轨道交通培训中心批复（2014-12-25111）()沪地铁委办【2013】01号——关于做好2013年春节工作的通知[1]()沪地铁委【2013】02号—关于集团领导干部深入基层开展保高峰保安全的通知0401[1]()沪地铁委【2013】02号—关于集团领导干部深入基层开展保高峰保安全的通知0401[1]()沪地铁委办[2013]3号——关于组织开展“六五”普法中期自查的通知()沪地铁委办[2013]4号——关于进一步加强智能手机移动终端使用保密管理的通知()沪地铁委办[2013]5号——关于印发《上海地铁官方微博客管理实施细则》的通知[1]()沪地铁委办[2013]6号—公务网接入的请示()沪地铁委办[2013]7号——关于表彰2013年度《上海地铁》报优秀通讯员的通知[1][1]()沪地铁委办[2013]8号——关于上海地铁音乐角第100场公益演出的会议纪要[1]()沪地铁委办[2014]1号——关于征集企业发展成果论文的通知[1][1]()沪地铁委办[2014]2号——十周年征文活动策划[1]()沪地铁委办[2014]3号——关于征求意见书的通知20130326[1][1]()沪地铁委办[2014]4号——关于印发“纪录800”活动通知()沪地铁委办[2014]5号——关于涉密单机转公务网用机的请示 (1)()沪地铁委办[2014]6号--关于减发县团级党委文件的请示()沪地铁委办[2014]7号——集团中心组学习制度()沪地铁委办[2014]9 号——2014纪录800公里下半年工作会会议纪要：()沪地铁委办[2014]10号——关于转发共青团上海申通地铁集团有限公司委员会《关于调整运营单位基层团组织设置的意见》的通知()沪地铁委办[2014]11号——上海地铁音乐角公益活动的管理规定()沪地铁委办[2014]12号——关于转发申通地铁集团机关党委关于改进作风提高效率的意见的通知()沪地铁委办[2014]13号——2014年10月17日“纪录800公里”工作专题会会议纪要-1()沪地铁委办[2014]14号——关于徐家汇站文化长廊相关工作 会议纪要()沪地铁委办[2014]15号——关于表彰2014年度《上海地铁》报优秀通讯员的通知()沪地铁委办【2015】1号——关于进一步加强大客流组织和重点管控的通知-无附表发文版()沪地铁委群办【2014】1号——党的群众路线教育实践活动整改项目的通知()沪地铁文明办【2013】01号——关于贯彻落实市文明办推动学习雷锋活动常态化工作安排的通知[1]()沪地铁文明 办[2014 ]2号——关于全市相关区域创建全国文明城区期间相关配合的通知()沪地铁文明委【2013】01号——关于积极申报市第六届文明行业的报告-130107[1]()沪地铁文明委【2013】02号——关于推荐、命名2011--2012年文明单位的通知[1][1]()沪地铁文明委【2013】03号——关于表彰上海申通地铁集团有限公司2012年度好人好事的决定[1][1]()沪地铁文明委【2013】04号——关于表彰2012年度上海轨道交通文明车站、文明班组和样板班组的决定[1]()沪地铁文明委[2013]5号——关于申报市国资委系统第十七届文明单位的汇报[1]()沪地铁文明委[2014]1号——关于调整上海地铁志愿者服务总队领导成员的通知[1]()沪地铁文明委[2014]2号——关于印发《2014年上海申通地铁集团精神文明创建工作指导意见》的通知()沪地铁文明委[2014]3号——关于表彰2013年度上海地铁公共文化建设先进集体和个人的通知()沪地铁志愿者总[2013]1号——关于表彰上海地铁志愿服务优秀团队和优秀个人的决定[1]";
        String ext = "pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,rar,pdf,pdf,doc,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,docx,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,doc,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,doc,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf,pdf";

        String[] fileNameArr = fileName.split("\\(\\)");
        String[] fileIdArr = fileId.split(",");
        String[] extArr = ext.split(",");
		List<String[]> fileList = new ArrayList<String[]>();
		//fileList.add(new String[]{"附件1.doc","http://10.1.48.101:8080/workflow/attach/downloadFile.action?fileId=5029497"});
		//fileList.add(new String[]{"附件2.doc","http://10.1.48.101:8080/workflow/attach/downloadFile.action?fileId=5029496"});
    System.out.println(fileNameArr.length);
        System.out.println(fileIdArr.length);
        System.out.println(extArr.length);
        for(int i=0;i<fileNameArr.length;i++){
            fileList.add(new String[]{fileNameArr[i]+"."+extArr[i],"http://10.1.48.101:8080/workflow/attach/downloadFile.action?fileId="+fileIdArr[i]});
        }

		String zipName = "ZipExport.rar";
		try {
			int len = 0;
			byte[] buffers = new byte[1024];

			
			File file = new File("D:\\"+zipName);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
			try {
				//zos = new ZipOutputStream(bos);

				for (String[] att : fileList) {
					URL url = new URL(att[1]);

					URLConnection con = url.openConnection();

					InputStream is = con.getInputStream();
					ZipEntry entry = new ZipEntry(att[0]);
					zos.putNextEntry(entry);

					while ((len = is.read(buffers)) != -1) {
						zos.write(buffers, 0, len);
					}
					zos.setEncoding("gbk");
					zos.closeEntry();
					is.close();
				}

				zos.flush();
				zos.close();
			} catch (Exception localException) {
		}
		}catch(Exception e){}
		/*
		 * String title = "test导出数据"; List head = new ArrayList();
		 * head.add("姓名"); head.add("编号"); List data = new ArrayList(); /*List
		 * data1 = new ArrayList(); data1.add("张三"); data1.add("0100");
		 * data.add(data1); List data2 = new ArrayList(); data2.add("李四");
		 * data2.add("0101"); data.add(data2);
		 */
		/*
		 * Map m1 = new HashMap(); m1.put("name", "张三"); m1.put("code", "0100");
		 * Map m2 = new HashMap(); m2.put("name", "李四"); m2.put("code", "0102");
		 * data.add(m1);data.add(m2);
		 */
		/*
		 * Object[] o1 = {"张三", new Integer(10)}; Object[] o2 = {"李四", new
		 * Integer(20)}; data.add(o1); data.add(o2);
		 * 
		 * OutputStream os = new FileOutputStream(new File("xx.xls"));
		 * createXls(os, title, head, data); os.close();
		 * System.out.println("done");
		 */
	}
	
	public static String getType(Object o){
		return o.getClass().toString();
		}
		public static String getType(int o){
		return "int";
		}
		public static String getType(byte o){
		return "byte";
		}
		public static String getType(char o){
		return "char";
		}
		public static String getType(double o){
		return "double";
		}
		public static String getType(float o){
		return "float";
		}
		public static String getType(long o){
		return "long";
		}
		public static String getType(boolean o){
		return "boolean";
		}
		public static String getType(short o){
		return "short";
		}
}
