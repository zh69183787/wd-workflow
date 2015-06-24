package com.wonders.common.action;

import com.wonders.constants.WorkflowConstants;
import com.wonders.util.PropertyUtil;
import com.wonders.util.StringUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URLEncoder;


//后续跟踪事项配套
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value = "/common")
@Controller("commonAction")
@Scope("prototype")
public class CommonAction extends AbstractParamAction {
    private final static String STPT_PATH = PropertyUtil.getValueByKey("config.properties", "stptPath");
    private final static String XMLX_PATH = PropertyUtil.getValueByKey("config.properties", "xmlxPath");
    private String pname;
    private String pincident;
    private String taskid;

    @Action(value = "view", results = {
            @Result(name = "removed", location = "/discipline/process/recv/removed.jsp")
            //纪委收文
            , @Result(name = "dcprecv", location = "../discipline-dcpRecvMain/forward",
            params = {"viewType", "1",
                    "pname", "%{pname}", "pincident", "%{pincident}", "taskid", "%{taskid}"},
            type = "redirectAction")
            //呈批件
            , @Result(name = "redv", location = "../receive-redvMain/forward",
            params = {"viewType", "1",
                    "pname", "%{pname}", "pincident", "%{pincident}", "taskid", "%{taskid}"},
            type = "redirectAction")
            //联系单
            , @Result(name = "contact", location = "../contact-deptContact/view",
            params = {"viewType", "1",
                    "pname", "%{pname}", "pincident", "%{pincident}", "taskid", "%{taskid}"},
            type = "redirectAction")
            //收文
            , @Result(name = "recv", location = "../receive-recvMain/forward",
            params = {"viewType", "1",
                    "pname", "%{pname}", "pincident", "%{pincident}", "taskid", "%{taskid}"},
            type = "redirectAction")
            //合同后审
            , @Result(name = "review", location = "../contract-reviewMain/forward",
            params = {"viewType", "1",
                    "pname", "%{pname}", "pincident", "%{pincident}", "taskid", "%{taskid}"},
            type = "redirectAction")
            //各类发文
            , @Result(name = "send", location = "../send-tDocSend/toFormPage",
            params = {"viewType", "1",
                    "modelName", "%{pname}", "incidentNo", "%{pincident}", "taskId", "%{taskid}"},
            type = "redirectAction")
            //部门审阅
            , @Result(name = "pass", location = "../dept-passMain/forward",
            params = {"viewType", "1",
                    "pname", "%{pname}", "pincident", "%{pincident}", "taskId", "%{taskid}"},
            type = "redirectAction")
            //项目销项
            , @Result(name = "pjdiscard", location = "../project-discardMain/forward",
            params = {"viewType", "1",
                    "pname", "%{pname}", "pincident", "%{pincident}", "taskId", "%{taskid}"},
            type = "redirectAction")
    })
    public String view() {
        String operateType = "removed";
        String pname = StringUtil.getNotNullValueString(request.getParameter("pname"));
        String pincident = StringUtil.getNotNullValueString(request.getParameter("pincident"));
        String taskid = StringUtil.getNotNullValueString(request.getParameter("taskid"));

        if (WorkflowConstants.NEW_RECV.equals(pname)) {
            operateType = "recv";
        } else if (WorkflowConstants.NEW_DOC_SEND.equals(pname) ||
                WorkflowConstants.NEW_PARTY_SEND.equals(pname) ||
                WorkflowConstants.NEW_DCP_SEND.equals(pname)) {
            operateType = "send";
        } else if (WorkflowConstants.CONTRACT_REVIEW.equals(pname)) {
            operateType = "review";
        } else if (WorkflowConstants.DCP_RECV.equals(pname)) {
            operateType = "dcprecv";
        } else if (WorkflowConstants.DEPT_CONTACT.equals(pname)) {
            operateType = "contact";
        } else if (WorkflowConstants.DEPT_PASS.equals(pname)) {
            operateType = "pass";
        } else if (WorkflowConstants.PROJECT_DISCARD.equals(pname)) {
            operateType = "pjdiscard";
        } else if (WorkflowConstants.NEW_PROJECT_APPROVE.equals(pname)) {
            try {
                String url = XMLX_PATH + "/xmlx/projectapp-pclMain/loadXMLX.action?.action?"
                        + "modelName=" + URLEncoder.encode(pname, "UTF-8")
                        + "&incident=" + pincident + "&taskid="+taskid
                        + "&viewType=1";
                this.response.sendRedirect(url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //默认老平台流程
        } else {
            try {
                String url = STPT_PATH + "/openflowform.action?"
                        + "task_id=" + taskid
                        + "&model_id=" + URLEncoder.encode(pname, "UTF-8")
                        + "&instance_id=" + pincident
                        + "&step_name=aa"
                        + "&task_type=1";
                this.response.sendRedirect(url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return operateType;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPincident() {
        return pincident;
    }

    public void setPincident(String pincident) {
        this.pincident = pincident;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }


}
