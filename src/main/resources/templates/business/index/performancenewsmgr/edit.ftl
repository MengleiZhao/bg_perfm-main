<div class="row">
	<div class="col-md-12">
		<form id="noticeEditForm">
			<input type="hidden" id="tPerformanceNewsMgr_edit_idx" name="idX" value=${tPerformanceNewsMgrEdit.idX}>
				<div class="form-group">
					<label id="tPerformanceNewsMgr_edit_titleLabel">绩效新闻标题</label>、
					<#if tPerformanceNewsMgrEdit.title??>
						<input type="text" class="form-control" name="title" id="tPerformanceNewsMgr_edit_title" value=${tPerformanceNewsMgrEdit.title} placeholder="请输入绩效新闻标题...">
					<#else>
						<input type="text" class="form-control" name="title" id="tPerformanceNewsMgr_edit_title" value='无' placeholder="请输入绩效新闻标题...">
					</#if>
				</div>
				<div class="form-group">
					<label id="tPerformanceNewsMgr_edit_subtitleLabel">绩效新闻副标题</label>
					<#if tPerformanceNewsMgrEdit.subtitle??>
						<input type="text" class="form-control" name="subtitle" id="tPerformanceNewsMgr_edit_subtitle" value=${tPerformanceNewsMgrEdit.subtitle} placeholder="公告副标题...">
					<#else>
						<input type="text" class="form-control" name="subtitle" id="tPerformanceNewsMgr_edit_subtitle" value='无' placeholder="公告副标题...">
					</#if>
				</div>


				<div class="form-group">
					<label id="tPerformanceNewsMgr_edit_noticeContentLabel">绩效新闻发布内容</label>
					<#if tPerformanceNewsMgrEdit.noticeContent??>
						<input type="text" class="form-control" name="noticeContent" id="tPerformanceNewsMgr_edit_noticeContent" value=${tPerformanceNewsMgrEdit.noticeContent} placeholder="请输入绩效新闻发布内容...">
					<#else>
						<input type="text" class="form-control" name="noticeContent" id="tPerformanceNewsMgr_edit_noticeContent" value='无' placeholder="请输入绩效新闻发布内容...">
					</#if>
				</div>


				<div class="form-group">
					<label id="createorLabel">绩效新闻发布人</label>
					<#if tPerformanceNewsMgrEdit.createor??>
						<input type="text" class="form-control" name="createor" id="tPerformanceNewsMgr_edit_createor" value=${tPerformanceNewsMgrEdit.createor} placeholder="请输入绩效新闻发布人...">
					<#else>
						<input type="text" class="form-control" name="createor" id="tPerformanceNewsMgr_edit_createor" value='无' placeholder="请输入绩效新闻发布人...">
					</#if>
				</div>


				<div class="form-group">
					<label id="tPerformanceNewsMgr_edit_isTopLabel">绩效新闻是否置顶</label>
					<select name="isTop" class="form-control select2" style="width: 100%;">
						<option <#if tPerformanceNewsMgrEdit.isTop=='1'>selected="selected"</#if> value="1">置顶</option>
						<option <#if tPerformanceNewsMgrEdit.isTop=='0'>selected="selected"</#if> value="0">不置顶</option>
					</select>
				</div>



			<div class="box-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
					<button onclick="tPerformanceNewsMgrEditUpdate();" type="button" class="btn btn-primary btn-sm"><i class="fa fa-paste"></i>更新</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	function tPerformanceNewsMgrEditUpdate(){
		$("span").remove(".errorClass");
		$("br").remove(".errorClass");
		var status = 1;
		if($("#tPerformanceNewsMgr_edit_title").val()==""){
			$("#tPerformanceNewsMgr_edit_titleLabel").prepend('<span class="errorClass" style="color:red">*请输入绩效新闻标题不能为空</span><br class="errorClass"/>');
			status = 0;
		}
		if($("#tPerformanceNewsMgr_edit_subtitle").val()==""){
			$("#tPerformanceNewsMgr_edit_subtitleLabel").prepend('<span class="errorClass" style="color:red">*请输入绩效新闻副标题不能为空</span><br class="errorClass"/>');
			status = 0;
		}
		if($("#tPerformanceNewsMgr_edit_noticeContent").val()==""){
			$("#tPerformanceNewsMgr_edit_noticeContentLabel").prepend('<span class="errorClass" style="color:red">*请输入绩效新闻内容不能为空</span><br class="errorClass"/>');
			status = 0;
		}
		if($("#tPerformanceNewsMgr_edit_createor").val()==""){
			$("#tPerformanceNewsMgr_edit_createorLabel").prepend('<span class="errorClass" style="color:red">*请输入绩效新闻发布人不能为空</span><br class="errorClass"/>');
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
			url: '${request.contextPath}/tPerformanceNewsMgr/update',
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