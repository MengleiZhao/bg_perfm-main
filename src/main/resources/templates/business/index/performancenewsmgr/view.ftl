<div class="row">
	<div class="col-md-12">
		<div class="box-body  no-padding">
			<table class="table table-striped">
                <tr>
                	<td>绩效新闻id：</td>
                	<td style="width: 90%">
						<#if tPerformanceNewsMgrView.idX??>
							${tPerformanceNewsMgrView.idX}
						<#else>
							无
						</#if>
					</td>
                </tr>

                <tr>
                	<td>绩效新闻标题：</td>
                	<td>
						<#if tPerformanceNewsMgrView.title??>
							${tPerformanceNewsMgrView.title}
						<#else>
							无
						</#if>
					</td>
                </tr>
                <tr>
                	<td>绩效新闻副标题：</td>
					<td>
						<#if tPerformanceNewsMgrView.subtitle??>
							${tPerformanceNewsMgrView.subtitle}
						<#else>
							无
						</#if>
					</td>
                </tr>
                
                <tr>
                	<td>绩效新闻内容：</td>
                	<td>
						<#if tPerformanceNewsMgrView.noticeContent??>
							${tPerformanceNewsMgrView.noticeContent}
						<#else>
							无
						</#if>
					</td>
                </tr>
                
                <tr>
                	<td>绩效新闻创建时间：</td>
                	<td>
						<#if tPerformanceNewsMgrView.createTime??>
							${tPerformanceNewsMgrView.createTime?string('yyyy-MM-dd')}
						<#else>
							无
						</#if>
					</td>
                </tr>
                
                 <tr>
                	<td>绩效新闻创建人：</td>
                	<td>
						<#if tPerformanceNewsMgrView.createor??>
							${tPerformanceNewsMgrView.createor}
						<#else>
							无
						</#if>
					</td>
                </tr>

				<tr>
					<td>是否置顶：</td>
					<td>
						<#if tPerformanceNewsMgrView.isTop=='1'>置顶</#if>
						<#if tPerformanceNewsMgrView.isTop=='0'>不置顶</#if>
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