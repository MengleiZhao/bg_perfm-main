<div class="row">
	<div class="col-md-12">
		<form id="noticeEditForm">
			<input type="hidden" id="tNoticeMgr_edit_idx" name="idx" value=${tNoticeMgrEdit.idx}>
			<div class="box-body">
				<div class="form-group">
					<label id="tNoticeMgr_edit_titleLabel">公告标题</label>
					<#if tNoticeMgrEdit.title??>
						<input type="text" class="form-control" name="title" id="tNoticeMgr_edit_title" value=${tNoticeMgrEdit.title} placeholder="公告标题...">
					<#else>
						<input type="text" class="form-control" name="title" id="tNoticeMgr_edit_title" value='无' placeholder="公告标题...">
					</#if>

				</div>
				<div class="form-group">
					<label id="tNoticeMgr_edit_subtitleLabel">公告副标题</label>
					<#if tNoticeMgrEdit.subtitle??>
						<input type="text" class="form-control" name="subtitle" id="tNoticeMgr_edit_subtitle" value=${tNoticeMgrEdit.subtitle} placeholder="公告副标题...">
					<#else>
						<input type="text" class="form-control" name="subtitle" id="tNoticeMgr_edit_subtitle" value='无' placeholder="公告副标题...">
					</#if>
				</div>


				<div class="form-group">
					<label id="tNoticeMgr_edit_noticeContentLabel">公告发布内容</label>
					<#if tNoticeMgrEdit.noticeContent??>
						<input type="text" class="form-control" name="noticeContent" id="tNoticeMgr_edit_noticeContent" value=${tNoticeMgrEdit.noticeContent} placeholder="公告发布内容...">
					<#else>
						<input type="text" class="form-control" name="noticeContent" id="tNoticeMgr_edit_noticeContent" value='无' placeholder="公告发布内容...">
					</#if>
				</div>


				<div class="form-group">
					<label id="createorLabel">公告发布人</label>
					<#if tNoticeMgrEdit.createor??>
						<input type="text" class="form-control" name="createor" id="tNoticeMgr_edit_createor" value=${tNoticeMgrEdit.createor} placeholder="公告发布人...">
					<#else>
						<input type="text" class="form-control" name="createor" id="tNoticeMgr_edit_createor" value=${tNoticeMgrEdit.createor} placeholder="公告发布人...">
					</#if>
				</div>


				<div class="form-group">
					<label id="tNoticeMgr_edit_isTopLabel">公告是否置顶</label>
					<select name="isTop" class="form-control select2" style="width: 100%;">
						<option <#if tNoticeMgrEdit.isTop=='1'>selected="selected"</#if> value="1">置顶</option>
						<option <#if tNoticeMgrEdit.isTop=='0'>selected="selected"</#if> value="0">不置顶</option>
					</select>
				</div>


			</div>
			<div class="box-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
					<button onclick="tNoticeMgrEditUpdate();" type="button" class="btn btn-primary btn-sm"><i class="fa fa-paste"></i>更新</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	function tNoticeMgrEditUpdate(){
		$("span").remove(".errorClass");
		$("br").remove(".errorClass");
		var status = 1;
		if($("#tNoticeMgr_edit_title").val()==""){
			$("#tNoticeMgr_edit_titleLabel").prepend('<span class="errorClass" style="color:red">*公告标题不能为空</span><br class="errorClass"/>');
			status = 0;
		}
		if($("#tNoticeMgr_edit_subtitle").val()==""){
			$("#tNoticeMgr_edit_subtitleLabel").prepend('<span class="errorClass" style="color:red">*公告副标题不能为空</span><br class="errorClass"/>');
			status = 0;
		}
		if($("#tNoticeMgr_edit_noticeContent").val()==""){
			$("#tNoticeMgr_edit_noticeContentLabel").prepend('<span class="errorClass" style="color:red">*公告内容不能为空</span><br class="errorClass"/>');
			status = 0;
		}
		if($("#tNoticeMgr_edit_createor").val()==""){
			$("#tNoticeMgr_edit_createorLabel").prepend('<span class="errorClass" style="color:red">*公告发布人不能为空</span><br class="errorClass"/>');
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
			url: '${request.contextPath}/tNoticeMgr/update',
			type: 'post',
			dataType: 'text',
			data: $("#noticeEditForm").serialize(),
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