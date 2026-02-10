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
public static com.tummosoft.SettingHelper _setting = null;
public static String  _appstart(anywheresoftware.b4j.objects.Form _form1,String[] _args) throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(ba, "appstart", false))
	 {return ((String) Debug.delegate(ba, "appstart", new Object[] {_form1,_args}));}
String _setting_file = "";
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
RDebugUtils.currentLine=65541;
 //BA.debugLineNum = 65541;BA.debugLine="If File.Exists(File.DirApp, \"setting.ini\") = Fals";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirApp(),"setting.ini")==anywheresoftware.b4a.keywords.Common.False) { 
RDebugUtils.currentLine=65542;
 //BA.debugLineNum = 65542;BA.debugLine="File.WriteString(File.DirApp, \"setting.ini\", \"\")";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirApp(),"setting.ini","");
 };
RDebugUtils.currentLine=65545;
 //BA.debugLineNum = 65545;BA.debugLine="Dim setting_file As String = File.Combine(File.Di";
_setting_file = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirApp(),"setting.ini");
RDebugUtils.currentLine=65546;
 //BA.debugLineNum = 65546;BA.debugLine="setting.Initialize2(setting_file, \"UTF-8\", True)";
_setting.Initialize2(ba,_setting_file,"UTF-8",anywheresoftware.b4a.keywords.Common.True);
RDebugUtils.currentLine=65548;
 //BA.debugLineNum = 65548;BA.debugLine="setting.Set(\"server\", \"localhost\")";
_setting.Set("server","localhost");
RDebugUtils.currentLine=65550;
 //BA.debugLineNum = 65550;BA.debugLine="setting.setByGroup(\"user\", \"account\", \"admin\")";
_setting.SetByGroup("user","account","admin");
RDebugUtils.currentLine=65551;
 //BA.debugLineNum = 65551;BA.debugLine="setting.setByGroup(\"password\", \"account\", \"123456";
_setting.SetByGroup("password","account","123456");
RDebugUtils.currentLine=65552;
 //BA.debugLineNum = 65552;BA.debugLine="setting.setByGroup(\"facebook\", \"social\", \"https:/";
_setting.SetByGroup("facebook","social","https://facebook.com/admin");
RDebugUtils.currentLine=65553;
 //BA.debugLineNum = 65553;BA.debugLine="setting.setByGroup(\"twitter\", \"social\", \"https://";
_setting.SetByGroup("twitter","social","https://x.com/admin");
RDebugUtils.currentLine=65555;
 //BA.debugLineNum = 65555;BA.debugLine="setting.Store";
_setting.Store();
RDebugUtils.currentLine=65556;
 //BA.debugLineNum = 65556;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
RDebugUtils.currentModule="main";
if (Debug.shouldDelegate(ba, "button1_click", false))
	 {return ((String) Debug.delegate(ba, "button1_click", null));}
boolean _server = false;
boolean _user = false;
RDebugUtils.currentLine=131072;
 //BA.debugLineNum = 131072;BA.debugLine="Sub Button1_Click";
RDebugUtils.currentLine=131073;
 //BA.debugLineNum = 131073;BA.debugLine="Dim server As Boolean = setting.containsKey(\"serv";
_server = _setting.containsKey("server");
RDebugUtils.currentLine=131074;
 //BA.debugLineNum = 131074;BA.debugLine="Log(server)";
anywheresoftware.b4a.keywords.Common.LogImpl("8131074",BA.ObjectToString(_server),0);
RDebugUtils.currentLine=131075;
 //BA.debugLineNum = 131075;BA.debugLine="Dim user As Boolean = setting.containsKey2(\"accou";
_user = _setting.containsKey2("account","user");
RDebugUtils.currentLine=131076;
 //BA.debugLineNum = 131076;BA.debugLine="Log(user)";
anywheresoftware.b4a.keywords.Common.LogImpl("8131076",BA.ObjectToString(_user),0);
RDebugUtils.currentLine=131077;
 //BA.debugLineNum = 131077;BA.debugLine="End Sub";
return "";
}
}