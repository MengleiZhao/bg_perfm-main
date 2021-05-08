<div class="row">
    <div class="col-md-12">
        <form id="securityEditForm">
            <input type="hidden" id="id" name="id" value="${bean.id}">
            <div class="box-body">
                <table width="100%" cellspacing="10" cellpadding="10">
                    <tr>
                        <td><label id="groupMemberCodeLabel">员工编号</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="groupMemberCode" id="groupMemberCode" placeholder="输入员工编号..."  value="${bean.groupMemberCode!}">
                        </td>
                        <td>
                            <label id="passwordLabel">密码</label>
                            <input type="password" class="form-control" style="width: 80%;"  name="password" id="password" placeholder="输入密码..." value="${bean.password!}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label >姓名</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="groupMemberName" id="groupMemberName" placeholder="输入姓名..." value="${bean.groupMemberName!}">
                        </td>
                        <td>
                            <label >所在分所</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="branchOfficeName" id="branchOfficeName" placeholder="输入所在分所..." value="${bean.branchOfficeName!}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label >人员级别</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="userLeavel" id="userLeavel" placeholder="输入人员级别..." value="${bean.userLeavel!}">
                        </td>
                        <td>
                            <label >毕业院校</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="graduatedFrom" id="graduatedFrom" placeholder="输入毕业院校..." value="${bean.graduatedFrom!}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label >学历</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="education" id="education" placeholder="输入学历..." value="${bean.education!}">
                        </td>
                        <td>
                            <label >专业</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="major" id="major" placeholder="输入专业..." value="${bean.major!}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label >是否有主评人资格</label>
                            <select name="isQualifiedMainReviewer" class="form-control select2" style="width: 80%;">
                                <option <#if bean.isQualifiedMainReviewer=='是'> selected="selected"</#if> value="是">是</option>
                                <option <#if bean.isQualifiedMainReviewer=='否'> selected="selected"</#if>  value="否">否</option>
                            </select>
                        </td>
                        <td>
                            <label >项目类型</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="projectType" id="projectType" placeholder="输入项目类型..." value="${bean.projectType!}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label >曾担任项目角色</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="previousProjectRole" id="previousProjectRole" placeholder="输入曾担任项目角色..." value="${bean.previousProjectRole!}">
                        </td>
                        <td>
                            <label >工作年限（经验）</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="yearsExperience" id="yearsExperience" placeholder="输入工作年限（经验）..." value="${bean.yearsExperience!}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label >备注</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="remark" id="remark" placeholder="输入备注..." value="${bean.remark!}">
                        </td>
                        <td>
                            <label >数据状态</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="dataStauts" id="dataStauts" placeholder="输入数据状态..." value="${bean.dataStauts!}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label >入库原因</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="inscreaseDesc" id="inscreaseDesc" placeholder="输入入库原因..." value="${bean.inscreaseDesc!}">
                        </td>
                        <td>
                            <label >出库原因</label>
                            <input type="text" class="form-control" style="width: 80%;"  name="reduceDesc" id="reduceDesc" placeholder="输入出库原因..." value="${bean.reduceDesc!}">
                        </td>
                    </tr>

                </table>


            </div>
            <div class="box-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                            class="fa fa-close"></i>关闭
                    </button>
                    <button onclick="securityUpdateUser();" type="button" class="btn btn-primary btn-sm"><i
                            class="fa fa-paste"></i>更新
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function securityUpdateUser() {
        $.ajax({
            url: '${request.contextPath}/user/update',
            type: 'post',
            dataType: 'text',
            data: $("#securityEditForm").serialize(),
            success: function (data) {
                var json = JSON.parse(data);
                if (json.status) {
                    $("#lgModal").modal('hide');
                    alertMsg("更新成功", "success");
                    reloadTable(list_ajax);
                } else {
                    alertMsg("更新失败:" + json.msg, "success");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }

</script>