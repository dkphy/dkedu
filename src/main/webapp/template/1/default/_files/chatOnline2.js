function ib_wopen(b, a) {
	if (typeof (b) == "undefined") {
		url = ib_.chaturl
	} else {
		if (typeof (a) == "undefined" || a == 3) {
			url = ib_.chaturl + "&ct=3&opid=" + b
		} else {
			if (a == 2) {
				url = ib_.chaturl + "&ct=2&opid=" + b
			}
		}
	}
	url = url + "&t=" + (new Date()).getTime();
	newwin = window.open(url, "_blank", ib_.chatwin);
	newwin.focus()
}
document
		.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="1" height="1" align="middle" id="mainserverim"><param name="allowScriptAccess" value="always" /><param name="movie" value="'
				+ ib_.fu
				+ '/img/g.swf"/><param name="quality" value="high" /><param name="bgcolor" value="#ffffff" /><embed name="mainserverim" src="'
				+ ib_.fu
				+ '/img/g.swf" quality="high" bgcolor="#ffffff" width="1" height="1" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" swLiveConnect="true"/></object>');
//setTimeout("ib_s1()", 2000);
if (typeof (ib_.code_id) == "undefined") {
	ib_.chaturl = ib_.tu + "/chat-" + ib_.l + ".html?l=" + ib_.l
} else {
	ib_.chaturl = ib_.tu + "/chat-" + ib_.l + "_" + ib_.code_id + ".html?l="
			+ ib_.l + "&code_id=" + ib_.code_id
}
ib_.chaturl += "&page=" + escape(location.href);
ib_.chatwin = "toolbar=0,scrollbars=0,location=0,menubar=0,resizable=1,z-look=yes,screenX=200,screenY=200,width=650,height=440";
var ib_icon_o;
/**
if (ib_.img_hide == 0) {
	ib_icon_o = new ib_icon();
	ib_icon_o.init();
	eval('setTimeout("ib_icon_o.autoScroll()", ' + ib_icon_o.loopmillisecond
			+ ");")
}
**/
document.close();