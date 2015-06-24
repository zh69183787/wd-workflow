<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
$(function(){
	if($("[name=incidentNo]").val()==""){
		$("#inprogress_ul").find("li:eq(0)").addClass("on");
	}else{
		var inprogress_img = $("#inprogress_img").val();
		if(inprogress_img!="6"){
			$("#inprogress_ul").find("li:eq("+(inprogress_img-1)+")").addClass("on");
		}
		$("#inprogress_ul").find("li").each(function(i){
			if(i<(inprogress_img-1)){
				$(this).addClass("pass");
			}
		});
	}
});
</script>

<div class="mb10 Step clearfix">
   <ul class="clearfix" id="inprogress_ul">
       <li class="fst">
           <dl>
               <dt></dt>
                 <dd>登记阶段</dd>
              </dl>
         </li>
       <li>
           <dl>
               <dt></dt>
                 <dd>拟办阶段</dd>
              </dl>
         </li>
       <li>
           <dl>
               <dt></dt>
                 <dd>批办阶段</dd>
              </dl>
         </li>
       <li>
           <dl>
               <dt></dt>
                 <dd>处理阶段</dd>
              </dl>
         </li>
         <li class="fin">
           <dl>
               <dt></dt>
                 <dd>办结阶段</dd>
             </dl>
         </li>
     </ul>
 </div>

