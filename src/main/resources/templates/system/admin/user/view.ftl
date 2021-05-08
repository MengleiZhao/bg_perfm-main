<div class="row">
	<div class="box-body  no-padding">
		<table width="100%" cellspacing="10" cellpadding="10">
			<tr>
				<td><label id="groupMemberCodeLabel">员工编号</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.groupMemberCode!}">
				</td>
				<td>
					<label >拥有角色</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${(bean.role.name)!}">
				</td>
			</tr>
			<tr>
				<td>
					<label >姓名</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.groupMemberName!}">
				</td>
				<td>
					<label >所在分所</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.branchOfficeName!}">
				</td>
			</tr>
			<tr>
				<td>
					<label >人员级别</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.userLeavel!}">
				</td>
				<td>
					<label >毕业院校</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.graduatedFrom!}">
				</td>
			</tr>
			<tr>
				<td>
					<label >学历</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.education!}">
				</td>
				<td>
					<label >专业</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.major!}">
				</td>
			</tr>
			<tr>
				<td>
					<label >是否有主评人资格</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.isQualifiedMainReviewer!}">
				</td>
				<td>
					<label >项目类型</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.projectType!}">
				</td>
			</tr>
			<tr>
				<td>
					<label >曾担任项目角色</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.previousProjectRole!}">
				</td>
				<td>
					<label >工作年限（经验）</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.yearsExperience!}">
				</td>
			</tr>
			<tr>
				<td>
					<label >备注</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.remark!}">
				</td>
				<td>
					<label >数据状态</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.dataStauts!}">
				</td>
			</tr>
			<tr>
				<td>
					<label >入库原因</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.inscreaseDesc!}">
				</td>
				<td>
					<label >出库原因</label>
					<input type="text" class="form-control" style="width: 80%;"  value="${bean.reduceDesc!}">
				</td>
			</tr>

		</table>
		<div class="box-footer">
			<div class="pull-right">
				<button type="button" class="btn btn-default btn-sm" id="close" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
			</div>
      	</div>
	</div>
</div>