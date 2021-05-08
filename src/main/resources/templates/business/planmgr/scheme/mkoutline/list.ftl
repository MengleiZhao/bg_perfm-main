<div class="row">
	<div class="col-xs-12">
		<div class="box">
		
		
			<div class="box-header">
				<h3 class="box-title">调研提纲管理</h3>
				
				<!--新增操作-->
				<div class="box-tools pull-right">				
					<@shiro.hasPermission name="tResearchOutlineTemp/add">
						<a onclick="tResearchOutlineTempToListAjax();" class="btn btn-sm btn-primary" target="modal" modal="lg" href="${request.contextPath}/tResearchOutlineTemp/add">添加</a>
					</@shiro.hasPermission>
				</div>
				
				<div class="clearfix">
                    <div class="col-md-4">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                            <input type="text" class="form-control" id="tResearchOutlineTemp_list_outlineName" placeholder="根据调研名称搜索...">
                        </div>
                    </div>
                    

                     <div class="col-md-4">
                        <button type="submit" onclick="tResearchOutlineTempReload();" class="btn btn-primary">搜索</button>
                    </div>
                    
                </div>
			
			<!--数据的显示-->	
			</div>
			<div class="box-body">
				<table id="tResearchOutlineTemp_tab" class="table table-bordered table-striped">
					<thead>
						<tr>
							<tr>
								<th>序号</th>
								<th>调研名称</th>
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
var tResearchOutlineTemp_tab;

//1.在加载页面时就去后台拿数据
$(function() {

		//初始化表格
		var No=0;
	tResearchOutlineTemp_tab = $('#tResearchOutlineTemp_tab').DataTable({
			"dom":'itflp',
			"processing":true,
			"searching":false,
			"serverSide":true, //启用服务器端分页
			"retrieve": true,
			"bInfo":false,
			"language":{"url":"adminlte/plugins/datatables/language.json"},
			"ajax":{
				"url":"${request.contextPath}/tResearchOutlineTemp/page",
				"type":"post"
	                
				},
				
				//1-3.拿到数据后，跟表格进行一对一的对应
				
			"columns":[ 
			    {"data":null}, //序号
				{"data":"outlineName"},//{"data":"outlineName"}中的"outlineName"是实体类中的属性 调研提纲标题
				{"data":null} //操作
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
					targets: -1,
					data : null,
					render : function(data) {
                        //alert(data.idx);
						var btn = '<a class="btn btn-xs btn-primary" target="modal" modal="lg" href="${request.contextPath}/tResearchOutlineTemp/view/'+data.idX+'">查看</a> &nbsp;';
							
								btn = btn+'<@shiro.hasPermission name="tResearchOutlineTemp/edit">'
								+'<a class="btn btn-xs btn-info" onclick="tResearchOutlineTempToListAjax();" target="modal" modal="lg" href="${request.contextPath}/tResearchOutlineTemp/edit/'+data.idX+'">修改</a> &nbsp;'
								+'</@shiro.hasPermission>'
								+'<@shiro.hasPermission name="tResearchOutlineTemp/delete">'
								+'<a class="btn btn-xs btn-default" callback="tResearchOutlineTempReload();" data-body="确认要删除吗？" target="ajaxTodo" href="${request.contextPath}/tResearchOutlineTemp/delete/'+data.idX+ '">删除</a>  &nbsp;'
								+'</@shiro.hasPermission>'
								
							
					return btn;
				}
			} ]
		}).on('preXhr.dt', function ( e, settings, data ) {
		console.log(data,settings)
			No=0;
	    } );
	    
        $("#securitySeek").on("click", function () {

			tResearchOutlineTempReload();//重新加载页面
        });
			
});




//3.重新加载页面函数
function tResearchOutlineTempReload(){
	 	var outlineName = $("#tResearchOutlineTemp_list_outlineName").val();//提纲名称
        var param = {
            "outlineName": outlineName
        };
	tResearchOutlineTemp_tab.settings()[0].ajax.data = param;
	tResearchOutlineTemp_tab.ajax.reload();
	
}

//4.“新增”和“修改”操作
function tResearchOutlineTempToListAjax(){
	list_ajax = tResearchOutlineTemp_tab;
}






</script>