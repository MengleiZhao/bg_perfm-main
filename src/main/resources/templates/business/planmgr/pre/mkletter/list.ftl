<div class="row" xmlns="http://www.w3.org/1999/html">
	<div class="col-xs-12">
		<div class="box">
			<div class="box-header">
				<h3 class="box-title">调研函模板管理</h3>
				<div class="box-tools pull-right">
					<@shiro.hasPermission name="tResearchLetterTemp/upload">
						<a onclick="tResearchLetterTempToListAjax();" class="btn btn-sm btn-primary" target="modal" modal="lg" href="${request.contextPath}/tResearchLetterTemp/upload">上传</a>
					</@shiro.hasPermission>
				</div>
				</p>
				<div class="clearfix">

					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-search"></i></span>
							文件名称：<input type="text" class="form-control" id="tResearchLetterTemp_tab_list_researchName" placeholder="根据文件名称搜索...">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-search"></i></span>
							调研函内容：<input type="text" class="form-control" id="tResearchLetterTemp_tab_list_researchContent" placeholder="根据调研函内容搜索...">
						</div>
					</div>

					<div class="col-md-4">
						<div class="input-group date ">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							上传开始时间：<input type="text" class="form-control pull-right" id="tResearchLetterTemp_tab_list_beginTime" placeholder="选择上传开始时间...">
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group date ">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							上传结束时间：<input type="text" class="form-control pull-right" id="tResearchLetterTemp_tab_list_endTime" placeholder="选择上传结束时间...">
						</div>
					</div>
					<div class="col-md-4">
						<button type="submit" onclick="tResearchLetterTempReload();" class="btn btn-primary">搜索</button>
					</div>

				</div>
			</div>
			<div class="box-body">
				<table id="tResearchLetterTemp_tab" class="table table-bordered table-striped">
					<thead>
					<tr>
						<th>序号</th>
						<th>调研函文件名称</th>
						<th>调研函内容</th>
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
	var tResearchLetterTemp_tab;

	//1.在加载页面时就去后台拿数据
	$(function() {
		//1-1.初始化时间选择器
		$('#tResearchLetterTemp_tab_list_beginTime').datepicker({
			language: 'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayHighlight: true
		});
		$('#tResearchLetterTemp_tab_list_endTime').datepicker({
			language: 'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayHighlight: true
		});

		//1-2.初始化表格
		var No=0;
		tResearchLetterTemp_tab = $('#tResearchLetterTemp_tab').DataTable({
				"dom":'itflp',
				"processing":true,
				"searching":false,
				"serverSide":true, //启用服务器端分页
				"retrieve": true,
				"bInfo":false,
			"language":{"url":"adminlte/plugins/datatables/language.json"},
			"ajax":{
				"url":"${request.contextPath}/tResearchLetterTemp/page",
				"type":"post"
			},

			//1-3.拿到数据后，跟表格进行一对一的对应

			"columns":[
				{"data":null},
				{"data":"researchName"},
				{"data":"researchContent"},
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
						//var btn = '<a class="btn btn-xs btn-primary" target="modal" modal="lg" href="/tResearchLetterTemp/view/'+ data.idX+ '">查看</a> &nbsp;';
						var btn ='';
						btn = btn+'<@shiro.hasPermission name="tResearchLetterTemp/delete">'
						+'<a class="btn btn-xs btn-default" callback="tResearchLetterTempReload();" data-body="确认要删除吗？" target="ajaxTodo" href="${request.contextPath}/tResearchLetterTemp/delete/'+ data.idX + '">删除</a>  &nbsp;'
						+'</@shiro.hasPermission>'


						return btn;
					}
				} ]
		}).on('preXhr.dt', function ( e, settings, data ) {
			console.log(data,settings)
			No=0;
		} );

		$("#securitySeek").on("click", function () {

			tResearchLetterTempReload();//重新加载页面
		});

	});




	//3.重新加载页面函数
	function tResearchLetterTempReload(){
		var researchName = $("#tResearchLetterTemp_tab_list_researchName").val();//文件名称
		var researchContent = $("#tResearchLetterTemp_tab_list_researchContent").val();//调研函内容
		var beginTime = $("#tResearchLetterTemp_tab_list_beginTime").val();//上传开始
		var endTime = $("#tResearchLetterTemp_tab_list_endTime").val();//上传结束时间

		// alert(title); alert(subtitle); alert(createor); alert(createTime);

		var param = {
			"researchName": researchName,
			"researchContent": researchContent,
			"beginTime": beginTime,
			"endTime":endTime
		};
		tResearchLetterTemp_tab.settings()[0].ajax.data = param;
		tResearchLetterTemp_tab.ajax.reload();

	}

	//4.“新增”和“修改”操作
	function tResearchLetterTempToListAjax(){
		list_ajax = tResearchLetterTemp_tab;
	}






</script>