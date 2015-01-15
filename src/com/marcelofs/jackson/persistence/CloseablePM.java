package com.marcelofs.jackson.persistence;

import java.io.Closeable;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import javax.jdo.Extent;
import javax.jdo.FetchGroup;
import javax.jdo.FetchPlan;
import javax.jdo.JDOException;
import javax.jdo.ObjectState;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.datastore.JDOConnection;
import javax.jdo.datastore.Sequence;
import javax.jdo.listener.InstanceLifecycleListener;

public class CloseablePM implements PersistenceManager, Closeable {

	private PersistenceManager pm;

	public CloseablePM(PersistenceManager pm) {
		this.pm = pm;
	}

	@SuppressWarnings("rawtypes")
	public void addInstanceLifecycleListener(InstanceLifecycleListener arg0,
			Class... arg1) {
		pm.addInstanceLifecycleListener(arg0, arg1);
	}

	public void checkConsistency() {
		pm.checkConsistency();
	}

	public void close() {
		pm.close();
	}

	public Transaction currentTransaction() {
		return pm.currentTransaction();
	}

	public void deletePersistent(Object arg0) {
		pm.deletePersistent(arg0);
	}

	@SuppressWarnings("rawtypes")
	public void deletePersistentAll(Collection arg0) {
		pm.deletePersistentAll(arg0);
	}

	public void deletePersistentAll(Object... arg0) {
		pm.deletePersistentAll(arg0);
	}

	public <T> T detachCopy(T arg0) {
		return pm.detachCopy(arg0);
	}

	public <T> Collection<T> detachCopyAll(Collection<T> arg0) {
		return pm.detachCopyAll(arg0);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] detachCopyAll(T... arg0) {
		return pm.detachCopyAll(arg0);
	}

	public void evict(Object arg0) {
		pm.evict(arg0);
	}

	public void evictAll() {
		pm.evictAll();
	}

