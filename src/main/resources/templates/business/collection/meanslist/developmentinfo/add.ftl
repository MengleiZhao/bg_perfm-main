<div class="row">
	<div class="col-md-12">
		<form id="DevelopmentInfoAddForm">
		 <input type="hidden" id="fInId" name="fInId" value="${td.fInId!}">
			<div class="modal-body">
				<div class="form-group">
					<label id="userNoLabel">资料一级分类</label>
					<input type="text" class="form-control" name="fInfoType1" id="fInfoType1" value="${td.fInfoType1!}" placeholder="输入资料一级分类...">
				</div>
				<div class="form-group">
					<label id="passwordLabel">资料二级分类</label>
					<input type="text" class="form-control" name="fInfoType2" id="fInfoType2" value="${td.fInfoType2!}" placeholder="输入资料二级分类...">
				</div>
				<div class="form-group">
					<label id="nickNameLabel">资料名称</label>
					<input type="text" class="form-control" name="fInfoName" id="fInfoName" value="${td.fInfoName!}" placeholder="输入资料名称...">
				</div>
			</div>
			<div class="modal-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
					<button type="button" class="btn btn-primary btn-sm" onclick="developmentInfoSave();"><i class="fa fa-save"></i>保存</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
function developmentInfoSave(){
	$("span").remove(".errorClass");
	$("br").remove(".errorClass");
	var status = 1;
//	if($("#fInfoType1").val()==""){
//		$("#userNoLabel").prepend('<span class="errorClass" style="color:red">*账号不能为空</span><br class="errorClass"/>');
//		status = 0;
//	}
//	if($("#fInfoType2").val()==""){
//		$("#passwordLabel").prepend('<span class="errorClass" style="color:red">*密码不能为空</span><br class="errorClass"/>');
//		status = 0;
//	}
	if(status == 0){
		return false;
	}else{
		$.ajax({
			url: '${request.contextPath}/tDevelopmentInformationListTemp/save',
	        type: 'post',
	        dataType: 'text',
	        data: $("#DevelopmentInfoAddForm").serialize(),
	        success: function (data) {
                var json = JSON.parse(data);
                if (json.status){
                    $("#lgModal").modal('hide');
                    alertMsg("添加成功","success");
                    reloadTable(list_ajax);
                }else{
                    alertMsg("添加失败:"+json.msg,"success");
                }
	        }
		});
	}
}
</script>