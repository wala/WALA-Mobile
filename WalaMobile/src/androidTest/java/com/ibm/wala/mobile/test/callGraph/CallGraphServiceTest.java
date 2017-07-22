package com.ibm.wala.mobile.test.callGraph;

import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.test.ServiceTestCase;
import android.util.Log;

import com.ibm.wala.mobile.CallGraphService;
import com.ibm.wala.util.collections.Pair;
import com.ibm.wala.util.graph.impl.SlowSparseNumberedGraph;
import com.ibm.wala.util.io.TemporaryFile;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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
		Assert.assertNotNull(service);
	}  

	/* get the libraries story straight, and enable this
	@Test
	public void testCallGraph() throws RemoteException, IOException {
		Intent startIntent = makeIntent();  
		IBinder service = bindService(startIntent); 
		
		Parcel callData = Parcel.obtain();
		Parcel returnData = Parcel.obtain();
		
		String appName = "com.ibm.wala.core.testdata_1.0.0a.dex";
		File app = new File(getContext().getDataDir(), "testdata.dex");
		TemporaryFile.streamToFile(app, InstrumentationRegistry.getContext().getAssets().open(appName));

		callData.writeString(app.getAbsolutePath());
		callData.writeString("LdynamicCG/MainClass");
		service.transact(CallGraphService.MAIN_CALL_GRAPH, callData, returnData, 0);

		returnData.setDataPosition(0);
		@SuppressWarnings("unchecked")
		SlowSparseNumberedGraph<Pair<String,Integer>> CG = (SlowSparseNumberedGraph<Pair<String, Integer>>) returnData.readSerializable();
		
		Log.i("CallGraphServiceTest", CG.toString());

		callData.recycle();
		returnData.recycle();
		
		assert CG != null;
	}  
    */
}
