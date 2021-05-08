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
        <form id="securityClassAddForm">
            <div class="modal-body">
                <div class="form-group">
                    <label class="" id="classCodeAddLabel">分类代码</label>
                    <input type="text" class="form-control" name="classCode" id="classCodeAdd" placeholder="输入分类代码...">
                </div>
                <div class="form-group">
                    <label class="" id="classNameAddLabel">类别名称</label>
                    <input type="text" class="form-control" name="className" id="classNameAdd" placeholder="输入类别名称...">
                </div>
                <div class="form-group">
                    <label id="classRemarkAddLabel">备注</label>
                    <input type="text" class="form-control" name="remark" id="classRemarkAdd" placeholder="输入备注...">
                </div>
                <div class="form-group">
                    <label id="pfuncremarkLabel">父级菜单</label>
                    <input type="text" readonly class="form-control" name="paraentName" id="classPNameAdd" placeholder="选择父级菜单..."
                           onclick='showClassAdd(${nationalEconmyadd})'/>
                    <#--<input type="text" hidden id="pId" name="funcCode">
                    <input type="text" hidden id="pCode" name="funcName">-->
                    <input type="text" hidden id="classParaentIdAdd" name="paraentId">
                    <input type="text" hidden id="classLevelAdd" name="level">
                </div>
            </div>
        </form>
    </div>
    <div id="budgetExpendClassAdd" class="budgetExpendClassAdd" style="display:none; position: absolute;">
        <ul id="classTreeBudgetExpendAdd" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
    </div>

</div>
<div class="modal-footer">
    <div class="pull-right">
        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
        <button type="button" class="btn btn-primary btn-sm" onclick="classSecuritySaveAdd();"><i class="fa fa-save"></i>保存
        </button>
    </div>
</div>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="other/zTree/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" type="text/css" href="other/zTree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript">
    function classSecuritySaveAdd() {
        $("span").remove(".errorClass");
        $("br").remove(".errorClass");
        var status = 1;
        if ($("#classCodeAdd").val() == "") {
            $("#classCodeAddLabel").prepend('<span class="errorClass" style="color:#ff0000">*支出功能编号不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if ($("#classNameAdd").val() == "") {
            $("#classNameAddLabel").prepend('<span class="errorClass" style="color:red">*支出功能名称不能为空</span><br class="errorClass"/>');
            status = 0;
        }
        if (status == 0) {
            return false;
        } else {
            $.ajax({
                url: '${request.contextPath}/tClassifcationOfNationalEconmy/save',
                type: 'post',
                dataType: 'text',
                data: $("#securityClassAddForm").serialize(),
                success: function (data) {
                    var json = JSON.parse(data);
                    if (json.status) {
                        $("#lgModal").modal('hide');
                        alertMsg("添加成功", "success");
                        reloadClassList();
                    } else {
                        alertMsg("添加失败:" + json.msg, "success");
                    }
                }
            });
        }
    }
    var classSettingAdd = {
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
            onClick: classOnClickAdd,
            onCheck: classOnCheckAdd
        }
    };

    function classOnClickAdd(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("classTreeBudgetExpendAdd");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        classHideMenuAdd();
        return false;
    }

    function classOnCheckAdd(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("classTreeBudgetExpendAdd"),
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
        var classPNameAdd = $("#classPNameAdd");
        var classParaentIdAdd = $("#classParaentIdAdd");
        var classLevelAdd = $("#classLevelAdd");
       /* var pId = $("#pId");
        var pCode = $("#pCode");
        pId.attr("value", id);
        pCode.attr("value", code);*/
        classLevelAdd.attr("value", lev+1);
        classPNameAdd.attr("value", v);
        classParaentIdAdd.attr("value", id);
        classHideMenuAdd();
    }

    function showClassAdd(node) {
       alert('111');
        <#--node = ${rcMenu};-->
        classInitZTreeAdd(node);
        var cityObj = $("#classPNameAdd");
        var cityOffset = $("#classPNameAdd").offset();
        $("#budgetExpendClassAdd").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + "px"
        }).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    }
    function classHideMenuAdd() {
        $("#budgetExpendClassAdd").fadeOut("fast");
        $("body").bind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "classPNameAdd" || event.target.id == "budgetExpendClassAdd" || $(event.target).parents("#budgetExpendClassAdd").length > 0)) {
            classHideMenuAdd();
        }
    }
    function classInitZTreeAdd(zNodes){
//        alert("node=" + zNodes);
        $.fn.zTree.init($("#classTreeBudgetExpendAdd"), classSettingAdd, zNodes);
    };
</script>