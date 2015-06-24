package com.wonders.send.mainProcess.send.service;

import java.util.Collection;
import java.util.List;

import com.wonders.send.mainProcess.send.model.bo.TNormativeDoc;

public abstract interface TDocSendService
{
  public abstract void save(Object paramObject);
  public abstract List<TNormativeDoc> getNormativeInfo(String mainId);
  public void saveNormative(Collection<TNormativeDoc> obj);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.mainProcess.send.service.TDocSendService
 * JD-Core Version:    0.6.0
 */