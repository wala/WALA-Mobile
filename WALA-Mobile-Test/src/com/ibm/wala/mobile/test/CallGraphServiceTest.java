package com.ibm.wala.mobile.test;

import org.junit.Test;

import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.test.ServiceTestCase;

import com.ibm.wala.mobile.CallGraphService;
import com.ibm.wala.util.collections.Pair;
import com.ibm.wala.util.graph.impl.SlowSparseNumberedGraph;

public class CallGraphServiceTest extends ServiceTestCase<CallGraphService> {

	public CallGraphServiceTest() {
		super(CallGraphService.class);
	}

	private Intent makeIntent() {
		Intent intent = new Intent();
		intent.setClass(getContext(), CallGraphService.class);
		return intent;
	}

	@Test  
	public void testStartable() {  
		Intent startIntent = makeIntent();  
		startService(startIntent);  
	}

	@Test  
	public void testBindable() {  
		Intent startIntent = makeIntent();  
		IBinder service = bindService(startIntent); 
		assert service != null;
	}  

	@Test  
	public void testCallGraph() throws RemoteException {  
		Intent startIntent = makeIntent();  
		IBinder service = bindService(startIntent); 
		
		Parcel callData = Parcel.obtain();
		Parcel returnData = Parcel.obtain();
		
		callData.writeString("/system/app/Browser/Browser.apk");
		service.transact(CallGraphService.CALL_GRAPH, callData, returnData, 0);

		@SuppressWarnings("unchecked")
		SlowSparseNumberedGraph<Pair<String,Integer>> CG = (SlowSparseNumberedGraph<Pair<String, Integer>>) returnData.readSerializable();
		
		callData.recycle();
		returnData.recycle();
		
		assert CG != null;
	}  

}
