<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">资料清单管理</h3>

                <div class="box-tools pull-left">
	                <@shiro.hasPermission name="/tDevelopmentInformationListTemp/add">
	                    <a onclick="securityToListAjax();" class="btn btn-sm btn-primary" target="modal" modal="lg"
	                       href="${request.contextPath}/tDevelopmentInformationListTemp/add">添加</a>
	                </@shiro.hasPermission>
                </div>
            </div>
            <div class="box-body">
                <div class="clearfix">
                    <div class="col-md-4">
                        <div class="input-group date ">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                            <input type="text" class="form-control " id="fInfoType1" placeholder="资料一级分类...">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                            <input type="text" class="form-control" id="fInfoType2" placeholder="资料二级分类...">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <button type="submit" onclick="DevelopmentInfoReload();" class="btn btn-primary">搜索</button>
                    </div>
                </div>
                <table id="DevelopmentInfo_tab" class="table table-bordered table-striped">
                    <thead>
                    <tr>
                    <tr>
                        <th>序号</th>
                        <th>资料一级分类</th>
                        <th>资料二级分类</th>
                        <th>资料名称</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var DevelopmentInfo_tab;
    $(function () {
        //初始化时间选择器
        $('#securityTime').datepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayHighlight: true
        });
        //初始化表格

        var No = 0;
        DevelopmentInfo_tab = $('#DevelopmentInfo_tab').DataTable({
            "dom": 'itflp',
            "processing": true,
            "searching": false,
            "serverSide": true, //启用服务器端分页
            "bInfo": false,
            "language": {"url": "adminlte/plugins/datatables/language.json"},
            "ajax": {
                "url": "${request.contextPath}/tDevelopmentInformationListTemp/page",
                "type": "post",
                "data": function(d) {
                    //自定义查询参数
                    d.roleId = $("#roleId").val();
                }
            },
            "columns": [
                {"data": null},
                {"data": "fInfoType1"},
                {"data": "fInfoType2"},
                {"data": "fInfoName"},
                {"data": "fCreateor"},
                {"data": "fCreateTime"},
                {"data": null}
            ],
            "columnDefs": [
                {
                    targets: 0,
                    data: null,
                    render: function (data) {
                        No = No + 1;
                        return No;
                    }
                },
                {
                    "targets": -1,
                    "data": null,
                    "render": function (data) {
                    console.log(data);
                        var btn = "";
                        btn = '<a class="btn btn-xs btn-primary" target="modal" modal="lg" href="${request.contextPath}/tDevelopmentInformationListTemp/view/' + data.fInId + '">查看</a> &nbsp;';
                        if (isNull(data.role) || 'super' != data.role.value) {
                            btn += '<@shiro.hasPermission name="/tDevelopmentInformationListTemp/edit">'
                                    + '<a class="btn btn-xs btn-info" onclick="securityToListAjax();" data-title="修改" target="modal" modal="lg" href="${request.contextPath}/tDevelopmentInformationListTemp/edit/'+ data.fInId+ '">修改</a> &nbsp;'
                                    +'</@shiro.hasPermission>'
                                    + '<@shiro.hasPermission name="/tDevelopmentInformationListTemp/delete">'
                                    + '<a class="btn btn-xs btn-default" callback="DevelopmentInfoReload();" data-body="确认要删除吗？" target="ajaxTodo" href="${request.contextPath}/tDevelopmentInformationListTemp/delete/'+ data.fInId + '">删除</a>'
                                    +'</@shiro.hasPermission>';
                        }
                        return btn;
                    }
                }]
        }).on('preXhr.dt', function (e, settings, data) {
            No = 0;
        });

        $("#securitySeek").on("click", function () {
//          reloadTable(DevelopmentInfo_tab, "#fInfoType1", "#fInfoType2");
            DevelopmentInfoReload();
        });
    });

    function DevelopmentInfoReload() {
        //reloadTable(DevelopmentInfo_tab);
        var fInfoType1 = $("#fInfoType1").val();
        var fInfoType2 = $("#fInfoType2").val();
        var param = {
            "fInfoType1": fInfoType1,
            "fInfoType2": fInfoType2,
        };
        DevelopmentInfo_tab.settings()[0].ajax.data = param;
        DevelopmentInfo_tab.ajax.reload();
    }

    function securityToListAjax() {
        list_ajax = DevelopmentInfo_tab;
        console.log(list_ajax);
    }
    function isNull(data) {
        return (data == "" || data == undefined || data == null) ? true : false;
    }
</script>