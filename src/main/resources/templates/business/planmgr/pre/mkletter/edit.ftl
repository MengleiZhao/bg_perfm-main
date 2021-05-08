<div class="row">
	<style>
		body{font-family: '微软雅黑'; color: #666;}
		input:focus { outline: none;}
		#submit_btn {width: 110px;height:30px;background-color: #1DBC88;border: none; border-radius:16px;color: #fff;
			font-size: 15px; font-weight: bold; cursor: pointer;}
	</style>
	<body>
	<div style="width: 100%;height:90%;margin: 0 auto;">
		<div style="width: 100%;height:100%;">
			<form id="upload_form" method="post" enctype="multipart/form-data" action="/tResearchLetterTemp/save?serviceType=template">
				<div style="width:100%;height:100%;padding-left: 10px;float: left;border:1px dashed #BFEDF0">
					<div style="padding-top: 10px;">
						<table>
							<tr>
								<td>
									<label id="nticeContentLabel">调研函内容</label>
									<input type="textarea" class="form-control" name="researchContent" id="edit_researchContent"
										   placeholder="输入调研函内容...">
								</td>
							</tr>
							<tr>
								<td><input id="files" type="file" name="files" multiple="multiple" onchange="fileOnchange(this)" style=""></td>
							</tr>
							<tr>
								<td>
									<!-- 显示上传的文件名 -->
									<div class="files_name" style="padding-left:46px;"></div>
								</td>
							</tr>

						</table>
					</div>
					<div class="progress" style="display: none;">
						<span class="bar"></span><span class="percent">0%</span>
					</div>
					<div class="upload_status"></div>
					<div style="height: 30px;text-align: center;padding-top: 40px;">
						<input id="submit_btn" type="button" onclick="submitClickUpload()" value="上传"/>
					</div>
				</div>
				<#--	<div style="width:48%;height: 100%;padding-left: 10px;float: left;">
                        <div style="border: 1px dashed #BFEDF0;width: 100%;height: 100%;overflow: auto">
                            <div style="text-align: center;"><h4>上传记录</h4></div>
                            <div id="upload_log" style="width: 100%;height: 80%;font-size:12px;overflow: auto;">

                            </div>
                        </div>
                    </div>-->
			</form>
		</div>
	</div>
	<script type="text/javascript">
		var bar = $('.bar');//进度条
		var percent = $('.percent');//获取上传百分比
		var progress = $('.progress');//显示进度的div
		var files = $('.files'); 	// 文件上传控件的input元素
		var upStatus = $('.upload_status');  // 上传状态
		var filesName = $('.files_name');  // 上传的文件名称
		//var upLog = $('#upload_log');  // 上传记录
		function submitClickUpload(){
			var fileval = $('#files').val();
			if(fileval == ''){
				alert('请选择文件!');
				return;
			}
			$('#upload_form').ajaxSubmit({
				dataType :'json',//返回数据类型
				beforeSend:function(){
					progress.show();
					var percentVal = '0%';
					bar.width(percentVal);
					percent.html(percentVal);
					upStatus.html('上传中..');
				},
				//更新进度条事件处理代码
				uploadProgress:function(event,position,total,percentComplete){
					var percentVal = percentComplete + '%';
					bar.width(percentVal);
					percent.html(percentVal);
					console.log(percentVal, position, total);
				},
				success:function(data){//图片上传成功时
					upStatus.html(data.msg);
					//upLog.append('</br>'+data.names);
					filesName.empty();
					$('#upload_form').resetForm();
					reloadTable(list_ajax, "#roleTime", "#rolePremise");
				},
				error:function(xhr){
					percent.html('0%');
					upStatus.html('上传失败');
					bar.width('0');
					files.html(xhr.responseText);
				}

			});
			return false;
		}
		function fileOnchange(elm){
			var files = elm.files;
			var names = '';
			if(files.length > 0){
				for(var i = 0;i < files.length; i++){
					if(i == files.length-1){
						names += files[i].name;
					}else{
						names += files[i].name+'</br>';
					}
				}
				filesName.html(names);
			}else{
				filesName.html('');
			}
		};
	</script>
</div>
