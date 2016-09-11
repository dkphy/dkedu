/**
 * 注册检查 start---
 */
function checkmobile(ctx) {
	if(ctx == null) {
		ctx = "";
	} else {
		ctx = ctx + "/";
	}
	var mobile = $("#mobile").val();
	if (!isMobile(mobile)) {
		$("#warn_mobile").css('display', 'block');
		$("#warn_mobile").html('！请输入正确的手机号');
		return false;
	}
	// 检查手机号是否存在
	$.post(ctx + "check_mobile.jspx", {
		"mobile" : mobile
	}, function(data) {
		var d = $.parseJSON(data);
		if (d !== true) {
			$("#warn_mobile").html("！手机号已被使用");
			$("#warn_mobile").css("display", "block");
			return false;
		}
	});
	$("#warn_mobile").css('display', 'none');
	return true;
}
function isMobile(str) {
	var reg = /^1\d{10}$/;
	return reg.test(str);
}


function checkemail() {
	var userEmail = $("#email").val();
	if (!isEmail(userEmail)) {
		$("#warn_email").html("！邮箱格式不正确");
		$("#warn_email").css("display", "block");
		return false;
	}
	// 检查邮箱是否存在
	$.post("check_email.jspx", {
		"email" : userEmail
	}, function(data) {
		var d = $.parseJSON(data);
		if (d !== true) {
			$("#warn_email").html("！邮箱已被使用");
			$("#warn_email").css("display", "block");
		}
	});
	$("#warn_email").css("display", "none");
	return true;
}
function isEmail(str) {
	var reg = /[a-z0-9-]{1,30}@[a-z0-9-]{1,65}.[a-z]{2,4}/;
	return reg.test(str);
}


function checkname(ctx) {
	if(ctx == null) {
		ctx = "";
	}else {
		ctx = ctx + "/";
	}
	var name = $("#username").val();
	if (name.length < 3 || name.length > 15) {
		$("#warn_username").html("！昵称长度须在3-15之间");
		$("#warn_username").css("display", "block");
		return false;
	}
	// 检查昵称是否存在
	$.post(ctx + "check_username.jspx", {
		"username" : name
	}, function(data) {
		var d = $.parseJSON(data);
		if (d !== true) {
			$("#warn_username").html("！昵称已存在");
			$("#warn_username").css("display", "block");
		}
	});
	$("#warn_username").css("display", "none");
	return true;
}


function checkpass() {
	var userPass = $("#password").val();
	if (userPass.length < 6 || userPass.length > 15) {
		$("#warn_password").html("！密码长度须在6-15之间");
		$("#warn_password").css("display", "block");
		return false;
	} else {
		$("#warn_password").css("display", "none");
		return true;
	}
}
function checkrpass() {
	var userPass = $("#password").val();
	var userRPass = $("#passwordAgain").val();
	if (userPass != userRPass) {
		$("#warn_passwordAgain").html("！两次输入的密码不一致");
		$("#warn_passwordAgain").css("display", "block");
		return false;
	} else {
		$("#warn_passwordAgain").css("display", "none");
		return true;
	}
}
// 注册检查 end---


function sendSmsCode(action) {
	var mobile = $("#mobile").val();
	if (!isMobile(mobile)) {
		$("#warn_mobile").css('display', 'block');
		$("#warn_mobile").html('！请输入正确的手机号');
		return false;
	}
	$("#warn_mobile").css('display', 'none');
	// 发送手机验证码
	$.post(action, {"mobile" : mobile}, function(data) {
		var d = $.parseJSON(data);
		if (d !== true) {
			alert('验证码发送失败');
		} else {
			alert('验证码发送成功');
		}
	});
}

function sendEmail() {
	var email = $("#email").val();
	if(!isEmail(email)){
    	$("#email_hint").html("！邮箱格式不正确");
    	$("#email_hint").css("display", "block");
        return false;
    }
	// 发送邮件
    $.post("forgot_password_send_email.jspx", {"email": email },function(data){
        var d = $.parseJSON(data);
        if(d !== true){
        	$("#email_hint").html("！该邮箱未注册用户");
        	$("#email_hint").css("display", "block");
        	return false;
        } else {
		    $("#email_hint").html("邮件已发送，请查收验证邮件并重新设定密码。");
    		$("#email_hint").css("display", "block");
        }
    });
}