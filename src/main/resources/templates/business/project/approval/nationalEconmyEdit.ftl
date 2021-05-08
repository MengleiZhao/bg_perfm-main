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
            <input type="text" hidden id="cId" name="cId" value="${id!}">
            <div class="modal-body">
                <div class="form-group">
                    <label class="" id="classCodeLabel">支出功能编号</label>
                    <input type="text" class="form-control" name="classCode" id="classClassCodeEdit" value="${bean.classCode!}" placeholder="输入支出功能编号...">
                </div>
                <div class="form-group">
                    <label class="" id="classNameLabel">类别名称</label>
                    <input type="text" class="form-control" name="className" id="classNameEdit" value="${bean.className!}" placeholder="输入类别名称...">
                </div>
                <div class="form-group">
                    <label id="classRemarkLabel">备注</label>
                    <input type="text" class="form-control" name="remark" id="classRemarkEdit" value="${bean.remark!}" placeholder="输入备注...">
                </div>
                <div class="form-group">
                    <label id="classParaentNameLabel">父级菜单</label>
                    <input type="text" readonly class="form-control" name="paraentName" id="classPNameBudgetExpendEdit" value="${bean.paraentName!}" placeholder="输入父级菜单..."
                           onclick='showClassEdit(${nationalEconmy})'/>
                   <#-- <input type="text" hidden id="pId" name="pId" value="${menu.pId!}">
                    <input type="text" hidden id="pCode" name="pCode" value="${menu.pCode!}">-->
                    <input type="text" hidden id="classLevelEdit" name="level" value="${bean.level!}">
                    <input type="text" hidden id="classParaentIdEdit" name="paraentId" value="${bean.paraentId!}">
                </div>
            </div>
        </form>
    </div>
    <div id="budgetExpendClassEdit" class="budgetExpendClassEdit" style="display:none; position: absolute;">
        <ul id="classTreeBudgetExpendEdit" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
    </div>

</div>
<div class="modal-footer">
    <div class="pull-right">
        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
        <button type="button" class="btn btn-primary btn-sm" onclick="classSecuritySaveEdit();"><i class="fa fa-save"></i>更新
        </button>
    </div>
</div>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" type="text/css" href="other/zTree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript">
    function classSecuritySaveEdit() {
        $("span").remove(".errorClass");
        $("br").remove(".errorClass");
        var status = 1;
        if ($("#classClassCodeEdit").val() == "") {
            $("#classCodeLabel").prepend('<span class="errorClass" style="color:#ff0000">*支出功能编号不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if ($("#classNameEdit").val() == "") {
            $("#classNameLabel").prepend('<span class="errorClass" style="color:red">*支出功能名称不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if (status == 0) {
            return false;
        } else {
            $.ajax({
                url: '${request.contextPath}/tClassifcationOfNationalEconmy/save',
                type: 'post',
                dataType: 'text',
                data: $("#securityEditForm").serialize(),
                success: function (data) {
                    var json = JSON.parse(data);
                    if (json.status) {
                        $("#lgModal").modal('hide');
                        alertMsg("更新成功", "success");
                        reloadClassList();
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
        var zTree = $.fn.zTree.getZTreeObj("classTreeBudgetExpendEdit");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        hideMenuEdit();
        return false;
    }

    function onCheckEdit(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("classTreeBudgetExpendEdit"),
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
        var classPNameBudgetExpendEdit = $("#classPNameBudgetExpendEdit");
        var classParaentIdEdit = $("#classParaentIdEdit");
        var classLevelEdit = $("#classLevelEdit");
        /* var pId = $("#pId");
         var pCode = $("#pCode");
         pId.attr("value", id);
         pCode.attr("value", code);*/
        classLevelEdit.attr("value", lev+1);
        classPNameBudgetExpendEdit.attr("value", v);
        classParaentIdEdit.attr("value", id);
        hideMenuEdit();
    }

    function showClassEdit(node) {
        alert('222')
        initZTreeEdit(node);
        var cityObj = $("#classPNameBudgetExpendEdit");
        var cityOffset = $("#classPNameBudgetExpendEdit").offset();
        $("#budgetExpendClassEdit").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + "px"
        }).slideDown("fast");

        $("body").bind("mousedown", onBodyDownEdit);
    }
    function hideMenuEdit() {
        $("#budgetExpendClassEdit").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownEdit);
    }
    function onBodyDownEdit(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "pName" || event.target.id == "budgetExpendClassEdit" || $(event.target).parents("#budgetExpendClassEdit").length > 0)) {
            hideMenuEdit();
        }
    }
    function initZTreeEdit(zNodes){
        $.fn.zTree.init($("#classTreeBudgetExpendEdit"), settingEdit, zNodes);
    };
</script>