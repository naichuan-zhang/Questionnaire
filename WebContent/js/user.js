function setRole (role,text,id,username){
	$(("#s_"+id)).html(text);
	
	$.ajax({
		url: basePath+'login/updateRole',
		type: 'POST',
		data: {
			roleId:role,
			loginId:id,
			username:username
		},
		success: function(data){
			if(data.success) {
				var pText = document.getElementById("alertOKText");
				pText.innerText = "权限更改";
				$("#alertOK").modal();
			}else{
				var pText = document.getElementById("alertOKText");
				pText.innerText = "权限为正常修改，请联系管理员";
				$("#alertOK").modal();
			}
		},
		error: function(){
			alert ('服务器问题。');
		}
		});
	
}

function delModal (login_id){
	$("#temoLoginId").val(login_id);
	$("#delModal").modal();
	
}

function deleteUser (){
	
	var temoLoginId = $("#temoLoginId").val();
	$.ajax({
		url:basePath+"login/del/"+temoLoginId,
		type:'DELETE',
		success:function (data){
			if (data.success){
				window.location.reload();
			}
		}
	});
}