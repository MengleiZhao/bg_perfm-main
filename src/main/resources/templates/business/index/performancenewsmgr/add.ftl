<div class="row">
    <div class="col-md-12">
        <form id="tPerformanceNewsMgrAddForm">
            <div class="modal-body">
                <div class="form-group">
                    <label id="tPerformanceNewsMgr_add_titleLabel">绩效新闻标题</label>
                    <input type="text" class="form-control" name="title" id="tPerformanceNewsMgr_add_title" placeholder="输入绩效新闻标题...">
                </div>
                <div class="form-group">
                    <label id="tPerformanceNewsMgr_add_subtitleLabel">公告绩效新闻副标题</label>
                    <input type="text" class="form-control" name="subtitle" id="tPerformanceNewsMgr_add_subtitle" placeholder="输入绩效新闻副标题...">
                </div>

                <div class="form-group">
                    <label id="tPerformanceNewsMgr_add_nticeContentLabel">绩效新闻发布内容</label>
                    <input type="text" class="form-control" name="noticeContent" id="tPerformanceNewsMgr_add_nticeContent"
                           placeholder="输入绩效新闻发布内容...">
                </div>

                <div class="form-group">
                    <label id="tPerformanceNewsMgr_add_createorLabel">绩效新闻发布人</label>
                    <input type="text" class="form-control" name="createor" id="tPerformanceNewsMgr_add_createor"
                           value="${user.name}" readonly="true">
                </div>

                <div class="form-group">
                    <label id="tPerformanceNewsMgr_add_isTopList" >是否置顶</label>
                    <select name="isTop"class="form-control select2" style="width: 100%;">
                        <option selected="selected" value="0">不置顶</option>
                        <option value="1">置顶</option>
                    </select>
                </div>


            </div>
            <div class="modal-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                                class="fa fa-close"></i>取消
                    </button>
                    <button type="button" class="btn btn-primary btn-sm" onclick="tPerformanceNewsMgrAdd();"><i
                                class="fa fa-save"></i>保存
                    </button>
                </div>
            </div>

    </form>
</div>
</div>
<script type="text/javascript">

    //1.添加的保存按钮
    function tPerformanceNewsMgrAdd() {
        $("span").remove(".errorClass");
        $("br").remove(".errorClass");
        var status = 1;
        if ($("#tPerformanceNewsMgr_add_title").val() == "") {
            $("#tPerformanceNewsMgr_add_titleLabel").prepend('<span class="errorClass" style="color:red">*公告标题不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if ($("#tPerformanceNewsMgr_add_subtitle").val() == "") {
            $("#tPerformanceNewsMgr_add_subtitleLabel").prepend('<span class="errorClass" style="color:red">*公告副标题不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if ($("#tPerformanceNewsMgr_add_nticeContent").val() == "") {
            $("#tPerformanceNewsMgr_add_nticeContentLabel").prepend('<span class="errorClass" style="color:red">*公告内容不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if ($("#tPerformanceNewsMgr_add_createor").val() == "") {
            $("#tPerformanceNewsMgr_add_createorLabel").prepend('<span class="errorClass" style="color:red">*公告发布人不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if (status == 0) {
            return false;
        } else {
            $.ajax({
                url: '${request.contextPath}/tPerformanceNewsMgr/save',
                type: 'post',
                dataType: 'text',
                data: $("#tPerformanceNewsMgrAddForm").serialize(),
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