	@SuppressWarnings("rawtypes")
	public void evictAll(boolean arg0, Class arg1) {
		pm.evictAll(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public void evictAll(Collection arg0) {
		pm.evictAll(arg0);
	}

	public void evictAll(Object... arg0) {
		pm.evictAll(arg0);
	}

	public void flush() {
		pm.flush();
	}

	public boolean getCopyOnAttach() {
		return pm.getCopyOnAttach();
	}

	public JDOConnection getDataStoreConnection() {
		return pm.getDataStoreConnection();
	}

	public Integer getDatastoreReadTimeoutMillis() {
		return pm.getDatastoreReadTimeoutMillis();
	}

	public Integer getDatastoreWriteTimeoutMillis() {
		return pm.getDatastoreWriteTimeoutMillis();
	}

	public boolean getDetachAllOnCommit() {
		return pm.getDetachAllOnCommit();
	}

	public <T> Extent<T> getExtent(Class<T> arg0, boolean arg1) {
		return pm.getExtent(arg0, arg1);
	}

	public <T> Extent<T> getExtent(Class<T> arg0) {
		return pm.getExtent(arg0);
	}

	@SuppressWarnings("rawtypes")
	public FetchGroup getFetchGroup(Class arg0, String arg1) {
		return pm.getFetchGroup(arg0, arg1);
	}

	public FetchPlan getFetchPlan() {
		return pm.getFetchPlan();
	}

	public boolean getIgnoreCache() {
		return pm.getIgnoreCache();
	}

	@SuppressWarnings("rawtypes")
	public Set getManagedObjects() {
		return pm.getManagedObjects();
	}

	@SuppressWarnings("rawtypes")
	public Set getManagedObjects(Class... arg0) {
		return pm.getManagedObjects(arg0);
	}

	@SuppressWarnings("rawtypes")
	public Set getManagedObjects(EnumSet<ObjectState> arg0, Class... arg1) {
		return pm.getManagedObjects(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public Set getManagedObjects(EnumSet<ObjectState> arg0) {
		return pm.getManagedObjects(arg0);
	}

	public boolean getMultithreaded() {
		return pm.getMultithreaded();
	}

	public <T> T getObjectById(Class<T> arg0, Object arg1) {
		return pm.getObjectById(arg0, arg1);
	}

	public Object getObjectById(Object arg0, boolean arg1) {
		return pm.getObjectById(arg0, arg1);
	}

	public Object getObjectById(Object arg0) {
		return pm.getObjectById(arg0);
	}

	public Object getObjectId(Object arg0) {
		return pm.getObjectId(arg0);
	}

	@SuppressWarnings("rawtypes")
	public Class getObjectIdClass(Class arg0) {
		return pm.getObjectIdClass(arg0);
	}

	public Object[] getObjectsById(boolean arg0, Object... arg1) {
		return pm.getObjectsById(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public Collection getObjectsById(Collection arg0, boolean arg1) {
		return pm.getObjectsById(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public Collection getObjectsById(Collection arg0) {
		return pm.getObjectsById(arg0);
	}

	public Object[] getObjectsById(Object... arg0) {
		return pm.getObjectsById(arg0);
	}

	@SuppressWarnings("deprecation")
	public Object[] getObjectsById(Object[] arg0, boolean arg1) {
		return pm.getObjectsById(arg0, arg1);
	}

	public PersistenceManagerFactory getPersistenceManagerFactory() {
		return pm.getPersistenceManagerFactory();
	}

	public Sequence getSequence(String arg0) {
		return pm.getSequence(arg0);
	}

	public Date getServerDate() {
		return pm.getServerDate();
	}

	public Object getTransactionalObjectId(Object arg0) {
		return pm.getTransactionalObjectId(arg0);
	}

	public Object getUserObject() {
		return pm.getUserObject();
	}

	public Object getUserObject(Object arg0) {
		return pm.getUserObject(arg0);
	}

	public boolean isClosed() {
		return pm.isClosed();
	}

	public void makeNontransactional(Object arg0) {
		pm.makeNontransactional(arg0);
	}

	@SuppressWarnings("rawtypes")
	public void makeNontransactionalAll(Collection arg0) {
		pm.makeNontransactionalAll(arg0);
	}

	public void makeNontransactionalAll(Object... arg0) {
		pm.makeNontransactionalAll(arg0);
	}

	public <T> T makePersistent(T arg0) {
		return pm.makePersistent(arg0);
	}

	public <T> Collection<T> makePersistentAll(Collection<T> arg0) {
		return pm.makePersistentAll(arg0);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] makePersistentAll(T... arg0) {
		return pm.makePersistentAll(arg0);
	}

	public void makeTransactional(Object arg0) {
		pm.makeTransactional(arg0);
	}

	@SuppressWarnings("rawtypes")
	public void makeTransactionalAll(Collection arg0) {
		pm.makeTransactionalAll(arg0);
	}

	public void makeTransactionalAll(Object... arg0) {
		pm.makeTransactionalAll(arg0);
	}

	public void makeTransient(Object arg0, boolean arg1) {
		pm.makeTransient(arg0, arg1);
	}

	public void makeTransient(Object arg0) {
		pm.makeTransient(arg0);
	}

	public void makeTransientAll(boolean arg0, Object... arg1) {
		pm.makeTransientAll(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public void makeTransientAll(Collection arg0, boolean arg1) {
		pm.makeTransientAll(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public void makeTransientAll(Collection arg0) {
		pm.makeTransientAll(arg0);
	}

	public void makeTransientAll(Object... arg0) {
		pm.makeTransientAll(arg0);
	}

	@SuppressWarnings("deprecation")
	public void makeTransientAll(Object[] arg0, boolean arg1) {
		pm.makeTransientAll(arg0, arg1);
	}

	public <T> T newInstance(Class<T> arg0) {
		return pm.newInstance(arg0);
	}

	@SuppressWarnings("rawtypes")
	public Query newNamedQuery(Class arg0, String arg1) {
		return pm.newNamedQuery(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public Object newObjectIdInstance(Class arg0, Object arg1) {
		return pm.newObjectIdInstance(arg0, arg1);
	}

	public Query newQuery() {
		return pm.newQuery();
	}

	@SuppressWarnings("rawtypes")
	public Query newQuery(Class arg0, Collection arg1, String arg2) {
		return pm.newQuery(arg0, arg1, arg2);
	}

	@SuppressWarnings("rawtypes")
	public Query newQuery(Class arg0, Collection arg1) {
		return pm.newQuery(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public Query newQuery(Class arg0, String arg1) {
		return pm.newQuery(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public Query newQuery(Class arg0) {
		return pm.newQuery(arg0);
	}

	@SuppressWarnings("rawtypes")
	public Query newQuery(Extent arg0, String arg1) {
		return pm.newQuery(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public Query newQuery(Extent arg0) {
		return pm.newQuery(arg0);
	}

	public Query newQuery(Object arg0) {
		return pm.newQuery(arg0);
	}

	public Query newQuery(String arg0, Object arg1) {
		return pm.newQuery(arg0, arg1);
	}

	public Query newQuery(String arg0) {
		return pm.newQuery(arg0);
	}

	public Object putUserObject(Object arg0, Object arg1) {
		return pm.putUserObject(arg0, arg1);
	}

	public void refresh(Object arg0) {
		pm.refresh(arg0);
	}

	public void refreshAll() {
		pm.refreshAll();
	}

	@SuppressWarnings("rawtypes")
	public void refreshAll(Collection arg0) {
		pm.refreshAll(arg0);
	}

	public void refreshAll(JDOException arg0) {
		pm.refreshAll(arg0);
	}

	public void refreshAll(Object... arg0) {
		pm.refreshAll(arg0);
	}

	public void removeInstanceLifecycleListener(InstanceLifecycleListener arg0) {
		pm.removeInstanceLifecycleListener(arg0);
	}

	public Object removeUserObject(Object arg0) {
		return pm.removeUserObject(arg0);
	}

	public void retrieve(Object arg0, boolean arg1) {
		pm.retrieve(arg0, arg1);
	}

	public void retrieve(Object arg0) {
		pm.retrieve(arg0);
	}

	public void retrieveAll(boolean arg0, Object... arg1) {
		pm.retrieveAll(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public void retrieveAll(Collection arg0, boolean arg1) {
		pm.retrieveAll(arg0, arg1);
	}

	@SuppressWarnings("rawtypes")
	public void retrieveAll(Collection arg0) {
		pm.retrieveAll(arg0);
	}

	public void retrieveAll(Object... arg0) {
		pm.retrieveAll(arg0);
	}

	@SuppressWarnings("deprecation")
	public void retrieveAll(Object[] arg0, boolean arg1) {
		pm.retrieveAll(arg0, arg1);
	}

	public void setCopyOnAttach(boolean arg0) {
		pm.setCopyOnAttach(arg0);
	}

	public void setDatastoreReadTimeoutMillis(Integer arg0) {
		pm.setDatastoreReadTimeoutMillis(arg0);
	}

	public void setDatastoreWriteTimeoutMillis(Integer arg0) {
		pm.setDatastoreWriteTimeoutMillis(arg0);
	}

	public void setDetachAllOnCommit(boolean arg0) {
		pm.setDetachAllOnCommit(arg0);
	}

	public void setIgnoreCache(boolean arg0) {
		pm.setIgnoreCache(arg0);
	}

	public void setMultithreaded(boolean arg0) {
		pm.setMultithreaded(arg0);
	}

	public void setUserObject(Object arg0) {
		pm.setUserObject(arg0);
	}

}
