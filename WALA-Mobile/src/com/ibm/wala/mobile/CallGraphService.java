package com.ibm.wala.mobile;

import static com.ibm.wala.mobile.Libraries.*;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Iterator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.ibm.wala.dalvik.test.callGraph.DalvikCallGraphTestBase;
import com.ibm.wala.ipa.callgraph.AnalysisOptions.ReflectionOptions;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.NullProgressMonitor;
import com.ibm.wala.util.collections.Pair;
import com.ibm.wala.util.graph.impl.SlowSparseNumberedGraph;

public class CallGraphService extends Service {

	public static final int APK_CALL_GRAPH = IBinder.FIRST_CALL_TRANSACTION;
	public static final int MAIN_CALL_GRAPH = APK_CALL_GRAPH + 1;
	
	private static String WALA_INTERFACE = "WALA";
	
	private Pair<String,Integer> node(CGNode n) {
		return Pair.make("" + n.getMethod().getName() + n.getMethod().getDescriptor(), n.getGraphNodeId());
	}
	
	private SlowSparseNumberedGraph<Pair<String,Integer>> writeable(CallGraph CG) {
		SlowSparseNumberedGraph<Pair<String,Integer>> result = SlowSparseNumberedGraph.make();
		
		for(CGNode n : CG) {
			result.addNode(node(n));
		}

		for(CGNode n : CG) {
			for(Iterator<CGNode> ss = CG.getSuccNodes(n); ss.hasNext(); ) {
				result.addEdge(node(n), node(ss.next()));
			}
		}
		
		return result;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return new IBinder() {

			@Override
			public String getInterfaceDescriptor() throws RemoteException {
				return WALA_INTERFACE;
			}

			@Override
			public boolean pingBinder() {
				return true;
			}

			@Override
			public boolean isBinderAlive() {
				return true;
			}

			@Override
			public IInterface queryLocalInterface(String descriptor) {
				final IBinder that = this;
				return new IInterface() {
					@Override
					public IBinder asBinder() {
						return that;
					}
				};
			}

			@Override
			public void dump(FileDescriptor fd, String[] args)
					throws RemoteException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void dumpAsync(FileDescriptor fd, String[] args)
					throws RemoteException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
				try {
					data.setDataPosition(0);
					String programFile = data.readString();
					
					Pair<CallGraph,PointerAnalysis<InstanceKey>> x;
					if (code == APK_CALL_GRAPH) {
						x = DalvikCallGraphTestBase.makeAPKCallGraph(systemLibs(), null, programFile, new NullProgressMonitor(), ReflectionOptions.ONE_FLOW_TO_CASTS_APPLICATION_GET_METHOD);
					
					} else {
						String mainClassName = data.readString();
						x = DalvikCallGraphTestBase.makeDalvikCallGraph(coreLibs(), null, mainClassName, programFile);
					}
					
					reply.writeSerializable(writeable(x.fst));
					return true;
				} catch (ClassHierarchyException | 
						IllegalArgumentException | 
						IOException | 
						CancelException e) {
					reply.writeException(e);
					return false;
				}
			}

			@Override
			public void linkToDeath(DeathRecipient recipient, int flags)
					throws RemoteException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
	}

}
