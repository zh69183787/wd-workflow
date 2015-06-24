package test;

import com.wonders.send.common.service.CommonService;
import com.wonders.send.external.service.ExternalService;
import com.wonders.send.mainProcess.send.model.bo.TDocSend;
import com.wonders.send.model.bo.DeptCode;
import com.wonders.send.model.bo.SendTodoItem;
import com.wonders.send.organTree.model.bo.OrganUserBo;
import com.wonders.send.util.PublicFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhoushun
 * Date: 2015/4/17
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Component
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class DeptPassUtil extends AbstractJUnit4SpringContextTests {

    @Resource
    private CommonService commonService;

    @Resource
    private ExternalService externalService;

    @Test
    public void test() {
        String contextIpPath = "http://10.1.48.101:8080/workflow";
        String listType = "0";
        String modelId = "纪委发文流程";
        TDocSend tDocSend = ((TDocSend) this.commonService.load("8a81ac904cec210c014d042f1be50327", TDocSend.class));

        String sendMainIds = tDocSend.getSendMainId();//主送
        String sendInsideIds = tDocSend.getSendInsideId();//抄送
        String sendReportIds = tDocSend.getSendReportId();//内发
        String newDeptIds = "";
        PublicFunction func = new PublicFunction();
        String sender = "G001000000362505";
        String senderName = "宗艳玲";
        if (sender != null && sender.length() > 0) {//根据12位工号得到id
            sender = sender.replace("ST/", "");
        }
        String Initiator = sender;
        String senderId = "";
        String title = "系统中已有一条业务流程完成流转，摘要：" + modelId + "-" + tDocSend.getDocTitle() + "。请关注。";

        String docsFileUrl = contextIpPath + "/attach/loadFileList.action?" +
                "attachMemo=fawen_att_dic&procType=view&fileGroup=contentAttMain&fileGroupName=contentAttMainGroup&fileGroupId=" + tDocSend.getContentAttMain() +
                "&userName=" + sender + "&loginName=" + sender + "&targetType=dialog&listType=" + listType;


        if (sendMainIds != null && sendMainIds.length() > 0) {
            newDeptIds += sendMainIds;
        }
        if (sendInsideIds != null && sendInsideIds.length() > 0) {
            String[] idSplit = sendInsideIds.split(",");
            String ids = "," + newDeptIds + ",";
            for (int i = 0; i < idSplit.length; i++) {
                if (ids.indexOf("," + idSplit[i] + ",") == -1) {
                    if (newDeptIds.length() > 0) {
                        newDeptIds += ",";
                    }
                    newDeptIds += idSplit[i];
                }
            }
        }
        if (sendReportIds != null && sendReportIds.length() > 0) {
            String[] idSplit = sendReportIds.split(",");
            String ids = "," + newDeptIds + ",";
            for (int i = 0; i < idSplit.length; i++) {
                if (ids.indexOf("," + idSplit[i] + ",") == -1) {
                    if (newDeptIds.length() > 0) {
                        newDeptIds += ",";
                    }
                    newDeptIds += idSplit[i];
                }
            }
        }
        if (newDeptIds.length() > 0) {
            List<Object> deptCodeList = commonService.findByHQL("from DeptCode t where t.removed = '0' and t.oldDeptId in (" + newDeptIds + ")");
            Map<String, DeptCode> codeMap = new HashMap<String, DeptCode>();
            newDeptIds = "";
            if (deptCodeList != null && deptCodeList.size() > 0) {
                for (int k = 0; k < deptCodeList.size(); k++) {
                    DeptCode deptCode = (DeptCode) deptCodeList.get(k);
                    codeMap.put(deptCode.getNewDeptId(), deptCode);
                    newDeptIds += deptCode.getNewDeptId() + ",";
                }
                if(newDeptIds.length() > 0) newDeptIds = newDeptIds.substring(0,newDeptIds.length()-1);
            }

            List<OrganUserBo> listReseivers = externalService.getDeptReceivers(newDeptIds, modelId, tDocSend.getPinstanceid(), "部门接受人工作分发");
            Map<String, OrganUserBo> receiversMap = new HashMap<String, OrganUserBo>();
            if (listReseivers != null && listReseivers.size() > 0) {
                for (int k = 0; k < listReseivers.size(); k++) {
                    OrganUserBo organUserBo = listReseivers.get(k);
                    receiversMap.put(organUserBo.pid, organUserBo);
                }
            }

            List<OrganUserBo> listLeaders = externalService.getDeptSingleLeader(newDeptIds);
            Map<String, OrganUserBo> leadersMap = new HashMap<String, OrganUserBo>();
            if (listLeaders != null && listLeaders.size() > 0) {
                for (int k = 0; k < listLeaders.size(); k++) {
                    OrganUserBo organUserBo = listLeaders.get(k);
                    leadersMap.put(organUserBo.pid, organUserBo);
                }
            }

            String[] newDeptIdsSplit = newDeptIds.split(",");
            for (int i = 0; i < newDeptIdsSplit.length; i++) {
                DeptCode deptCode = codeMap.get(newDeptIdsSplit[i]);
                OrganUserBo receiverBo = receiversMap.get(newDeptIdsSplit[i]);
                OrganUserBo leaderBo = leadersMap.get(newDeptIdsSplit[i]);

                String url = "";
                String stepName = "发文通知";
                Date date = new Date();
                String id = "";
                if (deptCode != null) {
                    id = func.getReceiverId(deptCode.getOldDeptId());
                }
                if (id != null && id.length() > 0 && !"null".equals(id) && deptCode != null && receiverBo != null && leaderBo != null) {
                    String loginName = "";
                    String userName = "";
                    String actionName = "";
                    if (PublicFunction.judgeDeptInfoForNotice(newDeptIdsSplit[i])) {
                        loginName = leaderBo.loginName;
                        userName = leaderBo.name;
                        actionName = "add";
                    } else {
                        loginName = receiverBo.loginName;
                        userName = receiverBo.name;
                        actionName = "sign";
                    }
                    if (PublicFunction.judgeSendPass(newDeptIdsSplit[i])) {//processname=send_notice
                        actionName = "add";
                    }
                    try {
                        url = contextIpPath + "/dept-passMain/" + actionName + ".action?modelName=" +
                                //url = "http://10.1.41.252:8080/workflow/dept-passMain/"+actionName+".action?modelName="+
                                URLEncoder.encode(modelId, "UTF-8") + "&incidentNo=" + tDocSend.getPinstanceid() + "&processName=" +
                                URLEncoder.encode(modelId, "UTF-8") + "&pinstanceId=" + tDocSend.getPinstanceid() + "&taskUserName=" +
                                loginName + "&stepName=" + URLEncoder.encode(stepName, "UTF-8") +
                                "&taskId=" + "041317655982a2b957e741ed77e987" + "&taskuser=" + loginName + "&codeId=" + deptCode.getId() +
                                "&attach=" + tDocSend.getContentAttMain() + "&title=" +
                                URLEncoder.encode(tDocSend.getSendId() + " " + tDocSend.getDocTitle(), "UTF-8") +
                                "&mainId=" + tDocSend.getId() + "&mainTable=T_DOC_SEND";
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    SendTodoItem todoItemBo = new SendTodoItem();
                    todoItemBo.setApp("sendNotice");
                    todoItemBo.setOccurTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                    todoItemBo.setTitle(title);
                    todoItemBo.setLoginName(loginName);
                    todoItemBo.setStatus(0);
                    todoItemBo.setRemoved(0);
                    todoItemBo.setTypename(modelId);
                    todoItemBo.setUrl(url);
                    todoItemBo.setPname(modelId);
                    todoItemBo.setPincident(Integer.valueOf(tDocSend.getPinstanceid()));
                    todoItemBo.setCname(modelId);
                    todoItemBo.setCincident(Integer.valueOf(tDocSend.getPinstanceid()));
                    todoItemBo.setStepName(stepName);
                    todoItemBo.setTaskId("0429158159e7fda39bf63cdba8de97");
                    todoItemBo.setType(0);
                    todoItemBo.setInitiator("ST/" + Initiator);
                    todoItemBo.setData("");
                    todoItemBo.setKey(tDocSend.getId());
                    commonService.save(todoItemBo);

                    todoItemBo.setUrl(todoItemBo.getUrl() + "&todoId=" + todoItemBo.getId());
                    commonService.update(todoItemBo);

                }
            }
        }


    }
}
