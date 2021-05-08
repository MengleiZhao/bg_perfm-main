<div class="row">
	<div class="col-xs-12">
		<div class="box">
		
		
			<div class="box-header">
				<h3 class="box-title">绩效新闻管理</h3>
				
				<!--新增操作-->
				<div class="box-tools pull-right">				
					<@shiro.hasPermission name="tPerformanceNewsMgr/add">
						<a onclick="tPerformanceNewsMgrToListAjax();" class="btn btn-sm btn-primary" target="modal" modal="lg" href="${request.contextPath}/tPerformanceNewsMgr/add">添加</a>
					</@shiro.hasPermission>
				</div>
				
				<div class="clearfix">
                    <div class="col-md-4">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                            <input type="text" class="form-control" id="tPerformanceNewsMgr_list_title" placeholder="根据绩效标题搜索...">
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                            <input type="text" class="form-control" id="tPerformanceNewsMgr_list_subtitle" placeholder="根据绩效副标题搜索...">
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                            <input type="text" class="form-control" id="tPerformanceNewsMgr_list_createor" placeholder="根据绩效发布人搜索...">
                        </div>
                    </div>
                   
                    
                     <div class="col-md-4">
                        <div class="input-group date ">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" class="form-control pull-right" id="tPerformanceNewsMgr_list_createTime" placeholder="根据发布时间搜索...">
                        </div>
                    </div>
                    
                     <div class="col-md-4">
                        <button type="submit" onclick="performanceNewsMgrReload();" class="btn btn-primary">搜索</button>
                    </div>
                    
                </div>
			
			<!--数据的显示-->	
			</div>
			<div class="box-body">
				<table id="performanceNewsMgr_tab" class="table table-bordered table-striped">
					<thead>
						<tr>
							<tr>
								<th>序号</th>
								<th>绩效新闻标题</th>
								<th>绩效新闻副标题</th>
								<th>绩效新闻发布时间</th>
								<th>绩效新闻发布人</th>
								<th>是否置顶</th>
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
var performanceNewsMgr_tab;

//1.在加载页面时就去后台拿数据
$(function() {
	
		//1-1.初始化时间选择器
        $('#tPerformanceNewsMgr_list_createTime').datepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayHighlight: true
        });
	
		
		//1-2.初始化表格
		var No=0;
    performanceNewsMgr_tab = $('#performanceNewsMgr_tab').DataTable({
			"dom":'itflp',
			"processing":true,
			"searching":false,
			"serverSide":true, //启用服务器端分页
			"retrieve": true,
			"bInfo":false,
			"language":{"url":"adminlte/plugins/datatables/language.json"},
			"ajax":{
				"url":"${request.contextPath}/tPerformanceNewsMgr/page",
				"type":"post"
	                
				},
				
				//1-3.拿到数据后，跟表格进行一对一的对应
				
			"columns":[ 
			    {"data":null}, //序号
				{"data":"title"},//{"data":"title"}中的"title"是实体类中的属性 公告标题
				{"data":"subtitle"},//公告副标题
				{"data":"createTime"},//公告发布时间
				{"data":"createor"},//公告发布人
				{"data":null},//是否置顶
				{"data":null} //操作
				],
				
			"columnDefs" : [
	  			{
	  			//alert(data.fIsTop),
				    targets: 0,
				    data: null,
				    render: function (data) {
				    	No=No+1;
				        return No;
				    }
				},
				
				{               
		                targets: 5,
		                data: null,
		                render: function (data) {

		                    if (data.isTop=='1') {
		                        return "置顶";
		                    }
		                    if (data.isTop=='0') {
		                        return "不置顶";
		                    }
		                    return "未知状态";
		                }
	                },
				
	            {

					targets: -1,
					data : null,
					render : function(data) {
                        //alert(data.idx);
						var btn = '<a class="btn btn-xs btn-primary" target="modal" modal="lg" href="${request.contextPath}/tPerformanceNewsMgr/view/'+data.idX+'">查看</a> &nbsp;';
							
								btn = btn+'<@shiro.hasPermission name="tPerformanceNewsMgr/edit">'
								+'<a class="btn btn-xs btn-info" onclick="tPerformanceNewsMgrToListAjax();" target="modal" modal="lg" href="${request.contextPath}/tPerformanceNewsMgr/edit/'+data.idX+'">修改</a> &nbsp;'
								+'</@shiro.hasPermission>'
								+'<@shiro.hasPermission name="tPerformanceNewsMgr/delete">'
								+'<a class="btn btn-xs btn-default" callback="performanceNewsMgrReload();" data-body="确认要删除吗？" target="ajaxTodo" href="${request.contextPath}/tPerformanceNewsMgr/delete/'+data.idX+ '">删除</a>  &nbsp;'
								+'</@shiro.hasPermission>'
								
							
					return btn;
				}
			} ]
		}).on('preXhr.dt', function ( e, settings, data ) {
		console.log(data,settings)
			No=0;
	    } );
	    
        $("#securitySeek").on("click", function () {

            performanceNewsMgrReload();//重新加载页面
        });
			
});




//3.重新加载页面函数
function performanceNewsMgrReload(){
	//reloadTable(performanceNewsMgr_tab,"#createTime","#createor");
	 	var title = $("#tPerformanceNewsMgr_list_title").val();//标题
        var subtitle = $("#tPerformanceNewsMgr_list_").val();//副标题
        var createor = $("#tPerformanceNewsMgr_list_createor").val();//发布人
        var createTime = $("#tPerformanceNewsMgr_list_createTime").val();//发布时间
        
       // alert(title); alert(subtitle); alert(createor); alert(createTime);
        
        var param = {
            "title": title,
            "subtitle": subtitle,
            "createor":createor,
            "createTime":createTime
        };
    performanceNewsMgr_tab.settings()[0].ajax.data = param;
    performanceNewsMgr_tab.ajax.reload();
	
}

//4.“新增”和“修改”操作
function tPerformanceNewsMgrToListAjax(){
	list_ajax = performanceNewsMgr_tab;
}






</script>