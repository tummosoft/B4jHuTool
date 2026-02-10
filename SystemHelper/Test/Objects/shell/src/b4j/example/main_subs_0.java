package b4j.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class main_subs_0 {


public static RemoteObject  _appstart(RemoteObject _form1,RemoteObject _args) throws Exception{
try {
		Debug.PushSubsStack("AppStart (main) ","main",0,main.ba,main.mostCurrent,16);
if (RapidSub.canDelegate("appstart")) { return b4j.example.main.remoteMe.runUserSub(false, "main","appstart", _form1, _args);}
Debug.locals.put("Form1", _form1);
Debug.locals.put("Args", _args);
 BA.debugLineNum = 16;BA.debugLine="Sub AppStart (Form1 As Form, Args() As String)";
Debug.ShouldStop(32768);
 BA.debugLineNum = 17;BA.debugLine="MainForm = Form1";
Debug.ShouldStop(65536);
main._mainform = _form1;
 BA.debugLineNum = 18;BA.debugLine="MainForm.RootPane.LoadLayout(\"Layout1\")";
Debug.ShouldStop(131072);
main._mainform.runMethod(false,"getRootPane").runMethodAndSync(false,"LoadLayout",main.ba,(Object)(RemoteObject.createImmutable("Layout1")));
 BA.debugLineNum = 19;BA.debugLine="MainForm.Show";
Debug.ShouldStop(262144);
main._mainform.runVoidMethodAndSync ("Show");
 BA.debugLineNum = 20;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}
public static RemoteObject  _button1_click() throws Exception{
try {
		Debug.PushSubsStack("Button1_Click (main) ","main",0,main.ba,main.mostCurrent,22);
if (RapidSub.canDelegate("button1_click")) { return b4j.example.main.remoteMe.runUserSub(false, "main","button1_click");}
RemoteObject _host_info = RemoteObject.declareNull("cn.hutool.system.HostInfo");
RemoteObject _java_info = RemoteObject.declareNull("cn.hutool.system.JavaInfo");
RemoteObject _jr = RemoteObject.declareNull("cn.hutool.system.JavaRuntimeInfo");
RemoteObject _jvr = RemoteObject.declareNull("cn.hutool.system.JavaSpecInfo");
RemoteObject _jvm = RemoteObject.declareNull("cn.hutool.system.JvmInfo");
RemoteObject _jvm_spec = RemoteObject.declareNull("cn.hutool.system.JvmSpecInfo");
RemoteObject _os = RemoteObject.declareNull("cn.hutool.system.OsInfo");
RemoteObject _rt_info = RemoteObject.declareNull("cn.hutool.system.RuntimeInfo");
RemoteObject _sys_util = RemoteObject.declareNull("cn.hutool.system.SystemUtil");
RemoteObject _user_info = RemoteObject.declareNull("cn.hutool.system.UserInfo");
 BA.debugLineNum = 22;BA.debugLine="Sub Button1_Click";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 23;BA.debugLine="Dim host_info As HostInfo";
Debug.ShouldStop(4194304);
_host_info = RemoteObject.createNew ("cn.hutool.system.HostInfo");Debug.locals.put("host_info", _host_info);
 BA.debugLineNum = 24;BA.debugLine="Log(host_info.Address)";
Debug.ShouldStop(8388608);
main.__c.runVoidMethod ("LogImpl","3131074",_host_info.runMethod(true,"getAddress"),0);
 BA.debugLineNum = 25;BA.debugLine="Log(host_info.Name)";
Debug.ShouldStop(16777216);
main.__c.runVoidMethod ("LogImpl","3131075",_host_info.runMethod(true,"getName"),0);
 BA.debugLineNum = 27;BA.debugLine="Dim java_info As JavaInfo";
Debug.ShouldStop(67108864);
_java_info = RemoteObject.createNew ("cn.hutool.system.JavaInfo");Debug.locals.put("java_info", _java_info);
 BA.debugLineNum = 28;BA.debugLine="Log(java_info.isJava11)";
Debug.ShouldStop(134217728);
main.__c.runVoidMethod ("LogImpl","3131078",BA.ObjectToString(_java_info.runMethod(true,"isJava11")),0);
 BA.debugLineNum = 29;BA.debugLine="Log(java_info.VendorURL)";
Debug.ShouldStop(268435456);
main.__c.runVoidMethod ("LogImpl","3131079",_java_info.runMethod(true,"getVendorURL"),0);
 BA.debugLineNum = 31;BA.debugLine="Dim jr As JavaRuntimeInfo";
Debug.ShouldStop(1073741824);
_jr = RemoteObject.createNew ("cn.hutool.system.JavaRuntimeInfo");Debug.locals.put("jr", _jr);
 BA.debugLineNum = 32;BA.debugLine="Log(jr.Name)";
Debug.ShouldStop(-2147483648);
main.__c.runVoidMethod ("LogImpl","3131082",_jr.runMethod(true,"getName"),0);
 BA.debugLineNum = 33;BA.debugLine="Log(jr.HomeDir)";
Debug.ShouldStop(1);
main.__c.runVoidMethod ("LogImpl","3131083",_jr.runMethod(true,"getHomeDir"),0);
 BA.debugLineNum = 34;BA.debugLine="Log(jr.LibraryPath)";
Debug.ShouldStop(2);
main.__c.runVoidMethod ("LogImpl","3131084",_jr.runMethod(true,"getLibraryPath"),0);
 BA.debugLineNum = 36;BA.debugLine="Dim jvr As JavaSpecInfo";
Debug.ShouldStop(8);
_jvr = RemoteObject.createNew ("cn.hutool.system.JavaSpecInfo");Debug.locals.put("jvr", _jvr);
 BA.debugLineNum = 37;BA.debugLine="Log(jvr.Name)";
Debug.ShouldStop(16);
main.__c.runVoidMethod ("LogImpl","3131087",_jvr.runMethod(true,"getName"),0);
 BA.debugLineNum = 39;BA.debugLine="Dim jvm As JvmInfo";
Debug.ShouldStop(64);
_jvm = RemoteObject.createNew ("cn.hutool.system.JvmInfo");Debug.locals.put("jvm", _jvm);
 BA.debugLineNum = 40;BA.debugLine="Log(jvm.Name)";
Debug.ShouldStop(128);
main.__c.runVoidMethod ("LogImpl","3131090",_jvm.runMethod(true,"getName"),0);
 BA.debugLineNum = 42;BA.debugLine="Dim jvm_spec As JvmSpecInfo";
Debug.ShouldStop(512);
_jvm_spec = RemoteObject.createNew ("cn.hutool.system.JvmSpecInfo");Debug.locals.put("jvm_spec", _jvm_spec);
 BA.debugLineNum = 43;BA.debugLine="Log(jvm_spec.Name)";
Debug.ShouldStop(1024);
main.__c.runVoidMethod ("LogImpl","3131093",_jvm_spec.runMethod(true,"getName"),0);
 BA.debugLineNum = 45;BA.debugLine="Dim os As OsInfo";
Debug.ShouldStop(4096);
_os = RemoteObject.createNew ("cn.hutool.system.OsInfo");Debug.locals.put("os", _os);
 BA.debugLineNum = 46;BA.debugLine="Log(os.isWindows10)";
Debug.ShouldStop(8192);
main.__c.runVoidMethod ("LogImpl","3131096",BA.ObjectToString(_os.runMethod(true,"isWindows10")),0);
 BA.debugLineNum = 48;BA.debugLine="Dim rt_info As RuntimeInfo";
Debug.ShouldStop(32768);
_rt_info = RemoteObject.createNew ("cn.hutool.system.RuntimeInfo");Debug.locals.put("rt_info", _rt_info);
 BA.debugLineNum = 49;BA.debugLine="Log(rt_info.MaxMemory)";
Debug.ShouldStop(65536);
main.__c.runVoidMethod ("LogImpl","3131099",BA.NumberToString(_rt_info.runMethod(true,"getMaxMemory")),0);
 BA.debugLineNum = 51;BA.debugLine="Dim sys_util As SystemUtil";
Debug.ShouldStop(262144);
_sys_util = RemoteObject.createNew ("cn.hutool.system.SystemUtil");Debug.locals.put("sys_util", _sys_util);
 BA.debugLineNum = 54;BA.debugLine="Dim user_info As UserInfo";
Debug.ShouldStop(2097152);
_user_info = RemoteObject.createNew ("cn.hutool.system.UserInfo");Debug.locals.put("user_info", _user_info);
 BA.debugLineNum = 55;BA.debugLine="Log(user_info.CurrentDir)";
Debug.ShouldStop(4194304);
main.__c.runVoidMethod ("LogImpl","3131105",_user_info.runMethod(true,"getCurrentDir"),0);
 BA.debugLineNum = 56;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return RemoteObject.createImmutable("");
}
catch (Exception e) {
			throw Debug.ErrorCaught(e);
		} 
finally {
			Debug.PopSubsStack();
		}}

private static boolean processGlobalsRun;
public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main_subs_0._process_globals();
main.myClass = BA.getDeviceClass ("b4j.example.main");
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static RemoteObject  _process_globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Private fx As JFX";
main._fx = RemoteObject.createNew ("anywheresoftware.b4j.objects.JFX");
 //BA.debugLineNum = 11;BA.debugLine="Private MainForm As Form";
main._mainform = RemoteObject.createNew ("anywheresoftware.b4j.objects.Form");
 //BA.debugLineNum = 12;BA.debugLine="Private xui As XUI";
main._xui = RemoteObject.createNew ("anywheresoftware.b4a.objects.B4XViewWrapper.XUI");
 //BA.debugLineNum = 13;BA.debugLine="Private Button1 As B4XView";
main._button1 = RemoteObject.createNew ("anywheresoftware.b4a.objects.B4XViewWrapper");
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
}