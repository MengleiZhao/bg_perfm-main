<div class="row">
	<div class="col-md-12">
		<form id="rcDictEditForm">
			<input type="hidden" id="id" name="id" value="${bean.id!}">
			<div class="box-body">
				<div class="form-group">
					<label id="dicCodeLabel">编码</label>
					<input type="text" class="form-control" name="dicCode" id="dicCode" value="${bean.dicCode!}" placeholder="编码...">
				</div>
				<div class="form-group">
					<label id="dicNameLabel">名称</label>
					<input type="text" class="form-control" name="dicName" id="dicName" value="${bean.dicName!}" placeholder="名称...">
				</div>
				<div class="form-group">
					<label id="typeLabel">类型</label>
					<input type="text" class="form-control" name="type" id="type" value="${bean.type!}" placeholder="类型...">
				</div>
				<div class="form-group">
					<label id="typeLabel">级联依赖</label>
					<input type="text" class="form-control" name="parentCode" id="parentCode" value="${bean.parentCode!}" placeholder="级联依赖...">
				</div>
				<div class="form-group">
					<label id="descriptLabel">描述</label>
					<input type="text" class="form-control" name="descript" id="descript" value="${bean.descript!}" placeholder="描述...">
				</div>
				<div class="form-group">
					<label id="sortLabel">排序</label>
					<input type="text" class="form-control" name="sort" id="sort" value="${bean.sort!}" placeholder="排序...">
				</div>
             </div>
			<div class="box-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
               		<button onclick="rcDictUpdate();" type="button" class="btn btn-primary btn-sm"><i class="fa fa-paste"></i>更新</button>
				</div>
          	</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	function rcDictUpdate(){
		$("span").remove(".errorClass");
		$("br").remove(".errorClass");
		var status = 1;
		if($("#dicCode").val()==""){
			$("#dicCodeLabel").prepend('<span class="errorClass" style="color:#ff0000">*编码不能为空</span><br class="errorClass"/>');
			status = 0;
		}
		if($("#dicName").val()==""){
			$("#dicNameLabel").prepend('<span class="errorClass" style="color:red">*名称不能为空</span><br class="errorClass"/>');
			status = 0;
		}
		if(status == 0){
			return false;
		}else{
			ajaxPost();
		}
	}
		
	function ajaxPost() {
		var options = {
	        url: '${request.contextPath}/rcDict/update',
	        type: 'post',
	        dataType: 'text',
	        data: $("#rcDictEditForm").serialize(),
	        success: function (data) {
                var json = JSON.parse(data);
                if (json.status){
                    $("#lgModal").modal('hide');
                    alertMsg("更新成功","success");
                    reloadTable(list_ajax);
                }else{
                    alertMsg("更新失败:"+json.msg,"success");
                }
	        }
		};
	$.ajax(options);
	}
</script>