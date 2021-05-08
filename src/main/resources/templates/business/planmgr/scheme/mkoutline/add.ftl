<div class="row">
    <div class="col-md-12">
        <form id="tResearchOutlineTempAddForm">
            <div class="modal-body">
                <div class="form-group">
                    <label id="tResearchOutlineTemp_add_outlineNameLabel">调研提纲名称</label>
                    <input type="text" class="form-control" name="outlineName" id="tResearchOutlineTemp_add_outlineName" placeholder="输入调研提纲名称...">
                </div>
                <div class="form-group">
                    <label id="tResearchOutlineTemp_add_createorLabel">调研提纲创建人</label>
                    <input type="text" class="form-control" name="createor" id="tResearchOutlineTemp_add_createor"
                           value="${user.name}" readonly="true">
                </div>



            </div>
            <div class="modal-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                                class="fa fa-close"></i>取消
                    </button>
                    <button type="button" class="btn btn-primary btn-sm" onclick="tResearchOutlineTempAdd();"><i
                                class="fa fa-save"></i>保存
                    </button>
                </div>
            </div>

    </form>
</div>
</div>
<script type="text/javascript">

    //1.添加的保存按钮
    function tResearchOutlineTempAdd() {
        $("span").remove(".errorClass");
        $("br").remove(".errorClass");
        var status = 1;
        if ($("#tResearchOutlineTemp_add_outlineName").val() == "") {
            $("#tResearchOutlineTemp_add_outlineNameLabel").prepend('<span class="errorClass" style="color:red">*调研提纲不能为空</span><br class="errorClass"/>');
            status = 0;
        }

        if (status == 0) {
            return false;
        } else {
            $.ajax({
                url: '${request.contextPath}/tResearchOutlineTemp/save',
                type: 'post',
                dataType: 'text',
                data: $("#tResearchOutlineTempAddForm").serialize(),
                success: function (data) {
                    var json = JSON.parse(data);
                    if (json.status) {
                        $("#lgModal").modal('hide');
                        alertMsg("添加成功", "success");
                        reloadTable(list_ajax, "#roleTime", "#rolePremise");
                    } else {
                        alertMsg("添加失败:" + json.msg, "success");
                    }
                }
            })
        }
    }
</script>