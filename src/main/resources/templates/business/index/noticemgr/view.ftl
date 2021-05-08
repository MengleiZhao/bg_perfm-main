<div class="row">
	<div class="col-md-12">
		<div class="box-body  no-padding">
			<table class="table table-striped">
                <tr>
                	<td>公告id：</td>
                	<td style="width: 90%">
						<#if tNoticeMgrView.idx??>
							${tNoticeMgrView.idx}
						<#else>
							无
						</#if>
					</td>
                </tr>



                <tr>
                	<td>公告标题：</td>
                	<td>
						<#if tNoticeMgrView.title??>
							${tNoticeMgrView.title}
						<#else>
							无
						</#if>
					</td>
                </tr>
                <tr>
                	<td>公告副标题：</td>
                	<td>
						<#if tNoticeMgrView.subtitle??>
							${tNoticeMgrView.subtitle}
						<#else>
							无
						</#if>
					</td>
                </tr>
                
                <tr>
                	<td>公告内容：</td>
                	<td>

						<#if tNoticeMgrView.noticeContent??>
							<!-- 加载编辑器的容器 -->
							<script id="notice_view_container" name="noticeContent" type="text/html">${tNoticeMgrView.noticeContent}</script>
						<#else>
							<!-- 加载编辑器的容器 -->
							<script id="container" name="noticeContent" type="text/html">...</script>
						</#if>
					</td>
                </tr>
                
                <tr>
                	<td>公告创建时间：</td>
                	<td>
						<#if tNoticeMgrView.createTime??>
							${tNoticeMgrView.createTime}
							<#--${tNoticeMgrView.createTime?string('yyyy-MM-dd HH:mm:ss')}-->

						<#else>
							无
						</#if>
					</td>
                </tr>
                
                 <tr>
                	<td>公告创建人：</td>
					 <td>
						 <#if tNoticeMgrView.createor??>
							 ${tNoticeMgrView.createor}
						 <#else>
							 无
						 </#if>
					 </td>

                </tr>

				<tr>
					<td>是否置顶：</td>
					<td>
						<#if tNoticeMgrView.isTop=='1'>置顶</#if>
						<#if tNoticeMgrView.isTop=='0'>不置顶</#if>
					</td>
				</tr>
               
         	</table>
         	<div class="box-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default btn-sm" id="close" data-dismiss="modal"><i class="fa fa-close"></i>关闭</button>
				</div>
			</div>
    	</div>
	</div>
</div>

<script type="text/javascript">
	var ue = UE.getEditor('notice_view_container');//实体类编辑器
</script>