package b4j.example;

import anywheresoftware.b4a.debug.*;

import anywheresoftware.b4a.BA;

public class main extends javafx.application.Application{
public static main mostCurrent = new main();

public static BA ba;
static {
		ba = new  anywheresoftware.b4a.shell.ShellBA("b4j.example", "b4j.example.main", null);
		ba.loadHtSubs(main.class);
        if (ba.getClass().getName().endsWith("ShellBA")) {
			anywheresoftware.b4a.shell.ShellBA.delegateBA = new anywheresoftware.b4j.objects.FxBA("b4j.example", null, null);
			ba.raiseEvent2(null, true, "SHELL", false);
			ba.raiseEvent2(null, true, "CREATE", true, "b4j.example.main", ba);
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}

 
    public static void main(String[] args) {
    	launch(args);
    }
    public void start (javafx.stage.Stage stage) {
        try {
            if (!false)
                System.setProperty("prism.lcdtext", "false");
            anywheresoftware.b4j.objects.FxBA.application = this;
		    anywheresoftware.b4a.keywords.Common.setDensity(javafx.stage.Screen.getPrimary().getDpi());
            anywheresoftware.b4a.keywords.Common.LogDebug("Program started.");
            initializeProcessGlobals();
            anywheresoftware.b4j.objects.Form frm = new anywheresoftware.b4j.objects.Form();
            frm.initWithStage(ba, stage, 600, 600);
            ba.raiseEvent(null, "appstart", frm, (String[])getParameters().getRaw().toArray(new String[0]));
        } catch (Throwable t) {
            BA.printException(t, true);
            System.exit(1);
        }
    }


private static boolean processGlobalsRun;
public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4j.objects.JFX _fx = null;
public static anywheresoftware.b4j.objects.Form _mainform = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper _button1 = null;
public static String  _appstart(anywheresoftware.b4j.objects.Form _form1,String[] _args) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(ba, "appstart", false))
	 {return ((String) Debug.delegate(ba, "appstart", new Object[] {_form1,_args}));}
RDebugUtils.currentLine=65536;
 //BA.debugLineNum = 65536;BA.debugLine="Sub AppStart (Form1 As Form, Args() As String)";
RDebugUtils.currentLine=65537;
 //BA.debugLineNum = 65537;BA.debugLine="MainForm = Form1";
_mainform = _form1;
RDebugUtils.currentLine=65538;
 //BA.debugLineNum = 65538;BA.debugLine="MainForm.RootPane.LoadLayout(\"Layout1\")";
_mainform.getRootPane().LoadLayout(ba,"Layout1");
RDebugUtils.currentLine=65539;
 //BA.debugLineNum = 65539;BA.debugLine="MainForm.Show";
_mainform.Show();
RDebugUtils.currentLine=65540;
 //BA.debugLineNum = 65540;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(ba, "button1_click", false))
	 {return ((String) Debug.delegate(ba, "button1_click", null));}
cn.hutool.system.HostInfo _host_info = null;
cn.hutool.system.JavaInfo _java_info = null;
cn.hutool.system.JavaRuntimeInfo _jr = null;
cn.hutool.system.JavaSpecInfo _jvr = null;
cn.hutool.system.JvmInfo _jvm = null;
cn.hutool.system.JvmSpecInfo _jvm_spec = null;
cn.hutool.system.OsInfo _os = null;
cn.hutool.system.RuntimeInfo _rt_info = null;
cn.hutool.system.SystemUtil _sys_util = null;
cn.hutool.system.UserInfo _user_info = null;
RDebugUtils.currentLine=131072;
 //BA.debugLineNum = 131072;BA.debugLine="Sub Button1_Click";
RDebugUtils.currentLine=131073;
 //BA.debugLineNum = 131073;BA.debugLine="Dim host_info As HostInfo";
_host_info = new cn.hutool.system.HostInfo();
RDebugUtils.currentLine=131074;
 //BA.debugLineNum = 131074;BA.debugLine="Log(host_info.Address)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131074",_host_info.getAddress(),0);
RDebugUtils.currentLine=131075;
 //BA.debugLineNum = 131075;BA.debugLine="Log(host_info.Name)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131075",_host_info.getName(),0);
RDebugUtils.currentLine=131077;
 //BA.debugLineNum = 131077;BA.debugLine="Dim java_info As JavaInfo";
_java_info = new cn.hutool.system.JavaInfo();
RDebugUtils.currentLine=131078;
 //BA.debugLineNum = 131078;BA.debugLine="Log(java_info.isJava11)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131078",BA.ObjectToString(_java_info.isJava11()),0);
RDebugUtils.currentLine=131079;
 //BA.debugLineNum = 131079;BA.debugLine="Log(java_info.VendorURL)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131079",_java_info.getVendorURL(),0);
RDebugUtils.currentLine=131081;
 //BA.debugLineNum = 131081;BA.debugLine="Dim jr As JavaRuntimeInfo";
_jr = new cn.hutool.system.JavaRuntimeInfo();
RDebugUtils.currentLine=131082;
 //BA.debugLineNum = 131082;BA.debugLine="Log(jr.Name)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131082",_jr.getName(),0);
RDebugUtils.currentLine=131083;
 //BA.debugLineNum = 131083;BA.debugLine="Log(jr.HomeDir)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131083",_jr.getHomeDir(),0);
RDebugUtils.currentLine=131084;
 //BA.debugLineNum = 131084;BA.debugLine="Log(jr.LibraryPath)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131084",_jr.getLibraryPath(),0);
RDebugUtils.currentLine=131086;
 //BA.debugLineNum = 131086;BA.debugLine="Dim jvr As JavaSpecInfo";
_jvr = new cn.hutool.system.JavaSpecInfo();
RDebugUtils.currentLine=131087;
 //BA.debugLineNum = 131087;BA.debugLine="Log(jvr.Name)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131087",_jvr.getName(),0);
RDebugUtils.currentLine=131089;
 //BA.debugLineNum = 131089;BA.debugLine="Dim jvm As JvmInfo";
_jvm = new cn.hutool.system.JvmInfo();
RDebugUtils.currentLine=131090;
 //BA.debugLineNum = 131090;BA.debugLine="Log(jvm.Name)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131090",_jvm.getName(),0);
RDebugUtils.currentLine=131092;
 //BA.debugLineNum = 131092;BA.debugLine="Dim jvm_spec As JvmSpecInfo";
_jvm_spec = new cn.hutool.system.JvmSpecInfo();
RDebugUtils.currentLine=131093;
 //BA.debugLineNum = 131093;BA.debugLine="Log(jvm_spec.Name)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131093",_jvm_spec.getName(),0);
RDebugUtils.currentLine=131095;
 //BA.debugLineNum = 131095;BA.debugLine="Dim os As OsInfo";
_os = new cn.hutool.system.OsInfo();
RDebugUtils.currentLine=131096;
 //BA.debugLineNum = 131096;BA.debugLine="Log(os.isWindows10)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131096",BA.ObjectToString(_os.isWindows10()),0);
RDebugUtils.currentLine=131098;
 //BA.debugLineNum = 131098;BA.debugLine="Dim rt_info As RuntimeInfo";
_rt_info = new cn.hutool.system.RuntimeInfo();
RDebugUtils.currentLine=131099;
 //BA.debugLineNum = 131099;BA.debugLine="Log(rt_info.MaxMemory)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131099",BA.NumberToString(_rt_info.getMaxMemory()),0);
RDebugUtils.currentLine=131101;
 //BA.debugLineNum = 131101;BA.debugLine="Dim sys_util As SystemUtil";
_sys_util = new cn.hutool.system.SystemUtil();
RDebugUtils.currentLine=131104;
 //BA.debugLineNum = 131104;BA.debugLine="Dim user_info As UserInfo";
_user_info = new cn.hutool.system.UserInfo();
RDebugUtils.currentLine=131105;
 //BA.debugLineNum = 131105;BA.debugLine="Log(user_info.CurrentDir)";
anywheresoftware.b4a.keywords.Common.LogImpl("3131105",_user_info.getCurrentDir(),0);
RDebugUtils.currentLine=131106;
 //BA.debugLineNum = 131106;BA.debugLine="End Sub";
return "";
}
}