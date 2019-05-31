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
	//判断用户名是否为空，不需要return
	if(document.loginForm.username.value=="")
	{
		window.alert("用户名不能为空！");
	}else
	{
        //使用form对象的submit()方法，实现提交，不用return
        document.loginForm.action="/login";
		document.loginForm.submit();
	}
}

function myRegist(){
	//判断用户名是否为空，不需要return
	if(document.loginForm.username.value=="")
	{
		window.alert("用户名不能为空！");
	}else
	{
        //使用form对象的submit()方法，实现提交，不用return
        document.loginForm.action="/reg";
		document.loginForm.submit();
	}
}