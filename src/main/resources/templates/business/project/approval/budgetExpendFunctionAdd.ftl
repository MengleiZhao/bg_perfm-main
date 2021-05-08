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
        <form id="securityAddForm">
            <div class="modal-body">
                <div class="form-group">
                    <label class="" id="funcCodeLabel">支出功能编号</label>
                    <input type="text" class="form-control" name="funcCode" id="funcCode" placeholder="输入支出功能编号...">
                </div>
                <div class="form-group">
                    <label class="" id="funcNameLabel">支出功能名称</label>
                    <input type="text" class="form-control" name="funcName" id="funcName" placeholder="输入支出功能名称...">
                </div>
                <div class="form-group">
                    <label id="remarkLabel">备注</label>
                    <input type="text" class="form-control" name="remark" id="remark" placeholder="输入备注...">
                </div>
                <div class="form-group">
                    <label id="pfuncremarkLabel">父级菜单</label>
                    <input type="text" readonly class="form-control" name="pName" id="pName" placeholder="选择父级支出功能..."
                           onclick='showMenu(${budgetExpendFunction})'/>
                    <#--<input type="text" hidden id="pId" name="funcCode">
                    <input type="text" hidden id="pCode" name="funcName">-->
                    <input type="text" hidden id="paraentId" name="paraentId">
                    <input type="text" hidden id="level" name="level">
                </div>
            </div>
        </form>
    </div>
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
        <ul id="treeBudgetExpend" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
    </div>

</div>
<div class="modal-footer">
    <div class="pull-right">
        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
        <button type="button" class="btn btn-primary btn-sm" onclick="securitySave();"><i class="fa fa-save"></i>保存
        </button>
    </div>
</div>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" type="text/css" href="other/zTree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript">
    function securitySave() {
        $("span").remove(".errorClass");
        $("br").remove(".errorClass");
        var status = 1;
        if ($("#funcCode").val() == "") {
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
                data: $("#securityAddForm").serialize(),
                success: function (data) {
                    var json = JSON.parse(data);
                    if (json.status) {
                        $("#lgModal").modal('hide');
                        alertMsg("添加成功", "success");
                        reloadMenuList();
                    } else {
                        alertMsg("添加失败:" + json.msg, "success");
                    }
                }
            });
        }
    }
    var setting = {
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
            onClick: onClick,
            onCheck: onCheck
        }
    };

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeBudgetExpend");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        hideMenu();
        return false;
    }

    function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeBudgetExpend"),
                nodes = zTree.getCheckedNodes(true);
        v = "";
        var id = '';
        var code = '';
        var lev=0;
        for (var i = 0, l = nodes.length; i < l; i++) {
            console.log(nodes[i]);
            v += nodes[i].name;
            id = nodes[i].id;
            code = nodes[i].code;
            lev = nodes[i].level;
        }
        if (v.length > 0) v = v.substring(0, v.length - 1);
        var pName = $("#pName");
        var paraentId = $("#paraentId");
        var level = $("#level");
       /* var pId = $("#pId");
        var pCode = $("#pCode");
        pId.attr("value", id);
        pCode.attr("value", code);*/
        level.attr("value", lev+1);
        pName.attr("value", v);
        paraentId.attr("value", id);
        hideMenu();
    }

    function showMenu(node) {
//        alert(node);
        <#--node = ${rcMenu};-->
        initZTree(node);
        var cityObj = $("#pName");
        var cityOffset = $("#pName").offset();
        $("#menuContent").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + "px"
        }).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    }
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "pName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }
    function initZTree(zNodes){
//        alert("node=" + zNodes);
        $.fn.zTree.init($("#treeBudgetExpend"), setting, zNodes);
    };
</script>