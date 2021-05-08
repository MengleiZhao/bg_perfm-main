<div class="row">
	<div class="col-xs-12">
		<div class="box">
			<div class="box-header">
				<h3 class="box-title">字典管理</h3>
				<div class="box-tools pull-right">
					<@shiro.hasPermission name="rcDict/add">
						<a onclick="rcDictToListAjax();" class="btn btn-sm btn-primary" target="modal" modal="lg" href="${request.contextPath}/rcDict/add">添加</a>
					</@shiro.hasPermission>
				</div>
			</div>
			<div class="box-body">
				<table id="rcDict_tab" class="table table-bordered table-striped">
					<thead>
						<tr>
							<tr>
								<th>序号</th>
								<th>编码</th>
								<th>名称</th>
								<th>类型</th>
								<th>级联依赖</th>
								<th>描述</th>
								<th>排序</th>
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
var rcDict_tab;
$(function() {

	//初始化表格
	var No=0;
	rcDict_tab = $('#rcDict_tab').DataTable({
		"dom":'itflp',
		"processing":true,
		"searching":false,
		"serverSide":true, //启用服务器端分页
		"bInfo":false,
		"language":{"url":"adminlte/plugins/datatables/language.json"},
		"ajax":{"url":"${request.contextPath}/rcDict/page","type":"post"},
		"columns":[ 
		    {"data":null}, 
			{"data":"dicCode"},
			{"data":"dicName"},
			{"data":"type"},
			{"data":"parentCode"},
			{"data":"descript"},
			{"data":"sort"},
			{"data":null} 
			],
		"columnDefs" : [
			{
			    targets: 0,
			    data: null,
			    render: function (data) {
			    	No=No+1;
			        return No;
			    }
			},
            {
				"targets" : -1,
				"data" : null,
				"render" : function(data) {
					var btn = '<a class="btn btn-xs btn-primary" target="modal" modal="lg" href="${request.contextPath}/rcDict/view/'+ data.id+ '">查看</a> &nbsp;';
						if(data.roleValue != 'super'){
							btn = btn+'<@shiro.hasPermission name="rcDict/edit">'
							+'<a class="btn btn-xs btn-info" onclick="roleToListAjax();" target="modal" modal="lg" href="${request.contextPath}/rcDict/edit/'+ data.id+'">修改</a> &nbsp;'
							+'</@shiro.hasPermission>'
							+'<@shiro.hasPermission name="rcDict/delete">'
							+'<a class="btn btn-xs btn-default" callback="rcDictReload();" data-body="确认要删除吗？" target="ajaxTodo" href="${request.contextPath}/rcDict/delete/'+ data.id + '">删除</a>  &nbsp;'
							+'</@shiro.hasPermission>'
						}
				return btn;
			}
		} ]
	}).on('preXhr.dt', function ( e, settings, data ) {
	console.log(data,settings)
		No=0;
    } );
});

function rcDictReload(){
	reloadTable(rcDict_tab);
}

function rcDictToListAjax(){
	list_ajax = rcDict_tab;
}
</script>