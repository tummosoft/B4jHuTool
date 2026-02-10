package b4j.example;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.pc.*;

public class main_subs_0 {


public static RemoteObject  _appstart(RemoteObject _form1,RemoteObject _args) throws Exception{
try {
		Debug.PushSubsStack("AppStart (main) ","main",0,main.ba,main.mostCurrent,18);
if (RapidSub.canDelegate("appstart")) { return b4j.example.main.remoteMe.runUserSub(false, "main","appstart", _form1, _args);}
RemoteObject _setting_file = RemoteObject.createImmutable("");
Debug.locals.put("Form1", _form1);
Debug.locals.put("Args", _args);
 BA.debugLineNum = 18;BA.debugLine="Sub AppStart (Form1 As Form, Args() As String)";
Debug.ShouldStop(131072);
 BA.debugLineNum = 19;BA.debugLine="MainForm = Form1";
Debug.ShouldStop(262144);
main._mainform = _form1;
 BA.debugLineNum = 20;BA.debugLine="MainForm.RootPane.LoadLayout(\"Layout1\")";
Debug.ShouldStop(524288);
main._mainform.runMethod(false,"getRootPane").runMethodAndSync(false,"LoadLayout",main.ba,(Object)(RemoteObject.createImmutable("Layout1")));
 BA.debugLineNum = 21;BA.debugLine="MainForm.Show";
Debug.ShouldStop(1048576);
main._mainform.runVoidMethodAndSync ("Show");
 BA.debugLineNum = 23;BA.debugLine="If File.Exists(File.DirApp, \"setting.ini\") = Fals";
Debug.ShouldStop(4194304);
if (RemoteObject.solveBoolean("=",main.__c.getField(false,"File").runMethod(true,"Exists",(Object)(main.__c.getField(false,"File").runMethod(true,"getDirApp")),(Object)(RemoteObject.createImmutable("setting.ini"))),main.__c.getField(true,"False"))) { 
 BA.debugLineNum = 24;BA.debugLine="File.WriteString(File.DirApp, \"setting.ini\", \"\")";
Debug.ShouldStop(8388608);
main.__c.getField(false,"File").runVoidMethod ("WriteString",(Object)(main.__c.getField(false,"File").runMethod(true,"getDirApp")),(Object)(BA.ObjectToString("setting.ini")),(Object)(RemoteObject.createImmutable("")));
 };
 BA.debugLineNum = 27;BA.debugLine="Dim setting_file As String = File.Combine(File.Di";
Debug.ShouldStop(67108864);
_setting_file = main.__c.getField(false,"File").runMethod(true,"Combine",(Object)(main.__c.getField(false,"File").runMethod(true,"getDirApp")),(Object)(RemoteObject.createImmutable("setting.ini")));Debug.locals.put("setting_file", _setting_file);Debug.locals.put("setting_file", _setting_file);
 BA.debugLineNum = 28;BA.debugLine="setting.Initialize2(setting_file, \"UTF-8\", True)";
Debug.ShouldStop(134217728);
main._setting.runVoidMethod ("Initialize2",main.ba,(Object)(_setting_file),(Object)(BA.ObjectToString("UTF-8")),(Object)(main.__c.getField(true,"True")));
 BA.debugLineNum = 30;BA.debugLine="setting.Set(\"server\", \"localhost\")";
Debug.ShouldStop(536870912);
main._setting.runVoidMethod ("Set",(Object)(BA.ObjectToString("server")),(Object)(RemoteObject.createImmutable("localhost")));
 BA.debugLineNum = 32;BA.debugLine="setting.setByGroup(\"user\", \"account\", \"admin\")";
Debug.ShouldStop(-2147483648);
main._setting.runVoidMethod ("SetByGroup",(Object)(BA.ObjectToString("user")),(Object)(BA.ObjectToString("account")),(Object)(RemoteObject.createImmutable("admin")));
 BA.debugLineNum = 33;BA.debugLine="setting.setByGroup(\"password\", \"account\", \"123456";
Debug.ShouldStop(1);
main._setting.runVoidMethod ("SetByGroup",(Object)(BA.ObjectToString("password")),(Object)(BA.ObjectToString("account")),(Object)(RemoteObject.createImmutable("123456")));
 BA.debugLineNum = 34;BA.debugLine="setting.setByGroup(\"facebook\", \"social\", \"https:/";
Debug.ShouldStop(2);
main._setting.runVoidMethod ("SetByGroup",(Object)(BA.ObjectToString("facebook")),(Object)(BA.ObjectToString("social")),(Object)(RemoteObject.createImmutable("https://facebook.com/admin")));
 BA.debugLineNum = 35;BA.debugLine="setting.setByGroup(\"twitter\", \"social\", \"https://";
Debug.ShouldStop(4);
main._setting.runVoidMethod ("SetByGroup",(Object)(BA.ObjectToString("twitter")),(Object)(BA.ObjectToString("social")),(Object)(RemoteObject.createImmutable("https://x.com/admin")));
 BA.debugLineNum = 37;BA.debugLine="setting.Store";
Debug.ShouldStop(16);
main._setting.runVoidMethod ("Store");
 BA.debugLineNum = 38;BA.debugLine="End Sub";
Debug.ShouldStop(32);
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
		Debug.PushSubsStack("Button1_Click (main) ","main",0,main.ba,main.mostCurrent,40);
if (RapidSub.canDelegate("button1_click")) { return b4j.example.main.remoteMe.runUserSub(false, "main","button1_click");}
RemoteObject _server = RemoteObject.createImmutable(false);
RemoteObject _user = RemoteObject.createImmutable(false);
 BA.debugLineNum = 40;BA.debugLine="Sub Button1_Click";
Debug.ShouldStop(128);
 BA.debugLineNum = 41;BA.debugLine="Dim server As Boolean = setting.containsKey(\"serv";
Debug.ShouldStop(256);
_server = main._setting.runMethod(true,"containsKey",(Object)(RemoteObject.createImmutable("server")));Debug.locals.put("server", _server);Debug.locals.put("server", _server);
 BA.debugLineNum = 42;BA.debugLine="Log(server)";
Debug.ShouldStop(512);
main.__c.runVoidMethod ("LogImpl","8131074",BA.ObjectToString(_server),0);
 BA.debugLineNum = 43;BA.debugLine="Dim user As Boolean = setting.containsKey2(\"accou";
Debug.ShouldStop(1024);
_user = main._setting.runMethod(true,"containsKey2",(Object)(BA.ObjectToString("account")),(Object)(RemoteObject.createImmutable("user")));Debug.locals.put("user", _user);Debug.locals.put("user", _user);
 BA.debugLineNum = 44;BA.debugLine="Log(user)";
Debug.ShouldStop(2048);
main.__c.runVoidMethod ("LogImpl","8131076",BA.ObjectToString(_user),0);
 BA.debugLineNum = 45;BA.debugLine="End Sub";
Debug.ShouldStop(4096);
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
 //BA.debugLineNum = 10;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private fx As JFX";
main._fx = RemoteObject.createNew ("anywheresoftware.b4j.objects.JFX");
 //BA.debugLineNum = 12;BA.debugLine="Private MainForm As Form";
main._mainform = RemoteObject.createNew ("anywheresoftware.b4j.objects.Form");
 //BA.debugLineNum = 13;BA.debugLine="Private xui As XUI";
main._xui = RemoteObject.createNew ("anywheresoftware.b4a.objects.B4XViewWrapper.XUI");
 //BA.debugLineNum = 14;BA.debugLine="Private Button1 As B4XView";
main._button1 = RemoteObject.createNew ("anywheresoftware.b4a.objects.B4XViewWrapper");
 //BA.debugLineNum = 15;BA.debugLine="Dim setting As SettingHelper";
main._setting = RemoteObject.createNew ("com.tummosoft.SettingHelper");
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return RemoteObject.createImmutable("");
}
}