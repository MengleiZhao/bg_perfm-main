<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">国民经济分类管理</h3>
            </div>
            <div class="box-body">
                <div class="clearfix">
                    <div class="col-md-4">
                        <a class="btn btn-sm btn-primary" target="modal" modal="lg"
                           href="${request.contextPath}/tClassifcationOfNationalEconmy/add">添加</a>
                    </div>
                </div>
                <table id="budgetClass_tab" class="table" style="margin-top: 20px">

                </table>
            </div>
        </div>
    </div>
</div>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/other/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/other/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="${request.contextPath}/other/jquery-easyui-1.5.3/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
    $(function () {
        var table = $('#budgetClass_tab').treegrid({
            height: 700,
            rownumbers: true,
            animate: true,
            collapsible: true,
            fitColumns: true,
            url: '${request.contextPath}/tClassifcationOfNationalEconmy/getTreeGridMenu',
            method: 'post',
            idField: 'id',
            treeField: 'name',
            showFooter: true,
            columns: [
                [
                    {
                        title: '类别名称',
                        field: 'name',
                        align: 'center',
                        width: 80
                    },
                    {
                        title: '分类代码',
                        field: 'code',
                        align: 'center',
                        width: 80
                    },
                    {
                        title: '级别',
                        field: 'level',
                        align: 'center',
                        width: 40
                    },
                    {
                        title: '备注',
                        field: 'url',
                        align: 'center',
                        width: 40
                    },
                    {
                        title:'操作',
                        field:'menuId',
                        align: 'center',
                        width: 80,
                        formatter:function(value){
                            // console.log(value);
                            // console.log(data);
                            var  content="";
                            if (value != '000000000000000000'){
                                content = '<a class="btn btn-xs btn-info"  target="modal" modal="lg" href="${request.contextPath}/tClassifcationOfNationalEconmy/edit/' + value + '">编辑</a>'
                                        + " &nbsp;"
                                        +'<a class="btn btn-xs btn-default" onclick="budgetClassdelete('+value+')" >'+"删除"+'</a>'

                            }
                            return content;
                        }

                    }
                ]
            ]
        });
    })
    function budgetClassdelete(id){
        if (confirm('确认要删除吗？')){
            $.ajax({
                url: '${request.contextPath}/tClassifcationOfNationalEconmy/delete/'+id,
                type: 'get',
                dataType: 'text',
                sync:'false',
                success: function (data) {
                    console.log(data);
                    var json = JSON.parse(data);
                    $("#lgModal").modal('hide');
                    if (json.status) {
                        alertMsg("操作成功", "success");
                        reloadClassList();
                    } else {
                        alertMsg("操作失败:" + json.msg, "success");
                    }
                }
            });
        }

    }
    function reloadClassList(){
        $('#budgetClass_tab').treegrid('reload');
    }
    function doClick() {
        var row = $('#tg').treegrid('getSelected');
        console.log(JSON.stringify(row));
    }
</script>