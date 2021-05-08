<div class="row" xmlns="http://www.w3.org/1999/html">
	<div class="col-xs-12">
		<div class="box">
			<div class="box-header">
				<h3 class="box-title">项目评价报告模板管理</h3>
				<div class="box-tools pull-right">
					<@shiro.hasPermission name="tProEvalReportTemp/upload">
						<a onclick="tProEvalReportTempToListAjax();" class="btn btn-sm btn-primary" target="modal" modal="lg" href="${request.contextPath}/tProEvalReportTemp/upload">上传</a>
					</@shiro.hasPermission>
				</div>
				</p>
				<div class="clearfix">

					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-search"></i></span>
							文件名称：<input type="text" class="form-control" id="tProEvalReportTemp_tab_list_reportName" placeholder="根据文件名称搜索...">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-search"></i></span>
							备注：<input type="text" class="form-control" id="tProEvalReportTemp_tab_list_remark" placeholder="根据备注搜索...">
						</div>
					</div>

					<div class="col-md-4">
						<div class="input-group date ">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							上传开始时间：<input type="text" class="form-control pull-right" id="tProEvalReportTemp_tab_list_beginTime" placeholder="选择上传开始时间...">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group date ">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							上传结束时间：<input type="text" class="form-control pull-right" id="tProEvalReportTemp_tab_list_endTime" placeholder="选择上传结束时间...">
						</div>
					</div>
					<div class="col-md-4">
						<button type="submit" onclick="tProEvalReportTempReload();" class="btn btn-primary">搜索</button>
					</div>

				</div>
			</div>
			<div class="box-body">
				<table id="tProEvalReportTemp_tab" class="table table-bordered table-striped">
					<thead>
					<tr>
						<th>序号</th>
						<th>报告文件名称</th>
						<th>备注</th>
						<th>文件大小</th>
						<th>上传人</th>
						<th>上传时间</th>
						<th>操作</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	var tProEvalReportTemp_tab;

	//1.在加载页面时就去后台拿数据
	$(function() {
		//1-1.初始化时间选择器
		$('#tProEvalReportTemp_tab_list_beginTime').datepicker({
			language: 'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayHighlight: true
		});
		$('#tProEvalReportTemp_tab_list_endTime').datepicker({
			language: 'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayHighlight: true
		});

		//1-2.初始化表格
		var No=0;
		tProEvalReportTemp_tab = $('#tProEvalReportTemp_tab').DataTable({
				"dom":'itflp',
				"processing":true,
				"searching":false,
				"serverSide":true, //启用服务器端分页
				"retrieve": true,
				"bInfo":false,
			"language":{"url":"adminlte/plugins/datatables/language.json"},
			"ajax":{
				"url":"${request.contextPath}/tProEvalReportTemp/page",
				"type":"post"
			},

			//1-3.拿到数据后，跟表格进行一对一的对应

			"columns":[
				{"data":null},
				{"data":"reportName"},
				{"data":"remark"},
				{"data":"fileSize"},
				{"data":"createor"},
				{"data":"createTime"},
				{"data":null}
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

					"targets" : -1,
					"data" : null,
					"render" : function(data) {
						console.log(data.idX);//看拿到的值
						console.log(data.idX);//看拿到的值
						//var btn = '<a class="btn btn-xs btn-primary" target="modal" modal="lg" href="/tProEvalReportTemp/view/'+ data.idX+ '">查看</a> &nbsp;';
						var btn ='';
						btn = btn+'<@shiro.hasPermission name="tProEvalReportTemp/delete">'
						+'<a class="btn btn-xs btn-default" callback="tProEvalReportTempReload();" data-body="确认要删除吗？" target="ajaxTodo" href="${request.contextPath}/tProEvalReportTemp/delete/'+ data.idX + '">删除</a>  &nbsp;'
						+'</@shiro.hasPermission>'


						return btn;
					}
				} ]
		}).on('preXhr.dt', function ( e, settings, data ) {
			console.log(data,settings)
			No=0;
		} );

		$("#securitySeek").on("click", function () {

			tProEvalReportTempReload();//重新加载页面
		});

	});




	//3.重新加载页面函数
	function tProEvalReportTempReload(){
		var reportName = $("#tProEvalReportTemp_tab_list_reportName").val();//文件名称
		var remark = $("#tProEvalReportTemp_tab_list_remark").val();//报告内容
		var beginTime = $("#tProEvalReportTemp_tab_list_beginTime").val();//上传开始
		var endTime = $("#tProEvalReportTemp_tab_list_endTime").val();//上传结束时间

		// alert(title); alert(subtitle); alert(createor); alert(createTime);

		var param = {
			"reportName": reportName,
			"remark": remark,
			"beginTime": beginTime,
			"endTime":endTime
		};
		tProEvalReportTemp_tab.settings()[0].ajax.data = param;
		tProEvalReportTemp_tab.ajax.reload();

	}

	//4.“新增”和“修改”操作
	function tProEvalReportTempToListAjax(){
		list_ajax = tProEvalReportTemp_tab;
	}






</script>