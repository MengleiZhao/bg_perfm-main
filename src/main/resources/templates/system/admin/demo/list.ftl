<div class="row">
	<div class="col-xs-12">
		<div class="box">
			<div class="box-header">
				<h3 class="box-title">demo管理</h3>
				
				<!--新增操作-->
				<div class="box-tools pull-right">				
					<@shiro.hasPermission name="userDemo/add">
						<a onclick="userDemoToListAjax();" class="btn btn-sm btn-primary" target="modal" modal="lg" href="${request.contextPath}/userDemo/add">新增</a>
					</@shiro.hasPermission>
				</div>
				
			<!--数据的显示-->	
			</div>
			<div class="box-body">
				<table id="demoUser_tab" class="table table-bordered table-striped">
					<thead>
						<tr>
							<tr>
								<th>序号</th>
								<th>用户姓名</th>
								<th>用户年龄</th>
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
var demoUser_tab;

//1.在加载页面时就去后台拿数据
$(function() {

	//初始化表格
	var No=0;
	demoUser_tab = $('#demoUser_tab').DataTable({
		"dom":'itflp',
		"processing":true,
		"searching":false,
		"serverSide":true, //启用服务器端分页
		"bInfo":false,
		"language":{"url":"adminlte/plugins/datatables/language.json"},
		"ajax":{"url":"${request.contextPath}/userDemo/showUserDemo","type":"post"},
		"columns":[ 
		    {"data":null}, 
			{"data":"userName"},//{"data":"userName"}中的"userName"是实体类中的属性
			{"data":"userAge"},
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
					var btn = '<a class="btn btn-xs btn-primary" target="modal" modal="lg" href="${request.contextPath}/userDemo/view/'+ data.id+ '">查看</a> &nbsp;';
						
							btn = btn+'<@shiro.hasPermission name="userDemo/edit">'
							+'<a class="btn btn-xs btn-info" onclick="userDemoToListAjax();" target="modal" modal="lg" href="${request.contextPath}/userDemo/edit/'+ data.id+'">修改</a> &nbsp;'
							+'</@shiro.hasPermission>'
							+'<@shiro.hasPermission name="userDemo/delete">'
							+'<a class="btn btn-xs btn-default" callback="userDemoReload();" data-body="确认要删除吗？" target="ajaxTodo" href="${request.contextPath}/userDemo/delete/'+ data.id + '">删除</a>  &nbsp;'
							+'</@shiro.hasPermission>'
							
						
				return btn;
			}
		} ]
	}).on('preXhr.dt', function ( e, settings, data ) {
	console.log(data,settings)
		No=0;
    } );
});

//2.删除
function userDemoReload(){
	reloadTable(demoUser_tab,"#roleTime","#rolePremise");
}

//3.“新增”和“修改”操作
function userDemoToListAjax(){
	list_ajax = demoUser_tab;
}
</script>