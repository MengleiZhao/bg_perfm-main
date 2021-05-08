<div class="row">
    <div class="col-md-12">
        <form id="developmentInfoEditForm">
		 <input type="hidden" id="fInId" name="fInId" value="${td.fInId!}">
			<div class="modal-body">
				<div class="form-group">
					<label id="userNoLabel">资料一级分类</label>
					<input type="text" class="form-control" name="fInfoType1" id="fInfoType1edit" value="${td.fInfoType1!}" placeholder="输入资料一级分类...">
				</div>
				<div class="form-group">
					<label id="passwordLabel">资料二级分类</label>
					<input type="text" class="form-control" name="fInfoType2" id="fInfoType2edit" value="${td.fInfoType2!}" placeholder="输入资料二级分类...">
				</div>
				<div class="form-group">
					<label id="nickNameLabel">资料名称</label>
					<input type="text" class="form-control" name="fInfoName" id="fInfoNameedit" value="${td.fInfoName!}" placeholder="输入资料名称...">
				</div>
            <div class="box-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                            class="fa fa-close"></i>关闭
                    </button>
                    <button onclick="UpdatedevelopInfo();" type="button" class="btn btn-primary btn-sm"><i
                            class="fa fa-paste"></i>更新
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function UpdatedevelopInfo() {
        $.ajax({
            url: '${request.contextPath}/tDevelopmentInformationListTemp/save',
            type: 'post',
            dataType: 'text',
            data: $("#developmentInfoEditForm").serialize(),
            success: function (data) {
                var json = JSON.parse(data);
                if (json.status) {
                    $("#lgModal").modal('hide');
                    alertMsg("更新成功", "success");
                    reloadTable(list_ajax,"#fInfoType1","#fInfoType2");
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