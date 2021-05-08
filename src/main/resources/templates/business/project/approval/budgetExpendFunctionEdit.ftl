<style>
    ul.ztree {
        margin-top: 10px;
        border: 1px solid #617775;
        background: #f0f6e4;
        width: 220px;
        height: 360px;
        overflow-y: scroll;
        overflow-x: auto;
    }
</style>
<div class="row">
    <div class="col-md-12">
        <form id="securityEditForm">
            <input type="text" hidden id="bId" name="bId" value="${id!}">
            <div class="modal-body">
                <div class="form-group">
                    <label class="" id="funcCodeLabel">支出功能编号</label>
                    <input type="text" class="form-control" name="funcCode" id="funcCodeEdit" value="${bean.funcCode!}" placeholder="输入支出功能编号...">
                </div>
                <div class="form-group">
                    <label class="" id="funcNameLabel">支出功能名称</label>
                    <input type="text" class="form-control" name="funcName" id="funcName" value="${bean.funcName!}" placeholder="输入支出功能名称...">
                </div>
                <div class="form-group">
                    <label id="remarkLabel">备注</label>
                    <input type="text" class="form-control" name="remark" id="remark" value="${bean.remark!}" placeholder="输入备注...">
                </div>
                <div class="form-group">
                    <label id="nickNameLabel">父级菜单</label>
                    <input type="text" readonly class="form-control" name="paraentName" id="pNameBudgetExpendEdit" value="${bean.paraentName!}" placeholder="输入父级菜单..."
                           onclick='showMenuEdit(${budgetExpendFunction})'/>
                   <#-- <input type="text" hidden id="pId" name="pId" value="${menu.pId!}">
                    <input type="text" hidden id="pCode" name="pCode" value="${menu.pCode!}">-->
                    <input type="text" hidden id="levelEdit" name="level" value="${bean.level!}">
                    <input type="text" hidden id="paraentIdEdit" name="paraentId" value="${bean.paraentId!}">
                </div>
            </div>
        </form>
    </div>
    <div id="menuContentBudgetExpendEdit" class="menuContent" style="display:none; position: absolute;">
        <ul id="treeBudgetExpendEdit" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
    </div>

</div>
<div class="modal-footer">
    <div class="pull-right">
        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
        <button type="button" class="btn btn-primary btn-sm" onclick="securitySaveEdit();"><i class="fa fa-save"></i>更新
        </button>
    </div>
</div>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" type="text/css" href="other/zTree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript">
    function securitySaveEdit() {
        $("span").remove(".errorClass");
        $("br").remove(".errorClass");
        var status = 1;
        if ($("#funcCodeEdit").val() == "") {
            $("#funcCodeLabel").prepend('<span class="errorClass" style="color:#ff0000">*支出功能编号不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if ($("#funcName").val() == "") {
            $("#funcNameLabel").prepend('<span class="errorClass" style="color:red">*支出功能名称不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if (status == 0) {
            return false;
        } else {
            $.ajax({
                url: '${request.contextPath}/tBudgetExpendFunctionClass/save',
                type: 'post',
                dataType: 'text',
                data: $("#securityEditForm").serialize(),
                success: function (data) {
                    var json = JSON.parse(data);
                    if (json.status) {
                        $("#lgModal").modal('hide');
                        alertMsg("更新成功", "success");
                        reloadMenuList();
                    } else {
                        alertMsg("更新失败:" + json.msg, "success");
                    }
                }
            });
        }
    }
    var settingEdit = {
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClickEdit,
            onCheck: onCheckEdit
        }
    };

    function onClickEdit(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeBudgetExpendEdit");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        hideMenuEdit();
        return false;
    }

    function onCheckEdit(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeBudgetExpendEdit"),
                nodes = zTree.getCheckedNodes(true);
        v = "";
        var id = '';
        var code = '';
        var lev=0;
        for (var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name + ',';
            id = nodes[i].id;
            code = nodes[i].code;
            lev = nodes[i].level;
        }
        if (v.length > 0) v = v.substring(0, v.length - 1);
        var pNameBudgetExpendEdit = $("#pNameBudgetExpendEdit");
        var paraentIdEdit = $("#paraentIdEdit");
        var levelEdit = $("#levelEdit");
        /* var pId = $("#pId");
         var pCode = $("#pCode");
         pId.attr("value", id);
         pCode.attr("value", code);*/
        levelEdit.attr("value", lev+1);
        pNameBudgetExpendEdit.attr("value", v);
        paraentIdEdit.attr("value", id);
        hideMenuEdit();
    }

    function showMenuEdit(node) {
        initZTreeEdit(node);
        var cityObj = $("#pNameBudgetExpendEdit");
        var cityOffset = $("#pNameBudgetExpendEdit").offset();
        $("#menuContentBudgetExpendEdit").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + "px"
        }).slideDown("fast");

        $("body").bind("mousedown", onBodyDownEdit);
    }
    function hideMenuEdit() {
        $("#menuContentBudgetExpendEdit").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownEdit);
    }
    function onBodyDownEdit(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "pName" || event.target.id == "menuContent" || $(event.target).parents("#menuContentBudgetExpendEdit").length > 0)) {
            hideMenuEdit();
        }
    }
    function initZTreeEdit(zNodes){
        $.fn.zTree.init($("#treeBudgetExpendEdit"), settingEdit, zNodes);
    };
</script>