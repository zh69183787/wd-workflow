package com.wonders.send.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract interface CommonDao
{
  public abstract List ListByHql(String paramString);

  public abstract List ListByHql(String paramString, Object[] paramArrayOfObject);

  public abstract void UpdateByHql(String paramString);

  public abstract void save(Object paramObject);

  public abstract Object load(String paramString, Class paramClass);

  public abstract void update(Object paramObject);

  public abstract void remove(Object paramObject);

  public abstract Object load(Serializable paramSerializable, Class paramClass);

  public abstract List<Object> findByHQL(String paramString);
  
  public void saveOrUpdateAll(Collection cols);
}

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.dao.CommonDao
 * JD-Core Version:    0.6.0
 */