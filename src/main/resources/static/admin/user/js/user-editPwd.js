$("#form-edit").validate({
	rules:{
        user_pwd:{
			required:true,
			minlength: 5,
			maxlength: 20
		},
		confirm:{
			required:true,
			minlength: 5,
			maxlength: 20,
			equalTo:"#user_pwd"
		}
		
	},
	messages: {
        user_pwd: {
             required: "请输入新密码",
             minlength: "密码不能小于6个字符",
             maxlength: "密码不能大于20个字符"
         },
         confirm: {
             required: "请再次输入新密码",
             equalTo: "两次密码输入不一致"
         }
    },
	submitHandler:function(form){
		edit();
	}
});


function edit() {
	var dataFormJson=$("#form-edit").serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : rootPath + "/user/editPwd",
		data : dataFormJson,
		async : false,
		error : function(request) {
			$.modal.alertError("系统错误");
		},
		success : function(data) {
			$.operate.saveSuccess(data);
		}
	});
}
