<div class="row">
	<div class="col-md-12">
		<form id="securityAddForm">
			<div class="modal-body">
				<table width="100%" cellspacing="10" cellpadding="10">
					<tr>
						<td><label id="groupMemberCodeLabel">员工编号</label>
							<input type="text" class="form-control" style="width: 80%;"  name="groupMemberCode" id="groupMemberCode" placeholder="输入员工编号...">
						</td>
						<td>
							<label id="passwordLabel">密码</label>
							<input type="password" class="form-control" style="width: 80%;"  name="password" id="password" placeholder="输入密码...">
						</td>
					</tr>
					<tr>
						<td>
							<label >姓名</label>
							<input type="text" class="form-control" style="width: 80%;"  name="groupMemberName" id="groupMemberName" placeholder="输入姓名...">
						</td>
						<td>
							<label >所在分所</label>
							<input type="text" class="form-control" style="width: 80%;"  name="branchOfficeName" id="branchOfficeName" placeholder="输入所在分所...">
						</td>
					</tr>
					<tr>
						<td>
							<label >人员级别</label>
							<input type="text" class="form-control" style="width: 80%;"  name="userLeavel" id="userLeavel" placeholder="输入人员级别...">
						</td>
						<td>
							<label >毕业院校</label>
							<input type="text" class="form-control" style="width: 80%;"  name="graduatedFrom" id="graduatedFrom" placeholder="输入毕业院校...">
						</td>
					</tr>
					<tr>
						<td>
							<label >学历</label>
							<input type="text" class="form-control" style="width: 80%;"  name="education" id="education" placeholder="输入学历...">
						</td>
						<td>
							<label >专业</label>
							<input type="text" class="form-control" style="width: 80%;"  name="major" id="major" placeholder="输入专业...">
						</td>
					</tr>
					<tr>
						<td>
							<label >是否有主评人资格</label>
							<select name="isQualifiedMainReviewer" class="form-control select2" style="width: 100%;">
								<option value="否">否</option>
								<option value="是">是</option>
							</select>
						</td>
						<td>
							<label >项目类型</label>
							<input type="text" class="form-control" style="width: 80%;"  name="projectType" id="projectType" placeholder="输入项目类型...">
						</td>
					</tr>
					<tr>
						<td>
							<label >曾担任项目角色</label>
							<input type="text" class="form-control" style="width: 80%;"  name="previousProjectRole" id="previousProjectRole" placeholder="输入曾担任项目角色...">
						</td>
						<td>
							<label >工作年限（经验）</label>
							<input type="text" class="form-control" style="width: 80%;"  name="yearsExperience" id="yearsExperience" placeholder="输入工作年限（经验）...">
						</td>
					</tr>
					<tr>
						<td>
							<label >备注</label>
							<input type="text" class="form-control" style="width: 80%;"  name="remark" id="remark" placeholder="输入备注...">
						</td>
						<td>
							<label >数据状态</label>
							<input type="text" class="form-control" style="width: 80%;"  name="dataStauts" id="dataStauts" placeholder="输入数据状态...">
						</td>
					</tr>
					<tr>
						<td>
							<label >入库原因</label>
							<input type="text" class="form-control" style="width: 80%;"  name="inscreaseDesc" id="inscreaseDesc" placeholder="输入入库原因...">
						</td>
						<td>
							<label >出库原因</label>
							<input type="text" class="form-control" style="width: 80%;"  name="reduceDesc" id="reduceDesc" placeholder="输入出库原因...">
						</td>
					</tr>

				</table>
			</div>
			<div class="modal-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
					<button type="button" class="btn btn-primary btn-sm" onclick="securitySave();"><i class="fa fa-save"></i>保存</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
function securitySave(){
	$("span").remove(".errorClass");
	$("br").remove(".errorClass");
	var status = 1;
	if($("#groupMemberCode").val()==""){
		$("#groupMemberCodeLabel").prepend('<span class="errorClass" style="color:red">*员工编号不能为空</span><br class="errorClass"/>');
		status = 0;
	}
	if($("#password").val()==""){
		$("#passwordLabel").prepend('<span class="errorClass" style="color:red">*密码不能为空</span><br class="errorClass"/>');
		status = 0;
	}
	if(status == 0){
		return false;
	}else{
		$.ajax({
			url: '${request.contextPath}/user/save',
	        type: 'post',
	        dataType: 'text',
	        data: $("#securityAddForm").serialize(),
	        success: function (data) {
                var json = JSON.parse(data);
                if (json.status){
                    $("#lgModal").modal('hide');
                    alertMsg("添加成功","success");
                    reloadTable(list_ajax,"#roleTime","#rolePremise");
                }else{
                    alertMsg("添加失败:"+json.msg,"success");
                }
	        }
		});
	}
}
</script>