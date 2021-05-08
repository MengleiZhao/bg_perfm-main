<div class="row">
	<div class="col-md-12">
		<form id="tResearchOutlineTempEditForm">
			<input type="hidden" id="tResearchOutlineTemp_edit_idx" name="idX" value=${tResearchOutlineTempEdit.idX}>
				<div class="form-group">
					<label id="tResearchOutlineTemp_edit_titleLabel">调研提纲名称</label>
					<#if tResearchOutlineTempEdit.outlineName??>
						<input type="text" class="form-control" name="title" id="tResearchOutlineTemp_edit_title" value=${tResearchOutlineTempEdit.outlineName} placeholder="请输入调研提纲名称...">
					<#else>
						<input type="text" class="form-control" name="title" id="tResearchOutlineTemp_edit_title" value='无' placeholder="请输入调研提纲名称...">
					</#if>
				</div>


				<div class="form-group">
					<label id="createorLabel">调研提纲发布人</label>
					<#if tResearchOutlineTempEdit.createor??>
						<input type="text" class="form-control" name="createor" id="tResearchOutlineTemp_edit_createor" value=${tResearchOutlineTempEdit.createor} placeholder="请输入调研提纲发布人...">
					<#else>
						<input type="text" class="form-control" name="createor" id="tResearchOutlineTemp_edit_createor" value='无' placeholder="请输入调研提纲发布人...">
					</#if>
				</div>





			<div class="box-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
					<button onclick="tResearchOutlineTempEditUpdate();" type="button" class="btn btn-primary btn-sm"><i class="fa fa-paste"></i>更新</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	function tResearchOutlineTempEditUpdate(){
		$("span").remove(".errorClass");
		$("br").remove(".errorClass");
		var status = 1;
		if($("#tResearchOutlineTemp_edit_title").val()==""){
			$("#tResearchOutlineTemp_edit_titleLabel").prepend('<span class="errorClass" style="color:red">*调研提纲名称不能为空</span><br class="errorClass"/>');
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
			url: '${request.contextPath}/tResearchOutlineTemp/update',
			type: 'post',
			dataType: 'text',
			data: $("#tResearchOutlineTempEditForm").serialize(),
			success: function (data) {
				var json = JSON.parse(data);
				if (json.status){
					$("#lgModal").modal('hide');
					alertMsg("更新成功","success");
					reloadTable(list_ajax,"#roleTime","#rolePremise");
				}else{
					alertMsg("更新失败:"+json.msg,"success");
				}
			}
		};
		$.ajax(options);
	}
</script>