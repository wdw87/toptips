window.onload=function(){
    var box_hight;  
    var box_width; 
    var con=document.getElementById("container");
    
    //位置赋值
    con.style.left="50%";
    con.style.top="50%";
    box_width=con.offsetWidth;  //获取盒子宽度
    box_hight=con.offsetHeight;  //获取盒子高度
    con.style.marginTop=-box_hight/2+"px";
    con.style.marginLeft=-box_width/2+"px";
}

function myLogin(){
	var username = document.loginForm.username.value;
	var password = document.loginForm.password.value;
	//判断用户名是否为空，不需要return
	if(document.loginForm.username.value=="")
	{
		window.alert("用户名不能为空！");
	}else
	{
		var request = getXMLHttpRequest();
		request.open("POST", "/login", true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var myObj = request.responseText;
				var myJSON =  JSON.parse(myObj);
				if(myJSON.code == 0){
					window.location.href="/";
				}else{
					var waining = "";
					if(myJSON.hasOwnProperty("msgname"))
						waining += myJSON.msgname + " ";
					if(myJSON.hasOwnProperty("msgpwd"))
						waining += myJSON.msgpwd + " ";
					window.alert(waining);
				}
			}
		};

		request.send("username=" + username + "&" + "password=" + password);
	}
}

function myRegist(){
	var username = document.loginForm.username.value;
	var password = document.loginForm.password.value;
	//判断用户名是否为空，不需要return
	if(document.loginForm.username.value=="")
	{
		window.alert("用户名不能为空！");
	}else
	{
		var request = getXMLHttpRequest();
		request.open("POST", "/reg", true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var myObj = request.responseText;
				var myJSON =  JSON.parse(myObj);
				if(myJSON.code == 0){
					window.location.href="/";
				}else{
					var waining = "";
					if(myJSON.hasOwnProperty("msgname"))
						waining += myJSON.msgname + " ";
					if(myJSON.hasOwnProperty("msgpwd"))
						waining += myJSON.msgpwd + " ";
					window.alert(waining);
				}
			}
		};
		request.send("username=" + username + "&" + "password=" + password);
	}
}
function getXMLHttpRequest(){
	var xhttp;
	if (window.XMLHttpRequest) {
		xhttp = new XMLHttpRequest();
	} else {
		// code for IE6, IE5
		xhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xhttp;
}